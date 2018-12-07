import java.util.Scanner;

public class StringType extends FieldType {

	Scanner cs = new Scanner(System.in);

	public boolean correctValue(String a) {
		return true;
	}

	public String getData() {
		return cs.nextLine();
	}
}