
//Class which represents a table of our database.
import java.util.ArrayList;
import java.util.Scanner;

public class Table {
	private ArrayList<Column> columns = new ArrayList<>();
	private int columnCounter = 0;
	private String name;
	private int numberOfRows;

    Scanner cs = new Scanner(System.in);

    //setters and getters
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

    //constructor
    public Table(String name) {
	    this.name = name;
	    Database.getTables().add(this);
	    int counter = Database.getTableCounter();
	    counter++;
	    Database.setTableCounter(counter);
    }

	//create field names and call method findType to create the arrays
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

			int choice = cs.nextInt();
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


	//ask how to fill the fields and call method columnFillerByRow or columnFillerByCollumn
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


	//Fill in with Data, Fills by row//
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


	//Fill in with Data, Fills by column//
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


	//Prints the header with the title of the fields
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


	//Prints one row of the table.
    public void presentRow(int row) {
        for (int i = 0; i < this.getColumnCounter(); i++) {
		    Column column = this.getColumns().get(i);
			column.printElement(row);
		}
		System.out.println();
	}


	//Prints all table insertions.
	public void printAll() {
		System.out.println();
		this.printHeader();
		Column firstColumn = this.getColumns().get(0);
		for (int k = 0; k < firstColumn.getField().size(); k++) {

			this.presentRow(k);
		}
		System.out.println();
	}




	//User gives the number of the starting row.
	public int startingRow() {
		System.out.println("Starting row: ");
		return cs.nextInt();
	}

	//User gives the ending row.
	public int endingRow() {
		System.out.println("Ending row: ");
		return cs.nextInt();
	}

	//Present specific rows within a range given by the user.
	public void printSpecificRows() {
		System.out.println();
		int start = startingRow() - 1;
		int end = endingRow();
		this.printHeader();
		for (int i = start; i < end; i++) {
			this.presentRow(i);
		}
		System.out.println();
	}

	/*Presents columns given by the user*/
	public void printSpecificColumns() {
		ArrayList <String>  attributes = new ArrayList<String>();

		System.out.println("Type the first attribute that you want print."
		    + "If you're done, type Done.");
		String attribute = cs.next();
		while (!attribute.equals("Done"))	                     {
			if (this.containsName(attribute) != -1) {
				attributes.add(attribute);
				System.out.println("Type another attribute that you want print."
				    + "If you're done, type Done.");
				attribute = cs.next();
			} else {
				System.out.println("There's no such attribute. Try again."
						+ "If you're done, type Done.");
				attribute = cs.next();
			}
		}

		if (attributes.isEmpty()) {
			System.out.println("There's nothing to be presented");
		} else {
		    this.printHeaderOfSpecificColumns(attributes);
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
    		   return this.columns.indexOf(c);

    	   }
    	}
    	return -1;
    }


    public void presentColumns( ArrayList <String> attributes) {
		for (int i = 0; i < this.columns.get(0).getField().size(); i++)  {
			for (String a: attributes) {
				Column column = this.columns.get(this.containsName(a));
		        column.printElement(i);
            }
        System.out.println();
        }
	}

}

