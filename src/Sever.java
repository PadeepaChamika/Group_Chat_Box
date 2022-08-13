import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Sever {
    public static void main(String[] args) throws IOException {
        Socket accept = null;

        ServerSocket serverSocket = new ServerSocket(5000);
        System.out.println("Server Started");
        accept = serverSocket.accept();
        System.out.println("Client Connected..");

        InputStreamReader inputStreamReader = new InputStreamReader(accept.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String record = bufferedReader.readLine();
        System.out.println(record);

    }
}
