package DBLayer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ModelLayer.Appointment;

public class DBAppointment implements IFDBAppointment {
	private Connection con;

	public DBAppointment() {
		con = DBConnection.getInstance().getDBcon();
	}
	
	@Override
	public ArrayList<Appointment> getAllAppointment(boolean retrieveAssociation) {
		return miscWhere("", retrieveAssociation);
	}

	@Override
	public Appointment getAppointment(int appointmentId, boolean retriveAssociation) {
		String wClause = "  appointmentId = '" + appointmentId + "'";
		return singleWhere(wClause, retriveAssociation);
	}

	@Override
	public int insertAppointment(Appointment app) {
		int nextId = GetMaxId.getMaxId("Select max(doctorId) from doctor") + 1;
		String query = "INSERT INTO appointment(appointmentId, date, time, patient, doctor, medicine) VALUES('"
				+ nextId
				+ "','"
				+ app.getDate()
				+ "','"
				+ app.getTime()
				+ "','"
				+ app.getPatient()
				+ "','"
				+ app.getDoctor()
				+ "','"
				+ app.getMedicine()
				+ "')";
		System.out.println("insert : " + query);
		try {
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			stmt.executeUpdate(query);
			stmt.close();
			con.commit();
		} catch (SQLException ex) {
			System.out.println("Appointment is not inserted");
		}
		return nextId;
	}

	@Override
	public int updateAppointment(Appointment app) {
		Appointment appointmentObj = app;
		int rc = -1;
		String query = "UPDATE appointment SET " 
				+ "date = '" + appointmentObj.getDate() + "'," 
				+ "time = '" + appointmentObj.getTime() + "'," 
				+ "patient = '" + appointmentObj.getPatient() + "'," 
				+ "doctor = '" + appointmentObj.getDoctor() + "',"
				+ "medicine = '" + appointmentObj.getMedicine() + "'"
				+ " WHERE doctorId = '" + appointmentObj.getID() + "'";
		System.out.println("Update query:" + query);
		try {
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			rc = stmt.executeUpdate(query);
			stmt.close();
		} catch (Exception e) {
			System.out.println("Update exception in appointment db: " + e);
		}
		return rc;
	}

	@Override
	public int deleteAppointment(int appointmentId) {
		int rc = -1;
		String query = "DELETE FROM appointment WHERE appointmentId = '" + appointmentId + "'";
		System.out.println(query);
		try {
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			rc = stmt.executeUpdate(query);
			stmt.close();
		} catch (Exception ex) {
			System.out.println("Delete exception in appointment db: " + ex);
		}
		return rc;
	}
	
	private Appointment singleWhere(String wClause, boolean retrieveAssociation) {
		ResultSet results;
		Appointment app = new Appointment();
		String query = buildQuery(wClause);
		System.out.println(query);
		try {
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery(query);

			if (results.next()) {
				app = buildAppointment(results);
				stmt.close();
			} else {
				app = null;
			}
		} catch (Exception e) {
			System.out.println("Query exception: " + e);
		}
		return app;
	}
	
	private ArrayList<Appointment> miscWhere(String wClause, boolean retrieveAssociation) {
		ResultSet results;
		ArrayList<Appointment> list = new ArrayList<Appointment>();
		String query = buildQuery(wClause);
		try {
			Statement stmt = con.createStatement();
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery(query);

			while (results.next()) {
				Appointment appointment = new Appointment();
				appointment = buildAppointment(results);
				list.add(appointment);
			}
			stmt.close();
		} catch (Exception e) {
			System.out.println("Query exception - select: " + e);
			e.printStackTrace();
		}
		return list;
	}

	private Appointment buildAppointment(ResultSet results) {
		Appointment app = new Appointment();
		try {
			app.setID(results.getInt("appointmentId"));
			//app.setDate(results.getString("date"));
			//app.setTime(results.getString("time"));
			app.setPatient(results.getInt("patient"));
			app.setDoctor(results.getInt("doctor"));
			app.setMedicine(results.getInt("medicine"));
		} catch (Exception e) {
			System.out.println("Error in building the appointment object");
		}
		return app;
	}

	private String buildQuery(String wClause) {
		String query = "SELECT appointmentId, date, time, patient, doctor, medicine FROM appointment";

		if (wClause.length() > 0)
			query = query + " WHERE " + wClause;

		return query;
	}
}
