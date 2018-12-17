import java.util.Scanner;
import java.io.Serializable;

public class StringType extends FieldType implements Serializable {

	transient Scanner cs = new Scanner(System.in);

	public boolean correctValue(String a) {
		return true;
	}

	public String getData() {
		return cs.nextLine();
	}
}