package test.nz.ac.massey.cs.ig.languages.java.instrumentation;

import static org.junit.Assert.fail;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import nz.ac.massey.cs.ig.core.game.MemoryExceededException;
import nz.ac.massey.cs.ig.core.game.instrumentation.JavaObserver;
import nz.ac.massey.cs.ig.core.game.instrumentation.OperationCodes;

import org.junit.Test;

@SuppressWarnings("restriction")
public class JavaObserverTest {

	@Test
	public void testInstantiationsAreAllowed() {
		JavaObserver observer = new JavaObserver(this);

		for (int i = 0; i < JavaObserver.INSTANTIATION_MAXIMUM; i++) {
			observer.invoke(2, OperationCodes.INSTANTIATION, "Test", "Blub", "");
		}
	}

	@Test
	public void testInstantiationsHaveALimit() {
		JavaObserver observer = new JavaObserver(this);

		try {
			for (int i = 0; i < JavaObserver.INSTANTIATION_MAXIMUM*2; i++) {
				observer.invoke(2, OperationCodes.INSTANTIATION, "Test", "Blub",
						"");
			}
		} catch (MemoryExceededException e) {
			return;
		}
		fail("There should be an MemoryExeceedException when instantiating twice as many objects as allowed");
	}

	@SuppressWarnings("deprecation")
	@Test(timeout = 30000)
	public void testMemoryUsageHasALimit() {
		if (!(ManagementFactory.getThreadMXBean() instanceof com.sun.management.ThreadMXBean)) {
			System.err.println("No Memory observation support by current jvm!");
			return;
		}

		final AtomicInteger counter = new AtomicInteger();
		final AtomicBoolean exceptionThrown = new AtomicBoolean(false);
		Thread.UncaughtExceptionHandler h = new Thread.UncaughtExceptionHandler() {
		    public void uncaughtException(Thread th, Throwable ex) {
		        if(ex instanceof MemoryExceededException) {
		        	exceptionThrown.getAndSet(true);
		        }
		    }
		};
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				JavaObserver observer = new JavaObserver(this);
				List<Object> lists = new ArrayList<Object>();
				for(int i=0;i>-1;i++) {
					lists.add(i);
					observer.invoke(2, OperationCodes.INVOKE, "list", "add", "(L)V");
					counter.incrementAndGet();
					if(Thread.interrupted()) {
						break;
					}
				}
			}
		});
		t.setUncaughtExceptionHandler(h);
		t.start();
		
		while(!exceptionThrown.get()) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if(counter.get() > 100000000) {
				t.stop();
				fail("There shouldn't be such an amout of instances without memory exception");
			}
		}
	}

}
