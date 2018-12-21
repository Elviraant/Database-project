/**
Represents a table of our database.
*/
import java.util.ArrayList;
import java.util.Scanner;
import java.io.Serializable;
import java.util.Collections;

public class Table implements Serializable {
	private ArrayList<Column> columns = new ArrayList<>();
	private int columnCounter = 0;
	private String name;
	private int numberOfRows;
	private int positionOfFK;

	/**
	*Constructor for Table class
	* @param columns arraylist with objects of Column
	* @param columnCounter number of columns in this table
	* @param name name of this table
	* @param numberOfRows number of row insertions from user
	*/
	 public Table(String name, Database d1) {
	    this.name = name;
	    d1.getTables().add(this);
	    int counter = d1.getTableCounter();
	    counter++;
	    d1.setTableCounter(counter);
    }

    transient Scanner cs = new Scanner(System.in);

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
 	public void setPositionOfFK(int positionOfFK) {
 		this.positionOfFK = positionOfFK;
 	}

 	public int getPositionOfFK() {
 		return positionOfFK;
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

			int check = -2;
			while(check != -1) {

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
	 *	Defines, which Column is the primary Key field
	 *	Checks, if the name of a field exists at this Table
	 *  If it exists, sets the boolean instance variable true
	 */
	public void definePrimaryKey(){

		System.out.println("Do you want to set a field as primary key? ");
		boolean continueProcess;
		continueProcess = Database.findDecision();
		if (continueProcess) {
			continueProcess = true;
			while(continueProcess) {
				System.out.print("Please, insert the name of the primary key field: ");
				String primaryKeyName = cs.next();
				int exists = this.containsName(primaryKeyName);
				if (exists != -1) {
					this.getColumns().get(exists).setPrimaryKey(true);
					continueProcess = false;
				} else {
					System.out.print(primaryKeyName + "No such field at this entity.");
					System.out.print("Do you want to try again? ");
					continueProcess = Database.findDecision();
				}
			}
		}
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
		} else if (filltype == 2) {
			this.columnFillerByColumn();
		}
		System.out.println("");
	}

	/**
	*Fill in with Data, Fills by row
	*/
	public void columnFillerByRow() {
		//System.out.println("mpike sthn filler by row");
		boolean continueProcess = true;
		int insertions = 0;
		while (continueProcess) {
			System.out.println("#" + (insertions + 1) + " Row: ");
			for(Column column : this.getColumns()) {
				System.out.println("Give "+ column.getName());
				Object data = column.getType().getData();
				//column.getField().add(data);
				column.fillPrimaryKeyField(data);
			}
			insertions++;
			System.out.println("Do you want to continue? Y/N");
			continueProcess = Database.findDecision();
			//if (decision) continueProcess = true; else continueProcess = false;
		}
		numberOfRows = insertions;
	}



	/**
	*Fill in with Data, Fills by column
	*/
	public void columnFillerByColumn() {
		for (int i = 0; i < columnCounter; i++){
			Column column = columns.get(i);
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
				case 3:
					deleteData();
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

				/*case(3): changeDataByColumn();
						break; */

				case(3): changeDataByRow();
				        break;

				case(4): sameValue();
						 break;


			}
			System.out.println("Do you want to continue the process of changing data?Yes/No");
			decision = Database.findDecision();
		 }  while (decision);
	}

	public void deleteData() {
		int choice;
		boolean continueProcess = true;
		while (continueProcess) {
			Menu.deletionMenu();
			choice = Database.choice(1,4);
			switch (choice)
			{
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
				break;
			case 5:
				deleteAll();
				break;
			}
			System.out.println("Deletion completed successfully");
			System.out.println("Continue with the deletion of data?");
			continueProcess = Database.findDecision();
		}
	}

	/**
	*prints all table insertions and the titles of the attributes
	*/
	public void printAll() {
		System.out.println();
		System.out.println("Table: "+this.name);
		printHeader();
		Column firstColumn = columns.get(0);
		for (int k = 0; k < firstColumn.getField().size(); k++) {

			presentRow(k);
		}
		System.out.println();
	}

