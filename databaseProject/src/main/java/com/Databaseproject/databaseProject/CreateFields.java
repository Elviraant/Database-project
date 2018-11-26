package com.Databaseproject.databaseProject;



import java.util.ArrayList;
import java.util.HashMap;
public class CreateFields  {

	//For Primary Key
	private static HashMap<String, Integer> primaryKey = new HashMap<String, Integer>();

	public static void setPrimaryKey(String s, Integer x){

		 primaryKey.put(s,x);
	}

	public static HashMap getPrimaryKey(){

		 return primaryKey;
	}

	//For other attributes
	public static ArrayList createStringField () {

		ArrayList <String> nameofList = new ArrayList <String> ();
		return nameofList;
	}

	public static ArrayList createIntegerField () {

		ArrayList <Integer> nameofList = new ArrayList <Integer> ();
		return nameofList;
	}

	public static ArrayList createDoubleField () {

		ArrayList <Double> nameofList = new ArrayList <Double> ();
		return nameofList;
	}

	public static ArrayList createBooleanField () {

		ArrayList <Boolean> nameofList = new ArrayList <Boolean> ();
		return nameofList;
	}





}