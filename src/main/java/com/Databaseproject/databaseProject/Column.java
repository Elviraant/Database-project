

import java.util.Scanner;
import java.util.ArrayList;

public class Column {
	private String name;
	private FieldType type;
	private boolean isPrimaryKey;
	private ArrayList<Object> field;

	//Setters and getters.
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Object> getField() {
		return field;
	}

	public void setField(ArrayList<Object> field) {
		this.field = field;
	}

	public FieldType getType() {
		return type;
	}

	public void setType(FieldType type) {
		this.type = type;
	}

	//Constructor.
	public Column(String name, FieldType type, boolean isPrimaryKey, Table thisTable) {
		this.name = name;
		this.type = type;
		this.isPrimaryKey = isPrimaryKey;
		thisTable.getColumns().add(this);
		int counter = thisTable.getColumnCounter();
		counter++;
		thisTable.setColumnCounter(counter);
		field = new ArrayList<Object>();
		}

	public void printElement(int row) {
		String data = String.format("|%-15s|", this.field.get(row).toString());
		System.out.print(data);
		System.out.print("     ");

	}



}//End of class Column.


