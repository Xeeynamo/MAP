package utility;

import data.Data;
import data.EmptyDatasetException;
import database.EmptySetException;
import mining.ClusteringRadiusException;
import mining.QTMiner;

import java.net.*;
import java.io.*;

/**
 * Classe che gestisce una singola connessione da parte di un client
 * @author Ciccariello Luciano, Palumbo Vito, Rosini Luigi
 */
public class ServerOneClient extends Thread {
    private Socket socket;
    private mining.QTMiner kmeans;

    /**
     * Inizializza gli attributi socket, in ed out. Avvia il thread.
     * @param s
     * @throws IOException
     */
    public ServerOneClient(Socket s) throws IOException {
        socket = s;
    }

    /**
     * Si occupa della ricezione sicura di un oggetto via socket
     * @param socket la quale ricevere l'oggetto
     * @return oggetto ricevuto dal socket specificato
     * @throws ClassNotFoundException nel caso la classe ricevuta non vi � riconosciuta
	 * @throws IOException nel caso si verifichi un errore in fase di lettura
     */
	private static Object readObject(Socket socket) throws ClassNotFoundException, IOException
	{
		Object o;
		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
		o = in.readObject();
		return o;
	}
	/**
     * Si occupa dell'invio sicuro di un oggetto via socket
     * @param socket la quale ricever� l'oggetto
	 * @param o oggetto da inviare
	 * @throws IOException nel caso si verifichi un errore in fase di scrittura
	 */
	private static void writeObject(Socket socket, Object o) throws IOException
	{
		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
		out.writeObject(o);
		out.flush();
	}

    /**
     * Riscrive il metodo run della superclasse Thread al fine di gestire le
     * richieste del client
     */
    public void run()
    {
    	while (socket.isConnected())
    	{
            try {
            	// il primo oggetto in ricezione sar� l'operazione da effettuare
                Object o = readObject(socket);
                // ci si aspetta che l'operazione sia un intero
                if (o instanceof Integer)
                {
                    switch ((Integer)o)
                    {
                        case 0: // STORE TABLE FROM DB
                            break; //To do : check table existence.
                        case 1: // LEARNING FROM DB
                            learningFromDb(socket);
                            break;
                        case 2: // STORE CLUSTER IN FILE
                            learningFromDb(socket);
                            break;
                        case 3: // LEARNING FROM FILE
                            learningFromFile(socket);
                            break;
                        default:
                        	// Nel caso venga selezionata un'operazione non supportata, si esce
                            System.out.println("Operation " + o + " from " + socket + " not supported.\nThe connection will be closed.");
                            socket.close();
                            break;
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println(e.getMessage());
                break;
            }
    	}
    }

    /**
     * Si occupa di leggere il set da file e di inviarlo al client
     * @param socket del client
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void learningFromFile(Socket socket) throws IOException, ClassNotFoundException
    {
        String fileName = (String)readObject(socket);
        try
        {
            String result = new QTMiner(fileName + ".dmp").toString();
            writeObject(socket, "OK");
            writeObject(socket, result);
        }
        catch (Exception e)
        {
            writeObject(socket, "BAD");
        }
    }
    
    /**
     * Si occupa di leggere il set dal database e di inviarlo al client
     * @param socket del client
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public boolean learningFromDb(Socket socket)
    {
    	Object o;
    	try {
			o = readObject(socket);
			if (o instanceof String)
			{
				String tableName = (String)o;
				try {
					Data data = new Data(tableName);
					writeObject(socket, "OK");
					o = readObject(socket);
					if (o instanceof Double)
					{
						double radius = (Double)o;
						if (radius <= 0.0)
							writeObject(socket, "Radius must be greater than 0.");
						else
						{
	                        QTMiner qt = new QTMiner(radius);
                            try {
								int numC = qt.compute(data);
								writeObject(socket, "OK");
								writeObject(socket, new Integer(numC));
								writeObject(socket, qt.getC().toString(data));
							} catch (ClusteringRadiusException e) {
								writeObject(socket, "An invalid radius value was specified.");
							} catch (EmptyDatasetException e) {
								writeObject(socket, "Dataset is empty.");
							}
						}
					}
					else
						writeObject(socket, "Expected a decimal value greater than 0.");
				} catch (EmptySetException e) {
					writeObject(socket, "Table " + tableName + " empty or not found.");
				}
			}
			else
				writeObject(socket, "Expected the name of table to process.");
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
			return false;
		} catch (IOException e) {
			System.out.println("I/O Error: " + e.getMessage());
			return false;
		}
    	return true;
    }
}
