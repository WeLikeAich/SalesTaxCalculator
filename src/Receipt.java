/**
 * The Receipt class represents all items that are to be purchased. The basket
 * is implemented as a linked list of BasketItems
 * 
 * @author Jonathna Aichler
 *
 */
public class Receipt
{
	private ReceiptItem firstItem; // the first item in the basket
	private ReceiptItem lastItem; // the last item in the basket
	private double total; // the total cost of all items including salesTaxes
	private double salesTaxes; // the total amount of sales tax payed.

	/**
	 * Default Constructor. Initializes basket total and salesTaxes to be paid
	 * to 0.
	 */
	public Receipt()
	{
		total = 0;
		salesTaxes = 0;
	}

	/**
	 * Adds a new ReceiptItem to the list
	 * 
	 * @param newItem item to be added to the list
	 */
	public void addItem(ReceiptItem newItem)
	{
		// if the basket is empty
		if (firstItem == null)
		{
			firstItem = newItem;
			lastItem = newItem;
		}
		// if there is one item in the list.
		else if (firstItem == lastItem)
		{
			lastItem = newItem;
			firstItem.setNextItem(lastItem);
		}
		// if there is more than one item in the list
		else
		{
			lastItem.setNextItem(newItem);
			lastItem = newItem;
		}
	}

	/**
	 * Calculates the total cost as well as the total salesTaxes paid.
	 */
	public void calculateCost()
	{
		ReceiptItem currItem = firstItem;

		// while the we are not at the end of the list
		while (currItem != null)
		{
			// add each items taxes to the total tax amount
			salesTaxes += currItem.getItemTaxes();

			// add each items total cost to the total cost amount
			total += currItem.getTotalItemCost();

			// get the next item
			currItem = currItem.getNextItem();
		}
	}

	/////////////////////////
	// Accessors && Mutators//
	/////////////////////////

	public ReceiptItem getFirstItem() {
		return firstItem;
	}

	public ReceiptItem getLastItem() {
		return lastItem;
	}

	public double getSalesTaxes() {
		return salesTaxes;
	}

	public double getTotal() {
		return total;
	}

	public void setFirstItem(ReceiptItem firstItem) {
		this.firstItem = firstItem;
	}

	public void setLastItem(ReceiptItem lastItem) {
		this.lastItem = lastItem;
	}

	public void setSalesTaxes(double salesTaxes) {
		this.salesTaxes = salesTaxes;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	////////////
	//toString//
	////////////
	@Override
	public String toString()
	{
		String receipt = "";
		ReceiptItem currentItem = firstItem;

		// traverse the list of items
		while (currentItem != null)
		{
			// check if it is the last item.
			if (currentItem.getNextItem() != null)
				receipt += (currentItem + "\n");
			else
				receipt += (currentItem);

			//get then next item
			currentItem = currentItem.getNextItem();
		}
		if(firstItem == null)
		{
			receipt = "There are no items on the receipt.";
		}
		return receipt;
	}
}
