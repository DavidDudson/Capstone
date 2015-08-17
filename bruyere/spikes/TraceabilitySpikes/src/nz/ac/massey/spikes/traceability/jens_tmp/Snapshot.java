package nz.ac.massey.spikes.traceability.jens_tmp;

import java.util.Map;

public interface Snapshot {
	/**
	 * Get the line number for this trace.
	 * @return
	 */
	int getLineNo();
	/**
	 * Get a map associating identifiers (incl "this") with values at the time the snapshot was taken. 
	 * Note that the values have been stringified to make them immutable.
	 * @return
	 */
	Map<String,String> getValues();
}
