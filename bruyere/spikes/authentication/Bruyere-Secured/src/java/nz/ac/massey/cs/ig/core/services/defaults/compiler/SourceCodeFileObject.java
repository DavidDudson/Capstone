package nz.ac.massey.cs.ig.core.services.defaults.compiler;

import java.net.URI;
import javax.tools.SimpleJavaFileObject;

/**
 * Simple in-memory file object implementation. 
 * from http://www.javablogging.com/dynamic-in-memory-compilation/
 * @author jens dietrich
 */
public class SourceCodeFileObject extends SimpleJavaFileObject {

    private String src;

    // check whether we can use the botid here, or whether this has to be the actual class name
    public SourceCodeFileObject(String botId, String src) { 
        super(URI.create("string:///" + botId.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
        this.src = src;
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
        return src;
    }
}
