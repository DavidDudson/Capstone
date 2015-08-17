
package nz.ac.massey.cs.ig.api;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

/**
 * Several utilities.
 * @author jens dietrich
 */
class Utils {
    public static final void handleException (HttpServlet servlet,HttpServletResponse response,String message,Throwable exception) {
        servlet.getServletContext().log(message, exception);
    }
    
    // use ISO8601 to get standardised, yet readable timestamps
    private static final DateTimeFormatter TIMESTAMP_FORMATTER = ISODateTimeFormat.dateTime();
    
    public static final String getTimeStamp () {
    	return TIMESTAMP_FORMATTER.print(new DateTime());
    }
}
