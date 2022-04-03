import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors

/**
 * Represent a global Save class
 * @author Durel Enzo
 * @author Villepreux Thibault
 * @version 1.0
 */
public class Save {

    private String filename;
    
    public Save(String filename) {
	/**
	 * Save constructor, access with fullname path of the file
	 *
	 * @param filename filename to write, read, delete
	 */
	this.filename = filename;
    }

    public void write(boolean verbose, Object src) {
	/**
	 * Verbose method of write(Object src)
	 *
	 * @param verbose if true print a successful message
	 * @param src Object to write in the save
	 */
	this.write(src);
	if (verbose) System.out.println("Successfully wrote to the file.");
    }

    public void write(Object src) {
	/**
	 * Call the toString of the object and write it on the file
	 *
	 * @param src Object to write in the save
	 */
	try {
	    FileWriter myWriter = new FileWriter(this.filename);
	    myWriter.write(src.toString());
	    myWriter.close();
	} catch (IOException e) {
	    System.out.println("An error occurred.");
	    e.printStackTrace();
	}
    }

    public String read(boolean verbose) {
	/**
	 * Verbose method of read()
	 *
	 * @param verbose if true print a successful message
	 * @return String representation of the content of the file
	 */
	String result = this.read();
	System.out.println("Succesfully read the file.");
	return result;
    }

    public String read() {
	/**
	 * Read from a file and give its contents as a String
	 *
	 * @return String representation of the content of the file
	 */	
	StringBuilder s = new StringBuilder();
	try {
	    
	    File myObj = new File(this.filename);
	    Scanner myReader = new Scanner(myObj);
	    while (myReader.hasNextLine()) {
		String data = myReader.nextLine();
		// System.out.println(data);
		s.append(data);
	    }
	    myReader.close();
	} catch (FileNotFoundException e) {
	    System.out.println("An error occurred.");
	    e.printStackTrace();
	}
	return s.toString();
    }

    public void delete(boolean verbose) {
	/**
	 * Verbose method of delete()
	 *
	 * @param verbose if true print a successful message
	 */
	this.delete();
	if (verbose) System.out.println("Successfully wrote to the file.");
    }
    
    public void delete() {
	/**
	 * Delete all the content from the file
	 */ 
	try {
	    new FileWriter(filename, false).close();
	} catch (IOException e) {
	    System.out.println("An error occurred.");
	    e.printStackTrace();
	}
    }

    public boolean isEmpty() {
	/**
	 * Response if the file content is empty
	 *
	 * @return boolean true if file is empty, else false
	 */
	return this.read().equals("");
    }
}
