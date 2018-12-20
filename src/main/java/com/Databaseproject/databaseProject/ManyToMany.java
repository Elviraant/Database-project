public class ManyToMany extends Correlation {

	private Column column1; //foreign key column for Table table1
	private Column column2; //foreign key column for Table table2



	public ManyToMany(String name, Table table1, Table table2) {

		super(name, table1, table2);
		this.column1 = new Column("Foreign Key", table2, true);
		this.column2 = new Column("Foreign Key", table1, true);
		table2.setPositionOfFK(table2.getColumnCounter() + 1);
		table1.setPositionOfFK(table1.getColumnCounter() + 1);
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