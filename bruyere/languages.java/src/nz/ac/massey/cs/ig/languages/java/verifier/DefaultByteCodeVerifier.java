package nz.ac.massey.cs.ig.languages.java.verifier;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.EnumSet;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedSet;
import java.util.Stack;
import java.util.TreeSet;
import java.util.Vector;

import javax.tools.Diagnostic.Kind;

import nz.ac.massey.cs.ig.core.game.Bot;
import nz.ac.massey.cs.ig.core.game.Game;
import nz.ac.massey.cs.ig.core.services.build.BuildProblemCollector;
import nz.ac.massey.cs.ig.core.services.defaults.SimpleDiagnostics;

/**
 * Default byte code verifier based on ASM. Allows access to: - the abstract
 * Move and Bot standardClassesInWhitelist - the project-specific Move and Bot
 * and other standardClassesInWhitelist (must be passed to constructor) - most
 * standardClassesInWhitelist in java.langetInternalName and java.util, excl
 * Threads, System, Runtime etc TODO: restrict access to Object.wait and
 * Object.notify
 * 
 * @author jens dietrich
 */
public class DefaultByteCodeVerifier extends AbstractByteCodeVerifier {

	private final Set<String> PERMITTED_CLASSES = new HashSet<String>();
	private final Set<String> PERMITTED_PACKAGES = new HashSet<String>();

	private static final String getInternalName(Class<?> clazz) {
		return clazz.getName().replace(".", "/");
	}
	private static final String getInternalName(String name) {
		return name.replace(".", "/");
	}

	public DefaultByteCodeVerifier(Collection<Class<?>> additionalClassesInWhitelist) {
		super();

		Class<?>[] standardClassesInWhitelist = new Class[] { Game.class,
				Bot.class, Object.class, String.class, Class.class,
				Number.class, Integer.class, Character.class, Float.class,
				Double.class, Long.class, Short.class, Boolean.class,
				Math.class, Iterable.class, Comparable.class, Comparator.class, Exception.class,
				Error.class, RuntimeException.class,
				ArrayIndexOutOfBoundsException.class,
				NullPointerException.class,
				UnsupportedOperationException.class, ClassCastException.class,
				IllegalArgumentException.class, IllegalStateException.class,
				SecurityException.class, StackOverflowError.class,
				java.lang.AssertionError.class,
				StringIndexOutOfBoundsException.class,
				ReflectiveOperationException.class,
				NumberFormatException.class, NegativeArraySizeException.class,
				CloneNotSupportedException.class, ArithmeticException.class,
				ArrayStoreException.class, Collection.class, Iterator.class,
				List.class, ArrayList.class, Vector.class, LinkedList.class,
				AbstractList.class, Stack.class, Set.class, TreeSet.class,
				HashSet.class, SortedSet.class, EnumSet.class,
				LinkedHashSet.class, BitSet.class, Arrays.class,
				Collections.class, Map.class, HashMap.class, Date.class,
				Calendar.class, GregorianCalendar.class, Random.class };
		
		// TODO: note that we do not check for "subpackages" - the current packages in the whitelist are all final
		String[]  lambdaSupportPackageNames = new String[] {
				"java.lang.invoke",
				"java.util.function"
		};

		for (Class<?> cl : standardClassesInWhitelist) {
			PERMITTED_CLASSES.add(getInternalName(cl));
		}
		
		if (additionalClassesInWhitelist != null) {
			for (Class<?> cl : additionalClassesInWhitelist) {
				PERMITTED_CLASSES.add(getInternalName(cl));
			}
		}
		
		for (String pck:lambdaSupportPackageNames) {
			PERMITTED_PACKAGES.add(getInternalName(pck));
		}
	}

	// whitebox API checks
	@Override
	public void checkAccessToType(String type, String thisClass, BuildProblemCollector dLog,int lineNo) throws SecurityException {
		
		if (PERMITTED_CLASSES.contains(type)) return;
		// skip inner classes
		if (type.startsWith(thisClass + "$") || thisClass.startsWith(type + "$")) return;
		
		// check if both classes are inner ones
		if(type.contains("$") && thisClass.contains("$")) {
			if(type.substring(0, type.indexOf("$")).equals(thisClass.substring(0, thisClass.indexOf("$")))) {
				return;
			}
		}
		
		for (String pck:PERMITTED_PACKAGES) {
			// TODO: note that we do not check for "subpackages" - the current packages in the whitelist are all final
			if (type.startsWith(pck)) {
				return;
			}
		}

		String t = type.replace('/', '.');
		dLog.report(new SimpleDiagnostics(Kind.ERROR, "Using " + t + " is not permitted", lineNo));
		throw new SecurityException("Access to " + t + " not allowed");
	}
	
	@Override
	public void checkClassNestingLevel(String name, int nestingLevel) throws SecurityException {
		if(nestingLevel>1) {
			throw new SecurityException("Nesting Level " + nestingLevel + " of \"" + name + "\" is higher than allowed");
		}
	}
}
