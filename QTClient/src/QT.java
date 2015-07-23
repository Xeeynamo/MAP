
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;




public class QT extends JApplet {

	private static String DEFAULT_HOST = "localhost";
	private static int DEFAULT_PORT = 8080;
	private static IAsyncResponsive ResponsiveInterface;

	private Socket socket = null;

	protected static Object readObject(Socket socket) throws ClassNotFoundException, IOException
	{
		Object o;
		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
		o = in.readObject();
		return o;
	}
	protected static void writeObject(Socket socket, Object o) throws IOException
	{
		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
		out.writeObject(o);
		out.flush();
	}

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
		@Override public Object runasync() {
			if (radius <= 0)
				throw new NumberFormatException("Radius <= 0");
			try
			{
				writeObject(socket, new Integer(1));
				writeObject(socket, table);

				String result = (String)readObject(socket);
				if(result.compareTo("OK") == 0) {
					writeObject(socket, new Double(radius));
					result = (String)readObject(socket);
					if(result.compareTo("OK") == 0) {
						return "Number of clusters :" + (Integer)readObject(socket) + "\n" + (String)readObject(socket);
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
	private class AsyncLearningFromFileRequest extends AsyncClass {
		Socket socket;
		private String table;
		private double radius;
		public AsyncLearningFromFileRequest(IAsyncResponsive responsive, Socket socket, String tableName, double radius) {
			super(responsive);
			this.socket = socket;
			this.table = tableName;
			this.radius = radius;
		}
		@Override public Object runasync() {
			if (radius <= 0)
				throw new NumberFormatException("Radius <= 0");
			try
			{
				writeObject(socket, new Integer(3));
				writeObject(socket, table);
				writeObject(socket, new Double(radius));

				String result = (String)readObject(socket);
				if(result.compareTo("OK") == 0) {
					return (String)readObject(socket);
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
			private JLabel plot = new JLabel("",null,JLabel.CENTER);


			JPanelCluster(String buttonName, ActionListener a) {


				JLabel text = new JLabel("Table:");
				JLabel radius = new JLabel("Radius:");
				JPanel upPanel = new JPanel(new GridBagLayout()), centralPanel = new JPanel(new BorderLayout()),
						downPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

				JScrollPane scrollingArea = new JScrollPane(clusterOutput);

				GridBagConstraints gbc = new GridBagConstraints();

				executeButton = new JButton(buttonName);
				executeButton.addActionListener(a);

				clusterOutput.setBorder(BorderFactory.createLineBorder(Color.black));
				clusterOutput.setEditable(false);


				gbc.gridx = 0;
				gbc.gridy = 0;
				upPanel.add(text,gbc);
				gbc.gridx = 1;
				gbc.gridy = 0;
				upPanel.add(tableText,gbc);
				gbc.gridx = 0;
				gbc.gridy = 1;
				upPanel.add(radius,gbc);
				gbc.gridx = 1;
				gbc.gridy = 1;
				upPanel.add(parameterText,gbc);


				centralPanel.add(plot,BorderLayout.CENTER);
				centralPanel.add(scrollingArea,BorderLayout.SOUTH);



				downPanel.add(executeButton);

				this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
				this.add(Box.createRigidArea(new Dimension(0, 10)));
				this.add(upPanel);
				this.add(Box.createRigidArea(new Dimension(0, 45)));
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
					double radius;
					try {
						radius = new Double(panelDB.parameterText.getText()).doubleValue();
					} catch (NumberFormatException ex) {
						radius = 0.0;
					}
					new AsyncLearningFromDatabaseRequest(ResponsiveInterface, socket, table, radius).start();
				}
			});
			tabbedPane.addTab("DB", iconDB, panelDB,
					"Does nothing");

			imgURL = getClass().getResource("img/file.jpg");
			ImageIcon iconFile = new ImageIcon(imgURL);
			panelFile = new JPanelCluster("STORE FROM FILE", new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String table = panelFile.tableText.getText();
					double radius;
					try {
						radius = new Double(panelFile.parameterText.getText()).doubleValue();
					} catch (NumberFormatException ex) {
						radius = 0.0;
					}
					new AsyncLearningFromFileRequest(ResponsiveInterface, socket, table, radius).start();
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
			JTextArea textArea;
			if (o instanceof AsyncLearningFromDatabaseRequest)
				textArea = panelDB.clusterOutput;
			else if (o instanceof AsyncLearningFromFileRequest)
				textArea = panelFile.clusterOutput;
			else
				return;
			textArea.setText("Processing the server...");
		}

		@Override public void asyncEnd(AsyncClass o, Object result)
		{
			JTextArea textArea;
			JLabel plot;
			if (o instanceof AsyncLearningFromDatabaseRequest){
				textArea = panelDB.clusterOutput;
				plot = panelDB.plot;
			}
			else if (o instanceof AsyncLearningFromFileRequest) {
				textArea = panelFile.clusterOutput;
				plot = panelFile.plot;
			}
			else
				return;
			textArea.setText((String)result);
			try {

				byte[] buffer = (byte[])readObject(socket);
				ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
				BufferedImage img = ImageIO.read(bais);
				bais.close();
				ImageIcon icon = new ImageIcon(img);
				plot.setIcon(icon);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
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
