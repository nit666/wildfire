import java.io.IOException;

import nit.history.DAOFactory;
import nit.history.dao.memory.MemoryDAOFactory;
import nit.history.dao.memory.MemoryToFileWriter;


public class TestHistory {
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {

		DAOFactory factory = new MemoryDAOFactory();

		MemoryToFileWriter fileWriter = new MemoryToFileWriter();
		
		while (true) {
			byte choice = printMainMenu();
			switch (choice) {
			case 'e': 
				addEntity();
				break;
			case 'l':
				addLocation();
				break;
			case 'h':
				addHistoryEvent();
				break;
			case 's':
				search();
				break;
			case 'q':
				fileWriter.writeFile(factory);
				System.exit(0);
			}
		}
	}

	public static byte printMainMenu() throws IOException {
		System.out.println("--- Options ---");
		System.out.println("e) Add Entity");
		System.out.println("l) Add Location");
		System.out.println("h) Add History Event");
		System.out.println("s) Search");
		System.out.println("q) Quit");
		System.out.print("Choice? ");
		
		byte[] input = new byte[1];
		System.in.read(input);
		System.out.println("input = " + input[0]);
		return input[0];
	}
	
	public static void addEntity() {System.out.println("addEntity");}
	public static void addLocation() {System.out.println("addLocation");}
	public static void addHistoryEvent() {System.out.println("addHistoryEvent");}
	public static void search() {System.out.println("Search");}
}
