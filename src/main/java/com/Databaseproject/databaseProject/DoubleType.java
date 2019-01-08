package com.Databaseproject.databaseProject;
import java.util.Scanner;
import java.io.Serializable;
import java.util.InputMismatchException;

/**
 *  Represents a Double Type of Database extend Field Type
 */
public class DoubleType extends FieldType implements Serializable {

	/**
	 * Reads a Double from keyboard
	 * Catch InputMismatchException e and reads again
	 * @return Double Double, that has been read
	 */
	public Double getData() {
		Scanner cs = new Scanner(System.in);
		double data = 0;
		while (true) {
			try {
				data = cs.nextDouble();
				break;
			} catch (InputMismatchException e) {
				 System.out.println("You should insert a Double");
				 cs.next();
			}
		}
		return data;

	}

	/**
	 * Returns the type of the object
	 * @return String
	 */
	public String toString() {
		return "Type: Double";
	}
}