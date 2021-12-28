package HW10;

public class StringFinder extends Thread {
	private static int counter;
	private int start;
	private int end;
	private String inputText;
	private String wordToFind;
	
	public StringFinder(String inputText, String wordToFind, int start, int end) {
		this.inputText = inputText;
		this.wordToFind = wordToFind;
		this.start = start;
		this.end = end;
	}
	
	public void run() {
		String find = wordToFind.toLowerCase();
		String input = inputText.toLowerCase().substring(start, end);
		String[] wordInput = input.split(" ");
		String[] punctuation = {"!","?",",",":",";","."};
		for (String i : wordInput) {
			for (String p : punctuation) {
				if (i.endsWith(p)) {
					i = i.substring(0, i.indexOf(p));
					if (i.equals(find)) {
						counter++;
					}
				}			
			}
			if (i.equals(find)) {
				counter++;
			}
		}
	}

	public static void main(String[] args) {
		String inputText = "She didn’t understand how change worked." +
                "When she looked at today compared to yesterday," +
                " there was nothing that she could see that was different." +
                " Yet, when she looked at today compared to last year, " + 
                "she couldn’t see how anything was ever the same.";
		StringFinder one = new StringFinder(inputText, "she", 0, 88);
		StringFinder two = new StringFinder(inputText, "she", 0, 88);
		Thread t1 = new Thread(one);
		Thread t2 = new Thread(two);
		t1.start();
		t2.start();
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
		System.out.println(StringFinder.counter);
	}

}
