package Chat;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
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

public class ChatRequestInform extends JDialog {

	ChatRequestInform cloneObj = this;
	
	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ChatRequestInform dialog = new ChatRequestInform("","","");
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ChatRequestInform(String UsernameA, String ipAStr, String UsernameB) {
		setBounds(100, 100, 395, 195);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblUser = new JLabel("User");
		lblUser.setBounds(10, 11, 46, 14);
		contentPanel.add(lblUser);
		
		JLabel name = new JLabel("unknown");
		name.setBounds(78, 11, 46, 14);
		contentPanel.add(name);
		
		JLabel lblWantToChat = new JLabel("want to chat with you");
		lblWantToChat.setBounds(167, 11, 150, 14);
		contentPanel.add(lblWantToChat);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						//dong y chat
						//tao ket noi den server
						//gui cho server chap nhan yeu cau chat
						try {
							
							
							//Ket noi den ChatServer
							InetAddress address = InetAddress.getByName("127.0.0.1");
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
//		            	            			ChatBox cb = new ChatBox(userKiaName, ipKia);
//		            	            			cb.setVisible(true);
//		            	            			cb.setTitle("Chat with " + userKiaName);
		            	            			
//		            	            			ma.ShowChatBox(userKiaName);
		            	            			//dong ket noi voi Server
		            	            			serverSocket.close();
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
						
						
						//dong dialog
//						closeDialog(cloneObj);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
		//hien thi ten
		name.setText(UsernameA);
	}
	
	public void closeDialog(ChatRequestInform ci) {
		ci.dispose();
	}
	
}
