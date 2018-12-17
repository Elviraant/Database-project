/**
Represents a table of our database.
*/
import java.util.ArrayList;
import java.util.Scanner;

public class Table {
	private ArrayList<Column> columns = new ArrayList<>();
	private int columnCounter = 0;
	private String name;
	private int numberOfRows;

	/**
	*Constructor for Table class
	* @param columns arraylist with objects of Column
	* @param columnCounter number of columns in this table
	* @param name name of this table
	* @param numberOfRows number of row insertions from user
	*/
	 public Table(String name) {
	    this.name = name;
	    Database.getTables().add(this);
	    int counter = Database.getTableCounter();
	    counter++;
	    Database.setTableCounter(counter);
    }

    Scanner cs = new Scanner(System.in);

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

	/**
	*create field names and call method findType to create the arrays
	*/

	public void setFieldNames() {
		System.out.println("Set the names of the fields that you want to create?\nEnter EXIT to stop");
		int counter = 1;
		System.out.print("#" +  counter + " Field Name: ");
		String nameOfField = cs.next();
		System.out.println();

		while (!nameOfField.equals("EXIT")) {

			System.out.println("Please choose one of the following data types:");
			System.out.println();
			System.out.println("1. Integer\n2. Double\n3. Text\n4. Own Type" );

			int choice = Database.choice(1,4);
			if (choice == 1 || choice == 2 || choice == 3) {
				FieldType type = Column.findType(choice);
				new Column(nameOfField, type, this);
			} else if (choice == 4) {
				EnumeratedType type = new EnumeratedType();
				type.defineEnumeration();
				new Column(nameOfField, type, this);
			}
			counter++;
			System.out.println();
			System.out.println("Please insert name of the next field");
			System.out.print("#" +  counter + " Field Name: ");
			nameOfField = cs.next();
		}
		System.out.println();
	}

	/**
	*ask how to fill the fields and call method columnFillerByRow or columnFillerByCollumn
	*/
	public void callFiller() {
		System.out.println("How would you like to fill the table?");
		System.out.println("1. By row\n2. By column");
		int filltype = cs.nextInt();
		while (!((filltype == 1) || (filltype == 2))) {
				System.out.print("select 1 or 2: ");
				filltype = cs.nextInt();
		}
		if (filltype == 1) {
			this.columnFillerByRow();
		}else if (filltype == 2) {
			this.columnFillerByColumn();
		}
		System.out.println("");
	}

	/**
	*Fill in with Data, Fills by row//
	*/
	public void columnFillerByRow() {
		//System.out.println("mpike sthn filler by row");
		boolean continueProcess = true;
		int insertions = 0;
		while (continueProcess) {
			System.out.println("#" + (insertions + 1) + " Row: ");
			/****for (int i = 0; i < this.getColumnCounter(); i++) {
				Column column = this.getColumns().get(i);
				System.out.println("Give "+ column.getName());
				Object data = column.getType().getData();
				column.getField().add(data);
			} ****/
			for(Column column : this.getColumns()) {
				System.out.println("Give "+ column.getName());
				Object data = column.getType().getData();
				column.getField().add(data);
			}
			insertions++;
			System.out.println("Do you want to continue? Y/N");
			continueProcess = Database.findDecision();
			//if (decision) continueProcess = true; else continueProcess = false;
		}
		 this.setNumberOfRows((insertions + 1));
	}


	/**
	*Fill in with Data, Fills by column
	*/
	public void columnFillerByColumn() {
		for (int i = 0; i < this.getColumnCounter(); i++){
			Column column = this.getColumns().get(i);
			System.out.println("How many cells would you like to fill for " + column.getName());
			int decision = cs.nextInt();
			//System.out.println("Please insert the values for array " + column.getName() + ". Enter EXIT to stop");
			System.out.println("	" + column.getName());
			System.out.println("-----------------");
			//int j = 1;
			for (int j = 0 ; j < decision; j++) {
				System.out.print("#"+ (j+1)+" Row: ");
				Object data = column.getType().getData();
				column.getField().add(data);
			}
			System.out.println();
			//String decision = cs.next();
		/*	while (!decision.equals("EXIT")) {
				j++;
				System.out.print("#"+ j+" Row: ");
				Object data = column.getType().getData();
				column.getField().add(data);
			//	decision = column.getData();
			} */
		}
	}

