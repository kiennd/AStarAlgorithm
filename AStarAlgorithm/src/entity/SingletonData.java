package entity;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

public class SingletonData {
	private ArrayList<Node> mapInfo;
	private static SingletonData instance;
	private NDKPoint axisLocation;
	private long NDKMax; // max of all cordinate content;
	private double ratio; // ratio between real cordinate value vs map value
	private Vector<LogEntity> logData;
	private StringBuffer mapString;
	private StringBuffer neighborhoodString;
	
	private SingletonData() {
		mapInfo = new ArrayList<>();
		axisLocation = new NDKPoint(20.939951, 105.772934);
		ratio = 4000;
		logData = new Vector<>();
		mapString = new StringBuffer("");
		neighborhoodString =  new StringBuffer("");
		reloadData();
	}
	
	

	public void reloadData() {
		// read node info
		try (BufferedReader br = new BufferedReader(new FileReader(
				"mapinfo.txt"))) {
			mapString = new StringBuffer("");
			neighborhoodString = new StringBuffer("");
			this.mapInfo.clear();
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				System.out.println(sCurrentLine);
				String[] lineContent = sCurrentLine.split(" ");
				String name = lineContent[0];
				double x = Double.parseDouble(lineContent[1]);
				double y = Double.parseDouble(lineContent[2]);
				Node node = new Node(x, y, name);
				mapInfo.add(node);
				mapString.append(sCurrentLine+"\n");
			}

			for (int i = 0; i < mapInfo.size(); i++) {
				Node nodei = mapInfo.get(i);
				for (int j = 0; j < mapInfo.size(); j++) {
					Node nodej = mapInfo.get(j);
					System.out.print(nodei.getLocation().distance(
							nodej.getLocation())
							+ " ");
				}
				System.out.println("");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		// read neighborhood
		try (BufferedReader br = new BufferedReader(new FileReader(
				"neighborhood.txt"))) {
			String sCurrentLine;
			int i = 0;
			while ((sCurrentLine = br.readLine()) != null) {
				System.out.println(sCurrentLine);
				String[] lineContent = sCurrentLine.split(" ");
				Node curNode1 = mapInfo.get(i);
				for (int j = 0; j < lineContent.length; j++) {
					String s = lineContent[j];
					if (!s.equalsIgnoreCase("0")) {
						Neighborhood neighborhood = new Neighborhood();
						neighborhood.setId(j);
						neighborhood.setLength(Double.parseDouble(s));
						curNode1.addNeighborhood(neighborhood);
					}
				}
				mapInfo.set(i, curNode1);
				i++;
				neighborhoodString.append(sCurrentLine+"\n");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("load done");
	}

	public static SingletonData getData() {
		if (instance == null) {
			instance = new SingletonData();
		}
		return instance;
	}
	
	public StringBuffer getMapString() {
		return mapString;
	}



	public void setMapString(StringBuffer mapString) {
		this.mapString = mapString;
	}



	public StringBuffer getNeighborhoodString() {
		return neighborhoodString;
	}



	public void setNeighborhoodString(StringBuffer neighborhoodString) {
		this.neighborhoodString = neighborhoodString;
	}



	public Vector<LogEntity> getLogData() {
		return logData;
	}



	public void setLogData(Vector<LogEntity> logData) {
		this.logData = logData;
	}



	public void addNode(Node node) {
		this.mapInfo.add(node);
	}

	public ArrayList<Node> getMapInfo() {

		return this.mapInfo;
	}

	public void setMapInfo(ArrayList<Node> mapInfo) {
		this.mapInfo = mapInfo;
	}

	public NDKPoint getAxisLocation() {
		return axisLocation;
	}

	public void setAxisLocation(NDKPoint axisLocation) {
		this.axisLocation = axisLocation;
	}

	public long getNDKMax() {
		return NDKMax;
	}

	public void setNDKMax(long nDKMax) {
		NDKMax = nDKMax;
	}

	public void setRatio(int ratio) {
		this.ratio = ratio;
	}

	public double getRatio() {
		return ratio;
	}

	public void setRatio(double ratio) {
		this.ratio = ratio;
	}

}
