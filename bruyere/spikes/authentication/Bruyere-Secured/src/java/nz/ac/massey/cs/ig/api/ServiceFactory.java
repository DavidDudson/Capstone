
package nz.ac.massey.cs.ig.api;

import java.lang.reflect.Constructor;

import javax.servlet.ServletContext;

import nz.ac.massey.cs.ig.core.services.Services;
import nz.ac.massey.cs.ig.examples.primegame.PGServices;


/**
 * Service factory - todo: initialise this from project metadata.
 * @author jens dietrich
 */
public class ServiceFactory {
	
	private static Services services = null;
	public static final String SERVICE_REGISTRY_CLASS_KEY = "service_registry_class";
    public synchronized static Services getServices(ServletContext context) {
    	if (services==null) {
    		String serviceClassName = null;
			try {
				serviceClassName = context.getInitParameter(SERVICE_REGISTRY_CLASS_KEY);
				// Class serviceClass = context.getClass().getClassLoader().loadClass(serviceClassName);
				Class serviceClass = Class.forName(serviceClassName);
				services = (Services)serviceClass.newInstance();
			} catch (ClassNotFoundException x) {
				context.log("Cannot load service class " + serviceClassName + " this class must be registered as context parameter with the key " + SERVICE_REGISTRY_CLASS_KEY,x);
			} catch (Exception x) {
				context.log("Cannot instantiate service class " + serviceClassName,x);
			}
    		
    	}
    	return services;
    }
}
