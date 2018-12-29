
//package com.databaseProject.Databaseproject;
import java.util.ArrayList;
import java.util.HashMap;

public class OneToOne extends Correlation {

	private Column column;
	private int posF;

	public OneToOne(String name, Table table1, Table table2 ) {
		super(name, table1, table2);
		column = new Column(table2, true);
		column.createFkColumnName(table1);
		posF = table2.getColumnCounter();
		table2.setPositionOffFk(table1, posF);
	}

	@Override
	/**
	*Shows the options concerning the management of an one to one correlation
	*/
	public void viewProperties() {
		boolean continueProcess = true;
 		while (continueProcess) {
			System.out.print(String.format("\n\n"));
			int choice = Menu.viewPopertiesMenu();
			switch (choice)
			{
				case 1:
					System.out.println(this.toString());
					System.out.println("This is an One-To-One correlation");
					System.out.println();
					System.out.println("Every record of a table should be linked to ecxactly one record of the other.");
					System.out.println();
					break;
				case 2:
					break;
				case 3:
					this.search();
					break;
			}
		}
	}

	/**
	 *Fills foreignKeyColumn with Data
	 **/
	public void fillForeignKeyColumn() {

		int posP1 = table1.findPrimaryKeyColumn();
		int posP2 = table2.findPrimaryKeyColumn();
		ArrayList <Object> primaryKeyField2 = table2
											.getColumns()
											.get(posP1)
											.getField();
		Column primaryKeyColumn = table1
								.getColumns()
								.get(posP2);



		for (int i = 0; i < table2.getNumberOfRows(); i++) {
			Object pKey2 =  primaryKeyField2.get(i);
			boolean continueProcess = true;
			while (continueProcess) {
				table1.printAll();
				System.out.println("Insert the primary key that's correlated with " + pKey2
										+ " from table " + table2.getName() + " :");
				Object key = primaryKeyColumn.getType().getData();
				int pos = primaryKeyColumn.getField().indexOf(key);

				if (pos != -1) {
					boolean check = column.checkUniqueness(key);
					if (check) {
						column.getField().add(key);
						continueProcess = false;
					} else {
						System.out.println("This record is already correlated with another record from " + table2.getName()
											+ ". Try again.");

					}
				} else {
					System.out.println("This primary key doesn't exist. Try again.");

				}
			}
		}
	}








}




