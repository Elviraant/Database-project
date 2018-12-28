public class OneToOne extends Correlation {

	private Column column;

	public OneToOne(String name, Table table1, Table table2 ) {
		super(name, table1, table2);
		column = new Column(table2, true);
		column.createFkColumnName(table1);
		table2.setPositionOffFk(table2.getColumnCounter(), table1);
	}
}