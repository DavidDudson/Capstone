package nz.ac.massey.cs.ig.core.game.instrumentation;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

import nz.ac.massey.cs.ig.core.game.MemoryExceededException;
import nz.ac.massey.cs.ig.core.game.instrumentation.Observer;
import nz.ac.massey.cs.ig.core.game.instrumentation.OperationCodes;

public class JavaObserver implements Observer {

	public static final int MEMORY_LIMIT = 100 * 1000 * 1000;
	public static final int INSTANTIATION_MAXIMUM = 100000;

	private Object observable;

	private int instantiationCounter;

	private long allocatedBytes = 0;

	private long minSize = 0;

	private ThreadMXBean bean;

	private int calls;
	
	private int borderReachedCounter;
	
	private int updateBorder;

	@SuppressWarnings("restriction")
	public JavaObserver(Object observable) {
		assert observable != null;
		this.observable = observable;
		bean = ManagementFactory.getThreadMXBean();

		if (bean instanceof com.sun.management.ThreadMXBean) {
			minSize = ((com.sun.management.ThreadMXBean) bean)
					.getThreadAllocatedBytes(Thread.currentThread().getId());
		}
		calls = 0;
		updateBorder = 1;
	}

	public void invoke() {
		observable.toString();
		throw new UnsupportedOperationException();
	}

	public void invoke(int lineNumber, int opcode, String owner, String name, String desc) {
		calls++;
		if ((name != null && name.contains("<init>")) || opcode == OperationCodes.INSTANTIATION) {
			instantiationCounter++;
		}
		if (calls >= updateBorder) {
			updateAllocatedBytes();

			if (getAllocatedBytes() > MEMORY_LIMIT) {
				throw new MemoryExceededException(
						"You exceeded memory limit of 100 MB");
			}

			if (instantiationCounter > INSTANTIATION_MAXIMUM) {
				throw new MemoryExceededException("More than " + INSTANTIATION_MAXIMUM
						+ " instantiations!");
			}

			calls = 0;
			borderReachedCounter++;
		}
	}

	@SuppressWarnings("restriction")
	private void updateAllocatedBytes() {
		if (bean instanceof com.sun.management.ThreadMXBean) {
			allocatedBytes = (((com.sun.management.ThreadMXBean) bean)
					.getThreadAllocatedBytes(Thread.currentThread().getId()));
			if(allocatedBytes < minSize) {
				minSize = allocatedBytes;
			}
		}
	}

	public int getInstantiationCounter() {
		return instantiationCounter;
	}
	
	public void setUpdateBorder(int border) {
		this.updateBorder = border;
	}

	public long getAllocatedBytes() {
		return allocatedBytes - minSize;
	}
	
	public int getBorderReachedCounter() {
		return borderReachedCounter;
	}

	@Override
	public void setObservable(Object observable) {
		this.observable = observable;
	}

}
