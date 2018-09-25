package Chat;
import java.net.InetAddress;
import java.util.List;

public class ChatClient {
	private InetAddress ipAddr;	//Dia chi IP chua Client
	private String name;	//Ten dang nhap cua Client
	private List<Integer> Ports;	//Cac cong cua Client
	
	///Methods
	//Constructor
	ChatClient(String userName, InetAddress IP) {
		this.name = userName;
		this.ipAddr = IP;
		//Client moi dang nhap nen ko co port nao dc su dung
	}
	
	//get methods
	public String getName() {
		return name;
	}
	public InetAddress ipAddr() {
		return ipAddr;
	}
	
	
	
	
}
