package nz.ac.massey.cs.ig.core.services.defaults;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.Manifest;

import nz.ac.massey.cs.ig.core.game.model.BotData;
import nz.ac.massey.cs.ig.core.services.GameSupport;
import nz.ac.massey.cs.ig.core.services.TemplateFactory;

/**
 * Created by Johannes Tandler on 15.05.15.
 */
public abstract class DefaultGameSupport implements GameSupport {

	public static final long DEFAULT_MOVE_TIMEOUT = 5000;

	protected TemplateFactory templateFactory;

	protected List<BotData> builtInBots;

	protected String name;

	private int[] version;

	public DefaultGameSupport(String name) {
		super();
		this.name = name;

		try {
			readVersionFromManifest();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void readVersionFromManifest() throws IOException {
		Enumeration<URL> resources = this.getClass().getClassLoader()
				.getResources("META-INF/MANIFEST.MF");
		while (resources.hasMoreElements()) {
			URL url = resources.nextElement();
			Manifest manifest = new Manifest(url.openStream());

			String versionString = manifest.getMainAttributes().getValue(
					"Game-Version");
			if (versionString == null) {
				continue;
			}

			String[] versionParas = versionString.split("\\.");
			version = new int[versionParas.length];
			for (int i = 0; i < versionParas.length; i++) {
				if (versionParas[i].contains("-")) {
					versionParas[i] = versionParas[i].substring(0,
							versionParas[i].indexOf("-"));
				}
				version[i] = Integer.parseInt(versionParas[i]);
			}
			break;

		}
	}

	@Override
	public boolean isLanguageSupported(String languageId) {
		return templateFactory.getTemplate(languageId, null) != null;
	}

	@Override
	public long getMoveTimeout() {
		return DEFAULT_MOVE_TIMEOUT;
	}

	@Override
	public TemplateFactory getTemplateFactory() {
		if (templateFactory == null) {
			templateFactory = createTemplateFactory();
		}

		return this.templateFactory;
	}

	protected TemplateFactory createTemplateFactory() {
		return new DefaultTemplateFactory();
	}

	@Override
	public Collection<BotData> getBuiltInBots() {
		if (builtInBots == null) {
			builtInBots = loadBuiltInBots();
		}
		return builtInBots;
	}

	protected abstract List<BotData> loadBuiltInBots();

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int[] getVersion() {
		if (version != null)
			return version.clone();
		else
			return null;
	}
}
