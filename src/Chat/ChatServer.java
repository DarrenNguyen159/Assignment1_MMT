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
		            	
		            	//kiem tra xem co trung ten ko?
		            	boolean ok = true;
		            	for (ChatClient cc: clientList) {
		            		if (cc.getName().equals(name)) {//trung ten
		            			ok = false;
		            			break;
		            		}
		            	}
		            	
		            	if (ok) {
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
		            	else {
		            		//tu choi gia nhap
		            		OutputStream os = socket.getOutputStream();
							OutputStreamWriter osw = new OutputStreamWriter(os);
							BufferedWriter bw = new BufferedWriter(osw);
							
							bw.write("REQ JOIN DENIED\n");
							bw.flush();
		            	}
	            	}
	            }
	            
	            //Nhan yeu cau muon chat
	            if (splited[0].equals("REQ")) {
	            	if (splited[1].equals("CHAT")) {
	            		System.out.println("User " + splited[4] + " with ip " + splited[3] + " wanna chat with user: " + splited[2]);
	            		//Thong bao cho B yeu cau chat cua A
	            		String ipAStr = splited[3];
	            		String name = splited[2];
	            		String othername = splited[4];
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
							bw.write(" ");
							bw.write(othername);
							bw.write("\n");
							bw.flush();
	            		}
	            		catch (Exception ex) {
	            			
	            		}
	            		
	            	}
	            }
	            
	            
	            //nhan tra loi chap nhan chat
	            if (splited[0].equals("RES")) {
	            	if (splited[2].equals("OK")) {//chap nhan
	            		String ipAStr = splited[5];
	            		String ipBStr = splited[3];
	            		//gui thong bao cho A
	            		//Tao ket noi den A
	            		try {
	            			InetAddress ipA = InetAddress.getByName(ipAStr);
	            			String nameB = splited[4];
	            			String portB = "9003";
	            			String portA = "9002";
	    					Socket socket = new Socket(ipA, 9005);
	    					OutputStream os = socket.getOutputStream();
							OutputStreamWriter osw = new OutputStreamWriter(os);
							BufferedWriter bw = new BufferedWriter(osw);
							bw.write("INFORM RES CHAT OK ");
							bw.write(ipBStr);
							bw.write(" ");
							bw.write(nameB);
							bw.write(" ");
							bw.write(portB);
							bw.write(" ");
							bw.write(portA);
							bw.write("\n");
							bw.flush();
	            		}
	            		catch (Exception ex) {
	            			
	            		}
	            		
	            		//gui thong bao cho B
	            		//Tao ket noi den B
	            		try {
	            			InetAddress ipB = InetAddress.getByName(ipBStr);
	            			String nameA = splited[6];
	            			String portA = "9002";
	            			String portB = "9003";
	    					Socket socket = new Socket(ipB, 9006);
	    					OutputStream os = socket.getOutputStream();
							OutputStreamWriter osw = new OutputStreamWriter(os);
							BufferedWriter bw = new BufferedWriter(osw);
							bw.write("INFORM RES CHAT OK ");
							bw.write(ipAStr);
							bw.write(" ");
							bw.write(nameA);
							bw.write(" ");
							bw.write(portA);
							bw.write(" ");
							bw.write(portB);
							bw.write("\n");
							bw.flush();
	            		}
	            		catch (Exception ex) {
	            			
	            		}
	            	}
	            }
	            
	            //nhan yeu refresh onlinelist
	            if (splited[0].equals("REQ")) {
	            	if (splited[1].equals("REFRESH")) {
	            		System.out.println("Refresh request from " + socket.getRemoteSocketAddress());
		            	
		            	//tra ve danh sach online
		            	OutputStream os = socket.getOutputStream();
						OutputStreamWriter osw = new OutputStreamWriter(os);
						BufferedWriter bw = new BufferedWriter(osw);

						//bw.write(ChatServer.clientList.size() + "\n");
						bw.write("RES REFRESH ");
						
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
