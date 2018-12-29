package GUILayer;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Cursor;
import java.awt.FlowLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainMenuSimpleWithLogIn {
	private JFrame frame;
	private JPanel mainPanel;
	private JPanel mainContainer;
	private JPanel mainMenuPanel;
	private JPanel personPanel;
	private JPanel appointmentPanel;
	private JPanel buttonPanel;
	private LoginUI loginUI;

	public static void main(String[] args) {
		MainMenuSimpleWithLogIn window = new MainMenuSimpleWithLogIn();
		window.frame.setVisible(true);
	}

	public MainMenuSimpleWithLogIn() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame("Clinic System"); // Name of frame
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				close();
			}
		});
		frame.setSize(600, 320); // Login frame size
		frame.setLocationRelativeTo(null); // Does not depend on previous form
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // If close - exit
		frame.getContentPane().setLayout(new CardLayout(0, 0));

		loginUI = new LoginUI();
		frame.getContentPane().add(loginUI, "loginUI");

		loginUI.getLoginBtnLogin().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loginUI.login();
				if (LoginUI.getLoggedInId() != 0) {
					loginInit();
				}
			}
		});
	}

	public void loginInit() {
		mainPanel = new JPanel();
		frame.setSize(1200, 900); // Main panel frame size
		frame.setLocationRelativeTo(null); // Does not depend on previous form
		frame.getContentPane().add(mainPanel, "mainPanel");
		mainPanel.setLayout(new BorderLayout());
		mainMenuPanel = new JPanel();
		mainPanel.add(mainMenuPanel, BorderLayout.NORTH);
		mainMenuPanel.setLayout(new BorderLayout());

		buttonPanel = new JPanel();
		mainMenuPanel.add(buttonPanel, BorderLayout.WEST);

		mainContainer = new JPanel();
		mainContainer.setLayout(new CardLayout());
		mainPanel.add(mainContainer, BorderLayout.CENTER);

		JButton personButton = new JButton("Person"); // Person button on form
		buttonPanel.add(personButton);

		personButton.addActionListener(new ActionListener() { // Person button action
			public void actionPerformed(ActionEvent e) {
				try {
					frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
					personPanel = new PersonGUI();
					mainContainer.add(personPanel, "personPanel");
					CardLayout cl = (CardLayout) (mainContainer.getLayout());
					cl.show(mainContainer, "personPanel");
					mainContainer.updateUI();
				} finally {
					frame.setCursor(Cursor.getDefaultCursor());
				}
			}
		});

		JButton appointmentButton = new JButton("Appointment"); // Appointment button on form
		buttonPanel.add(appointmentButton);

		appointmentButton.addActionListener(new ActionListener() { // Appointment button action
			public void actionPerformed(ActionEvent e) {
				try {
					frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
					appointmentPanel = new AppointmentGUI();
					mainContainer.add(appointmentPanel, "appointmentPanel");
					CardLayout cl = (CardLayout) (mainContainer.getLayout());
					cl.show(mainContainer, "appointmentPanel");
					mainContainer.updateUI();
				} finally {
					frame.setCursor(Cursor.getDefaultCursor());
				}
			}
		});
		
		JLabel lbllogIn = new JLabel("Log In Name: " + LoginUI.getLoggedInName()); // shows logged in name
		JButton logoutButton = new JButton("Log Out"); // logout button

		JPanel logPanel = new JPanel();
		logPanel.setLayout(new FlowLayout());
		logPanel.add(lbllogIn);
		logPanel.add(logoutButton);
		mainMenuPanel.add(logPanel, BorderLayout.EAST);

		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().remove(mainPanel);
				LoginUI.logout();
				CardLayout cl = (CardLayout) frame.getContentPane().getLayout();
				cl.show(frame.getContentPane(), "loginUI");
			}
		});

		CardLayout cl = (CardLayout) frame.getContentPane().getLayout();
		cl.show(frame.getContentPane(), "mainPanel");
	}

	// Exit on close
	public void close() {
		System.exit(1);
	}
}
