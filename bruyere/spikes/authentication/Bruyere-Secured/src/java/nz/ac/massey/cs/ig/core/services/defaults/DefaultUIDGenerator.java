package nz.ac.massey.cs.ig.core.services.defaults;

import java.rmi.server.UID;
import nz.ac.massey.cs.ig.core.services.UIDGenerator;

/**
 * Default implementation of the UID generator.
 *
 * @author jens dietrich
 */
public class DefaultUIDGenerator implements UIDGenerator {

    @Override
    public String nextUID() {
        String s = new UID().toString();
        StringBuilder b = new StringBuilder("id");
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isLetterOrDigit(c) || c=='_') {
                b.append(c);
            }
            else {
                b.append('_');
            }
        }
        return b.toString();
    }

}
