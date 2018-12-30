/**
 * @author Jelizaveta Kuznecova
 */
package DBLayer;

import java.util.ArrayList;

import ModelLayer.Person;

public interface IFDBPerson {
	public Person getPerson(int personId, boolean retriveAssociation);

	public int insertPerson(Person person);

	public int updatePerson(Person person);

	public int deletePerson(int personId);

	public Person loginGUI(String logname, boolean retriveAssociation);
	
	public ArrayList<Person> getAllPerson(boolean retrieveAssociation);
}