	/**
	*Prints the header with the title of all the fields
	*/
	public void printHeader() {
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < columnCounter; i++) {
			Column column = columns.get(i);
			list.add(column.getName());
		}
		printHeaderOfSpecificColumns(list);
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
		System.out.println();
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
		printHeaderOfSpecificColumns(attributes);
		for (int i = 0; i < numberOfRows; i++)  {
			for (String a: attributes) {
				Column column = columns.get(containsName(a));
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
					return inputFieldName();
		    	} else if (answer == false) {
					Menu.startingMenu();
				}
			}
			return ex;
		}

	public void changeDataByRow() {
		int posprimarykey = findPrimaryKeyColumn();
		for (int i=0; i< getColumnCounter() ; i++) {
			Column col= columns.get(i);
			System.out.println("Field:" +col.getName() );
			System.out.println("Give the new value:");
			Object nValue = col.getType().getData();
		    col.getField().set(posprimarykey, nValue);
		}
	}





	public void changeFieldName() {
		StringType name = new StringType();
		int pos = this.inputFieldName();
		if(pos != -1) {
			Column col = this.getColumns().get(pos);
			System.out.println("Give the new name of the field");
			String newName = name.getData();
			int k = this.containsName(newName);
			if (k == -1){
				col.setName(newName);
			}
			else {
				System.out.println ("This name is already in use.");
		}
			System.out.println ("Do u want to try again?");
			Boolean answer = Database.findDecision();
			if(answer){
				inputFieldName();
			}
			else {
				Menu.startingMenu();
			}
		}

	}


    /* search if there is a PrimaryKey Column.
	 Then calls method primaryKeyColumn.
	 attribute: position of field to be changed.*/

	public int findPrimaryKeyColumn() {   //int pfield

		int j = 0;
		int exprimarykey = -1;
		do {
			Column col = columns.get(j);
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
				for(int i=0; i < numberOfRows; i++) {
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
				exprimaryKey =createIncreasedNumber();
			}
				return informUser(exprimaryKey);
		}


	/*if a column with primary keys exists, keeps the position in table and calls informUser.
	Else make a new list in table with increased number that it will be a primary key list
	and calls informUser()*/

		public int informUser(int ex){
					Column col = columns.get(ex);
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
								return informUser(ex);
							} else  {
								Menu.startingMenu();

							}
				    	}

						return col.getField().indexOf(searchKey); //position of primary key in list.
				}


	public void changeValue() {
		int pfield = inputFieldName();
		if(pfield != -1 ) {
			int pkeypos = findPrimaryKeyColumn();
			if (pkeypos != -1 ) {
				Column x = columns.get(pfield);
				System.out.println("Enter the new value of element you want to change");
				Object nValue = x.getType().getData();
				x.getField().set(pkeypos, nValue);
				System.out.println("Change succeed");
			}
		}
	}




	/*public void changeDataByColumn() {
			int pfield = inputFieldName();
			if (pfield != -1 ) {
				int pkeypos = findPrimaryKeyColumn();
				if(pkeypos != -1) {
					Column x = this.getColumns().get(pfield);
					do {
						System.out.println("Enter the new value of element you want to change");
						Object nValue = x.getType().getData();
						x.getField().set(pkeypos, nValue);
						System.out.println("Do you want to continue?Y/N");
						Boolean decision = Database.decision();
						System.out.println("Enter primary key of next element(else enter 0 to exit");
						pkeypos = Database.choice(1,numberOfRows);
					} while ();

			}
		}
	}*/

	public void sameValue() {
		int fieldpos = inputFieldName();
		int pfield = inputFieldName();
		if(pfield != -1 ) {
			Column col = this.getColumns().get(fieldpos);
			System.out.println("Insert the new value of all elements");
			Object newValue = col.getType().getData();
			for (int i=0; i < col.getField().size(); i++) {
				col.getField().set(i,newValue);
		}
			this.printAll();
		}
	}

	public int checkOffLimitsRows() {
		int x;
		Column firstColumn = this.getColumns().get(0);
		do {
			x = cs.nextInt();
			if ((x > numberOfRows) || (x<0)) {
				System.out.println("The number that you gave was off limits.\nPlease try again:");
			}
		} while ((x > numberOfRows) || (x<0));
		return x;
	}

	public void deleteRows() {
		System.out.println(String.format("%s\n%s\n", "1. Delete Specific Rows", "2. Delete specific range of rows"));
		int choice;
		choice = Database.choice(1,2);
		switch (choice)
		{
		case 1:
			deleteSpecificRows();
			break;
		case 2:
			deleteSpecificRangeofRows();
			break;
		}
	}

	/** deletes any row you want(one or more)*/
	public void deleteSpecificRows() {
		System.out.println("How many rows do you want to delete?");
		int x = Database.choice(1,numberOfRows);
		int counter=0;
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i<x; i++) {
			System.out.println("Which row do you want to delete?");
			int y = Database.choice(1,numberOfRows);
			list.add(y);
		}
		Collections.sort(list);
		System.out.println(list);
		for (int k=0; k<this.getColumnCounter(); k++) {
			Column column = this.getColumns().get(k);
			for (int j=0; j<list.size(); j++) {
				System.out.println(list.get(j)-1-counter);
				column.getField().remove(list.get(j)-1-counter);
				counter++;
			}
		}
		numberOfRows=numberOfRows-counter;
	}

	/**
	* deletes specific rows, which are within a range given by the user
	*/
	public void deleteSpecificRangeofRows(){
		System.out.println("Please insert the range of rows you want to delete.");
		int startRow;
		int endRow;
		do {
			System.out.println("Starting row: ");
			startRow = Database.choice(1, numberOfRows)-1;
			System.out.println("Ending row: ");
			endRow = Database.choice(1,numberOfRows);
			if (startRow>= endRow) {
				System.out.println("Starting can't be greater than ending row");
			}
		} while ( startRow>= endRow);
		for (int i=0; i<this.getColumnCounter(); i++) {
			Column column = this.getColumns().get(i);
			for (int j=startRow; j<endRow; j++) {
				column.getField().remove(j);
			}
		}
	}

	public void setDeleteCounter() {
		this.setColumnCounter(columnCounter-1);
			}


	public int checkOffLimitsColumns() {
		int x;
		do {
			x = cs.nextInt();
			if ((x> this.getColumnCounter()) || (x<0)) {
				System.out.println("The number that you gave was off limits.\nPlease try again:");
			}
			} while ((x> this.getColumnCounter()) || (x<0));
			return x;
		}

	/**
	* deletes any columns you want
	*/
	public void deleteColumns() {
		System.out.println("How many columns do you want to delete?");
		int x = checkOffLimitsColumns();
		for (int i=0; i<x; i++) {
			System.out.print("Please give me the name of the column you want to delete: ");
			boolean cont = false;
			while (!cont){
				String y = cs.next();
				for (int k=0; k<this.getColumnCounter(); k++) {
					Column column = this.getColumns().get(k);
						if (y.equals(column.getName())) {
							this.setDeleteCounter();
							this.getColumns().remove(k);
							cont = true;
						}

					}if (!cont) {
						System.out.println("The name you inserted is not valid. Please try again.");
				}
			}
		}
	}


	/**
	* deletes any element you want
	*/
	public void deleteElements(){
		boolean continueProcess = true;
		while (continueProcess) {
			int x;
			do{
				System.out.println("Please insert the name of the column in which the element exists: ");
				String name = cs.next();
				x = containsName(name);
			} while ( x == -1);
			Column column = this.getColumns().get(x);
			System.out.println("Please enter the element you want to delete: ");
			Object element = column.getType().getData();
			System.out.println("Please insert the row in which the element exists: ");
			int y = Database.choice(1,numberOfRows);
			column.getField().set(y-1, " ");
			System.out.println("Do you want to continue? Y/N");
			continueProcess = Database.findDecision();
		}
	}

	/*deletes a whole table*/
	public void deleteAll() {
		for (int i=0; i< this.getColumnCounter(); i++) {
			this.getColumns().remove(i);
		}
	}

}
