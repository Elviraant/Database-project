//package com.databaseProject.Databaseproject;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestColumn {

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
		type = new FieldType(1);
		column = new Column("Age", type, table);
		column.getField().add(19);
		column.getField().add(20);
		column.getField().add(19);
	}

	@Test
	public void TestmatchingRows() {
		ArrayList<Integer> list = column.matchingRows(19);
		for(int i=0; i < list.size(); i++) {
			Assert.assertEquals("Failure-elements are not equal", 19, col.getField().get(list.get(i)));
		}

		ArrayList<Integer> list2 = column.matchingRows(10);
		Assert.assertEquals("Failure-element does not exist", 0, list2.size());
	}
}