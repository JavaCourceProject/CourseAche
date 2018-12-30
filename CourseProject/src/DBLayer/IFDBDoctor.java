/**
 * @author Jelizaveta Kuznecova
 */
package DBLayer;

import java.util.ArrayList;

import ModelLayer.Doctor;

public interface IFDBDoctor {
	public Doctor getDoctor(int doctorId, boolean retriveAssociation);

	public int insertDoctor(Doctor doctor);

	public int updateDoctor(Doctor doctor);

	public int deleteDoctor(int doctorId);

	public ArrayList<Doctor> getAllDoctor(boolean retrieveAssociation);
}
