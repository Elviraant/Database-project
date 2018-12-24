import java.io.Serializable;
public class Menu implements Serializable{

	public static void presentationMenu() {
		System.out.println();
		System.out.println("Presentation Options:");
		System.out.println("---------------------");
		System.out.println(String.format("%s\n%s\n%s\n", "1. Present all", "2. Present specific rows", "3. Present specific columns"));
		System.out.println("Please chose one of the above options: ");
	}

	public static void startingMenu() {
		System.out.println();
		System.out.println("Manage your data: ");
		System.out.println("-----------------");
		System.out.println(String.format("%s\n%s\n%s\n%s\n%s\n%s\n", "1. Present Data", "2. Change Data", "3. Delete Data", "4. Sort Data", "5. Add Data", "6. Exit" ));
		System.out.println("Please chose one of the above options: ");
	}

	public static void multipleTablesMenu() {
		System.out.println();
		System.out.println("Manage your tables: ");
		System.out.println("---------------------");
		System.out.println(String.format("%s\n%s\n%s\n%s\n", "1. Create new table", "2. Manage Tables", "3. Ask Questions", "4. Exit"));
		System.out.println("Plese choose one of the above options");
	}

	public static void changeMenu() {
		System.out.println();
		System.out.println("-----------------");

		System.out.println(String.format("%s\n%s\n%s\n%s\n",
		                                 "1. Change name of Field",
		                                 "2. Change specific value of an element in a list.",
										 "3. Change data by row",
										 "4. Change all values of elements with the same value"));


		System.out.println("Please chose one of the above options: ");
	}

	public static void deletionMenu() {
		System.out.println();
		System.out.println("Deletion Options:");
		System.out.println("-----------------");
		System.out.println(String.format("%s\n%s\n%s\n%s\n", "1. Delete Rows", "2. Delete Columns", "3. Delete Elements", "4. Delete All"));
		System.out.println("Please choose one of the above options: ");
	}


	public static void assortmentMenu() {
			System.out.println();
			System.out.println("Assortment Options:");
			System.out.println("-------------------");
			System.out.println(String.format("%s\n%s\n", "1. Sort in alphabetical order", "2."));
			System.out.println("Please choose one of the above options: ");
		}


	public static void additionMenu() {
		System.out.println();
		System.out.println("Add Options:");
		System.out.println("-----------------");
		System.out.println(String.format("%s\n%s\n", "1. Add records", "2. Add Columns"));
		System.out.println("Please choose one of the above options: ");
	}


}