	public void manageData() {
		boolean continueProcess = true;
		while (continueProcess) {
			Menu.startingMenu();
			int choice = Database.choice(1,4);
			switch (choice)
			{
				case 1:
					presentData();
					break;
				case 2:
					changeData();
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
			choice = Database.choice(1,3);
			switch (choice)
			{
				case 1:
					printAll();
					break;
				case 2:
					printSpecificRows();
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
			choice = Database.choice(1,4);
			switch(choice) {

				case(1): changeFieldName();
										break;


				case(2): changeValue();
									break;


				case(3): changeAllData();
							 		 break;

				case(4): sameValue();
									break;
			}
			System.out.println("Do you want to continue the process of changing data?Yes/No");
			decision = Database.findDecision();
		 }  while (decision);
	}

	/**
	*prints all table insertions and the titles of the attributes
	*/
	public void printAll() {
		System.out.println();
		System.out.println("Table: "+this.name);
		printHeader();
		Column firstColumn = this.columns.get(0);
		for (int k = 0; k < firstColumn.getField().size(); k++) {

			this.presentRow(k);
		}
		System.out.println();
	}

	/**
	*Prints the header with the title of the fields
	*/
	public void printHeader() {
		int spaces = 0;
		String title;
		for (int i = 0; i < this.getColumnCounter(); i++) {
			Column column = this.getColumns().get(i);
			title = String.format("|%-15s|", column.getName());
			System.out.print(title);
			spaces =spaces + title.length() + 5;
			System.out.print("     ");
		}
		System.out.println();
		for (int i = 0; i < spaces - 5; i++) {
			System.out.print("-");
	    }

		System.out.println();
	}

	//Prints one row of the table.
    public void presentRow(int row) {
        for (int i = 0; i < columnCounter; i++) {
		    Column column = columns.get(i);
			column.printElement(row);
		}
		System.out.println();
	}

	/**
	*Present specific rows within a range given by the user
	*/
	public void printSpecificRows() {
		System.out.println("Please insert the range of rows you want to print.");
		int start = startingRow() - 1;
		int end = endingRow();
		this.printHeader();
		for (int i = start; i < end; i++) {
			presentRow(i);
		}
		System.out.println();
	}
	/**
	@return starting row given by the user
	*/
	public int startingRow() {
		System.out.println("Starting row: ");
		int row = cs.nextInt();
		while ((row > numberOfRows-1) || (row < 1)) {
			System.out.println("Your choice is out of boundaries. Please chose another starting row.");
			row = cs.nextInt();
		}
		return row;
	}

	public int endingRow() {
		System.out.println("Ending row: ");
		int row = cs.nextInt();
		while ((row > numberOfRows-1) || (row < 1)) {
			System.out.println("Your choice is out of boundaries. Please chose another ending row.");
			row = cs.nextInt();
		}
		return row;
	}

	public void printHeaderOfSpecificColumns(ArrayList <String> attributes) {
	    int spaces = 0;
	    for (String attribute: attributes ) {
	    	String title = String.format("|%-15s|", attribute);
			System.out.print(title);
			spaces =spaces + title.length() + 5;
			System.out.print("     ");
	    }
	    System.out.println();
	    for (int i = 0; i < spaces - 5; i++) {
			System.out.print("-");
		}
		System.out.println();
	}

	/**
	*Gets the names of the attributes by the user and puts them in an ArrayList
	*@return the ArrayList of the attributes given by the user
	*/
	public ArrayList <String> inputSpecificColumns() {
		ArrayList <String>  attributes = new ArrayList<String>();
		boolean continueProcess = true;
		String attribute;

		while (continueProcess)	{
			System.out.println("Type an attribute that you want print:");
			attribute = cs.next();
			if (this.containsName(attribute) != -1) {
				attributes.add(attribute);
				System.out.println("Do you want to continue? Y/N");
				continueProcess = Database.findDecision();

			} else {
				System.out.println("There's no such attribute.");
				System.out.println("Do you want to continue? Y/N");
				continueProcess = Database.findDecision();
			}
		}
		return attributes;
	}

	/**
	*Prints columns according to a list given by the user
	*/
	public void printSpecificColumns() {
		ArrayList<String> attributes = inputSpecificColumns();
		if (attributes.isEmpty()) {
			System.out.println("There's nothing to be presented.\n");
		} else {
			this.presentColumns(attributes);
		}
	}




    /**
     *	Checks by name, if a Column exists in a Table
     *	If the column exists, returns its position at ArrayList columns.
     *  If it doesn't exist, returns -1
     *  @param name of this field
     *	@returns int position
     */
	public int containsName(String name) {

    	for (Column c: this.columns) {
    	   if (c.getName().equals(name)) {
    		   //System.out.println(this.columns.indexOf(c));
    		   return columns.indexOf(c);

    	   }
    	}
    	return -1;
    }

	/**
	*present columns according to a list of attributes
	*@param attributes the list of the attributes
	*/
    public void presentColumns( ArrayList <String> attributes) {
		this.printHeaderOfSpecificColumns(attributes);
		for (int i = 0; i < numberOfRows -1; i++)  {
			for (String a: attributes) {
				Column column = columns.get(this.containsName(a));
		        column.printElement(i);
            }
        System.out.println();
        }
	}

	//Input name of field to change and check for existance.
	public int inputFieldName () {
			System.out.println("Give name of field you want to update");
			StringType name = new StringType();
			String nameofField = name.getData();
			int ex = this.containsName(nameofField);
			if (ex == -1) {
				System.out.println("This name of field doesn't exist in your Data Base");
				System.out.println("Do you want to try again?");
				//System.out.println("Answer Yes or No");
				Boolean answer = Database.findDecision();
				if (answer == true) {
					inputFieldName();
		    	} else if (answer == false) {
					Menu.presentationMenu();
				}
			}
			return ex;
		}


	public void changeFieldName() {
		StringType name = new StringType();
		int pos = this.inputFieldName();
		Column col = this.getColumns().get(pos);
		System.out.println("Give the new name of the field");
		String newName = name.getData();
		int k = this.containsName(newName);
		if (k == -1){
			col.setName(newName);
		}
		else {
			System.out.println ("This name is already in use.");
			System.out.println ("Do u want to try again?");
			Boolean answer = Database.findDecision();
			if(answer){
				inputFieldName();
			}
			else {
				//call method with menu of choices
			}
		}

	}

    /* search if there is a PrimaryKey Column.
	 Then calls method primaryKeyColumn.
	 attribute: position of field to be changed.*/
	public int findPrimaryKeyColumn(int pfield) {
		int j = 0;
		int exprimarykey = -1;
		do {
			Column col = this.getColumns().get(j);
			if (col.getPrimaryKey() == true) {
				exprimarykey = j;
			} else {
				j++;
			}
		} while (exprimarykey == -1 && j< this.columnCounter);
		  return  primaryKeyColumn(exprimarykey);
	}

	public int createIncreasedNumber() {
		FieldType type = Column.findType(1);
		Column newcolumn = new Column("Increased Number",type, this); //create a new list with increased number.
			for(int i=0; i < numberOfRows - 1; i++) {
				newcolumn.getField().add(i + 1);
				//fill the new list.
			}
		newcolumn.setPrimaryKey(true);
		return this.containsName("Increased Number");
	}


		 /*if a column with primary keys exists, keeps the position in table and calls informUser.
		  Else make a new list in table with increased number that it will be a primary key list
		  and calls informUser()*
		  attributes: position of of primarykey list position of field to be changed*/
		public int primaryKeyColumn(int exprimaryKey) {
			if(exprimaryKey == -1) {
			exprimaryKey = createIncreasedNumber();
			}
				return informUser(exprimaryKey);
		}


	/*if a column with primary keys exists, keeps the position in table and calls informUser.
	Else make a new list in table with increased number that it will be a primary key list
	and calls informUser()*/
		public int informUser(int ex){
					Column col = this.getColumns().get(ex);
					System.out.println("This is your Database :");
					this.printAll();
					//Inform him which list is primary key.
					System.out.print("This is the list with the primary keys of your elements ");
					System.out.println(col.getName());
					System.out.println("Type the primary key of element you want to change");
					Object searchKey = col.getType().getData();
						if ( !col.getField().contains(searchKey)) {
							System.out.println("The primary key you typed doesn't exist.");
							System.out.println("Do you want to try again?");
							//System.out.println("Answer Yes or No");
							Boolean answer = Database.findDecision();
							if (answer) {
								informUser(ex);
							} else {
								Menu.presentationMenu();
							}
					}
						return col.getField().indexOf(searchKey); //position of primary key in list.


			}


	public void changeValue() {
		int pfield = inputFieldName();
		int pkeypos = findPrimaryKeyColumn(pfield);
		Column x = this.getColumns().get(pfield);
		System.out.println("Enter the new value of element you want to change");
		Object nValue = x.getType().getData();

		x.getField().set(pkeypos, nValue);
		System.out.println("Change succeed");
	}


	public void changeAllData() {
			int pos = this.inputFieldName();
			Column col = this.getColumns().get(pos);
			for ( int i=0 ; i<col.getField().size() ; i++) {
				System.out.println("Enter the new value of element you want to change");
				Object newValue = col.getType().getData();
				col.getField().set(i, newValue);
			}
		}

	public void sameValue() {
		int fieldpos = this.inputFieldName();
		Column col = this.getColumns().get(fieldpos);
		System.out.println("Insert the new value of all elements");
		Object newValue = col.getType().getData();
		for (int i=0; i < col.getField().size(); i++) {
			col.getField().set(i,newValue);
		}
		this.printAll();
	}
}
