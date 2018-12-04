package
com.Databaseproject.databaseProject;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestCreateFields {


	//@Before
	//public void setUp() {

	//	 HashMap<String, Integer> testMap = new HashMap<String, Integer>();

	//}

   @Test
	public void testSetPrimaryKey() {

		CreateFields.setPrimaryKey("ABC", 0);
    	Assert.assertTrue("failure - wrong set method", CreateFields.getPrimaryKey().get("ABC").equals(0));
	}


	@Test
	public void testGetPrimaryKey() {

		HashMap<String, Integer> testMap = new HashMap<String, Integer>();
    	Assert.assertTrue("failure - wrong get method", CreateFields.getPrimaryKey().equals(testMap));


	}

	@Test
	public void testCreateStringField() {
		ArrayList list1 = CreateFields.createStringField();
		ArrayList <String> list2 = new ArrayList<String>();
       	Assert.assertTrue("failure - createStringField() method went wrong.",
                list1.equals(list2));
	}

	@Test
	public void testCreateIntegerField() {
		ArrayList list1 = CreateFields.createIntegerField();
		ArrayList <Integer> list2 = new ArrayList<Integer>();
		Assert.assertTrue("failure - createIntegerField() method went wrong.",
			list1.equals(list2));

	}

	@Test
	public void testCreateDoubleField() {
		ArrayList list1 = CreateFields.createDoubleField();
		ArrayList <Double> list2 = new ArrayList<Double>();

       	Assert.assertTrue("failure - createDoubleField() method went wrong.",
                list1.equals(list2));
	}

	@Test
	public void testCreateBooleanField() {
		ArrayList list1 = CreateFields.createBooleanField();
		ArrayList <Boolean> list2 = new ArrayList<Boolean>();
		Assert.assertTrue("failure - createBooleanField() method went wrong.",
                list1.equals(list2));
	}


//   @After
//    public void tearDown() {
//        list = null;
//    }



}

