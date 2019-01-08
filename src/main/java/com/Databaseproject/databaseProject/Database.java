
/**
 *Represent a Database
 */
package com.Databaseproject.databaseProject;
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
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.BufferedReader;

public class Database implements Serializable {
	private ArrayList<Table> tables = new ArrayList<Table>();
	private int tableCounter;
	private String name;
	private ArrayList<Correlation> correlations = new ArrayList<Correlation>();

	/**
	 * Creates a Database with the specified name
	 * @param name name of Database
	 */
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

	public ArrayList<Correlation> getCorrelations() {
		return correlations;
	}

	/**
	 * Check whether a table's name in a database is unique
	 * @param name name of the table
	 * @return boolean true, if the name is unique, false otherwise
	 */
	public boolean uniqueTableName(String name) {				//JUNIT TESTED

		boolean exists = true;
		for(Table t : tables) {
			if((t.getName()).equals(name)) {
				exists = false;
			}
		}
		return exists;
	}

	public static void main(String[] args) throws IOException, ClassNotFoundException, EOFException{
		Database d1 = Database.startBase();
		d1.manageTables();
		d1.save();
	}
	/**
	*Set up the base that the user wants to use
	*and create it or retrieve an already existing
	*@return Database
	*@throws ClassNotFoundException if the table cannot be stored
	*@throws EOFException if the database doesn't exist
	*@throws FileNotFoundException if the database doesn't exist
	*/
	public static Database startBase() throws ClassNotFoundException, EOFException, IOException {
		Database d1 = new Database("");
		boolean can = true;
		boolean wants = false;
		if (Database.wantsToRetrieve()) {
			wants = true;
			String name = Database.chooseBase();
			if (!name.equals("no bases")) {
				d1 = Database.readObject(name);
			} else {
				can = false;
			}
		}
		if (!(wants && can)) {
			Scanner cs = new Scanner(System.in);
			System.out.println("Name of new database: ");
			String name = cs.next();
			d1 = new Database(name);
		}
		return d1;
	}

	public void manageTables() {
		boolean continueProcess = true;
		while (continueProcess) {
			Menu.multipleTablesMenu();
			int choice = Database.choice(1,5);
			switch (choice)
			{
				case 1:
					Table table = createNewTable();
					table.setUpColumns();
					table.definePrimaryKey();
					table.callFiller();
					break;
				case 2:
					if (tableCounter == 0) {
						System.out.println("No tables in your database");
					} else {
						table = chooseTable();
						table.manageData();
					}
					break;
				case 3:
					Correlation.manageCorrelations(this);
					break;
				case 4:
					if (tableCounter == 0) {

						System.out.println("No tables in your database");
					} else {
						defineCorrelation();
					}
					break;
				default:
					continueProcess = false;
			}
		}
	}

	/**
	 * Create a new table in the database
	 * if it doesn't already exist
	 * @return Table database's new table/entity
	 */
	public Table createNewTable() {
		Scanner cs = new Scanner(System.in);
		System.out.println("Name of table:");
		String name = cs.next();
		while(!uniqueTableName(name)) {
			System.out.print("This name already exists. Please try with another: ");
			name = cs.next();
		}
		return new Table(name, this);
	}

	/**
	*Get which table the user choose to be retrieved
	*@return Table
	*/
	public Table chooseTable() {
		Table table = tables.get(0);
		if (tableCounter > 1) {
			System.out.println("Choose one of your tables: ");
			for (int i = 0; i < tableCounter; i++) {
				table = tables.get(i);
				System.out.println(String.format("%s%s%s", (i +1), ". ", table.getName()));
			}
			System.out.println("Insert the number of your choice: ");
			int choice = Database.choice(1, tableCounter);
			table = tables.get(choice - 1);
		}else if (tableCounter == 1) table = tables.get(0);
		return table;
	}

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
	*Check if user's choice is within an allowed range
	*@param start starting number of this range
	*@param end ending number of this range
	*@return choice final decision of the user
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
	*Check if user gives an integer and handles Exception.
	*@return int given by the user and prints message
	*asking for numerical data.
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
				System.out.println("Number expected");
				cs.reset();
			}
		}
		return choice;
	}

