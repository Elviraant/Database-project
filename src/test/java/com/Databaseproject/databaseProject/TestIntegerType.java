import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.io.InputStream;
import java.io.ByteArrayInputStream;

public class TestIntegerType {

	private IntegerType integer;

	@Before
	public void setUp() {
		integer = new IntegerType();
	}


	@Test(expected = InputMismatchException.class)
	public void testGetData() {
		String input = "NOT AN INTEGER";
		InputStream in = new ByteArrayInputStream(input.getBytes());
   		System.setIn(in);
   		integer.getData();
	}



}