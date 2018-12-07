abstract class FieldType {
	enum Type {
		STRING,
		INTEGER,
		DOUBLE,
		ENUMERATION_TYPE,
		DATE
	}

	abstract boolean correctValue(String a);
	abstract Object getData();


}