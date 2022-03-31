import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors

public class Save {

    private String filename;
    
    public Save(String filename) {
	this.filename = filename;
    }

    public void write(Object... src) {
	try {
	    FileWriter myWriter = new FileWriter(this.filename);
	    for (Object o: src) {
		myWriter.write(o.toString());
	    }
	    //myWriter.write("\n");
	    myWriter.close();
	    System.out.println("Successfully wrote to the file.");
	} catch (IOException e) {
	    System.out.println("An error occurred.");
	    e.printStackTrace();
	}
    }

    public String read() {
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
}
