//package com.databaseProject.Databaseproject;
import java.util.Scanner;
import java.io.Serializable;

public class DoubleType extends FieldType implements Serializable {

	public boolean correctValue(String a) {
		return true;
	}

	public Double getData() {
		Scanner cs = new Scanner(System.in);
		double data = 0;
		while (true) {
			try {
				data = cs.nextDouble();
				break;
			} catch (Exception e) {
				 System.out.println("You should insert a Double");
				 cs.next();
			}
		}
		return data;

	}
}