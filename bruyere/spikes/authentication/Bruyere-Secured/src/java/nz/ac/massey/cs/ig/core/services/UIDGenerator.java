
package nz.ac.massey.cs.ig.core.services;

/**
 * Utility to create unique ids.
 * ids should be network and file system friendly (i.e., should be valid file names)
 * and must be valid Java identifiers
 * @author jens dietrich
 */
public interface UIDGenerator {
    String nextUID();
}
