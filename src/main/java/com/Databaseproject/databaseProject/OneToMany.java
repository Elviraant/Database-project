/**
Represents a relationship between two tables where the rows of table1
*can be linked to multiple rows of table2 but
*a row of table2 can be linked only to one row of table1
*/
//package com.databaseProject.Databaseproject;
import java.util.Scanner;


public class OneToMany extends Correlation{

	private Column column;

	public OneToMany(String name, Table table1, Table table2) {

		super(name, table1, table2);
		column = new Column(table2, true);
		column.createFkColumnName(table1);
		posF = table2.getColumnCounter();
		table2.setPositionOffFk(table1, posF);

	}


	/**
	 *Fills the foreign key column of table2
	 **/
	public void fillForeignKeyColumn() {

		int posPMany = table2.findPrimaryKeyColumn();
		int posPOne = table1.findPrimaryKeyColumn();

		Column primaryKeyColumnMany = table2.getColumns().get(posPMany);
		Column primaryKeyColumnOne = table1.getColumns().get(posPOne);
		Column foreignKeyColumn = table2.getColumns().get(posF);

		for (int i = 0; i < table2.getNumberOfRows(); i++) {
			boolean continueProcess = true;
			while (continueProcess) {
				System.out.println("Insert the primary key of the correlated record of " + primaryKeyColumnMany.getField().get(i)
										+ " from" + table2.getName() + ":");
				Object key = primaryKeyColumnOne.getType().getData();
				int pos = primaryKeyColumnOne.getField().indexOf(key);

				if (pos != -1)	{
					foreignKeyColumn.getField().add(key);
				} else {
					System.out.println(" This primary key doesn't exist. Do you want to try again?");
					continueProcess = Database.findDecision();
				}
			}
		}
	}


}
