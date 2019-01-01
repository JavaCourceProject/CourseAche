/**
 * @author Aurimas Blazys
 */
package GUILayer;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.table.DefaultTableModel;

import ControlLayer.AppointmentCtr;
import DBLayer.DBAppointment;
import DBLayer.DBDoctor;
import DBLayer.DBMedicine;
import DBLayer.DBPerson;
import DBLayer.IFDBAppointment;
import DBLayer.IFDBDoctor;
import DBLayer.IFDBMedicine;
import DBLayer.IFDBPerson;
import ModelLayer.Appointment;
import ModelLayer.Doctor;
import ModelLayer.Medicine;
import ModelLayer.Person;

public class AppointmentGUI extends JPanel {
	private static final long serialVersionUID = 1L;
	private AppointmentCtr appCtr;
	private DefaultTableModel appTableModel;
	private JScrollPane appTableScrollPane;
	private JPanel showAllAppPanel;
	private JTable showAllAppTable;
	private JPanel appInfoPanel;
	private JPanel choicePanel;

	private JTextField textField_appId;
	private JTextField txt_Search;
	private JTextField textField_date;
	private JTextField textField_time;
	//private JTextField textField_patient;
	private JComboBox textField_patient;
	//private JTextField textField_doctor;
	private JComboBox textField_doctor;
	//private JTextField textField_medicine;
	private JComboBox textField_medicine;
//	private JCheckBox chkMedicine;
	private JButton btnMedicine;

	private JButton btnSave;
	private JButton btnCreate;
	private JButton btnCancel;
	private JButton btnDelete;
	
	private JFrame frame;
	private MedicineUI medicineUI;
	
	private String doc_name = "";
	private String patient_name = "";
	private String medicine_name = "";
	
	private IFDBDoctor dbDoctor = new DBDoctor();
	private ArrayList<Doctor> doctors = dbDoctor.getAllDoctor(true);
	
	private IFDBPerson dbPerson = new DBPerson();
	private ArrayList<Person> persons = dbPerson.getAllPerson(true);
	
	private IFDBMedicine dbmedicine = new DBMedicine();
	private ArrayList<Medicine> medicines = dbmedicine.getAllMedicines(true);
	
	private String[][] doctors_array = new String[doctors.size()][2];
	private String[][] patient_array = new String[persons.size()][2];
	private String[][] medicine_array = new String[medicines.size()][2];
	private int i = 0;
	
	private LocalDate todayLocalDate  = LocalDate.now();
	private Date date = java.sql.Date.valueOf(todayLocalDate.toString());
	
	private static final String NOT_SELECTABLE_OPTION = " - Select an Option - ";

	public AppointmentGUI() {
		appCtr = new AppointmentCtr();
		initialize();
	}

