
package nz.ac.massey.cs.ig.core.services.defaults.compiler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import javax.tools.SimpleJavaFileObject;

/**
 * In-memory file object implementation.
 * from http://www.javablogging.com/dynamic-in-memory-compilation/
 */

public class ByteCodeFileObject extends SimpleJavaFileObject {

    protected final ByteArrayOutputStream bos = new ByteArrayOutputStream();


    public ByteCodeFileObject(String name, Kind kind) {
        super(URI.create("string:///" + name.replace('.', '/') + kind.extension), kind);
    }

    public byte[] getBytes() {
        return bos.toByteArray();
    }

    @Override
    public OutputStream openOutputStream() throws IOException {
    	System.out.println("opening output stream to write file");
        return bos;
    }
}
