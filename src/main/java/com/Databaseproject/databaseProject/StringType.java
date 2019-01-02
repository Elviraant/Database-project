//package com.databaseProject.Databaseproject;
import java.util.Scanner;
import java.io.Serializable;

public class StringType extends FieldType implements Serializable{



	public boolean correctValue(String a) {
		return true;
	}

	public String getData() {

		 Scanner cs = new Scanner(System.in);
		return cs.nextLine();
	}

}
