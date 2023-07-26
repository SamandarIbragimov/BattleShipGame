package fr.uga.battleship.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import fr.uga.battleship.AutomaticPlayer;
import fr.uga.battleship.GraphicNavalGrid;
import fr.uga.battleship.GraphicPlayer;
import fr.uga.battleship.Player;
import fr.uga.battleship.RemotePlayer;
import fr.uga.battleship.net.BattleshipServer;

import javax.swing.border.TitledBorder;

public class BattleShip {

	private JFrame frame;
	private JTextField tfNickName;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField tfAddress;
	private JPanel panelNetwork;
	private JSpinner spinnerPort;
	private BattleshipServer s;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BattleShip window = new BattleShip();
					window.frame.pack();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public BattleShip() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 455, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 434, 0 };
		gridBagLayout.rowHeights = new int[] { 46, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		frame.getContentPane().setLayout(gridBagLayout);

		JPanel panelServer = new JPanel();
		panelServer.setBorder(new TitledBorder(null, "Server", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panelServer = new GridBagConstraints();
		gbc_panelServer.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelServer.insets = new Insets(0, 0, 5, 0);
		gbc_panelServer.gridx = 0;
		gbc_panelServer.gridy = 0;
		frame.getContentPane().add(panelServer, gbc_panelServer);
		GridBagLayout gbl_panelServer = new GridBagLayout();
		gbl_panelServer.columnWidths = new int[] { 0, 0 };
		gbl_panelServer.rowHeights = new int[] { 0, 0 };
		gbl_panelServer.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_panelServer.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panelServer.setLayout(gbl_panelServer);

		JButton btStartServer = new JButton("Start");
		btStartServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if ("Start".equals(btStartServer.getText())) {
					if (s == null) {
						try {
							s = new BattleshipServer(8080);
							btStartServer.setText("Stop");
							new Thread() {
								public void run() {
									s.startServer();
								}
							}.start();
						} catch (IOException e1) {
							JOptionPane.showMessageDialog(null, "Network Error, cannot start the server",
									"Network Error", JOptionPane.ERROR_MESSAGE);
							;
							e1.printStackTrace();
						}

					} else {
						s.stopServer();
						btStartServer.setText("Start");
					}
				}
			}
		});
		GridBagConstraints gbc_btStartServer = new GridBagConstraints();
		gbc_btStartServer.gridx = 0;
		gbc_btStartServer.gridy = 0;
		panelServer.add(btStartServer, gbc_btStartServer);

		JPanel panelGame = new JPanel();
		panelGame.setBorder(new TitledBorder(null, "Game", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panelGame = new GridBagConstraints();
		gbc_panelGame.fill = GridBagConstraints.BOTH;
		gbc_panelGame.gridx = 0;
		gbc_panelGame.gridy = 1;
		frame.getContentPane().add(panelGame, gbc_panelGame);
		GridBagLayout gbl_panelGame = new GridBagLayout();
		gbl_panelGame.columnWidths = new int[] { 65, 53, 86, 61, 41, 65, 0 };
		gbl_panelGame.rowHeights = new int[] { 23, 0, 80, 0 };
		gbl_panelGame.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panelGame.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panelGame.setLayout(gbl_panelGame);

		JLabel lblNickname = new JLabel("NickName: ");
		GridBagConstraints gbc_lblNickname = new GridBagConstraints();
		gbc_lblNickname.anchor = GridBagConstraints.WEST;
		gbc_lblNickname.insets = new Insets(0, 0, 5, 5);
		gbc_lblNickname.gridx = 0;
		gbc_lblNickname.gridy = 0;
		panelGame.add(lblNickname, gbc_lblNickname);

		tfNickName = new JTextField();
		GridBagConstraints gbc_tfNickName = new GridBagConstraints();
		gbc_tfNickName.anchor = GridBagConstraints.WEST;
		gbc_tfNickName.insets = new Insets(0, 0, 5, 5);
		gbc_tfNickName.gridx = 1;
		gbc_tfNickName.gridy = 0;
		panelGame.add(tfNickName, gbc_tfNickName);
		tfNickName.setColumns(10);

		JRadioButton radioNetwork = new JRadioButton("Network");
		GridBagConstraints gbc_radioNetwork = new GridBagConstraints();
		gbc_radioNetwork.anchor = GridBagConstraints.NORTH;
		gbc_radioNetwork.insets = new Insets(0, 0, 5, 5);
		gbc_radioNetwork.gridx = 0;
		gbc_radioNetwork.gridy = 1;
		panelGame.add(radioNetwork, gbc_radioNetwork);
		radioNetwork.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tfAddress.setEnabled(true);
				spinnerPort.setEnabled(true);
			}
		});
		buttonGroup.add(radioNetwork);

		panelNetwork = new JPanel();
		GridBagConstraints gbc_panelNetwork = new GridBagConstraints();
		gbc_panelNetwork.anchor = GridBagConstraints.NORTHWEST;
		gbc_panelNetwork.insets = new Insets(0, 0, 5, 5);
		gbc_panelNetwork.gridwidth = 3;
		gbc_panelNetwork.gridx = 1;
		gbc_panelNetwork.gridy = 1;
		panelGame.add(panelNetwork, gbc_panelNetwork);
		GridBagLayout gbl_panelNetwork = new GridBagLayout();
		gbl_panelNetwork.columnWidths = new int[] { 46, 67, 0 };
		gbl_panelNetwork.rowHeights = new int[] { 20, 20, 0, 0 };
		gbl_panelNetwork.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gbl_panelNetwork.rowWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		panelNetwork.setLayout(gbl_panelNetwork);

		JLabel lblServerName = new JLabel("server name: ");
		GridBagConstraints gbc_lblServerName = new GridBagConstraints();
		gbc_lblServerName.anchor = GridBagConstraints.WEST;
		gbc_lblServerName.insets = new Insets(0, 0, 5, 5);
		gbc_lblServerName.gridx = 0;
		gbc_lblServerName.gridy = 0;
		panelNetwork.add(lblServerName, gbc_lblServerName);

		tfAddress = new JTextField();
		tfAddress.setText("localhost");
		GridBagConstraints gbc_tfAddress = new GridBagConstraints();
		gbc_tfAddress.fill = GridBagConstraints.BOTH;
		gbc_tfAddress.insets = new Insets(0, 0, 5, 0);
		gbc_tfAddress.gridx = 1;
		gbc_tfAddress.gridy = 0;
		panelNetwork.add(tfAddress, gbc_tfAddress);
		tfAddress.setColumns(10);

		JLabel lblPortNumber = new JLabel("port number: ");
		GridBagConstraints gbc_lblPortNumber = new GridBagConstraints();
		gbc_lblPortNumber.anchor = GridBagConstraints.WEST;
		gbc_lblPortNumber.insets = new Insets(0, 0, 5, 5);
		gbc_lblPortNumber.gridx = 0;
		gbc_lblPortNumber.gridy = 1;
		panelNetwork.add(lblPortNumber, gbc_lblPortNumber);

		spinnerPort = new JSpinner();

		spinnerPort.setModel(new SpinnerNumberModel(8080, 0, 65535, 1));
		GridBagConstraints gbc_spinnerPort = new GridBagConstraints();
		gbc_spinnerPort.insets = new Insets(0, 0, 5, 0);
		gbc_spinnerPort.gridx = 1;
		gbc_spinnerPort.gridy = 1;
		panelNetwork.add(spinnerPort, gbc_spinnerPort);

		JRadioButton radioAutomatic = new JRadioButton("Automatic Player");
		GridBagConstraints gbc_radioAutomatic = new GridBagConstraints();
		gbc_radioAutomatic.anchor = GridBagConstraints.NORTHWEST;
		gbc_radioAutomatic.insets = new Insets(0, 0, 0, 5);
		gbc_radioAutomatic.gridwidth = 2;
		gbc_radioAutomatic.gridx = 0;
		gbc_radioAutomatic.gridy = 2;
		panelGame.add(radioAutomatic, gbc_radioAutomatic);
		radioAutomatic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// getPanelNetwork().setEnabled(false);
				tfAddress.setEnabled(false);
				spinnerPort.setEnabled(false);
			}
		});
		buttonGroup.add(radioAutomatic);

		JButton btnPlay = new JButton("Play !");
		GridBagConstraints gbc_btnPlay = new GridBagConstraints();
		gbc_btnPlay.insets = new Insets(0, 0, 0, 5);
		gbc_btnPlay.gridx = 3;
		gbc_btnPlay.gridy = 2;
		panelGame.add(btnPlay, gbc_btnPlay);
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nickName = tfNickName.getText();

				new Thread() {
					public void run() {
						frame.setVisible(false);
						if (radioAutomatic.isSelected()) {
							startAutomatic(nickName);
						} else {
							int port = (Integer) spinnerPort.getValue();
							String address = tfAddress.getText();
							startNetwork(nickName, address, port);

						}
						frame.setVisible(true);
					}
				}.start();

			}

		});
	}

	protected JPanel getPanelNetwork() {
		return panelNetwork;
	}

	protected JSpinner getSpinnerPort() {
		return spinnerPort;
	}

	protected void startNetwork(String nickName, String address, int port) {
		try {
			GraphicNavalGrid g1 = new GraphicNavalGrid(10);
			g1.automaticPosition(new int[] { 2, 3, 3, 4, 5 });
			GraphicPlayer p1 = new GraphicPlayer("Jack", g1);

			Player p2 = new RemotePlayer(p1, InetAddress.getByName(address), port);
			p2.close();
			p1.close();
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(null, "Bad Address, please enter a valid adress or IP", "Bad Address",
					JOptionPane.ERROR_MESSAGE);
			;
			e.printStackTrace();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Network Error, check your network connection", "Network Error",
					JOptionPane.ERROR_MESSAGE);
			;
			e.printStackTrace();
		} catch (RuntimeException e) {
			if (e.getCause() instanceof EOFException) {
				JOptionPane.showMessageDialog(null, "The other player gives up!", "communication error",
						JOptionPane.ERROR_MESSAGE);

			}
		}
	}

	public static GraphicNavalGrid createGrid() {
		GraphicNavalGrid g1 = new GraphicNavalGrid(10);
		g1.automaticPosition(new int[] { 2, 3, 3, 4, 5 });
		return g1;
	}

	protected void startAutomatic(String nickName) {
		GraphicNavalGrid ng1 = createGrid();
		GraphicPlayer p1 = new GraphicPlayer(nickName, ng1);
		GraphicNavalGrid ng2 = createGrid();
		AutomaticPlayer p2 = new AutomaticPlayer("Robot", ng2);
		p1.playWith(p2);
		p1.close();
		p2.close();

	}
}
