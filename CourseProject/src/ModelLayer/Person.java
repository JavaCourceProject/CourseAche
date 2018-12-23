package ModelLayer;

public class Person {

	private int ID;
	private String logId;
	private String fName;
	private String lName;
	private String address;
	private String email;
	private String phone;

	public Person() {
	}

	public Person(int id) {
		ID = id;
	}

	public void generateLogId() {
		setLogId(Integer.toString(getID()));
	}

	public String toString() {
		return "/nID: " + getID() + "/nName: " + getfName() + " " + getlName() + "/nAddress: " + getAddress()
				+ "/nEmail: " + getEmail() + "/nPhone: " + getPhone();
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}
}