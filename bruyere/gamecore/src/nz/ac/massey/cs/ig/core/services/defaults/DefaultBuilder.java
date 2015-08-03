package nz.ac.massey.cs.ig.core.services.defaults;

import java.util.Map;

import nz.ac.massey.cs.ig.core.game.model.BotData;
import nz.ac.massey.cs.ig.core.services.ProgrammingLanguageSupport;
import nz.ac.massey.cs.ig.core.services.build.BotFactory;
import nz.ac.massey.cs.ig.core.services.build.BuildProblemCollector;
import nz.ac.massey.cs.ig.core.services.build.BuildResult.BuildStatus;
import nz.ac.massey.cs.ig.core.services.build.Builder;
import nz.ac.massey.cs.ig.core.services.build.BuilderException;
import nz.ac.massey.cs.ig.core.services.build.verifier.DynamicVerifier;
import nz.ac.massey.cs.ig.core.services.build.verifier.DynamicVerifier.DynamicVerificationResult;
import nz.ac.massey.cs.ig.core.services.build.verifier.StaticASTVerifier;
import nz.ac.massey.cs.ig.core.services.build.verifier.StaticByteCodeVerifier;
import nz.ac.massey.cs.ig.core.services.build.verifier.VerifierException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

/**
 * Base class for builders
 * 
 * @author Johannes Tandler
 *
 */
public abstract class DefaultBuilder implements Builder {

	/**
	 * True if builds should be instrumented
	 */
	protected boolean instrument;

	/**
	 * used {@link ProgrammingLanguageSupport}
	 */
	private ProgrammingLanguageSupport languageSupport;

	/**
	 * used {@link Logger}
	 */
	protected Logger logger;

	/**
	 * used byte code verifier
	 */
	private StaticByteCodeVerifier byteCodeVerifier;

	/**
	 * dynamic verifier
	 */
	private DynamicVerifier dynamicVerifier;

	/**
	 * static ast verifier
	 */
	protected StaticASTVerifier astVerifier;

	/**
	 * build problem collector
	 */
	protected BuildProblemCollector dLog;

	/**
	 * current build status
	 */
	private BuildStatus status;

	/**
	 * default constructor
	 * 
	 * @param languageSupport
	 * @param logger
	 */
	public DefaultBuilder(ProgrammingLanguageSupport languageSupport,
			Logger logger) {
		instrument = false;
		this.languageSupport = languageSupport;
		this.logger = logger;
		this.byteCodeVerifier = null;
	}

	@Override
	public BotFactory build(BotData data) throws BuilderException,
			VerifierException {
		// log starting time
		long start = System.currentTimeMillis();
		logger.info("building bot " + data.getId());

		dLog = new BuildProblemCollector();

		status = BuildStatus.OTHERS;

		BotFactory factory = null;
		try {
			// if programming language supports ast verification verify ast
			if (astVerifier != null) {
				try {
					verifyAST(data);
				} catch (Exception e) {
					status = BuildStatus.AST_VERIFICATION_FAILED;
					e.printStackTrace();
					throw e;
				}
			}

			// compile
			Object buildArtefact = null;
			try {
				buildArtefact = compile(data, languageSupport);
			} catch (Exception e) {
				status = BuildStatus.COMPILATION_FAILED;
				e.printStackTrace();
				throw e;
			}

			// if programming langue supports
			if (byteCodeVerifier != null) {
				try {
					verifyByteCode(data, buildArtefact, byteCodeVerifier);
				} catch (Exception e) {
					status = BuildStatus.BYTECODE_VERIFICATION_FAILED;
					throw e;
				}
			}

			// instrument
			if (instrument) {
				buildArtefact = instrumentByteCode(data, buildArtefact,
						languageSupport);
			}

			// create bot factory
			factory = createBotFactory(data, buildArtefact);

			// dynamic verification
			if (dynamicVerifier != null) {
				DynamicVerificationResult veriResult = verifyBotInstance(factory);
				if (veriResult == DynamicVerificationResult.TIMEOUT) {
					status = BuildStatus.TESTS_FAILED_TIMEOUT;
					throw new VerifierException("Tests timed out");
				} else if (veriResult == DynamicVerificationResult.FAILED) {
					status = BuildStatus.TESTS_FAILED_FUNC;
					throw new VerifierException("Tests failed");
				} else {
					status = BuildStatus.OK;
				}
			} else {
				status = BuildStatus.OK;
			}
		} catch (VerifierException e) {
			if (logger != null && logger.getLevel() != null
					&& logger.getLevel().isLessSpecificThan(Level.FATAL)) {
				logger.log(Level.ERROR, e.toString());
			}
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BuilderException(e);
		} finally {
			long end = System.currentTimeMillis();
			logger.info("building bot " + data.getId() + " done, this took "
					+ (end - start) + "ms");
		}

		return factory;
	}

