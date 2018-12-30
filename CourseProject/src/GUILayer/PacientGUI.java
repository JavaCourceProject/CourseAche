/**
 * @author Jelizaveta Kuznecova
 */
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

import ControlLayer.PersonCtr;
import DBLayer.DBPerson;
import DBLayer.IFDBPerson;
import ModelLayer.Person;

public class PacientGUI extends JPanel {
	private static final long serialVersionUID = 1L;
	private PersonCtr personCtr;
	private DefaultTableModel personTableModel;
	private JScrollPane personTableScrollPane;
	private JPanel showAllPersonPanel;
	private JTable showAllPersonTable;
	private JPanel personInfoPanel;
	private JPanel choicePanel;

	private JTextField textField_personId;
	private JTextField txt_Search;
	private JTextField textField_fname;
	private JTextField textField_address;
	private JTextField textField_email;
	private JTextField textField_phone;
	private JTextField textField_lname;

	private JButton btnSave;
	private JButton btnCreate;
	private JButton btnCancel;
	private JButton btnDelete;

	public PacientGUI() {
		personCtr = new PersonCtr();
		initialize();
	}

	public void initialize() {
		this.setLayout(new BorderLayout());
		showAllPersonPanel = new JPanel();
		this.add(showAllPersonPanel, BorderLayout.CENTER);
		showAllPersonPanel.setBorder(BorderFactory.createTitledBorder("Patient Info"));
		showAllPersonPanel.setLayout(new BorderLayout());

		txt_Search = new JTextField();
		txt_Search.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				showAllPerson();
			}
		});
		txt_Search.setColumns(10);

		JPanel searchPanel = new JPanel();
		showAllPersonPanel.add(searchPanel, BorderLayout.NORTH);
		GroupLayout gl_searchPanel = new GroupLayout(searchPanel);
		gl_searchPanel
				.setHorizontalGroup(gl_searchPanel.createParallelGroup(Alignment.LEADING).addComponent(txt_Search));
		gl_searchPanel.setVerticalGroup(gl_searchPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_searchPanel.createSequentialGroup().addGap(5).addComponent(txt_Search,
						GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)));
		searchPanel.setLayout(gl_searchPanel);

		// show customer info view
		JPanel showPersonPanel = new JPanel();
		this.add(showPersonPanel, BorderLayout.EAST);
		showPersonPanel.setLayout(new BorderLayout());
		showPersonPanel.setBorder(BorderFactory.createTitledBorder("Patient"));

		// Person - Patient labels
		personInfoPanel = new JPanel();
		JLabel lblpersonId = new JLabel("Patient Id: ");
		JLabel lblfname = new JLabel("First Name: ");
		JLabel lbladdress = new JLabel("Address: ");
		JLabel lblPhone = new JLabel("Phone: ");
		JLabel lbllname = new JLabel("Last Name: ");
		JLabel lblemail = new JLabel("Email: ");

		// Person - Patient fields
		textField_personId = new JTextField();
		textField_personId.setEditable(false); // personId not editable

		textField_fname = new JTextField();
		textField_address = new JTextField();
		textField_email = new JTextField();
		textField_phone = new JTextField();
		textField_lname = new JTextField();

		GroupLayout gl = new GroupLayout(personInfoPanel);
		personInfoPanel.setLayout(gl);
		personInfoPanel.setBorder(BorderFactory.createTitledBorder("Info"));
		gl.setAutoCreateGaps(true);
		gl.setAutoCreateContainerGaps(true);

		gl.setHorizontalGroup(gl.createSequentialGroup()
				.addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(lblpersonId)
						.addComponent(lblfname)
						.addComponent(lbllname)
						.addComponent(lblPhone)
						.addComponent(lbladdress)
						.addComponent(lblemail))
				.addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(textField_personId)
						.addComponent(textField_fname)
						.addComponent(textField_lname)
						.addComponent(textField_address)
						.addComponent(textField_phone)
						.addComponent(textField_email)));
		gl.setVerticalGroup(gl.createSequentialGroup()
				.addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lblpersonId)
						.addComponent(textField_personId))
				.addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lblfname)
						.addComponent(textField_fname))
				.addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lbllname)
						.addComponent(textField_lname))
				.addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lbladdress)
						.addComponent(textField_address))
				.addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lblPhone)
						.addComponent(textField_phone))
				.addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(lblemail)
						.addComponent(textField_email)));

		showPersonPanel.add(personInfoPanel, BorderLayout.NORTH);

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
		showPersonPanel.add(choicePanel, BorderLayout.CENTER);

		addPersonTable();
		showAllPerson();

		// Create button
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!textField_personId.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please clear patient fields before create one", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					try {
						personCtr.insertPerson(
								textField_fname.getText(), 
								textField_lname.getText(),
								textField_address.getText(), 
								textField_phone.getText(), 
								textField_email.getText(),
								"124");
						JOptionPane.showMessageDialog(null, "The patient is created", "Create patient",
								JOptionPane.INFORMATION_MESSAGE);

					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "Please input correct values", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
					cleanTextField();
					showAllPerson();
				}
			}
		});

		// Delete button
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textField_personId.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please select a patient", "Error", JOptionPane.ERROR_MESSAGE);
				} else {
					int reply = JOptionPane.showConfirmDialog(null, "Do you want to delete this patient ?",
							"Delete patient", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					if (reply == JOptionPane.YES_OPTION) {
						try {
							personCtr.deletePerson(Integer.parseInt(textField_personId.getText()));
							JOptionPane.showMessageDialog(null, "The patient is deleted", "Delete patient",
									JOptionPane.INFORMATION_MESSAGE);
							cleanTextField();

							showAllPerson();
						} catch (Exception e1) {
						}
					}
				}
			}
		});

		// Save button
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textField_personId.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please select a patient", "Error", JOptionPane.ERROR_MESSAGE);
				} else {
					try {
						personCtr.updatePerson(Integer.parseInt(
								textField_personId.getText()),
								textField_fname.getText(), 
								textField_lname.getText(), 
								textField_address.getText(),
								textField_phone.getText(), 
								textField_email.getText(), 
								5);
						JOptionPane.showMessageDialog(null, "The patient info is saved", "Update patient",
								JOptionPane.INFORMATION_MESSAGE);
					} catch (Exception a) {
						JOptionPane.showMessageDialog(null, "Please input correct values", "Error",
								JOptionPane.ERROR_MESSAGE);
					}

					cleanTextField();
					showAllPerson();
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

	// Person - Person grid
	public void addPersonTable() {
		personTableModel = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;

			// Cell not editable in grid
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		// Grid column names
		personTableModel.addColumn("ID");
		personTableModel.addColumn("First Name");
		personTableModel.addColumn("Last Name");
		personTableModel.addColumn("Phone");
		personTableModel.addColumn("Email");

		personTableScrollPane = new JScrollPane();

		showAllPersonPanel.add(personTableScrollPane, BorderLayout.CENTER);

		showAllPersonTable = new JTable(personTableModel);
		showAllPersonTable.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
		showAllPersonTable.setAutoCreateRowSorter(true);

		showAllPersonTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = showAllPersonTable.rowAtPoint(e.getPoint());
				showPerson(Integer.valueOf((Integer) showAllPersonTable.getValueAt(row, 0)));
			}
		});
		personTableScrollPane.setViewportView(showAllPersonTable);
	}

	public void showAllPerson() {
		personTableModel.setRowCount(0);
		String searchFilter = txt_Search.getText();

		IFDBPerson dbPerson = new DBPerson();
		ArrayList<Person> persons = dbPerson.getAllPerson(true);

		for (Person person : persons) {
			if (((Integer) person.getID()).toString().toLowerCase().contains(searchFilter.toLowerCase())
					|| person.getfName().toLowerCase().contains(searchFilter.toLowerCase())
					|| person.getlName().toLowerCase().contains(searchFilter.toLowerCase()))
				try {
					personTableModel.addRow(new Object[] { person.getID(), person.getfName(), person.getlName(),
							person.getPhone(), person.getEmail() });
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
				}
		}
	}

	public boolean showPerson(int personId) {
		boolean success = true;
		Person person = null;
		try {
			if (personId != 0) {
				IFDBPerson dbPerson = new DBPerson();
				person = dbPerson.getPerson(personId, true);

				person.setAddress(person.getAddress());
				person.setfName(person.getfName());
				person.setlName(person.getlName());
				person.setPhone(person.getPhone());
				person.setEmail(person.getEmail());
				person.setID(person.getID());
				person.setLogId(person.getLogId());
			}
		} catch (Exception e) {
			success = false;
			JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
			return success;
		}

		if (success) {
			textField_personId.setText(Integer.toString(person.getID()));
			textField_fname.setText(person.getfName());
			textField_lname.setText(person.getlName());
			textField_address.setText(person.getAddress());
			textField_email.setText(person.getEmail());
			textField_phone.setText(person.getPhone());
		}
		return success;
	}

	public void cleanTextField() {
		txt_Search.setText("");
		textField_personId.setText("");
		textField_fname.setText("");
		textField_lname.setText("");
		textField_address.setText("");
		textField_email.setText("");
		textField_phone.setText("");
	}
}
