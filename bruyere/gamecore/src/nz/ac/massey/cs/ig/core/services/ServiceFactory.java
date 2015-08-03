package nz.ac.massey.cs.ig.core.services;


/**
 * Service factory. Note that reflection will be used, and a constructor that
 * takes the ServletCotext as parameter is expected.
 * 
 * @author jens dietrich
 */
public interface ServiceFactory {

	/**
	 * Name of init parameter
	 */
	public static final String SERVICE_FACTORY_CLASS_KEY = "serviceFactory";

	/**
	 * Builds the actual service. Which serviceFactory is used can be defined by using
	 * web.xml and defining the {@link ServiceFactory#SERVICE_FACTORY_CLASS_KEY}
	 * property.
	 * 
	 * @param context
	 * @return
	 */
	public Services build(Configuration config);

}
