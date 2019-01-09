
package com.Databaseproject.databaseProject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

public class TestColumn {

  private Database d1 = new Database("SUPER");
  private Table table = new Table("Test", d1);
  private Column column;
  private FieldType type;
  private final String firstElement = "AGE";
  private final String missingElement = "NAME";

  @Before
  public void setUp() {
    d1 = new Database("SUPER");
    table = new Table("Test", d1);
    type = new IntegerType();
    column = new Column(firstElement, type, table);
    column.getField().add(19);
    column.getField().add(20);
    column.getField().add(19);
  }

  @Test
  public void testCheckUniqueness() {
    Object data = 20;
    Assert.assertFalse("Failure - Data already exists", column.checkUniqueness(data));
    data = 13;
    Assert.assertTrue("Failure - Data doesn't exist", column.checkUniqueness(data));
  }

  @Test
  public void testFindPKeyPosition() {
    column.setPrimaryKey(true);
    Object data = 20;
    Assert.assertSame("Failure - Key is not at first position", column.findPKeyPosition(data), 1);
    data = 10;
    Assert.assertSame("Failure - Key exists", column.findPKeyPosition(data), -1);
  }

  @Test
  public void TestmatchingRows() {
    ArrayList<Integer> list = column.matchingRows(19);
    for (int i = 0; i < list.size(); i++) {
      Assert.assertEquals("Failure-elements are not equal", 19, column.getField().get(list.get(i)));
    }
    ArrayList<Integer> list2 = column.matchingRows(10);
    Assert.assertEquals("Failure-element does not exist", 0, list2.size());
  }
}
