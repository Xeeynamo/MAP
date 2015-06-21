package utility;

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
            InputStreamReader inStreamReader = new InputStreamReader(socket.getInputStream());

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
