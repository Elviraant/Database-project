import java.util.*;

public class Example {

	private static HashMap <String, Integer> primaryKey = new HashMap <String, Integer> ();
	private static HashMap <String, ArrayList> names = new HashMap <String, ArrayList> ();

	public static HashMap<String, ArrayList> getNames() {
		return names;
	}

	public static void main (String [] args) {

		Scanner sc = new Scanner(System.in);

		//PRIMARY KEY
		boolean yn = true;
		System.out.print("Do you want a primary key? Y/N ");
		String decision = sc.next();

	    switch(decision) {

			case "Y": yn = true;
						break;

			case "N": yn = false;
						break;
		}
		System.out.print("Name of primary Key field: ");
		String primaryKeyFieldName = sc.next();


		System.out.print("How many Fields do you want? ");
		int numberOfFields = sc.nextInt();

		for (int i = 1; i <= numberOfFields; i++) {

			System.out.print("#" +  i + " Field Name: ");
			String fieldName = sc.next();
			ArrayList field = CreateFields.createStringField();
			names.put(fieldName, field);

		}


		//FILL WITH DATA

		for (int i = 0; i < 3; i++) {

			System.out.println("Please enter the " + primaryKeyFieldName + ": ");
			String key = sc.next();
			primaryKey.put(key, i);

			for (int j = 0; j < numberOfFields; j++) {
				System.out.println("Please enter the name of the field, that you want to fill: ");
				String name = sc.next();
				ArrayList list = names.get(name);
				System.out.println("Enter Data: ");
				String data = sc.next();
				list.add(i,data);
			}

		}


		PresentData.printSpecificRows();
		PresentData.printAll();

		// SEARCHING
		System.out.println("SEARCHING");
		System.out.println("Enter a " + primaryKeyFieldName + ":");
		String keyforsearching = sc.next();

		int thesi = primaryKey.get(keyforsearching);

		System.out.println("In which field do you want to search? ");
		String key = sc.next();
		ArrayList list = names.get(key);
		System.out.print(list.get(thesi));
		System.out.println();


	}



}