package nz.ac.massey.cs.ig.api;

import java.io.PrintWriter;

import javax.tools.Diagnostic;

import org.json.JSONException;
import org.json.JSONWriter;

import nz.ac.massey.cs.ig.core.services.build.BuildProblemCollector;

/**
 * Utility to encode issues collected during builds a json collection.
 * @author jens dietrich
 */
public class BuildProblemJSONEncoder {
	public static void encode(BuildProblemCollector issues, PrintWriter out) throws JSONException {

		JSONWriter json = new JSONWriter(out);
		json.object().key("collection").object().key("version").value("1.0").key("items").array();

		for (Diagnostic<?> issue : issues.getIssues()) {
			json.object();
			json.key("linenumber").value(issue.getLineNumber());
			json.key("kind").value(issue.getKind());
			json.key("message").value(issue.getMessage(null));
			json.endObject();
		}
		json.endArray().endObject().endObject();
	}
}
