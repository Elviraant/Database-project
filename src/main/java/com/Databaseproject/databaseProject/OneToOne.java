
//package com.Databaseproject.databaseProject;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represent a correlation between two Table objects where each record of tabl2
 * can be linked to one record of table1 between two Table objects.
 */
public class OneToOne extends Correlation {

  private Column column;
  private int posF;

  public OneToOne(String name, Table table1, Table table2) {
    super(name, table1, table2);
    column = new Column(table2, true);
    column.createFkColumnName(table1);
    table1.setReferences(true);
    posF = table2.getColumnCounter();
    table2.setPositionOffFk(table1, posF);
    table2.setInvPositionOffFk(posF, table1);
  }

  /**
   * Show options concerning the management of an one to one correlation
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
          System.out.println("This is an One-To-One correlation");
          System.out.println();
          System.out.println("Every record of a table should be linked to ecxactly one record of the other.");
          System.out.println();
          break;
        case 2:
          search();
          break;
        case 3:
          continueProcess = false;
          break;
      }
    }
  }

  /**
   * Fill foreignKeyColumn with Data.
   **/
  @Override
  public void fillForeignKeyColumn() {
    Column pKColumn = pKColumn1();
    printPrimaryKeyColumns();
    for (int i = 0; i < table2.getNumberOfRows(); i++) {
      Object pKey2 = getPKey2(i);
      boolean continueProcess = true;
      while (continueProcess) {
        Object key = printInsertionMessage(pKey2);
        int pos = pKColumn.findPKeyPosition(key);
        if (pos != -1) {
          boolean check = column.checkUniqueness(key);
          if (check) {
            column.getField().add(key);
            continueProcess = false;
          } else {
            printAlreadyCorrelatedMessage();
            Menu.printTryAgainMessage();
          }
        } else {
          Menu.printNonExistantKeyMessage();
          Menu.printTryAgainMessage();

        }
      }
    }
  }

}
