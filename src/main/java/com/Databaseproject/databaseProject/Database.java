/**
Represents our Database
*/
import java.util.ArrayList;
import java.util.Scanner;

public class Database {
	private static ArrayList<Table> tables = new ArrayList<Table>();
	private static int tableCounter;
	private static String name;

	public static ArrayList<Table> getTables() {
		return tables;
	}

	public static void setTables(ArrayList<Table> tables) {
		Database.tables = tables;
	}

	public static int getTableCounter() {
		return tableCounter;
	}

	public static void setTableCounter(int tableCounter) {
		Database.tableCounter = tableCounter;
	}

	public static String getName() {
		return name;
	}

	public static void setName(String name) {
		Database.name = name;
	}

	public static Scanner cs = new Scanner(System.in);

	public static void main(String[] args) {
		System.out.println("Name of database: ");
		String name = cs.nextLine();
		Database.setName(name);
		System.out.println("Database: "+ name);

		//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		Table table = new Table("student");

		table.setFieldNames();
		table.callFiller();
		table.printAll();
		table.printSpecificRows();
		table.printSpecificColumns();

	}

	//YES OR NO METHOD
	public static boolean findDecision() {
		boolean yn = true;
		String decision = cs.next();
		boolean answerfound = false;
		while (answerfound == false) {
			switch(decision) {

				case "YES": yn = true;
				case "yes": yn = true;
				case "Yes": yn = true;
				case "Y": yn = true;
				case "y": yn = true;
							answerfound = true;
							break;

				case "NO": yn = false;
				case "no": yn = false;
				case "No": yn = false;
				case "N": yn = false;
				case "n": yn = false;
							answerfound = true;
							break;
				default:
					System.out.print("Please answer with 'Yes' or 'No' ");
					decision = cs.next();
			}
		}
		return yn;
	}
}
