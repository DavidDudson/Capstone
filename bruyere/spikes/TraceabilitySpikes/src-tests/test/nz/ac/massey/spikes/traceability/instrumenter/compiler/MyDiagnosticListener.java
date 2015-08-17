package test.nz.ac.massey.spikes.traceability.instrumenter.compiler;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticListener;
import javax.tools.JavaFileObject;
import java.util.Locale;

/**
 * Created by Li on 20/04/15.
 */
public class MyDiagnosticListener implements DiagnosticListener<JavaFileObject> {

    private String diagnosticReport="";
    @Override
    public void report(Diagnostic<? extends JavaFileObject> diagnostic) {
        diagnosticReport ="Line Number->" + diagnostic.getLineNumber()+"\n"
                +"code->" + diagnostic.getCode()+"\n"
                +"Message->" + diagnostic.getMessage(Locale.ENGLISH)+"\n"
                +"Source->" + diagnostic.getSource();
    }

    public String getDiagnosticReport(){
        return this.diagnosticReport;
    }
}
