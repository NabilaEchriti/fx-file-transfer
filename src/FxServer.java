import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.util.StringTokenizer;

public class FxServer {

    public static void main(String[] args) throws Exception {

        try (ServerSocket serverSocket = new ServerSocket(9090)) {
            System.out.println("Server waiting...");

            while (true) {
                Socket connectionFromClient = serverSocket.accept();
                System.out.println("Client connected: " + connectionFromClient.getPort());

                InputStream in = connectionFromClient.getInputStream();
                OutputStream out = connectionFromClient.getOutputStream();

                BufferedReader headerReader = new BufferedReader(new InputStreamReader(in));
                BufferedWriter headerWriter = new BufferedWriter(new OutputStreamWriter(out));

                String header = headerReader.readLine();
                StringTokenizer tokenizer = new StringTokenizer(header, " ");
                String command = tokenizer.nextToken();
                String fileName = tokenizer.nextToken();

                if (command.equals("download")) {
                    try {
                        FileInputStream fileIn = new FileInputStream("ServerShare/" + fileName);
                        int fileSize = fileIn.available();

                        String response = "OK " + fileSize + "\n";
                        headerWriter.write(response);
                        headerWriter.flush();

                        byte[] bytes = new byte[fileSize];
                        fileIn.read(bytes);
                        fileIn.close();

                        DataOutputStream dataOut = new DataOutputStream(out);
                        dataOut.write(bytes);

                    } catch (FileNotFoundException e) {
                        headerWriter.write("NOT FOUND\n");
                        headerWriter.flush();
                    }
                }

                connectionFromClient.close();
            }
        }
    }
}