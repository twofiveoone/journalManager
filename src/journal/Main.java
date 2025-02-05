package journal;

import java.util.Scanner;

public class Main {
	
	private static final String CREATE_JOURNAL = "new";
	private static final String DELETE_JOURNAL = "delete";
	private static final String CREATE_ENTRY = "create entry";
	private static final String DELETE_ENTRY = "delete entry";
	private static final String PRINT_ENTRY = "print entry";
	private static final String PRINT_JOURNAL = "print";
	private static final String SAVE = "save";
	private static final String LOAD = "load";
	private static final String EXIT = "exit";
	
	private static final String BASE_DIRECTORY = "src/base/AllFiles";
	private static JournalManager journalManager;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String answer = "";
		try (Scanner scan = new Scanner(System.in)){
			journalManager = new JournalManager(scan, BASE_DIRECTORY);
			boolean running = true;
			while(running) {
				printOpt();
				answer = scan.nextLine().toLowerCase();
				switch(answer) {
				case CREATE_JOURNAL:
					journalManager.createJournal();
					break;
				case DELETE_JOURNAL:
					journalManager.deleteJournal();
					break;
				case CREATE_ENTRY:
					journalManager.createEntry();
					break;
				case DELETE_ENTRY:
					journalManager.deleteEntry();
					break;
				case PRINT_ENTRY:
					journalManager.printEntry();
					break;
				case PRINT_JOURNAL:
					journalManager.printJournal();
					break;
				case SAVE:
					journalManager.saveByteFile();
					break;
				case LOAD:
					journalManager.loadFromByteFile();
					break;
				case EXIT:
					running = false;
					continue;
				default:
					System.out.println("Couldn't read that, try again?");
					continue;
				}
			}
		}
	}

	private static void printOpt() {
		System.out.println("""
			Please choose your action:	
			\tCreate a journal -new-
			\tDelete a journal -delete-
			\tCreate an entry inside a journal -create entry-
			\tDelete an entry from a journal -delete entry-
			\tPrint an entry's content -print entry-
			\tPrint a journal -print-
			\tSave a journal as a serialized byte stream (archives) -save-
			\tLoad a journal from a serialized file (archives) -load-
			\tExit the application -exit-
				""");
		
	}
}