package resourse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientFxmlController {
    public TextField txtmessage;
    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    String message="";
    String reply="";
    @FXML
    private TextArea txtArea;

    public void initialize() {
        new Thread(() -> {
            try {
                socket = new Socket("localhost", 3004);
                dataInputStream = new DataInputStream(socket.getInputStream());
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                while (!message.equals("finish")) {
                    message = dataInputStream.readUTF();
                    txtArea.appendText("\n server:" + message);
                }
                dataInputStream.close();
                dataOutputStream.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();


    }

    public void btnSendOnAction(ActionEvent actionEvent) {
        reply=txtmessage.getText();
        try {
            dataOutputStream.writeUTF(txtmessage.getText().trim());
            dataOutputStream.flush();
            txtArea.appendText("\n client:" + reply);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
