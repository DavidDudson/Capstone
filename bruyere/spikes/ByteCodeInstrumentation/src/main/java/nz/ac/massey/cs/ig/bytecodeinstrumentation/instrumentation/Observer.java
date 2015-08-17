package nz.ac.massey.cs.ig.bytecodeinstrumentation.instrumentation;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

public class Observer {

	private Object observable;

	private int instantiationCounter;

	private long allocatedBytes = 0;

	private long minSize = 0;

	private ThreadMXBean bean;

	private int calls;

	public Observer(Object observable) {
		assert observable != null;
		this.observable = observable;
		bean = ManagementFactory.getThreadMXBean();

		if (bean instanceof com.sun.management.ThreadMXBean) {
			minSize = ((com.sun.management.ThreadMXBean) bean)
					.getThreadAllocatedBytes(Thread.currentThread().getId());
		}
		calls = 0;
	}

	public void invoke() {
		observable.toString();
	}

	public void invoke(int opcode, String owner, String name, String desc) {
		calls++;
		if (name.contains("<init>")) {
			instantiationCounter++;
		}
		if (calls == 5000) {
			updateAllocatedBytes();

			if (getAllocatedBytes() > 10 * 1000 * 1000) {
				System.out.println("TOO BIG!");
			}
			
			calls = 0;
		}
	}
	
	private void updateAllocatedBytes() {
		if (bean instanceof com.sun.management.ThreadMXBean) {
			allocatedBytes = (((com.sun.management.ThreadMXBean) bean)
					.getThreadAllocatedBytes(Thread.currentThread().getId()));
		}
	}

	public int getInstantiationCounter() {
		return instantiationCounter;
	}

	public long getAllocatedBytes() {
		return allocatedBytes - minSize;
	}

}
