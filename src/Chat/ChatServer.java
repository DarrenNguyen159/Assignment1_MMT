package Chat;
import java.util.List;
import java.util.Vector;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
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
	            
	            String[] splitedIPName = line.split(" ");	            
	            
	            if (splitedIPName[0].equals("REQ")) {
		            System.out.println("This is a access request from user " + splitedIPName[2] + " with ip: " + splitedIPName[1]);
		            String name = splitedIPName[2];
		            String IP = splitedIPName[1];
		            InetAddress ipAdrr = InetAddress.getByName(IP);
		            ChatClient newClient = new ChatClient(name,ipAdrr);
		            AddClient(newClient);
		            
		            //tra ve danh sach online
		            OutputStream os = socket.getOutputStream();
					OutputStreamWriter osw = new OutputStreamWriter(os);
					BufferedWriter bw = new BufferedWriter(osw);
					//gui so luong user dang online
					//bw.write(ChatServer.clientList.size() + "\n");
					
					//tao 1 danh sach cac usernames
					for (ChatClient cc : ChatServer.clientList) {//duyet danh sach client
						//sendText += cc.getName() + " ";
						System.out.println(cc.getName());
						bw.write(cc.getName() + " ");
					}
					bw.write("\n");
					bw.flush();
		            
	            }
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
