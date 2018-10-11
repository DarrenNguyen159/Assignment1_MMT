package Chat;

import java.awt.BorderLayout;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTable;
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
import java.util.Arrays;
import java.awt.event.ActionEvent;

public class OnlineList extends JFrame {

    private JPanel contentPane;
    private JTable table;
    private String username;
    private String[] OnlineNames;
    int port = 9000;
    Client m;

    /**
     * Create the frame.
     */
    public OnlineList(String name, String[] names, Client ma) {
        username = name;
        this.m = ma;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        GridBagLayout gbl_contentPane = new GridBagLayout();
        gbl_contentPane.columnWidths = new int[] { 0, 0, 0 };
        gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 0, 0 };
        gbl_contentPane.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
        gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
        contentPane.setLayout(gbl_contentPane);

        JLabel lblUsername = new JLabel("Username:");
        GridBagConstraints gbc_lblUsername = new GridBagConstraints();
        gbc_lblUsername.anchor = GridBagConstraints.WEST;
        gbc_lblUsername.insets = new Insets(0, 0, 5, 5);
        gbc_lblUsername.gridx = 0;
        gbc_lblUsername.gridy = 0;
        contentPane.add(lblUsername, gbc_lblUsername);

        JLabel clientName = new JLabel(name);
        GridBagConstraints gbc_clientName = new GridBagConstraints();
        gbc_clientName.anchor = GridBagConstraints.WEST;
        gbc_clientName.insets = new Insets(0, 0, 5, 0);
        gbc_clientName.gridx = 1;
        gbc_clientName.gridy = 0;
        contentPane.add(clientName, gbc_clientName);

        JButton connectBTN = new JButton("Connect");
        connectBTN.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                /* Su kien khi bam nut Connect */
                int rowIndex = table.getSelectedRow();
                int colIndex = table.getSelectedColumn();
                Object selected = table.getValueAt(rowIndex, colIndex);
                String selectedNameB = selected.toString();
                // DEBUG: System.out.println(selectedName);

                // gui cho server name cua user muon chat
                try {
                    InetAddress ip = null;
                    byte[] ipAddr = InetAddress.getLocalHost().getAddress();
                    ip = InetAddress.getByAddress(ipAddr);

                    // Ket noi den ChatServer
                    InetAddress address = InetAddress.getByName(Client.svip);
                    Socket socket = new Socket(address, port);
                    OutputStream os = socket.getOutputStream();
                    OutputStreamWriter osw = new OutputStreamWriter(os);
                    BufferedWriter bw = new BufferedWriter(osw);
                    System.out.println("DEBUG: " + selectedNameB);
                    bw.write("REQ CHAT ");
                    bw.write(selectedNameB + " ");
                    bw.write(name);
                    bw.write("\n");
                    bw.flush();

                    ma.createChatSocket(selectedNameB, null);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        GridBagConstraints gbc_connectBTN = new GridBagConstraints();
        gbc_connectBTN.anchor = GridBagConstraints.NORTH;
        gbc_connectBTN.insets = new Insets(0, 0, 5, 5);
        gbc_connectBTN.gridx = 0;
        gbc_connectBTN.gridy = 1;
        contentPane.add(connectBTN, gbc_connectBTN);

        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                // gui yeu cau refresh online list
                try {
                    // Ket noi den ChatServer
                    InetAddress address = InetAddress.getByName(Client.svip);
                    Socket socket = new Socket(address, 9000);

                    OutputStream os = socket.getOutputStream();
                    OutputStreamWriter osw = new OutputStreamWriter(os);
                    BufferedWriter bw = new BufferedWriter(osw);
                    bw.write("REQ REFRESH\n");
                    bw.flush();

                    // nhan ve danh sach online
                    InputStream is = socket.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader br = new BufferedReader(isr);

                    System.out.println("Server return: ");
                    String line = br.readLine();
                    System.out.println(line + "\n");
                    // line la danh sach online
                    String[] splited = line.split(" ");
                    String[] names = Arrays.copyOfRange(splited, 2, splited.length);// Bo RES REFRESH
                    OnlineNames = names;
                    updateTable();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });
        GridBagConstraints gbc_btnRefresh = new GridBagConstraints();
        gbc_btnRefresh.insets = new Insets(0, 0, 5, 0);
        gbc_btnRefresh.gridx = 1;
        gbc_btnRefresh.gridy = 1;
        contentPane.add(btnRefresh, gbc_btnRefresh);

        JPanel panel = new JPanel();
        GridBagConstraints gbc_panel = new GridBagConstraints();
        gbc_panel.anchor = GridBagConstraints.WEST;
        gbc_panel.gridheight = 2;
        gbc_panel.gridwidth = 2;
        gbc_panel.fill = GridBagConstraints.VERTICAL;
        gbc_panel.gridx = 0;
        gbc_panel.gridy = 2;
        contentPane.add(panel, gbc_panel);

        table = new JTable();
        panel.add(table);
        OnlineNames = names;
        updateTable();
    }

    private Object[] loadColumnName() {
        return new Object[] { "UserName" };
    }

    private Object[][] loadData() {
        int j = 0;
        Object[][] data = new Object[][] { { "None" }, { "None" }, { "None" }, { "None" }, { "None" }, { "None" },
                { "None" }, { "None" }, { "None" }, { "None" } };
        for (int i = 0; i < OnlineNames.length; i++) {
            if (OnlineNames[i].equals(username)) {
//				System.out.println("Trung ten: " + username);
                continue;
            }
            data[j][0] = OnlineNames[i];
            j++;
        }
        return data;
    }

    private void updateTable() {

        TableModel model = new DefaultTableModel(loadData(), loadColumnName());
        table.setModel(model);
    }

}
