package nz.ac.massey.cs.ig.performance.test;

import java.util.Arrays;

import nz.ac.massey.cs.ig.core.game.model.BotData;
import nz.ac.massey.cs.ig.core.game.model.UserData;
import nz.ac.massey.cs.ig.core.services.GameSupport;
import nz.ac.massey.cs.ig.core.services.LogService;
import nz.ac.massey.cs.ig.core.services.ProgrammingLanguageSupport;
import nz.ac.massey.cs.ig.core.services.Services;
import nz.ac.massey.cs.ig.core.services.defaults.DefaultBotCache;
import nz.ac.massey.cs.ig.core.utils.ResourceUtils;
import nz.ac.massey.cs.ig.games.primegame.PGBot;
import nz.ac.massey.cs.ig.languages.java.JavaSupport;
import nz.ac.massey.cs.ig.languages.python.PythonSupport;

import org.mockito.Mockito;

public class Utils {

	public static BotData loadBot(Object service, String fileName, String lang) {
		String anxiousBoutSrc = ResourceUtils.loadFromClassPath(service,
				fileName);
		BotData data = new BotData(fileName);
		data.setOwner(new UserData(fileName));
		data.setLanguage(lang);
		data.setSrc(anxiousBoutSrc);
		return data;
	}
	
	public static BotData loadJavaBot(Object service, String fileName) {
		return loadBot(service, fileName, "JAVA");
	}
	
	public static BotData loadPythonBot(Object service, String fileName) {
		return loadBot(service, fileName, "PYTHON");
	}

	public static Services initializeServices(boolean caching) {
		Services service = Mockito.mock(Services.class);
		if (caching) {
			Mockito.when(service.getBotCache()).thenReturn(
					new DefaultBotCache());
		}
		ProgrammingLanguageSupport langSupport = new JavaSupport();
		Mockito.when(service.getProgrammingLanguageSupport(langSupport.getIdentifier()))
				.thenReturn(langSupport);
		PythonSupport pySup = new PythonSupport();
		Mockito.when(service.getProgrammingLanguageSupport(pySup.getIdentifier()))
		.thenReturn(pySup);
		
		
		GameSupport gsupport = Mockito.mock(GameSupport.class);
		Mockito.when(gsupport.getWhitelistedClasses()).thenReturn(
				Arrays.asList(PGBot.class));
		Mockito.when(service.getGameSupport()).thenReturn(gsupport);

		LogService logService = Mockito.mock(LogService.class);
		Mockito.when(service.getLogService()).thenReturn(logService);

		Mockito.when(logService.getLogger(Mockito.anyString())).thenReturn(
				org.apache.logging.log4j.LogManager.getRootLogger());
		return service;
	}
	
}
