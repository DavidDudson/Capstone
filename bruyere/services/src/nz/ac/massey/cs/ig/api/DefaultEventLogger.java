package nz.ac.massey.cs.ig.api;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import nz.ac.massey.cs.ig.core.services.event.EventLogger;
import nz.ac.massey.cs.ig.core.services.event.logging.BuildEvent;
import nz.ac.massey.cs.ig.core.services.event.logging.GameEvent;
import nz.ac.massey.cs.ig.core.services.event.logging.GsonReader;
import nz.ac.massey.cs.ig.core.services.event.logging.GsonWriter;

import com.google.gson.Gson;

public class DefaultEventLogger implements Runnable, EventLogger {

	private final BlockingQueue<BuildEvent> queue1;
	private final BlockingQueue<GameEvent> queue2;

	private final String baseUrl;

	private Thread workerThread;

	private final Client client;

	public DefaultEventLogger(String baseUrl) {
		this.baseUrl = baseUrl;
		this.queue1 = new ArrayBlockingQueue<BuildEvent>(1000);
		this.queue2 = new ArrayBlockingQueue<GameEvent>(1000);
		workerThread = new Thread(this);

		client = ClientBuilder.newClient();
		client.register(GsonWriter.class);
		client.register(GsonReader.class);
	}

	public void logBuildEvent(BuildEvent event) {
		try {
			queue1.offer(event, 100, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (!workerThread.isAlive()) {
			workerThread = new Thread(this);
			workerThread.start();
		}
	}

	public void logGameEvent(GameEvent event) {
		try {
			queue2.offer(event, 100, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (!workerThread.isAlive()) {
			workerThread = new Thread(this);
			workerThread.start();
		}
	}

	@Override
	public void run() {
		try {
			List<BuildEvent> buildEvents = new ArrayList<BuildEvent>();
			BuildEvent ev = null;
			while ((ev = queue1.poll()) != null) {
				buildEvents.add(ev);
			}
			WebTarget target = client.target(baseUrl).path("buildevents");

			target.request(MediaType.APPLICATION_JSON_TYPE)
					.post(Entity.json(new Gson().toJson(buildEvents)));
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			List<GameEvent> gameEvents = new ArrayList<GameEvent>();
			GameEvent ev = null;
			while ((ev = queue2.poll()) != null) {
				gameEvents.add(ev);
			}
			WebTarget target = client.target(baseUrl).path("gameevents");

			target.request(MediaType.APPLICATION_JSON_TYPE)
					.post(Entity.json(new Gson().toJson(gameEvents)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String ping() {
		WebTarget target = client.target(baseUrl);
		return target.request().get(String.class);
	}

}
