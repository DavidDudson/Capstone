
package nz.ac.massey.cs.ig.languages.java.compiler;

import nz.ac.massey.cs.ig.core.services.build.BuilderException;
import nz.ac.massey.cs.ig.core.services.build.SourceCodeUtils;

/**
 * Utilities related to Java source code. 
 * @author jens dietrich
 */
public class SourceUtils implements SourceCodeUtils {
	
    /**
     * Extract the full class name from byte code. 
     * TODO: improve using AST api or better regex
     * @param src
     * @param includePackageName
     * @return the full class name of the respective class
     * @throws BuilderException 
     */
    private static String doExtractClassName(String src,boolean includePackageName) throws BuilderException {
        String[] lines = src.split("\n");
        StringBuilder fullName = new StringBuilder();
        
        boolean isMultilineComment = false;
        boolean comment = false;
        
        for (String line:lines) {
            line = line.trim();
            comment = false;
            if (line.startsWith("//")) comment = true;
            else if (isMultilineComment && line.startsWith("*")) comment = true;
            else if (isMultilineComment && line.startsWith("*/")) {
                comment = true;
                isMultilineComment = false;
            }
            else if (line.startsWith("/*")) {
                isMultilineComment = true;
                comment = true;
            }
            
            // we should skip comments
            if(comment) {
            	continue;
            }
            
            if (line.startsWith("package") && includePackageName) {
                fullName.append(line.substring(8,line.indexOf(";")).trim());
            }
            
            else {
                if (line.startsWith("public ")) {
                    line = line.substring(6).trim();
                }
                if (line.startsWith("class")) {
                    line = line.substring(5).trim();
                    if (fullName.length()>0) {
                        fullName.append('.');
                    }
                    for (char c:line.toCharArray()) {
                        if (Character.isLetterOrDigit(c) || c=='_' || Character.isIdentifierIgnorable(c)) {
                            fullName.append(c);
                        }
                        else {
                            break;
                        }
                    }
                    return fullName.toString();

                }
                else if (line.startsWith("interface")) {
                    throw new BuilderException("This is an interface - a class is expected");
                }
                else if (line.startsWith("@interface")) {
                    throw new BuilderException("This is an interface - a class is expected");
                }
                else if (line.startsWith("enum")) {
                    throw new BuilderException("This is an enum - a class is expected");
                }
            }
        }
        
        throw new BuilderException("Cannot extract fully qualified class name from source:\n"+src);
    }

	@Override
	public String extractClassName(String botName, String src) throws BuilderException {
		return doExtractClassName(src,false);
	}

	@Override
	public String extractFullClassName(String botName, String src) throws BuilderException {
    	return doExtractClassName(src,true);
	}
	
	public String extractFullClassName(String src) throws BuilderException {
		return doExtractClassName(src,true);
	}
}
