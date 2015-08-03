import java.lang.management.*;

public class TestClass implements Runnable {
	
	public void run() {
		System.out.print(fibonacci(45));
	}
	
	private int fibonacci(int n) {
		if(n <= 0) throw new UnsupportedOperationException();
		if(n <= 2) return 1;
		return fibonacci(n-1) + fibonacci(n-2);
	}
}
