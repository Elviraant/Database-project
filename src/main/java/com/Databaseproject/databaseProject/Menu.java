//package com.Databaseproject.databaseProject;
import java.io.Serializable;
public class Menu implements Serializable{

	public static int fieldTypesMenu() {

		System.out.println(String.format("%s%n%s%n%s%n%s%n", "1. Integer", "2. Double", "3. Text", "4. Enumerated Type"));
		System.out.println("Please chose one of the above options: ");
		return Database.choice(1, 4);
	}

	public static void printDeletePath() {
		System.out.print(String.format("%s%n%s%n%s%n","You have to create -at least- one field/column ",
						"If you don't want to create this table, follow this path: ",
						"Manage Tables -> Delete Data -> Delete All"));
	}
	public static void presentationMenu() {
		System.out.println();
		System.out.println("Presentation Options:");
		System.out.println("---------------------");
		System.out.println(String.format("%s\n%s\n%s\n%s\n", "1. Present all", "2. Present range of rows", "3. Present specific columns", "4. Exit"));
		System.out.println("Please chose one of the above options: ");
	}

	public static int startingMenu() {
		System.out.println();
		System.out.println("Manage your data: ");
		System.out.println("-----------------");
		System.out.println(String.format("%s%n%s%n%s%n%s%n%s%n", "1. Present Data", "2. Change Data", "3. Delete Data", "4. More Options", "5. Exit"));
		return Database.choice(1,5);
	}

	public static int moreOptionsMenu() {
		System.out.println();
		System.out.println("More options ");
		System.out.println("------------");
		System.out.println(String.format("%s%n%s%n%s%n%s%n%s%n%s%n%s%n", "1. Sort Data", "2. Add Data",
					       "3. Search Data", "4. Find Maximum Element", "5. Find Minimum Element", "6. Design View", "7. Exit" ));
		return Database.choice(1,7);
	}

	public static void multipleTablesMenu() {
		System.out.println();
		System.out.println("Manage your tables: ");
		System.out.println("---------------------");
		System.out.println(String.format("%s%n%s%n%s%n%s%n%s%n", "1. Create new table", "2. Manage Tables", "3. Ask Questions", "4. Make Correlationship", "5. Exit"));
		System.out.println("Please choose one of the above options");
	}

	public static void changeMenu() {
		System.out.println();
		System.out.println("-----------------");
		System.out.println(String.format("%s%n%s%n%s%n%s%n%s%n", "1. Change name of Field", "2. Change specific value of an element in a list","3. Change data by row",
										"4. Change all values of elements with the same value", "5. Exit"));
		System.out.println("Please chose one of the above options: ");
	}

	public static void deletionMenu() {
		System.out.println();
		System.out.println("Deletion Options:");
		System.out.println("-----------------");
		System.out.println(String.format("%s%n%s%n%s%n%s%n%s%n", "1. Delete Rows", "2. Delete Columns", "3. Delete Elements", "4. Delete All", "5. Exit"));
		System.out.println("Please choose one of the above options: ");
	}


	public static void assortmentMenu() {
			System.out.println();
			System.out.println("Assortment Options:");
			System.out.println("-------------------");
			System.out.println(String.format("%s%n%s%n", "1. Sort", "2. Exit"));
			System.out.println("Please choose one of the above options: ");
		}


	public static void additionMenu() {
		System.out.println();
		System.out.println("Add Options:");
		System.out.println("-----------------");
		System.out.println(String.format("%s%n%s%n%s%n", "1. Add records", "2. Add Columns", "3. Exit"));
		System.out.println("Please choose one of the above options: ");
	}

	public static void searchingMenu() {
		System.out.println("Do you want to search for a specific element? (Yes/No)");
	}

	public static int correlationOptions() {
		System.out.println(String.format("%s%n%s%n%s%n%s%n%s%n","What kind of corrleation do you want to create between your tables?",
				"1. One to one",
				"2. One to Many",
				"3. Many to Many",
				"4. None"));
		return Database.choice(1,4);
	}

	public static int manageCorrelationsMenu() {
		System.out.println(String.format("%s%n%s%n%s%n%s%n%s%n"
							,"1. How many correlations have I created?"
							,"2. View all correlated tables"
							,"3. Delete correlation"
							,"4. View properties"
							,"5. Exit"));
		System.out.println("Please choose one of the above options: ");
		return Database.choice(1,5);
	}

	public static int viewPopertiesMenu() {
		System.out.println(String.format("%s%n%s%n%s%n"
							,"1. Show info"
							,"2. Search related records"
							,"3. Exit"));
		System.out.println("Please choose one of the above options: ");
		return Database.choice(1,3);
	}

	public static void printNonExistantKeyMessage() {
		System.out.println("This primary key doesn't exist.");
	}

	public static boolean printTryAgainQuestionMessage() {
			System.out.println("Do you want to try again?");
			return Database.findDecision();
	}

	public static void printTryAgainMessage() {
		System.out.println("Try again.");
	}

	public static int tablesInCorrelationMenu(Table table1, Table table2) {

		System.out.println("Choose the table, that in your Correlation has the foreign key column: ");
		System.out.println(String.format("%s%s", "1. " , table1.getName()));
		System.out.println(String.format("%s%s", "2. " , table2.getName()));
		System.out.println("Please choose one of the above options: ");
		return Database.choice(1,2);
	}

	public static void deletionFailed(Table table) {
		System.out.println("Deletion failed");
		System.out.println("Table " + table.getName() + " references another table of the base.");
		System.out.println();
	}


	public static void printColumnReferredMessage(String function) {
		System.out.println("This column is referred by another table and can't " + function);

	}

	public static void printColumnRefersMessage(String function) {
		System.out.println("This column refers to another table and can't " + function);
	}
}

