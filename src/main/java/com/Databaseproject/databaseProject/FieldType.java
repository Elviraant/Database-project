//package com.databaseProject.Databaseproject;
import java.io.Serializable;


abstract class FieldType implements Serializable{
  enum Type {
    STRING,
    INTEGER,
    DOUBLE,
    ENUMERATION_TYPE,
    DATE
  }


  abstract boolean correctValue(String a);

  abstract Object getData();
}