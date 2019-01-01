/**
 * @author Jelizaveta Kuznecova
 */
package GUILayer;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;

import DBLayer.DBMedicine;
import DBLayer.IFDBMedicine;
import ModelLayer.Medicine;
import javax.swing.*;

public class MedicineUI extends JPanel {
	

	private JTextField textField_medicine_name;
	private JTextField textField_medicine_qty;
	private JTextField textField_medicine_usage;
	private JTextField textField_medicine_supplier;
	private JPanel appInfoPanel;
	private JPanel choicePanel;
	
	public MedicineUI(int medicine_ID){
		initialize(medicine_ID);
	}
	
	public void initialize(int medicine_ID) {
		
		textField_medicine_name = new JTextField();
		textField_medicine_qty = new JTextField();
		textField_medicine_usage = new JTextField();
		textField_medicine_supplier = new JTextField();
		
		// show medicine info view
		JPanel showAppPanel = new JPanel();
		this.add(showAppPanel, BorderLayout.EAST);
		showAppPanel.setLayout(new BorderLayout());
		showAppPanel.setBorder(BorderFactory.createTitledBorder("Appointment"));
		
		//Medicine Label
		appInfoPanel = new JPanel();
		JLabel lblmedicinename = new JLabel("Appointment Id: ");
		JLabel lblmedicineqty = new JLabel("Date: ");
		JLabel lblmedicineusage = new JLabel("Time: ");
		JLabel lblmedicinesupplier = new JLabel("Patient: ");
		
		GroupLayout gl = new GroupLayout(appInfoPanel);
		appInfoPanel.setLayout(gl);
		appInfoPanel.setBorder(BorderFactory.createTitledBorder("Info"));
		gl.setAutoCreateGaps(true);
		gl.setAutoCreateContainerGaps(true);

		gl.setHorizontalGroup(gl.createSequentialGroup()
				.addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(lblmedicinename)
						.addComponent(lblmedicineqty)
						.addComponent(lblmedicineusage)
						.addComponent(lblmedicinesupplier))
				.addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(textField_medicine_name)
						.addComponent(textField_medicine_qty)
						.addComponent(textField_medicine_usage)
						.addComponent(textField_medicine_supplier)
						));
		gl.setVerticalGroup(gl.createSequentialGroup()
				.addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lblmedicinename)
						.addComponent(textField_medicine_name))
				.addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lblmedicineqty)
						.addComponent(textField_medicine_qty))
				.addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lblmedicineusage)
						.addComponent(textField_medicine_usage))
				.addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lblmedicinesupplier)
						.addComponent(textField_medicine_supplier))
						);

		showAppPanel.add(appInfoPanel, BorderLayout.NORTH);

		choicePanel = new JPanel();
		choicePanel.setLayout(new FlowLayout());
		
		showAppPanel.add(choicePanel, BorderLayout.CENTER);

		
		IFDBMedicine dbMedicine = new DBMedicine();
		ArrayList<Medicine> medicines = dbMedicine.getAllMedicines(true);

		for (Medicine medicine : medicines) {
			if (medicine.getID() == medicine_ID) System.out.println(medicine.getName());
		}
		
	}

}
