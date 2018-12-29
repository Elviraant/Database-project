/**
Represents a relationship between two tables where the rows of table1
*can be linked to multiple rows of table2 but
*a row of table2 can be linked only to one row of table1
*/
//package com.databaseProject.Databaseproject;
import java.util.Scanner;
import java.util.HashMap;

public class OneToMany extends Correlation{

	private Column column;
	private int posF;

	public OneToMany(String name, Table table1, Table table2) {

		super(name, table1, table2);
		column = new Column(table2, true, this);
		column.createFkColumnName(table2);
		posF = table2.getColumnCounter();
		table2.setPositionOffFk(table1, posF);

	}


	/**
	 *Fills the foreign key column of table2
	 **/
	public void fillForeignKeyColumn() {
		Column primaryKeyColumnMany = pKColumn2();
		Column primaryKeyColumnOne = pKColumn1();
		printPrimaryKeyColumns() ;
		for (int i = 0; i < table2.getNumberOfRows(); i++) {
			Object pKey2= primaryKeyColumnMany.getField().get(i);
			boolean continueProcess = true;
			while (continueProcess) {
				System.out.println("Insert the primary key of record that is correlated with " + pKey2
										+ " from " + table2.getName() + ":");
				Object key = primaryKeyColumnOne.getType().getData();
				int pos = primaryKeyColumnOne.getField().indexOf(key);

				if (pos != -1)	{
					column.getField().add(key);
					continueProcess = false;
				} else {
					System.out.println("This primary key doesn't exist. Try again.");
				}
			}
		}
	}


	@Override
	public void viewProperties() {
		boolean continueProcess = true;
 		while (continueProcess) {
			System.out.print(String.format("\n\n"));
			int choice = Menu.viewPopertiesMenu();
			switch (choice)
			{
				case 1:
					System.out.println(this.toString());
					System.out.println("This is an one-to-many relationship");
					System.out.println("The table where one record can be linked to multiple records of the other is: "
					+ table1.getName());
					System.out.println();
					System.out.println("The table where the records are linked only to one record of the other table is: "
					+ table2.getName());
					System.out.println();
				case 2:
					break;
				case 3:
					this.search();
					break;
			}
		}
	}

	public int fK() {
		HashMap<Table, Integer> foreignKeyMapping = table2.getPositionOffFk();
		return foreignKeyMapping.get(table1);
	}

}
