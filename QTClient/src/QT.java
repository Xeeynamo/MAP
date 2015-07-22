
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

	private ObjectOutputStream out;
	private ObjectInputStream in;

	private class TabbedPane extends JPanel {

		private JPanelCluster panelDB;
		private JPanelCluster panelFile;

		private class JPanelCluster extends JPanel {

			private JTextField tableText = new JTextField(20);
			private JTextField parameterText = new JTextField(10);
			private JTextArea clusterOutput = new JTextArea(10,72);
			private JButton executeButton;

			JPanelCluster(String buttonName, ActionListener a) {

				JLabel text = new JLabel("Table:");
				JLabel radius = new JLabel("Radius:");
				JPanel upPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)), centralPanel = new JPanel(new BorderLayout()),
						downPanel = new JPanel(new BorderLayout());

				JScrollPane scrollingArea = new JScrollPane(clusterOutput);

				executeButton = new JButton(buttonName);
				executeButton.addActionListener(a);

				clusterOutput.setBorder(BorderFactory.createLineBorder(Color.black));
				clusterOutput.setEditable(false);



				upPanel.add(text);
				upPanel.add(tableText);
				upPanel.add(radius);
				upPanel.add(parameterText);


				centralPanel.add(scrollingArea,BorderLayout.CENTER);


				downPanel.add(executeButton, BorderLayout.PAGE_END);


				this.add(upPanel);
				this.add(Box.createRigidArea(new Dimension(0,145)));
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
					try {
						learningFromDBAction();
					} catch (IOException | ClassNotFoundException e1) {
						System.out.println(e1);
					}
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

		private void learningFromDBAction() throws SocketException, IOException, ClassNotFoundException {

			double radius;
			String table, result;

			try{
				radius = new Double(panelDB.parameterText.getText()).doubleValue();
				if (radius<=0)
					throw new NumberFormatException("Radius<=0");
			}
			catch(NumberFormatException e){
				JOptionPane.showMessageDialog(this,e.toString());
				return;
			}

			table = panelDB.tableText.getText();

			out.writeObject(new Integer(1));
			out.writeObject(table);

			result = (String)in.readObject();

			if(result.compareTo("OK") == 0) {
				out.writeObject(new Double(radius));
				result = (String)in.readObject();

				if(result == "OK") {
					//Reading amount of clusters and showing them on JTextArea
					panelDB.clusterOutput.setText( "Number of clusters :" + (Integer)in.readObject() + "\n" + (String)in.readObject());
				}
				else {
					JOptionPane.showMessageDialog(this, result, "Error", JOptionPane.ERROR_MESSAGE);
				}

			}
			else {
				JOptionPane.showMessageDialog(this, result, "Error", JOptionPane.ERROR_MESSAGE);
			}



		}


		private void learningFromFileAction() throws SocketException, IOException, ClassNotFoundException {


			String table = panelFile.tableText.getText();
			double radius = Double.parseDouble(panelFile.parameterText.getText());

			out.writeObject(3);
			out.writeObject(table);
			out.writeObject(radius);

			String result = (String)in.readObject();
			if(result == "OK")
				JOptionPane.showMessageDialog(this,"Success!!");
			else
				JOptionPane.showMessageDialog(this,"Error: something went wrong");



		}


	}

	public void init() {
		String strHost = getParameter("ServerIP");
		String strPort = getParameter("Port");
		int port;

		if (strHost == null) { // se non è specificato alcun indirizzo IP
			strHost = DEFAULT_HOST; // allora ne imposta uno di default
		}

		if (strPort == null) { // se non è specificata la porta
			port = DEFAULT_PORT; // allora imposta la porta di default
		}
		else
		{
			// altrimenti la processa dal parametro
			try {
				port = Integer.parseInt(strPort);
			} catch (NumberFormatException e) {
				// se il parametro è un formato differente da un numero
				// allora imposta la porta come da default
				port = DEFAULT_PORT;
			}
		}

		try {
			InetAddress addr = InetAddress.getByName(strHost); //ip
			System.out.println("addr = " + addr);
			Socket socket = new Socket(addr, port); //Port
			System.out.println(socket);

			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());

			TabbedPane tab = new TabbedPane();
			getContentPane().setLayout(new GridLayout(1, 1));
			getContentPane().add(tab);

		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Unable to connect to " + strHost + ":" + port + ".\n" + e.getMessage(),
					"Error", JOptionPane.ERROR_MESSAGE);
			this.destroy();
			System.exit(0);
		}

	}
}
