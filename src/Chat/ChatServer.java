package Chat;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;

public class ChatServer {
	private static Socket socket;
	public static int port = 9000;
	
	public void main(String[] args) {
		ListenToRequest();
	}
	
	//Methods
	private void ListenToRequest() {
		try {
			System.out.println("Server listen on port " + port);
			ServerSocket serverSocket = new ServerSocket(port);
			System.out.println("Server's IP :"+InetAddress.getLocalHost().getHostAddress());
			 
			while (true) {
				socket = serverSocket.accept();
				System.out.println("Server connect with ip: " + socket.getRemoteSocketAddress());
				 
				InputStream is = socket.getInputStream();
	            InputStreamReader isr = new InputStreamReader(is);
	            BufferedReader br = new BufferedReader(isr);
	            String userName = br.readLine();
	            System.out.println("Client's Name : " + userName);
			}
		}
		 
		catch (Exception e){
			e.printStackTrace();
		}
		 
		finally {
			try {
				socket.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
