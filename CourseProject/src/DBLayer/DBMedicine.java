/**
 * @author Jelizaveta Kuznecova
 */
package DBLayer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ModelLayer.Medicine;

public class DBMedicine implements IFDBMedicine {
	private Connection con;

	public DBMedicine() {
		con = DBConnection.getInstance().getDBcon();
	}
	
	@Override
	public ArrayList<Medicine> getAllMedicines(boolean retrieveAssociation) {
		return miscWhere("", retrieveAssociation);
	}

	@Override
	public Medicine getMedicine(int medicineId, boolean retriveAssociation) {
		String wClause = "  medicineId = '" + medicineId + "'";
		return singleWhere(wClause, retriveAssociation);
	}

//	@Override
//	public int insertDoctor(Doctor doctor) {
//		int nextId = GetMaxId.getMaxId("Select max(doctorId) from doctor") + 1;
//		String query = "INSERT INTO doctor(doctorId, name, sname, address, email, phone) VALUES('"
//				+ nextId
//				+ "','"
//				+ doctor.getName()
//				+ "','"
//				+ doctor.getsName()
//				+ "','"
//				+ doctor.getAddress()
//				+ "','"
//				+ doctor.getEmail()
//				+ "','"
//				+ doctor.getPhone()
//				+ "')";
//		System.out.println("insert : " + query);
//		try {
//			Statement stmt = con.createStatement();
//			stmt.setQueryTimeout(5);
//			stmt.executeUpdate(query);
//			stmt.close();
//			con.commit();
//		} catch (SQLException ex) {
//			System.out.println("Doctor is not inserted");
//		}
//		return nextId;
//	}
//
//	@Override
//	public int updateDoctor(Doctor doctor) {
//		Doctor doctorObj = doctor;
//		int rc = -1;
//		String query = "UPDATE doctor SET " 
//				+ "name = '" + doctorObj.getName() + "'," 
//				+ "sname = '" + doctorObj.getsName() + "'," 
//				+ "address = '" + doctorObj.getAddress() + "'," 
//				+ "email = '" + doctorObj.getEmail() + "',"
//				+ "phone = '" + doctorObj.getPhone() + "'"
//				+ " WHERE doctorId = '" + doctorObj.getID() + "'";
//		System.out.println("Update query:" + query);
//		try {
//			Statement stmt = con.createStatement();
//			stmt.setQueryTimeout(5);
//			rc = stmt.executeUpdate(query);
//			stmt.close();
//		} catch (Exception e) {
//			System.out.println("Update exception in doctor db: " + e);
//		}
//		return rc;
//	}
//
//	@Override
//	public int deleteDoctor(int doctorId) {
//		int rc = -1;
//		String query = "DELETE FROM doctor WHERE doctorId = '" + doctorId + "'";
//		System.out.println(query);
//		try {
//			Statement stmt = con.createStatement();
//			stmt.setQueryTimeout(5);
//			rc = stmt.executeUpdate(query);
//			stmt.close();
//		} catch (Exception ex) {
//			System.out.println("Delete exception in doctor db: " + ex);
//		}
//		return rc;
//	}
	
	private Medicine singleWhere(String wClause, boolean retrieveAssociation) {
		ResultSet results;
		Medicine medicine = new Medicine();
		String query = buildQuery(wClause);
		System.out.println(query);
		try {
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery(query);

			if (results.next()) {
				medicine = buildMedicine(results);
				stmt.close();
			} else {
				medicine = null;
			}
		} catch (Exception e) {
			System.out.println("Query exception: " + e);
		}
		return medicine;
	}
	
	private ArrayList<Medicine> miscWhere(String wClause,
			boolean retrieveAssociation) {
		ResultSet results;
		ArrayList<Medicine> list = new ArrayList<Medicine>();
		String query = buildQuery(wClause);
		try {
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery(query);

			while (results.next()) {
				Medicine medicine = new Medicine();
				medicine = buildMedicine(results);
				list.add(medicine);
			}
			stmt.close();
		} catch (Exception e) {
			System.out.println("Query exception - select: " + e);
			e.printStackTrace();
		}
		return list;
	}

	private Medicine buildMedicine(ResultSet results) {
		Medicine medicine = new Medicine();
		try {
			medicine.setID(results.getInt("medicineId"));
			medicine.setName(results.getString("name"));
			medicine.setQty(results.getDouble("qty"));
			medicine.setUsage(results.getInt("usage"));
			medicine.setSupplier(results.getString("supplier"));
		} catch (Exception e) {
			System.out.println("Error in building the medicine object");
		}
		return medicine;
	}

	private String buildQuery(String wClause) {
		String query = "SELECT medicineId, name, qty, usage, supplier FROM medicine";

		if (wClause.length() > 0)
			query = query + " WHERE " + wClause;

		return query;
	}
}
