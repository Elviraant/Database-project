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

	public int option() {
		System.out.println(String.format("%s\n%s\n%s\n%s\n" ,"What kind of corrleation do you want to create between your tables?"
							,"1. One to one"
							,"2. One to Many"
							,"3. Many to Many"
							,"4. None"));
		return Database.choice(1,4);
	}

	public void defineCorrelation(Database d1) {
		if (d1.getTableCounter() != 1) {
			System.out.println("Please choose the entities you want to relate");
			System.out.println("First table: ");
			Table table1 = d1.chooseTable();
			do {
				Table table2 = d1.chooseTable();
				if (table2.equals(table1)) {
					System.out.println("Error: Same table chosen");
					System.out.println("Please try again");
					table2 = d1.chooseTable();
				}
			} while (table2.equals(table1));
		}
		Scanner cs = new Scanner(System.in);
		int option = option();
		System.out.println("What is the relationship between your entities?");
		String name = cs.next();
		switch (option) {
			case (1): new OneToOne(name, table1, table2);
		}
	}

}