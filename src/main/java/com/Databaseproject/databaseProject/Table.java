
/**
Represents a table of our database.
*/
//package com.databaseProject.Databaseproject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.io.Serializable;
import java.util.Collections;
import java.util.*;
import java.io.*;

public class Table implements Serializable {
    private ArrayList<Column> columns = new ArrayList<>();
    private int columnCounter = 0;
    private String name;
    private int numberOfRows = 0;
    private HashMap<Table, Integer> positionOffFk = new HashMap<Table, Integer>();

    /**
     * Constructor for Table class
     *
     *  @param columns
     *            arraylist with objects of Column
     * @param columnCounter
     *            number of columns in this table
     * @param name
     *            name of this table
     * @param numberOfRows
     *            number of row insertions from user
     */
    public Table(String name, Database d1) {
        this.name = name;
        d1.getTables().add(this);
        int counter = d1.getTableCounter();
        counter++;
        d1.setTableCounter(counter);
    }

    public ArrayList<Column> getColumns() {
        return columns;
    }

    public void setColumns(ArrayList<Column> columns) {
        this.columns = columns;
    }

    public int getColumnCounter() {
        return columnCounter;
    }

    public void setColumnCounter(int columnCounter) {
        this.columnCounter = columnCounter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public void setNumberOfRows(int numberOfRows) {
        this.numberOfRows = numberOfRows;
    }

    public HashMap<Table, Integer> getPositionOffFk() {
        return positionOffFk;
    }

    public void setPositionOffFk(Table key, Integer value) {
        positionOffFk.put(key, value);
    }

    /**
     * public HashMap<Table, Integer> getPositionOfFK() { return positionOffFk; }
     **/

    /**
     * Returns void Creates fields for a Table and calls findType method, to create
     *  the correct ArrayList
     */

    public void setUpColumns() {
        Column rows = new Column("Record", new IntegerType(), this);
        setFieldNames();
    }

    public void setFieldNames() {
        Scanner cs = new Scanner(System.in);
        System.out.println("Set the names of the fields that you want to create\nEnter EXIT to stop");
        // int counter = 1;
        // columnCounter = 1;
        System.out.print("#" + columnCounter + " Field Name: ");
        String nameOfField = cs.next();
        System.out.println();

        while (!nameOfField.equals("EXIT")) {

            System.out.println("Please choose one of the following data types:");
            System.out.println();
            System.out.println("1. Integer\n2. Double\n3. Text\n4. Own Type");

            int choice = Database.choice(1, 4);
            if (choice == 1 || choice == 2 || choice == 3) {
                FieldType type = Column.findType(choice);
                new Column(nameOfField, type, this);
            } else if (choice == 4) {
                EnumeratedType type = new EnumeratedType();
                type.defineEnumeration();
                new Column(nameOfField, type, this);
            }
            // counter++;
            System.out.println();
            System.out.println("Please insert name of the next field");
            System.out.print("#" + columnCounter + " Field Name: ");

            int check = -2;
            while (check != -1) {
                nameOfField = cs.next();
                check = this.containsName(nameOfField);
                if (check != -1) {
                    System.out.println("That name already exists. Please try with another: ");
                }
            }

        }
        System.out.println();
    }

    /*
     * Defines, which Column is the primary Key field Checks, if the name of a field
     * exists at this Table If it exists, sets the boolean instance variable true
     */
    public void definePrimaryKey() {
        Scanner cs = new Scanner(System.in);
        System.out.println("Do you want to set a field as primary key? ");
        boolean continueProcess;
        continueProcess = Database.findDecision();
        if (continueProcess) {
            continueProcess = true;
            while (continueProcess) {
                System.out.print("Please, insert the name of the primary key field: ");
                String primaryKeyName = cs.next();
                int exists = this.containsName(primaryKeyName);
                if (exists != -1) {
                    this.getColumns().get(exists).setPrimaryKey(true);
                    continueProcess = false;
                } else {
                    System.out.print(primaryKeyName + ". No such field at this entity.");
                    System.out.print("Do you want to try again? ");
                    continueProcess = Database.findDecision();
                }
            }
        }
    }

    /**
     * ask how to fill the fields and call method columnFillerByRow or
     * columnFillerByCollumn
     */
    public void callFiller() {
        System.out.println("How would you like to fill the table?");
        System.out.println("1. By row\n2. By column");
        int filltype = Database.choice(1, 2);
        if (filltype == 1) {
            this.columnFillerByRow();
        } else if (filltype == 2) {
            this.columnFillerByColumn();
        }
        System.out.println("");
    }

    /**
     * Fill in with Data, Fills by row
     */
    public void columnFillerByRow() {
        boolean continueProcess = true;
        while (continueProcess) {
            addRow();
            numberOfRows++;
            System.out.println("Do you want to continue? Y/N");
            continueProcess = Database.findDecision();
        }
        // numberOfRows+= insertions;
    }

    public void addRow() {
        System.out.println("#" + (numberOfRows + 1) + " Row: ");
        for (Column column : this.getColumns()) {
            if (this.getColumns().get(0).equals(column)) {
                column.getField().add(numberOfRows + 1);
            } else {
                System.out.println("Give " + column.getName());
                Object data = column.getType().getData();
                if (column.getPrimaryKey()) {
                    column.fillPrimaryKeyField(data);
                } else {
                    column.getField().add(data);
                }
            }
        }

    }

    /**
     * Fill in with Data, Fills by column
     */
    public void columnFillerByColumn() {

        if (numberOfRows == 0) {
            System.out.println("How many records would you like to fill for " + this.getName());
            numberOfRows = Database.valid();
        }
        for (Column column : columns) {
            if (column.getField().isEmpty()) {
                if (column.getPrimaryKey()) {
                    System.out.println("	" + column.getName());
                    System.out.println("-----------------");
                    for (int j = 0; j < numberOfRows; j++) {
                        System.out.print("#" + (j + 1) + " Row: ");
                        Object data = column.getType().getData();
                        column.fillPrimaryKeyField(data);
                    }
                } else if (columns.get(0).equals(column)) {
                    for (int i = 0; i < numberOfRows; i++) {
                        column.getField().add(i + 1);
                        // fill the record column.
                    }
                } else {
                    System.out.println("	" + column.getName());
                    System.out.println("-----------------");
                    for (int j = 0; j < numberOfRows; j++) {
                        System.out.print("#" + (j + 1) + " Row: ");
                        Object data = column.getType().getData();
                        column.getField().add(data);
                    }
                }
            }
            System.out.println();
        }

    }

    public void manageData() {
        boolean continueProcess = true;
        while (continueProcess) {
            Menu.startingMenu();
            int choice = Database.choice(1, 9);
            switch (choice) {
            case 1:
                presentData();
                break;
            case 2:
                changeData();
                break;
            case 3:
                deleteData();
                break;
            case 4:
                sortData();
                break;
            case 5:
                addData();
                break;
            case 6:
                searchData();
                break;
            case 7:
                findMaxData();
                break;
            case 8:
                findMinData();
                break;
            default:
                continueProcess = false;
            }
        }
    }

    public void presentData() {
        int choice;
        boolean continueProcess = true;
        while (continueProcess) {
            Menu.presentationMenu();
            choice = Database.choice(1, 3);
            switch (choice) {
            case 1:
                printAll();
                break;
            case 2:
                printRangeOfRows();
                break;
            case 3:
                printSpecificColumns();
                break;
            default:
                Menu.startingMenu();
            }
            System.out.println("Continue with the presentation of data?");
            continueProcess = Database.findDecision();
        }
    }

    public void changeData() {
        int choice;
        boolean decision;
        do {
            Menu.changeMenu();
            choice = Database.choice(1, 5);
            switch (choice) {

            case (1):
                changeFieldName();
                break;

            case (2):
                changeValue();
                break;

            /*
             * case(3): changeDataByColumn(); break;
             */

            case (3):
                changeDataByRow();
                break;

            case (4):
                sameValue();
                break;
            case (5):
                Menu.startingMenu();

            }
            System.out.println("Continue with the changing of data? Yes/No");
            decision = Database.findDecision();
        } while (decision);
    }

    public void deleteData() {
        int choice;
        boolean continueProcess = true;
        while (continueProcess) {
            Menu.deletionMenu();
            choice = Database.choice(1, 4);
            switch (choice) {
            case 1:
                deleteRows();
                break;
            case 2:
                deleteColumns();
                break;
            case 3:
                deleteElements();
                break;
            case 4:
                deleteAll();
                break;
            }
            System.out.println("Deletion completed successfully");
            System.out.println("Continue with the deletion of data?");
            continueProcess = Database.findDecision();
        }
    }

  public void sortData() {
    int choice;
    boolean continueProcess = true;
    while (continueProcess) {
        Menu.assortmentMenu();
        choice = Database.choice(1, 2);
        switch (choice) {
        case 1:
            sortInAlphabeticalOrder();
            break;
        case 2:
            break;

            }
            System.out.println("Assortment completed successfully");
            System.out.println("Continue with the assortment of data?");
            continueProcess = Database.findDecision();
        }
    }

    public void addData() {
        int choice;
        boolean continueProcess = true;
        while (continueProcess) {
            Menu.additionMenu();
            choice = Database.choice(1, 2);
            switch (choice) {
            case 1:
                columnFillerByRow();
                break;
            case 2:
                setFieldNames();
                callFiller();
                break;
            }
            System.out.println("Add completed successfully");
            System.out.println("Continue with the adding of data?");
            continueProcess = Database.findDecision();
        }
    }

    public void searchData() {
        Menu.searchingMenu();
        Boolean answer = Database.findDecision();
        Boolean continueProcess = true;
        do {
            if (answer) {
                int pfield = inputFieldName("to search for element");
                if (pfield != -1) {
                    Column col = getColumns().get(pfield);
                    System.out.println("Enter the element you want to search");
                    Object element = col.getType().getData();
                    col.searchElement(element);
                }
            }
            System.out.println("Search Data completed successfully");
            System.out.println("Continue with the searching of data?");
            continueProcess = Database.findDecision();
        } while (continueProcess);
    }

    public void findMaxData() {
        Boolean continueProcess = true;
        do {
            int pfield = inputFieldName("find the maximum value");
            if (pfield != -1) {
                Column col = this.getColumns().get(pfield);
                Object max = Collections.max(col.getField(), null);
                System.out.println("The maximum value is : " + max);
                System.out.println("at row : " + (col.getField().indexOf(max) + 1));
            }
            System.out.println("Finding Maximum Data completed successfully");
            System.out.println("Continue with the finding Maximum Data?");
            continueProcess = Database.findDecision();
        } while (continueProcess);
    }

    public void findMinData() {
        Boolean continueProcess = true;
        do {
            int pfield = inputFieldName("find the minimum value");
            if (pfield != -1) {
                Column col = this.getColumns().get(pfield);
                Object min = Collections.min(col.getField(), null);
                System.out.println("The minimum value is : " + min);
                System.out.println("at row : " + (col.getField().indexOf(min)) + 1);
            }
            System.out.println("Finding Minimum Data completed successfully");
            System.out.println("Continue with finding Minimum Data?");
            continueProcess = Database.findDecision();
        } while (continueProcess);
    }

    /**
     * prints all table insertions and the titles of the attributes
     */
    public void printAll() {
        if (numberOfRows != 0) {
            System.out.println();
            System.out.println("Table: " + this.name);
            printHeader();
            for (int k = 0; k < numberOfRows; k++) {
                presentRow(k);
            }
        } else {
            System.out.println("No records in this table");
        }
    }

    /**
     * Prints the header with the title of all the fields
     */
    public void printHeader() {
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < columnCounter; i++) {
            Column column = columns.get(i);
            list.add(column.getName());
        }
        printHeaderOfSpecificColumns(list);
    }

