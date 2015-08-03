
package nz.ac.massey.cs.ig.core.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

/**
 * Several utilities.
 * @author jens dietrich
 */
public class Utils {
    
    // use ISO8601 to get standardised, yet readable timestamps
    private static final DateTimeFormatter TIMESTAMP_FORMATTER = ISODateTimeFormat.dateTime();
    
    public static final String getTimeStamp () {
    	return TIMESTAMP_FORMATTER.print(new DateTime());
    }
}
