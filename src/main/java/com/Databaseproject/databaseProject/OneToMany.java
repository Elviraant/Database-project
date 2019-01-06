/**
Represents a relationship between two tables where the rows of table1
*can be linked to multiple rows of table2 but
*a row of table2 can be linked only to one row of table1
*/
//package com.Databaseproject.databaseProject;
import java.util.Scanner;
import java.util.HashMap;

public class OneToMany extends Correlation{

	private Column column;
	private int posF;

	public OneToMany(String name, Table table1, Table table2) {

		super(name, table1, table2);
		column = new Column(table2, true, this);
		column.createFkColumnName(table1);
		table1.setReferences(true);
		posF = table2.getColumnCounter();
		table2.setPositionOffFk(table1, posF);
		table2.setInvPositionOffFk(posF, table1);
	}


	/**
	 *Fills the foreign key column of table2
	 **/
	public void fillForeignKeyColumn() {
		Column pKColumnOne = pKColumn1();
		printPrimaryKeyColumns() ;
		for (int i = 0; i < table2.getNumberOfRows(); i++) {
			Object pKey2= getPKey2(i);
			boolean continueProcess = true;
			while (continueProcess) {
				Object key = printInsertionMessage(pKey2);
				int pos = pKColumnOne.findPKeyPosition(key);
				if (pos != -1)	{
					column.getField().add(key);
					continueProcess = false;
				} else {
					Menu.printNonExistantKeyMessage();
					Menu.printTryAgainMessage();
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
					System.out.println();
					System.out.println(this.toString());
					System.out.println("This is an one-to-many relationship");
					System.out.println("The table where one record can be linked to multiple records of the other is: "
					+ table1.getName());
					System.out.println();
					System.out.println("The table where the records are linked only to one record of the other table is: "
					+ table2.getName());
					System.out.println();
				case 2:
					search();
					break;
				case 3:
					continueProcess = false;
					break;
			}
		}
	}
}
