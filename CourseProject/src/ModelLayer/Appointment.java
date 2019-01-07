/**
 * @author Aurimas Blazys
 */
package ModelLayer;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import DBLayer.DBDoctor;

public class Appointment {
	private int ID;
	private Date date;
	private Time time;
	private int patient;
	private int doctor;
	private String doctor_name;
	private int medicine;
	private ArrayList<Doctor> doctors = new ArrayList<Doctor>();
	private ArrayList<DBDoctor> dbdoctor;

	public Appointment() {
	}

	public Appointment(int id) {
		ID = id;
	}

	public String toString() {
		return "/nID: " + getID() 
			+ "/nDate: " + getDate() 
			+ "/nTime: " + getTime()
			+ "/nPacient: " + getPatient() 
			+ "/nDoctor: " + getDoctor()
			+ "/nMedicine: " + getMedicine();
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public int getPatient() {
		return patient;
	}

	public void setPatient(int patient) {
		this.patient = patient;
	}

	public int getDoctor() {
		return doctor;
	}

	public void setDoctor(int doctor) {
		this.doctor = doctor;
	}

	public int getMedicine() {
		return medicine;
	}

	public void setMedicine(int medicine) {
		this.medicine = medicine;
	}
	
	public ArrayList<Doctor> getDoctors() {
		return doctors;
	}

	public void setDoctors(ArrayList<Doctor> doctors) {
		this.doctors = doctors;
	}
}
