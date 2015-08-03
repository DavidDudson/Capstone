package nz.ac.massey.cs.ig.core.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class ResourceUtils {

	public static String loadFromClassPath(Object caller, String resource) {
		ClassLoader classLoader = caller.getClass().getClassLoader();
		URL url = classLoader.getResource(resource);
		
		if(url == null) {
			return null;
		}
		
		StringBuffer buffer = new StringBuffer();
		String line = null;
		try {
			
			InputStream input = url.openStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					input));

			while ((line = reader.readLine()) != null) {
				buffer.append(line + "\n");
			}

			reader.close();
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return buffer.toString();
	}
}
