package HW10;

public class MyThreadTwo extends Thread {

	public void run() {
		System.out.println("Running!");
	}

	public static void main(String[] args) {
		MyThreadTwo toRun = new MyThreadTwo();
		toRun.start();
		try {
			toRun.join();
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
	}
}
