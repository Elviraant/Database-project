package com.Databaseproject.databaseProject;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

public class DataPresenter {
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
					System.out.print(String.format("%s %-8s %-8s", "|", a.get(i).toString(), "|"));
				}
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

	/*Prints the columns*/
	public static void printColumns(ArrayList <String> attributes) {

		if ( attributes.size() == 0 ) {
			System.out.println("There's nothing to be presented");
		} else {
			for (String attribute: attributes ) {
				System.out.print(String.format("%s %-8s %-8s", "|", attribute, "|"));
			}
			System.out.println();
			System.out.println("-----------------------------------------------------------------------------");


			for (int i=0; i < Example.getNames().get(attributes.get(0)).size() ; i++ ) {
				for (String attribute: attributes) {
					System.out.print(String.format("%s %-8s %-8s", "|", Example.getNames().get(attribute).get(i).toString(), "|"));
				}
				System.out.println();
			}


			}

		}

	public static void presentationFormat(Object o) {

	}






	/*Prints the columns given by the user*/
	public static void printSpecificColumns() {
		ArrayList <String>  attributes = new ArrayList<String>();

		System.out.println("Type the first attribute that you want print. If you're done, type Done.");
		String attribute = cs.next();
		while (!attribute.equals("Done"))	                     {
			if (Example.getNames().containsKey(attribute)) {
				attributes.add(attribute);
				System.out.println("Type another attribute that you want print. If you're done, type Done.");
				attribute = cs.next();
			} else {
				System.out.println("There's no such attribute. Try again. If you're done, type Done.");
				attribute = cs.next();
			}
		}
		printColumns(attributes);
	}


}