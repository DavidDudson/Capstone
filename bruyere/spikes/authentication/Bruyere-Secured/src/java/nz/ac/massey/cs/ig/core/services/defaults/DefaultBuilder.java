
package nz.ac.massey.cs.ig.core.services.defaults;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.security.SecureClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;

import nz.ac.massey.cs.ig.core.game.Bot;
import nz.ac.massey.cs.ig.core.services.BuildProblemCollector;
import nz.ac.massey.cs.ig.core.services.Builder;
import nz.ac.massey.cs.ig.core.services.BuilderException;
import nz.ac.massey.cs.ig.core.services.DynamicVerifier;
import nz.ac.massey.cs.ig.core.services.StaticASTVerifier;
import nz.ac.massey.cs.ig.core.services.StaticByteCodeVerifier;
import nz.ac.massey.cs.ig.core.services.defaults.compiler.CompilationClassPathUtil;
import nz.ac.massey.cs.ig.core.services.defaults.compiler.SourceCodeFileObject;
import nz.ac.massey.cs.ig.core.services.defaults.compiler.ClassFileManager;
import nz.ac.massey.cs.ig.core.services.defaults.compiler.SourceUtils;

/**
 * Simple compiler that wraps the compiler in javax.tools.
 * Requires the JDK (not just the JRE).
 * @author jens dietrich
 */
public class DefaultBuilder<B extends Bot<?, ?>> implements Builder<B> {
	
	
	private final static List<String> COMPILER_OPTIONS = Arrays.asList("-classpath",CompilationClassPathUtil.getCompilationClassPath());

    @Override
    public B build(String botId, String src, StaticASTVerifier astverifier, StaticByteCodeVerifier sverifier, DynamicVerifier<B> dverifier, BuildProblemCollector dLog) throws BuilderException {
        
        String fullName = SourceUtils.extractFullClassName(src);
                
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        final ClassFileManager fileManager = new ClassFileManager(compiler.getStandardFileManager(null, null, null));

        List<JavaFileObject> jfiles = new ArrayList<JavaFileObject>();
        jfiles.add(new SourceCodeFileObject(fullName, src));

        compiler.getTask(null, fileManager,dLog, COMPILER_OPTIONS,null, jfiles).call();
        
        try {
            sverifier.verify(fileManager.getByteCode(),dLog);
            
            SecureClassLoader classLoader =  new SecureClassLoader(this.getClass().getClassLoader()) {
                  @Override
                  protected Class<?> findClass(String name) throws ClassNotFoundException {
                      byte[] b = fileManager.getByteCode();
                      return super.defineClass(name,b, 0, b.length);
                  }
              };
            	
            Class<?> clazz = classLoader.loadClass(fullName);
            
            // get constructor with id
            Constructor<?> constructor = clazz.getConstructor(String.class);
            @SuppressWarnings("unchecked")
			B b = (B)constructor.newInstance(botId);
            
            dverifier.verify(b,dLog);
            return b;
        } 
        // maintain src code level 6 (no multi catch)
        // VerifierExceptions are throws as is
        catch (RuntimeException x ) {
            throw new BuilderException("Exception instantiating bot class for bot " + botId,x);
        }
        catch (ClassNotFoundException x ) {
            throw new BuilderException("Exception loading bot class",x);
        }
        catch (InstantiationException x ) {
            throw new BuilderException("Exception instantiating bot class for bot " + botId,x);
        }
        catch (IllegalAccessException x ) {
            throw new BuilderException("Exception instantiating bot class for bot " + botId,x);
        } 
        catch (NoSuchMethodException x) {
            throw new BuilderException("Exception instantiating bot class for bot " + botId + " class does not provide a public constructor with a single string parameter to pass botId",x);
        } 
        catch (InvocationTargetException x) {
            throw new BuilderException("Exception instantiating bot class for bot " + botId,x);
        } 
    }
}
