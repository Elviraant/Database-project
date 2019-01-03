//package com.databaseProject.Databaseproject;
import java.util.Scanner;
import java.io.Serializable;

/**
 *  Represents a String Type of Database extend Field Type
 */
public class StringType extends FieldType implements Serializable{

	/**
	 * Reads from keyboard a String
	 * @return String has been read String
	 */
	public String getData() {

		Scanner cs = new Scanner(System.in);
		return cs.nextLine();
	}

}
