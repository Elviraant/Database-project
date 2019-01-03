//package com.databaseProject.Databaseproject;
import java.io.Serializable;
import java.util.Scanner;
import java.util.InputMismatchException;

/**
 *  Represents an Integer Type of Database extend Field Type
 */
public class IntegerType extends FieldType implements Serializable {

/**
 * Reads an Integer from keyboard
 * Catch InputMismatchException e and reads again
 * @return Integer Integer, that has been read
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


}