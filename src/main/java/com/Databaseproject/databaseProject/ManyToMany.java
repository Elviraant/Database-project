
//package com.databaseProject.Databaseproject;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;


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
						 //column2.getForeignKeys().get(pos).add(pKey1);
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
					//column1.getForeignKeys().add(foreignKeys1);
				}
			}
			for ( ArrayList <Object> c: column1.getForeignKeys() ) {
				for ( Object a: c) {
					System.out.print( "" + a + "   |");
				}
				System.out.println();
			}
			for ( ArrayList <Object> c: column2.getForeignKeys() ) {
				for ( Object a: c) {
					System.out.print( "" + a + "   |");
				}
				System.out.println();
			}
		}



	public void createTable2Lists() {
		for (int i = 0; i < table2.getColumnCounter(); i++) {
			column2.getForeignKeys().add(new ArrayList <Object>());
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
					System.out.println("This is a many-to-many correlation");
					System.out.println("Multiple records of one table are linked to multiple tables of the other");
					break;
				case 2:
					break;
				case 3:
					break;
			}
		}
	}
	public int fK1() {
		HashMap<Table, Integer> foreignKeyMapping = table1.getPositionOffFk();
		return foreignKeyMapping.get(table2);
	}

	public int fK2() {
		HashMap<Table, Integer> foreignKeyMapping = table2.getPositionOffFk();
		return foreignKeyMapping.get(table1);
	}

	public Table defineSearched() {
		int choice = choice();
		Table searched;
		if (choice == 1) {
			searched = table1;
		} else {
			searched = table2;
		}
		return searched;
	}

	@Override
	public void search() {
		Scanner cs = new Scanner(System.in);
		Table searched = defineSearched();
	}

}