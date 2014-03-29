package entity;

import java.util.ArrayList;

public class LogEntity {
	
	private ArrayList<Node> openList;
	private ArrayList<Node> closeList;
	private Node sourceNode;
	private Node desNode;
	public ArrayList<Node> getOpenList() {
		return openList;
	}
	public void setOpenList(ArrayList<Node> openList) {
		this.openList = openList;
	}
	public ArrayList<Node> getCloseList() {
		return closeList;
	}
	public void setCloseList(ArrayList<Node> closeList) {
		this.closeList = closeList;
	}
	public Node getSourceNode() {
		return sourceNode;
	}
	public void setSourceNode(Node sourceNode) {
		this.sourceNode = sourceNode;
	}
	public Node getDesNode() {
		return desNode;
	}
	public void setDesNode(Node desNode) {
		this.desNode = desNode;
	}
	
	
	
}