	public void initialize() {
		this.setLayout(new BorderLayout());
		showAllAppPanel = new JPanel();
		this.add(showAllAppPanel, BorderLayout.CENTER);
		showAllAppPanel.setBorder(BorderFactory.createTitledBorder("Appointment Info"));
		showAllAppPanel.setLayout(new BorderLayout());

		txt_Search = new JTextField();
		txt_Search.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				showAllApp();
			}
		});

		txt_Search.setColumns(10);

		JPanel searchPanel = new JPanel();
		showAllAppPanel.add(searchPanel, BorderLayout.NORTH);
		GroupLayout gl_searchPanel = new GroupLayout(searchPanel);
		gl_searchPanel
				.setHorizontalGroup(gl_searchPanel.createParallelGroup(Alignment.LEADING).addComponent(txt_Search));
		gl_searchPanel.setVerticalGroup(gl_searchPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_searchPanel.createSequentialGroup().addGap(5).addComponent(txt_Search,
						GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)));
		searchPanel.setLayout(gl_searchPanel);

		// show customer info view
		JPanel showAppPanel = new JPanel();
		this.add(showAppPanel, BorderLayout.EAST);
		showAppPanel.setLayout(new BorderLayout());
		showAppPanel.setBorder(BorderFactory.createTitledBorder("Appointment"));

		// Appointment labels
		appInfoPanel = new JPanel();
		JLabel lblappId = new JLabel("Appointment Id: ");
		JLabel lbldate = new JLabel("Date: ");
		JLabel lbltime = new JLabel("Time: ");
		JLabel lblpatient = new JLabel("Patient: ");
		JLabel lbldoctor = new JLabel("Doctor: ");
		JLabel lblmedicine = new JLabel("Medicine: ");

		// Appointment fields
		textField_appId = new JTextField();
		textField_appId.setEditable(false); // doctorId not editable
		
		textField_date = new JTextField();
		textField_date.setText(new SimpleDateFormat("yyyy-MM-dd")
				.format(Calendar.getInstance().getTime()));
		textField_time = new JTextField();
		//textField_patient = new JTextField();
		textField_patient = new JComboBox();
		//textField_doctor = new JTextField();
		textField_doctor = new JComboBox();
		//chkMedicine = new JCheckBox();
		btnMedicine = new JButton("Medicine");
