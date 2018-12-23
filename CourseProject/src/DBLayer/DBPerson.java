package DBLayer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ModelLayer.Person;

public class DBPerson implements IFDBPerson {
	private Connection con;

	public DBPerson() {
		con = DBConnection.getInstance().getDBcon();
	}

	@Override
	public Person loginGUI(String logname, boolean retriveAssociation) {
		String wClause = "  logId = '" + logname + "'";
		return singleWhere(wClause, retriveAssociation);
	}
	
	@Override
	public ArrayList<Person> getAllPerson(boolean retrieveAssociation) {
		return miscWhere("", retrieveAssociation);
	}

	@Override
	public Person getPerson(int personId, boolean retriveAssociation) {
		String wClause = "  personId = '" + personId + "'";
		return singleWhere(wClause, retriveAssociation);
	}

	@Override
	public int insertPerson(Person person) {
		int nextId = GetMaxId.getMaxId("Select max(personId) from person") + 1;
		String query = "INSERT INTO person(personId, fname, lname, address, phone, email, logId) VALUES('"
				+ nextId
				+ "','"
				+ person.getfName()
				+ "','"
				+ person.getlName()
				+ "','"
				+ person.getAddress()
				+ "','"
				+ person.getPhone()
				+ "','"
				+ person.getEmail()
				+ "','"
				+ nextId
				+ "')";
		System.out.println("insert : " + query);
		try {
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			stmt.executeUpdate(query);
			stmt.close();
			con.commit();
		} catch (SQLException ex) {
			System.out.println("Person is not inserted");
		}
		return nextId;
	}

	@Override
	public int updatePerson(Person person) {
		Person personObj = person;
		int rc = -1;
		String query = "UPDATE person SET " 
				+ "fname = '"     + personObj.getfName() + "'," 
				+ "lname = '"     + personObj.getlName() + "'," 
				+ "address = '"   + personObj.getAddress() + "'," 
				+ "phone = '"     + personObj.getPhone() + "'," 
				+ "email = '" 	  + personObj.getEmail() + "'"
				+ " WHERE personId = '" + personObj.getID() + "'";
		System.out.println("Update query:" + query);
		try {
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			rc = stmt.executeUpdate(query);
			stmt.close();
		} catch (Exception e) {
			System.out.println("Update exception in person db: " + e);
		}
		return rc;
	}

	@Override
	public int deletePerson(int personId) {
		int rc = -1;
		String query = "DELETE FROM person WHERE personId = '" + personId + "'";
		System.out.println(query);
		try {
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			rc = stmt.executeUpdate(query);
			stmt.close();
		} catch (Exception ex) {
			System.out.println("Delete exception in person db: " + ex);
		}
		return rc;
	}
	
	private Person singleWhere(String wClause, boolean retrieveAssociation) {
		ResultSet results;
		Person person = new Person();
		String query = buildQuery(wClause);
		System.out.println(query);
		try {
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery(query);

			if (results.next()) {
				person = buildPerson(results);
				stmt.close();
			} else {
				person = null;
			}
		} catch (Exception e) {
			System.out.println("Query exception: " + e);
		}
		return person;
	}
	
	private ArrayList<Person> miscWhere(String wClause,
			boolean retrieveAssociation) {
		ResultSet results;
		ArrayList<Person> list = new ArrayList<Person>();
		String query = buildQuery(wClause);
		try {
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery(query);

			while (results.next()) {
				Person person = new Person();
				person = buildPerson(results);
				list.add(person);
			}
			stmt.close();
		} catch (Exception e) {
			System.out.println("Query exception - select: " + e);
			e.printStackTrace();
		}
		return list;
	}

	private Person buildPerson(ResultSet results) {
		Person person = new Person();
		try {
			person.setID(results.getInt("personId"));
			person.setfName(results.getString("fname"));
			person.setlName(results.getString("lname"));
			person.setAddress(results.getString("address"));
			person.setPhone(results.getString("phone"));
			person.setEmail(results.getString("email"));
			person.setLogId(results.getString("logId"));
		} catch (Exception e) {
			System.out.println("error in building the person object");
		}
		return person;
	}

	private String buildQuery(String wClause) {
		String query = "SELECT personId, fname, lname, address, phone, email, logId, password FROM person";

		if (wClause.length() > 0)
			query = query + " WHERE " + wClause;

		return query;
	}

}
