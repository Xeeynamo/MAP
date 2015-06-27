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
                case 0: // STORE TABLE FROM DD
                    break;
                case 1: // LEARNING FROM DB
                    learningFromDb(socket);
                    break;
                case 2: // STORE CLUSTER IN FILE
                    learningFromDb(socket);
                    break;
                case 3: // LEARNING FROM FILE
                    learningFromFile(socket);
                    break;
            }
        } catch (IOException | ClassNotFoundException e) {
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
    public void learningFromFile(Socket socket) throws IOException, ClassNotFoundException
    {
        ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
        String fileName = (String)inStream.readObject();
        try
        {
            String result = new QTMiner(fileName + ".dmp").toString();
            outStream.writeObject("OK");
            outStream.writeObject(result);
        }
        catch (Exception e)
        {
            outStream.writeObject("BAD");
        }
    }
    public void learningFromDb(Socket socket)
    {

    }
}
