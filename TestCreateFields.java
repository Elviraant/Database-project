import java.util.ArrayList;
import java.util.HashMap;

//import org.junit.After;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;

public class TestCreateFields {

   // @Test
	public void testSetPrimaryKey(){

		CreateFields.setPrimaryKey("ABC", 0);
    	//assertEquals("failure - wrong set method", CreateFields.getPrimaryKey.size(), 1);
	}

	//@Test
	public void testGetPrimaryKey(){

		CreateFields.setPrimaryKey("GRDFGFG", 1);
		CreateFields.getPrimaryKey();
    	//Assert.assertEquals("failure - wrong get method", list.size(), 1);

	}

	//@Test
	private void testCreateStringField() {
		ArrayList list1 = CreateFields.createStringField();
		ArrayList<String> list2 = new ArrayList<String>();

		if (list1.equals(list2)) {
			System.out.println("Nice!");
		} else {
			System.out.println("Sth gone wrong!");
		}

		//Assert.assertArrayEquals(list2, list1);
	}

	//@Test
	public void testCreateIntegerField() {
		ArrayList list1 = CreateFields.createIntegerField();
		ArrayList<Integer> list2 = new ArrayList<Integer>();

		if (list1.equals(list2)) {
			System.out.println("Nice!");
		} else {
			System.out.println("Sth gone wrong!");
		}
	}

	//@Test
	public void testCreateDoubleField() {
		ArrayList list1 = CreateFields.createDoubleField();
		ArrayList<Double> list2 = new ArrayList<Double>();

		if (list1.equals(list2)) {
			System.out.println("Nice!");
		} else {
			System.out.println("Sth gone wrong!");
		}

	}

	//@Test
	public void testCreateBooleanField() {
		ArrayList list1 = CreateFields.createBooleanField();
		ArrayList<Boolean> list2 = new ArrayList<Boolean>();

		if (list1.equals(list2)) {
			System.out.println("Nice!");
		} else {
			System.out.println("Sth gone wrong!");
		}

	}
//
//   @After
//    public void tearDown() {
//        list = null;
//    }



}

