//package com.Databaseproject.databaseProject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.util.InputMismatchException;

public class TestDoubleType {

	private DoubleType doub;

	@Before
	public void setUp() {
		doub = new DoubleType();
	}


	@Test
	public void testGetData() {
		String input = "1.2";
		Double doub1 = 1.2;
		ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
   		System.setIn(in);
   		Assert.assertSame("Failure - Not a DoubleType", doub.getData(), doub1);
	}

}