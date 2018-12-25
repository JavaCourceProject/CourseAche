package ControlLayer;

import java.util.ArrayList;

import DBLayer.DBConnection;
import DBLayer.DBDoctor;
import DBLayer.IFDBDoctor;
import ModelLayer.Doctor;

public class DoctorCtr {
	public DoctorCtr() {
	}

	// Insert doctor
	public int insertDoctor(String name, String sname, String address, String email, String phone) {
		int doctorId = 0;
		Doctor doctor = new Doctor();
		doctor.setName(name);
		doctor.setsName(sname);
		doctor.setAddress(address);
		doctor.setPhone(email);
		doctor.setEmail(phone);
		try {
			DBConnection.startTransaction();
			DBDoctor dbDoctor = new DBDoctor();
			doctorId = dbDoctor.insertDoctor(doctor);
			DBConnection.commitTransaction();
		} catch (Exception e) {
			DBConnection.rollbackTransaction();
		}
		return doctorId;
	}

	// Get doctor
	public Object getDoctor(int doctorId) throws Exception {
		Doctor doctor = new Doctor();
		IFDBDoctor dbDoctor = new DBDoctor();
		doctor = dbDoctor.getDoctor(doctorId, true);
		if (doctor != null) {
			doctor = dbDoctor.getDoctor(doctor.getID(), true);
			doctor.setAddress(doctor.getAddress());
			doctor.setName(doctor.getName());
			doctor.setsName(doctor.getsName());
			doctor.setPhone(doctor.getEmail());
			doctor.setEmail(doctor.getPhone());
			doctor.setID(doctor.getID());
			return doctor;
		} else {
			throw new Exception("Doctor not found");
		}
	}

	// Update doctor
	public void updateDoctor(int doctorId, String name, String sname, String address, String email, String phone) {
		Doctor doctor = new Doctor();
		doctor.setID(doctorId);
		doctor.setName(name);
		doctor.setsName(sname);
		doctor.setAddress(address);
		doctor.setPhone(email);
		doctor.setEmail(phone);
		try {
			DBConnection.startTransaction();
			DBDoctor dbDoctor = new DBDoctor();
			dbDoctor.updateDoctor(doctor);
			DBConnection.commitTransaction();
		} catch (Exception e) {
			DBConnection.rollbackTransaction();
		}
	}

	// Delete doctor
	public void deleteDoctor(int doctorId) {
		try {
			DBConnection.startTransaction();
			IFDBDoctor dbDoctor = new DBDoctor();
			dbDoctor.deleteDoctor(doctorId);
			DBConnection.commitTransaction();
		} catch (Exception e) {
			DBConnection.rollbackTransaction();
		}
	}

	// Get doctor array
	public ArrayList<Doctor> getAllDoctor() {
		ArrayList<Doctor> doctors = new ArrayList<Doctor>();
		for (Doctor doctor : doctors) {
			try {
				doctor = (Doctor) getDoctor(doctor.getID());
				doctor.getName();
				doctor.getsName();
				doctor.getAddress();
				doctor.getEmail();
				doctor.getPhone();
			} catch (Exception e) {
			}
		}
		return doctors;
	}
}
