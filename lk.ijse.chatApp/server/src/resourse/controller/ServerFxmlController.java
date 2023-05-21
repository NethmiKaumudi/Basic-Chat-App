package resourse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerFxmlController {
    ServerSocket serverSocket;
    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    String message="";
    String reply="";
    @FXML
    private TextArea txtArea;
    @FXML
    private TextField txtmessage;

    public void initialize() throws IOException {
        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(3004);
                txtArea.appendText("Server Started!");
                socket = serverSocket.accept();
                txtArea.appendText("\n Client Connected!");
                dataInputStream = new DataInputStream(socket.getInputStream());
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                while (!message.equals("finish")) {
                    message = dataInputStream.readUTF();
                    txtArea.appendText("\n client:" + message);
                }
                dataInputStream.close();
                dataOutputStream.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }).start();


    }

    @FXML
    void btnSendOnAction(ActionEvent event) {
        reply=txtmessage.getText();
        try {
            dataOutputStream.writeUTF(txtmessage.getText().trim());
            dataOutputStream.flush();
            txtArea.appendText("\n server:" + reply);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
