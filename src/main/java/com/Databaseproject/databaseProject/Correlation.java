import java.util.Scanner;

public class Correlation {

	private String name;
	private Table table1; //entity 1
	private Table table2; //entity 2

	/**
	*Constructor for Correlation class
	* @param name name of correlationship between the entities
	* @param table1 first entity
	* @param table2 second entity
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

}