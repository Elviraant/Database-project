//package com.Databaseproject.databaseProject;
import java.io.Serializable;
import java.util.ArrayList;


public class Column implements Serializable {
  private String name;
  private FieldType type;
  private boolean isPrimaryKey;
  private boolean isForeignKey;
  private ArrayList<Object> field;
  private ArrayList<ArrayList<Object>> foreignKeys = new ArrayList<ArrayList<Object>>();

  /**
   * First Constructor for Column class.
   * @param name name of this field.
   * @param type type of this field.
   * @param table database's entity of this field.
   */

  public Column(String name, FieldType type, Table table) { /*checkstyle checked*/
    this.name = name;
    this.type = type;
    field = new ArrayList<Object>();
    table.getColumns().add(this);
    table.setColumnCounter(table.getColumnCounter() + 1);
  }

   /*CHECKSTYLE MESSAGE :UNUSED @param tag for name*/
  /**
   * Second Constructor for Column class.
   * Used by Correlation for the foreign keys.
   * @param name name of this field.
   * @param table database's entity of this field.
   * @param isForeignKey sets true is this Column is a foreign key.
   */
  public Column(Table table, boolean isForeignKey, Correlation correlation) {
    //this.name = name;
    field = new ArrayList<Object>();
    //foreignKey.add(field);
    this.isForeignKey = isForeignKey;
    table.getColumns().add(this);
    table.setColumnCounter(table.getColumnCounter() + 1);
  }


  /**public static int getCounter() {
    return counter;
  } **/

  public String getName() { /*checkstyle checked*/
    return name;
  }

  public void setName(String name) { /*checkstyle checked*/
    this.name = name;
  }

  public boolean getPrimaryKey() { /*checkstyle checked*/
    return isPrimaryKey;
  }

  public void setPrimaryKey(boolean isPrimaryKey) { /*checkstyle checked*/
    this.isPrimaryKey = isPrimaryKey;
  }

  public void setForeignKey(boolean isForeignKey) { /*checkstyle checked*/
    this.isForeignKey = isForeignKey;
  }

  public boolean getForeignKey() { /*checkstyle checked*/
    return isForeignKey;
  }

  public ArrayList<Object> getField() { /*checkstyle checked*/
    return field;
  }

  public FieldType getType() { /*checkstyle checked*/
    return type;
  }

  public void setFieldType(FieldType type) { /*checkstyle checked*/
    this.type = type;
  }

  public ArrayList<ArrayList<Object>> getForeignKeys() { /*checkstyle checked*/
    return foreignKeys;
  }

  /*
   *Prints an element of the field accoording to the row given.
   *@param row
   */

  public void printElement(int row) { /*checkstyle checked*/
    String data = String.format("|%-15s|", field.get(row).toString());
    System.out.print(data);
    System.out.print("     ");
  }

  /*
   * Creates the right FieldType object, based on a choice
   * This is a static method
   * @param choice
   * @return FieldType object, that is created
   */

  public static FieldType findType(int choice) {
    FieldType type;
    switch (choice) {
      case 1: { type = new IntegerType(); }
        break;
      case 2: { type = new DoubleType(); }
        break;
      case 3: {type = new StringType();}
        break;
      default: {type = new StringType();}
    }
    return type;
  }

  /**
   * Returns void.
   * Fills the Primary Key Field.
   * @param data user's insertion.
   */

  public void fillPrimaryKeyField(Object data) { /*checkstyle checked*/
    boolean unique = this.checkUniqueness(data);
    while (!unique) {
      System.out.print("This data already exists. Try again: ");
      data = this.getType().getData();
      unique = this.checkUniqueness(data);
    }
    this.getField().add(data);
  }

  /**
   * Returns true if the data is unique.
   * Use for Primary Key Field.
   * @param data user's insertion.
   */

  public boolean checkUniqueness(Object data) { /*checkstyle checked*/
    boolean unique = true;
    for (Object f : field) {
      if (f.equals(data)) {
        unique = false;
      }
    }
    return unique;
  }

