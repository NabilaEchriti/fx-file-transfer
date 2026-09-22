import java.net.Socket;
import java.io.*;
import java.util.StringTokenizer;

public class FxClient {

    public static void main(String[] args) throws Exception {
        String command = args[0];
        String fileName = args[1];

        try (Socket connectionToServer = new Socket("localhost", 9090)) {

            InputStream in = connectionToServer.getInputStream();
            OutputStream out = connectionToServer.getOutputStream();

            BufferedReader headerReader = new BufferedReader(new InputStreamReader(in));
            BufferedWriter headerWriter = new BufferedWriter(new OutputStreamWriter(out));

            if (command.equals("d")) {
                String request = "download " + fileName + "\n";
                headerWriter.write(request);
                headerWriter.flush();

                String response = headerReader.readLine();

                if (response.equals("NOT FOUND")) {
                    System.out.println("Fichier introuvable sur le serveur.");
                } else {
                    StringTokenizer tokenizer = new StringTokenizer(response, " ");
                    String status = tokenizer.nextToken();

                    if (status.equals("OK")) {
                        int fileSize = Integer.parseInt(tokenizer.nextToken());

                        byte[] fileBytes = new byte[fileSize];
                        DataInputStream dataIn = new DataInputStream(in);
                        dataIn.readFully(fileBytes);

                        FileOutputStream fileOut = new FileOutputStream("ClientShare/" + fileName);
                        fileOut.write(fileBytes);
                        fileOut.close();

                        System.out.println("Fichier téléchargé avec succès : " + fileName);
                    }
                }
            }
        }
    }
}