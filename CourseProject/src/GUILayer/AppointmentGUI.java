package GUILayer;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
//import java.util.Date;

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

import ControlLayer.AppointmentCtr;
import DBLayer.DBAppointment;
import DBLayer.IFDBAppointment;
import ModelLayer.Appointment;

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
	private JTextField textField_patient;
	private JTextField textField_doctor;
	private JTextField textField_medicine;

	private JButton btnSave;
	private JButton btnCreate;
	private JButton btnCancel;
	private JButton btnDelete;

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

		// Person - Doctor labels
		appInfoPanel = new JPanel();
		JLabel lblappId = new JLabel("Appointment Id: ");
		JLabel lbldate = new JLabel("Date: ");
		JLabel lbltime = new JLabel("Time: ");
		JLabel lblpatient = new JLabel("Patient: ");
		JLabel lbldoctor = new JLabel("Doctor: ");
		JLabel lblmedicine = new JLabel("Medicine: ");

		// Person - Doctor fields
		textField_appId = new JTextField();
		textField_appId.setEditable(false); // doctorId not editable
		
		textField_date = new JTextField();
		textField_date.setText(new SimpleDateFormat("yyyy-MM-dd")
				.format(Calendar.getInstance().getTime()));
		textField_time = new JTextField();
		textField_patient = new JTextField();
		textField_doctor = new JTextField();
		textField_medicine = new JTextField();

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
						.addComponent(textField_medicine)));
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
						.addComponent(textField_medicine)));

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

		addAppTable();
		showAllApp();

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
						appCtr.insertApp(
								java.sql.Date.valueOf(textField_date.getText()), 
								java.sql.Time.valueOf(time_format),
								Integer.parseInt(textField_patient.getText()), 
								Integer.parseInt(textField_doctor.getText()), 
								Integer.parseInt(textField_medicine.getText()));
						JOptionPane.showMessageDialog(null, "The appointment is created", "Create appointment",
								JOptionPane.INFORMATION_MESSAGE);
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
						appCtr.updateAppointment(Integer.parseInt(
								textField_appId.getText()), 
								java.sql.Date.valueOf(textField_date.getText()), 
								java.sql.Time.valueOf(textField_time.getText()), 
								Integer.parseInt(textField_patient.getText()), 
								Integer.parseInt(textField_doctor.getText()),
								Integer.parseInt(textField_medicine.getText()));
						JOptionPane.showMessageDialog(null, "The appointment info is saved", "Update appointment",
								JOptionPane.INFORMATION_MESSAGE);
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

	// Person - Doctor grid
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
		appTableModel.addColumn("Medicine");

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
					appTableModel.addRow(new Object[] { app.getID(), app.getDate(), app.getTime(),
							app.getPatient(), app.getDoctor(), app.getMedicine() });
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
			textField_patient.setText(Integer.toString(app.getPatient()));
			textField_doctor.setText(Integer.toString(app.getDoctor()));
			textField_medicine.setText(Integer.toString(app.getMedicine()));
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
		textField_patient.setText("");
		textField_doctor.setText("");
		textField_medicine.setText("");
	}
}
