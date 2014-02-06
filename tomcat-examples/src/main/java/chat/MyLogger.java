package chat;

public class MyLogger {

	public static void print(String... strings) {
		for (String string : strings) {
			System.out.print(string);
			System.out.print(" ");
		}
		System.out.println();
	}

}
