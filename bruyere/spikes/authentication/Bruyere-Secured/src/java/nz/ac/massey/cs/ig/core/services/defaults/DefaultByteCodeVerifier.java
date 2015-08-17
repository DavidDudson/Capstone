
package nz.ac.massey.cs.ig.core.services.defaults;

import nz.ac.massey.cs.ig.core.game.Game;

import java.util.*;

import javax.tools.Diagnostic.Kind;

import nz.ac.massey.cs.ig.core.game.Bot;
import nz.ac.massey.cs.ig.core.services.BuildProblemCollector;

/**
 * Default byte code verifier based on ASM.
 * Allows access to:
 - the abstract Move and Bot standardClassesInWhitelist
 - the project-specific Move and Bot and other standardClassesInWhitelist (must be passed to constructor)
 - most standardClassesInWhitelist in java.langetInternalName and java.util, excl Threas, System, Runtime etc
 * TODO: restrict access to Object.wait and Object.notify
 * @author jens dietrich
 */
public class DefaultByteCodeVerifier extends AbstractByteCodeVerifier {
    
    private final Set<String> PERMITTED_CLASSES = new HashSet<String>();
    
    private static final String getInternalName(Class<?> clazz) {
        return clazz.getName().replace(".", "/");
    }
    
    public DefaultByteCodeVerifier(Class<?>... additionalClassesInWhitelist) {
        super();
        
        Class<?>[] standardClassesInWhitelist = new Class[]{
            Game.class,Bot.class,
            Object.class,String.class,Class.class,
            Number.class,Integer.class,Character.class,Float.class,Double.class,Long.class,Short.class,Boolean.class,
            Math.class,Iterable.class,Comparable.class,
            Exception.class,Error.class,RuntimeException.class,ArrayIndexOutOfBoundsException.class,
            NullPointerException.class,UnsupportedOperationException.class,ClassCastException.class,
            IllegalArgumentException.class,IllegalStateException.class,SecurityException.class,
            StackOverflowError.class,java.lang.AssertionError.class,
            StringIndexOutOfBoundsException.class,ReflectiveOperationException.class,
            NumberFormatException.class,NegativeArraySizeException.class,CloneNotSupportedException.class,ArithmeticException.class,
            ArrayStoreException.class,
            Collection.class,Iterator.class,
            List.class,ArrayList.class,Vector.class,LinkedList.class,AbstractList.class,Stack.class,
            Set.class,TreeSet.class,SortedSet.class,EnumSet.class,LinkedHashSet.class,BitSet.class,
            Arrays.class,Collections.class,
            Date.class,Calendar.class,GregorianCalendar.class,
            Random.class
        };
        
        for (Class<?> cl:standardClassesInWhitelist) {
            PERMITTED_CLASSES.add(getInternalName(cl));
        }
        for (Class<?> cl:additionalClassesInWhitelist){
            PERMITTED_CLASSES.add(getInternalName(cl));
        }
    }

    // whitebox API checks 
    @Override
    public void checkAccessToType (String type,BuildProblemCollector dLog,int lineNo) throws SecurityException {
        if (PERMITTED_CLASSES.contains(type)) return;
        
        String t = type.replace('/','.');
        dLog.report(new SimpleDiagnostics(Kind.ERROR,"Using " + t + " is not permitted",lineNo));
        throw new SecurityException("Access to " + t + " not allowed");
    }
}
