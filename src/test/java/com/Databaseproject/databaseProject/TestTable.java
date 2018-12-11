import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestTable {

	private Table table = new Table("Test");
	private Column column;
	private FieldType type;
	private final String firstElement = "NAME";
   	private final String missingElement = "AGE";

	@Before
	public void setUp() {
		table = new Table("Test");
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
