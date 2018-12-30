
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
		this.column1 = new Column(table2, true, this);
		this.column1.createFkColumnName(table1);
		posF1 = table1.getColumnCounter();
		table2.setPositionOffFk(table1, posF1);
		this.column2 = new Column(table1, true, this);
		this.column2.createFkColumnName(table2);
		posF2 = table2.getColumnCounter();
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

		Column primaryKeyColumn1 = pKColumn1();
		Column primaryKeyColumn2 = pKColumn2();

		createTableLists(table1, column1);
		createTableLists(table1, column2);

		for ( int i = 0; i < table2.getNumberOfRows(); i++) {
			ArrayList <Object> foreignKeys2 = new ArrayList<Object>();
			boolean continueProcess = true;
			int q =0;
			Object pKey2 = primaryKeyColumn2.getField().get(i);
			printPrimaryKeyColumns();
			while (continueProcess) {
				boolean repeat = true;
				q++;
				while (repeat) {

					System.out.println("Insert the primary key of the #" + q +  " record that is correlated with "
										+ pKey2 + " from " + table2.getName() + ": ");

					Object key = primaryKeyColumn1.getType().getData();
					int pos = primaryKeyColumn1.getField().indexOf(key);
					if (pos != -1) {
						boolean check = checkForeignKeysUniqueness(foreignKeys2, key);
						if (check) {
							foreignKeys2.add(key);
							column1.getForeignKeys().get(pos).add(pKey2);
							repeat = false;
						} else {
							System.out.println("This record is already correlated with another record from " + table2.getName()
											+ ". Do you want to try again?");
							repeat = Database.findDecision();
						}
					} else {
						System.out.println("This primary key doesn't exist.");
						/*if ( q == 1) {
							System.out.println("Try again.");
						} else {*/
							System.out.println("Do you want to try again?");
							repeat = Database.findDecision();

					}
				}
					System.out.println("Are there any other correlated records of " + pKey2 + ": ");
					continueProcess = Database.findDecision();
				}

				if (!foreignKeys2.isEmpty()) {
					column2.getForeignKeys().set( i , foreignKeys2);
			}
			System.out.println("" + column1.getName() + "");
			for ( ArrayList <Object> c: column1.getForeignKeys() ) {
				for ( Object a: c) {
					System.out.print( "" + a + "   |");
				}
				System.out.println();
			}
			System.out.println("" + column2.getName() + "");
			for ( ArrayList <Object> c: column2.getForeignKeys() ) {
				for ( Object a: c) {
					System.out.print( "" + a + "   |");
				}
				System.out.println();
			}
		}

}
	public void createTableLists(Table table, Column column) {
		for (int i = 0; i < table.getNumberOfRows(); i++) {
			column.getForeignKeys().add(new ArrayList <Object>());
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
					System.out.println("This is a many-to-many correlation");
					System.out.println();
					System.out.println("Multiple records of one table are linked to multiple tables of the other");
					System.out.println(String.format("\n\n"));
					break;
				case 2:
					break;
				case 3:
					this.search();
					break;
			}
		}
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
		Table keyHolder;
		Column fKColumnOfKeys;
		if (searched.equals(table1)) {
			keyHolder = table2;
			fKColumnOfKeys = column2;
		} else {
			keyHolder = table1;
			fKColumnOfKeys = column1;
		}
		int posPK1 = keyHolder.findPrimaryKeyColumn();
		Column pKColumnOfKeys = keyHolder.getColumns().get(posPK1);
		int posPK2 = searched.findPrimaryKeyColumn();
		Column pKColumnOfSearched = searched.getColumns().get(posPK2);
		ArrayList<ArrayList<Object>> foreignsWithKeys = fKColumnOfKeys.getForeignKeys();
		ArrayList<Object> foreigns = foreigns(pKColumnOfKeys,foreignsWithKeys);
		if (foreigns.size() != 0) {
			ArrayList<Integer> rows = pKColumnOfSearched.matchingRows(foreigns);
			printRelated(rows, searched);
		}
	}

	public ArrayList<Object> foreigns(Column column, ArrayList<ArrayList<Object>> foreignKeys) {
		System.out.println("Please insert a primary key of the above table: ");
		Object element = column.getType().getData();
		ArrayList<Integer> rows = new ArrayList<Integer>();
		rows.add(column.matchingRows(element).get(0));
		if (rows.size() != 0) {
			return foreignKeys.get(rows.get(0));
		} else {
			System.out.println("No much found");
			return null;
		}
	}

	public boolean checkForeignKeysUniqueness(ArrayList <Object> foreignKeys, Object element) {
		if (!foreignKeys.isEmpty()) {
			for ( Object key: foreignKeys) {
				if ( key == element) {
					return false;
				}
			}
		}
		return true;
	}

}