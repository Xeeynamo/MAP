
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;


public class QT extends JApplet {

	private static String DEFAULT_HOST = "localhost";
	private static int DEFAULT_PORT = 8080;
	public static IAsyncResponsive ResponsiveInterface;

	private Socket socket = null;

	private class AsyncLearningFromDatabaseRequest extends AsyncClass {
		Socket socket;
		private String table;
		private double radius;
		public AsyncLearningFromDatabaseRequest(IAsyncResponsive responsive, Socket socket, String tableName, double radius) {
			super(responsive);
			this.socket = socket;
			this.table = tableName;
			this.radius = radius;
		}
		Object readObject() throws ClassNotFoundException, IOException
		{
			Object o;
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			o = in.readObject();
			return o;
		}
		void writeObject(Object o) throws IOException
		{
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			out.writeObject(o);
			out.flush();
		}
		@Override public Object runasync() {
			String result;

			if (radius<=0)
				throw new NumberFormatException("Radius<=0");

			try
			{
				writeObject(new Integer(1));
				writeObject(table);

				result = (String)readObject();

				if(result.compareTo("OK") == 0) {
					writeObject(new Double(radius));
					result = (String)readObject();

					if(result.compareTo("OK") == 0) {
						return "Number of clusters :" + (Integer)readObject() + "\n" + (String)readObject();
					}
					else {
						JOptionPane.showMessageDialog(null, result, "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
				else {
					JOptionPane.showMessageDialog(null, result, "Error", JOptionPane.ERROR_MESSAGE);
				}
			} catch (IOException e) {

			} catch (ClassNotFoundException e) {

			}
			return "Error";
		}
	}
	private class TabbedPane extends JPanel implements IAsyncResponsive {
		private JPanelCluster panelDB;
		private JPanelCluster panelFile;

		private class JPanelCluster extends JPanel {

			private JTextField tableText = new JTextField(20);
			private JTextField parameterText = new JTextField(10);
			private JTextArea clusterOutput = new JTextArea(10,12);
			private JButton executeButton;
			java.net.URL imgURL = getClass().getResource("img/db.jpg");	  //DA ELIMINARE UNA VOLTA IMPLEMENTATO IL MECCANISMO PER LA RICEZIONE DEL GRAFICO
			private JLabel plotimage = new JLabel(new ImageIcon(imgURL)); //In realtà l'immagine sarà ottenuta dal server.


			JPanelCluster(String buttonName, ActionListener a) {


				JLabel text = new JLabel("Table:");
				JLabel radius = new JLabel("Radius:");
				JPanel upPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)), centralPanel = new JPanel(new BorderLayout()),
						downPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

				JScrollPane scrollingArea = new JScrollPane(clusterOutput);

				executeButton = new JButton(buttonName);
				executeButton.addActionListener(a);

				clusterOutput.setBorder(BorderFactory.createLineBorder(Color.black));
				clusterOutput.setEditable(false);



				upPanel.add(text);
				upPanel.add(tableText);
				upPanel.add(radius);
				upPanel.add(parameterText);


				centralPanel.add(plotimage,BorderLayout.NORTH);
				centralPanel.add(scrollingArea,BorderLayout.SOUTH);



				downPanel.add(executeButton);

				this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
				this.add(Box.createRigidArea(new Dimension(0, 20)));
				this.add(upPanel);
				this.add(Box.createRigidArea(new Dimension(0, 145)));
				this.add(centralPanel);
				this.add(downPanel);



			}
		}

		TabbedPane() {
			super(new GridLayout(1, 1));
			JTabbedPane tabbedPane = new JTabbedPane();
			//copy img in src Directory and bin directory
			java.net.URL imgURL = getClass().getResource("img/db.jpg");
			ImageIcon iconDB = new ImageIcon(imgURL);
			 panelDB = new JPanelCluster("MINE", new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String table = panelDB.tableText.getText();
					double radius = new Double(panelDB.parameterText.getText()).doubleValue();
					new AsyncLearningFromDatabaseRequest(ResponsiveInterface, socket, table, radius).start();
					return;
				}
			});
			tabbedPane.addTab("DB", iconDB, panelDB,
					"Does nothing");

			imgURL = getClass().getResource("img/file.jpg");
			ImageIcon iconFile = new ImageIcon(imgURL);
			 panelFile = new JPanelCluster("STORE FROM FILE", new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						learningFromFileAction();
					} catch (IOException | ClassNotFoundException e1) {
						System.out.println(e1);
					}
				}
			});
			tabbedPane.addTab("FILE", iconFile, panelFile,
					"Does nothing");
			//Add the tabbed pane to this panel.
			add(tabbedPane);
			//The following line enables to use scrolling tabs.
			tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		}

		@Override public void asyncStart(AsyncClass o)
		{
			panelDB.clusterOutput.setText("Processing the server...");
		}
		@Override public void asyncEnd(AsyncClass o, Object result)
		{
			if (o instanceof AsyncLearningFromDatabaseRequest)
			{
				if (result != null)
					panelDB.clusterOutput.setText((String)result);
			}
		}
		private void learningFromFileAction() throws SocketException, IOException, ClassNotFoundException {
			/*String table = panelFile.tableText.getText();
			double radius = Double.parseDouble(panelFile.parameterText.getText());

			out.writeObject(3);
			out.writeObject(table);
			out.writeObject(radius);

			String result = (String)in.readObject();
			if(result == "OK")
				JOptionPane.showMessageDialog(this,"Success!!");
			else
				JOptionPane.showMessageDialog(this,"Error: something went wrong"*/
		}
	}

	public void init() {
		String strHost = getParameter("ServerIP");
		String strPort = getParameter("Port");
		int port;

		if (strHost == null) { // se non � specificato alcun indirizzo IP
			strHost = DEFAULT_HOST; // allora ne imposta uno di default
		}

		if (strPort == null) { // se non � specificata la porta
			port = DEFAULT_PORT; // allora imposta la porta di default
		}
		else
		{
			// altrimenti la processa dal parametro
			try {
				port = Integer.parseInt(strPort);
			} catch (NumberFormatException e) {
				// se il parametro � un formato differente da un numero
				// allora imposta la porta come da default
				port = DEFAULT_PORT;
			}
		}

		try {
			InetAddress addr = InetAddress.getByName(strHost); // ottiene l'indirizzo dell'host specificato
			System.out.println("Connecting to " + addr + "...");
			socket = new Socket(addr, port); // prova a connetters
			System.out.println("Success! Connected to " + socket);

			TabbedPane tab = new TabbedPane();
			getContentPane().setLayout(new GridLayout(1, 1));
			getContentPane().add(tab);
			ResponsiveInterface = tab;

		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Unable to connect to " + strHost + ":" + port + ".\n" + e.getMessage(),
					"Error", JOptionPane.ERROR_MESSAGE);
			this.destroy();
			System.exit(0);
		}

	}
}
