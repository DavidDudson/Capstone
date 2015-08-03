package test.nz.ac.massey.spikes.traceability.instrumenter.compiler;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * a invoker to call method with argrument
 * @author Li Sui
 *
 */
public class MethodInvoker {

	private Method thisMethod;
	private Object instance;
	/**
	 * invoke a mehtod from a lodaded class with its name, type, and argrument
	 * @param loadedClass
	 * @param methodName
	 * @param paramType
	 * @param methodArgs
	 * @param botId
	 * @throws Exception
	 */
	public void invoke(Class<?> loadedClass, String methodName, Class<?> paramType, Object methodArgs,String botId) throws Exception{
		
		thisMethod = loadedClass.getMethod(methodName, paramType);
		//constructor arguments -- botID
		Constructor<?> constructor = loadedClass.getConstructor(String.class);
		instance = constructor.newInstance(botId);
		//call nextMove method
		thisMethod.invoke(instance, methodArgs);	
	}
	/**
	 * simply call method agian with new argrument
	 * @param methodArgs
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public void callAgain(Object methodArgs) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		thisMethod.invoke(instance, methodArgs);
	}

}
