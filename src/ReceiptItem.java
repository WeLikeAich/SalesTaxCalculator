import java.text.DecimalFormat;

/**
 * The Receipt Item Class is representative of a shopping cart item. Each object
 * represents a different type of object, however if it is the same type of
 * object, quantity will be updated instead of making a new object.
 * 
 * @author Jonathan Aichler
 *
 */
public class ReceiptItem {
	private String description; // Description of the item being purchased
	private double price; // Pre-tax price of the item type
	private final double SALESTAX = .10; // sales tax percentage
	private final double IMPORTTAX = .05; // import tax percentage
	private boolean imported; // boolean flag for if the item is imported
	private double itemTaxes; // amount of tax based of the item type
	private double totalItemCost; // total cost of the item including tax
	private ReceiptItem nextItem; // the next item in the basket
	private int quantity; // the number of the item type in the basket

	/**
	 * Constructor
	 * 
	 * @param newDescription	String representing the item description.
	 * @param newPrice	Double representing the items price.
	 * @param newImported	If the item is imported.
	 * @param newQuantity	Integer representing the quantity of the item.
	 */
	public ReceiptItem(int newQuantity, boolean newImported, String newDescription, double newPrice) {

		description = newDescription;
		price = newPrice;
		imported = newImported;
		quantity = newQuantity;

		//calculate the total cost and taxes for the item
		calculateCosts();
	}

	/**
	 * Calculates the total item type cost
	 */
	public void calculateCosts() 
	{
		//if the quantity is not zero
		if (quantity != 0) 
		{
			//calculate taxes and cost normally
			calculateItemTaxes();
			totalItemCost = (price * quantity) + itemTaxes;
		}
		//the quantity is zero
		else 
		{
			//set taxes and cost to zero
			itemTaxes = 0;
			totalItemCost = 0;
		}
	}

	/**
	 * Calculates the amount in taxes for each item type. Rounds sales taxes to
	 * the nearest $0.05
	 */
	private void calculateItemTaxes() {
		
		//if the item is imported, factor in import tax costs
		//Math.ceil used for rounding to nearest 0.05
		if (imported)
			itemTaxes = (Math.ceil((price * (SALESTAX + IMPORTTAX)) * 20) / 20)*quantity;
		else
			itemTaxes = (Math.ceil((price * SALESTAX) * 20) / 20) * quantity;
	}


	/////////////////////////
	//Accessors && Mutators//
	/////////////////////////	
	public String getDescription() {
		return description;
	}

	public double getIMPORTTAX() {
		return IMPORTTAX;
	}

	public double getItemTaxes() {
		return itemTaxes;
	}

	public ReceiptItem getNextItem() {
		return nextItem;
	}

	public double getPrice() {
		return price;
	}

	public int getQuantity() {
		return quantity;
	}

	public double getSALESTAX() {
		return SALESTAX;
	}

	public double getTotalItemCost() {
		return totalItemCost;
	}

	public boolean isImported() {
		return imported;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setImported(boolean isimported) {
		this.imported = isimported;
	}

	public void setItemTaxes(double itemTaxes) {
		this.itemTaxes = itemTaxes;
	}

	public void setNextItem(ReceiptItem nextItem) {
		this.nextItem = nextItem;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setTotalItemCost(double totalItemCost) {
		this.totalItemCost = totalItemCost;
	}

	////////////
	//toString//
	////////////
	@Override
	public String toString() {
		DecimalFormat dollarFormat = new DecimalFormat("#,##0.00");

		String item = quantity + description + ": " + dollarFormat.format(totalItemCost);
		return item;
	}

}
