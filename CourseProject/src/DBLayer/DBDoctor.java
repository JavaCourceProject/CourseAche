/**
 * @author Jelizaveta Kuznecova
 */
package DBLayer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ModelLayer.Doctor;

public class DBDoctor implements IFDBDoctor {
	private Connection con;

	public DBDoctor() {
		con = DBConnection.getInstance().getDBcon();
	}
	
	@Override
	public ArrayList<Doctor> getAllDoctor(boolean retrieveAssociation) {
		return miscWhere("", retrieveAssociation);
	}

	@Override
	public Doctor getDoctor(int doctorId, boolean retriveAssociation) {
		String wClause = "  doctorId = '" + doctorId + "'";
		return singleWhere(wClause, retriveAssociation);
	}

	@Override
	public int insertDoctor(Doctor doctor) {
		int nextId = GetMaxId.getMaxId("Select max(doctorId) from doctor") + 1;
		String query = "INSERT INTO doctor(doctorId, name, sname, address, email, phone) VALUES('"
				+ nextId
				+ "','"
				+ doctor.getName()
				+ "','"
				+ doctor.getsName()
				+ "','"
				+ doctor.getAddress()
				+ "','"
				+ doctor.getEmail()
				+ "','"
				+ doctor.getPhone()
				+ "')";
		System.out.println("insert : " + query);
		try {
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			stmt.executeUpdate(query);
			stmt.close();
			con.commit();
		} catch (SQLException ex) {
			System.out.println("Doctor is not inserted");
		}
		return nextId;
	}

	@Override
	public int updateDoctor(Doctor doctor) {
		Doctor doctorObj = doctor;
		int rc = -1;
		String query = "UPDATE doctor SET " 
				+ "name = '" + doctorObj.getName() + "'," 
				+ "sname = '" + doctorObj.getsName() + "'," 
				+ "address = '" + doctorObj.getAddress() + "'," 
				+ "email = '" + doctorObj.getEmail() + "',"
				+ "phone = '" + doctorObj.getPhone() + "'"
				+ " WHERE doctorId = '" + doctorObj.getID() + "'";
		System.out.println("Update query:" + query);
		try {
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			rc = stmt.executeUpdate(query);
			stmt.close();
		} catch (Exception e) {
			System.out.println("Update exception in doctor db: " + e);
		}
		return rc;
	}

	@Override
	public int deleteDoctor(int doctorId) {
		int rc = -1;
		String query = "DELETE FROM doctor WHERE doctorId = '" + doctorId + "'";
		System.out.println(query);
		try {
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			rc = stmt.executeUpdate(query);
			stmt.close();
		} catch (Exception ex) {
			System.out.println("Delete exception in doctor db: " + ex);
		}
		return rc;
	}
	
	private Doctor singleWhere(String wClause, boolean retrieveAssociation) {
		ResultSet results;
		Doctor doctor = new Doctor();
		String query = buildQuery(wClause);
		System.out.println(query);
		try {
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery(query);

			if (results.next()) {
				doctor = buildDoctor(results);
				stmt.close();
			} else {
				doctor = null;
			}
		} catch (Exception e) {
			System.out.println("Query exception: " + e);
		}
		return doctor;
	}
	
	private ArrayList<Doctor> miscWhere(String wClause,
			boolean retrieveAssociation) {
		ResultSet results;
		ArrayList<Doctor> list = new ArrayList<Doctor>();
		String query = buildQuery(wClause);
		try {
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery(query);

			while (results.next()) {
				Doctor doctor = new Doctor();
				doctor = buildDoctor(results);
				list.add(doctor);
			}
			stmt.close();
		} catch (Exception e) {
			System.out.println("Query exception - select: " + e);
			e.printStackTrace();
		}
		return list;
	}

	private Doctor buildDoctor(ResultSet results) {
		Doctor doctor = new Doctor();
		try {
			doctor.setID(results.getInt("doctorId"));
			doctor.setName(results.getString("name"));
			doctor.setsName(results.getString("sname"));
			doctor.setAddress(results.getString("address"));
			doctor.setEmail(results.getString("email"));
			doctor.setPhone(results.getString("phone"));
		} catch (Exception e) {
			System.out.println("Error in building the doctor object");
		}
		return doctor;
	}

	private String buildQuery(String wClause) {
		String query = "SELECT doctorId, name, sname, address, email, phone FROM doctor";

		if (wClause.length() > 0)
			query = query + " WHERE " + wClause;

		return query;
	}
}
