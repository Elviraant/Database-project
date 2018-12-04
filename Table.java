//Class which represents a table of our database.
import java.util.ArrayList;
import java.util.Scanner;

public class Table {
	private ArrayList<Column> columns = new ArrayList<>();
	private int columnCounter = 0;
	private String name;
	private int numberOfRows;

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
		for (int i = 0; i < spaces-5; i++) {
			System.out.print("-");
	}
	System.out.println();
	}

	//Prints one row of the table.
	public void presentRow(int row) {
		String data;
		for (int i = 0; i < this.getColumnCounter(); i++) {
			Column column = this.getColumns().get(i);
			data = String.format("|%-15s|", column.getField().get(row).toString());
			System.out.print(data);
			System.out.print("     ");
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


	Scanner cs = new Scanner(System.in);

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
}
