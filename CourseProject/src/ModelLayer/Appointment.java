package ModelLayer;

import java.sql.Time;
import java.util.Date;
import java.util.LinkedList;

public class Appointment {
	private int ID;
	private String date;
	private Time time;
	private int patient;
	private int doctor;
	private int medicine;
	private LinkedList<Doctor> doctors;

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
			//+ "/nDoctors: " + getDoctors();
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
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
	
	public LinkedList<Doctor> getDoctors() {
		return doctors;
	}

	public void setDoctors(LinkedList<Doctor> doctors) {
		this.doctors = doctors;
	}
}
