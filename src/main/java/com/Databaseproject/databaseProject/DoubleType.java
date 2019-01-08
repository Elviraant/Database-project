//package com.Databaseproject.databaseProject;
import java.util.Scanner;
import java.io.Serializable;
import java.util.InputMismatchException;

/**
 * Represent a Double Type of Database
 */
public class DoubleType extends FieldType implements Serializable {

	/**
	 * Read a Double from keyboard
	 * , catch InputMismatchException e and read again
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
	 * Return the type of the object
	 * @return String
	 */
	public String toString() {
		return "Type: Double";
	}
}