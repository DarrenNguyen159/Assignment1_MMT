package Chat;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ChatBox extends JFrame {

    public JPanel contentPane;
    private JTextField sendText;
    public static JTextArea chatflow;
    public static String name;
    public static String ip;
    public static String portKia;
    public static String portNay;
    private JButton btnClose;
    Socket socket = null;
    ServerSocket serverSocket = null;
    ChatBox cloneObj = this;
    public Client client = null;

    /**
     * Create the frame.
     */
//	public ChatBox(String name, String ip, String portKia, String portNay) {
    public ChatBox(Client cl) {
        this.client = cl;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(24, 23, 370, 179);
        contentPane.add(scrollPane);

        chatflow = new JTextArea();
        scrollPane.setViewportView(chatflow);

        sendText = new JTextField();
        sendText.setBounds(25, 213, 269, 20);
        contentPane.add(sendText);
        sendText.setColumns(10);

        JButton btnSend = new JButton("Send");
        btnSend.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                // gui cho user kia tin nhan
                try {
                    // ket noi den user kia
                    if (null == socket) {
                        socket = new Socket(ip, 9005);
                    }

                    String message = sendText.getText();
                    if (null != client) {
                        client.sendMess(message);
                    }

                    // hien thi tin nhan vua go
                    synchronized (chatflow) {
                        String currentText = chatflow.getText();
                        String newText = currentText + "\n";
                        newText += "Me: ";
                        // in doan tin nhan
                        newText += message;
                        chatflow.setText(newText);
                    }

                } catch (Exception ex) {

                }
            }
        });
        btnSend.setBounds(305, 212, 89, 23);
        contentPane.add(btnSend);

        this.setTitle("Chat with " + name);
        chatflow.setEditable(false);

        btnClose = new JButton("Close");
        btnClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                // ket thuc chat
                try {
                    // ket noi den user kia
                    socket = new Socket(ip, 9005);
                    OutputStream os = socket.getOutputStream();
                    OutputStreamWriter osw = new OutputStreamWriter(os);
                    BufferedWriter bw = new BufferedWriter(osw);

                    bw.write("CHAT CLOSE");
                    bw.write("\n");
                    bw.flush();

                    // dong cua so
                    closeChatBox(cloneObj);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        btnClose.setBounds(305, 0, 89, 23);
        contentPane.add(btnClose);
    }

    public static void WaitChat() {

        Thread wc = new Thread(new WaitChat(Integer.parseInt(portNay), name));
        wc.start();

    }

    public void closeChatBox(ChatBox cb) {
        cb.dispose();
        chatflow.setText("");
    }

    public void setVisible(boolean v) {
//        Thread.dumpStack();
        super.setVisible(v);
    }
}
