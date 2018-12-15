/**
Represents our Database
*/
import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;

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
		table.manageData();

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
	/**
	*returns number of choice from given range checking it is valid
	*@param start starting number of the range of options
	*@param end ending number of the range of options
	*@return choice returns the final option
	*/

	/**
	*checks if user gives an integer and handles Exception.
	*@param return integer given by the user.
	*/
	public static int valid() {
		int choice = 0;
		boolean hasError = true;
		while (hasError) {
			try {
				choice = Integer.parseInt(cs.next());
				hasError = false;
			} catch (Exception e) {
				System.out.println("Please choose a valid option");
				cs.reset();
			}
		}
		return choice;
	}

	public static int choice(int start, int end) {
		int choice = valid();
		while ((choice < start) || (choice > end)) {
			System.out.println("Please chose a valid option");
			choice = valid();
		}
		return choice;
	}
}

