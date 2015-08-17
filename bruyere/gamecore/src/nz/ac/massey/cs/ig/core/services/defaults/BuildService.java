package nz.ac.massey.cs.ig.core.services.defaults;

import javax.persistence.EntityManager;
import javax.tools.Diagnostic.Kind;

import nz.ac.massey.cs.ig.core.game.Bot;
import nz.ac.massey.cs.ig.core.game.model.BotData;
import nz.ac.massey.cs.ig.core.services.BotCache;
import nz.ac.massey.cs.ig.core.services.GameSupport;
import nz.ac.massey.cs.ig.core.services.ProgrammingLanguageSupport;
import nz.ac.massey.cs.ig.core.services.Services;
import nz.ac.massey.cs.ig.core.services.build.BotFactory;
import nz.ac.massey.cs.ig.core.services.build.BuildProblemCollector;
import nz.ac.massey.cs.ig.core.services.build.BuildResult;
import nz.ac.massey.cs.ig.core.services.build.BuildResult.BuildStatus;
import nz.ac.massey.cs.ig.core.services.build.Builder;
import nz.ac.massey.cs.ig.core.services.build.BuilderException;
import nz.ac.massey.cs.ig.core.services.build.verifier.DynamicVerifier;
import nz.ac.massey.cs.ig.core.services.build.verifier.StaticASTVerifier;
import nz.ac.massey.cs.ig.core.services.build.verifier.StaticByteCodeVerifier;
import nz.ac.massey.cs.ig.core.services.build.verifier.StaticByteCodeVerifierFactory;
import nz.ac.massey.cs.ig.core.services.storage.StorageException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

/**
 * Integrated build service.
 * 
 * @author Johannes Tandler
 */

public class BuildService {

	private Services services;

	private Boolean setInstrumentation;
	
	private Boolean dynamicVerificationEnabled;

	public BuildService(Services services) {
		this.services = services;
	}

	/**
	 * See {@link #buildBot(String, BuildProblemCollector)} for more details.
	 * 
	 * @param data
	 * @param issues
	 * @return
	 * @throws BuilderException
	 * @throws StorageException
	 */
	public BuildResult buildBot(BotData data) {
		BotCache cache = services.getBotCache();
		if (cache != null) {
			if (cache.isBotCached(data.getId())) {
				BotFactory factory = cache.getCachedBot(data.getId());
				return new BuildResult(BuildStatus.OK, factory);
			}
		}
		BuildResult result = doBuildBot(data);
		if (cache != null && result.isSuccess()) {
			cache.cacheBot(result.getBotFactory());
		}
		return result;
	}

	private BuildResult doBuildBot(BotData data) {
		String lang = data.getLanguage();
		String userId = data.getOwner().getId();

		Logger logger = services.getLogService().getLogger(userId);

		ProgrammingLanguageSupport support = services
				.getProgrammingLanguageSupport(lang);

		Builder builder = support.getBuilderFactory().createBuilder(support,
				logger);
		if (builder == null) {
			// this will log to the users private log
			logger.warn("No builder available, the bot's language was not set correctly");
			BuildProblemCollector col = new BuildProblemCollector();
			col.report(new SimpleDiagnostics(Kind.ERROR,
					"No Compiler for language " + lang + " available", -1));
			return new BuildResult(BuildStatus.OTHERS, col);
		}

		// activate instrumentation if possible
		if (builder.isInstrumentationSupported() && setInstrumentation != null) {
			builder.setInstrumenationSupport(setInstrumentation);
		}

		// initialize dynamic verifier
		DynamicVerifier dynamicVerifier = createDynamicVerifier();
		if (dynamicVerifier != null && (dynamicVerificationEnabled == null || dynamicVerificationEnabled)) {
			builder.setDynamicVerifier(dynamicVerifier);
		}

		// initialize static byte code verifier
		StaticByteCodeVerifier byteCodeVerifier = createByteCodeVerifier(
				support, services.getGameSupport());
		if (byteCodeVerifier != null) {
			builder.setByteCodeVerifier(byteCodeVerifier);
		}

		// initialize static ast code verifier
		StaticASTVerifier astVerifier = createASTVerifier(support,
				services.getGameSupport());
		if (astVerifier != null) {
			builder.setAstVerifier(astVerifier);
		}

		// build
		BotFactory factory = null;
		try {
			factory = builder.build(data);
		} catch (BuilderException e) {
			logger.log(Level.ERROR, e.toString());
			return new BuildResult(builder.getCurrentStatus(),
					builder.getBuildProblemCollector());
		}

		// cache if possible
		if (factory.isCachingSupported()) {
			BotCache cache = services.getBotCache();
			if (cache != null) {
				cache.cacheBot(factory);
			}
		}

		return new BuildResult(BuildStatus.OK,
				builder.getBuildProblemCollector(), factory);
	}

	private StaticByteCodeVerifier createByteCodeVerifier(
			ProgrammingLanguageSupport sup, GameSupport gsup) {
		StaticByteCodeVerifierFactory fac = sup.getByteCodeVerifierFactory();
		if (fac == null) {
			return null;
		}

		return fac.build(gsup.getWhitelistedClasses());
	}

	private DynamicVerifier createDynamicVerifier() {
		DynamicVerifier dynamicVerifier = null;
		if (services.getDynamicVerifierFactory() != null) {
			Class<?> testClass = services.getGameSupport().getTestClass();
			if (testClass != null) {
				dynamicVerifier = services.getDynamicVerifierFactory().build(
						testClass);
			}
		}
		return dynamicVerifier;
	}

	private StaticASTVerifier createASTVerifier(ProgrammingLanguageSupport sup,
			GameSupport gsup) {
		StaticASTVerifier astVerifier = null;
		if (sup.getStaticASTVerifierFactory() != null) {
			astVerifier = sup.getStaticASTVerifierFactory().build(
					gsup.getWhitelistedClasses());
		}
		return astVerifier;
	}

	/**
	 * Builds one {@link Bot} specified by its botId with a given
	 * {@link Services}
	 * 
	 * @param services
	 *            given {@link Services} to build bot
	 * @param botId
	 *            Id of {@link Bot} which should be built
	 * @param issues
	 *            {@link BuildProblemCollector} which will contain any build
	 *            errors.
	 * @return newly created bot
	 * @throws BuilderException
	 *             is throw, if
	 *             {@link Builder#build(String, String, StaticASTVerifier, StaticByteCodeVerifier, DynamicVerifier, BuildProblemCollector)}
	 *             fails.
	 * @throws StorageException
	 *             is thrown, if {@link Storage} is not accessible
	 */
	public BuildResult buildBot(String botId, EntityManager manager)
			throws StorageException {

		BotData data = manager.find(BotData.class, botId);

		if (data == null) {
			throw new StorageException("Bot with id '" + botId + "' not found.");
		}

		return buildBot(data);
	}

	public void setSetInstrumentation(Boolean setInstrumentation) {
		this.setInstrumentation = setInstrumentation;
	}

	public Boolean getDynamicVerificationEnabled() {
		return dynamicVerificationEnabled;
	}

	public void setDynamicVerificationEnabled(Boolean dynamicVerificationEnabled) {
		this.dynamicVerificationEnabled = dynamicVerificationEnabled;
	}
}