//		textField_medicine = new JTextField();
		textField_medicine = new JComboBox();

		GroupLayout gl = new GroupLayout(appInfoPanel);
		appInfoPanel.setLayout(gl);
		appInfoPanel.setBorder(BorderFactory.createTitledBorder("Info"));
		gl.setAutoCreateGaps(true);
		gl.setAutoCreateContainerGaps(true);

		gl.setHorizontalGroup(gl.createSequentialGroup()
				.addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(lblappId)
						.addComponent(lbldate)
						.addComponent(lbltime)
						.addComponent(lblpatient)
						.addComponent(lbldoctor)
						.addComponent(lblmedicine))
				.addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(textField_appId)
						.addComponent(textField_date)
						.addComponent(textField_time)
						.addComponent(textField_patient)
						.addComponent(textField_doctor)
						.addComponent(textField_medicine)
						//.addComponent(chkMedicine)
						.addComponent(btnMedicine)
						));
		gl.setVerticalGroup(gl.createSequentialGroup()
				.addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lblappId)
						.addComponent(textField_appId))
				.addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lbldate)
						.addComponent(textField_date))
				.addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lbltime)
						.addComponent(textField_time))
				.addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lblpatient)
						.addComponent(textField_patient))
				.addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lbldoctor)
						.addComponent(textField_doctor))
				.addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(lblmedicine)
						.addComponent(textField_medicine))
						//.addComponent(chkMedicine)
						.addComponent(btnMedicine)
						);

		showAppPanel.add(appInfoPanel, BorderLayout.NORTH);

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
		showAppPanel.add(choicePanel, BorderLayout.CENTER);

		//Dropdown List for Patient, Doctor	and Medicine
		
		textField_doctor.setModel(new DefaultComboBoxModel(){
			  private static final long serialVersionUID = 1L;
			  boolean selectionAllowed = true;

			  @Override
			  public void setSelectedItem(Object anObject) {
			    if (!NOT_SELECTABLE_OPTION.equals(anObject)) {
			      super.setSelectedItem(anObject);
			    } else if (selectionAllowed) {
			      // Allow this just once
			      selectionAllowed = false;
			      super.setSelectedItem(anObject);
			    }
			  }
		});
		
		textField_doctor.addItem(NOT_SELECTABLE_OPTION);
		
		for (Doctor doctor : doctors) {			
			textField_doctor.addItem(doctor.getName() + " " + doctor.getsName());
			for (int j = 0; j <= 1; j++){
				if ( j == 0 ) doctors_array[i][j] = ((Integer) doctor.getID()).toString();
				else doctors_array[i][j] = doctor.getName() + " " + doctor.getsName();				
			}
		
			i++;
			//textField_doctor..insertItemAt(doctor.getName() + " " + doctor.getsName(), doctor.getID());
		}

		i = 0;
		
		textField_patient.setModel(new DefaultComboBoxModel(){
			  private static final long serialVersionUID = 1L;
			  boolean selectionAllowed = true;

			  @Override
			  public void setSelectedItem(Object anObject) {
			    if (!NOT_SELECTABLE_OPTION.equals(anObject)) {
			      super.setSelectedItem(anObject);
			    } else if (selectionAllowed) {
			      // Allow this just once
			      selectionAllowed = false;
			      super.setSelectedItem(anObject);
			    }
			  }
		});
		
		textField_patient.addItem(NOT_SELECTABLE_OPTION);
		
		for (Person person : persons) {
			textField_patient.addItem(person.getfName() + " " + person.getlName());
			for (int j = 0; j <= 1; j++){
				if ( j == 0 ) patient_array[i][j] = ((Integer) person.getID()).toString();
				else patient_array[i][j] = person.getfName() + " " + person.getlName();				
			}
		
			i++;
		}
		
		i = 0;
		
		textField_medicine.setModel(new DefaultComboBoxModel(){
			  private static final long serialVersionUID = 1;
			  boolean selectionAllowed = true;

			  @Override
			  public void setSelectedItem(Object anObject) {
			    if (!NOT_SELECTABLE_OPTION.equals(anObject)) {
			      super.setSelectedItem(anObject);
			    } else if (selectionAllowed) {
			      // Allow this just once
			      selectionAllowed = false;
			      super.setSelectedItem(anObject);
			    }
			  }
		});
		
		textField_medicine.addItem(NOT_SELECTABLE_OPTION);
		
		for (Medicine medicine : medicines) {
			textField_medicine.addItem(medicine.getName() + " QTY " + medicine.getQty() + " Usage " + medicine.getUsage());
			
			for (int j = 0; j <= 1; j++){
				if ( j == 0 ) medicine_array[i][j] = ((Integer) medicine.getID()).toString();
				else medicine_array[i][j] = medicine.getName() + " QTY " + medicine.getQty() + " Usage " + medicine.getUsage();				
			}
		
			i++;
		}		
		
		addAppTable();
		showAllApp();

		// Medicine button // Liza
		btnMedicine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = new JFrame("Medicine");
				frame.setSize(550, 320); // Login frame size
				frame.setLocationRelativeTo(null); // Does not depend on previous form
				frame.getContentPane().setLayout(new CardLayout(0, 0));
			    frame.setVisible(true);

				medicineUI = new MedicineUI(Integer.parseInt(medicine_array[textField_medicine.getSelectedIndex()-1][0]));
			}
		});
		
		// Create button
		btnCreate.addActionListener(new ActionListener() {		
			public void actionPerformed(ActionEvent e) {

				String time_format = "";

				if (!textField_appId.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please clear appointment fields before create one", "Error",
							JOptionPane.ERROR_MESSAGE);
					System.out.println("177");
				} else {
					
					time_format = textField_time.getText();
					
					//Time validation
					if (time_format.length() <= 5){
						time_format = time_format + ":00";
					}
					
					try {
						
						if (java.sql.Date.valueOf(textField_date.getText()) != null &&
								date.after(java.sql.Date.valueOf(textField_date.getText())) ){
							
							System.out.println(date);
							System.out.println(java.sql.Date.valueOf(textField_date.getText()) );
							JOptionPane.showMessageDialog(null, "Please input correct date", "Error",
									JOptionPane.ERROR_MESSAGE);	
						}else {
							appCtr.insertApp(
									java.sql.Date.valueOf(textField_date.getText()), 
									java.sql.Time.valueOf(time_format),
									//Integer.parseInt(textField_patient.getText()), 
									Integer.parseInt(patient_array[textField_patient.getSelectedIndex()-1][0]),
									//Integer.parseInt(textField_doctor.getText()), 
									Integer.parseInt(doctors_array[textField_doctor.getSelectedIndex()-1][0]),
//									Integer.parseInt(textField_medicine.getText())
									Integer.parseInt(medicine_array[textField_medicine.getSelectedIndex()-1][0])
									);
							JOptionPane.showMessageDialog(null, "The appointment is created", "Create appointment",
									JOptionPane.INFORMATION_MESSAGE);
						}
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "Please input correct values", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
					cleanTextField();
					showAllApp();
				}
			}
		});

		// Delete button
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textField_appId.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please select an appointment", "Error", JOptionPane.ERROR_MESSAGE);
				} else {
					int reply = JOptionPane.showConfirmDialog(null, "Do you want to delete this appointment ?",
							"Delete doctor", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					if (reply == JOptionPane.YES_OPTION) {
						try {
							appCtr.deleteAppointment(Integer.parseInt(textField_appId.getText()));
							JOptionPane.showMessageDialog(null, "The appointment is deleted", "Delete appointment",
									JOptionPane.INFORMATION_MESSAGE);
							cleanTextField();
							showAllApp();
						} catch (Exception e1) {
						}
					}
				}
			}
		});

		// Save button
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textField_appId.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please select an appointment", "Error", JOptionPane.ERROR_MESSAGE);
				} else {
					try {
						
						if (java.sql.Date.valueOf(textField_date.getText()) != null &&
								date.after(java.sql.Date.valueOf(textField_date.getText())) ){
							
							System.out.println(date);
							System.out.println(java.sql.Date.valueOf(textField_date.getText()) );
							JOptionPane.showMessageDialog(null, "Please input correct date", "Error",
									JOptionPane.ERROR_MESSAGE);							
						}else {
							appCtr.updateAppointment(Integer.parseInt(
									textField_appId.getText()), 
									java.sql.Date.valueOf(textField_date.getText()), 
									java.sql.Time.valueOf(textField_time.getText()),
									//Integer.parseInt(textField_patient.getText()), 
									Integer.parseInt(patient_array[textField_patient.getSelectedIndex()-1][0]),
									//Integer.parseInt(textField_doctor.getText()),
									Integer.parseInt(doctors_array[textField_doctor.getSelectedIndex()-1][0]),
//									Integer.parseInt(textField_medicine.getText())
									Integer.parseInt(medicine_array[textField_medicine.getSelectedIndex()-1][0]));
							JOptionPane.showMessageDialog(null, "The appointment info is saved", "Update appointment",
									JOptionPane.INFORMATION_MESSAGE);
						}
					} catch (Exception a) {
						JOptionPane.showMessageDialog(null, "Please input correct values", "Error",
								JOptionPane.ERROR_MESSAGE);
					}

					cleanTextField();
					showAllApp();
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

	// Appointment grid
	public void addAppTable() {
		appTableModel = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;

			// Cell not editable in grid
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		// Grid column names
		appTableModel.addColumn("ID");
		appTableModel.addColumn("Date");
		appTableModel.addColumn("Time");
		appTableModel.addColumn("Patient");
		appTableModel.addColumn("Doctor");
//		appTableModel.addColumn("Medicine");

		appTableScrollPane = new JScrollPane();

		showAllAppPanel.add(appTableScrollPane, BorderLayout.CENTER);

		showAllAppTable = new JTable(appTableModel);
		showAllAppTable.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
		showAllAppTable.setAutoCreateRowSorter(true);

		showAllAppTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = showAllAppTable.rowAtPoint(e.getPoint());
				showApp(Integer.valueOf((Integer) showAllAppTable.getValueAt(row, 0)));
			}
		});
		appTableScrollPane.setViewportView(showAllAppTable);
	}

	public void showAllApp() {
		appTableModel.setRowCount(0);
		String searchFilter = txt_Search.getText();

		IFDBAppointment dbApp = new DBAppointment();
		ArrayList<Appointment> apps = dbApp.getAllAppointment(true);
		

		// Sorting
		for (Appointment app : apps) {
			if (((Integer) app.getID()).toString().toLowerCase().contains(searchFilter.toLowerCase())
						|| app.getDate().toString().toLowerCase().contains(searchFilter.toLowerCase())
						|| app.getTime().toString().toLowerCase().contains(searchFilter.toLowerCase())
						)
				try {
//					IFDBDoctor dbDoctor = new DBDoctor();
//					ArrayList<Doctor> doctors = dbDoctor.getAllDoctor(true);

					for (Doctor doctor : this.doctors) {
						if (doctor.getID() == app.getDoctor()) doc_name = doctor.getName() + " " + doctor.getsName();
						
					}
					
//					IFDBPerson dbPerson = new DBPerson();
//					ArrayList<Person> persons = dbPerson.getAllPerson(true);

					for (Person person : persons) {
						if (person.getID() == app.getPatient()) patient_name = person.getfName() + " " + person.getlName();
					}
					
//					IFDBMedicine dbMedicine = new DBMedicine();
//					ArrayList<Medicine> medicines = dbMedicine.getAllMedicines(true);
//
//					for (Medicine medicine : medicines) {
//						if (medicine.getID() == app.getMedicine()) medicine_name = medicine.getName();
//					}
					
					//Doctor doctors = new Doctor(app.getDoctor());
					
					
					appTableModel.addRow(new Object[] { app.getID(), app.getDate(), app.getTime(),
							patient_name, doc_name });
					//System.out.println("69 " + app.getDoctors().get(app.getDoctor()).getID());
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
				}
		}
	}

	public boolean showApp(int appId) {
		boolean success = true;
		Appointment app = null;
		try {
			if (appId != 0) {
				IFDBAppointment dbApp = new DBAppointment();
				app = dbApp.getAppointment(appId, true);
				app.setDate(app.getDate());
				app.setTime(app.getTime());
				app.setPatient(app.getPatient());
				
				app.setDoctor(app.getDoctor());
				app.setMedicine(app.getMedicine());
				app.setID(app.getID());
			}
		} catch (Exception e) {
			success = false;
			JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
			return success;
		}

		if (success) {
			textField_appId.setText(Integer.toString(app.getID()));
			textField_date.setText(String.valueOf(app.getDate()));
			textField_time.setText(String.valueOf(app.getTime()));
			//textField_patient.setText(Integer.toString(app.getPatient()));
			for (i = 0; i <= patient_array.length - 1; i++){
				if (patient_array[i][0].equals(Integer.toString(app.getPatient()))) textField_patient.setSelectedIndex(i+1);	
			}
			//textField_doctor.setText(Integer.toString(app.getDoctor()));
			for (i = 0; i <= doctors_array.length - 1; i++){
				if (doctors_array[i][0].equals(Integer.toString(app.getDoctor()))) textField_doctor.setSelectedIndex(i+1);
			}
			
			//textField_medicine.setText(Integer.toString(app.getMedicine()));
			for (i = 0; i <= medicine_array.length - 1; i++){
				if (medicine_array[i][0].equals(Integer.toString(app.getMedicine()))) textField_medicine.setSelectedIndex(i+1);
				else textField_medicine.setSelectedIndex(1);
				
			}
			
		}
		return success;
	}

	public void cleanTextField() {
		txt_Search.setText("");
		//textField_appId.setText("");
		textField_appId.setText("");
		textField_date.setText(new SimpleDateFormat("yyyy-MM-dd")
				.format(Calendar.getInstance().getTime()));
		textField_time.setText("");
		//textField_patient.setText("");
		textField_patient.setSelectedItem(null);
		//textField_doctor.setText("");
		textField_doctor.setSelectedItem(null);
		//textField_medicine.setText("");
		textField_medicine.setSelectedItem(null);
	}
}
