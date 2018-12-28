//package com.databaseProject.Databaseproject;
import java.util.ArrayList;
public class ManyToMany extends Correlation {

	private Column column1; //foreign key column for Table table1
	private Column column2; //foreign key column for Table table2
	private int posF1;
	private int posF2;


	public ManyToMany(String name, Table table1, Table table2) {

		super(name, table1, table2);
		this.column1 = new Column(table2, true);
		this.column1.createFkColumnName(table1);
		posF1 = table1.getColumnCounter();
		table2.setPositionOffFk(table1, posF1);
		this.column2 = new Column(table1, true);
		this.column2.createFkColumnName(table2);
		posF2 = table2.getColumnCounter();
		table1.setPositionOffFk(table2, posF2);

		//table2.setPositionOfFK(table2.getColumnCounter() + 1);
		//table1.setPositionOfFK(table1.getColumnCounter() + 1);
	}

	public Column getColumn1() {
		return column1;
	}

	public void setColumn1(Column column) {
		column1 = column;
	}

	public Column getColumn2() {
		return column2;
	}

	public void setColumn2(Column column) {
		column2 = column;
	}

	public void fillForeignKeyColumn() {
		int posP1 = table1.findPrimaryKeyColumn();
		Column primaryKeyColumn1 = table1.getColumns().get(posP1);
		int posP2 = table2.findPrimaryKeyColumn();
		Column primaryKeyColumn2 = table2.getColumns().get(posP2);
		/*createTable2Lists();*/

		for ( int i = 0; i < table1.getNumberOfRows(); i++) {
			ArrayList <Object> foreignKeys1 = new ArrayList<Object>();
			boolean continueProcess = true;
			int q =0;
			Object pKey1 = primaryKeyColumn1.getField().get(i);

			while (continueProcess) {
				boolean repeat = true;
				q++;
				while (repeat) {
					System.out.println("Insert the primary key of the #" + q +  " record that is correlated with "
										+ pKey1 + " from " + table1.getName() + ": ");

					Object key = primaryKeyColumn2.getType().getData();
					int pos = primaryKeyColumn2.getField().indexOf(key);
					if (pos != -1) {
						foreignKeys1.add(key);
						 column2.getForeignKeys().get(pos).add(pKey1);
						continueProcess = false;
					} else {
						System.out.println(" This primary key doesn't exist. Do you want to try again?");
						repeat = Database.findDecision();
					}
				}
					System.out.println("Are there any others correlated records of " + pKey1 + ": ");
					continueProcess = Database.findDecision();
				}
				if (!foreignKeys1.isEmpty()) {
					column1.getForeignKeys().add(foreignKeys1);
				}
			}
		}

	/*public void createTable2Lists() {
		for (int i = 0; i < table2.getColumnCounter(); i++) {
			column2.getForeignKeys().add(new ArrayList <Object>());
		}
	}*/

}