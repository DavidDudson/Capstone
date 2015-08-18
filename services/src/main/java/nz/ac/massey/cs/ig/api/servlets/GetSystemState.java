package nz.ac.massey.cs.ig.api.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.text.NumberFormat;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicLong;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nz.ac.massey.cs.ig.core.services.Services;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Service to return all user ids.
 *
 * @author jens dietrich
 */

@WebServlet(name = "GetSystemState", urlPatterns = { "/systemstate" })
public class GetSystemState extends BasicBruyereServlet {

	private static final long serialVersionUID = 8965175168858659772L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		JSONArray data = new JSONArray();
		JSONObject state = new JSONObject();

		Services services = getServices();
		EntityManager manager = services.createEntityManager();

		Runtime runtime = Runtime.getRuntime();

		NumberFormat format = NumberFormat.getInstance();
		
		Subject subject = SecurityUtils.getSubject();
		
		if (services.getExecutorServiceForGames() instanceof ThreadPoolExecutor) {
			ThreadPoolExecutor exec = (ThreadPoolExecutor) services
					.getExecutorServiceForGames();
			state.put("playedGames", exec.getTaskCount());
		}
		
		
		long botAmount = manager.createQuery("SELECT COUNT(b.id) FROM BotData b", Long.class).getSingleResult();
		state.put("bots", botAmount);
		
		long userAmount = manager.createQuery("SELECT COUNT(b.id) FROM UserData b", Long.class).getSingleResult();
		state.put("users", userAmount);
		
		
		state.put("eventQueueState", services.getEventLogger().ping());
		

		if(subject.isAuthenticated() && subject.hasRole("teacher")) {
			long allocatedMemory = runtime.totalMemory();
			state.put("ram", format.format(allocatedMemory / 1024) + " KB");
			state.put("disk", format.format(getSize(getStorageDirectory()) / 1024)
					+ " KB");

			if (services.getExecutorServiceForGames() instanceof ThreadPoolExecutor) {
				ThreadPoolExecutor exec = (ThreadPoolExecutor) services
						.getExecutorServiceForGames();
				state.put("activeGames", exec.getActiveCount());
				state.put("gamesQueueLength", exec.getQueue().size());
			}

		}
		data.put(state);
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.write(data.toString());
		out.close();
	}

	private String getStorageDirectory() {
		return getServletContext().getAttribute(Services.ROOT_PATH).toString();
	}

	static long getSize(String startPath) throws IOException {
		final AtomicLong size = new AtomicLong(0);
		Path path = Paths.get(startPath);

		Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(Path file,
					java.nio.file.attribute.BasicFileAttributes attrs)
					throws IOException {
				size.addAndGet(attrs.size());
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFileFailed(Path file, IOException exc)
					throws IOException {
				return FileVisitResult.CONTINUE;
			}
		});

		return size.get();
	}
}
