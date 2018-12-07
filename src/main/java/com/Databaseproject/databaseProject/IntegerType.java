import java.util.Scanner;

public class IntegerType extends FieldType {

	Scanner cs = new Scanner(System.in);

	public boolean correctValue(String a) {
		return true;
	}

	public Integer getData() {
		int data = 0;
		while (true) {
			try {
				data = cs.nextInt();
				break;
			} catch (Exception e) {
				 System.out.println("You should insert an Integer");
				 cs.next();
			}
		}
		return data;
	}


}