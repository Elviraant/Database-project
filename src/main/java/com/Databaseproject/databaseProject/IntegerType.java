package com.Databaseproject.databaseProject;
import java.io.Serializable;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *  Represent an Integer Type of Database.
 */
public class IntegerType extends FieldType implements Serializable {

  /**
 * Read an Integer from keyboard
 * , catch InputMismatchException e and read again.
 * @return Integer Integer, that has been read.
 */
  public Integer getData() {
    Scanner cs = new Scanner(System.in);
    int data = 0;
    while (true) {
      try {
        data = cs.nextInt();
        break;
      } catch (InputMismatchException e) {
        System.out.println("You should insert an Integer");
        cs.next();
      }
    }
    return data;
  }

  /**
   * Return the type of the object.
   * @return String.
   */

  public String toString() {
    return "Type: Integer";
  }

}