    // Prints one row of the table.
    public void presentRow(int row) {
        for (int i = 0; i < columnCounter; i++) {
            Column column = columns.get(i);
            if (i == 0) {
                System.out.print(String.format("|%-6s|", columns.get(0).getField().get(row).toString()));
                System.out.print("     ");
            } else {
                column.printElement(row);
            }
        }
        System.out.println();
    }

	/**
	*Present specific rows within a range given by the user
	*/
	public void printRangeOfRows() {
		if (numberOfRows != 0) {
			System.out.println("Please insert the range of rows you want to print.");
			int start = 1;
			int end = 0;
			while (start >= end) {
				System.out.println("Starting row: ");
				start = Database.choice(1, numberOfRows)-1;
				System.out.println("Ending row: ");
				end = Database.choice(1,numberOfRows);
				if (start >= end) {
					System.out.println("Starting can't be greater than ending row");
				}
			}
			printHeader();
			for (int i = start; i < end; i++) {
				presentRow(i);
			}
		} else {
			System.out.println("No records in this table");
		}
	}

	public void specificRows(ArrayList<Integer> rows) {
		printHeader();
		for (Integer row : rows) {
			presentRow(row);
		}
	}

	 /**
	  * Gets the names of the attributes by the user and puts them in an ArrayList
	  *
	  * @return the ArrayList of the attributes given by the user
	  */
	public ArrayList <String> inputSpecificColumns() {
		ArrayList <String>  attributes = new ArrayList<String>();
		attributes.add("Record");
		boolean continueProcess = true;
		String attribute;
		int position = -1;

		if (numberOfRows != 0) {
			while (continueProcess)	{
				position = inputFieldName("be presented");
				if ( position != -1) {
					Column col = columns.get(position);
					attributes.add(col.getName());
				}
					System.out.println("\nDo you want to present another column? Y/N");
					continueProcess = Database.findDecision();

			}
		} else  {
			System.out.println("\nNo records in this table.\n");
		}
		return attributes;
	}

