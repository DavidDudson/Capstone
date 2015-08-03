package nz.ac.massey.cs.ig.core.services.build;


public interface SourceCodeUtils {

	String extractClassName(String botName, String src) throws BuilderException;

	String extractFullClassName(String botName, String src) throws BuilderException;

}
