public final class Menu {

	public static void presentationMenu() {
		System.out.println();
		System.out.println("Presentation Options:");
		System.out.println("---------------------");
		System.out.println(String.format("%s\n%s\n%s\n", "1. Present all", "2. Present specific rows", "3. Present specific columns"));
		System.out.println("Please chose one of the above options: ");
	}

	public static void startingMenu() {
		System.out.println();
		System.out.println("Manage your data: ");
		System.out.println("-----------------");
		System.out.println(String.format("%s\n%s\n%s\n%s\n", "1. Present Data", "2. Change Data", "3. Delete Data", "4. Exit" ));
		System.out.println("Please chose one of the above options: ");
	}
}