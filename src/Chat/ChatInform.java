package Chat;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
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
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.event.ActionEvent;

public class ChatInform extends JFrame {
    private JPanel contentPane;
    private ChatInform cloneObj = this;
//	public main ma;

    /**
     * Create the frame.
     */
    public ChatInform(String UsernameA, String ipAStr, Client client) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 265, 205);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        GridBagLayout gbl_contentPane = new GridBagLayout();
        gbl_contentPane.columnWidths = new int[] { 0, 0, 0, 0 };
        gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 0 };
        gbl_contentPane.columnWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
        gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
        contentPane.setLayout(gbl_contentPane);

        JLabel lblUser = new JLabel("User ");
        GridBagConstraints gbc_lblUser = new GridBagConstraints();
        gbc_lblUser.insets = new Insets(0, 0, 5, 5);
        gbc_lblUser.gridx = 0;
        gbc_lblUser.gridy = 0;
        contentPane.add(lblUser, gbc_lblUser);

        JLabel name = new JLabel("unknown");
        GridBagConstraints gbc_name = new GridBagConstraints();
        gbc_name.insets = new Insets(0, 0, 5, 5);
        gbc_name.gridx = 1;
        gbc_name.gridy = 0;
        contentPane.add(name, gbc_name);

        JLabel lblWantToChat = new JLabel("want to chat with you");
        GridBagConstraints gbc_lblWantToChat = new GridBagConstraints();
        gbc_lblWantToChat.insets = new Insets(0, 0, 5, 0);
        gbc_lblWantToChat.gridx = 2;
        gbc_lblWantToChat.gridy = 0;
        contentPane.add(lblWantToChat, gbc_lblWantToChat);

        JButton btnAccecpt = new JButton("Accept");
        btnAccecpt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                // dong y chat
                // tao ket noi den server
                // gui cho server chap nhan yeu cau chat
                try {
                    InetAddress.getLocalHost().getHostAddress();
                    Client.cbox.setVisible(true);
                    Client.cbox.name = UsernameA;
                    Client.cbox.setTitle("Chat with " + UsernameA);
                    Client.cbox.ip = ipAStr;
                    client.createChatSocket(UsernameA, ipAStr);
                } catch (Exception ex) {

                }

                // dong server
                closeDialog(cloneObj);

            }
        });
        GridBagConstraints gbc_btnAccecpt = new GridBagConstraints();
        gbc_btnAccecpt.insets = new Insets(0, 0, 0, 5);
        gbc_btnAccecpt.gridx = 1;
        gbc_btnAccecpt.gridy = 2;
        contentPane.add(btnAccecpt, gbc_btnAccecpt);

        JButton btnDecline = new JButton("Decline");
        GridBagConstraints gbc_btnDecline = new GridBagConstraints();
        gbc_btnDecline.gridx = 2;
        gbc_btnDecline.gridy = 2;
        contentPane.add(btnDecline, gbc_btnDecline);

        // hien thi ten
        name.setText(UsernameA);
    }

    public void closeDialog(ChatInform ci) {
        ci.dispose();
    }

}
