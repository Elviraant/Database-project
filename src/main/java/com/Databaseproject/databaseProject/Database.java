/**
Represents our Database
*/
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.EOFException;
import java.io.NotSerializableException;
import java.util.InputMismatchException;
import java.io.Serializable;

public class Database implements Serializable {
	private ArrayList<Table> tables = new ArrayList<Table>();
	private int tableCounter;
	private String name;

	public Database(String name) {
		this.name = name;
	}

	public ArrayList<Table> getTables() {
		return tables;
	}

	public void setTables(ArrayList<Table> tables) {
		this.tables = tables;
	}

	public int getTableCounter() {
		return tableCounter;
	}

	public void setTableCounter(int tableCounter) {
		this.tableCounter = tableCounter;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static void main(String[] args) throws IOException, ClassNotFoundException, EOFException{
		Database d1 = Database.startBase();
		d1.getTables().get(0).manageData();
		d1.save();
	}

	public static Scanner cs = new Scanner(System.in);



	public static Database startBase() throws ClassNotFoundException, EOFException, IOException {
		Database d1 = new Database("");
		if (Database.wantsToRetrieve()) {
			d1 = Database.readObject();
		} else {
			Scanner cs = new Scanner(System.in);
			System.out.println("Name of database: ");
			String name = cs.next();
			d1 = new Database(name);
			System.out.println("Database: "+ name);
			Table table = new Table("student", d1);
			table.setFieldNames();
			table.callFiller();
		}
		return d1;
	}

	//YES OR NO METHOD
	public static boolean findDecision() {
		Scanner cs = new Scanner(System.in);
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
	public static int choice(int start, int end) {
		int choice = valid();
		while ((choice < start) || (choice > end)) {
			System.out.println("Please chose a valid option");
			choice = valid();
		}
		return choice;
	}

	/**
	*checks if user gives an integer and handles Exception.
	*@param return integer given by the user.
	*/
	public static int valid() {
		Scanner cs = new Scanner(System.in);
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

//SAVING

	public void save() throws IOException {
		if (wantsToSave()) {
			writeObject();
		}
	}

	public boolean wantsToSave() {
		System.out.println("Do you want to save your database?");
		return Database.findDecision();
	}

	/**
	*Checks if user wants to save the data
	*@return true if user doesn't want to create a new Database
	*/
	public static boolean wantsToRetrieve() {
		System.out.println("Do you want to create a new Database?");
		return (!(Database.findDecision()));
	}

	//works only with right path
	public void writeObject() throws IOException {
		Scanner cs = new Scanner(System.in);
		String filename = this.getName();

		try {
			System.out.println("Please insert the path where you would like to save your database");
			String path = cs.next();
			FileOutputStream file = new FileOutputStream(path+"\\"+ filename);
			ObjectOutputStream out = new ObjectOutputStream(file);
			out.writeObject(this);
			out.reset();
			out.close();
			System.out.println("Your database was saved successfully");
		} catch (FileNotFoundException e) {
			System.err.println("Unable to open file " + filename + ": " + e.getMessage());
		}
	}
	//Works only with right path
	public static Database readObject() throws ClassNotFoundException, IOException {
		Scanner cs = new Scanner(System.in);
		System.out.println("Which database do you want to open?");
		String filename = cs.next();
		Database d1= new Database("");
		System.out.println("Please insert the path where your database is stored.");
		String path = cs.next();
		FileInputStream file = new FileInputStream(path +"\\" +filename);
		ObjectInputStream in = new ObjectInputStream(file);
		try {
			d1 = (Database) in.readObject();
			System.out.println("Data retrieved successfully");
		} catch (EOFException e) {
			System.out.println("There is no database with this name");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("This table can't be stored");
		}
		return d1;
	}
}

