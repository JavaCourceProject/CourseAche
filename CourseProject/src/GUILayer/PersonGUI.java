/**
 * @author Jelizaveta Kuznecova
 */
package GUILayer;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class PersonGUI extends JPanel {

	private static final long serialVersionUID = 1L;

	public PersonGUI() {
		initialize();
	}

	public void initialize() {
		this.setLayout(new BorderLayout());

		JTabbedPane subMenu = new JTabbedPane();
		subMenu.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
		this.add(subMenu, BorderLayout.CENTER);

		JPanel PacientGUI = new PacientGUI(); // Patient tab
		subMenu.add("Patient", PacientGUI);

		JPanel DoctorGUI = new DoctorGUI(); // Doctor tab
		subMenu.add("Doctor", DoctorGUI);
	}
}