	/**
	*Prints columns according to a list given by the user
	*/
	public void printSpecificColumns() {
		ArrayList<String> attributes = inputSpecificColumns();
		if (attributes.size() == 1) {
			System.out.println("No records in this table.\n");
		} else {
			this.presentColumns(attributes);
		}
	}

    public void printHeaderOfSpecificColumns(ArrayList<String> attributes) {
        int spaces = 0;
        String title = "";
        for (String attribute : attributes) {
            if (attribute.equals("Record")) {
                title = String.format("|%-6s|", attribute);
            } else {
                title = String.format("|%-15s|", attribute);
            }
            System.out.print(title);
            spaces = spaces + title.length() + 5;
            System.out.print("     ");
        }
        System.out.println();
        for (int i = 0; i < spaces - 5; i++) {
            System.out.print("-");
        }
        System.out.println();
    }

    /**
     * Checks by name, if a Column exists in a Table If the column exists, returns
     * its position at ArrayList columns. If it doesn't exist, returns -1
     *
     * @param name
     *            of this field
     * @returns int position
     */
    public int containsName(String name) {
        if (name.equals("Record")) {
            ;
            return -1;
        }
        for ( int i = 0; i < columnCounter; i++) {
			Column c = getColumns().get(i);
            if (c.getName().equals(name)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * present columns according to a list of attributes
     *
     * @param attributes
     *            the list of the attributes
     */
    public void presentColumns(ArrayList<String> attributes) {
        printHeaderOfSpecificColumns(attributes);
        for (int i = 0; i < numberOfRows; i++) {
            for (String a : attributes) {
                Column column = columns.get(0);
                if (a != "Record") {
                    column = columns.get(containsName(a));
                    column.printElement(i);
                } else {
                    System.out.print(String.format("|%-6s|", columns.get(0).getField().get(i).toString()));
                    System.out.print("     ");
                }
            }
            System.out.println();
        }
    }

    // Input name of field to change and check for existance.

    public int inputFieldName(String function) {
        System.out.println("Give the name of the field you want to " + function + ".");
        StringType name = new StringType();
        String nameofField = name.getData();
        int ex = this.containsName(nameofField);
        if (ex == -1) {
            System.out.println("This name of field doesn't exist in your Data Base");
            System.out.println("Do you want to try again?");
            // System.out.println("Answer Yes or No");
            Boolean answer = Database.findDecision();
            if (answer) {
                return inputFieldName(function);
            }
        }
        return ex;
    }

    public void changeDataByRow() {
        System.out.println("Which row do you want to change?");
        printAll();
        int row = Database.choice(1, numberOfRows) - 1;
        if (row != -1) {
            Boolean continueProcess = true;
            int i = 1;
            System.out.println("#" + (row + 1) + " Row: ");
            do {
                Column x = columns.get(i);
                System.out.println("#" + (i) + " Field: ");
                System.out.println("Give the new value of " + x.getName());
                Boolean answer = false;
                do {
                    Object nValue = x.getType().getData();
                    if (x.getPrimaryKey()) {
                        if (x.checkUniqueness(nValue)) {
                            x.getField().set(row, nValue);
                            System.out.println("Change completed successfully");
                            answer = false;
                        } else {
                            System.out.println(
                                    "This value of primary key already exists.Do you want to try again?(Yes/No)");
                            answer = Database.findDecision();
                            if (answer) {
                                System.out.println("Enter new value again :");
                            }
                        }
                    } else {
                        x.getField().set(row, nValue);
                        System.out.println("Change completed successfully.");
                        answer = false;
                    }
                } while (answer);
                if (i <= columnCounter) {
                    System.out.println("Do you want to continue? (Yes/No)");
                    continueProcess = Database.findDecision();
                    if (continueProcess) {
                        i++;
                    } else {
                        i = columnCounter + 1;
                    }
                }
            } while (i < columnCounter);
        } else {
            System.out.println("The row you typed is probably incorrect.");
        }
    }

    public void changeFieldName() {
        StringType name = new StringType();
        int pos = this.inputFieldName("change");
        Boolean answer = true;
        if (pos != -1) {
            Column col = this.getColumns().get(pos);
            while (answer) {
                System.out.println("Give the new name of the field");
                String newName = name.getData();
                int k = this.containsName(newName);
                if (k == -1) {
                    col.setName(newName);
                    answer = false;
                    System.out.println("Change completed successfully");
                } else {
                    System.out.println("This name is already in use.");
                    System.out.println("Do you want to try again?");
                    answer = Database.findDecision();
                }
            }
        }
    }

    /*
     * search if there is a PrimaryKey Column. Then calls method primaryKeyColumn.
     * attribute: position of field to be changed.
     */

    public int findPrimaryKeyColumn() {
        int j = 0;
        int exprimarykey = -1;
        do {
            Column col = columns.get(j);
            if (col.getPrimaryKey()) {
                exprimarykey = j;
            } else {
                j++;
            }
        } while (exprimarykey == -1 && j < columnCounter);
        return exprimarykey;
    }

    public int findForeignKeyColumn() {

        int posF = -1;
        boolean cont = false;

        for (Column c : columns) {
            if (c.getForeignKey()) {
                return posF = columns.indexOf(c);
            }
        }
        return -1;
    }

    public boolean primaryKeyColumnExists() {
        boolean exists = false;
        for (Column column : columns) {
            if (column.getPrimaryKey()) {
                exists = true;
            }
        }
        return exists;
    }

    /*
     * if a column with primary keys exists, keeps the position in table and calls
     * informUser. Else make a new list in table with increased number that it will
     * be a primary key list and calls informUser()* attributes: position of of
     * primarykey list position of field to be changed
     */

    /*
     * public int primaryKeyColumn(int exprimaryKey) { if(exprimaryKey == -1) {
     * exprimaryKey =createIncreasedNumber(); } return informUser(exprimaryKey); }
     */

    /*
     * if a column with primary keys exists, keeps the position in table and calls
     * informUser. Else make a new list in table with increased number that it will
     * be a primary key list and calls informUser()
     */

    public int informUser(int ex) {
        Column col = columns.get(ex);
        System.out.println("This is your Database :");
        this.printAll();
        // Inform him which list is primary key.
        System.out.print("This is the list with the primary keys of your elements ");
        System.out.println(col.getName());
        System.out.println("Type the primary key of element you want to change");
        Object searchKey = col.getType().getData();
        if (!col.getField().contains(searchKey)) {
            System.out.println("The primary key you typed doesn't exist.");
            System.out.println("Do you want to try again?");
            // System.out.println("Answer Yes or No");
            Boolean answer = Database.findDecision();
            if (answer) {
                return informUser(ex);
            }
        }
        return col.getField().indexOf(searchKey); // position of primary key in list.
    }

    public void changeValue() {
        int pfield = inputFieldName("change");
        if (pfield != -1) {
            System.out.println("Which row do you want to change?");
            printAll();
            int row = Database.choice(1, numberOfRows) - 1;
            if (row != -1) {
                Column x = columns.get(pfield);
                System.out.println("Enter the new value of element you want to change :");
                Boolean answer = false;
                do {
                    Object nValue = x.getType().getData();
                    if (x.getPrimaryKey()) {
                        if (x.checkUniqueness(nValue)) {
                            x.getField().set(row, nValue);
                            System.out.println("Change completed successfully");
                            answer = false;
                        } else {
                            System.out.println(
                                    "This value of primary key already exists. Do you want to try again?(Yes/No)");
                            answer = Database.findDecision();
                            if (answer) {
                                System.out.println("Enter new value again :");
                            }
                        }
                    } else {
                        x.getField().set(row - 1, nValue);
                        System.out.println("Change completed successfully.");
                        answer = false;
                    }
                } while (answer);
            } else {
                System.out.println("The row you typed is probably incorrect.");
            }
        }
    }

    public void sameValue() {
        int pfield = inputFieldName("change");
        if (pfield != -1) {
            Column col = this.getColumns().get(pfield);
            System.out.println("Insert the new value of all elements");
            Object newValue = col.getType().getData();
            for (int i = 0; i < col.getField().size(); i++) {
                col.getField().set(i, newValue);
            }
            printAll();
        }
    }

    public void deleteRows() {
        System.out.println(String.format("%s\n%s\n", "1. Delete Specific Rows", "2. Delete specific range of rows"));
        int choice;
        choice = Database.choice(1, 2);
        switch (choice) {
        case 1:
            deleteSpecificRows();
            break;
        case 2:
            deleteSpecificRangeofRows();
            break;
        }
    }

    /** deletes any row you want(one or more) */
    public void deleteSpecificRows() {
        boolean continueProcess = true;
        while (continueProcess) {
            System.out.println("Which row do you want to delete?");
            int x = Database.choice(1, numberOfRows);
            for (int k = 0; k < columnCounter; k++) {
                Column column = columns.get(k);
                column.getField().remove(x - 1);
            }
            numberOfRows--;
            for (int i = 0; i < numberOfRows; i++) {
                columns.get(0).getField().set(i, i + 1);
            }
            printAll();
            System.out.println("Delete another row?");
            continueProcess = Database.findDecision();
        }
    }

    /**
     * deletes specific rows, which are within a range given by the user
     */
    public void deleteSpecificRangeofRows() {
        System.out.println("Please insert the range of rows you want to delete.");
        int startRow;
        int endRow;
        do {
            System.out.println("Starting row: ");
            startRow = Database.choice(1, numberOfRows) - 1;
            System.out.println("Ending row: ");
            endRow = Database.choice(1, numberOfRows);
            if (startRow > endRow) {
                System.out.println("Starting can't be greater than ending row");
            }
        } while (startRow > endRow);

        for (int i = 0; i < getColumnCounter(); i++) {
            Column column = columns.get(i);
            for (int j = startRow; j < endRow; j++) {
                column.getField().remove(j);
            }
        }
        numberOfRows = numberOfRows - (endRow - startRow);
        for (int i = 0; i < numberOfRows; i++) {

            columns.get(0).getField().set(i, i + 1);
        }

    }

    public void setDeleteCounter() {
        setColumnCounter(columnCounter - 1);
    }

    public int checkOffLimitsColumns() {
        int x;
        do {
            x = Database.valid();
            if ((x > columnCounter - 1) || (x < 0)) {
                System.out.println("The number that you gave was off limits.\nPlease try again:");
            }
        } while ((x > columnCounter - 1) || (x < 0));
        return x;
    }

    /**
     * deletes any columns you want
     */
    public void deleteColumns() {
        Scanner cs = new Scanner(System.in);
        System.out.println("How many columns do you want to delete?");
        int x = checkOffLimitsColumns();
        for (int i = 0; i < x; i++) {
            System.out.print("Please give me the name of the column you want to delete: ");
            boolean cont = false;
            while (!cont) {
                String y = cs.next();
                while (y.equals("Row")) {
                    System.out.println("You can't delete this field. Please try again");
                    y = cs.next();
                }
                for (int k = 1; k < columnCounter; k++) {
                    Column column = columns.get(k);
                    if (y.equals(column.getName())) {
                        setDeleteCounter();
                        columns.remove(k);
                        cont = true;
                    }
                }
                if (columnCounter == 1) {
                    columns.remove(0);
                }
                if (!cont) {
                    System.out.println("The name you inserted is not valid. Please try again.");
                }
            }
        }
    }

    /**
     * deletes any element you want
     */
    public void deleteElements() {
        Scanner cs = new Scanner(System.in);
        boolean continueProcess = true;
        while (continueProcess) {
            int x;
            do {
                System.out.println("Please insert the name of the column in which the element exists: ");
                String name = cs.next();
                x = containsName(name);
            } while (x == -1);
            Column column = this.getColumns().get(x);
            System.out.println("Please insert the row in which the element exists: ");
            int y = Database.choice(1, numberOfRows);
            column.getField().set(y - 1, " ");
            System.out.println("Do you want to continue? Y/N");
            continueProcess = Database.findDecision();
        }
    }

    /* deletes a whole table */
    public void deleteAll() {
        for (int i = columnCounter - 1; i >= 0; i--) {
            columns.remove(i);

        }
        columnCounter = 0;
        numberOfRows = 0;
    }

    /**
     * Compares two given datas(Strings) if the Strings are equal returns 0 if the
     * first given String is greater than the second returns a positive number if
     * the first given String is less than the second returns a negative number
     */
    public int compareTo(String str1, String str2) {
        return str1.compareTo(str2);
    }

    /**
     * changes the data in order to create a table in ascesing order
     */
    public void sortInAscendingOrder(int j, Object s1, Object s2, Column column1) {
        Object temp = s2;
        column1.getField().set(j, s1);
        column1.getField().set(j - 1, temp);
    }

    /**
     * changes the data in order to create a table in descending order
     */
    public void sortInDescendingOrder(int j, Object s1, Object s2, Column column1) {
        Object temp = s1;
        column1.getField().set(j - 1, s2);
        column1.getField().set(j, temp);
    }

    /**
     * Sorts the table based on a column given by the user
     */
    public void sortInAlphabeticalOrder() {
        Scanner cs = new Scanner(System.in);
        int x;
        do {
            System.out
                    .println("Please insert the name of the column on which you want the assortment to be based on: ");
            String name = cs.next();
            x = containsName(name);
        } while (x == -1);
        System.out.println("Sort in ascending or descending order?\n1.Ascending order\n2.Descending order");
        int choice;
        choice = Database.choice(1, 2);
        Column column = this.getColumns().get(x);
        if (column.getType() instanceof StringType) {
            for (int i = 1; i < numberOfRows; i++) {
                for (int j = numberOfRows - 1; j >= i; j--) {
                    Object str1 = column.getField().get(j - 1);
                    Object str2 = column.getField().get(j);
                    String st1 = String.valueOf(str1);
                    String st2 = String.valueOf(str2);
                    int result = compareTo(st1, st2);
                    for (int k = 1; k < columnCounter; k++) {
                        Column column1 = this.getColumns().get(k);
                        Object s1 = column1.getField().get(j - 1);
                        Object s2 = column1.getField().get(j);
                        if (choice == 1) {
                            if (result > 0) {
                                sortInAscendingOrder(j, s1, s2, column1);
                            }
                        } else {
                            if (result < 0) {
                                sortInDescendingOrder(j, s1, s2, column1);
                            }
                        }
                    }
                }
            }
        }
    }

    public void printPrimaryKeyColumn() {
        if (primaryKeyColumnExists()) {
            System.out.println("Table: " + name + "");
            int pos = findPrimaryKeyColumn();
            ArrayList<String> print = new ArrayList<String>();
            print.add(columns.get(pos).getName());
            presentColumns(print);
        }
    }
}
