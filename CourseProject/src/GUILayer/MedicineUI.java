/**
 * @author Jelizaveta Kuznecova
 */
package GUILayer;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import DBLayer.DBMedicine;
import DBLayer.IFDBMedicine;
import ModelLayer.Medicine;
import javax.swing.*;

import ControlLayer.PersonCtr;

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

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		textField_medicine_name = new JTextField("", 20);
		textField_medicine_qty = new JTextField("", 20);
		textField_medicine_usage = new JTextField("", 20);
		textField_medicine_supplier = new JTextField("", 20);
		
		// show medicine info view
		JPanel showAppPanel = new JPanel();
		this.add(showAppPanel, BorderLayout.EAST);
		showAppPanel.setLayout(new FlowLayout());
		showAppPanel.setBorder(BorderFactory.createTitledBorder("Medicine"));
		
		//Medicine Label
		appInfoPanel = new JPanel();
		JLabel lblmedicinename = new JLabel("Medicine Name: ");
		JLabel lblmedicineqty = new JLabel("Medicine QTY: ");
		JLabel lblmedicineusage = new JLabel("Medicine Usage: ");
		JLabel lblmedicinesupplier = new JLabel("Medicine Supplier: ");
		
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
		choicePanel.setBorder(BorderFactory.createTitledBorder("Login"));
		
//		showAppPanel.add(choicePanel, BorderLayout.CENTER);

		
		IFDBMedicine dbMedicine = new DBMedicine();
		ArrayList<Medicine> medicines = dbMedicine.getAllMedicines(true);

		for (Medicine medicine : medicines) {
			if (medicine.getID() == medicine_ID) {
				textField_medicine_name.setText(medicine.getName());
				textField_medicine_qty.setText(Double.toString(medicine.getQty()));
				textField_medicine_usage.setText(Integer.toString(medicine.getUsage()));
				textField_medicine_supplier.setText(medicine.getSupplier());
			}
		}
		
	}

}
