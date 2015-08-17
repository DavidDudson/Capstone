
package nz.ac.massey.cs.ig.core.services;


/**
 * A factory for (bot source code) templates to be used as starting points when writing new bots
 * @author jens dietrich
 */
public interface TemplateFactory {

    /**
     * Build a class from source code, and instantiate it
     * @param name a template name, used if there are multiple templates, this could also be used to 
     * encode a programming language
     * @return a template - a default template should always be returned
     */
    public String getTemplate(String language, String name) ;
    
}
