package Chat;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
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
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.event.ActionEvent;

public class ChatInform extends JFrame {
	private JPanel contentPane;
	ChatInform cloneObj = this;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatInform frame = new ChatInform("unknown","","none");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ChatInform(String UsernameA, String ipAStr, String UsernameB) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 265, 205);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblUser = new JLabel("User ");
		GridBagConstraints gbc_lblUser = new GridBagConstraints();
		gbc_lblUser.insets = new Insets(0, 0, 5, 5);
		gbc_lblUser.gridx = 0;
		gbc_lblUser.gridy = 0;
		contentPane.add(lblUser, gbc_lblUser);
		
		JLabel name = new JLabel("unknown");
		GridBagConstraints gbc_name = new GridBagConstraints();
		gbc_name.insets = new Insets(0, 0, 5, 5);
		gbc_name.gridx = 1;
		gbc_name.gridy = 0;
		contentPane.add(name, gbc_name);
		
		JLabel lblWantToChat = new JLabel("want to chat with you");
		GridBagConstraints gbc_lblWantToChat = new GridBagConstraints();
		gbc_lblWantToChat.insets = new Insets(0, 0, 5, 0);
		gbc_lblWantToChat.gridx = 2;
		gbc_lblWantToChat.gridy = 0;
		contentPane.add(lblWantToChat, gbc_lblWantToChat);
		
		JButton btnAccecpt = new JButton("Accept");
		btnAccecpt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//dong y chat
				//tao ket noi den server
				//gui cho server chap nhan yeu cau chat
				try {
					
					
					//Ket noi den ChatServer
					InetAddress address = InetAddress.getByName(main.svip);
					Socket socket = new Socket(address, 9000);
					OutputStream os = socket.getOutputStream();
                	OutputStreamWriter osw = new OutputStreamWriter(os);
                	BufferedWriter bw = new BufferedWriter(osw);
                	
                	bw.write("RES CHAT OK ");
                	//ip cua client gui yeu cau(A)
                	String ipBStr = InetAddress.getLocalHost().getHostAddress();
                	bw.write(ipBStr);
                	bw.write(" ");
                	bw.write(UsernameB);
                	bw.write(" ");
                	bw.write(ipAStr);
                	bw.write(" ");
                	bw.write(UsernameA);
                	bw.write("\n");
                	bw.flush();
		            
                	//nhan thong bao tu ChatServer
                	ServerSocket serverSocket = new ServerSocket(9006);
                	System.out.println("Waiting on port 9006\n");
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
//            	            			ChatBox cb = new ChatBox(userKiaName, ipKia);
//            	            			cb.setVisible(true);
//            	            			cb.setTitle("Chat with " + userKiaName);
            	            			
            	            			main.cbox.setVisible(true);
            	            			main.cbox.name = userKiaName;
            	            			main.cbox.setTitle("Chat with " + main.cbox.name);
            	            			main.cbox.ip = ipKia;
            	            			main.cbox.portKia = portKia;
            	            			main.cbox.portNay = portNay;
            	            			main.cbox.WaitChat();
            	            			
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
				
				//dong server
				closeDialog(cloneObj);
			
			}
		});
		GridBagConstraints gbc_btnAccecpt = new GridBagConstraints();
		gbc_btnAccecpt.insets = new Insets(0, 0, 0, 5);
		gbc_btnAccecpt.gridx = 1;
		gbc_btnAccecpt.gridy = 2;
		contentPane.add(btnAccecpt, gbc_btnAccecpt);
		
		JButton btnDecline = new JButton("Decline");
		GridBagConstraints gbc_btnDecline = new GridBagConstraints();
		gbc_btnDecline.gridx = 2;
		gbc_btnDecline.gridy = 2;
		contentPane.add(btnDecline, gbc_btnDecline);
		
		//hien thi ten
		name.setText(UsernameA);
	}
	
	public void closeDialog(ChatInform ci) {
		ci.dispose();
	}

}
