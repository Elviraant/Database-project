import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.io.InputStream;
import java.io.ByteArrayInputStream;


public class TestDatabase {

	private	Database db;
	private	Table table1;
	private Table table2;
	private Table table3;
	private Column column;
	private FieldType type;
	private Correlation correlation;
	private String missingName = "Lessons";
	private String existingName = "Student";
	private int startPoint = 1;
	private int endPoint = 4;
	private int inRange = 3;
	private int outOfRange = 5;

	@Before
	public void setUp() {
		db = new Database("University");
		table1 = new Table("Student", db);
		table2 = new Table("Teachers", db);
		table3 = new Table("Countries", db);
		correlation = new Correlation("teaches", table2, table1);
		type = new StringType();
		column = new Column("Name", type, table1);

	}

	@Test
	public void testUniqueTableName() {
		Assert.assertTrue("Failure - Name of table is not unique",
						db.uniqueTableName(missingName));
		Assert.assertFalse("Failure - Name of table is not used by other Table",
						db.uniqueTableName(existingName));

	}
	/**@Test //!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	public void testCreateNewTable() {
		Assert.assertEquals("Failure - Table didn't created", db.createNewTable());

	}**/
	/**
	@Test
	public void testChooseTable() {


	} **/

	@Test
	public void testFindDecision() {
		String input = "y";
		InputStream in = new ByteArrayInputStream(input.getBytes());
   		System.setIn(in);
		Assert.assertTrue("Failure - yes returns false", Database.findDecision());
	}

	/**@Test //!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	public void testChoice() {

		int input = inRange;
		InputStream in = new ByteArrayInputStream(input.getBytes());
   		System.setIn(in);
		Assert.assertEquals("Failure - Out of range", Database.choice(startPoint,endPoint), 3);
		//Assert.assertEquals("Failure - Out of range", Database.choice(startPoint,endPoint), 3);
	}**/

	/**@Test
	public void testValid() {


	}**/

	@Test
	public void testSetUpTableForCorrelation() {
		Assert.assertSame("Failure - Table has changed", db.setUpTableForCorrelation(table1), table1);
	}

	@Test
	public void testCheckingCorrelation() {
		Assert.assertTrue("Failure - Correlation doesn't exist",
						db.checkingCorrelation(table1, table2));
		Assert.assertFalse("Failure - Correlation exists",
						db.checkingCorrelation(table1, table3));

	}

	@Test
	public void testCheckDiffrentTables() {

		Assert.assertSame("Failure - Tables are not different",
							db.checkDiffrentTables(table1, table2), table2);
		Assert.assertNotSame("Failure - Tables are different",
							db.checkDiffrentTables(table1, table1), table1);

	}

}