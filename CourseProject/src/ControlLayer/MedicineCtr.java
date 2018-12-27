package ControlLayer;

import java.util.ArrayList;

import DBLayer.DBMedicine;
import DBLayer.IFDBMedicine;
import ModelLayer.Medicine;

public class MedicineCtr {

	public MedicineCtr() {
	}

	// Get Medicine
	public Object getMedicine(int medicineId) throws Exception {
		Medicine medicine = new Medicine();
		IFDBMedicine dbMedicine = new DBMedicine();
		medicine = dbMedicine.getMedicine(medicineId, true);
		if (medicine != null) {
			medicine = dbMedicine.getMedicine(medicine.getID(), true);
			medicine.setName(medicine.getName());
			medicine.setQty(medicine.getQty());
			medicine.setUsage(medicine.getUsage());
			medicine.setSupplier(medicine.getSupplier());
			return medicine;
		} else {
			throw new Exception("Medicine not found");
		}
	}

	// Get person array
	public ArrayList<Medicine> getAllMedicine() {
		ArrayList<Medicine> medicines = new ArrayList<Medicine>();
		for (Medicine medicine : medicines) {
			try {
				medicine = (Medicine) getMedicine(medicine.getID());
				medicine.getName();
				medicine.getQty();
				medicine.getUsage();
				medicine.getSupplier();				
			} catch (Exception e) {
			}
		}
		return medicines;
	}
}
