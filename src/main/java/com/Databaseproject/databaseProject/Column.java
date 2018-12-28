//package com.databaseProject.Databaseproject;
import java.util.ArrayList;
import java.io.Serializable;

public class Column implements Serializable{
	private String name;
	private FieldType type;
	private boolean isPrimaryKey;
	private boolean isForeignKey;
	private ArrayList<Object> field;
	private ArrayList<ArrayList<Object>> foreignKeys = new ArrayList<ArrayList<Object>>();;

	/**
	 * First Constructor for Column class
	 * @param name 	name of this field
	 * @param type 	type of this field
	 * @param table database's entity of this field
	 */
	public Column(String name, FieldType type, Table table) {
		this.name = name;
		this.type = type;
		field = new ArrayList<Object>();
		table.getColumns().add(this);
		table.setColumnCounter(table.getColumnCounter() + 1);
	}

	/**
	 * Second Constructor for Column class
	 * Used by Correlation for the foreign keys
	 * @param name 	name of this field
	 * @param table database's entity of this field
	 * @param isForeignKey sets true is this Column is a foreign key
	 */

	public Column(Table table, boolean isForeignKey) {
		//this.name = name;
		field = new ArrayList<Object>();
		//foreignKey.add(field);
		this.isForeignKey = isForeignKey;
		table.getColumns().add(this);
		table.setColumnCounter(table.getColumnCounter() + 1);
	}

	/**public static int getCounter() {
		return counter;
	} **/

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean getPrimaryKey() {
		return isPrimaryKey;
	}

	public void setPrimaryKey(boolean isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}

	public void setForeignKey(boolean isForeignKey) {
		this.isForeignKey = isForeignKey;
	}

	public boolean getForeignKey() {
		return isForeignKey;

	}

	public ArrayList<Object> getField() {
		return field;
	}

	public FieldType getType() {
		return type;
	}

	public void setFieldType(FieldType type) {
		this.type = type;
	}

	public ArrayList<ArrayList<Object>> getForeignKeys() {
		return foreignKeys;
	}


	public void printElement(int row) {
		String data = String.format("|%-15s|", field.get(row).toString());
		System.out.print(data);
		System.out.print("     ");

	}


	public static FieldType findType(int choice) {
		FieldType type;
		switch (choice) {
			case 1: {type = new IntegerType();}
			break;
			case 2: {type = new DoubleType();}
			break;
			case 3: {type = new StringType();}
			break;
			default: {type = new StringType();}
		}
		return type;
	}

	/**
	 * Returns void
	 * Fills the Primary Key Field
	 * @param data user's insertion
	 */
	public void fillPrimaryKeyField(Object data) {

			boolean unique = this.checkUniqueness(data);
			while (!unique) {
				System.out.print("This data alredy exists. Try again: ");
				data = this.getType().getData();
				unique = this.checkUniqueness(data);
			}
			this.getField().add(data);
	}

	/**
	 * Returns true if the data is unique
	 * Use for Primary Key Field
	 * @param data user's insertion
	 */
	public boolean checkUniqueness(Object data) {

		boolean unique = true;
		for (Object f : field) {
			if (f.equals(data)) {
				unique = false;
			}
		}
		return unique;

	}

	public void createFkColumnName(Table table) {

		String name =("Fk_from_").concat(table.getName());
		setName(name);

	}

	public void searchElement(Object element) {
		ArrayList<Integer> rows = matchingRows(element);
		if (rows.size() != 0) {
			System.out.println(element);
			System.out.print("found in position(s):");
			for (Integer row : rows) {
				System.out.println(row);
			}
		} else {
			System.out.print(element);
			System.out.println("doesn't exist in this field /nDo you want to try again?(Yes/No)");
			Boolean answer = Database.findDecision();
			if(answer) {
				Menu.searchingMenu();
			}
		}
	}

	public ArrayList<Integer> matchingRows(Object element) {
		ArrayList<Integer> rows = new ArrayList<Integer>();
		for (int i = 0; i < getField().size(); i++) {
			if (element.equals(getField().get(i))) {
				rows.add(i);
			}
		}
		return rows;
	}
}
