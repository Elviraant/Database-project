package com.Databaseproject.databaseProject;
import java.io.Serializable;

/**
 * Represents the Field Type of Database
 * This is an abstract class
 */
abstract class FieldType implements Serializable{

	enum Type {
    	STRING,
    	INTEGER,
    	DOUBLE,
    	ENUMERATION_TYPE,
  	}

 	/**
 	 * Abstract method, the child class
   	 * must implement these method
   	 * @return Object
 	 */
	abstract Object getData();

}
