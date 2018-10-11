package Chat;

import java.net.InetAddress;
import java.util.List;
import java.util.Vector;

public class ChatClient {
    private InetAddress ipAddr; // Dia chi IP chua Client
    private String name; // Ten dang nhap cua Client
    private boolean[] Ports; // Cac cong cua Client

    /// Methods
    // Constructor
    ChatClient(String userName, InetAddress IP) {
        this.name = userName;
        this.ipAddr = IP;
        // Client moi dang nhap nen ko co port nao dc su dung
        Ports = new boolean[] { false, false, false };
    }

    // get methods
    public String getName() {
        return name;
    }

    public InetAddress ipAddr() {
        return ipAddr;
    }

    // method tra ve port con trong
    public int freePort() {
        int freePort = 9002;
        for (int i = 0; i < 3; i++) {
            if (Ports[i] == false) {
                freePort = 9002 + i;
                Ports[i] = true;
            }
        }
        return freePort;
    }

}
