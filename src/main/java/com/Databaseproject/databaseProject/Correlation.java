
package com.Databaseproject.databaseProject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
*Represent a correlation of a base.
* Correlation exists between two tables of the base
* Correlation can be one-to-one, one-to-many, many-to-many
*/
public class Correlation implements Serializable {

  protected String name;
  protected Table table1; //entity/table 1
  protected Table table2; //entity/table 2

  /**
   *Constructor for Correlation class.
   * @param name name of correlationship between the entities.
   * @param table1 first entity.
   * @param table2 second entity.
   */

  public Correlation(String name, Table table1, Table table2) {
    this.name = name;
    this.table1 = table1;
    this.table2 = table2;
  }

  public String getName() {
    return this.name;
  }

  public Table getTable1() {
    return this.table1;
  }

  public Table getTable2() {
    return this.table2;
  }

  public String toString() {
    return table1.getName() + " " + name + " " + table2.getName();
  }

  public void fillForeignKeyColumn() {
  }

  public void viewProperties() {
  }

  public static void manageCorrelations(Database d1) {
    boolean continueProcess = true;
    ArrayList<Correlation> correlations = d1.getCorrelations();
    while (continueProcess) {
      int choice = Menu.manageCorrelationsMenu();
      switch (choice) {
        case 1:
          int number = correlations.size();
          System.out.println("You have created " + number + " correlation(s)");
          System.out.println();
          break;
        case 2:
          printCorrelations(correlations);
          System.out.println();
          break;
        case 3:
          Correlation.deleteCorrelation(correlations);
          break;
        case 4:
          if (correlations.size() != 0) {
            System.out.println("Choose the correlation you want to view");
            Correlation correlation = chooseCorrelation(correlations);
            if (correlation instanceof OneToOne) {
              correlation = (OneToOne) correlation;
            } else if (correlation instanceof ManyToMany) {
              correlation = (ManyToMany) correlation;
            } else {
              correlation = (OneToMany) correlation;
            }
            correlation.viewProperties();
          } else {
            System.out.println("This option has to do with linked entities");
            System.out.println("You should create at least one correlation");
            System.out.println();
          }
          break;
        default:
          continueProcess = false;
      }
    }
  }

  public static void printCorrelations(ArrayList<Correlation> correlations) {
    if (correlations.size() != 0) {
      for (int i = 0; i < correlations.size(); i++) {
        System.out.println(String.format("%s. %s", (i + 1), correlations.get(i).toString()));
      }
    } else {
      System.out.println("You have not created any correlations");
    }
  }


  public static Correlation chooseCorrelation(ArrayList<Correlation> correlations) {
    printCorrelations(correlations);
    System.out.println();
    int choice = Database.choice(1, correlations.size());
    return correlations.get(choice - 1);
  }

  /**
   *Delete a correlation of the base.
   *@param correlations the list of the correlations in the base.
   */

  public static void deleteCorrelation(ArrayList<Correlation> correlations) {
    if (correlations.size() != 0) {
      System.out.println("Please choose the correlation you want to delete");
      Correlation correlation = chooseCorrelation(correlations);
      int pos = correlations.indexOf(correlation);
      correlations.remove(pos);
      correlation.getTable2().getColumns().remove(correlation.fK());
      int counter = correlation.getTable2().getColumnCounter() - 1;
      correlation.getTable2().setColumnCounter(counter);
      correlation.getTable1().setReferences(false);
      if (correlation instanceof ManyToMany) {
        correlation.getTable1().getColumns().remove(((ManyToMany)correlation).fK1());
        counter = correlation.getTable1().getColumnCounter() - 1;
        correlation.getTable1().setColumnCounter(counter);
        correlation.getTable2().setReferences(false);
      }
    } else {
      System.out.println("No correlations found");
      System.out.println();
    }
  }

  /**
  * Ask user which is the table that he wants to search for related records
  * according to the choice and print the other table which contains the primary keys
  * returns number of chosen table.
  * @return choice int.
  */

  public int choice() {
    System.out.println("1. " + table1.getName());
    System.out.println("2. " + table2.getName());
    System.out.println("In which table do you want to search for related "
            + "records? (insert number of choice)");
    int choice = Database.choice(1,2);
    if (choice == 1) {
      table2.printAll();
    } else {
      table1.printAll();
    }
    System.out.println("Insert a primary key of the table: ");
    return choice;
  }

  /**
   *Searching applied to one-to-one and one-to-many relationships.
   *if user searches in table with foreigns,
   *finds row with primary, and compares the respective foreign with
   *primary keys of the other table.
   *if user searches in table without foreigns compares
   * the key with foreign fields of the other tables
   *and prints the respective row.
   */

  public void search() {
    Scanner cs = new Scanner(System.in);
    int choice = choice();
    if (choice == 1) {
      Column fColumn = fKColumn();
      fColumn.setFieldType(pKColumn1().getType());
      Object element = getKey(pKColumn2());
      ArrayList<Integer> rowsWanted = new ArrayList<>();
      ArrayList<Integer> primaryRow = pKColumn2().matchingRows(element);
      if (primaryRow.size() != 0) {
        System.out.println("Found in row " + primaryRow.get(0) + " of " + table2.getName());
        ArrayList<Object> foreigns = new ArrayList<Object>();
        for (Integer row: primaryRow) {
          foreigns.add(fColumn.getField().get(row));
          System.out.println("added foreign " + fKColumn().getField().get(row));
        }
        for (Object foreign : foreigns) {
          rowsWanted = pKColumn1().matchingRows(foreign);
        }
        printRelated(rowsWanted,table1);
      } else {
        System.out.println("No match found");
        System.out.println();
      }
    } else {
      Object element = getKey(pKColumn1());
      ArrayList<Integer> rows = fKColumn().matchingRows(element);
      printRelated(rows, table2);
    }
  }

  /**
   *Print the related rows of a record in a correlation.
   *@param rows related records.
   *@param table related table.
   */

  public void printRelated(ArrayList<Integer> rows, Table table) {
    if (rows.size() == 0) {
      System.out.println("No matching records found");
      System.out.println();
    } else {
      System.out.println("Related records with given primary key: ");
      System.out.println();
      System.out.println("TABLE: " + table.getName());
      table.specificRows(rows);
    }
  }

  public Object getKey(Column column) {
    System.out.println("Primary key: ");
    return column.getType().getData();
  }

  public int prKey1() {
    return table1.findPrimaryKeyColumn();
  }

  public int prKey2() {
    return table2.findPrimaryKeyColumn();
  }

  public Column fKColumn() {
    int pos = fK();
    return table2.getColumns().get(pos);
  }

  public int fK() {
    HashMap<Table, Integer> foreignKeyMapping = table2.getPositionOffFk();
    int pos = foreignKeyMapping.get(table1);
    return pos - 1;
  }

  public Column pKColumn1() {
    return table1.getColumns().get(prKey1());
  }

  public Column pKColumn2() {
    return table2.getColumns().get(prKey2());
  }

  public void printPrimaryKeyColumns() {
    table1.printPrimaryKeyColumn();
    System.out.println();
    table2.printPrimaryKeyColumn();
  }

  public Object getPKey2(int i) {
    return pKColumn2().getField().get(i);
  }

  public Object printInsertionMessage(Object pkey) {
    System.out.println("Insert the primary key that's correlated with " + pkey
        + " from table " + table2.getName() + " :");
    return pKColumn1().getType().getData();
  }

  public void printAlreadyCorrelatedMessage() {
    System.out.println("This record is already correlated with another "
            + "record from " + table2.getName() + "");
  }


}