//SAVING
	/**
	*Check if user wants to save the database
	*calls method writeObject() and throws IOException which is
	*handled in writeObject()
	*@throws IOException if no database is stored
	*/
	public void save() throws IOException {
		if (wantsToSave()) {
			writeObject();
		}
	}

	/**
	*Ask if user wants to save the database
	*@return true if user wants to save the base
	*/
	public boolean wantsToSave() {
		System.out.println("Do you want to save your database?");
		return Database.findDecision();
	}

	/**
	*Check if user wants to save the data
	*@return true if user doesn't want to create a new Database
	*/
	public static boolean wantsToRetrieve() {
		System.out.println("Do you want to create a new Database?");
		return (!(Database.findDecision()));
	}

	/**
	*Save database in file with the name of the base
	*@throws IOException if the file cannot be opened
	*/
	public void writeObject() throws IOException {
		Scanner cs = new Scanner(System.in);
		String filename = this.getName();
		try {
			FileWriter file = new FileWriter("saved", true);
			BufferedWriter out = new BufferedWriter(file);
			out.write(filename);
			out.newLine();
			out.close();
		} catch (FileNotFoundException e) {
			System.err.println("Unable to open file " + filename + ": " + e.getMessage());
		}

		try {
			FileOutputStream file = new FileOutputStream(filename);
			ObjectOutputStream out = new ObjectOutputStream(file);
			out.writeObject(this);
			out.reset();
			out.close();
			System.out.println("Your database was saved successfully");
		} catch (FileNotFoundException e) {
			System.err.println("Unable to open file " + filename + ": " + e.getMessage());
		}
	}

	/**
	*Retrieve the asked database
	*@param
			nameOfBase name chosen by the user
	*@return Database by retrieving the file in which the object is stored
	*@throws ClassNotFoundException if the table cannot be stored
	*@throws EOFException if the database doesn't exist
	*@throws FileNotFoundException if the database doesn't exist
	*/
	public static Database readObject(String nameOfBase) throws ClassNotFoundException, IOException {
		Database d1 = new Database("");
		String filename = nameOfBase;
		try {
			FileInputStream file = new FileInputStream(filename);
			ObjectInputStream in = new ObjectInputStream(file);
			d1 = (Database) in.readObject();
			System.out.println("Data retrieved successfully");
		} catch (EOFException e) {
			System.out.println("Error: There is no such database");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("This table can't be stored");
		} catch (FileNotFoundException e) {
			System.out.println("Error: There is no such database");
		}
		return d1;
	}

	/**
	*Print available bases created by the user
	*@return String
			 the name of the chosen base
			 if no bases are saved it returns "no bases"
	*/
	public static String chooseBase() {
		ArrayList<String> saved = new ArrayList<String>();
		try {
			FileReader file = new FileReader("saved");
			BufferedReader in = new BufferedReader(file);
			int i = 1;
			String line;
			while ((line = in.readLine()) != null) {
				saved.add(line);
			}
		} catch (EOFException e) {
			System.out.println("Error: There are no bases stored");
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			System.out.println("Error: There are no bases stored");
		} catch (IOException e) {
			System.out.println("Unable to save");
		}
		if (saved.size() != 0) {
			System.out.println("Which database do you want to open?");
			ArrayList<String> diff = new ArrayList<String>();
			diff.add(saved.get(0));
			boolean exists;
			for (int i =1; i < saved.size(); i++) {
				String base = saved.get(i);
				exists = false;
				for (int k = 0; k < diff.size(); k++) {
					if (base.equals(diff.get(k))) {
						exists = true;
					}
				}
				if (!exists) {
					diff.add(base);
				}
			}
			for (int i = 0; i < diff.size(); i++) {
				System.out.println(String.format("%s.%s", (i+1), diff.get(i)));
			}
			int choice = choice(1, saved.size());
			return saved.get(choice-1);
		} else {
			return "no bases";
		}
	}

	/**
	 * A.Define a correlation by checking the following
	 * 1st Table objects in the Database, must be more than one
	 * 2nd Table objects must be different
	 * 3rd Table objects don't participate again in a same Correlation
	 * 4th Table objects must have a primary Key List
	 * 5th Database has enough tables for a new Correlation
	 * B.User insert the name of the Correlation
	 */
	public void defineCorrelation() {
		if ((getTableCounter() != 1)) {			//1st
			if(checkAvailabilityForCorrelation()) { //5th
				Table table1;
				Table table2;
				do {
					System.out.println("Please choose the entities you want to relate");
					System.out.print("First table: ");
					table1 = chooseTable();
					System.out.print("Second table: ");
					table2 = chooseTable();
					table2 = checkDiffrentTables(table1, table2); //2nd
					table1 = setUpTableForCorrelation(table1);	//4th
					table2 = setUpTableForCorrelation(table2);	//4th

					if(checkingCorrelation(table1, table2)) {
						System.out.println("Correlation with tables/entities " + table1.getName() + " and " + table2.getName() + " already exists.");
					}
				} while(checkingCorrelation(table1, table2)); //3rd

				Scanner sc = new Scanner(System.in);
				System.out.println("Please insert the name of Correlation. ex: Teacher teaches-name of Correlation- Student");
				String name = sc.next();

				createCorrelation(name, table1, table2);

			}
			if(checkAvailabilityForCorrelation() == false) {
				System.out.println("Your Database ran out of tables for a new Correlation." +
									"You have to create more!");
			}
		} else {
			System.out.println("There are not enough entities/tables in your Database. You have to create -at least- one more. ");
		}
	}

	/**
	 * If a Table has no primary key Column, the method will provide to user the option to add primary key column
	 * @param table Table
	 * @return Table same Table, if a pk Column already exists, OR same Table with one more Column
	 */
	public Table setUpTableForCorrelation(Table table) {
			while(!table.primaryKeyColumnExists()) {
				System.out.print("Table " + table.getName() + " doesn't have a primary key Column.");
				System.out.println("It's time to add one: ");
				table.addPrimaryKeyColumn();
			}
			return table;
	}

	/**
	 * Check if a correlation already exists
	 * @param table1 first table/entity in correlation
	 * @param table2 second table/entity in correlation
	 * @return boolean
	 */
	public boolean checkingCorrelation(Table table1, Table table2) { //JUNIT TESTED
		boolean exists = false;
		for(Correlation c : correlations) {
			if((c.getTable1().equals(table1) && c.getTable2().equals(table2))
				|| (c.getTable1().equals(table2) && c.getTable2().equals(table1))) {
				exists = true;
			}
		}
		return exists;
	}

	/**
	 * Checks, if a new Correlation is available or Database ran out of tables,
	 * that make a new Correlation
	 * @return boolean true if a new Correlation is possible, false otherwise
	 */
	public boolean checkAvailabilityForCorrelation() { //JUNIT TESTED
		 int counter = 0;
		 for(Table t1 : tables) {
			 for(Table t2 : tables) {
				 if(!t1.equals(t2)) {
					 if(!checkingCorrelation(t1, t2)) {
						 counter++;
					 }
				 }
			 }
		 }
		 if (counter == 0) {
			 return false;
		 } else {
			 return true;
		 }

	}

	/**
	 * Define which table will have the foreign key Column
	 * @param table1 first table/entity in correlation
	 * @param table2 second table/entity in correlation
	 * @return Table table who will have the foreign key Column
	 */
	public Table defineTable2(Table table1, Table table2) {

		int choice = Menu.tablesInCorrelationMenu(table1, table2);
		if (choice == 1) {
			return table1;
		} else {
			return table2;
		}
	}

	/**
	 * Checks, if the user correlate same tables
	 * Returns Table object
	 * @param table1 first table/entity in correlation
	 * @param table2 second table/entity in correlation
	 * @return Table
	 */
	public Table checkDiffrentTables(Table table1, Table table2) { //JUNIT TESTED
			do {
				if (table2.equals(table1)) {
					System.out.println("Error: Same table chosen");
					System.out.println("Please try again");
					table2 = chooseTable();
				}
			} while (table2.equals(table1));
			return table2;
	}

	/**
	 * User chooses from a menu the type of correlation, that suits him
	 * @param name name of correlation
	 * @param table1 first table/entity of this correlation
	 * @param table2 second table/entity of this correlation
	 */
	public void createCorrelation(String name, Table table1, Table table2) {
		int option = Menu.correlationOptions();
		if(option == 1 || option == 2) {
			if(!defineTable2(table1, table2).equals(table2)) {
				Table temp = table2;
				table2 = table1;
				table1 = temp;
			}
		}
		switch (option) {
			case (1): OneToOne c1 = new OneToOne(name,table1, table2);
					  correlations.add(c1);
					  c1.fillForeignKeyColumn();
					  break;
			case (2): OneToMany c2 = new OneToMany(name, table1, table2);
					  correlations.add(c2);
					  c2.fillForeignKeyColumn();
					  break;
			case (3): ManyToMany c3 = new ManyToMany(name, table1, table2);
					  correlations.add(c3);
					  c3.fillForeignKeyColumn();
					  break;
			default : manageTables();
					  break;
			}
	}

}


