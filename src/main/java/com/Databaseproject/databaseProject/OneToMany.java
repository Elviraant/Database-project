/**
Represents a relationship between two tables where the rows of table1
*can be linked to multiple rows of table2 but
*a row of table2 can be linked only to one row of table1
*/

import java.util.Scanner;
public class OneToMany extends Correlation{

/**
*Defines the table the rows of which will be linked to multiple
*rows of the other as table1
*/
	public void define() {
		Table temp;
		System.out.println("This is an One-To-Many relationship.");
		System.out.println("Please choose the table of the rows that will be linked to multiple rows of the other table");
		System.out.println(String.format("%s %s\n%s %s\n", "1. ", table1.getName(), "2. ", table2.getName()));
		int choice = Database.choice(1,2);
		if (choice == 2) {
			temp = table2;
			table2 = table1;
			table1 = temp;
		}
	}



}
