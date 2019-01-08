package com.Databaseproject.databaseProject;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.Serializable;

/**
 *  Represents an Enumerated Type of Database
 */
public class EnumeratedType extends FieldType implements Serializable{

	/**
	 * EnumeratedType's allowed Strings list
	 */
	private ArrayList<String> allowedStrings;

	/**
	 * Creates an EnumeratedType with an ArrayList of Strings, that will keep a set
	 * of possible values.
	 */
	public EnumeratedType() {
		allowedStrings = new ArrayList<String>();
	}

	/**
	 * Gets the EnumeratedType's allowed Strings list
	 * @return ArrayList
	 */
	public ArrayList getAllowedStrings() {
		return allowedStrings;
	}

	/**
	 * Checks, if the user's insertion is one of the allowed Strings
	 * If it isn't, user tries again
	 * @return String user's final choice
	 */
	public String getData() {
		Scanner cs = new Scanner(System.in);
		String data = null;
		boolean flag = true;
		while(flag == true) {

			data = cs.nextLine();

			if (correctValue(data)) {
				flag = false;
			} else {
				System.out.print("You have to insert one of these: " + getAllowedStrings());
			}

		}
		return data;
	}

	/**
	 * User defines the set on which EnumeratedType type will be defined
	 * Returns nothing
	 * @param nameOfField Column name
	 */
	public void defineEnumeration(String nameOfField) {
		Scanner cs = new Scanner(System.in);
		System.out.println("Please type the values that you want for " + nameOfField
							+ " . \nEnter EXIT to stop");
		String type = cs.nextLine();
		while (!type.equals("EXIT")) {
			allowedStrings.add(type);
			type = cs.nextLine();
		}
	}

	/**
	 * Checks, if there is an insertion in the set, on which EnumeratedType type is defined
	 * @param filler under control insertion
	 * @return boolean true, if there is, and false otherwise
	 */
	public boolean correctValue(String filler) {
		boolean isCorrect = false;
		for (String allowedString: allowedStrings) {
			if (filler.equals(allowedString)) {
				isCorrect = true;
			}
		}
		return isCorrect;
	}

	/**
	 * Returns the type of the object and allowed Strings ArrayList
	 * @return String
	 */
	public String toString() {
		return "Type: Enumerated Type " + allowedStrings;
	}

}
