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
        in = new ObjectInputStream(socket.getInputStream());
        out = new ObjectOutputStream(socket.getOutputStream());
        this.start();
    }

    /**
     * Riscrive il metodo run della superclasse Thread al fine di gestire le richieste del client
     */
    public void run()
    {
        try {
            //BufferedInputStream inStream = new BufferedInputStream(socket.getInputStream());
            Object o = in.readObject();
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
        //ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());
        //ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
        String fileName = (String)in.readObject();
        try
        {
            String result = new QTMiner(fileName + ".dmp").toString();
            out.writeObject("OK");
            out.writeObject(result);
        }
        catch (Exception e)
        {
            out.writeObject("BAD");
        }
    }
    public boolean learningFromDb(Socket socket)
    {
    	Object o;
    	try {
			o = in.readObject();
			if (o instanceof String)
			{
				String tableName = (String)o;
				try {
					Data data = new Data(tableName);
					out.writeObject("OK");
					o = in.readObject();
					if (o instanceof Double)
					{
						double radius = (Double)o;
						if (radius <= 0.0)
							out.writeObject("Radius must be greater than 0.");
						else
						{
	                        QTMiner qt = new QTMiner(radius);
                            try {
								int numC = qt.compute(data);
								out.writeObject("OK");
								out.writeObject(new Integer(numC));
								out.writeObject(qt.getC().toString(data));
							} catch (ClusteringRadiusException e) {
								out.writeObject("An invalid radius value was specified.");
							} catch (EmptyDatasetException e) {
								out.writeObject("Dataset is empty.");
							}
						}
					}
					else
						out.writeObject("Expected a decimal value greater than 0.");
				} catch (EmptySetException e) {
					out.writeObject("Table " + tableName + " empty or not found.");
				}
			}
			else
				out.writeObject("Expected the name of table to process.");
		} catch (ClassNotFoundException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
    	return true;
    }
}
