import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatRoomFormController {
    public TextField txtClientMassege;

    Socket socket = null;
    public void initialize() throws IOException {

        new Thread(()->{
            try {
                socket = new Socket("localhost",5000);

                InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String record = bufferedReader.readLine();
                System.out.println(record);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

    }

    public void sendOnAction(ActionEvent actionEvent) throws IOException {
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
        printWriter.println(txtClientMassege.getText());
        printWriter.flush();
    }
}
