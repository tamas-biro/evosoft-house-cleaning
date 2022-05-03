package robovacuuming;

public class RoomCoordinate {
	private int coordinateX;
	private int coordinateY;
	private boolean isObstacle;
	private boolean isRobovacuumHere; //For calculating new starting position for the new vacuuming cycle
	
	public RoomCoordinate(int coordinateX, int coordinateY) {
		this.coordinateX = coordinateX;
		this.coordinateY = coordinateY;
		this.isObstacle = false;
		this.isRobovacuumHere = false;
	}

	public int getCoordinateX() {
		return coordinateX;
	}

	public void setCoordinateX(int coordinateX) {
		this.coordinateX = coordinateX;
	}

	public int getCoordinateY() {
		return coordinateY;
	}

	public void setCoordinateY(int coordinateY) {
		this.coordinateY = coordinateY;
	}

	public boolean isObstacle() {
		return isObstacle;
	}

	public void setObstacle(boolean isObstacle) {
		this.isObstacle = isObstacle;
	}

	public boolean isRobovacuumHere() {
		return isRobovacuumHere;
	}

	public void setRobovacuumHere(boolean isRobovacuumHere) {
		this.isRobovacuumHere = isRobovacuumHere;
	}	
	
	
	
}
