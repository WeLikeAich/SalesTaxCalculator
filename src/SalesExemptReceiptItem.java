/**
 * SalesExemptReceiptItem objects are basket items that are sales tax exempt but
 * not import tax exempt
 * 
 * @author Jonathan Aichler
 *
 */
public class SalesExemptReceiptItem extends ReceiptItem
{

	/**
	 * Constructor
	 * 
	 * @param newDescription	String representing the item description.
	 * @param newPrice	Double representing the items price.
	 * @param newImported	If the item is imported.
	 * @param newQuantity	Integer representing the quantity of the item.
	 */
	public SalesExemptReceiptItem(int newQuantity, boolean newImported, String newDescription, double newPrice)
	{
		super(newQuantity, newImported, newDescription, newPrice);
		calculateCosts();
	}

	/**
	 * Calculates the total item type cost
	 */
	@Override
	public void calculateCosts()
	{
		//if the quantity is not zero
		if (getQuantity() != 0)
		{
			//calculate taxes and cost normally
			calculateItemTaxes();
			setTotalItemCost((getPrice() * getQuantity()) + getItemTaxes());
		}
		//the quantity is zero
		else
		{
			//set taxes and cost to zero
			calculateItemTaxes();
			setTotalItemCost(0);
		}
	}

	/**
	 * Calculates the amount in taxes for each item type. Rounds sales taxes to
	 * the nearest $0.05
	 */
	private void calculateItemTaxes()
	{
		//if the item is imported, factor in import tax costs
		//Math.ceil used for rounding to nearest 0.05
		if (isImported())
			setItemTaxes((Math.ceil((getPrice() * getIMPORTTAX()) * 20) / 20) * getQuantity());
		//otherwise no taxes
		else
			setItemTaxes(0);
	}
}
