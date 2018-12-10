import java.util.ArrayList;

public class Column {
	private String name;
	private FieldType type;
	//private static int counter = 0;
	private boolean isPrimaryKey;
	private ArrayList<Object> field;

	/**
	 * Constructor for Column class
	 * @param name 	name of this field
	 * @param type 	type of this field
	 * @param table database's entity of this field
	 */
	public Column(String name, FieldType type, Table table) {
		this.name = name;
		this.type = type;
		field = new ArrayList<Object>();
		table.getColumns().add(this);
		table.setColumnCounter(table.getColumnCounter() + 1);
	}

	/**public static int getCounter() {
		return counter;
	} **/

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrimaryKey() {
		isPrimaryKey = true;
	}

	public ArrayList<Object> getField() {
		return field;
	}

	public FieldType getType() {
		return type;
	}

	/**public Column findName(String name) {

		Column column = null;
		for(Column c : columns){
			if (c.getName().equals(name)) {
				column = c;
			}
		}
		return column;
	} ***/

	public void printElement(int row) {
		String data = String.format("|%-15s|", this.field.get(row).toString());
		System.out.print(data);
		System.out.print("     ");

	}

	/**
	 * Returns void
	 * Checks uniqueness and adds data in a field
	 * Use for Primary Key Field
	 */
	public void checkUniqueness() {

		boolean exists = false;
		Object data = getType().getData();

		for (Object f : field) {
			if (f.equals(data)) {
				exists = true;
			}
		}
		if (exists) {
			System.out.println("That data is already exist. Try again");
		} else {
			field.add(data);
		}
	}

}
