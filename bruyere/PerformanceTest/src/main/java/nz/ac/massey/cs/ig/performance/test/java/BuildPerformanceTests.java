package nz.ac.massey.cs.ig.performance.test.java;

import java.util.ArrayList;
import java.util.List;

import nz.ac.massey.cs.ig.core.game.model.BotData;
import nz.ac.massey.cs.ig.core.services.Services;
import nz.ac.massey.cs.ig.core.services.build.BuildResult;
import nz.ac.massey.cs.ig.core.services.defaults.BuildService;
import nz.ac.massey.cs.ig.performance.test.Utils;

public class BuildPerformanceTests {

	private static Services service;

	public static void main(String[] args) throws Exception {

		service = Utils.initializeServices(false);

		List<BotData> botMetas = new ArrayList<BotData>();
		botMetas.add(Utils.loadJavaBot(service, "java/AnxiousBot.java"));
		botMetas.add(Utils.loadJavaBot(service, "java/GreedyBot.java"));
		botMetas.add(Utils.loadJavaBot(service, "java/PrimeNumberBot.java"));
		botMetas.add(Utils.loadJavaBot(service, "java/NoFactorsLeftBot.java"));
		botMetas.add(Utils.loadJavaBot(service, "java/BestAdvantageBot.java"));

		BuildService buildServiceWithoutInstrumentation = new BuildService(
				service);
		buildServiceWithoutInstrumentation.setSetInstrumentation(false);

		BuildService buildServiceWithInstrumentation = new BuildService(service);
		buildServiceWithInstrumentation.setSetInstrumentation(true);

		System.out.format("%-24s%8s%8s%n", "Initial Build:", "Off", "On");
		for (BotData data : botMetas) {
			// without instrumentation
			long start = System.currentTimeMillis();
			BuildResult resOff = buildServiceWithoutInstrumentation
					.buildBot(data);
			long timeWithoutInstr = System.currentTimeMillis() - start;
			assert resOff.isSuccess();

			// with instrumentation
			start = System.currentTimeMillis();
			BuildResult resOn = buildServiceWithInstrumentation.buildBot(data);
			long timeWithInstr = System.currentTimeMillis() - start;
			assert resOn.isSuccess();

			// print results
			System.out.format("%-24s%8d%8d%n", data.getId(), timeWithoutInstr,
					timeWithInstr);
		}

		System.out.println();
		System.out.println();

		int numbers = 100;
		System.out.println("RUNNING BUILDS " + numbers + " TIMES");
		System.out.println();

		System.out.format("%-24s%8s%8s%n", "Bot name :", "Off", "On");
		for (BotData data : botMetas) {
			// without instrumentation
			long summedTimeWithoutInstr = 0;
			for (int i = 0; i < numbers; i++) {
				long start = System.currentTimeMillis();
				BuildResult resOff = buildServiceWithoutInstrumentation
						.buildBot(data);
				long timeWithoutInstr = System.currentTimeMillis() - start;
				assert resOff.isSuccess();
				summedTimeWithoutInstr += timeWithoutInstr;
			}

			// with instrumentation
			long summedTimeWithInstr = 0;
			for (int i = 0; i < numbers; i++) {
				long start = System.currentTimeMillis();
				BuildResult resOn = buildServiceWithInstrumentation
						.buildBot(data);
				long timeWithInstr = System.currentTimeMillis() - start;
				assert resOn.isSuccess();
				summedTimeWithInstr += timeWithInstr;
			}

			long timeAvgWithoutInstr = summedTimeWithoutInstr / numbers;
			long timeAvgWithInstr = summedTimeWithInstr / numbers;
			
			// print results
			System.out.format("%-24s%8d%8d%n", data.getId(), timeAvgWithoutInstr,
					timeAvgWithInstr);
		}

	}

}
