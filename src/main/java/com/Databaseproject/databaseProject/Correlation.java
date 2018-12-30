//package com.databaseProject.Databaseproject;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.Serializable;

public class Correlation implements Serializable {

	protected String name;
	protected Table table1; //entity 1
	protected Table table2; //entity 2

	/**
	*Constructor for Correlation class
	* @param name name of correlationship between the entities
	* @param table1 first entity
	* @param table2 second entity
	*/
	public Correlation(String name, Table table1, Table table2) {
		this.name = name;
		this.table1 = table1;
		this.table2 = table2;
	}

	public String getName() {
		return this.name;
	}

	public Table getTable1() {
		return this.table1;
	}

	public Table getTable2() {
		return this.table2;
	}

	public String toString() {
		return table1.getName() + " " + name + " " + table2.getName();
	}

	public void viewProperties() {
	}

	public static void manageCorrelations(Database d1) {
		boolean continueProcess = true;
		ArrayList<Correlation> correlations = d1.getCorrelations();
 		while (continueProcess) {
			int choice = Menu.manageCorrelationsMenu();
			switch (choice)
			{
				case 1:
					int number = correlations.size();
					System.out.println("You have created " + number + " correlation(s)");
					System.out.println();
					break;
				case 2:
					printCorrelations(correlations);
					break;
				case 3:
					Correlation correlation = chooseCorrelation(correlations);
					if (correlation instanceof OneToOne) {
						correlation = (OneToOne) correlation;
					} else if (correlation instanceof ManyToMany) {
						correlation = (ManyToMany) correlation;
					} else {
						correlation = (OneToMany) correlation;
					}
					correlation.viewProperties();
					break;
			}
		}
	}

public static void printCorrelations(ArrayList<Correlation> correlations) {
		System.out.println();
		for (int i = 0; i < correlations.size(); i++) {
			System.out.println(String.format("%s. %s", (i + 1), correlations.get(i).toString() ));
		}
	}

	public static Correlation chooseCorrelation(ArrayList<Correlation> correlations) {
		printCorrelations(correlations);
		System.out.println("Please choose the correlation you want to view");
		System.out.print(String.format("\n\n"));
		int choice = Database.choice(1, correlations.size());
		return correlations.get(choice - 1);
	}

	public int choice() {
		System.out.println("1. " + table1.getName());
		System.out.println("2. " + table2.getName());
		System.out.println("In which table do you want to search for related records? (insert number of choice)");
		int choice = Database.choice(1,2);
		if (choice == 1) {
			table2.printAll();
		} else {
			table1.printAll();
		}
		System.out.println("Insert a primary key of the table: ");
		return choice;
	}

	public void search() {
		Scanner cs = new Scanner(System.in);
		int choice = choice();
		if (choice == 1) {
			Column fColumn = fKColumn();
			fColumn.setFieldType(pKColumn1().getType());
			Object element = getKey(fKColumn());
			ArrayList<Integer> rowsWanted = new ArrayList<>();
			ArrayList<Integer> primaryRow = pKColumn2().matchingRows(element);
			if (primaryRow.size() != 0) {
				System.out.println("Found in row " + primaryRow.get(0) +"of " + table2.getName() ); //to be deleted
				ArrayList<Object> foreigns = new ArrayList<Object>();
				for (Integer row: primaryRow) {
					foreigns.add(fKColumn().getField().get(row));
					System.out.println("added foreign" + fKColumn().getField().get(row));//will be deleted when checked
				}
				for (Object foreign : foreigns) {
					rowsWanted = pKColumn1().matchingRows(foreign);
				}
				printRelated(rowsWanted,table1);
			} else {
				System.out.println("No match found");
				System.out.print(String.format("\n\n"));
			}
		} else {
			Object element = getKey(pKColumn1());
			ArrayList<Integer> rows = fKColumn().matchingRows(element);
			if ( rows.size() != 0)  {
				for (int i = 0; i < rows.size(); i++) {
					System.out.println("Found in row: " + (rows.get(i) + 1)); //will be deleted when checked
				}
				printRelated(rows, table2);
			} else {
				System.out.println("No matches found");
			}
		}
	}

	public void printRelated(ArrayList<Integer> rows, Table table) {
		if (rows.size() == 0) {
			System.out.println("No matching records found");
			System.out.print(String.format("\n"));
		} else {
			System.out.println("Related records with given primary key: ");
			System.out.print(String.format("\n"));
			table.specificRows(rows);
		}
	}

	public Object getKey(Column column) {
		System.out.println("Primary key: ");
		return column.getType().getData();
	}

	public int prKey1() {
		System.out.println("Primary key of column one is in position " + table1.findPrimaryKeyColumn()); //to be deleted
		return table1.findPrimaryKeyColumn();
	}

	public int prKey2() {
		System.out.println("Primary key of column one is in position " + table1.findPrimaryKeyColumn()); //to be deleted
		return table2.findPrimaryKeyColumn();
	}

	public Column fKColumn() {
		HashMap<Table, Integer> foreignKeyMapping = table2.getPositionOffFk();
		int pos = foreignKeyMapping.get(table1);
		System.out.println("foreign found in position " + pos); //to be deleted
		return table2.getColumns().get(pos - 1);
	}

	public Column pKColumn1() {
		return table1.getColumns().get(prKey1());
	}

	public Column pKColumn2() {
		return table2.getColumns().get(prKey2());
	}

	public void printPrimaryKeyColumns() {
		table1.printPrimaryKeyColumn();
		System.out.println();
		table2.printPrimaryKeyColumn();
	}


}