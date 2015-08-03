
package nz.ac.massey.cs.ig.core.services;

/**
 * Utility to create unique ids.
 * ids should be network and file system friendly (i.e., should be valid file names)
 * and must be valid Java identifiers.
 * The user name can be used as hint to create "human-friendly" ids.
 * @author jens dietrich
 */
public interface UIDGenerator {
    String nextUID(String user);   
}
