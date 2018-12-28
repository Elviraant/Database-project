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
		column = new Column(table1, true);
		column.createFkColumnName(table1);
		posF = table2.getColumnCounter();
		table2.setPositionOffFk(table2, posF);

	}


	/**
	 *Fills the foreign key column of table2
	 **/
	public void fillForeignKeyColumn() {

		int posPMany = table2.findPrimaryKeyColumn();
		int posPOne = table1.findPrimaryKeyColumn();

		Column primaryKeyColumnMany = table2.getColumns().get(posPMany);
		Column primaryKeyColumnOne = table1.getColumns().get(posPOne);


		for (int i = 0; i < table1.getNumberOfRows(); i++) {
			boolean continueProcess = true;
			while (continueProcess) {
				System.out.println("Insert the primary key of record that is correlated with " + primaryKeyColumnOne.getField().get(i)
										+ " from " + table1.getName() + ":");
				Object key = primaryKeyColumnMany.getType().getData();
				int pos = primaryKeyColumnMany.getField().indexOf(key);

				if (pos != -1)	{
					column.getField().add(key);
					continueProcess = false;
				} else {
					System.out.println(" This primary key doesn't exist. Do you want to try again?");
					continueProcess = Database.findDecision();
				}
			}
		}
	}

	@Override
	public void viewProperties() {
		boolean continueProcess = true;
 		while (continueProcess) {
			int choice = Menu.viewPopertiesMenu();
			switch (choice)
			{
				case 1:
					System.out.println(this.toString());
					System.out.println("This is an one-to-many relationship");
					System.out.println("The table where one record can be linked to multiple records of the other is: "
					+ table1.getName());
					System.out.println("The table where the records are linked only to one record of the other table is: "
					+ table2.getName());
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
