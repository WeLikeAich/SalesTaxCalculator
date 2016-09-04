import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.Scanner;

/**
 * The ReceiptClient class is designed to test the Receipt.java, ReceiptItem.java, ReceiptItemParser.java,
 *  and SalesExemptReceiptItem.java classes.
 * @author Jonathan Aichler
 *
 */
public class ReceiptClient {

	public static void main(String[] args) {
		DecimalFormat dollarFormat = new DecimalFormat("#,##0.00");

		Scanner fileReader;

		//creates a fileNameFilter object used to identify valid input files in the directory.
		FilenameFilter fileNameFilter = new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) 
			{
				if (name.lastIndexOf('.') > 0) {
					// get the index for the file type extension
					int fileTypeExtensionIndex = name.lastIndexOf('.');

					// get the file type extension
					String fileTypeExtension = name.substring(fileTypeExtensionIndex);

					// match path name extension and has valid input file name
					if (fileTypeExtension.equals(".txt") && name.substring(0, fileTypeExtensionIndex).contains("receiptInputFile-TestCase")) {
						return true;
					}
					return false;
				}
				return false;
			}
		};

		// returns the number of valid input files for program autonomation
		File[] inputFilesList = new File(".").listFiles(fileNameFilter);

		// loop to automate all test cases
		for (int caseNumber = 0; caseNumber < inputFilesList.length; caseNumber++) {
			boolean error = false;
			try {
				// read inputs from the test case files
				fileReader = new Scanner(new File(inputFilesList[caseNumber].getName()));

				// creates a new receipt object to add each item too
				Receipt receipt = new Receipt();

				// the current item in the list
				ReceiptItem currentItem;

				// While there are items to read from the
				while (fileReader.hasNextLine()) {
					String lineItem = fileReader.nextLine();
					// checks for blank lines
					currentItem = ReceiptItemParser.parse(lineItem);

					// the line was an invalid item
					if (currentItem == null) {
						// if the line was not a blank line
						if (!lineItem.matches("(\\s*)?"))
							error = true;
					}
					// a valid item to add to the receipt list
					else
						receipt.addItem(currentItem);
				}

				
				// set the output stream to the respective output file.
				System.setOut(new PrintStream(new File("receiptOutputFile-TestCase" + 
				inputFilesList[caseNumber].getName().substring(//substring out current test case #
						inputFilesList[caseNumber].getName().lastIndexOf('e')+1, //start after worse Case
						inputFilesList[caseNumber].getName().lastIndexOf('.')) + ".txt")));//go until the file type suffix

				// calculate the cost of the receit
				receipt.calculateCost();

				// if an error was thrown, write to the output file the error
				// message
				if (error)
					System.out.println("One or more of your items may not have been entered in properly, "
							+ "please check ErrorFile.txt for details.\n"
							+ "Please check all items to be in the format [(quantity) (description) at (price)] "
							+ "(i.e. 1 imported box of chocolates at 11.25)\n");

				// write any items that were created properly to the output file
				System.out.println(receipt);
				// write the total sales taxes and price to the output file
				System.out.println("Sales Taxes: " + dollarFormat.format(receipt.getSalesTaxes()));
				System.out.println("Total: " + dollarFormat.format(receipt.getTotal()));
			}
			// if the file attempting to be read does not exist
			catch (FileNotFoundException e) {
				System.out.println("The file " + inputFilesList[caseNumber].getName()
						+ ".txtdoes not exist, please check the file name and try again.");
				e.printStackTrace();
			}
		}
	}
}
