package entity;

import gui.LabelImageScalable;

import java.util.ArrayList;

public class Node {
	// real location 
	private NDKPoint location;
	private String name;
	private LabelImageScalable guiComponent;
	private double estimateCost;
	private double actualCost;
	private Node previousNode;
	private boolean isOpen;
	
	private ArrayList<Neighborhood> neighborhoods;
	
	public Node() {
		neighborhoods = new ArrayList<>();
	}
	
	public Node(double x, double y, String name) {
		super();
		location = new NDKPoint(x, y);
		this.name = name;
		neighborhoods = new ArrayList<>();
	}
	
	public void addNeighborhood(Neighborhood n){
		this.neighborhoods.add(n);
	}
	
	public ArrayList<Neighborhood> getNeighborhood() {
		return neighborhoods;
	}

	public void setNeighborhood(ArrayList<Neighborhood> neighborhood) {
		this.neighborhoods = neighborhood;
	}

	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public NDKPoint getLocation() {
		return location;
	}

	public void setLocation(NDKPoint location) {
		this.location = location;
	}

	public LabelImageScalable getGuiComponent() {
		return guiComponent;
	}

	public void setGuiComponent(LabelImageScalable guiComponent) {
		this.guiComponent = guiComponent;
	}

	public Neighborhood getNearstNeighborhood(){
		Neighborhood res=null;
		double min=Double.MAX_VALUE;
		for (Neighborhood ne : this.neighborhoods) {
			if(ne.getLength()<min){
				res = ne;
				min = ne.getLength();
			}
		}
		return res;
	}
	
	public double getfCost(){
		return estimateCost+actualCost;
	}

	public double getEstimateCost() {
		return estimateCost;
	}

	public void setEstimateCost(double estimateCost) {
		this.estimateCost = estimateCost;
	}

	public double getActualCost() {
		return actualCost;
	}

	public void setActualCost(double actualCost) {
		this.actualCost = actualCost;
	}

	public Node getPreviousNode() {
		return previousNode;
	}

	public void setPreviousNode(Node previousNode) {
		this.previousNode = previousNode;
	}

	public ArrayList<Neighborhood> getNeighborhoods() {
		return neighborhoods;
	}

	public void setNeighborhoods(ArrayList<Neighborhood> neighborhoods) {
		this.neighborhoods = neighborhoods;
	}

	public boolean isOpen() {
		return isOpen;
	}

	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
}
