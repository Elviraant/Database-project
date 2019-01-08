package com.Databaseproject.databaseProject;
import java.util.Scanner;
import java.io.Serializable;
import java.util.Scanner;

/**
 * Represent a String Type of Database
 */

public class StringType extends FieldType implements Serializable { /*checkstyle checked*/

  /**
   * Read a String from keybord
   * and don't allow space records
   * @return String - inserted data
   */
  public String getData() {
    Scanner sc = new Scanner(System.in);
    String s = sc.nextLine();
    while ((s.startsWith("") && s.endsWith(" ")) || (s.length() == 0) || (s.startsWith(" "))) {
      System.out.print("A space record, or a space at the beginning is not acceptable. \n"
          + "Please try again: ");
      s = sc.nextLine();
    }
    return s;
  }

  /**
   * Return the type of the object
   * @return String
   */
  public String toString() {
    return "Type: Text";
  }
}

