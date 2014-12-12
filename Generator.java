package generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.NoSuchElementException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import generator.GeneratorLibrary;

public class Generator {

	public static void main(String[] args) {
		// Bring up a file chooser and get the user's choice
		JFileChooser chooser = new JFileChooser();
		int result = chooser.showOpenDialog(null);

		// If the user didn't choose a file, display a message and exit
		if (result != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(null, "No file chosen");
			return;
		}

		// Get the file that the user chose
		File file = chooser.getSelectedFile();
		
		int level = 0;
		
		while (true) {
			// Display the request to the user
			String reply = JOptionPane.showInputDialog("Enter an analysis level");

			// If the user fails to put a valid analysis level in
			// Display a message and exit.
			// Try to parse the reply as an integer. If it succeeds, break out
			// of the otherwise infinite loop. If it fails, catch the resulting
			// exception and display a message. The loop will continue in
			// this case.
			try {
				level = Integer.parseInt(reply);
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "That is an incorrect analysis level. Please enter a correct one");
			}
			if (level > 0) {
				break;
			} else {
				JOptionPane.showMessageDialog(null, "Not a valid analysis level");
			}
		}
		
		String text = "";
		
		// convert the file to a string
		// and catch any exceptions
		try {
			text = GeneratorLibrary.fileToString(file);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "There was a problem reading your file.");
			return;
		}
		
		int length = 0;
		
		while (true) {
			String reply = JOptionPane.showInputDialog("Enter the length of the output text");
			// If the user fails to put a valid analysis level in
			// Display a message and exit.
			// Try to parse the reply as an integer. If it succeeds, break out
			// of the otherwise infinite loop. If it fails, catch the resulting
			// exception and display a message. The loop will continue in
			// this case.
			try {
				length = Integer.parseInt(reply);
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "That is an incorrect analysis level. Please enter a correct one");
			}
			if (level > 0) {
				break;
			} else {
				JOptionPane.showMessageDialog(null, "Not a valid length");
			}
		}
		
		// try to generate the random text from the user's input
		// and catch any errors or exceptions as needed
		
		String reply = "";
		try {
				reply = GeneratorLibrary.generateText(text, level, length);
				JOptionPane.showMessageDialog(null, reply);
		} catch (NoSuchElementException e) {
				JOptionPane.showMessageDialog(null, "NOSUCHYou entered either an either your analysis level or lenght\nwere greater then the length of your text file");
				return;
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(null, "ILLEGALYou entered either an either your analysis level or lenght\nwere greater then the length of your text file");
			return;
		}
		
		
		// asks if the user wants to save the text to a new file
		int yesOrNo = JOptionPane.showConfirmDialog(null, "Would you like to save this random text?", "", JOptionPane.YES_NO_OPTION);
        if (yesOrNo == JOptionPane.YES_OPTION) {
        	// Takes in a string and displays a saving pane to have the user save their file.
		    // attempt to save the file
            // JFileChooser chooser = new JFileChooser();
    	    chooser.setCurrentDirectory(new File("/"));
    	    int retrival = chooser.showSaveDialog(null);
    	    if (retrival == JFileChooser.APPROVE_OPTION) {
    	        try {
    	            FileWriter fw = new FileWriter(chooser.getSelectedFile());
    	            fw.write(reply.toString());
    	            fw.close();
    	            JOptionPane.showMessageDialog(null, "File was successfully saved");
    	            return;
    	        } catch (Exception e) {
    	            JOptionPane.showMessageDialog(null, "Invalid Saving");
    	        }
    	    // If the user didn't choose a file, display a message and exit
    	    } else {
     			JOptionPane.showMessageDialog(null, "Invalid Saving");
     			return;
     		}
	    	return;
        }
        else {
           JOptionPane.showMessageDialog(null, "GOODBYE");
           return;
        }
	}
}
