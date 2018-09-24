import java.io.DataInputStream;  
import java.io.DataOutputStream;  
import java.net.ServerSocket;  
import java.net.Socket;


public class ChatClient {
	static int port = 95123;
	
	public static void RunClient() {
		try {
			System.out.println("Connecting ...");
			Socket theSocket;
			theSocket = new Socket("localhost", port);
			while (true) {
				System.out.println("Client try to connect: " + theSocket.getRemoteSocketAddress().toString());
				theSocket.close();
			}
		}
		catch (Exception e){
			
		}
		finally {
		}
	}
}
