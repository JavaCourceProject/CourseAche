package ControlLayer;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

import DBLayer.DBAppointment;
import DBLayer.DBConnection;
import DBLayer.IFDBAppointment;
import ModelLayer.Appointment;

public class AppointmentCtr {
	
	public AppointmentCtr() {
	}

	// Insert appointment
	public int insertApp(String date, Time time, int patient, int doctor, int medicine) {
		System.out.println("19");
		int appId = 0;
		Appointment app = new Appointment();
		app.setDate(date);
		app.setTime(time);
		app.setPatient(patient);
		app.setDoctor(doctor);
		app.setMedicine(medicine);
		try {
			DBConnection.startTransaction();
			DBAppointment dbApp = new DBAppointment();
			appId = dbApp.insertAppointment(app);
			DBConnection.commitTransaction();
		} catch (Exception e) {
			DBConnection.rollbackTransaction();
		}
		return appId;
	}

	// Get doctor
	public Object getAppointment(int appId) throws Exception {
		Appointment app = new Appointment();
		IFDBAppointment dbApp = new DBAppointment();
		app = dbApp.getAppointment(appId, true);
		if (app != null) {
			app = dbApp.getAppointment(app.getID(), true);
			app.setDate(app.getDate());
			app.setTime(app.getTime());
			app.setPatient(app.getPatient());
			app.setDoctor(app.getDoctor());
			app.setMedicine(app.getMedicine());
			app.setID(app.getID());
			return app;
		} else {
			throw new Exception("Appointment not found");
		}
	}

	// Update doctor
	public void updateAppointment(int appId, String date, Time time, int patient, int doctor, int medicine) {
		Appointment app = new Appointment();
		app.setID(appId);
		app.setDate(date);
		app.setTime(time);
		app.setPatient(patient);
		app.setDoctor(doctor);
		app.setMedicine(medicine);
		try {
			DBConnection.startTransaction();
			DBAppointment dbApp = new DBAppointment();
			dbApp.updateAppointment(app);
			DBConnection.commitTransaction();
		} catch (Exception e) {
			DBConnection.rollbackTransaction();
		}
	}

	// Delete doctor
	public void deleteAppointment(int appId) {
		try {
			DBConnection.startTransaction();
			IFDBAppointment dbApp = new DBAppointment();
			dbApp.deleteAppointment(appId);
			DBConnection.commitTransaction();
		} catch (Exception e) {
			DBConnection.rollbackTransaction();
		}
	}

	// Get doctor array
	public ArrayList<Appointment> getAllAppointment() {
		ArrayList<Appointment> apps = new ArrayList<Appointment>();
		for (Appointment app : apps) {
			try {
				app = (Appointment) getAppointment(app.getID());
				app.getDate();
				app.getTime();
				app.getPatient();
				app.getDoctor();
				app.getMedicine();
			} catch (Exception e) {
			}
		}
		return apps;
	}
}
