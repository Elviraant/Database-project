//package com.databaseProject.Databaseproject;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

public class Correlation {

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
		int choice = choice();
		if (choice == 1) {
			Object element = fKColumn().getType().getData();
			ArrayList<Integer> rowsWanted = new ArrayList<Integer>();
			ArrayList<Integer> primaryRow = table2.getColumns().get(prKey2() - 1).matchingRows(element);
			ArrayList<Object> foreigns = new ArrayList<Object>();
			for (Integer row: primaryRow) {
				foreigns.add(fKColumn().getField().get(row - 1));
			}
			for (Object foreign : foreigns) {
				rowsWanted = table1.getColumns().get(prKey1() - 1).matchingRows(foreign);
			}
			table1.specificRows(rowsWanted);
			//call method to find the foreign key of table2 which matches the primaryKey given by the user
			//call method to find the rows where the primary key of the primaryKeyColumn (table1) is equal to the foreign key (prev met)
			//print the rows (found from the previous method)
		} else {
			Object element = table1.getColumns().get(prKey1() - 1).getType().getData();
			ArrayList<Integer> rows = fKColumn().matchingRows(element);
			table2.specificRows(rows);
			//call method to find the rows where the primary key given by the user matches the foreign key of table2
			//print the rows (found from the previous method)
		}
	}



	public int prKey1() {
		return table1.findPrimaryKeyColumn();
	}

	public int prKey2() {
		return table2.findPrimaryKeyColumn();
	}

	public Column fKColumn() {
		HashMap<Table, Integer> foreignKeyMapping = table2.getPositionOffFk();
		int pos = foreignKeyMapping.get(table1);
		return table2.getColumns().get(pos - 1);
	}

}