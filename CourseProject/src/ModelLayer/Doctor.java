/**
 * @author Jelizaveta Kuznecova
 */
package ModelLayer;

public class Doctor {

	private int ID;
	private String name;
	private String sName;
	private String address;
	private String email;
	private String phone;

	public Doctor() {
	}
	
	public Doctor(int id) {
		ID = id;
	}
	
	public String toString() {
		return "/nID: " + getID() + "/nName: " + getName() + " " + getsName() + "/nAddress: " + getAddress()
				+ "/nEmail: " + getEmail() + "/nPhone: " + getPhone();
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

	public String getsName() {
		return sName;
	}

	public void setsName(String sName) {
		this.sName = sName;
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
}