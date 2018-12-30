/**
 * @author Jelizaveta Kuznecova
 */
package GUILayer;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

import ControlLayer.PersonCtr;
import ModelLayer.Person;

public class LoginUI extends JPanel {

	private static final long serialVersionUID = 1L;
	private static JTextField loginNameField;
	private static JPasswordField loginPasswordField;
	private static int loggedInId = 0;
	private static int loggedInPosition;
	private static String loggedInName = "";

	private JButton loginBtnLogin;

	private JPanel login;

	private PersonCtr personCtr;

	public LoginUI() {
		personCtr = new PersonCtr();
		initialize();
	}

	public void initialize() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		Component verticalGlue = Box.createVerticalGlue();
		add(verticalGlue);

		JPanel panel = new JPanel();
		add(panel);

		login = new JPanel();
		panel.add(login);
		login.setLayout(new BoxLayout(login, BoxLayout.Y_AXIS));

		JPanel loginContainer = new JPanel();
		login.add(loginContainer);
		loginContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		loginContainer.setBorder(BorderFactory.createTitledBorder("Login"));

		JPanel loginInfo = new JPanel();
		loginInfo.setLayout(new BoxLayout(loginInfo, BoxLayout.X_AXIS));
		loginContainer.add(loginInfo);

		JPanel loginLeft = new JPanel();
		loginInfo.add(loginLeft);
		loginLeft.setLayout(new GridLayout(0, 1, 0, 0));

		JPanel loginNameLabelPanel = new JPanel();
		loginNameLabelPanel.setLayout(new BoxLayout(loginNameLabelPanel, BoxLayout.X_AXIS));
		loginLeft.add(loginNameLabelPanel);

		JLabel loginNameLabel = new JLabel("Name");
		loginNameLabelPanel.add(loginNameLabel);
		loginNameLabel.setHorizontalAlignment(SwingConstants.CENTER);

		JPanel loginPasswordLabelPanel = new JPanel();
		loginPasswordLabelPanel.setLayout(new BoxLayout(loginPasswordLabelPanel, BoxLayout.X_AXIS));
		loginLeft.add(loginPasswordLabelPanel);

		JLabel loginPasswordLabel = new JLabel("Password");
		loginPasswordLabelPanel.add(loginPasswordLabel);
		loginPasswordLabel.setHorizontalAlignment(SwingConstants.CENTER);

		JPanel loginRight = new JPanel();
		loginRight.setLayout(new BoxLayout(loginRight, BoxLayout.Y_AXIS));
		loginInfo.add(loginRight);

		JPanel loginNameFieldPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		loginRight.add(loginNameFieldPanel);

		loginNameField = new JTextField();
		loginNameField.setColumns(25);
		loginNameFieldPanel.add(loginNameField);
		loginNameField.setHorizontalAlignment(SwingConstants.LEFT);

		JPanel loginPasswordFieldPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		loginRight.add(loginPasswordFieldPanel);

		loginPasswordField = new JPasswordField();
		loginPasswordField.setColumns(25);
		loginPasswordFieldPanel.add(loginPasswordField);

		JPanel loginBottomMenu = new JPanel(); // Bottom menu for login button
		login.add(loginBottomMenu);
		loginBottomMenu.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		loginBtnLogin = new JButton("Login");
		loginBottomMenu.add(loginBtnLogin);

		getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke("ENTER"), "loginEnter");

		getActionMap().put("loginEnter", new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				loginBtnLogin.doClick();
			}

		});

		JOptionPane.showMessageDialog(null, "Note: you can always use log in name \"test\" with password \"test\"",
				"Login", JOptionPane.INFORMATION_MESSAGE);

	}

	public void login() {
		String name = loginNameField.getText();
		// Checks if the user name or password are nor empty
		if (name.isEmpty() || loginPasswordField.getPassword().length == 0) { 
			JOptionPane.showMessageDialog(null, "Fields are empty", "Error", JOptionPane.ERROR_MESSAGE);
		} else {
			try {
				Person person = personCtr.loginGUI(name);
				loggedInId = person.getID(); // logged in id
				loggedInName = person.getfName() + " " + person.getlName(); // logged in name and surname
				String text = "You are logged in";
				JOptionPane.showMessageDialog(null, text, "Login", JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Bad login credentials", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public static int getLoggedInId() {
		return loggedInId;
	}

	public static int getLoggedInPosition() {
		return loggedInPosition;
	}

	public static String getLoggedInName() {
		return loggedInName;
	}

	public JButton getLoginBtnLogin() {
		return loginBtnLogin;
	}

	public static void logout() {
		loginNameField.setText("");
		loginPasswordField.setText("");
		loggedInId = 0;
		loggedInPosition = 0;
		loggedInName = "";
	}
}