	/**
	 * Dynamic verification
	 * 
	 * @param factory
	 *            factory for bots
	 * @throws VerifierException
	 */
	private DynamicVerificationResult verifyBotInstance(BotFactory factory) {
		DynamicVerificationResult result = dynamicVerifier
				.verify(factory, dLog);

		if (logger.isDebugEnabled()) {
			logger.debug("Dynamic verification done for bot "
					+ factory.getBotId());
		}
		if (dLog.isError()) {
			logger.warn("Dynamic verification failed for bot "
					+ factory.getBotId());
		}
		return result;
	}

	/**
	 * Verifies the AST of a given bot data
	 * 
	 * @param data
	 * @throws VerifierException
	 */
	protected void verifyAST(BotData data) throws VerifierException {
		astVerifier.verify(data.getSrc(), dLog);
		logVerifier(data.getId(), astVerifier.getClass().getName(), dLog,
				logger);
	}

	/**
	 * Sets the {@link StaticByteCodeVerifier}
	 */
	public void setByteCodeVerifier(StaticByteCodeVerifier verifier) {
		this.byteCodeVerifier = verifier;
	}

	/**
	 * Sets the {@link DynamicVerifier}
	 */
	public void setDynamicVerifier(DynamicVerifier verifier) {
		this.dynamicVerifier = verifier;
	}

	/**
	 * Compiles a {@link BotData}.
	 * 
	 * @param data
	 * @param support
	 * @param dLog
	 * @return
	 * @throws BuilderException
	 */
	protected abstract Object compile(BotData data,
			ProgrammingLanguageSupport support) throws BuilderException;

	/**
	 * Verifies the bytecode / buildArtefact of a compiled bot
	 * 
	 * @param data
	 * @param buildArtefact
	 * @param byteCodeVerifier
	 * @throws VerifierException
	 */
	protected void verifyByteCode(BotData data, Object buildArtefact,
			StaticByteCodeVerifier byteCodeVerifier) throws VerifierException {

		// bytecode verification
		if (byteCodeVerifier != null) {
			if (buildArtefact instanceof Iterable<?>) {
				for (Object artefact : ((Iterable<?>) buildArtefact)) {
					if (!(artefact instanceof byte[])) {
						throw new UnsupportedOperationException(
								"Only verification of byte[] is allowed");
					}

					byteCodeVerifier.verify((byte[]) artefact, dLog);
				}
			} else if (buildArtefact instanceof Map<?, ?>) {
				Map<?, ?> artefacts = (Map<?, ?>) buildArtefact;
				for (Object key : artefacts.keySet()) {
					Object value = artefacts.get(key);
					if (!(value instanceof byte[])) {
						throw new UnsupportedOperationException(
								"Only verification of byte[] is allowed");
					}
					byteCodeVerifier.verify((byte[]) value, dLog);
				}
			} else {
				throw new UnsupportedOperationException();
			}
			logVerifier(data.getId(), byteCodeVerifier.getClass().getName(),
					dLog, logger);
		}
	}

	/**
	 * instruments the byte code. Default implementation does nothing. Has to be
	 * implemented by real Builders.
	 * 
	 * @param data
	 * @param buildArtefact
	 * @param support
	 * @return
	 */
	protected Object instrumentByteCode(BotData data, Object buildArtefact,
			ProgrammingLanguageSupport support) {
		return buildArtefact;
	}

	/**
	 * Creates the BotFactory from a given build artefact.
	 * 
	 * @param data
	 * @param buildArtefact
	 * @return
	 * @throws BuilderException
	 */
	protected abstract BotFactory createBotFactory(BotData data,
			Object buildArtefact) throws BuilderException;

	@Override
	public void setInstrumenationSupport(boolean enabled) {
		if (!isInstrumentationSupported() && enabled) {
			throw new UnsupportedOperationException(
					"You can not activate instrumentation if it is not supported");
		}
		this.instrument = enabled;
	}

	protected void logVerifier(String botId, String verifierName,
			BuildProblemCollector dLog, Logger logger) throws VerifierException {
		if (logger.isDebugEnabled()) {
			logger.debug("verification with verifier " + verifierName
					+ " done for bot " + botId);
		}
		if (dLog.isError()) {
			logger.warn("verification with verifier " + verifierName
					+ " failed for bot " + botId);
			throw new VerifierException(
					"Verification failed, verifier used was: " + verifierName);
		}
	}

	public void setAstVerifier(StaticASTVerifier astVerifier) {
		this.astVerifier = astVerifier;
	}

	@Override
	public BuildProblemCollector getBuildProblemCollector() {
		return dLog;
	}

	@Override
	public BuildStatus getCurrentStatus() {
		return status;
	}
}
