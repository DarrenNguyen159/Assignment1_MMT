import java.io.DataInputStream;  
import java.io.DataOutputStream;  
import java.net.ServerSocket;  
import java.net.Socket; 

public class ChatServer {
	//This is the Over All Server That helps other computers connect to each others
	
	
	static int port = 95123;
	
	static ServerSocket severSocket;
	//static DataInputStream dataIStream;
	//static DataOutputStream dataOStream;
	static Socket theSocket;
	
	
	public static void RunServer() {
		System.out.println("ChatServer is listening");
		try {
			//Run Server
			severSocket = new ServerSocket(port);
			System.out.println("ChatServer is listening");
			while (true) {
				theSocket = severSocket.accept();
				System.out.println("ChatServer is connected to " + theSocket.getRemoteSocketAddress().toString());
			}
		}
	
		catch (Exception e) {
			//Handle Exception
		}
	}
	
}
