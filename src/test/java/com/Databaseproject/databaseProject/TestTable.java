
package com.Databaseproject.databaseProject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.lang.System.*;

public class TestTable {

  private Database d1 = new Database("SUPER");
  private Table table = new Table("Test", d1);
  private Column column1;
  private Column column2;
  private Column column3;
  private FieldType type1;
  private FieldType type2;
  private final String firstElement = "NAME";
  private final String missingElement = "AGE";

  @Before
  public void setUp() {
    d1 = new Database("SUPER");
    table = new Table("Test", d1);
    type1 = new StringType();
    type2 = new EnumeratedType();
    column1 = new Column(firstElement, type1, table);
  }

  @Test
  public void testCheckOwnType() {
    column2 = new Column("GENDER", type2, table);
    Assert.assertTrue("Failure - Enumerated Field return false", table.checkOwnType("GENDER"));
    Assert.assertFalse("Failure - String Field return true", table.checkOwnType(firstElement));
  }

  @Test
  public void testContainsName() {
    Assert.assertEquals("Failure - does not contain first element", table.containsName(firstElement), 0);
    Assert.assertEquals("Failure - contains missing element", table.containsName(missingElement), -1);
  }

  @Test
  public void testFindPrimaryKeyColumn() {
    column1.setPrimaryKey(true);
    Assert.assertEquals("Failure - wrong position of Primary Key Column", table.findPrimaryKeyColumn(), 0);

    column1.setPrimaryKey(false);
    Assert.assertEquals("Failure - Primary Key Column does not exist", table.findPrimaryKeyColumn(), -1);
  }

  @Test
  public void testFindForeignKeyColumn() {
    Assert.assertEquals("Failure - Foreign Key Column exists", table.findForeignKeyColumn(), -1);
    column3 = new Column(table, true);
    Assert.assertEquals("Failure - Foreign Key Column does not exist", table.findForeignKeyColumn(), 1);

  }

  @Test
  public void testPrimaryKeyColumnExists() {
    column1.setPrimaryKey(true);
    Assert.assertTrue("Failure - Primary Key Column does not exist", table.primaryKeyColumnExists());
    column1.setPrimaryKey(false);
    Assert.assertFalse("Failure - Primary Key Column exists", table.primaryKeyColumnExists());

  }

  @Test
  public void testDeleteRow() {
    column1.getField().add("John");
    table.deleteRow(1);
    Assert.assertTrue("Failure - First Record is still in table", column1.getField().isEmpty());
  }

  @Test
  public void testCompareStrings() {
    Object s1 = "1";
    Object s2 = "1";
    Assert.assertEquals("Failure - Equal Objcets don't return 0", table.compareStrings(s1, s2), 0);

  }

  @Test
  public void testCompareIntegers() {
    Object s1 = 1;
    Object s2 = 1;
    Assert.assertEquals("Failure - Equal Objcets don't return 0", table.compareIntegers(s1, s2), 0);
    s1 = 2;
    s2 = 1;
    Assert.assertEquals("Failure - Greater s1 doesn't return 1", table.compareIntegers(s1, s2), 1);
    s1 = 1;
    s2 = 2;
    Assert.assertEquals("Failure - Greater s2 doesn't return -1", table.compareIntegers(s1, s2), -1);
  }

  @Test
  public void testCompareDoubles() {
    Object s1 = 1.4;
    Object s2 = 1.4;
    Assert.assertEquals("Failure - Equal Objcets don't return 0", table.compareDoubles(s1, s2), 0);
    s1 = 2.1;
    s2 = 1.2;
    Assert.assertTrue("Failure - Greater s1 doesn't return positive number", table.compareDoubles(s1, s2) > 0);
    s1 = 1.3;
    s2 = 2.5;
    Assert.assertTrue("Failure - Greater s2 doesn't return negative number", table.compareDoubles(s1, s2) < 0);
  }
}
