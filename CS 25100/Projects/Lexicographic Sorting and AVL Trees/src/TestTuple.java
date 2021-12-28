import java.util.Random;

/**
 * CS 251: Data Structures and Algorithms Project 3: Part 1
 * <p>
 *
 * @author Shivam Bairoloya
 * @username sbairoli
 */
public class TestTuple {
	public static void main(String[] args) {
		for (int i = 0; i < 20; i++) {
			boolean check = true;
			int test = 0;
			try {
				// test 1
				test = 1;
				check = test1();
				// test 2
				if (check) {
					test = 2;
					check = test2();
				}
				// test 3
				if (check) {
					test = 3;
					check = test3();
				}
				// test 4
				if (check) {
					test = 4;
					check = test4();
				}
			} catch (Exception E) {
				E.printStackTrace();
				check = false;
			} finally {
				if (check) {
					System.out.println("Lexicographic Tests Passed");
					System.out.println("Score for Part 1: 20");
				} else {
					System.out.printf("Lexicographic Tests Failed %d\n", test);
					System.out.println("Score for Part 1: 0");
				}
			}
		}

	}

	private static boolean test1() {
		return runTest(1000);
	}

	private static boolean test2() {
		return runTest(10000);
	}

	private static boolean test3() {
		Integer[] arr1 = new Integer[0];
		Integer[] arr2 = new Integer[0];
		Tuple tuple1 = new Tuple(arr1);
		Tuple tuple2 = new Tuple(arr2);

		boolean check = true;
		for (int i = 0; i < 10000; i++) {
			if (!testInt(tuple1, tuple2)) {
				check = false;
				break;
			}
		}
		return check;
	}

	private static boolean test4() {
		Random random = new Random();
		boolean bool = true;
		for (int i = 0; i < 1000 && bool; i++) {
			int str1Len = random.nextInt(1000);
			String str1 = random.ints(48, 122 + 1).limit(str1Len)
					.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
			Character[] arr1 = str1.chars().mapToObj(c -> (char) c).toArray(Character[]::new);
			Character[] arr2 = str1.chars().mapToObj(c -> (char) c).toArray(Character[]::new);
			Tuple tuple1 = new Tuple(arr1);
			Tuple tuple2 = new Tuple(arr2);
			int tupleResult = tuple1.compareTo(tuple2);
			if (tupleResult != 0) {
				bool = false;
				System.out.printf("Test failed\ntype: String\nstring1: %s\nstring2: %s", str1, str1);
				System.out.println();
				return false;
			}
		}
		return bool;

	}

	private static boolean runTest(int instances) {

		Character[] arr1 = new Character[0];
		Character[] arr2 = new Character[0];
		Tuple tuple1 = new Tuple(arr1);
		Tuple tuple2 = new Tuple(arr2);
		int maxLength = 1000;
		boolean check = true;
		for (int i = 0; i < instances; i++) {
			if (!testString(tuple1, tuple2, maxLength)) {
				check = false;
				break;
			}
		}
		return check;

	}

	@SuppressWarnings("unchecked")
	private static boolean testString(Tuple tuple1, Tuple tuple2, int maxLength) {
		int str1Len = new Random().nextInt(maxLength);
		int str2Len = new Random().nextInt(maxLength);
		Random random = new Random();
		String str1 = random.ints(48, 122 + 1).limit(str1Len)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
		String str2 = random.ints(48, 122 + 1).limit(str2Len)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
		Character[] arr1 = str1.chars().mapToObj(c -> (char) c).toArray(Character[]::new);
		Character[] arr2 = str2.chars().mapToObj(c -> (char) c).toArray(Character[]::new);
		tuple1.setItems(arr1);
		tuple2.setItems(arr2);

		int tupleResult = tuple1.compareTo(tuple2);
		int strResult = str1.compareTo(str2);

		boolean bool = false;
		if (tupleResult < 0 && strResult < 0) {
			bool = true;
		} else if (tupleResult > 0 && strResult > 0) {
			bool = true;
		} else if (tupleResult == 0 && strResult == 0) {
			bool = true;
		}

		if (bool) {
			return true;
		} else {
			System.out.printf("Test failed\ntype: String\nstring1: %s\nstring2: %s\ntuple res: %d\n", str1, str2,
					tupleResult);
			System.out.println();
			return false;
		}
	}

	private static boolean testInt(Tuple tuple1, Tuple tuple2) {
		int int1 = new Random().nextInt(10000000);
		int int2 = new Random().nextInt(10000000);

		String str1 = Integer.toString(int1);
		String str2 = Integer.toString(int2);
		Integer[] arr1 = new Integer[str1.length()];
		Integer[] arr2 = new Integer[str2.length()];
		for (int i = 0; i < str1.length(); i++) {
			arr1[i] = str1.charAt(i) - '0';
		}
		for (int i = 0; i < str2.length(); i++) {
			arr2[i] = str2.charAt(i) - '0';
		}
		tuple1.setItems(arr1);
		tuple2.setItems(arr2);

		int tupleResult = tuple1.compareTo(tuple2);
		int strResult = str1.compareTo(str2);

		boolean bool = false;
		if (tupleResult < 0 && strResult < 0) {
			bool = true;
		} else if (tupleResult > 0 && strResult > 0) {
			bool = true;
		} else if (tupleResult == 0 && strResult == 0) {
			bool = true;
		}

		if (bool) {
			return true;
		} else {
			System.out.printf("Test failed\ntype: int\nint: %d\nint2: %d", int1, int2);
			System.out.println();
			return false;
		}
	}
}
