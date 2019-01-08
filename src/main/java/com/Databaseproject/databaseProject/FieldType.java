//package com.Databaseproject.databaseProject;
import java.io.Serializable;

/**
 * Represent the Field Type of Database
 */
abstract class FieldType implements Serializable{

	enum Type {
    	STRING,
    	INTEGER,
    	DOUBLE,
    	ENUMERATION_TYPE,
  	}

 	/**
 	 * Read and return data
   	 * @return Object
 	 */
	abstract Object getData();

}
