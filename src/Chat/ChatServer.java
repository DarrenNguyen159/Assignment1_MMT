package Chat;
import java.util.List;
import java.util.Vector;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;

public class ChatServer {
	private static Socket socket;
	public final static int PORT = 9000;
	public static List<ChatClient> clientList = new Vector<ChatClient>();;
	
	public static void main(String[] args) {
		ListenToRequest();
	}
	

	
	///Methods
	private static void ListenToRequest() {
		try {
			System.out.println("Server listen on port " + PORT);
			ServerSocket serverSocket = new ServerSocket(PORT);
			System.out.println("Server's IP :"+InetAddress.getLocalHost().getHostAddress());
			 
			while (true) {
				socket = serverSocket.accept();
				System.out.println("Server connect with ip: " + socket.getRemoteSocketAddress());
				 
				InputStream is = socket.getInputStream();
	            InputStreamReader isr = new InputStreamReader(is);
	            BufferedReader br = new BufferedReader(isr);
	            System.out.println("Client sent: ");
	            String line = br.readLine();
	            System.out.println(line + "\n");
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
	
	//Them vao danh sach Client
	public static void AddClient(ChatClient client) {
		clientList.add(client);
	}
	
	//
	
	
}
