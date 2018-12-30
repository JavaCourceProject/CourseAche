/**
 * @author Jelizaveta Kuznecova
 */
package ControlLayer;

import java.util.ArrayList;

import DBLayer.DBConnection;
import DBLayer.DBPerson;
import DBLayer.IFDBPerson;
import ModelLayer.Person;

public class PersonCtr {

	public PersonCtr() {
	}

	// Login
	public Person loginGUI(String logname) {
		IFDBPerson dbPerson = new DBPerson();
		Person person = dbPerson.loginGUI(logname, false);
		return person;
	}

	// Insert person
	public int insertPerson(String fname, String lname, String address, String phone, String email, String logId) {
		int personId = 0;
		Person person = new Person();
		person.setfName(fname);
		person.setlName(lname);
		person.setAddress(address);
		person.setPhone(phone);
		person.setEmail(email);
		person.setLogId(logId);
		person.generateLogId();
		try {
			DBConnection.startTransaction();
			DBPerson dbPerson = new DBPerson();
			personId = dbPerson.insertPerson(person);
			DBConnection.commitTransaction();
		} catch (Exception e) {
			DBConnection.rollbackTransaction();
		}
		return personId;
	}

	// Get person
	public Object getPerson(int personId) throws Exception {
		Person person = new Person();
		IFDBPerson dbPerson = new DBPerson();
		person = dbPerson.getPerson(personId, true);
		if (person != null) {
			person = dbPerson.getPerson(person.getID(), true);
			person.setAddress(person.getAddress());
			person.setfName(person.getfName());
			person.setlName(person.getlName());
			person.setPhone(person.getPhone());
			person.setEmail(person.getEmail());
			person.setID(person.getID());
			person.setLogId(person.getLogId());
			return person;
		} else {
			throw new Exception("Person not found");
		}
	}

	// Update person
	public void updatePerson(int guestId, String fname, String lname, String address, String phone, String email,
			int authLevel) {
		Person person = new Person();
		person.setID(guestId);
		person.setfName(fname);
		person.setlName(lname);
		person.setAddress(address);
		person.setPhone(phone);
		person.setEmail(email);
		try {
			DBConnection.startTransaction();
			DBPerson dbPerson = new DBPerson();
			dbPerson.updatePerson(person);
			DBConnection.commitTransaction();
		} catch (Exception e) {
			DBConnection.rollbackTransaction();
		}
	}

	// Delete person
	public void deletePerson(int personId) {
		try {
			DBConnection.startTransaction();
			IFDBPerson dbPerson = new DBPerson();
			dbPerson.deletePerson(personId);
			DBConnection.commitTransaction();
		} catch (Exception e) {
			DBConnection.rollbackTransaction();
		}
	}

	// Get person array
	public ArrayList<Person> getAllPerson() {
		ArrayList<Person> persons = new ArrayList<Person>();
		for (Person person : persons) {
			try {
				person = (Person) getPerson(person.getID());
				person.getAddress();
				person.getfName();
				person.getlName();
				person.getPhone();
				person.getEmail();
				person.getLogId();
			} catch (Exception e) {
			}
		}
		return persons;
	}
}
