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
	            
	            String[] splited = line.split(" ");	            
	            
	            
	            //Nhan yeu cau gia nhap chat server
	            if (splited[0].equals("REQ")) {
	            	if (splited[1].equals("JOIN")) {
	            		System.out.println("This is a access request from user " + splited[3] + " with ip: " + splited[2]);
		            	String name = splited[3];
		            	String IP = splited[2];
		            	InetAddress ipAdrr = InetAddress.getByName(IP);
		            	ChatClient newClient = new ChatClient(name,ipAdrr);
		            	AddClient(newClient);
		            	
		            	//tra ve danh sach online
		            	OutputStream os = socket.getOutputStream();
						OutputStreamWriter osw = new OutputStreamWriter(os);
						BufferedWriter bw = new BufferedWriter(osw);
						//gui so luong user dang online
						//bw.write(ChatServer.clientList.size() + "\n");
						bw.write("REQ JOIN OK ");
						
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
	            
	            //Nhan yeu cau muon chat
	            if (splited[0].equals("REQ")) {
	            	if (splited[1].equals("CHAT")) {
	            		System.out.println("User with ip " + splited[3] + " wanna chat with user: " + splited[2]);
	            		//Thong bao cho B yeu cau chat cua A
	            		String ipAStr = splited[3];
	            		String name = splited[2];
	            		InetAddress ipb = null;
	            		//tim name trong ClientList
	            		for (ChatClient cc : ChatServer.clientList) {
	            			if (cc.getName().equals(name)) {
	            				ipb = cc.ipAddr();
	            				break;
	            			}
	            		}
	            		if (ipb == null) {
	            			//tu choi chat
	            			OutputStream os = socket.getOutputStream();
							OutputStreamWriter osw = new OutputStreamWriter(os);
							BufferedWriter bw = new BufferedWriter(osw);
							bw.write("RES CHAT DENIED");
							bw.write("\n");
							bw.flush();
	            		}
	            		
	            		
	            		//Tao ket noi den B
	            		try {
	    					Socket socket = new Socket(ipb, 9001);
	    					OutputStream os = socket.getOutputStream();
							OutputStreamWriter osw = new OutputStreamWriter(os);
							BufferedWriter bw = new BufferedWriter(osw);
							bw.write("INFORM REQ CHAT ");
							bw.write(ipAStr);
							bw.write("\n");
							bw.flush();
	            		}
	            		catch (Exception ex) {
	            			
	            		}
	            		
	            	}
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
