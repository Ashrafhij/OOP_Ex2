package api;
import gameClient.util.Point3D;

public class GeoLocation implements geo_location {
	
	Point3D myPoint;
	
	public GeoLocation() {
		this.myPoint = new Point3D(0, 0, 0);
	}
	
	public GeoLocation(double x , double y , double z) {
		this.myPoint = new Point3D(x , y , z);
	}
	
	public GeoLocation(Point3D p) {
		this.myPoint = new Point3D(p);
	}
	
	public GeoLocation(geo_location g) {
		this.myPoint = new Point3D(g.x() , g.y() , g.z());
	}
	
	public GeoLocation(String pos) {
		this.myPoint = new Point3D(pos);
	}
	
	
	@Override
	public double x() {
		return this.myPoint.x();
	}

	@Override
	public double y() {
		return this.myPoint.y();
	}

	@Override
	public double z() {
		return this.myPoint.z();
	}

	@Override
	public double distance(geo_location g) {
		return this.myPoint.distance(g);
	}

}
