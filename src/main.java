import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JButton;  
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.BoxLayout;
import java.awt.CardLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import java.awt.Component;
import javax.swing.border.TitledBorder;
import java.awt.Panel;
import java.awt.Button;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class main {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					main window = new main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public main() {
		initialize();
		ChatServer.RunServer();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		//Creating Frame
		frame = new JFrame("My App");
		frame.setBounds(200, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JLabel lblClientNameText = new JLabel("Client Name:");
		lblClientNameText.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblClientNameText = new GridBagConstraints();
		gbc_lblClientNameText.insets = new Insets(0, 0, 5, 5);
		gbc_lblClientNameText.gridx = 1;
		gbc_lblClientNameText.gridy = 2;
		frame.getContentPane().add(lblClientNameText, gbc_lblClientNameText);
		
		JLabel lblUserName = new JLabel("Duy");
		GridBagConstraints gbc_lblUserName = new GridBagConstraints();
		gbc_lblUserName.insets = new Insets(0, 0, 5, 0);
		gbc_lblUserName.gridx = 3;
		gbc_lblUserName.gridy = 2;
		frame.getContentPane().add(lblUserName, gbc_lblUserName);
		
		JButton btnGetIn = new JButton("Get in");
		btnGetIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Button is clicked");
				ChatClient.RunClient();
			}
		});
		GridBagConstraints gbc_btnGetIn = new GridBagConstraints();
		gbc_btnGetIn.insets = new Insets(0, 0, 0, 5);
		gbc_btnGetIn.gridx = 1;
		gbc_btnGetIn.gridy = 4;
		frame.getContentPane().add(btnGetIn, gbc_btnGetIn);
	}

}
