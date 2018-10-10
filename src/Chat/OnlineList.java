package Chat;

import java.awt.BorderLayout;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.event.ActionEvent;

public class OnlineList extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private String[] OnlineNames;
	int port = 9000;
	main m;
	
	
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					OnlineList frame = new OnlineList("unknown", null);
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public OnlineList(String name, String[] names, main ma) {
		System.out.println("MA: " + ma);
		this.m = ma;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblUsername = new JLabel("Username:");
		GridBagConstraints gbc_lblUsername = new GridBagConstraints();
		gbc_lblUsername.anchor = GridBagConstraints.WEST;
		gbc_lblUsername.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsername.gridx = 0;
		gbc_lblUsername.gridy = 0;
		contentPane.add(lblUsername, gbc_lblUsername);
		
		JLabel clientName = new JLabel(name);
		GridBagConstraints gbc_clientName = new GridBagConstraints();
		gbc_clientName.anchor = GridBagConstraints.WEST;
		gbc_clientName.insets = new Insets(0, 0, 5, 0);
		gbc_clientName.gridx = 1;
		gbc_clientName.gridy = 0;
		contentPane.add(clientName, gbc_clientName);
		
		JButton connectBTN = new JButton("Connect");
		connectBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				/* Su kien khi bam nut Connect 			*/
				int rowIndex = table.getSelectedRow();
				int colIndex = table.getSelectedColumn();
				Object selected = table.getValueAt(rowIndex, colIndex);
				String selectedNameB = selected.toString();
				//DEBUG:	System.out.println(selectedName);
				
				//gui cho server name cua user muon chat
				try {
					InetAddress ip = null;
					byte[] ipAddr = InetAddress.getLocalHost().getAddress();
					ip = InetAddress.getByAddress(ipAddr);
					
					//Ket noi den ChatServer
					InetAddress address = InetAddress.getByName(main.svip);
					Socket socket = new Socket(address, port);
					OutputStream os = socket.getOutputStream();
                	OutputStreamWriter osw = new OutputStreamWriter(os);
                	BufferedWriter bw = new BufferedWriter(osw);
                	
                	bw.write("REQ CHAT ");
                	//ten cua client muon chat(B)
                	bw.write(selectedNameB + " ");
                	//ip cua may A
                	bw.write(ip.getHostAddress());
                	bw.write(" ");
                	bw.write(name);
                	bw.write("\n");
                	bw.flush();
		            
                	//doi phan hoi tu ChatServer
            		try {
            			//nhan thong bao tu ChatServer
                    	ServerSocket serverSocket = new ServerSocket(9005);
                    	System.out.println("Waiting on port 9005\n");
                    	Socket socketListener = null;
                        while (true) {
                        	try {
                        		socketListener = serverSocket.accept();
                        		InputStream is = socketListener.getInputStream();
                	            InputStreamReader isr = new InputStreamReader(is);
                	            BufferedReader br = new BufferedReader(isr);
                	            
                	            System.out.println("ChatServer sent: ");
                	            String line = br.readLine();
                	            System.out.println(line + "\n");
                	            String[] splited = line.split(" ");
                	            if (splited[0].equals("INFORM")) {
                	            	if (splited[1].equals("RES")) {
                	            		if (splited[3].equals("OK")) {
                	            			//System.out.println("Received " + splited[4]);
                	            			//tao cua so chat
                	            			String userKiaName = splited[5];
                	            			String ipKia = splited[4];
                	            			String portKia = splited[6];
                	            			String portNay = splited[7];
                	    					
//                	            			ma.createChatBox(userKiaName, ipKia);
//                	            			ma.ShowChatBox(userKiaName);
                	            			main.cbox.setVisible(true);
                	            			main.cbox.name = userKiaName;
                	            			main.cbox.setTitle("Chat with " + main.cbox.name);
                	            			main.cbox.ip = ipKia;
                	            			main.cbox.portKia = portKia;
                	            			main.cbox.portNay = portNay;
                	            			main.cbox.WaitChat();
                	            			
//                	            			main.Show(userKiaName);
                	            			
                	            			
//                	            			ChatBox cb = new ChatBox(userKiaName,ipKia);
//                	            			System.out.println("JFRAME bg: " + cb.contentPane);
//                	            			Object obj = cb.contentPane.getComponent(0);
//                	            			cb.setVisible(true);
//                	            			m.ShowChatBox();
//                	    					cb.setTitle("Chat with " + userKiaName);
                	            			//main.createChatBox(userKiaName, ipKia);
                	            			
                	            			//dong ket noi voi Server
                	            			serverSocket.close();
                	            			break;
                	            		}
                	            		
                	            	}
                	            }
                	            
                        	}
                        	catch (Exception ex) {
                        		
                        	}
                        }
            			}
            			catch (Exception ex) {
            				
            			}
					
				}
				catch (Exception ex) {
					
				}
			}
		});
		GridBagConstraints gbc_connectBTN = new GridBagConstraints();
		gbc_connectBTN.anchor = GridBagConstraints.NORTH;
		gbc_connectBTN.insets = new Insets(0, 0, 5, 5);
		gbc_connectBTN.gridx = 0;
		gbc_connectBTN.gridy = 1;
		contentPane.add(connectBTN, gbc_connectBTN);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.anchor = GridBagConstraints.WEST;
		gbc_panel.gridheight = 2;
		gbc_panel.gridwidth = 2;
		gbc_panel.fill = GridBagConstraints.VERTICAL;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 2;
		contentPane.add(panel, gbc_panel);

		table = new JTable();
		panel.add(table);
		OnlineNames = names;
		updateTable();
	}
	
	private Object[] loadColumnName() {
		return new Object[] {"UserName"};
	}
	private Object[][] loadData() {
		Object[][] data = new Object[][] {{"None"},{"None"},{"None"},{"None"},{"None"},{"None"},{"None"},{"None"},{"None"},{"None"}};
		for (int i = 0; i < OnlineNames.length; i++) {
			data[i][0] = OnlineNames[i];
		}
		return data;
	}
	
	private void updateTable() {
		
		TableModel model = new DefaultTableModel(loadData(), loadColumnName());
		table.setModel(model);
	}
	
}
