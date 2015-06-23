package utility;

import mining.QTMiner;

import java.net.*;
import java.io.*;
import java.io.IOException;

/**
 * @author Ciccariello Luciano, Palumbo Vito, Rosini Luigi
 */
public class ServerOneClient extends Thread {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private mining.QTMiner kmeans;

    /**
     * Inizializza gli attributi socket, in ed out. Avvia il thread.
     * @param s
     * @throws IOException
     */
    public ServerOneClient(Socket s) throws IOException
    {
        socket = s;
    }

    /**
     * Riscrive il metodo run della superclasse Thread al fine di gestire le richieste del client
     */
    public void run()
    {
        try {
            BufferedInputStream inStream = new BufferedInputStream(socket.getInputStream());
            int program = inStream.read();
            switch (program)
            {
                case 1:
                    learningFromFile(socket);
                    break;
                case 2:
                    learningFromDb(socket);
                    break;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public String readString(BufferedInputStream stream) throws IOException
    {
        byte[] contents = new byte[1024];
        int bytesRead = 0;
        String ret = "";

        while( (bytesRead = stream.read(contents)) != -1){
            ret = new String(contents, 0, bytesRead);
        }
        return ret;
    }
    public void learningFromFile(Socket socket) throws IOException
    {
        BufferedInputStream inStream = new BufferedInputStream(socket.getInputStream());
        String fileName = readString(inStream);
        //new QTMiner(fileName + ".dmp");
    }
    public void learningFromDb(Socket socket)
    {

    }
}
