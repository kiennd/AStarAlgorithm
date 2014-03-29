package entity;

public class NDKPoint {
	double x,y;
	public double distance(NDKPoint pointB){
		double R = 6371000; // m
		double dLat = Math.toRadians(x-pointB.x);
		double dLon = Math.toRadians(y-pointB.y);
		double lat1 = Math.toRadians(x);
		double lat2 = Math.toRadians(pointB.x);

		double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
		        Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat1) * Math.cos(lat2); 
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
		double d = R * c;
		return d;
//		return Math.sqrt((this.x-pointB.x)*(this.x-pointB.x)+(this.y-pointB.y)*(this.y-pointB.y));
	}
	
	
	public NDKPoint(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}


	public static double distance(NDKPoint A,NDKPoint B){
		return A.distance(B);
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	

}
