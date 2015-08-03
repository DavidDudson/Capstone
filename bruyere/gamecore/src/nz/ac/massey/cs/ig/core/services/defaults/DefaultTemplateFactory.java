package nz.ac.massey.cs.ig.core.services.defaults;

import nz.ac.massey.cs.ig.core.services.TemplateFactory;
import nz.ac.massey.cs.ig.core.utils.ResourceUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Default implementation of {@link TemplateFactory}
 * 
 * @author Jake
 *
 */
public class DefaultTemplateFactory implements TemplateFactory {

	Map<String, String> templates;

	public DefaultTemplateFactory() {
		templates = new HashMap<>();
	}

	@Override
	public String getTemplate(String language, String name) {
		String template = null;
		if (templates.containsKey(language)) {
			template = templates.get(language);
		} else {
			template = ResourceUtils.loadFromClassPath(this,
					"resources/templates/" + language + ".template");
			if (template != null) {
				templates.put(language, template);
			}
		}
		
		// sanitize name
		String sanitizedName = name.trim();
		if(sanitizedName.contains(" ")) {
			sanitizedName = sanitizedName.replace(" ", "_");
		}
		if(sanitizedName.length() == 0) {
			sanitizedName = "bot";
		}
		if(Character.isDigit(sanitizedName.charAt(0))) {
			sanitizedName = "bot_" + sanitizedName;
		}
		
		if (template != null)
			return String.format(template, sanitizedName, sanitizedName);
		else
			return null;
	}
}
