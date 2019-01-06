//package com.databaseProject.Databaseproject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

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

	@Test
	public void testFindPrimaryKeyColumn() {
		column.setPrimaryKey(true);
		Assert.assertEquals("failure - wrong position of Primary Key Column", table.findPrimaryKeyColumn(), 0);

		column.setPrimaryKey(false);
		Assert.assertEquals("failure - Primary Key Column does not exist", table.findPrimaryKeyColumn(), -1);
	}

	public void input() {
			Scanner sc = new Scanner(System.in);
			String input = sc.nextLine();
			System.out.println(input);
			input = sc.nextLine();
			System.out.println(input);
		}

		public void output() {
			String fieldname = "NAME";
			System.setIn(new ByteArrayInputStream(fieldname.getBytes()));
			input();
		}

			@Test
			public void testInputFieldName() {
				String fieldname = firstElement;
				System.setIn(new ByteArrayInputStream(fieldname.getBytes()));
				input();
				Assert.assertEquals("failure - wrong position", table.inputFieldName("random process"), 0);

				fieldname = missingElement;
				System.setIn(new ByteArrayInputStream(fieldname.getBytes()));
				input();
				Assert.assertEquals("failure - this field does not exist in Table",table.inputFieldName("random process") , -1);
			}
		}
