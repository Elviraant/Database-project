//package com.databaseProject.Databaseproject;
import java.io.Serializable;
import java.util.Scanner;

public class IntegerType extends FieldType implements Serializable {


  public boolean correctValue(String a) {
    return true;
  }

  public Integer getData() {
    Scanner cs = new Scanner(System.in);
    int data = 0;
    while (true) {
      try {
        data = cs.nextInt();
        break;
      } catch (Exception e) {
        System.out.println("You should insert an Integer");
        cs.next();
      }
    }
    return data;
  }


}