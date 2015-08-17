package nz.ac.massey.spikes.traceability.jens_tmp;

import java.util.ArrayList;
import java.util.List;

public class Test {
	
	private StringBuilder source =new StringBuilder(
		"package nz.ac.massey.spikes.testSourceCode;\n"
		+ "import java.util.List;\n"
		+	"public class TestBot1{\n"
		+	"public TestBot1(String botId) {\n"
		+	"}\n"
		+	"public Integer nextMove(List<Integer> game) {\n"
		+		"int size = game.size();\n"
		+		"return game.get(size-1);\n"
		+	"}\n"
		+	"}"
			);
	 private final String classPath = "nz.ac.massey.spikes.testSourceCode.TestBot1";
	@org.junit.Test
	public void test() throws Exception {
		String botId = "bot42";
	
		Instrumenter instrumenter = new Instrumenter(){
			@Override
			public String instrument(String source, String botId) throws Exception {
				//TODO: implement instrument
				 return null;
			}};
			
		Excerciser<PGBot> excerciser = new Excerciser<PGBot>() {
			@Override
			public void excercise(PGBot bot, String id) {
				// simulate some moves
				bot.move(getListOfInt(1,2,3,4,5,6));
				bot.move(getListOfInt(1,2,4,6));
				bot.move(getListOfInt(5));
			}
		};
		Tracer tracer = new Tracer() {

			@Override
			public void setMaxDepth(int i) {
				// TODO
				throw new UnsupportedOperationException("Tracer not yet implemented");
			}

			@Override
			public void trace(String botId, int line, int iteration,String identifier, Object value) {
				// TODO
				throw new UnsupportedOperationException("Tracer not yet implemented");
			}

			@Override
			public List<List<Snapshot>> getTraces(String botId) {
				// TODO
				throw new UnsupportedOperationException("Tracer not yet implemented");
			}

			@Override
			public void setMaxNumberOfSnapshots(int i) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void setMaxNumberOfVariables(int i) {
				// TODO Auto-generated method stub
				
			}
			
		};
		
		
		String instrumentedSource = instrumenter.instrument(source.toString(),botId);
		
		// now source is instrumented
		
		// TODO: build (compile and load) bot from instrumented code
		
//		DefaultBuilder<PGBot> builder = new DefaultBuilder<>();
//		BuildProblemCollector dLog = new BuildProblemCollector();
//		PGBot bot = builder.build(botId,instrumentedSource,null,null,null,dLog);
		
		// TODO: use a MockExcerciser here that calls nextMove a few times with different parameters

		
//		excerciser.excercise(bot,botId);
		
		List<List<Snapshot>> traces = tracer.getTraces(botId);
		
	}
	
	
	private List<Integer> getListOfInt(int... numbers) {
		List<Integer> list = new ArrayList<>(numbers.length);
		for (int i:numbers) list.add(i);
		return list;
	}

}