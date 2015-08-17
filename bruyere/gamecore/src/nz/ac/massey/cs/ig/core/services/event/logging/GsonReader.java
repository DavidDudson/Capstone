package nz.ac.massey.cs.ig.core.services.event.logging;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Type;

import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

@Provider
@Consumes(MediaType.APPLICATION_JSON)
public class GsonReader<T> implements MessageBodyReader<T> {
 
    @Override
    public boolean isReadable(Class<?> type, Type genericType,
            Annotation[] antns, MediaType mt) {
        return true;
    }
 
    @SuppressWarnings("unchecked")
	@Override
    public T readFrom(Class<T> type, Type genericType,
            Annotation[] antns, MediaType mt,
            MultivaluedMap<String, String> mm, InputStream in)
            throws IOException, WebApplicationException {
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(_convertStreamToString(in));
    	Gson g = new Gson();
    	if(!type.isArray()) {
    		return g.fromJson(element, type);
    	}
    	
    	if(element.isJsonArray()) {
    		return g.fromJson(element, type);
    	} else {
    		Class<?> clazz = type.getComponentType();
    		
    		Object[] array = (Object[])Array.newInstance(clazz, 1);
    		array[0] = g.fromJson(element, clazz);
    		
    		return (T)array;
    	}
    }
 
    private String _convertStreamToString(InputStream inputStream)
            throws IOException {
        if (inputStream != null) {
            Writer writer = new StringWriter();
 
            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(
                        new InputStreamReader(inputStream, "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } finally {
                inputStream.close();
            }
            return writer.toString();
        } else {
            return "";
        }
    }
}