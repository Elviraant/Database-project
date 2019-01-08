//package com.Databaseproject.databaseProject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestEnumeratedType {

  private EnumeratedType type;
  private String missingValue = "Dog"

  @Before
  public void setUp() {
    type = new EnumeratedType();
    type.getAllowedStrings().add("Male");
    type.getAllowedStrings().add("Female");
    type.getAllowedStrings().add("Other");

  }

  @Test
  public void testCorrectValue() {

  Assert.assertFalse("Failure - Dog isn't one of the values", type.correctValue(missingValue));
  Assert.assertTrue("Failure - Female is one of the values", type.correctValue("Female"));
 }
}