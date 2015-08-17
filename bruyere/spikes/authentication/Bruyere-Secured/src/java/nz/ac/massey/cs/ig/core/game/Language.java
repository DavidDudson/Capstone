package nz.ac.massey.cs.ig.core.game;

import java.security.InvalidParameterException;

import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

public class Language{
	public static final String PYTHON = "python27";
	public static final String JAVA = "java";
	
	public static String getLanguageInfo(String language) throws InvalidParameterException{
		switch(language){
		case JAVA:
			return System.getProperty("java.vm.version");
			
		case PYTHON:
			PythonInterpreter python = new PythonInterpreter();		
    		python.exec("import sys");	
    		PyObject version = python.eval("sys.version");
    		return version.__str__().getString();
    	
		default:
			throw new InvalidParameterException("Input language is not supported");
		}
	}
}
