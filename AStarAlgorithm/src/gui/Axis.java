package gui;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import javax.swing.JComponent;

public class Axis extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int x, y;
	private int max;
	private int min;
	private int unit, viewSize;
	private int lengthUnit;
	private int Ox,Oy;

	public Axis() {
		// TODO Auto-generated constructor stub
	}
	
	
	public Axis(int max, int min, int unit, int x, int y) {
		super();
		this.max = max;
		this.min = min;
		this.unit = unit;
		this.x = x;
		this.y = y;
//		lengthUnit = (int) (viewSize / ((max - min) /( unit*Constance.RATE)));
		// repaint();
	}
	
	

	public int getOx() {
		return Ox;
	}

	public void setOx(int ox) {
		Ox = ox;
	}

	public int getOy() {
		return Oy;
	}

	public void setOy(int oy) {
		Oy = oy;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getUnit() {
		return unit;
	}

	public void setUnit(int unit) {
		this.unit = unit;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getLengthUnit() {
		return lengthUnit;
	}

	public void setLengthUnit(int lengthUnit) {
		this.lengthUnit = lengthUnit;
	}

	public int getLength() {
		return viewSize;
	}

	public void setLength(int length) {
		this.viewSize = length;
	}


	
	public void paint(Graphics g) {
		super.paint(g); // fixes the immediate problem.
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(1));
		Line2D lin = null;
		lin = new Line2D.Float(x, y+viewSize - 5, x + viewSize, y+viewSize  - 5);
		int a = this.viewSize / lengthUnit;

		int currentPoint = x;
		int currentValue = min;
		for (int i = 0; i < a; i++) {
			currentPoint += lengthUnit;
			currentValue += unit;
			Line2D lin2 = new Line2D.Float(currentPoint, viewSize , currentPoint, 4+viewSize);
			g2.draw(lin2);
			g2.drawString("" + currentValue, currentPoint - 10, 20+viewSize );
		}
		g2.draw(lin);
		
		Line2D liny = new Line2D.Float(x + 15, y, x + 15, y + viewSize);
		int ay = this.viewSize / lengthUnit;

		int currentPointy = y;
		int currentValuey = max;
		for (int i = 0; i < ay; i++) {
			Line2D lin2y = new Line2D.Float(x + 15, currentPointy, x + 20,
					currentPointy);
			g2.draw(lin2y);
			g2.drawString("" + currentValuey, x - 5, currentPointy);
			currentPointy += lengthUnit;
			currentValuey -= unit;
		}
		g2.draw(liny);
	}

}
