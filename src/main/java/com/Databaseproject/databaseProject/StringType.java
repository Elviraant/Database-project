//package com.Databaseproject.databaseProject;
import java.util.Scanner;
import java.io.Serializable;

/**
 *  Represents a String Type of Database extend Field Type
 */
public class StringType extends FieldType implements Serializable{

	/**
	 * Reads from keyboard a String
	 * Checks a space record for a StringType and doesn't allow it
	 * @return String has been read String
	 */
	public String getData() {

		Scanner sc = new Scanner(System.in);
		String s = sc.nextLine();
		while((s.startsWith("") && s.endsWith(" ")) || (s.length() == 0) || (s.startsWith(" "))) {
			System.out.print("A space record, or a space at the beginning is not acceptable. \n"
							+ "Please try again: ");
			s = sc.nextLine();
		}
		return s;
	}

	/**
	 * Returns the type of the object
	 * @return String
	 */
	public String toString() {
		return "Type: Text";
	}

}

