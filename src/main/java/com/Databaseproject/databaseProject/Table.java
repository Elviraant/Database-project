
//package com.Databaseproject.databaseProject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Represent a table of a database.
 */
public class Table implements Serializable {
  private ArrayList<Column> columns = new ArrayList<>();
  private int columnCounter = 0;
  private String name;
  private int numberOfRows = 0;
  private boolean references = false;
  private HashMap<Table, Integer> positionOffFk = new HashMap<Table, Integer>();
  private HashMap<Integer, Table> invPositionOffFk = new HashMap<Integer, Table>();

  /**
   * Create a Table for Database and add it to its Database tables ArrayList. with
   * specified name
   *
   * @param name
   *          name of this table.
   * @param d1
   *          Database that belongs to.
   */

  public Table(String name, Database d1) { /* checkstyle checked */
    this.name = name;
    d1.getTables().add(this);
    int counter = d1.getTableCounter();
    counter++;
    d1.setTableCounter(counter);
  }

  public ArrayList<Column> getColumns() { /* checkstyle checked setters and getters */
    return columns;
  }

  public void setColumns(ArrayList<Column> columns) {
    this.columns = columns;
  }

  public int getColumnCounter() {
    return columnCounter;
  }

  public void setColumnCounter(int columnCounter) {
    this.columnCounter = columnCounter;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getNumberOfRows() {
    return numberOfRows;
  }

  public void setNumberOfRows(int numberOfRows) {
    this.numberOfRows = numberOfRows;
  }

  public HashMap<Table, Integer> getPositionOffFk() {
    return positionOffFk;
  }

  public void setPositionOffFk(Table key, Integer value) {
    positionOffFk.put(key, value);
  }

  public HashMap<Integer, Table> getInvPositionOffFk() {
    return invPositionOffFk;
  }

  public void setInvPositionOffFk(Integer value, Table key) {
    invPositionOffFk.put(value, key);
  }

  public boolean getReferences() {
    return references;
  }

  public void setReferences(boolean references) {
    this.references = references;
  }

  /**
   * public HashMap<Table, Integer> getPositionOfFK() { return positionOffFk; }
   **/

  /**
   * Create fields for a Table.
   */

  public void setUpColumns() {
    Column rows = new Column("Record", new IntegerType(), this);
    setFieldNames();
  }

  /**
   * Set the Column objects' names until the user inserts EXIT.
   */

  public void setFieldNames() { /* checkstyle checked */
    System.out.println("Set the name of the field that you want to create\nEnter EXIT to stop");
    String nameOfField = nameAColumn();
    nameOfField = uniqueFieldName(nameOfField);
    while ((nameOfField.equals("EXIT")) && (columnCounter < 2)) {
      Menu.printDeletePath();
      nameOfField = nameAColumn();
      nameOfField = uniqueFieldName(nameOfField);
    }
    while (!nameOfField.equals("EXIT")) {
      setFieldType(nameOfField);
      nameOfField = nameAColumn();
      nameOfField = uniqueFieldName(nameOfField);
    }
    System.out.println();
  }

  /**
   * Ask user to define Column object's type of field.
   *
   * @param nameOfField
   *          Column object's name.
   */
  public void setFieldType(String nameOfField) {

    System.out.println("Please choose one of the following data types:");
    System.out.println();
    int choice = Menu.fieldTypesMenu();

    if (choice == 1 || choice == 2 || choice == 3) {
      FieldType type = Column.findType(choice);
      new Column(nameOfField, type, this);
    } else if (choice == 4) {
      EnumeratedType type = new EnumeratedType();
      type.defineEnumeration(nameOfField);
      new Column(nameOfField, type, this);
    }
  }

  /**
   * Set a Column object's name.
   *
   * @return String - Column object's name.
   */
  public String nameAColumn() {
    Scanner sc = new Scanner(System.in);
    System.out.print("#" + columnCounter + " Field Name: ");
    String nameOfField = sc.next();
    System.out.println();
    return nameOfField;
  }

  /*
   * Check the Column object's name uniqueness in the Table and ask user to insert
   * again until it is unique
   *
   * @param nameOfField name for checking
   *
   * @return String tested name
   */
  public String uniqueFieldName(String nameOfField) {
    Scanner sc = new Scanner(System.in);
    int check = this.containsName(nameOfField);
    while (check != -1) {
      System.out.println("That name already exists. Please try with another: ");
      nameOfField = sc.next();
      check = this.containsName(nameOfField);
    }
    return nameOfField;
  }

  /**
   * Add a Column object which is primary key in the Table.
   *
   * @return Table - the Table with the primary key Column object.
   */
  public Table addPrimaryKeyColumn() {
    String name = nameAColumn();
    uniqueFieldName(name);
    setFieldType(name);
    columns.get((columnCounter - 1)).setPrimaryKey(true);
    columnFillerByColumn();
    return this;
  }

  /**
   * Define which existent Column is the primary Key field.
   */
  public void definePrimaryKey() {
    Scanner cs = new Scanner(System.in);
    System.out.println("Do you want to set a field as primary key? ");
    boolean continueProcess;
    continueProcess = Database.findDecision();
    while (continueProcess) {
      System.out.print("Please, insert the name of the primary key field: ");
      String primaryKeyName = cs.next();
      int exists = this.containsName(primaryKeyName);

      if (exists != -1) {
        if (checkOwnType(primaryKeyName)) {
          continueProcess = true;
          System.out.println("Enumerated type Column can not be Primary Key. Please try again. ");
        } else {
          this.getColumns().get(exists).setPrimaryKey(true);
          continueProcess = false;
        }
      } else {
        System.out.print(primaryKeyName + ". No such field at this entity.");
        System.out.print("Do you want to try again? ");
        continueProcess = Database.findDecision();
      }
    }

  }

  /**
   * Check by name if a Column object's type is EnumeratedType.
   *
   * @param name
   *          name of Column object.
   * @return boolean true if this Column object's type is EnumeratedType; false
   *         otherwise.
   */
  public boolean checkOwnType(String name) {
    boolean ownType = false;
    for (Column column : columns) {
      if (column.getName().equals(name)) {
        if (column.getType() instanceof EnumeratedType) {
          ownType = true;
        }
      }
    }
    return ownType;
  }

  /**
   * Ask how to fill the fields and call suitable methods.
   */
  public void callFiller() {
    System.out.println("How would you like to fill the table?");
    System.out.println("1. By row\n2. By column");
    int filltype = Database.choice(1, 2);
    if (filltype == 1) {
      this.columnFillerByRow();
    } else if (filltype == 2) {
      this.columnFillerByColumn();
    }
    System.out.println("");
  }

  /**
   * Fill Column objects' fields by row.
   */
  public void columnFillerByRow() {
    if (foreignKeyColumnExists()) {
      System.out.println("This tables refers to another table and you can't add data");
    } else {
      boolean continueProcess = true;
      while (continueProcess) {
        addRow();
        numberOfRows++;
        System.out.println("Do you want to continue? Y/N");
        continueProcess = Database.findDecision();
      }
    }
    // numberOfRows+= insertions;
  }

  /**
   * Add a record in the Table.
   */
  public void addRow() {
    System.out.println("#" + (numberOfRows + 1) + " Record: ");
    for (int i = 0; i < columnCounter; i++) {
      Column column = getColumns().get(i);
      if (this.getColumns().get(0).equals(column)) { // if is the Record field
        column.getField().add(numberOfRows + 1);
      } else {
        System.out.println("Insert " + column.getName());
        Object data = column.getType().getData();
        if (column.getPrimaryKey()) { // if is primary key
          column.fillPrimaryKeyField(data);
        } else {
          column.getField().add(data);
          // neither foreign, nor primary
        }
      }

    }

  }

  /**
   * Fill Column objects' fields by column.
   */
  public void columnFillerByColumn() {

    if (numberOfRows == 0) {
      System.out.println("How many records would you like to fill for " + this.getName());
      numberOfRows = Database.valid();
    }
    ArrayList<String> attributes = new ArrayList<String>();
    for (Column column : columns) {
      if (column.getField().isEmpty() && !column.getForeignKey()) {
        attributes.add(column.getName());
        if (column.getPrimaryKey()) {
          printHeaderOfSpecificColumns(attributes);
          for (int j = 0; j < numberOfRows; j++) {
            System.out.print("#" + (j + 1) + " Record: ");
            Object data = column.getType().getData();
            column.fillPrimaryKeyField(data);
          }
        } else if (columns.get(0).equals(column)) {
          for (int i = 0; i < numberOfRows; i++) {
            column.getField().add(i + 1);
            // fill the record column.
          }
        } else {
          printHeaderOfSpecificColumns(attributes);
          for (int j = 0; j < numberOfRows; j++) {
            System.out.print("#" + (j + 1) + " Record: ");
            Object data = column.getType().getData();
            column.getField().add(data);
          }
        }
        attributes.remove(0);
      }
      System.out.println();
    }

  }

  /**
   * Call method for presentation, changing or deletion according to user's choice
   * if table is empty processes cannot be done.
   */
  public void manageData() {
    if (columnCounter != 0) {
      boolean continueProcess = true;
      while (continueProcess) {
        int choice = Menu.startingMenu();
        switch (choice) {
          case 1:
            presentData();
            break;
          case 2:
            changeData();
            break;
          case 3:
            deleteData();
            break;
          case 4:
            moreOptions();
            break;
          default:
            continueProcess = false;
        }
      }
    } else {
      System.out.println("There are no columns to be processed in this table.");
    }
  }

  /**
   * Call method for sorting, adding data, searching findind max, min element or
   * view design according to user's choice.
   */
  public void moreOptions() {
    boolean continueProcess = true;
    while (continueProcess) {
      int choice = Menu.moreOptionsMenu();
      if (choice == 2) {
        addData();
      }
      if (numberOfRows > 0) {
        switch (choice) {
          case 1:
            sortData();
            break;
          case 2:
            addData();
            break;
          case 3:
            searchData();
            break;
          case 4:
            findMaxData();
            break;
          case 5:
            findMinData();
            break;
          case 6:
            designView();
            break;
          default:
            continueProcess = false;
        }
      } else {
        continueProcess = false;
        System.out.println("This table is empty.");
      }
    }
  }

  /**
   * Call method concerning data deletion according to user's choice.
   */

  public void deleteData() {
    int choice;
    boolean continueProcess = true;
    while (continueProcess) {
      if (numberOfRows > 0) {
        Menu.deletionMenu();
        choice = Database.choice(1, 5);
        switch (choice) {
          case 1:
            if (!references) {
              deleteRows();
              if (numberOfRows > 0) {
                System.out.println("Deletion completed successfully");
              }
            } else {
              System.out.println("This table is refered by another table of your base");
            }
            break;
          case 2:
            deleteColumns();
            break;
          case 3:
            printAll();
            deleteElements();
            break;
          case 4:
            printAll();
            deleteAll();
            break;
          default:
            manageData();
            break;
        }
        if ((choice >= 1) && (choice < 5)) {
          if (numberOfRows > 0) {
            System.out.println("Continue with the deletion of data?");
            continueProcess = Database.findDecision();
          }
        } else {
          continueProcess = false;
        }
      } else {
        continueProcess = false;
      }
    }
  }

  /**
   * Call method concerning data presentation according to user's choice.
   */
  public void presentData() {
    int choice;
    boolean continueProcess = true;
    while (continueProcess) {
      if (numberOfRows > 0) {
        Menu.presentationMenu();
        choice = Database.choice(1, 4);
        switch (choice) {
          case 1:
            printAll();
            break;
          case 2:
            printRangeOfRows();
            break;
          case 3:
            printSpecificColumns();
            break;
          default:
            manageData();
            break;
        }
        if ((choice >= 1) && (choice < 4)) {
          System.out.println("Continue with the presentation of data?");
          continueProcess = Database.findDecision();
        } else {
          continueProcess = false;
        }
      } else {
        continueProcess = false;
        System.out.println("This table is empty");
      }
    }
  }

  /**
   * Call method concerning data changing according to user's choice.
   */
  public void changeData() {
    int choice;
    boolean decision;
    do {
      Menu.changeMenu();
      choice = Database.choice(1, 5);
      switch (choice) {

        case (1):
          if (columnCounter > 0) {
            changeFieldName();
          } else {
            System.out.println("No existing fields");
          }
          break;

        case (2):
          if (numberOfRows > 0) {
            changeValue();
          } else {
            System.out.println("Table is empty");
          }
          break;

        case (3):
          if (numberOfRows > 0) {
            changeDataByRow();
          } else {
            System.out.println("Table is empty");
          }
          break;
        case (4):
          if (numberOfRows > 0) {
            sameValue();
          } else {
            System.out.println("Table is empty");
          }
          break;
        default:
          decision = false;
          break;
      }
      if ((choice >= 1) && (choice < 5)) {
        System.out.println("Continue with the changing of data? Yes/No");
        decision = Database.findDecision();
      } else {
        decision = false;
      }
    } while (decision);
  }

  /**
   * Call method concerning data assortment according to user's choice.
   */

  public void sortData() {
    int choice = 0;
    boolean continueProcess = true;
    while (continueProcess) {
      if (numberOfRows > 1) {
        Menu.assortmentMenu();
        choice = Database.choice(1, 2);
        switch (choice) {
          case 1:
            printAll();
            chooseSort();
            break;
          default:
            continueProcess = false;
            break;
        }
      }
      if (choice == 1) {
        System.out.println("Assortment completed successfully");
        System.out.println("Continue with the assortment of data?");
        continueProcess = Database.findDecision();
      } else {
        System.out.println("Not enough records");
        continueProcess = false;
      }
    }
  }

  /**
   * Call method concerning data addition according to user's choice.
   */
  public void addData() {
    int choice;
    boolean continueProcess = true;
    while (continueProcess) {
      Menu.additionMenu();
      choice = Database.choice(1, 3);
      switch (choice) {
        case 1:
          if (columnCounter > 0) {
            System.out.println("Until now, these are your records: ");
            System.out.println();
            printAll();
            columnFillerByRow();
          } else {
            System.out.println("You have to define fields first");
          }
          break;
        case 2:
          if (columnCounter > 0) {
            System.out.print("Until now, you have these columns: ");
            System.out.println();
            printHeader();
            setFieldNames();
          } else if (columnCounter == 0) {
            setUpColumns();
          }
          columnFillerByColumn();
          break;
        default:
          continueProcess = false;
          break;
      }
      if ((choice == 1) || (choice == 2)) {
        System.out.println("Adding completed successfully");
        System.out.println("Continue with the adding of data?");
        continueProcess = Database.findDecision();
      } else {
        continueProcess = false;
      }
    }
  }

  /**
   * Search for the element given and print all the positions found else print
   * suitable message.
   */
  public void searchData() {
    Menu.searchingMenu();
    Boolean answer = Database.findDecision();
    Boolean continueProcess = true;
    do {
      if (answer) {
        int pfield = inputFieldName("search for element");
        if (pfield != -1) {
          Column col = getColumns().get(pfield);
          System.out.println("Enter the element you want to search");
          Object element = col.getType().getData();
          ArrayList<Integer> list = col.matchingRows(element);
          if (list.size() != 0) {
            col.searchElement(element);
            for (int i = 0; i < list.size(); i++) {
              presentRow(list.get(i));
            }
          } else {
            col.searchElement(element);
          }
          System.out.println("Search Data completed successfully");
        }
      }
      System.out.println("Continue with the searching of data?");
      continueProcess = Database.findDecision();
    } while (continueProcess);
  }

  /**
   * Find maximum value of an element in a specific field given by the user and
   * print all the positions where it is found.
   */
  public void findMaxData() {
    Boolean continueProcess = true;
    do {
      int pfield = inputFieldName("find the maximum value");
      if (pfield != -1) {
        Column col = this.getColumns().get(pfield);
        Object max = Collections.max(col.getField(), null);
        System.out.print("The maximum value is : ");
        ArrayList<Integer> list = col.matchingRows(max);
        if (list.size() != 0) {
          col.searchElement(max);
          for (int i = 0; i < list.size(); i++) {
            presentRow(list.get(i));
          }
        } else {
          col.searchElement(max);
        }
      }
      System.out.println("Finding Maximum Data completed successfully");
      System.out.println("Continue with the finding Maximum Data?");
      continueProcess = Database.findDecision();
    } while (continueProcess);
  }

  /**
   * Find minimum value of an element in a specific field given by the user and
   * print all the positions where it is found.
   */
  public void findMinData() {
    Boolean continueProcess = true;
    do {
      int pfield = inputFieldName("find the minimum value");
      if (pfield != -1) {
        Column col = this.getColumns().get(pfield);
        Object min = Collections.min(col.getField(), null);
        System.out.print("The minimum value is : ");
        ArrayList<Integer> list = col.matchingRows(min);
        if (list.size() != 0) {
          col.searchElement(min);
          for (int i = 0; i < list.size(); i++) {
            presentRow(list.get(i));
          }
        } else {
          col.searchElement(min);
        }
      }
      System.out.println("Finding Minimum Data completed successfully");
      System.out.println("Continue with finding Minimum Data?");
      continueProcess = Database.findDecision();
    } while (continueProcess);
  }

  /**
   * Print all table insertions and the names of columns.
   */
  public void printAll() {
    if (numberOfRows != 0) {
      System.out.println();
      System.out.println("Table: " + this.name);
      printHeader();
      for (int k = 0; k < numberOfRows; k++) {
        presentRow(k);
      }
    } else {
      System.out.println("No records in this table");
    }
  }

  /**
   * Print the header with the names of all columns.
   */
  public void printHeader() {
    ArrayList<String> list = new ArrayList<String>();
    for (int i = 0; i < columnCounter; i++) {
      Column column = columns.get(i);
      if (column.getForeignKeys().size() == 0) {
        list.add(column.getName());
      }
    }
    printHeaderOfSpecificColumns(list);
  }

  /**
   * Print a row of the table and if a column is foreign key in a many-to-many
   * relationship, it is not printed.
   *
   * @param row
   *          position in the arraylist of fields.
   */
  public void presentRow(int row) {
    for (int i = 0; i < columnCounter; i++) {
      Column column = columns.get(i);
      if (column.getForeignKeys().size() == 0) {
        if (i == 0) {
          System.out.print(String.format("|%-6s|", columns.get(0).getField().get(row).toString()));
          System.out.print("     ");
        } else {
          column.printElement(row);
        }
      }
    }
    System.out.println();
  }

  /**
   * Present specific rows within a range given by the user.
   */
  public void printRangeOfRows() {
    if (numberOfRows != 0) {
      System.out.println("Please insert the range of records you want to print.");
      int start = 1;
      int end = 0;
      while (start >= end) {
        System.out.println("Starting record: ");
        start = Database.choice(1, numberOfRows) - 1;
        System.out.println("Ending record: ");
        end = Database.choice(1, numberOfRows);
        if (start >= end) {
          System.out.println("Starting can't be greater than ending record");
        }
      }
      printHeader();
      for (int i = start; i < end; i++) {
        presentRow(i);
      }
    } else {
      System.out.println("No records in this table");
    }
  }

  /**
   * Print specific rows of the table.
   *
   * @param rows
   *          given.
   */
  public void specificRows(ArrayList<Integer> rows) {
    printHeader();
    for (Integer row : rows) {
      presentRow(row);
    }
  }

  /**
   * Get the names of the columns by the user and put them in an ArrayList.
   *
   * @return ArrayList of the columns given by the user.
   */
  public ArrayList<String> inputSpecificColumns() {
    ArrayList<String> attributes = new ArrayList<String>();
    attributes.add("Record");
    boolean continueProcess = true;
    String attribute;
    int position = -1;

    if (numberOfRows != 0) {
      while (continueProcess) {
        position = inputFieldName("be presented");
        if (position != -1) {
          Column col = columns.get(position);
          attributes.add(col.getName());
        }
        System.out.println("%nDo you want to present another column? Y/N");
        continueProcess = Database.findDecision();

      }
    } else {
      System.out.println("%nNo records in this table.%n");
    }
    return attributes;
  }

  /**
   * Print columns according to a list given by the user.
   */
  public void printSpecificColumns() {
    ArrayList<String> attributes = inputSpecificColumns();
    if (attributes.size() == 1) {
      System.out.println("No records in this table.");
    } else {
      this.presentColumns(attributes);
    }
  }

  /**
   * Print specific names of columns as header to a table.
   *
   * @param attributes
   *          the list of field names given by the user or the programmer.
   */
  public void printHeaderOfSpecificColumns(ArrayList<String> attributes) {
    int spaces = 0;
    String title = "";
    for (String attribute : attributes) {
      if (attribute.equals("Record")) {
        title = String.format("|%-6s|", attribute);
      } else {
        title = String.format("|%-15s|", attribute);
      }
      System.out.print(title);
      spaces = spaces + title.length() + 5;
      System.out.print("     ");
    }
    System.out.println();
    for (int i = 0; i < spaces - 5; i++) {
      System.out.print("-");
    }
    System.out.println();
  }

  /**
   * Find the position of the column searching by to the name given.
   *
   * @param name
   *          name of field.
   * @return int - returns -1 if the column's name is Record or it doesn't exist.
   */
  public int containsName(String name) {
    if (name.equals("Record")) {
      return -1;
    }
    for (int i = 0; i < columnCounter; i++) {
      Column c = getColumns().get(i);
      if (c.getName().equals(name)) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Present columns according to a list of attributes.
   *
   * @param attributes
   *          the list of the attributes.
   */
  public void presentColumns(ArrayList<String> attributes) {
    printHeaderOfSpecificColumns(attributes);
    for (int i = 0; i < numberOfRows; i++) {
      for (String a : attributes) {
        Column column = columns.get(0);
        if (a != "Record") {
          column = columns.get(containsName(a));
          column.printElement(i);
        } else {
          System.out.print(String.format("|%-6s|", columns.get(0).getField().get(i).toString()));
          System.out.print("     ");
        }
      }
      System.out.println();
    }
  }

  /**
   * Insert name of field by user return its position, if it exists , else return
   * -1.
   *
   * @param function
   *          the process to be done in field.
   * @return int.
   */
  public int inputFieldName(String function) {
    printHeader();
    System.out.println("Give the name of the field you want to " + function + ".");
    StringType name = new StringType();
    String nameofField = name.getData();
    int ex = this.containsName(nameofField);
    if (ex == -1) {
      if (nameofField.equals("Record")) {
        System.out.println("You are not allowed to process field Record");
      } else {
        System.out.println("This name of field doesn't exist in your Data Base");
      }
      Boolean answer = Menu.printTryAgainQuestionMessage();
      if (answer) {
        return inputFieldName(function);
      }
    }
    return ex;
  }

  /**
   * Change values of elements by row until the user wants to stop.
   */
  public void changeDataByRow() {
    System.out.println("Which record do you want to change?");
    printAll();
    int row = Database.choice(1, numberOfRows) - 1;
    if (row != -1) {
      Boolean continueProcess = true;
      int i = 1;
      System.out.println("#" + (row + 1) + " Record: "); // checks if record input is valid and
      //keeps position of element.
      do {
        Column col = columns.get(i);
        System.out.println("#" + (i) + " Field: ");
        Boolean answer = false;
        if (col.getForeignKey()) {
          Menu.printColumnRefersMessage("change its element");
        } else if (col.getPrimaryKey() && references) {
          Menu.printColumnReferredMessage("change its element");
        } else {
          System.out.println("Give the new value of " + col.getName());
          do {
            Object nValue = col.getType().getData();
            if (col.getPrimaryKey()) {
              if (col.checkUniqueness(nValue)) {
                col.getField().set(row, nValue);
                System.out.println("Change completed successfully");
                answer = false;
              } else {
                System.out.println("This value of primary key already exists.");
                answer = Menu.printTryAgainQuestionMessage();
                if (answer) {
                  System.out.println("Enter new value again :");
                }
              }
            } else {
              col.getField().set(row, nValue);
              System.out.println("Change completed successfully.");
              answer = false;
            }
          } while (answer);
        }
        if (i != columnCounter - 1) {
          System.out.println("Do you want to continue? (Yes/No)");
          continueProcess = Database.findDecision();
          if (!continueProcess) {
            i = columnCounter;
          }
        }
        i++;
      } while (i <= columnCounter - 1);
    } else {
      System.out.println("The record you typed is probably incorrect.");
    }
  }

  /**
   * Change existent name of field given by the user with an non-existent name.
   */
  public void changeFieldName() {
    StringType name = new StringType();
    int pos = this.inputFieldName("change");
    Boolean answer = true;
    if (pos != -1) {
      Column col = this.getColumns().get(pos);
      if (col.getForeignKey()) {
        Menu.printColumnRefersMessage("change its name");
      } else {
        while (answer) {
          System.out.println("Give the new name of the field");
          String newName = name.getData();
          int k = this.containsName(newName);
          if (k == -1) {
            col.setName(newName);
            answer = false;
            System.out.println("Change completed successfully");
          } else {
            System.out.println("This name is already in use.");
            answer = Menu.printTryAgainQuestionMessage();
          }
        }
      }
    }
  }

  /**
   * Find foreign key's Column position and return -1 if it doesn't exist.
   *
   * @return int.
   */
  public int findPrimaryKeyColumn() {
    int j = 0;
    int exprimarykey = -1;
    do {
      Column col = columns.get(j);
      if (col.getPrimaryKey()) {
        exprimarykey = j;
      } else {
        j++;
      }
    } while (exprimarykey == -1 && j < columnCounter);
    return exprimarykey;
  }

  /**
   * Find foreign key's Column position and return -1 if it doesn't exist.
   *
   * @return int.
   */
  public int findForeignKeyColumn() {

    int posF = -1;
    boolean cont = false;

    for (Column c : columns) {
      if (c.getForeignKey()) {
        return posF = columns.indexOf(c);
      }
    }
    return -1;
  }

  public boolean foreignKeyColumnExists() {
    for (Column c : columns) {
      if (c.getForeignKey()) {
        return true;
      }
    }
    return false;
  }

  public boolean primaryKeyColumnExists() {
    boolean exists = false;
    for (Column column : columns) {
      if (column.getPrimaryKey()) {
        exists = true;
      }
    }
    return exists;
  }

  /**
   * Change value of element in a field if the Column doesn't refer to or is
   * referred by another Column.
   */
  public void changeValue() {
    int pfield = inputFieldName("change");
    if (pfield != -1) {
      System.out.println("Which record do you want to change?");
      printAll();
      int row = Database.choice(1, numberOfRows) - 1;
      if (row != -1) {
        Column x = columns.get(pfield);
        if (x.getForeignKey()) {
          Menu.printColumnRefersMessage("be changed");
        } else if (x.getPrimaryKey() && references) {
          Menu.printColumnReferredMessage("be changed");
        } else {
          System.out.println("Enter the new value of element you want to change :");
          Boolean answer = false;
          do {
            Object nValue = x.getType().getData();
            if (x.getPrimaryKey()) {
              if (x.checkUniqueness(nValue)) {
                x.getField().set(row, nValue);
                System.out.println("Change completed successfully");
                answer = false;
              } else {
                System.out.println("This value of primary key already exists.");
                answer = Menu.printTryAgainQuestionMessage();
                if (answer) {
                  System.out.println("Enter new value again :");
                }
              }

            } else {
              x.getField().set(row, nValue);
              System.out.println("Change completed successfully.");
              answer = false;
            }
          } while (answer);
        }
      } else {
        System.out.println("The record you typed is probably incorrect.");
      }
    }
  }

  /**
   * Replace all elements of a field with the same value, if the Column isn't
   * primary or foreign key.
   */
  public void sameValue() {
    int pfield = inputFieldName("change");
    if (pfield != -1) {
      Column col = this.getColumns().get(pfield);
      if (col.getPrimaryKey()) {
        System.out.println("This field contains Primary Keys."
                + " You are not allowed to set same values.");
      } else if (col.getForeignKey()) {
        Menu.printColumnRefersMessage("be set with same values");
      } else {
        System.out.println("Insert the new value of all elements");
        Object newValue = col.getType().getData();
        for (int i = 0; i < col.getField().size(); i++) {
          col.getField().set(i, newValue);
        }
        System.out.println("Change completed successfully !");
      }
    }
  }

  /**
   * Call method concerning rows' deletion according to user's choice.
   */
  public void deleteRows() {
    if (numberOfRows != 0) {
      System.out
          .println(String.format("%s%n%s%n", "1. Delete Specific Records",
                  "2. Delete specific range of records"));
      System.out.println("Please choose one of the above options:");
      int choice;
      choice = Database.choice(1, 2);
      switch (choice) {
        case 1:
          printAll();
          deleteSpecificRows();
          break;
        case 2:
          printAll();
          deleteSpecificRangeofRows();
          break;
        default:
          deleteData();
          break;
      }
    } else {
      System.out.println("No records to delete.");
    }
  }

  /**
   * Delete the row given.
   *
   * @param row
   *          - the row given.
   */
  public void deleteRow(int row) {
    for (int k = 0; k < columnCounter; k++) {
      Column column = columns.get(k);
      column.getField().remove(row - 1);
    }
    numberOfRows--;
    for (int i = 0; i < numberOfRows; i++) {
      columns.get(0).getField().set(i, i + 1);
    }
  }

  /**
   * Delete rows.
   */
  public void deleteSpecificRows() {
    boolean continueProcess = true;
    int counter = 0;
    while (continueProcess) {
      System.out.println("Which record do you want to delete?");
      int x = Database.choice(1, numberOfRows);
      deleteRow(x);
      printAll();
      if (numberOfRows != 0) {
        System.out.println("Delete another record?");
        continueProcess = Database.findDecision();
      } else {
        System.out.println("Deletion completed successfully.\nYou can't delete another record");
        continueProcess = false;
      }
    }
  }

  /**
   * Delete a specific range of rows given by the user.
   */
  public void deleteSpecificRangeofRows() {
    System.out.println("Please insert the range of records you want to delete.");
    int startRow;
    int endRow;
    do {
      System.out.println("Starting record: ");
      startRow = Database.choice(1, numberOfRows);
      System.out.println("Ending record: ");
      endRow = Database.choice(1, numberOfRows);
      if (startRow > endRow) {
        System.out.println("Starting can't be greater than ending record");
      }
    } while (startRow > endRow);
    int counter = 0;
    for (int j = startRow; j <= endRow; j++) {
      deleteRow(j - counter);
      counter++;
    }
    printAll();
  }

  /**
   * Delete a Column, if it isn't foreign key.
   */
  public void deleteColumns() {
    boolean continueProcess = true;
    int y = 0;
    while (continueProcess) {
      if ((columnCounter == 2) && (primaryKeyColumnExists())) {
        System.out.println("No columns to delete.");
        y = 1;
      } else {
        int x = inputFieldName("delete");
        if (x != -1) {
          Column column = columns.get(x);
          if (column.getPrimaryKey()) {
            System.out.println("This column is primary key. Cannot be deleted!");
          } else if (column.getForeignKey()) {
            Menu.printColumnRefersMessage("be deleted");
            System.out.println("To delete this column please "
                    + "delete the " + "correlation between the tables");
          } else {
            columns.remove(x);
            columnCounter--;
            printAll();
            if (!primaryKeyColumnExists()) {
              if (columnCounter == 1) {
                columns.remove(0);
                columnCounter = 0;
                numberOfRows = 0;
                System.out.println("You deleted all the columns of the table");
                continueProcess = false;
                y = 1;
              }
            } else {
              if (columnCounter == 2) {
                System.out.println("Deletion completed"
                        + " successfully.\n" + "You can't delete another column.");
                continueProcess = false;
                y = 1;
              }
            }
          }
        }
      }
      if (y == 0) {
        System.out.println("Delete another column?");
        continueProcess = Database.findDecision();
      } else {
        continueProcess = false;
      }
    }
  }

  /**
   * Delete an element of a field , if it is not primary ot foreign key. If it's
   * primary the user is able to delete the whole record
   */
  public void deleteElements() {
    Scanner cs = new Scanner(System.in);
    boolean continueProcess = true;
    boolean continueProcessPrimaryKey = false;
    while (continueProcess) {
      int x;
      do {
        System.out.println("Please insert the name of the column in which the element exists: ");
        String name = cs.next();
        x = containsName(name);
      } while (x == -1);
      Column column = this.getColumns().get(x);
      System.out.println("Please insert the row in which the element exists: ");
      int y = Database.choice(1, numberOfRows);
      if (column.getPrimaryKey()) {
        if (!references) {
          System.out.println("This element is a primary key. If you delete it the whole row "
              + "will be deleted.\nAre you sure you want to continue? Y/N");
          continueProcessPrimaryKey = Database.findDecision();
          if (continueProcessPrimaryKey) {
            deleteRow(y);
            printAll();
          }
        } else {
          Menu.deletionFailed(this);
        }
      } else {
        column.getField().set(y - 1, " ");
        System.out.println("Deletion completed successfully");
        printAll();
      }
      System.out.println("Do you want to delete another element? Y/N");
      continueProcess = Database.findDecision();
    }
  }

  /**
   * Delete a whole table.
   */
  public void deleteAll() {
    if (!getReferences()) {
      for (int i = columnCounter - 1; i >= 0; i--) {
        columns.remove(i);
      }
      columnCounter = 0;
      numberOfRows = 0;
      System.out.println("Deletion completed successfully");
    } else {
      Menu.deletionFailed(this);
    }
  }

  /**
   * Compare two given elements If they are equal, returns 0 If the first given
   * Object is greater than the second, returns a positive number If the first
   * given Object is less than the second, returns a negative number.
   *
   * @param str1
   *          Object.
   * @param str2
   *          Object.
   * @return int.
   */
  public int compareStrings(Object str1, Object str2) {
    String st1 = String.valueOf(str1);
    String st2 = String.valueOf(str2);
    return st1.compareTo(st2);
  }

  /**
   * Compare two given elements If they are equal, returns 0. If the first given
   * Object is greater than the second, returns 1. If the first given Object is
   * less than the second, returns -1.
   *
   * @param str1
   *          Object
   * @param str2
   *          Object
   * @return int
   */
  public int compareIntegers(Object str1, Object str2) {
    int st1 = (int) str1;
    int st2 = (int) str2;
    if (st1 > st2) {
      return 1;
    } else if (st1 < st2) {
      return -1;
    } else {
      return 0;
    }
  }

  /**
   * Compare two given elements If they are equal, returns 0 If the first given
   * Object is greater than the second, returns a positive number If the first
   * given Object is less than the second, returns a negative number.
   *
   * @param str1
   *          Object.
   * @param str2
   *          Object.
   * @return int.
   */
  public int compareDoubles(Object str1, Object str2) {
    String st1 = String.valueOf(str1);
    String st2 = String.valueOf(str2);
    st1 = st1.replace(",", ".");
    st2 = st2.replace(",", ".");
    double st11 = Double.parseDouble(st1);
    double st22 = Double.parseDouble(st2);
    return Double.compare(st11, st22);
  }

  /**
   * Sort the table according to the assortment chosen by the user based on a
   * Column given by the user.
   */
  public void chooseSort() {
    Scanner cs = new Scanner(System.in);
    int x;
    do {
      System.out.println("Please insert the name of the column on"
              + " which you want the assortment to be based on: ");
      String name = cs.next();
      x = containsName(name);
    } while (x == -1);
    System.out.println("Sort in ascending or descending "
            + "order?\n1.Ascending order\n2.Descending order");
    int choice;
    choice = Database.choice(1, 2);
    Column column = this.getColumns().get(x);
    for (int i = 1; i < numberOfRows; i++) {
      for (int j = numberOfRows - 1; j >= i; j--) {
        Object str1 = column.getField().get(j - 1);
        Object str2 = column.getField().get(j);
        if (column.getType() instanceof StringType) {
          int result = compareStrings(str1, str2);
          sort(result, j, choice);
        }
        if (column.getType() instanceof IntegerType) {
          int result = compareIntegers(str1, str2);
          sort(result, j, choice);
        }
        if (column.getType() instanceof DoubleType) {
          int result = compareDoubles(str1, str2);
          sort(result, j, choice);
        }
      }
    }
    printAll();
  }

  /*
   * Sort all columns
   *
   * @param result comparison's result
   *
   * @param j Column object's position
   *
   * @param choice user's assortment choice
   */
  public void sort(int result, int j, int choice) {
    for (int k = 1; k < columnCounter; k++) {
      Column column1 = this.getColumns().get(k);
      if (column1.getForeignKeys().isEmpty()) {
        Object s1 = column1.getField().get(j - 1);
        Object s2 = column1.getField().get(j);
        if (choice == 1) {
          if (result > 0) {
            column1.sortInAscendingOrder(j, s1, s2);
          }
        } else {
          if (result < 0) {
            column1.sortInDescendingOrder(j, s1, s2);
          }
        }
      } else {
        column1.sortForeignKeysColumn(result, j, choice);
      }

    }
  }

  /**
   * Print the primary key Column of the Table, if it exists. 
   */
  public void printPrimaryKeyColumn() {
    if (primaryKeyColumnExists()) {
      System.out.println("Table: " + name + "");
      int pos = findPrimaryKeyColumn();
      ArrayList<String> print = new ArrayList<String>();
      print.add(columns.get(pos).getName());
      presentColumns(print);
    }
  }

  /**
   * Present number of Table's columns and Table's records and each Table's Column.
   */
  public void designView() {

    System.out
        .println("Table: " + name + "\n Number of Columns: "
        + columnCounter + "\n Number of Records: " + numberOfRows);
    int i = 0;
    for (Column column : columns) {
      if (i != 0) {
        System.out.println("#" + i + " " + column.toString());
      }
      i++;
    }
  }

}
