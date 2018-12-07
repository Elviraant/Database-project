import java.util.Scanner;

public class DoubleType extends FieldType {
	Scanner cs = new Scanner(System.in);

	public boolean correctValue(String a) {
		return true;
	}

	public Double getData() {
		double data = 0;
		while (true) {
			try {
				data = cs.nextDouble();
				break;
			} catch (Exception e) {
				 System.out.println("You should insert a Double");
				 cs.next();
			}
		}
		return data;

	}
}