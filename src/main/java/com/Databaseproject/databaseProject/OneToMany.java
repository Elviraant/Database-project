
//package com.Databaseproject.databaseProject;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Represent a correlation between two tables where the records of table1 can be
 * linked to multiple records of table2 but each record of table2 can be linked
 * only to one record of table1.
 */
public class OneToMany extends Correlation {

  private Column column;
  private int posF;

  public OneToMany(String name, Table table1, Table table2) {

    super(name, table1, table2);
    column = new Column(table2, true);
    column.createFkColumnName(table1);
    table1.setReferences(true);
    posF = table2.getColumnCounter();
    table2.setPositionOffFk(table1, posF);
    table2.setInvPositionOffFk(posF, table1);
  }

  /**
   * Fill the foreign key column with data.
   **/
  @Override
  public void fillForeignKeyColumn() {
    Column pKColumnOne = pKColumn1();
    printPrimaryKeyColumns();
    for (int i = 0; i < table2.getNumberOfRows(); i++) {
      Object pKey2 = getPKey2(i);
      boolean continueProcess = true;
      while (continueProcess) {
        Object key = printInsertionMessage(pKey2);
        int pos = pKColumnOne.findPKeyPosition(key);
        if (pos != -1) {
          column.getField().add(key);
          continueProcess = false;
        } else {
          Menu.printNonExistantKeyMessage();
          Menu.printTryAgainMessage();
        }
      }
    }
  }

  /**
   * Give user a range of options concerning an one-to-many relationship.
   */
  @Override
  public void viewProperties() {
    boolean continueProcess = true;
    while (continueProcess) {
      System.out.println();
      int choice = Menu.viewPopertiesMenu();
      switch (choice) {
        case 1:
          System.out.println();
          System.out.println(this.toString());
          System.out.println("This is an one-to-many relationship");
          System.out.println(
              "The table where one record can be linked to multiple records of "
              + "the other is: " + table1.getName());
          System.out.println();
          System.out.println(
              "The table where the records are linked only to one record of "
              + "the other table is: " + table2.getName());
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
