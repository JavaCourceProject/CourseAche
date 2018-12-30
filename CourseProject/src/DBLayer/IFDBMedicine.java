/**
 * @author Jelizaveta Kuznecova
 */
package DBLayer;


import java.util.ArrayList;

import ModelLayer.Medicine;

public interface IFDBMedicine {
	public Medicine getMedicine(int medicineId, boolean retriveAssociation);
	
	public ArrayList<Medicine> getAllMedicines(boolean retrieveAssociation);
}
