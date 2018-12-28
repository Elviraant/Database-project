/**
Represents our Database
*/
//package com.databaseProject.Databaseproject;
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
		d1.manageTables();
		d1.save();
	}

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
					continueProcess = false;
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

	public Table createNewTable() {
		Scanner cs = new Scanner(System.in);
		System.out.println("Name of table:");
		String name = cs.next();
		return new Table(name, this);
	}


	public Table chooseTable() {
		Table table = tables.get(0);
		if (tableCounter > 1) {
			Scanner cs = new Scanner(System.in);
			System.out.println("Choose one of your tables: ");
			for (int i = 0; i < tableCounter; i++) {
				table = tables.get(i);
				System.out.println(String.format("%s%s%s", (i +1), ". ", table.getName()));
			}
			System.out.println("Insert the number of your choice: ");
			int choice = Database.choice(1, tableCounter);
			table = tables.get(choice - 1);
		} else if (tableCounter == 1) table = tables.get(0);
		return table;
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
			//System.out.println("Please insert a valid filepath: ");
			//String path = cs.next();
			FileWriter file = new FileWriter("saved", true); //(path+"\\"+ filename);
			BufferedWriter out = new BufferedWriter(file);
			out.write(filename);
			out.newLine();
			out.close();
		} catch (FileNotFoundException e) {
			System.err.println("Unable to open file " + filename + ": " + e.getMessage());
		}

		try {
			//System.out.println("Please insert a valid filepath: ");
			//String path = cs.next();
			FileOutputStream file = new FileOutputStream(filename); //(path+"\\"+ filename);
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
	public static Database readObject(String nameOfBase) throws ClassNotFoundException, IOException {
		Database d1 = new Database("");
		String filename = nameOfBase;
		try {
			//System.out.println("Please insert the path where your database is stored.");
			//String path = cs.next();
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


	public static String chooseBase() {
		ArrayList<String> saved = new ArrayList<String>();
		try {
			//System.out.println("Please insert the path where your database is stored.");
			//String path = cs.next();
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

	public void defineCorrelation() {
		if ((getTableCounter() != 1)) {
			System.out.println("Please choose the entities you want to relate"); //check for Pk column
			System.out.print("First table: ");
			Table table1 = chooseTable();
			table1 = chooseRightTableForCorrelation(table1);
			System.out.print("Second table: ");
			Table table2 = chooseTable();
			table2 = chooseRightTableForCorrelation(table2);
			do {											//diffrent tables
				if (table2.equals(table1)) {
					System.out.println("Error: Same table chosen");
					System.out.println("Please try again");
					table2 = chooseTable();
				}
			} while (table2.equals(table1));
			Scanner sc = new Scanner(System.in);
			System.out.println("Please insert the name of Correlation. ex: Teacher teaches-name of Correlation- Student");
			String name = sc.next();
			int option = Menu.correlationOptions();
			System.out.println(option);
			switch (option) {
				case (1): System.out.println("asdf");
						 OneToOne c1 = new OneToOne(name, table1, table2);
						 correlations.add(c1);
						 System.out.println("asdf");
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
			}

			table2.printHeader();
	 	} else {
			System.out.println("There are not enough entities/tables in your Database. You have to create -at least- one more. ");
		}
	}

	public Table chooseRightTableForCorrelation(Table table) {
			while(!table.primaryKeyColumnExists()) {
				System.out.println("This table doesn't have a primary key Column.");
				int choice = Menu.menuForNoPkColumn();
				switch (choice)
				{
					case 1:
						table = chooseTable();
						//return true;
						break;
					case 2:
						table = chooseTable();
						//return false;
						break;
					default:
						//return false;
						break;
				}

			}
			return table;
	}

	public boolean checkingCorrelation(Table table1,Table table2) {
		boolean exists;
		for(Correlation c : correlations) {
			if((c.getTable1().equals(table1) && c.getTable2().equals(table2))
				|| (c.getTable1().equals(table2) && c.getTable2().equals(table1))) {
				exists = true;
			}
		}
		return true; //not ready yet
	}


}

