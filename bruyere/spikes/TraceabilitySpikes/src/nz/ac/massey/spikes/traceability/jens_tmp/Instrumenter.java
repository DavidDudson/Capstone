package nz.ac.massey.spikes.traceability.jens_tmp;

/**
 * Interface to instrument source code. 
 * We assume that source code is represented as string (submitted from client), we could change this to reader etc
 * @author jens dietrich
 */
public interface Instrumenter {

	/**
	 * Instrument source code - insert race statements.
	 * @param source - java source code to be instrumented
	 * @param botId - to identify bots, note that during execution all bots concurrently use trace
	 * @return the instrumented code
	 * @throws Exception
	 */
	String instrument(String source,String botId) throws Exception ;
}
