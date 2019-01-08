//package com.Databaseproject.databaseProject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.util.InputMismatchException;

public class TestIntegerType {

	private IntegerType integer;

	@Before
	public void setUp() {
		integer = new IntegerType();
	}


	@Test
	public void testGetData() {
		String input = "1";
		ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
   		System.setIn(in);
   		Assert.assertSame("Failure - Not an IntegerType", integer.getData(), 1);
	}



}