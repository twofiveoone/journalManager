package journal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class JournalManager {
	
	private static final String EXIT = "exit";
	private Scanner scan;
	private String base;
	private String journals;
	private String byteFiles;
	
	public JournalManager(Scanner scan, String baseDirectory) {
		this.scan = scan;
		this.base = baseDirectory;
		this.journals = baseDirectory + "/journals";
		this.byteFiles = baseDirectory + "/byteFiles";
		this.start();
	}
	
	private void start() {
		File directory = new File(this.base);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        File jDir = new File(this.journals);
        if (!jDir.exists()) {
        	jDir.mkdirs();
        }
        File bfDir = new File(this.byteFiles);
        if (!bfDir.exists()) {
        	bfDir.mkdirs();
        }
	}

	public void createJournal() {
		System.out.println("Write the journal name");
		String journalName = this.scan.next();
		this.scan.nextLine();
		this.createJournal(journalName);
	}

	private void createJournal(String journalName) {
		File journal = new File(this.journals + "/" + journalName);
		if(journal.exists()) {
			System.out.println("Journal already exists!");
			return;
		}
		journal.mkdirs();
	}

	public void deleteJournal() {
		System.out.println("Write the journal name to delete");
		String journalName = this.scan.next();
		this.scan.nextLine();
		this.deleteJournal(journalName);
		
	}

	private void deleteJournal(String journalName) {
		File journal = new File(this.journals + "/" + journalName);
		if(!this.checkJournal(journal)) {
			return;
		}
		this.deleteDirectory(journal);
	}
	
	

	private boolean checkJournal(File journal) {
		if(!journal.exists()) {
			System.out.println("Journal doesn't exist!");
			return false;
		}
		if(!journal.isDirectory()) {
			System.out.println("File is not a folder!");
			return false;
		}
		return true;
	}
	
	private void deleteDirectory(File journal) {
		File[] contents = journal.listFiles();
		if(contents != null) {
			for(File f : contents) {
				this.deleteDirectory(f);
			}
		}
		journal.delete();
		
	}

	public void createEntry() {
		System.out.println("Write the journal name");
		String journalName = this.scan.next();
		this.scan.nextLine();
		System.out.println("Write the entry name");
		String entryName = this.scan.next();
		this.scan.nextLine();
		System.out.println("Enter the entry content");
		StringBuffer content = new StringBuffer();
		String line = "";
		while(true) {
			System.out.println("\t>");
			line = scan.nextLine();
			if(EXIT.equalsIgnoreCase(line)) {
				break;
			}
			content.append(line);
			content.append(System.lineSeparator());
		}
		this.createEntry(journalName, entryName, content.toString());
		
	}

	private void createEntry(String journalName, String entryName, String content) {
		File journal = new File(this.journals + "/" + journalName);
		if(!this.checkJournal(journal)) {
			return;
		}
		File entry = new File(this.journals + "/" + journalName + "/" + entryName);
		try {
			if(entry.createNewFile()) {
				System.out.println("Entry created!");
				this.fillEntry(entry, content);
			}
			else {
				System.out.println("Entry already exists!");
			}
		}catch(IOException e){
			System.err.println("Error occured while creating entry: "+ e.getMessage());
		}
	}

	private void fillEntry(File entry, String content) {
		try(FileWriter writer = new FileWriter(entry)){
			writer.append(content);
			writer.append(System.lineSeparator());
			System.out.println("Entry filled!!");
		}catch(IOException e){
			System.err.println("Error occured while filling to entry: "+ e.getMessage());
		}
	}

	public void deleteEntry() {
		System.out.println("Write the journal name");
		String journalName = this.scan.next();
		this.scan.nextLine();
		System.out.println("Write the entry name to delete");
		String entryName = this.scan.next();
		this.scan.nextLine();
		this.deleteEntry(journalName, entryName);
	}

	private void deleteEntry(String journalName, String entryName) {
		File journal = new File(this.journals + "/" + journalName);
		if(!this.checkJournal(journal)) {
			return;
		}
		File entry = new File(this.journals + "/" + journalName + "/" + entryName);
		if(!entry.exists()) {
			System.out.println("Entry doesn't exist");
			return;
		}
		entry.delete();
	}

	public void printEntry() {
		System.out.println("Write the journal name");
		String journalName = this.scan.next();
		this.scan.nextLine();
		System.out.println("Write the entry name to print");
		String entryName = this.scan.next();
		this.scan.nextLine();
		this.printEntry(journalName, entryName);
		
	}

	private void printEntry(String journalName, String entryName) {
		File journal = new File(this.journals + "/" + journalName);
		if(!this.checkJournal(journal)) {
			return;
		}
		File entryFile = new File(this.journals + "/" + journalName + "/" + entryName);
		if(!entryFile.exists()) {
			System.out.println("Entry doesn't exist");
			return;
		}
		Entry entry = this.getEntry(entryFile);
		System.out.println(entry);
	}

	private Entry getEntry(File entryFile) {
		String name = entryFile.getName();
		StringBuilder strb = new StringBuilder();
		try(BufferedReader reader = new BufferedReader(new FileReader(entryFile))){
			String line = "";
			while((line = reader.readLine()) != null) {
				strb.append(line);
				strb.append(System.lineSeparator());
			}
		}catch(IOException e){
			System.err.println("Error occured while reading file: "+ e.getMessage());
		}
		return new Entry(name, strb.toString());
	}

	public void printJournal() {
		System.out.println("Write the journal name");
		String journalName = this.scan.next();
		this.scan.nextLine();
		this.printJournal(journalName);		
	}

	private void printJournal(String journalName) {
		File journal = new File(this.journals + "/" + journalName);
		if(!this.checkJournal(journal)) {
			return;
		}
		File[] contents = journal.listFiles();
		if(contents != null) {
			for(File f : contents) {
				try {
					System.out.println(this.getEntry(f));
				}catch(Exception e) {
					System.err.println("Error occured while reading entry: "+ e.getMessage());
					continue;
				}
			}
		}
	}

	public void saveByteFile() {
		System.out.println("Write the journal name");
		String journalName = this.scan.next();
		this.scan.nextLine();
		this.saveByteFile(journalName);
	}

	private void saveByteFile(String journalName) {
		File journal = new File(this.journals + "/" + journalName);
		if(!this.checkJournal(journal)) {
			return;
		}
		File byteFolder = new File(this.byteFiles + "/" + journalName);
		if(byteFolder.exists()) {
			this.deleteDirectory(byteFolder);
		}
		byteFolder.mkdirs();
		File[] contents = journal.listFiles();
		Entry entry;
		File byteFile;
		if(contents != null) {
			for(File f : contents) {
				try {
					entry = this.getEntry(f);
				}catch(Exception e) {
					System.err.println("Error occured while reading entry: "+ e.getMessage());
					continue;
				}
				byteFile = new File(byteFolder, entry.getEntryName());
				try(FileOutputStream fileOutStream = new FileOutputStream(byteFile);
					ObjectOutputStream outStream = new ObjectOutputStream(fileOutStream)){
					outStream.writeObject(entry);
				}catch(IOException e) {
					e.getStackTrace();
					continue;
				}
				
			}
		}
	}

	public void loadFromByteFile() {
		System.out.println("Write the journal name");
		String journalName = this.scan.next();
		this.scan.nextLine();
		this.loadFromByteFile(journalName);
		
	}

	private void loadFromByteFile(String journalName) {
		File byteFolder = new File(this.byteFiles + "/" + journalName);
		if(!this.checkJournal(byteFolder)) {
			return;
		}
		File journal = new File(this.journals + "/" + journalName);
		if(journal.exists()) {
			this.deleteDirectory(journal);
		}
		journal.mkdirs();
		File[] contents = byteFolder.listFiles();
		Entry entry;
		if(contents != null) {
			for(File f : contents) {
				try(FileInputStream fileInStream = new FileInputStream(f);
					ObjectInputStream inStream = new ObjectInputStream(fileInStream)){
					entry = (Entry) inStream.readObject();
					this.createEntry(journalName, entry.getEntryName(), entry.getContent());
				}catch(IOException | ClassNotFoundException e) {
					e.getStackTrace();
					continue;
				}
			}
		}
		// 
	}
	
	

}
