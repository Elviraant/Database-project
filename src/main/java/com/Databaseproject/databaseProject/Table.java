
/**
Represents a table of our database.
*/
//package com.databaseProject.Databaseproject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.io.Serializable;
import java.util.Collections;


public class Table implements Serializable {
    private ArrayList<Column> columns = new ArrayList<>();
    private int columnCounter = 0;
    private String name;
    private int numberOfRows = 0;
    private boolean references = false;
    private HashMap<Table, Integer> positionOffFk = new HashMap<Table, Integer>();
	private HashMap<Integer, Table> invPositionOffFk = new HashMap<Integer, Table>();
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

    public HashMap<Integer, Table> getInvPositionOffFk() {
	    return invPositionOffFk;
	}

	public void setInvPositionOffFk(Integer value, Table key) {
	    invPositionOffFk.put(value, key);
    }

    public boolean getReferences() {
	    return references;
	}

	public void setReferences(boolean references) {
	    this.references = references;
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

	/*
	 * Set the Column's names until the user insert EXIT
	 * Method setFieldNames() calls setFieldType(String) to create a Column object
	 * Returns nothing
	 */
    public void setFieldNames() {

        System.out.println("Set the name of the field that you want to create\nEnter EXIT to stop");
		String nameOfField = nameAColumn();
		nameOfField = uniqueFieldName(nameOfField);
		 while (!nameOfField.equals("EXIT")) {
			Scanner sc = new Scanner(System.in);
			setFieldType(nameOfField);
			nameOfField = nameAColumn();
			nameOfField = uniqueFieldName(nameOfField);
		}
        System.out.println();
    }

	/*
	 * User decide the type for Column FieldType type and create a Column object
	 * Method calls the fieldTypesMenu() and if choice is 1, 2 or 3 create the Column object
	 * If choice is number 4, the Column object is created, but first method calls defineEnumeration() method
	 * Returns nothing
	 * @param nameOfField Column object's name
	 */
    public void setFieldType(String nameOfField) {

		System.out.println("Please choose one of the following data types:");
		System.out.println();
		int choice = Menu.fieldTypesMenu();

		if (choice == 1 || choice == 2 || choice == 3) {
		    FieldType type = Column.findType(choice);
		    new Column(nameOfField, type, this);
		} else if (choice == 4) {
		    EnumeratedType type = new EnumeratedType();
		    type.defineEnumeration();
		    new Column(nameOfField, type, this);
        }
	}

	/*
	 * Set one Column object's name
	 * @return String Column object's name
	 */
	public String nameAColumn() {
		Scanner sc = new Scanner(System.in);
        System.out.print("#" + columnCounter + " Field Name: ");
        String nameOfField = sc.next();
        System.out.println();
        return nameOfField;
	}

   /*
   	* Checks, if the Column object's name is unique in this Table
   	* If it is not, user will insert again a name until it is unique in this Table
   	* @param nameOfField name for checking
   	* @return String tested name
   	*/
	public String uniqueFieldName(String nameOfField) {
		Scanner sc = new Scanner(System.in);
		int check = this.containsName(nameOfField);
		while (check != -1) {
			System.out.println("That name already exists. Please try with another: ");
			nameOfField = sc.next();
			check = this.containsName(nameOfField);
		}
		return nameOfField;
	}

   /*
    * Adds a Column object, that is primary key, in a Table object
    * Calls nameAColumn() method to have a Column name
    * Calls uniqueFieldName(String) to check if this name is unique
    * Calls setFieldType(String) to create the right Column object
    * Sets Column objects as a primary key -column object is the last element in ArrayList columns at this moment-
    * Calls columnFillerByColumn() to fill the new Column object
    * @returns Table same Table with a Primary key Column object
    */
	public Table addPrimaryKeyColumn() {
		String name = nameAColumn();
		uniqueFieldName(name);
		setFieldType(name);
		columns.get((columnCounter - 1)).setPrimaryKey(true);
		columnFillerByColumn();
		return this;
	}


    /*
     * Defines, which Column is the primary Key field Checks, if the name of a field
     * exists at this Table If it exists, sets the boolean instance variable true
     * Returns nothing
     */
    public void definePrimaryKey() {
        Scanner cs = new Scanner(System.in);
        System.out.println("Do you want to set a field as primary key? ");
        boolean continueProcess;
        continueProcess = Database.findDecision();
        while (continueProcess) {
    	    System.out.print("Please, insert the name of the primary key field: ");
            String primaryKeyName = cs.next();
            int exists = this.containsName(primaryKeyName);

            if (exists != -1) {
				if(checkOwnType(primaryKeyName)) {
					continueProcess = true;
					System.out.println("Own type Column can not be Primary Key. Please try again. ");
				} else {
					this.getColumns().get(exists).setPrimaryKey(true);
					continueProcess = false;
				}
            } else {
                System.out.print(primaryKeyName + ". No such field at this entity.");
                System.out.print("Do you want to try again? ");
                continueProcess = Database.findDecision();
            }
		}

    }

	/*
	 * Checks by name, if Column object's type is EnumeratedType
	 * @param name name of Column object
	 * @return boolean true if this Column object's type is EnumeratedType; false otherwise
	 */
    public boolean checkOwnType(String name) {
		boolean ownType = false;
		for(Column column : columns) {
			if (column.getName().equals(name)) {
				if(column.getType() instanceof EnumeratedType) {
					ownType = true;
				}
			}
		}
		return ownType;
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
        System.out.println("#" + (numberOfRows + 1) + " Record: ");
        for (int i = 0; i < columnCounter; i++) {
			Column column = getColumns().get(i);
            if (this.getColumns().get(0).equals(column)) {
                column.getField().add(numberOfRows + 1);
            } else {
                System.out.println("Give " + column.getName());
                Object data = column.getType().getData();
                if (column.getPrimaryKey()) { //if is primary key
                    column.fillPrimaryKeyField(data);
				} else if (column.getForeignKey()) { //is foreign key
					Column prKeyColumn = matchingKeys(i);
					if (column.getPrimaryKey()) { //one to one correlation
					//method to check uniqueness of data in one to one
					//add data in field
					} else if (column.getForeignKeys().size() != 0) { //many to many correlation
					//method to check uniqueness of data in many to many
					//add data in arraylist of foreign keys
					} else { //one to many correlation
					//same with one to one without checking uniqueness of data in field
					}
                } else {
                    column.getField().add(data); //neither foreign, nor primary
                }
            }
        }

    }

    public Column matchingKeys(int position) {
		Column prKey = null;
		for (int key : invPositionOffFk.keySet()) {
			if (key == position) {
				Table table = invPositionOffFk.get(key);
				int pkpos = table.findPrimaryKeyColumn();
				prKey = table.getColumns().get(pkpos);
			}
		}
		return prKey;
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
                        System.out.print("#" + (j + 1) + " Record: ");
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
                        System.out.print("#" + (j + 1) + " Record: ");
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
			if (numberOfRows > 0) {
        	    int choice = Menu.startingMenu();
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
        	    	moreOptions();
        	    	break;
        	    default:
        	    	continueProcess = false;
				}
			} else {
				continueProcess = false;
				System.out.println("This table is empty");
			}
		}
	}

    public void moreOptions() {
        boolean continueProcess = true;
        while (continueProcess) {
			if (numberOfRows > 0) {
            	int choice = Menu.moreOptionsMenu();
            	switch (choice) {
				case 1:
            	    sortData();
            	    break;
            	case 2:
            	    addData();
            	    break;
            	case 3:
            	    searchData();
            	    break;
            	case 4:
            	    findMaxData();
            	    break;
            	case 5:
            	    findMinData();
            	    break;
            	default:
            	    continueProcess = false;
				}
            } else {
				continueProcess = false;
				System.out.println("This table is empty.");
			}
        }
    }


    public void deleteData() {
        int choice;
        boolean continueProcess = true;
        while (continueProcess) {
			if (numberOfRows > 0) {
            	Menu.deletionMenu();
            	choice = Database.choice(1, 5);
            	switch (choice) {
            	case 1:
            		if (! references) {
            		    deleteRows();
            		 	if (numberOfRows>0) {
            		    System.out.println("Deletion completed successfully");
						}
					} else {
						System.out.println("This table references another table of your base");
					}
            	    break;
            	case 2:
            		printAll();
            	    deleteColumns();
            	    break;
            	case 3:
            		printAll();
            	    deleteElements();
            	    break;
            	case 4:
            		printAll();
            	    deleteAll();
            	    break;
            	case 5:
					manageData();
					break;
            	}
            	if ((choice>=1) && (choice<5)) {
					if (numberOfRows>0) {
					System.out.println("Continue with the deletion of data?");
					continueProcess = Database.findDecision();
					}
				} else {
					continueProcess = false;
				}
			} else {
				continueProcess = false;
			}
        }
    }


    public void presentData() {
        int choice;
        boolean continueProcess = true;
        while (continueProcess) {
			if (numberOfRows > 0) {
            	Menu.presentationMenu();
            	choice = Database.choice(1, 4);
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
				case 4:
					manageData();
					break;
            	}
				if ((choice>=1) && (choice<4)) {
					System.out.println("Continue with the presentation of data?");
					continueProcess = Database.findDecision();
				} else {
					continueProcess = false;
				}
			} else {
				continueProcess = false;
				System.out.println("This table is empty");
			}
        }
    }

	/* *checks if input is valid.
	   *if it is, calls the suitable method.
	   *When process done, asks user if he wants to continue.
	   *if it is not, the process is repeated until input is valid.
	*/
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

            case (3):
            	printAll();
                changeDataByRow();
                break;

            case (4):
            	printAll();
                sameValue();
                break;
            case (5):
                manageData();
				break;
            }
			if ((choice>=1) && (choice<5)) {
				System.out.println("Continue with the changing of data? Yes/No");
				decision = Database.findDecision();
			} else {
				decision = false;
			}
        } while (decision);
    }

  	public void sortData() {
		int choice;
		boolean continueProcess = true;
		while (continueProcess) {
			Menu.assortmentMenu();
			choice = Database.choice(1, 2);
			switch (choice)
			{
				case 1:
					printAll();
					chooseSort();
					break;
				case 2:
					manageData();
					break;
			}
			if (choice==1) {
				System.out.println("Assortment completed successfully");
				System.out.println("Continue with the assortment of data?");
				continueProcess = Database.findDecision();
			} else {
				continueProcess = false;
			}
		}
	}

    public void addData() {
        int choice;
        boolean continueProcess = true;
        while (continueProcess) {
            Menu.additionMenu();
            choice = Database.choice(1, 3);
            switch (choice) {
            case 1:
            	System.out.println("Until now, these are your records: ");
            	System.out.println();
            	printAll();
                columnFillerByRow();
                break;
            case 2:
             	System.out.print("Until now, you have these columns: ");
             	System.out.println();
            	printHeader();
            	setFieldNames();
				columnFillerByColumn();
                break;
            case 3:
            	manageData();
            	break;
            }
            if ((choice==1) || (choice==2)){
				System.out.println("Add completed successfully");
				System.out.println("Continue with the adding of data?");
				continueProcess = Database.findDecision();
			} else {
				continueProcess = false;
			}
        }
    }

	/* *search for element given by the user.
	   *if it exists, prints all the positions where it is found.
	   *else prints suitable message.
	*/
    public void searchData() {
        Menu.searchingMenu();
        Boolean answer = Database.findDecision();
        Boolean continueProcess = true;
        do {
        	if (answer) {
                int pfield = inputFieldName("search for element");
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

	/* *find maximum value of an element in a specific field given by user.
	   *prints all the positions that maximum value is found.
	*/
    public void findMaxData() {
        Boolean continueProcess = true;
        do {
            int pfield = inputFieldName("find the maximum value");
            if (pfield != -1) {
                Column col = this.getColumns().get(pfield);
                Object max = Collections.max(col.getField(), null);
                System.out.print("The maximum value is : ");
                col.searchElement(max);
            }
            System.out.println("Finding Maximum Data completed successfully");
            System.out.println("Continue with the finding Maximum Data?");
            continueProcess = Database.findDecision();
        } while (continueProcess);
    }

	/* *find minimum value of an element in a specific field given by user.
	   *prints all the positions that minimum value is found.
	*/
    public void findMinData() {
        Boolean continueProcess = true;
        do {
            int pfield = inputFieldName("find the minimum value");
            if (pfield != -1) {
                Column col = this.getColumns().get(pfield);
                Object min = Collections.min(col.getField(), null);
                System.out.print("The minimum value is : ");
              	col.searchElement(min);
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
           	if (column.getForeignKeys().size() == 0) {
            	list.add(column.getName());
			}
        }
        printHeaderOfSpecificColumns(list);
    }


    // Prints one row of the table.
    public void presentRow(int row) {
        for (int i = 0; i < columnCounter; i++) {
            Column column = columns.get(i);
            if (column.getForeignKeys().size() == 0) {
            	if (i == 0) {
              	  System.out.print(String.format("|%-6s|", columns.get(0).getField().get(row).toString()));
              	  System.out.print("     ");
              	  } else {
                  column.printElement(row);
			  }
            }
        }
        System.out.println();
    }

	/**
	*Present specific rows within a range given by the user
	*/
	public void printRangeOfRows() {
		if (numberOfRows != 0) {
			System.out.println("Please insert the range of records you want to print.");
			int start = 1;
			int end = 0;
			while (start >= end) {
				System.out.println("Starting record: ");
				start = Database.choice(1, numberOfRows)-1;
				System.out.println("Ending record: ");
				end = Database.choice(1,numberOfRows);
				if (start >= end) {
					System.out.println("Starting can't be greater than ending record");
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
            return -1;
        }
        for (int i = 0; i < columnCounter; i++) {
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

  /*  *Input name of field by user.
      *Checks for existance.
      *If exists, method returns field's position in Table.
      *If it does not exist, prints suitable message.
      *If user wants to try again, process of input name of field is repeated.
      *@param String variable : states the process to be done in field.
   */
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

	/* *Change values of elements by row.
	   *Process stops, if user types suitable string('no').
	   *or if there are no other columns to be processed.
	 */
    public void changeDataByRow() {
        System.out.println("Which record do you want to change?");
        printAll();
        int row = Database.choice(1, numberOfRows) - 1;
        if (row != -1) {
            Boolean continueProcess = true;
            int i = 1;
            System.out.println("#" + (row + 1) + " Record: ");      //checks if record input is valid and keeps position of element.
            do {
                Column col = columns.get(i);
                System.out.println("#" + (i) + " Field: ");
                System.out.println("Give the new value of " + col.getName());
                Boolean answer = false;
                do {
                    Object nValue = col.getType().getData();
                    if (col.getPrimaryKey()) {                         //checks if field contains primary keys.
                        if (col.checkUniqueness(nValue)) {           //checks if new value is unique.
                            col.getField().set(row, nValue);
                            System.out.println("Change completed successfully");
                            answer = false;
                        } else {
                            System.out.println("This value of primary key already exists.Do you want to try again?(Yes/No)");
                            answer = Database.findDecision();
                            if (answer) {
                                System.out.println("Enter new value again :");
                            }
                        }
                    } else {
                        col.getField().set(row, nValue);
                        System.out.println("Change completed successfully.");
                        answer = false;
                    }
                } while (answer);
                if (i != columnCounter-1) {
                    System.out.println("Do you want to continue? (Yes/No)");
                    continueProcess = Database.findDecision();
                    if (!continueProcess) {
                        i = columnCounter;
                    }
                }
               	i++;
            } while (i <= columnCounter-1);
        } else {
            System.out.println("The record you typed is probably incorrect.");
        }
    }


	/* *change name of field given by user.
	   *checks if the given field exists in Table.
	   *if it does not, prints suitable message.
	   *Process is repeated until user types existing field name
        or until types "no".
	   *if name of field is valid, checks if the new name is unique.
	   *if name is not unique, prints suitable message and asks user to try again.
	*/
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

   /*  *search if there is a Field with primary keys.
   	   *if exists, returns field's position in Table.
   	   *else returns -1.
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


	/* *if name of field is valid, change specific value of an element in given field.
	   *asks user to input which record(row) is the element that wants to change.
	   *if record is valid, asks user to type new value.
	   *if new value is valid, change is done.
	 */
    public void changeValue() {
        int pfield = inputFieldName("change");
        if (pfield != -1) {
            System.out.println("Which record do you want to change?");
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
                        x.getField().set(row, nValue);
                        System.out.println("Change completed successfully.");
                        answer = false;
                    }
                } while (answer);
            } else {
                System.out.println("The record you typed is probably incorrect.");
            }
        }
    }

	/* *if name of field is valid,replace all values of elements of field with same value.
	   *if field contains primary keys, method does not allow this process to be done.
	*/
	public void sameValue() {
		int pfield = inputFieldName("change");
        if (pfield != -1) {
			Column col = this.getColumns().get(pfield);
			if(col.getPrimaryKey()) {
				System.out.println("This field contains Primary Keys. You are not allowed to set same values.");
			}else {
            	System.out.println("Insert the new value of all elements");
            	Object newValue = col.getType().getData();
            	for (int i = 0; i < col.getField().size(); i++) {
            	    col.getField().set(i, newValue);
           	 	}
            	System.out.println("Change completed successfully !");
	   	}
	   	}
    }

    public void deleteRows() {
		if (numberOfRows!=0){
        System.out.println(String.format("%s\n%s\n", "1. Delete Specific Records", "2. Delete specific range of records"));
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
		} else {
			System.out.println("No records to delete.");
		}
    }

	/**
	* deletes one row
	*/
	public void deleteRow(int row) {
		for (int k=0; k<columnCounter; k++) {
			Column column = columns.get(k);
			column.getField().remove(row-1);
		}
		numberOfRows--;
		for (int i = 0; i < numberOfRows; i++) {
			columns.get(0).getField().set(i,i+1);
		}
	}

	/** deletes any row you want(one or more)*/
	public void deleteSpecificRows() {
		boolean continueProcess = true;
		int counter =0;
		while (continueProcess) {
			printAll();
			System.out.println("Which record do you want to delete?");
			int x = Database.choice(1,numberOfRows);
			deleteRow(x);
			if (numberOfRows!=0){
				System.out.println("Delete another record?");
				continueProcess = Database.findDecision();
			} else {
				System.out.println("Deletion completed successfully.\nYou can't delete another record");
				continueProcess= false;
			}
		}
	}

	/**
	* deletes specific rows, which are within a range given by the user
	*/
	public void deleteSpecificRangeofRows(){
		System.out.println("Please insert the range of records you want to delete.");
		int startRow;
		int endRow;
		do {
			System.out.println("Starting record: ");
			startRow = Database.choice(1, numberOfRows);
			System.out.println(startRow);
			System.out.println("Ending record: ");
			endRow = Database.choice(1,numberOfRows) ;
			System.out.println(endRow);
			if (startRow > endRow) {
				System.out.println("Starting can't be greater than ending record");
			}
		} while ( startRow> endRow);
			int counter=0;
			for (int j= startRow; j <= endRow; j++) {
				deleteRow(j-counter);
				counter++;
			}
	}

	/** deletes any column you want(one or more)*/
	public void deleteColumns() {
		Scanner cs = new Scanner(System.in);
		boolean continueProcess = true;
		int y = 0;
		while (continueProcess) {
			if ((columnCounter==2) && (primaryKeyColumnExists())) {
				System.out.println("No columns to delete.");
				y=1;
			} else {
			int x =  inputFieldName("delete");
			if (x != -1) {
				Column column = columns.get(x);
				if (column.getPrimaryKey()) {
					System.out.println("This column is primary key. Cannot be deleted!");
				} else if (column.getForeignKey()) {
					System.out.println("This column is reference from another table and can't be deleted");
					System.out.println("To delete this column please delete the correlation between the tables");
				} else {
					columns.remove(x);
					columnCounter--;
					if (!primaryKeyColumnExists()) {
						if (columnCounter == 1) {
							columns.remove(0);
							columnCounter=0;
							System.out.println("You deleted all the columns of the table");
							continueProcess = false;
							y=1;
						}
					} else {
						if (columnCounter ==2) {
							System.out.println("Deletion completed successfully.\nYou can't delete another column.");
							continueProcess = false;
							y=1;
						}
					}
				}
			}
		}
			if (y==0) {
				System.out.println("Delete another column?");
				continueProcess = Database.findDecision();
			} else {
				continueProcess = false;
			}
		}
	}

	/**
	* deletes any element you want
	*/
	public void deleteElements(){
		Scanner cs = new Scanner(System.in);
		boolean continueProcess = true;
		boolean continueProcessPrimaryKey = false;
		while (continueProcess) {
			int x;
			do{
				System.out.println("Please insert the name of the column in which the element exists: ");
				String name = cs.next();
				x = containsName(name);
			} while ( x == -1);
			Column column = this.getColumns().get(x);
			System.out.println("Please insert the row in which the element exists: ");
			int y = Database.choice(1,numberOfRows);
			if (column.getPrimaryKey()) {
				if (!references) {
					System.out.println("This element is a primary key. If you delete it the whole row will be deleted.\nAre you sure you want to continue? Y/N");
					continueProcessPrimaryKey= Database.findDecision();
					if (continueProcessPrimaryKey) {
						deleteRow(y);
					}
				} else {
					Menu.deletionFailed(this);
				}
			} else {
				column.getField().set(y-1, " ");
				System.out.println("Deletion completed successfully");
			}
			System.out.println("Do you want to delete another element? Y/N");
			continueProcess = Database.findDecision();
		}
	}

    /* deletes a whole table */
    public void deleteAll() {
		if (!getReferences()) {
        	for (int i = columnCounter - 1; i >= 0; i--) {
        	    columns.remove(i);

        	}
        	columnCounter = 0;
        	numberOfRows = 0;
        	System.out.println("Deletion completed successfully");
		} else {
			Menu.deletionFailed(this);
		}
    }

   /**
   	* Compares two given datas(Objects)
   	* Converts them to Strings in order to compare them.
   	* If the Objects are equal, returns 0
   	* If the first given Object is greater than the second, returns a positive number
   	* If the first given Object is less than the second, returns a negative number
   	*/
   	public int compareStrings(Object str1, Object str2) {
   			String st1= String.valueOf(str1);
   			String st2 = String.valueOf(str2);
   			return st1.compareTo(st2);
   	}

   	/**
   	* Compares two given datas(Objects)
   	* Converts them to Integers in order to compare them.
   	* If the Objects are equal, returns 0.
   	* If the first given Object is greater than the second, returns 1.
   	* If the first given Object is less than the second, returns -1.
   	*/
   	public int compareIntegers(Object str1, Object str2) {
   			int st1 = (int)str1;
   			int st2 = (int)str2;
   			if (st1 > st2) {
   				return 1;
   			} else if (st1 < st2) {
   				return -1;
   			} else {
   				return 0;
   			}
   	}

   	/**
   	* Compares two given datas(Objects)
   	* Converts them to Double in order to compare them.
   	* If the Objects are equal, returns 0.
   	* If the first given Object is greater than the second, returns a positive number.
   	* If the first given Object is less than the second, returns a negative number.
   	*/
   	public int compareDoubles(Object str1, Object str2) {
   			String st1= String.valueOf(str1);
   			String st2 = String.valueOf(str2);
   			st1 = st1.replace(",",".");
   			st2 = st2.replace(",",".");
   			double st11 =  Double.parseDouble(st1);
   			double st22 =  Double.parseDouble(st2);
   			return Double.compare(st11,st22);
   	}


   	/**
   	* Sorts the table based on a column given by the user
   	* The user seletcs in which order the assortment will be made(ascending or descending)
   	* According to the type of the column, the corresponding method is called in order to compare all the datas of the column.
   	* Then it is called another method, which sorts the whole table based on the the previous comparation.
   	*/
   	public void chooseSort() {
   		Scanner cs = new Scanner(System.in);
   		int x;
   		do{
   			System.out.println("Please insert the name of the column on which you want the assortment to be based on: ");
   			String name = cs.next();
   			x = containsName(name);
   		} while ( x == -1);
   		System.out.println("Sort in ascending or descending order?\n1.Ascending order\n2.Descending order");
   		int choice;
   		choice = Database.choice(1,2);
   		Column column = this.getColumns().get(x);
   			for (int i=1; i<numberOfRows; i++) {
   				for (int j=numberOfRows-1; j>=i; j--) {
   					Object str1 = column.getField().get(j-1);
   					Object str2 = column.getField().get(j);
   					if (column.getType() instanceof StringType) {
   						int result = compareStrings(str1,str2);
   						sort(result,j, choice);
   					}
   					if (column.getType() instanceof IntegerType) {
   						int result = compareIntegers(str1,str2);
   						sort(result,j, choice);
   					}
   					if (column.getType() instanceof DoubleType) {
   						int result = compareDoubles(str1,str2);
   						sort(result,j, choice);
   					}
   				}
   			}
   	}

   	public void sort(int result, int j, int choice) {
   		for (int k=1; k<columnCounter; k++) {
   			Column column1 = this.getColumns().get(k);
   			Object s1 = column1.getField().get(j-1);
   			Object s2 = column1.getField().get(j);
   			if (choice ==1) {
   				if (result > 0) {
   					column1.sortInAscendingOrder(j,s1,s2);
   				}
   			} else {
   				if (result<0) {
   					column1.sortInDescendingOrder(j, s1,s2);
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
