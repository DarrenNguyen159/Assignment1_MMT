package Chat;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class WaitChat implements Runnable {
	int portNay;
	public WaitChat(int port) {
		portNay = port;
	}
	
	@Override
	public void run() {
		System.out.println("Waiting chat on port " + portNay);
		try {
			ServerSocket serverSocket = new ServerSocket(portNay);
			Socket socketListener = null;
			
			while (true) {
        		try {
        			socketListener = serverSocket.accept();
        			InputStream is = socketListener.getInputStream();
	            	InputStreamReader isr = new InputStreamReader(is);
	            	BufferedReader br = new BufferedReader(isr);
	            
	            	System.out.println("ClientKia sent: ");
	            	String line = br.readLine();
	            	System.out.println(line + "\n");
	            	String[] splited = line.split(" ");
	            	if (splited[0].equals("CHAT")) {
	            		//hien tin nhan len o chat
	            		String currentText = ChatBox.chatflow.getText();
	            		String newText = currentText + "\n";
	            		//in doan tin nhan
	            		newText += line.substring(5);
	            		ChatBox.chatflow.setText(newText);
	            	}
	            	
        		}
        		catch (Exception ex) {
        		
        		}
        	}
		
		}
		catch (Exception ex) {
			
		}
	}
}
