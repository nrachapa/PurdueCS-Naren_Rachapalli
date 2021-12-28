package HW10;

public class MyThread implements Runnable {

	public void run() {
		System.out.println("Running!");
	}

	public static void main(String[] args) {
		Thread toRun = new Thread(new Thread());
		toRun.start();
		try {
			toRun.join();
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
	}
}


