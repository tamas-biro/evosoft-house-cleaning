package robovacuuming;

import java.util.LinkedList;

public class BasicRoom extends Room {
	final int dimensionX;
	final int dimensionY;
	final LinkedList<RoomCoordinate> obstacleList;
	
	public BasicRoom(int dimensionX, int dimensionY, LinkedList<RoomCoordinate> obstacleList) {
		this.dimensionX = dimensionX;
		this.dimensionY = dimensionY;
		this.obstacleList = obstacleList;
	}
	
	public boolean isCoordinateObstacle(int coordinateX, int coordinateY) {
		if(coordinateX > dimensionX || coordinateY > dimensionY || coordinateX < 1 || coordinateY < 1) {
			return true;
		}
		
		for(RoomCoordinate listElement : obstacleList) {
			if(listElement.getCoordinateX() == coordinateX && listElement.getCoordinateY() == coordinateY) {
				return true;
			}
		}
		
		return false;
	}
	
}
