//package com.databaseProject.Databaseproject;
import java.util.ArrayList;

public class OneToOne extends Correlation {

	private Column column;

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
	public void fillForeignKeyColumn(Table table1, Table table2) {

		int posP1 = table1.findPrimaryKeyColumn();
		int posP2 = table2.findPrimaryKeyColumn();
		ArrayList <Object> primaryKeyField1 = table1
											.getColumns()
											.get(posP1)
											.getField();
		Column primaryKeyColumn = table2
								.getColumns()
								.get(posP2);

		Column foreignKeyColumn = table1
								.getColumns()
								.get(posF);



		for (int i = 0; i < table1.getNumberOfRows(); i++) {
			boolean continueProcess = true;
			while (continueProcess) {
				System.out.println("Insert the primary key that's correlated with " + primaryKeyField1.get(i)
										+ " from " + table1.getName() + " :");
				Object key = primaryKeyColumn.getType().getData();
				int pos = primaryKeyColumn.getField().indexOf(key);

				if (pos != -1) {
					boolean check = foreignKeyColumn.checkUniqueness(key);
					if (check) {
						foreignKeyColumn.getField().add(key);
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

}




