package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import entity.NDKPoint;
import entity.Neighborhood;
import entity.Node;
import entity.SingletonData;

public class MapView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextArea txtPathInfo, txtNodeLocation;
	private SingletonData data;
	private ArrayList<Node> pathAstar;
	private JComboBox cbStart, cbDestination;
	private JTable tblLog;
	private DefaultTableModel model;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					MapView frame = new MapView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MapView() {

		this.data = SingletonData.getData();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 904, 751);
		contentPane = new DrawPane();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		//
		// JLabel lblNewLabel = new JLabel("New label");
		// lblNewLabel.setBounds(214, 120, 46, 14);
		// contentPane.add(lblNewLabel);

		getContentPane().setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(509, 36, 323, 134);
		getContentPane().add(scrollPane);

		txtNodeLocation = new JTextArea();
		scrollPane.setViewportView(txtNodeLocation);
		txtNodeLocation.setToolTipText("V\u1ECB tr\u00ED c\u00E1c node");
		txtNodeLocation.setRows(8);
		txtNodeLocation.setLineWrap(true);
		txtNodeLocation.setText(data.getMapString().toString());

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(509, 206, 323, 266);
		getContentPane().add(scrollPane_1);

		txtPathInfo = new JTextArea();
		txtPathInfo.setText(data.getNeighborhoodString().toString());
		scrollPane_1.setViewportView(txtPathInfo);

		JLabel lblVTrCc = new JLabel("V\u1ECB tr\u00ED c\u00E1c node:");
		lblVTrCc.setBounds(509, 11, 164, 14);
		getContentPane().add(lblVTrCc);

		JLabel lblThngTinng = new JLabel(
				"Th\u00F4ng tin \u0111\u01B0\u1EDDng \u0111i");
		lblThngTinng.setBounds(507, 181, 166, 14);
		getContentPane().add(lblThngTinng);

		JButton btnSave = new JButton("save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String s = txtNodeLocation.getText();
				System.out.println(s);
				PrintWriter out;
				BufferedWriter writer = null;
				try {
					writer = new BufferedWriter(new FileWriter("mapInfo.txt"));
					writer.write(s);

				} catch (IOException e) {
				} finally {
					try {
						if (writer != null)
							writer.close();
					} catch (IOException e) {
					}
				}
				
				String s2 = txtPathInfo.getText();
				try {
					writer = new BufferedWriter(new FileWriter("neighborhood.txt"));
					writer.write(s2);

				} catch (IOException e) {
				} finally {
					try {
						if (writer != null)
							writer.close();
					} catch (IOException e) {
					}
				}
				data.reloadData();
				reloadGUIComponent();
				repaint();
				

			}
		});
		btnSave.setBounds(743, 483, 89, 23);
		getContentPane().add(btnSave);

		JLabel lblimu = new JLabel("Start point");
		lblimu.setBounds(26, 453, 68, 14);
		getContentPane().add(lblimu);

		JLabel lblimCui = new JLabel("Destination point");
		lblimCui.setBounds(261, 453, 83, 14);
		getContentPane().add(lblimCui);

		JButton btnTmngi = new JButton("Get direction");
		btnTmngi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Node start = (Node) cbStart.getSelectedItem();
				Node destination = (Node) cbDestination.getSelectedItem();
				while (model.getRowCount() > 0) {
					model.removeRow(0);
				}
				pathAstar = findPath(start, destination);
				StringBuffer path = new StringBuffer("");
				path.append(start.getName());
				for (int i = pathAstar.size() - 1; i >= 0; i--) {
					Node array_element = pathAstar.get(i);
					path.append(" -> " + array_element.getName());
				}
				txtresult.setText(path.toString());
				contentPane.repaint();
			}
		});
		btnTmngi.setBounds(84, 483, 112, 23);
		getContentPane().add(btnTmngi);

		cbStart = new JComboBox();
		cbStart.setBounds(84, 449, 145, 23);
		getContentPane().add(cbStart);

		cbDestination = new JComboBox();
		cbDestination.setBounds(354, 449, 145, 23);
		getContentPane().add(cbDestination);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(26, 522, 806, 179);
		getContentPane().add(scrollPane_2);

		tblLog = new JTable();
		scrollPane_2.setViewportView(tblLog);
		Vector<String> columnNames = new Vector<>();
		columnNames.add("#");
		columnNames.add("Node");
		columnNames.add("Open node");
		columnNames.add("Close node");

		model = new DefaultTableModel() {
			public Class getColumnClass(int columnIndex) {
				return String.class;
			}
		};
		model.setColumnIdentifiers(columnNames);
		tblLog.setModel(model);
		tblLog.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		txtresult = new JTextField();
		txtresult.setBounds(354, 484, 145, 20);
		getContentPane().add(txtresult);
		txtresult.setColumns(10);

		JLabel lblResult = new JLabel("Result");
		lblResult.setBounds(261, 487, 46, 14);
		getContentPane().add(lblResult);
		tblLog.getColumnModel().getColumn(0).setPreferredWidth(40);
		tblLog.getColumnModel().getColumn(1).setPreferredWidth(180);
		tblLog.getColumnModel().getColumn(2).setPreferredWidth(300);
		tblLog.getColumnModel().getColumn(3).setPreferredWidth(300);

		tblLog.setDefaultRenderer(String.class, new MultiLineCellRenderer());

		// drawAxis();

		reloadGUIComponent();

	}
	
	private void reloadGUIComponent(){
		ArrayList<Node> nodeList = data.getMapInfo();
		this.cbDestination.removeAllItems();
		this.cbStart.removeAllItems();
		for (int i = 0; i < nodeList.size(); i++) {
			Node node = nodeList.get(i);
			this.cbDestination.addItem(node);
			this.cbStart.addItem(node);
			
			LabelImageScalable lbl = new LabelImageScalable();
			Point p = realLocationToMapPoint(node.getLocation());
			lbl.setBounds(p.x, p.y, 20, 20);
			lbl.setImage("node.png");

			JLabel lblName = new JLabel(node.getName());
			lblName.setBounds(p.x - 20, p.y + 10, 100, 40);
			this.contentPane.add(lblName);

			this.contentPane.add(lbl);
			node.setGuiComponent(lbl);
			nodeList.set(i, node);
			
		}
		data.setMapInfo(nodeList);
	}

	private Point realLocationToMapPoint(NDKPoint realPoint) {
		Point p = new Point();
		NDKPoint pointtmp = new NDKPoint(realPoint.getX()
				- data.getAxisLocation().getX(), realPoint.getY()
				- data.getAxisLocation().getY());
		p.x = (int) (pointtmp.getX() * data.getRatio());
		p.y = (int) (pointtmp.getY() * data.getRatio());
		return p;
	}

	//
	// public void drawAxis() {
	// Axis axisY = new Axis(realSize, 0, axisUnit, mapSize, locationAxis.x,
	// locationAxis.y - mapSize);
	// axisY.setBounds(5, 5, 692, 428);
	// contentPane.add(axisY);
	// }

	class DrawPane extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = -5150976278886835709L;

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(2));
			g2.setColor(Color.BLACK);
			ArrayList<Node> nodes = data.getMapInfo();
			for (int i = 0; i < nodes.size(); i++) {
				Node node = nodes.get(i);
				Point p1 = node.getGuiComponent().getLocation();
				p1.x = p1.x + 10;
				p1.y = p1.y + 10;
				ArrayList<Neighborhood> neighborhoods = node.getNeighborhood();
				for (Neighborhood neighborhood : neighborhoods) {
					if (neighborhood.getId() < i)
						continue;
					Node neiNode = nodes.get(neighborhood.getId());
					Point p2 = neiNode.getGuiComponent().getLocation();
					p2.x = p2.x + 10;
					p2.y = p2.y + 10;
					Line2D lin = new Line2D.Float(p1, p2);

					g2.draw(lin);
					g2.drawString("" + neighborhood.getLength(),
							(p1.x + p2.x) / 2 + 7, (p1.y + p2.y) / 2 + 7);

				}
			}

			// paint path
			if (pathAstar != null) {
				for (int i = 0; i < pathAstar.size(); i++) {
					Node node = pathAstar.get(i);
					g2.setColor(Color.red);
					Point p1 = node.getGuiComponent().getLocation();
					p1.x = p1.x + 10;
					p1.y = p1.y + 10;

					Node node2 = node.getPreviousNode();
					Point p2 = node2.getGuiComponent().getLocation();
					p2.x = p2.x + 10;
					p2.y = p2.y + 10;
					Line2D lin = new Line2D.Float(p1, p2);
					g2.draw(lin);

					System.out.println("path :" + node.getName() + " "
							+ node.getPreviousNode().getName());
					if (node.getGuiComponent() == null) {
						return;
					}

					node = node2;

				}
			}
		}
	}

	/** list containing nodes not visited but adjacent to visited nodes. */
	private ArrayList<Node> openList;
	/** list containing nodes already visited/taken care of. */
	private ArrayList<Node> closedList;

	private Node lowestFInOpen() {
		// TODO currently, this is done by going through the whole openList!
		Node cheapest = openList.get(0);
		for (int i = 0; i < openList.size(); i++) {
			if (openList.get(i).getfCost() < cheapest.getfCost()) {
				cheapest = openList.get(i);
			}
		}
		return cheapest;
	}

	public Double heuristic(Node nodeA, Node nodeB) {
		return nodeA.getLocation().distance(nodeB.getLocation());
	}

	int maxLine;
	private JTextField txtresult;

	public ArrayList<Node> findPath(Node start, Node finish) {
		System.out.println("start :" + start.getName() + "end :"
				+ finish.getName());
		// TODO check input
		openList = new ArrayList<>();
		closedList = new ArrayList<>();
		start.setPreviousNode(null);
		start.setActualCost(0);
		start.setEstimateCost(this.heuristic(start, finish));
		openList.add(start); // add starting node to open list
		int step = 0;
		boolean done = false;
		Node current = null;
		while (!done) {
			step++;
			current = lowestFInOpen();

			closedList.add(current); // add current node to closed list
			openList.remove(current); // delete current node from open list
			System.out.println("check name :" + current.getName());
			if (current.getName().equalsIgnoreCase(finish.getName())) { // found
																		// goal
				ArrayList<Node> path = calcPath(start, finish);
				return path;
			}

			ArrayList<Neighborhood> neighborhoods = current.getNeighborhoods();
			for (Neighborhood neighborhood : neighborhoods) {
				int nodeId = neighborhood.getId();
				Node neiNode = data.getMapInfo().get(nodeId);

				if (!openList.contains(neiNode)
						&& !closedList.contains(neiNode)) { // node is not in
															// openList
					neiNode.setPreviousNode(current);// set current node as
														// previous for this
														// node
					neiNode.setActualCost(current.getActualCost()
							+ neighborhood.getLength());// set g costs of this
														// node (costs from
														// start to this node)
					neiNode.setEstimateCost(this.heuristic(neiNode, finish));// set
																				// h
																				// costs
																				// of
																				// this
																				// node
																				// (estimated
																				// costs
																				// to
																				// goal)
					System.out.println("add to openlist :" + neiNode.getName());
					openList.add(neiNode); // add node to openList
				} else if (openList.contains(neiNode)) { // node is in openList
					if (neiNode.getActualCost() > current.getActualCost()
							+ neighborhood.getLength()) { // costs from current
															// node are cheaper
															// than previous
															// costs
						neiNode.setPreviousNode(current);
						; // set current node as previous for this node
						neiNode.setActualCost(current.getActualCost()
								+ neighborhood.getLength()); // set g costs of
																// this node
																// (costs from
																// start to this
																// node)
					}
				}
			}
			if (openList.isEmpty()) { // no path exists
				return new ArrayList<>(); // return empty list
			}
			Vector<String> rows = new Vector<>();
			rows.add("" + step);
			if (current.getPreviousNode() != null) {
				rows.add("" + current.getName() + " (previous :"
						+ current.getPreviousNode().getName() + ")");
			} else {
				rows.add("" + current.getName());

			}
			StringBuffer openString = new StringBuffer("");
			StringBuffer closeString = new StringBuffer("");
			int line1 = 0, line2 = 0;
			for (Node node : openList) {
				line1++;
				openString.append("" + node.getName() + " g="
						+ node.getActualCost() + "h= "
						+ String.format("%,.2f", node.getEstimateCost()));
				openString.append("\n");
			}

			for (Node node : closedList) {
				line2++;
				closeString.append("" + node.getName() + " g="
						+ node.getActualCost() + " h= "
						+ String.format("%,.2f", node.getEstimateCost()));
				closeString.append("\n");
			}
			maxLine = Math.max(maxLine, line2);
			maxLine = Math.max(maxLine, line1);

			this.tblLog.setRowHeight(20 * maxLine);

			rows.add(openString.toString());
			rows.add(closeString.toString());
			this.model.addRow(rows);
		}

		return null; // unreachable

	}

	class MultiLineCellRenderer extends JTextArea implements TableCellRenderer {

		public MultiLineCellRenderer() {
			setLineWrap(true);
			setWrapStyleWord(true);
			setOpaque(true);
		}

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			if (isSelected) {
				setForeground(table.getSelectionForeground());
				setBackground(table.getSelectionBackground());
			} else {
				setForeground(table.getForeground());
				setBackground(table.getBackground());
			}
			setFont(table.getFont());
			if (hasFocus) {
				setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
				if (table.isCellEditable(row, column)) {
					setForeground(UIManager
							.getColor("Table.focusCellForeground"));
					setBackground(UIManager
							.getColor("Table.focusCellBackground"));
				}
			} else {
				setBorder(new EmptyBorder(1, 2, 1, 2));
			}
			setText((value == null) ? "" : value.toString());
			return this;
		}
	}

	private ArrayList<Node> calcPath(Node start, Node goal) {
		// goal to start, this method will result in an infinite loop!)
		ArrayList<Node> path = new ArrayList<>();

		Node curr = goal;
		boolean done = false;
		while (!done) {
			path.add(curr);
			curr = curr.getPreviousNode();
			if (curr.equals(start)) {
				done = true;
			}
		}
		return path;
	}
}
