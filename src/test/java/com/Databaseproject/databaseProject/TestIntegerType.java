package com.Databaseproject.databaseProject;
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
	public void testGetData() throws InputMismatchException {
		String input = "NOT AN INTEGER";
		ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
   		System.setIn(in);
   		integer.getData();
	}



}