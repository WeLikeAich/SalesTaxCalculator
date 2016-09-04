import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Timestamp;
import java.util.Scanner;

/**
 * This class is responsible for parsing a basket item from a string input. The
 * class creates either a ReceiptItem or an ExemptBasketItem.
 * 
 * @author Jonathan Aichler
 *
 */
public class ReceiptItemParser
{
	/**
	 * Parses a string describing a line on a receipt creating a basket item.
	 * 
	 * @param itemDescription
	 *            The string description of the item type.
	 * @return A basketItem object created based off of the description.
	 */
	public static ReceiptItem parse(String itemDescription)
	{
		if (itemDescription.matches("(\\s*)?"))
		{
			return null;
		}
		// checks the inputted description for proper format
		if (itemDescription.matches("(\\d+)(\\s)((\\w+)(\\s))+(\\bat|AT|At|aT\\b||\\bA)\\s(\\d+(\\.\\d+)?)")) {
			Scanner quantityReader = new Scanner(itemDescription);

			// read in the quantity of items
			int quantity = quantityReader.nextInt();
			quantityReader.close();

			// check if the item is imported
			boolean imported = false;
			if (itemDescription.toLowerCase().contains("imported"))
				imported = true;

			// substring out the item description
			int descriptionEndex = itemDescription.toLowerCase().lastIndexOf(" at"); // index of where the description ends.
			String description = itemDescription.substring(itemDescription.indexOf(" "), descriptionEndex);

			// parse out the item price.
			double price = Double.parseDouble(itemDescription.substring(itemDescription.lastIndexOf(" ")));

			// checks if the item is tax exempt.
			if (description.contains("book") || description.contains("chocolate") || description.contains("pill"))
				// return the newly parsed SalesExemptReceiptItem
				return new SalesExemptReceiptItem(quantity, imported, description, price);
			else
				// return the newly created taxable ReceiptItem
				return new ReceiptItem(quantity, imported, description, price);
		}
		else
		{
			try
			{
				// append the ErrorFile.txt log file with Date/Timestamp and identify which item causes an issue.
				java.util.Date date = new java.util.Date();
				Files.write(Paths.get("ErrorLog.txt"),
						(new Timestamp(date.getTime()) + "\nThe item:\n" + itemDescription
								+ "\nWas not entered in the correct format.\n\n").getBytes(),
						StandardOpenOption.APPEND);
			}
			// if ErrorFile.txt does not exist, create it and add the error log
			catch (IOException e)
			{
				try
				{
					// set the output stream to ErrorFile.txt
					System.setOut(new PrintStream(new File("ErrorLog.txt")));
					java.util.Date date = new java.util.Date();
					System.out.println(new Timestamp(date.getTime()) + "\nThe item:\n" + itemDescription
							+ "\nWas not entered in the correct format.\n\n");
				} 
				catch (FileNotFoundException e1) 
				{}
			}
			return null;
		}
	}
}
