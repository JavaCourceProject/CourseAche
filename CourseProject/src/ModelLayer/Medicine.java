package ModelLayer;

public class Medicine {

	private int ID;
	private String name;
	private Double qty;
	private int usage;
	private String supplier;

	public Medicine() {
	}
	
	public Medicine(int id) {
		ID = id;
	}

	@Override
	public String toString() {
		return "Medicine [ID=" + ID + ", name=" + name + ", qty=" + qty + ", usage=" + usage + ", supplier=" + supplier
				+ "]";
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getQty() {
		return qty;
	}

	public void setQty(Double qty) {
		this.qty = qty;
	}

	public int getUsage() {
		return usage;
	}

	public void setUsage(int usage) {
		this.usage = usage;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	
}