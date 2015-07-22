package utility;

import data.Data;
import data.EmptyDatasetException;
import database.EmptySetException;
import mining.ClusteringRadiusException;
import mining.QTMiner;

import java.net.*;
import java.io.*;

/**
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
    public ServerOneClient(Socket s) throws IOException
    {
        socket = s;
        this.start();
    }

	private static Object readObject(Socket socket) throws ClassNotFoundException, IOException
	{
		Object o;
		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
		o = in.readObject();
		return o;
	}
	private static void writeObject(Socket socket, Object o) throws IOException
	{
		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
		out.writeObject(o);
		out.flush();
	}

    /**
     * Riscrive il metodo run della superclasse Thread al fine di gestire le richieste del client
     */
    public void run()
    {
        try {
            //BufferedInputStream inStream = new BufferedInputStream(socket.getInputStream());
            Object o = readObject(socket);
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
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            //System.out.println(e.getMessage());
        	try {
				out.writeObject(e.getMessage());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				System.out.println(e1.getMessage());
			}
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
