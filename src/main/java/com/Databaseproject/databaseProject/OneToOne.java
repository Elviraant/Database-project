
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


	/**
	 *Fills foreignKeyColumn with Data
	 **/
	public void fillForeignKeyColumn() {

		int posP1 = table1.findPrimaryKeyColumn();
		int posP2 = table2.findPrimaryKeyColumn();
		ArrayList <Object> primaryKeyField1 = table1
											.getColumns()
											.get(posP1)
											.getField();
		Column primaryKeyColumn = table2
								.getColumns()
								.get(posP2);


		Column fKColumn = fKColumn();
		for (int i = 0; i < table1.getNumberOfRows(); i++) {
			Object pKey1 =  primaryKeyField1.get(i);
			boolean continueProcess = true;
			while (continueProcess) {
				System.out.println("Insert the primary key that's correlated with " + pKey1
										+ " from " + table1.getName() + " :");
				Object key = primaryKeyColumn.getType().getData();
				int pos = primaryKeyColumn.getField().indexOf(key);

				if (pos != -1) {
					boolean check = column.checkUniqueness(key);
					if (check) {
						column.getField().set(pos, pKey1);
						continueProcess = false;
					} else {
						System.out.println("This record is already correlated with another record from " + table1.getName()
											+ ". Do you want to try again?");
						continueProcess = Database.findDecision();
					}
				} else {
					System.out.println("This primary key doesn't exist. Do you want to try again?");
					continueProcess = Database.findDecision();
				}
			}
		}
	}

	@Override
	/**
	*Shows the options concerning the management of an one to one correlation
	*/
	public void viewProperties() {
		boolean continueProcess = true;
 		while (continueProcess) {
			int choice = Menu.viewPopertiesMenu();
			switch (choice)
			{
				case 1:
					System.out.println(this.toString());
					System.out.println("This is an One-To-One correlation");
					System.out.println("Every record of a table should be linked to ecxactly one record of the other.");
					break;
				case 2:
					break;
				case 3:
					this.search();
					break;
			}
		}
	}
}




