package test.nz.ac.massey.cs.ig.languages.java.compiler;

import nz.ac.massey.cs.ig.core.services.build.BuilderException;
import nz.ac.massey.cs.ig.languages.java.compiler.SourceUtils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test source utils.
 *
 * @author jens dietrich
 */
public class SourceUtilTests {

    @Test
    public void testExtractFullClassName1() throws BuilderException {
        String src
                = "package foo;                                              \n"
                + "import nz.ac.massey.cs.ig.core.game.Bot;                  \n"
                + "import test.nz.ac.massey.cs.ig.core.services.*;           \n"
                + "public class MyBot implements Bot<MockGame,MockMove> {    \n"
                + "}                                                         \n";

        String qName = new SourceUtils().extractFullClassName(src);
        assertEquals("foo.MyBot",qName);
    }
    
    @Test
    public void testExtractFullClassName2() throws BuilderException {
        String src
                = "package foo.bar;                                          \n"
                + "import nz.ac.massey.cs.ig.core.game.Bot;                  \n"
                + "import test.nz.ac.massey.cs.ig.core.services.*;           \n"
                + "public class MyBot implements Bot<MockGame,MockMove> {    \n"
                + "}                                                         \n";

        String qName = new SourceUtils().extractFullClassName(src);
        assertEquals("foo.bar.MyBot",qName);
    }
    
    @Test
    public void testExtractFullClassName3() throws BuilderException {
        String src
                = "import nz.ac.massey.cs.ig.core.game.Bot;                  \n"
                + "import test.nz.ac.massey.cs.ig.core.services.*;           \n"
                + "public class MyBot implements Bot<MockGame,MockMove> {    \n"
                + "}                                                         \n";

        String qName = new SourceUtils().extractFullClassName(src);
        assertEquals("MyBot",qName);
    }
    
    @Test
    public void testExtractFullClassName4() throws BuilderException {
        String src
                = "   \tpackage foo.bar;                                     \n"
                + "import nz.ac.massey.cs.ig.core.game.Bot;                  \n"
                + "import test.nz.ac.massey.cs.ig.core.services.*;           \n"
                + " public  \t class MyBot implements Bot<MockGame,MockMove> {\n"
                + "}                                                         \n";

        String qName = new SourceUtils().extractFullClassName(src);
        assertEquals("foo.bar.MyBot",qName);
    }
    
    @Test
    public void testExtractFullClassName5() throws BuilderException {
        String src
                = " // comment                                               \n"
                + " /*                                                       \n"
                + "  * more comment                                          \n"
                + "  /*                                                      \n"
                + "package foo.bar;                                          \n"
                + "import nz.ac.massey.cs.ig.core.game.Bot;                  \n"
                + "import test.nz.ac.massey.cs.ig.core.services.*;           \n"
                + " /*                                                       \n"
                + "  * even more comment                                     \n"
                + "  /*                                                      \n"
                + "public class MyBot implements Bot<MockGame,MockMove> {    \n"
                + "}                                                         \n";

        String qName = new SourceUtils().extractFullClassName(src);
        assertEquals("foo.bar.MyBot",qName);
    }
    
    
    @Test(expected=BuilderException.class)
    public void testExtractFullClassNameFails1() throws BuilderException {
        String src
                = "package foo.bar;                                          \n"
                + "import nz.ac.massey.cs.ig.core.game.Bot;                  \n"
                + "import test.nz.ac.massey.cs.ig.core.services.*;           \n"
                + "public interface MyInterface {}                           \n";

        new SourceUtils().extractFullClassName(src);
    }
    
    @Test(expected=BuilderException.class)
    public void testExtractFullClassNameFails2() throws BuilderException {
        String src
                = "package foo.bar;                                          \n"
                + "import nz.ac.massey.cs.ig.core.game.Bot;                  \n"
                + "import test.nz.ac.massey.cs.ig.core.services.*;           \n"
                + "public @interface MyAnnotation  {}                        \n";

        new SourceUtils().extractFullClassName(src);
    }
    
    @Test(expected=BuilderException.class)
    public void testExtractFullClassNameFails3() throws BuilderException {
        String src
                = "package foo.bar;                                          \n"
                + "import nz.ac.massey.cs.ig.core.game.Bot;                  \n"
                + "import test.nz.ac.massey.cs.ig.core.services.*;           \n"
                + "public enum MyBot {ONE,TWO}                               \n";

        new SourceUtils().extractFullClassName(src);
    }
    
    
    @Test(expected=BuilderException.class)
    public void testExtractFullClassNameFails() throws BuilderException {
        String src
                = "package foo.bar;                                          \n"
                + "import nz.ac.massey.cs.ig.core.game.Bot;                  \n"
                + "import test.nz.ac.massey.cs.ig.core.services.*;           \n";

        new SourceUtils().extractFullClassName(src);
    }
    
}
