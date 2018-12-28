//package com.databaseProject.Databaseproject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestTable {

	private Database d1 = new Database("SUPER");
	private Table table = new Table("Test", d1);
	private Column column;
	private FieldType type;
	private final String firstElement = "NAME";
   	private final String missingElement = "AGE";

	@Before
	public void setUp() {
		d1 = new Database("SUPER");
		table = new Table("Test", d1 );
		type = new StringType();
		column = new Column(firstElement, type, table);

	 }

	@Test
	public void testContainsName() {
		Assert.assertEquals("failure - does not contain first element",
                	table.containsName(firstElement), 0);
       		Assert.assertEquals("failure - contains missing element",
                	table.containsName(missingElement), -1);
	}
}
