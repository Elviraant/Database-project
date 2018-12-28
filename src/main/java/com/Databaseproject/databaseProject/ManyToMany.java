public class ManyToMany extends Correlation {

	private Column column1; //foreign key column for Table table1
	private Column column2; //foreign key column for Table table2



	public ManyToMany(String name, Table table1, Table table2) {

		super(name, table1, table2);
		this.column1 = new Column(table2, true);
		this.column1.createFkColumnName(table1);
		table2.setPositionOffFk(table2.getColumnCounter(), table1);
		this.column2 = new Column(table1, true);
		this.column2.createFkColumnName(table2);
		table1.setPositionOffFk(table1.getColumnCounter(), table2);

		//table2.setPositionOfFK(table2.getColumnCounter() + 1);
		//table1.setPositionOfFK(table1.getColumnCounter() + 1);
	}

	public Column getColumn1() {
		return column1;
	}

	public void setColumn1(Column column) {
		column1 = column;
	}

	public Column getColumn2() {
		return column2;
	}

	public void setColumn2(Column column) {
		column2 = column;
	}

}