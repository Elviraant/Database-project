/**
Represents a relationship between two tables where the rows of table1
*can be linked to multiple rows of table2 but
*a row of table2 can be linked only to one row of table1
*/

import java.util.Scanner;
public class OneToMany extends Correlation{

	private Column column;

	public OneToMany(String name, Table table1, Table table2) {

		super(name, table1, table2);
		column = new Column(table2, true);
		column.createFkColumnName(table1);
		table2.setPositionOffFk(table2.getColumnCounter(), table1);

	}
}
