import java.util.Scanner;
import java.util.ArrayList;
import java.io.Serializable;

public class EnumeratedType extends FieldType implements Serializable{

	private ArrayList<String> allowedStrings;

	public EnumeratedType() {
		allowedStrings = new ArrayList<String>();
	}

	public ArrayList getAllowedStrings() {
		return allowedStrings;
	}

	public String getData() {
		Scanner cs = new Scanner(System.in);
		String data = null;
		boolean flag = true;
		while(flag == true) {
			data = cs.nextLine();

			if (correctValue(data)) {
				flag = false;
			} else {
				System.out.print("You have to insert one of these: " + getAllowedStrings());
			}

		}
		return data;
	}

	public void defineEnumeration() {
		Scanner cs = new Scanner(System.in);
		System.out.println("Please insert the types you want to fill your fields with. Enter EXIT to stop");
		String type = cs.nextLine();
		while (!type.equals("EXIT")) {
			allowedStrings.add(type);
			type = cs.nextLine();
		}
	}

	public boolean correctValue(String filler) {
		boolean isCorrect = false;
		for (String allowedString: allowedStrings) {
			if (filler.equals(allowedString)) {
				isCorrect = true;
			}
		}
		return isCorrect;
	}

}
