import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

public class PresentData {
	static Scanner cs = new Scanner(System.in);

	public static int startingRow() {
		System.out.println("Starting row: ");
		return cs.nextInt();
	}

	public static int endingRow() {
		System.out.println("Ending row: ");
		return cs.nextInt();
	}

	/*Prints the titles of the attributes*/
	public static void printHeader() {
		for (String key : Example.getNames().keySet()) {
			System.out.print(String.format("%s %-8s %-8s", "|", key, "|"));
		}
		System.out.println();
		System.out.println("-----------------------------------------------------------------------------");
	}

	/*Prints rows within a given range*/
	public static void printRows(int start, int end) {
			printHeader();
			for (int i = start; i < end; i++) {
				for (ArrayList a : Example.getNames().values()) {
					System.out.print(String.format("%s %-8s %-8s", "|", a.get(i).toString(), "|"));}
					System.out.println();
				}
			}
	/*Prints rows within a range given by the user*/
	public static void printSpecificRows() {
			System.out.println();
			int start = startingRow() - 1;
			int end = endingRow();
			printRows(start, end);
		}
	/*Prints all rows*/
	public static void printAll() {
		System.out.println();
		printRows(0, Example.getNames().size());
	}

}