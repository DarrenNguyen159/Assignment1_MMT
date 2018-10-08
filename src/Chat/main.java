package Chat;

import java.awt.EventQueue;
import java.net.*;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.table.TableColumn;


public class main {

	private JFrame frame;
	private JTextField userNameBox;
	private JList list;
	private JTable table;

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
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("My Chat App");
		frame.setBounds(100, 100, 400, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JLabel lblClient = new JLabel("Client");
		GridBagConstraints gbc_lblClient = new GridBagConstraints();
		gbc_lblClient.anchor = GridBagConstraints.EAST;
		gbc_lblClient.insets = new Insets(0, 0, 5, 5);
		gbc_lblClient.gridx = 0;
		gbc_lblClient.gridy = 0;
		frame.getContentPane().add(lblClient, gbc_lblClient);
		
		userNameBox = new JTextField();
		GridBagConstraints gbc_userNameBox = new GridBagConstraints();
		gbc_userNameBox.insets = new Insets(0, 0, 5, 0);
		gbc_userNameBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_userNameBox.gridx = 1;
		gbc_userNameBox.gridy = 0;
		frame.getContentPane().add(userNameBox, gbc_userNameBox);
		userNameBox.setColumns(10);
		
		JButton btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				/*int port = 9000;
				try {
					byte[] ipAdr = new byte[] {127, 0, 0, 1};
					InetAddress address = InetAddress.getByAddress(ipAdr);
	            	Socket socket = new Socket(address, port);
	            	System.out.println("Client connect to: " + socket.getRemoteSocketAddress());
	            	
	            	 OutputStream os = socket.getOutputStream();
	                 OutputStreamWriter osw = new OutputStreamWriter(os);
	                 BufferedWriter bw = new BufferedWriter(osw);
	                 
	                 bw.write(userNameBox.getText() + "\n");
	                 System.out.println("Send to Server: " + userNameBox.getText());
	                 bw.flush();
				}
				catch (Exception ex){
					
				}*/
				
				//Client yeu cau dang nhap bang ten, tao 1 ChatClient moi voi name = text go vao userNameBox, ip = ip cua Client
				String inputName = userNameBox.getText();
				InetAddress ip = null;
				try {
					byte[] ipAddr = InetAddress.getLocalHost().getAddress();
					ip = InetAddress.getByAddress(ipAddr);
				}
				catch (Exception e) {
					
				}
				finally {
					String[] names = null;
					int port = 9000;
					ChatClient newClient = new ChatClient(inputName, ip);
					//Send Request
					try {
						//byte[] ipAdr = new byte[] {(byte) 192, (byte) 168,(byte) 1,(byte) 58};
						//InetAddress address = InetAddress.getByAddress(ipAdr);
						//Ket noi den ChatServer
						InetAddress address = InetAddress.getByName("127.0.0.1");
						Socket socket = new Socket(address, port);
						
						//get client ip address
						String clientIP = InetAddress.getLocalHost().getHostAddress();
						
	            		System.out.println("Client wanna connect to Server: " + socket.getRemoteSocketAddress());
						OutputStream os = socket.getOutputStream();
	                	OutputStreamWriter osw = new OutputStreamWriter(os);
	                	BufferedWriter bw = new BufferedWriter(osw);
	                	bw.write("REQ JOIN ");
	                	bw.write(clientIP + " ");
	                	bw.write(userNameBox.getText() + "\n");
		                bw.flush();
		                
		                //nhan ve danh sach online
		                InputStream is = socket.getInputStream();
			            InputStreamReader isr = new InputStreamReader(is);
			            BufferedReader br = new BufferedReader(isr);
			            
			            System.out.println("Server return: ");
			            String line = br.readLine();
			            System.out.println(line + "\n");
			            //line la danh sach online
			            //tach ten ra
			            names = line.split(" ");
			            names = Arrays.copyOfRange(names, 3, names.length);//bo REQ JOIN OK
			            
					}
					catch (Exception ex){
						
					}
					
					//Mo cua so OnlineList
					OnlineList ol = new OnlineList(inputName, names);
					ol.setVisible(true);
					ol.setTitle("Online List");
					
					
				}
				
			}
		});
		
		
		GridBagConstraints gbc_btnConnect = new GridBagConstraints();
		gbc_btnConnect.insets = new Insets(0, 0, 5, 0);
		gbc_btnConnect.gridx = 1;
		gbc_btnConnect.gridy = 1;
		frame.getContentPane().add(btnConnect, gbc_btnConnect);
		
	}

	
}
