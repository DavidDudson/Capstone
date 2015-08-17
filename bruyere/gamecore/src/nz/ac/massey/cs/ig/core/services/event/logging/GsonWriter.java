package nz.ac.massey.cs.ig.core.services.event.logging;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import com.google.gson.Gson;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class GsonWriter<T> implements MessageBodyWriter<T> {
 
    @Override
    public void writeTo(T t, Class<?> type, Type genericType,
            Annotation[] annotations, MediaType mediaType,
            MultivaluedMap<String, Object> httpHeaders,
            OutputStream entityStream)
            throws IOException, WebApplicationException {
        Gson g = new Gson();
        httpHeaders.get("Content-Type").add("charset=UTF-8");
        String json = g.toJson(t);
        entityStream.write(json.getBytes("UTF-8"));
    }
 
    @Override
    public long getSize(T t, Class<?> type, Type genericType,
            Annotation[] annotations, MediaType mediaType) {
        return -1;
    }
 
    @Override
    public boolean isWriteable(Class<?> type, Type genericType,
            Annotation[] annotations, MediaType mediaType) {
        return true;
    }
}

