package nz.ac.massey.spikes.traceability.sourcecode.instrumenter.analyser;

import com.sun.source.util.TreePath;
import com.sun.source.util.Trees;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import nz.ac.massey.spikes.traceability.sourcecode.instrumenter.JavaClassInfo;

import java.util.Set;

/**
 * @author Li Sui
 */
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes("*")
@SuppressWarnings("restriction")
public class CodeAnalyserProcessor extends AbstractProcessor {

    private final CodeAnalyserTreeVisitor visitor = new CodeAnalyserTreeVisitor();
  
    private Trees trees;

    
	@Override
    public void init(ProcessingEnvironment pe) {
        super.init(pe);
        trees = Trees.instance(pe);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {
        for (Element e : roundEnvironment.getRootElements()) {
            // Normally the element should represent a class
            TreePath tp = trees.getPath(e);
            visitor.scan(tp, trees);
        }
        return true;  
    }
    public JavaClassInfo getClassInfo(){
		return visitor.getClassInfo();
    	
    }
    
}
