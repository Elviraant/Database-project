public class Correlation {

	public String name; //name of correlationship between the entities.
	public Table table1; //entity 1
	public Table table2; //entity 2

	public int option() {
		System.out.println(String.format("%s\n%s\n%s\n%s\n" ,"What kind of corrleation do you want to create between your tables?"
							,"1. One to one"
							,"2. One to Many"
							,"3. Many to Many"));
		return Database.choice(1,3);
	}

}