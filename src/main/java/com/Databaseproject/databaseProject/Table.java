
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

    //Checks if a Column exists in a Table
	public int containsName(String name) {

    	for (Column c: this.columns) {
    	   if (c.getName().equals(name)) { //if it exists, it returns its position
    		   //System.out.println(this.columns.indexOf(c));
    		   return this.columns.indexOf(c);

    	   }
    	}
    	return -1; //if it doesn't exist, it returns -1
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

