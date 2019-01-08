import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.util.InputMismatchException;

public class TestStringType {

	private StringType str;

	@Before
	public void setUp() {
		str = new StringType();
	}


	@Test
	public void testGetData() {
		String input = "ABC";
		ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
   		System.setIn(in);
   		Assert.assertSame("Failure - Not a StringType", str.getData(), input);
	}

}