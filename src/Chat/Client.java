package Chat;

import java.awt.EventQueue;
import java.net.*;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class Client {

    private JFrame frame;
    private static JTextField userNameBox;
    public static ChatBox cbox;
    public Client cloneObj = this;
    private JLabel lblServerIp;
    private JTextField serveripBox;
    public static String svip = "";

    /**
     * Launch the application.
     */

    public static void Show(String name) {
        if (cbox != null) {
            cbox.setVisible(true);
            cbox.setTitle("Chats with " + name);
        }
    }

    public void ShowChatBox(String name) {
        if (cbox != null) {
            cbox.setVisible(true);
            cbox.setTitle("Chats with " + name);
        }
//		System.out.println("COBX: " + cbox);
    }

    public static void main(String[] args) {
//		cbox.setTitle("Chat with ");
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Client window = new Client();
//                    window.frame.setVisible(true);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * Create the application.
     */
    public Client() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        cbox = new ChatBox(this);
        cbox.setVisible(false);

        frame = new JFrame("My Chat App");
        frame.setBounds(100, 100, 400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 0, 0, 0 };
        gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
        gridBagLayout.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
        gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
        frame.getContentPane().setLayout(gridBagLayout);

        JLabel lblClient = new JLabel("Client");
        GridBagConstraints gbc_lblClient = new GridBagConstraints();
        gbc_lblClient.anchor = GridBagConstraints.EAST;
        gbc_lblClient.insets = new Insets(0, 0, 5, 5);
        gbc_lblClient.gridx = 0;
        gbc_lblClient.gridy = 0;
        frame.getContentPane().add(lblClient, gbc_lblClient);

        userNameBox = new JTextField();
        GridBagConstraints gbc_userNameBox = new GridBagConstraints();
        gbc_userNameBox.insets = new Insets(0, 0, 5, 0);
        gbc_userNameBox.fill = GridBagConstraints.HORIZONTAL;
        gbc_userNameBox.gridx = 1;
        gbc_userNameBox.gridy = 0;
        frame.getContentPane().add(userNameBox, gbc_userNameBox);
        userNameBox.setColumns(10);

        JButton btnConnect = new JButton("Connect");
        btnConnect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                // Client yeu cau dang nhap bang ten, tao 1 ChatClient moi voi name = text go
                // vao userNameBox, ip = ip cua Client
                String inputName = userNameBox.getText();
                InetAddress ip = null;
                try {
                    byte[] ipAddr = InetAddress.getLocalHost().getAddress();
                    ip = InetAddress.getByAddress(ipAddr);
                } catch (Exception e) {

                } finally {
                    int port = 9000;
                    ChatClient newClient = new ChatClient(inputName, ip);
                    // Send Request
                    try {
                        svip = serveripBox.getText();
                        InetAddress address = InetAddress.getByName(svip);
                        Socket socket = new Socket(address, port);

                        System.out.println("Client wanna connect to Server: " + socket.getRemoteSocketAddress());
                        OutputStream os = socket.getOutputStream();
                        OutputStreamWriter osw = new OutputStreamWriter(os);
                        BufferedWriter bw = new BufferedWriter(osw);
                        bw.write("REQ JOIN ");
                        bw.write(userNameBox.getText() + "\n");
                        bw.flush();

                        // nhan ve danh sach online
                        InputStream is = socket.getInputStream();
                        InputStreamReader isr = new InputStreamReader(is);
                        BufferedReader br = new BufferedReader(isr);

                        System.out.println("Server return: ");
                        String line = br.readLine();
                        System.out.println(line + "\n");
                        String[] splited = line.split(" ");
                        if (splited[0].equals("REQ")) {
                            if (splited[1].equals("JOIN")) {
                                if (splited[2].equals("OK")) {
                                    // tach ten ra
                                    String[] names = Arrays.copyOfRange(splited, 3, splited.length);// bo REQ JOIN OK
                                    System.out.println(
                                            "Client.initialize().new ActionListener() {...}.actionPerformed()");

                                    OnlineList ol = new OnlineList(inputName, names, cloneObj);
                                    ol.setVisible(true);
                                    ol.setTitle("Online List");
                                } else if (splited[2].equals("DENIED")) {
                                    // bi tu choi
                                    userNameBox.setText("Denied: Invalid username or taken");
                                }
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }

            }
        });

        GridBagConstraints gbc_btnConnect = new GridBagConstraints();
        gbc_btnConnect.insets = new Insets(0, 0, 5, 0);
        gbc_btnConnect.gridx = 1;
        gbc_btnConnect.gridy = 1;
        frame.getContentPane().add(btnConnect, gbc_btnConnect);

        lblServerIp = new JLabel("Server IP");
        GridBagConstraints gbc_lblServerIp = new GridBagConstraints();
        gbc_lblServerIp.anchor = GridBagConstraints.EAST;
        gbc_lblServerIp.insets = new Insets(0, 0, 5, 5);
        gbc_lblServerIp.gridx = 0;
        gbc_lblServerIp.gridy = 2;
        frame.getContentPane().add(lblServerIp, gbc_lblServerIp);

        serveripBox = new JTextField();
        GridBagConstraints gbc_serveripBox = new GridBagConstraints();
        gbc_serveripBox.insets = new Insets(0, 0, 5, 0);
        gbc_serveripBox.fill = GridBagConstraints.HORIZONTAL;
        gbc_serveripBox.gridx = 1;
        gbc_serveripBox.gridy = 2;
        frame.getContentPane().add(serveripBox, gbc_serveripBox);
        serveripBox.setColumns(10);
        svip = serveripBox.getText();
        frame.setVisible(true);

        (new Thread(new ListenServerTask())).start();
    }

    private ServerSocket serverSoc = null;
    private Socket clientSoc = null;

    public void createChatSocket(String name, String ip) {
        try {
            if (null == ip) {
                serverSoc = new ServerSocket(9005);
            } else {
                clientSoc = new Socket(ip, 9005);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ReceiveTask recvTask = new ReceiveTask(name);
        Thread recvThread = new Thread(recvTask);
        recvThread.start();
    }

    public void sendMess(String mess) {
        if (null == clientSoc) {
            return;
        }
        try {
            OutputStream os = clientSoc.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
            bw.write("CHAT SEND ");
            bw.write(mess);
            bw.write("\n");
            bw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void sendClose() {
        if (null == clientSoc) {
            return;
        }
        try {
            OutputStream os = clientSoc.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
            bw.write("CHAT CLOSE");
            bw.write("\n");
            bw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class ListenServerTask implements Runnable {
        public void run() {
            // cho thong bao dc yeu cau chat tu Server
            try {
                // nhan thong bao tu ChatServer
                ServerSocket serverSocket = new ServerSocket(9001);
                System.out.println("Waiting on port 9001\n");
                Socket socketListener = serverSocket.accept();
                while (true) {
                    try {
                        InputStream is = socketListener.getInputStream();
                        InputStreamReader isr = new InputStreamReader(is);
                        BufferedReader br = new BufferedReader(isr);

                        System.out.println("ChatServer sent: ");
                        String line = br.readLine();
                        System.out.println(line + "\n");
                        String[] splited = line.split(" ");
                        if (splited[0].equals("INFORM")) {
                            if (splited[1].equals("REQ")) {
                                String ipAStr = splited[3];
                                String nameA = splited[4];

                                ChatInform ci = new ChatInform(nameA, ipAStr, cloneObj);
                                ci.setVisible(true);
                                ci.setTitle("Chat request from " + nameA + " with ip: " + ipAStr);
                            }
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public class ReceiveTask implements Runnable {
        String name = null;

        public ReceiveTask(String name) {
            this.name = name;
        }

        public void run() {

            try {
                if (null == clientSoc) {
                    clientSoc = serverSoc.accept();
                }
                cbox.name = name; // userKiaName;
                cbox.setTitle("Chat with " + name);
                cbox.setVisible(true);
                while (true) {
                    try {
                        InputStream is = clientSoc.getInputStream();
                        InputStreamReader isr = new InputStreamReader(is);
                        BufferedReader br = new BufferedReader(isr);

//                        System.out.println("ChatServer sent: ");
                        String line = br.readLine();
                        System.out.println(line + "\n");
                        String[] splited = line.split(" ");
                        
                        if (splited[1].equals("CLOSE")) {
                            //dong cua so
                            cbox.setVisible(false);
                        }
                        
                        synchronized (cbox.chatflow) {
                            String currentText = cbox.chatflow.getText();
                            String newText = currentText + "\n";
                            newText += name + ": ";
                            // in doan tin nhan
                            newText += line.substring(("CHAT SEND ").length());
                            cbox.chatflow.setText(newText);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
