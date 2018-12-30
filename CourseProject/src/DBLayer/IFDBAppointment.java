/**
 * @author Aurimas Blazys
 */
package DBLayer;

import java.util.ArrayList;

import ModelLayer.Appointment;

public interface IFDBAppointment {

	public Appointment getAppointment(int appointmentId, boolean retriveAssociation);

	public int insertAppointment(Appointment appointment);

	public int updateAppointment(Appointment appointment);

	public int deleteAppointment(int appointmentId);

	public ArrayList<Appointment> getAllAppointment(boolean retrieveAssociation);
}
