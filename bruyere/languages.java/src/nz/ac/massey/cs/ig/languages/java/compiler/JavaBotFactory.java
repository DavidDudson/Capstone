package nz.ac.massey.cs.ig.languages.java.compiler;

import java.lang.reflect.InvocationTargetException;

import nz.ac.massey.cs.ig.core.game.Bot;
import nz.ac.massey.cs.ig.core.game.instrumentation.InstrumentedBot;
import nz.ac.massey.cs.ig.core.game.instrumentation.JavaObserver;
import nz.ac.massey.cs.ig.core.services.build.BotFactory;

public class JavaBotFactory implements BotFactory {

	private String id;

	private Class<?> botClass;

	public JavaBotFactory(String id, Class<?> bot) {
		this.id = id;
		this.botClass = bot;
	}

	@Override
	public String getBotId() {
		return id;
	}

	@Override
	public Bot<?, ?> createBot() throws InstantiationException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, SecurityException, NoSuchMethodException {
		Bot<?, ?> bot = (Bot<?, ?>) botClass.getConstructor(String.class).newInstance(id);
		if(bot instanceof InstrumentedBot) {
			((InstrumentedBot) bot).__initialize(new JavaObserver(bot));
		}
		return bot;
	}
	
	@Override
	public boolean isCachingSupported() {
		return true;
	}

}
