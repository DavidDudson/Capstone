package nz.ac.massey.cs.ig.core.services.build;

import nz.ac.massey.cs.ig.core.services.defaults.BuildService;

/**
 * Helper class to provide build results from the {@link BuildService} to other
 * classes.
 * 
 * @author Jake
 *
 */
public class BuildResult {

	/**
	 * Created bot factory
	 */
	private BotFactory botFactory;

	/**
	 * Used {@link BuildProblemCollector}
	 */
	private BuildProblemCollector collector;
	
	private BuildStatus status;

	/**
	 * Create a new BuildResult from a {@link BuildProblemCollector} only
	 * 
	 * @param collector
	 */
	public BuildResult(BuildStatus status, BuildProblemCollector collector) {
		this.status = status;
		this.collector = collector;
	}

	/**
	 * Creates a new BuildResult from a {@link BotFactory}
	 * 
	 * @param factory
	 */
	public BuildResult(BuildStatus status, BotFactory factory) {
		this.status = status;
		this.botFactory = factory;
	}

	/**
	 * Creates a new BuildResult from a {@link BotFactory} and given
	 * {@link BuildProblemCollector}
	 * 
	 * @param collector
	 * @param factory
	 */
	public BuildResult(BuildStatus status, BuildProblemCollector collector, BotFactory factory) {
		this.status = status;
		this.botFactory = factory;
		this.collector = collector;
	}

	/**
	 * A Build succeed if a bot factory could be created
	 * 
	 * @return
	 */
	public boolean isSuccess() {
		return status == BuildStatus.OK;
	}

	/**
	 * Returns the {@link BotFactory}
	 * @return
	 */
	public BotFactory getBotFactory() {
		return botFactory;
	}

	/**
	 * Returns Issues of build process
	 * @return
	 */
	public BuildProblemCollector getIssues() {
		return collector;
	}

	/**
	 * Returns true if isSuccess returns false
	 * @return
	 */
	public boolean isError() {
		return !isSuccess();
	}
	
	public BuildStatus getBuildStatus() {
		return status;
	}

	public enum BuildStatus {
		OK,
		AST_VERIFICATION_FAILED,
		COMPILATION_FAILED,
		BYTECODE_VERIFICATION_FAILED,
		TESTS_FAILED_FUNC,
		TESTS_FAILED_TIMEOUT,
		OTHERS				
	}
	
}
