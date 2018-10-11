package Chat;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class WaitChat implements Runnable {
    int portNay;
    String nameKia;

    public WaitChat(int port, String name) {
        portNay = port;
        nameKia = name;
    }

    @Override
    public void run() {
        System.out.println("Waiting chat on port " + portNay);
        try {
            ServerSocket serverSocket = new ServerSocket(portNay);
            Socket socketListener = null;

            while (true) {
                try {
                    socketListener = serverSocket.accept();
                    InputStream is = socketListener.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader br = new BufferedReader(isr);

                    System.out.println("ClientKia sent: ");
                    String line = br.readLine();
                    System.out.println(line + "\n");
                    String[] splited = line.split(" ");
                    if (splited[0].equals("CHAT")) {
                        if (splited[1].equals("SEND")) {
                            // hien tin nhan len o chat
                            String currentText = ChatBox.chatflow.getText();
                            String newText = currentText + "\n";
                            // in ten nguoi gui
                            newText += nameKia + ": ";
                            // in doan tin nhan
                            newText += line.substring(9);
                            ChatBox.chatflow.setText(newText);
                        }
                        if (splited[1].equals("CLOSE")) {
                            // thong bao nguoi kia roi chat
                            String currentText = ChatBox.chatflow.getText();
                            String newText = currentText + "\n";
                            // in doan tin nhan
                            newText += nameKia + " has left.";
                            ChatBox.chatflow.setText(newText);
                        }
                    }

                } catch (Exception ex) {

                }
            }

        } catch (Exception ex) {

        }
    }
}