  /*
   * Create name for a foreign key Column object,
   * by using String.concat(String) method to combine the phrase "FK_from_",
   * with the Table object's name that foreign key Column object is coming from
   * Then, set the new name as this Column object's name
   * @param Table object, that the foreign key Column object is coming from
   */

  public void createFkColumnName(Table table) { /*checkstyle checked*/
    String name = ("Fk_from_").concat(table.getName());
    setName(name);
  }

  /*
  *search if a specific element exists in specific field.
  *if exists, prints the all records that it is found in a list.
  *if it does not, prints suitable message.
  *@param Object Type variable, to be searched in field.
 */
  public void searchElement(Object element) { /*checkstyle checked*/
    ArrayList<Integer> rows = matchingRows(element);
    if (rows.size() != 0) {
      System.out.println(element);
      System.out.print("found in record(s):");
      for (Integer row : rows) {
        System.out.print(+row + 1 + ". ");
      }
    } else {
      System.out.print(element);
      System.out.println("doesn't exist in this field.");
    }
    System.out.println();
  }

  /*
    *search if a specific element exists in specific field.
    *if exists, keeps all the positions that it is found in a list.
    *returns this list, even if element does not exist.
    *@param Object Type variable, to be searched in field.
 */
  public ArrayList<Integer> matchingRows(Object element) { /*checkstyle checked*/
    ArrayList<Integer> rows = new ArrayList<Integer>();
    for (int i = 0; i < getField().size(); i++) {
      if (element.equals(getField().get(i))) {
        rows.add(i);
      }
    }
    return rows;
  }

  /**
  *returns a list of rows if any element of an arraylist matches a certain key of the table.
  */
  public ArrayList<Integer> matchingRows(ArrayList<Object> foreigns) { /*checkstyle checked*/
    ArrayList<Integer> rows = new ArrayList<Integer>();
    ArrayList<Integer> toBeReturned = new ArrayList<Integer>();
    for (Object foreign : foreigns) {
      rows = matchingRows(foreign);
      if (rows.size() != 0) {
        toBeReturned.add(rows.get(0));
      }
    }
    return toBeReturned;
  }

  /**
  *changes the data in order to create a table in ascesing order.
  */

  public void sortInAscendingOrder(int j, Object s1, Object s2) { /*checkstyle checked*/
    Object temp = s2;
    this.getField().set(j,s1);
    this.getField().set(j - 1,temp);
  }

  public void sortInAscendingOrder(int j, ArrayList<Object> s1, ArrayList<Object> s2) {
    ArrayList<Object> temp = s2;
    this.getForeignKeys().set(j,s1);
    this.getForeignKeys().set(j - 1,temp);
  }


  /**
  *changes the data in order to create a table in descending order.
  */

  public void sortInDescendingOrder(int j, Object s1, Object s2) {  /*checkstyle checked*/
    Object temp = s1;
    this.getField().set(j - 1,s2);
    this.getField().set(j,temp);
  }
  
  public void sortInDescendingOrder(int j, ArrayList<Object> s1, ArrayList<Object> s2) {
    ArrayList<Object> temp = s1;
    this.getForeignKeys().set(j - 1,s2);
    this.getForeignKeys().set(j,temp);
  }
  

  public void sortForeignKeysColumn(int result, int j, int choice) {
    ArrayList<Object> s1 = getForeignKeys().get(j - 1);
    ArrayList<Object> s2 = getForeignKeys().get(j);
    if (choice == 1) {
      if (result > 0) {
        sortInAscendingOrder(j,s1,s2);
      }
    } else {
      if (result < 0) {
        sortInDescendingOrder(j, s1,s2);
      }
    }
  }

  public Integer findPKeyPosition(Object key) {    /*checkstyle checked*/
    if (isPrimaryKey) {
      ArrayList<Integer> positions = matchingRows(key);
      if (!positions.isEmpty()) {
        return positions.get(0);
      }
    }
    return -1;
  }

  /**
   * Present a Column object with its characteristics.
   * @return String.
   */
  public String toString() {
    return "Column Name: " + name + " ---- " + type.toString() + " ---- Primary Key: "
        + isPrimaryKey + " ---- Foreign Key: " + isForeignKey + " ---- ";

  }


}
