
public class OrderLine {
	private int orderId;
	private int barcode;
	private String description;
	private int quantity;
	private float unitPrice;
	private int discount;
	private float total;
	
	public OrderLine(int orderId, int barcode, String description, int quantitiy, float unitPrice, int discount, float total) {
		this.setOrderId(orderId);
		this.setBarcode(barcode);
		this.setDescription(description);
		this.setQuantity(quantitiy);
		this.setUnitPrice(unitPrice);
		this.setDiscount(discount);
		this.setTotal(total);
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getBarcode() {
		return barcode;
	}

	public void setBarcode(int barcode) {
		this.barcode = barcode;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public float getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(float unitPrice) {
		this.unitPrice = unitPrice;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}
}
