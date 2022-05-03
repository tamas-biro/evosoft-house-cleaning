package robovacuuming;

import java.util.LinkedList;

public class AdvancedRoom extends Room {
	private final int dimensionX;
	private final int dimensionY;
	private final LinkedList<RoomCoordinate> obstacleList;
	private RoomCoordinate[][] roomMatrix;
	
	public AdvancedRoom(int dimensionX, int dimensionY, LinkedList<RoomCoordinate> obstacleList) {
		this.dimensionX = dimensionX;
		this.dimensionY = dimensionY;
		this.obstacleList = obstacleList;
		this.roomMatrix = new RoomCoordinate[dimensionX][dimensionY];
		fillRoomMatrix();
	}
	
	//Fill the Array which represents the room with RoomCoordinate objects and marking the obstacles 
	public void fillRoomMatrix() {
		for(int coordinateX = 0; coordinateX < getRoomMatrix().length; coordinateX++) {
			for(int coordinateY = 0; coordinateY < getRoomMatrix()[0].length; coordinateY++) {
				getRoomMatrix()[coordinateX][coordinateY] = new RoomCoordinate(coordinateX, coordinateY);
				
				//Check if obstacle and turn the coordinate's obstacle flag
				for(RoomCoordinate obstacleElement : obstacleList) {
					if(obstacleElement.getCoordinateX() == coordinateX && obstacleElement.getCoordinateY() == coordinateY) {
						getRoomMatrix()[coordinateX][coordinateY].setObstacle(true);
					}
				}
			}
		}
	}
	
	//Returns true if the given coordinate is obstacle or out of the room
	public boolean isCoordinateObstacle(int coordinateX, int coordinateY) {
		if(coordinateX >= dimensionX || coordinateY >= dimensionY || coordinateX < 0 || coordinateY < 0) {
			return true;
		}
		else if(roomMatrix[coordinateX][coordinateY].isObstacle()) {
			return true;
		}
		else {
			return false;
		}
	}

	public RoomCoordinate[][] getRoomMatrix() {
		return roomMatrix;
	}

	public void setRoomMatrix(RoomCoordinate[][] roomMatrix) {
		this.roomMatrix = roomMatrix;
	}
	
	
	
	
}
