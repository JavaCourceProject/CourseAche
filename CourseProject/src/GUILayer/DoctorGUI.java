//GUILayer doctorGUI
package GUILayer;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.table.DefaultTableModel;

import ControlLayer.DoctorCtr;
import DBLayer.DBDoctor;
import DBLayer.IFDBDoctor;
import ModelLayer.Doctor;

public class DoctorGUI extends JPanel {
	private static final long serialVersionUID = 1L;
	private DoctorCtr doctorCtr;
	private DefaultTableModel doctorTableModel;
	private JScrollPane doctorTableScrollPane;
	private JPanel showAllDoctorPanel;
	private JTable showAllDoctorTable;
	private JPanel doctorInfoPanel;
	private JPanel choicePanel;

	private JTextField textField_doctorId;
	private JTextField txt_Search;
	private JTextField textField_name;
	private JTextField textField_sname;
	private JTextField textField_address;
	private JTextField textField_email;
	private JTextField textField_phone;

	private JButton btnSave;
	private JButton btnCreate;
	private JButton btnCancel;
	private JButton btnDelete;

	public DoctorGUI() {
		doctorCtr = new DoctorCtr();
		initialize();
	}

	public void initialize() {
		this.setLayout(new BorderLayout());
		showAllDoctorPanel = new JPanel();
		this.add(showAllDoctorPanel, BorderLayout.CENTER);
		showAllDoctorPanel.setBorder(BorderFactory.createTitledBorder("Doctor Info"));
		showAllDoctorPanel.setLayout(new BorderLayout());

		txt_Search = new JTextField();
		txt_Search.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				showAllDoctor();
			}
		});

		txt_Search.setColumns(10);

		JPanel searchPanel = new JPanel();
		showAllDoctorPanel.add(searchPanel, BorderLayout.NORTH);
		GroupLayout gl_searchPanel = new GroupLayout(searchPanel);
		gl_searchPanel
				.setHorizontalGroup(gl_searchPanel.createParallelGroup(Alignment.LEADING).addComponent(txt_Search));
		gl_searchPanel.setVerticalGroup(gl_searchPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_searchPanel.createSequentialGroup().addGap(5).addComponent(txt_Search,
						GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)));
		searchPanel.setLayout(gl_searchPanel);

		// show customer info view
		JPanel showDoctorPanel = new JPanel();
		this.add(showDoctorPanel, BorderLayout.EAST);
		showDoctorPanel.setLayout(new BorderLayout());
		showDoctorPanel.setBorder(BorderFactory.createTitledBorder("Doctor"));

		// Person - Doctor labels
		doctorInfoPanel = new JPanel();
		JLabel lbldoctorId = new JLabel("Doctor Id: ");
		JLabel lblname = new JLabel("First Name: ");
		JLabel lblsname = new JLabel("Last Name: ");
		JLabel lbladdress = new JLabel("Address: ");
		JLabel lblphone = new JLabel("Phone: ");
		JLabel lblemail = new JLabel("Email: ");

		// Person - Doctor fields
		textField_doctorId = new JTextField();
		textField_doctorId.setEditable(false); // doctorId not editable

		textField_name = new JTextField();
		textField_sname = new JTextField();
		textField_address = new JTextField();
		textField_email = new JTextField();
		textField_phone = new JTextField();

		GroupLayout gl = new GroupLayout(doctorInfoPanel);
		doctorInfoPanel.setLayout(gl);
		doctorInfoPanel.setBorder(BorderFactory.createTitledBorder("Info"));
		gl.setAutoCreateGaps(true);
		gl.setAutoCreateContainerGaps(true);

		gl.setHorizontalGroup(gl.createSequentialGroup()
				.addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(lbldoctorId)
						.addComponent(lblname)
						.addComponent(lblsname)
						.addComponent(lbladdress)
						.addComponent(lblphone)
						.addComponent(lblemail))
				.addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(textField_doctorId)
						.addComponent(textField_name)
						.addComponent(textField_sname)
						.addComponent(textField_address)
						.addComponent(textField_phone)
						.addComponent(textField_email)));
		gl.setVerticalGroup(gl.createSequentialGroup()
				.addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lbldoctorId)
						.addComponent(textField_doctorId))
				.addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lblname)
						.addComponent(textField_name))
				.addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lblsname)
						.addComponent(textField_sname))
				.addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lbladdress)
						.addComponent(textField_address))
				.addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lblphone)
						.addComponent(textField_phone))
				.addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(lblemail)
						.addComponent(textField_email)));

		showDoctorPanel.add(doctorInfoPanel, BorderLayout.NORTH);

		choicePanel = new JPanel();
		choicePanel.setLayout(new FlowLayout());

		// Choice button labels
		btnCreate = new JButton("Create");
		btnDelete = new JButton("Delete");
		btnSave = new JButton("Save");
		btnCancel = new JButton("Clear");

		// Choice buttons
		choicePanel.add(btnCreate);
		choicePanel.add(btnSave);
		choicePanel.add(btnDelete);
		choicePanel.add(btnCancel);
		showDoctorPanel.add(choicePanel, BorderLayout.CENTER);

		addDoctorTable();
		showAllDoctor();

		// Create button
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!textField_doctorId.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please clear doctor fields before create one", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					try {
						doctorCtr.insertDoctor(
								textField_name.getText(), 
								textField_sname.getText(),
								textField_address.getText(), 
								textField_phone.getText(), 
								textField_email.getText());
						JOptionPane.showMessageDialog(null, "The doctor is created", "Create doctor",
								JOptionPane.INFORMATION_MESSAGE);

					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "Please input correct values", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
					cleanTextField();
					showAllDoctor();
				}
			}
		});

		// Delete button
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textField_doctorId.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please select a doctor", "Error", JOptionPane.ERROR_MESSAGE);
				} else {
					int reply = JOptionPane.showConfirmDialog(null, "Do you want to delete this doctor ?",
							"Delete doctor", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					if (reply == JOptionPane.YES_OPTION) {
						try {
							doctorCtr.deleteDoctor(Integer.parseInt(textField_doctorId.getText()));
							JOptionPane.showMessageDialog(null, "The doctor is deleted", "Delete doctor",
									JOptionPane.INFORMATION_MESSAGE);
							cleanTextField();

							showAllDoctor();
						} catch (Exception e1) {
						}
					}
				}
			}
		});

		// Save button
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textField_doctorId.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please select a doctor", "Error", JOptionPane.ERROR_MESSAGE);
				} else {
					try {
						doctorCtr.updateDoctor(Integer.parseInt(
								textField_doctorId.getText()), 
								textField_name.getText(),
								textField_sname.getText(), 
								textField_address.getText(), 
								textField_phone.getText(),
								textField_email.getText());
						JOptionPane.showMessageDialog(null, "The doctor info is saved", "Update doctor",
								JOptionPane.INFORMATION_MESSAGE);
					} catch (Exception a) {
						JOptionPane.showMessageDialog(null, "Please input correct values", "Error",
								JOptionPane.ERROR_MESSAGE);
					}

					cleanTextField();
					showAllDoctor();
				}
			}
		});

		// Clear button
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cleanTextField();
			}
		});

	}

	// Person - Doctor grid
	public void addDoctorTable() {
		doctorTableModel = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;

			// Cell not editable in grid
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		// Grid column names
		doctorTableModel.addColumn("ID");
		doctorTableModel.addColumn("First Name");
		doctorTableModel.addColumn("Last Name");
		doctorTableModel.addColumn("Phone");
		doctorTableModel.addColumn("Email");

		doctorTableScrollPane = new JScrollPane();

		showAllDoctorPanel.add(doctorTableScrollPane, BorderLayout.CENTER);

		showAllDoctorTable = new JTable(doctorTableModel);
		showAllDoctorTable.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
		showAllDoctorTable.setAutoCreateRowSorter(true);

		showAllDoctorTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = showAllDoctorTable.rowAtPoint(e.getPoint());
				showDoctor(Integer.valueOf((Integer) showAllDoctorTable.getValueAt(row, 0)));
			}
		});
		doctorTableScrollPane.setViewportView(showAllDoctorTable);
	}

	public void showAllDoctor() {
		doctorTableModel.setRowCount(0);
		String searchFilter = txt_Search.getText();

		IFDBDoctor dbDoctor = new DBDoctor();
		ArrayList<Doctor> doctors = dbDoctor.getAllDoctor(true);

		for (Doctor doctor : doctors) {
			if (((Integer) doctor.getID()).toString().toLowerCase().contains(searchFilter.toLowerCase())
						|| doctor.getName().toLowerCase().contains(searchFilter.toLowerCase())
						|| doctor.getsName().toLowerCase().contains(searchFilter.toLowerCase()))
				try {
					doctorTableModel.addRow(new Object[] { doctor.getID(), doctor.getName(), doctor.getsName(),
							doctor.getPhone(), doctor.getEmail() });
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
				}
		}
	}

	public boolean showDoctor(int doctorId) {
		boolean success = true;
		Doctor doctor = null;
		try {
			if (doctorId != 0) {
				IFDBDoctor dbDoctor = new DBDoctor();
				doctor = dbDoctor.getDoctor(doctorId, true);
				doctor.setAddress(doctor.getAddress());
				doctor.setName(doctor.getName());
				doctor.setsName(doctor.getsName());
				doctor.setPhone(doctor.getPhone());
				doctor.setEmail(doctor.getEmail());
				doctor.setID(doctor.getID());
			}
		} catch (Exception e) {
			success = false;
			JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
			return success;
		}

		if (success) {
			textField_doctorId.setText(Integer.toString(doctor.getID()));
			textField_name.setText(doctor.getName());
			textField_sname.setText(doctor.getsName());
			textField_address.setText(doctor.getAddress());
			textField_email.setText(doctor.getEmail());
			textField_phone.setText(doctor.getPhone());
		}
		return success;
	}

	public void cleanTextField() {
		txt_Search.setText("");
		textField_doctorId.setText("");
		textField_name.setText("");
		textField_sname.setText("");
		textField_address.setText("");
		textField_email.setText("");
		textField_phone.setText("");
	}
}
