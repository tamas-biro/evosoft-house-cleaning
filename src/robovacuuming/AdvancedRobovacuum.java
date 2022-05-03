package robovacuuming;

import java.util.LinkedList;

public class AdvancedRobovacuum extends Robovacuum {
	private AdvancedRoom room;
	
	private int currentCoordinateX;
	private int currentCoordinateY;
	private LinkedList<RoomCoordinate> routeCoordinateList;
	private Direction currentDirection;
	private Direction previousDirection;
	
	public AdvancedRobovacuum(AdvancedRoom room, int startingCoordinateX, int startingCoordinateY) {
		this.room = room;
		this.currentCoordinateX = startingCoordinateX;
		this.currentCoordinateY = startingCoordinateY;
		this.routeCoordinateList = new LinkedList<RoomCoordinate>();
		this.routeCoordinateList.add(new RoomCoordinate(startingCoordinateX,startingCoordinateY));
		this.currentDirection = Direction.SOUTH;
		this.previousDirection = Direction.SOUTH;
	}
	
	//Adding coordinate to route list
	public void addRouteElement(int coordinateX, int coordinateY) {
		if(!isCoordinateInRouteList(coordinateX, coordinateY)) {
			getRouteCoordinateList().add(new RoomCoordinate(coordinateX, coordinateY));
		}
	}

	//Checking if coordinate has been cleaned in this cycle
	public boolean isCoordinateInRouteList(int coordinateX, int coordinateY) {
		for(RoomCoordinate listElement : getRouteCoordinateList()) {
			if(listElement.getCoordinateX() == coordinateX && listElement.getCoordinateY() == coordinateY) {
				return true;
			}
		}
		return false;
	}
	
	//TODO combining movements
	//Return true if moving in the given direction was possible, otherwise false
	public boolean move(Direction direction) {
		switch(direction) {
			case SOUTH :
				if(!room.isCoordinateObstacle(getCurrentCoordinateX(), getCurrentCoordinateY()+1)) { //Check for obstacle
					setCurrentCoordinateY(getCurrentCoordinateY()+1); //Move by setting new coordinate
					addRouteElement(getCurrentCoordinateX(), getCurrentCoordinateY()); //Adding new coordinate to route
					updateDirectionData(direction);
					return true;
				}
				else {
					return false;
				}
			case EAST :
				if(!room.isCoordinateObstacle(getCurrentCoordinateX()+1, getCurrentCoordinateY())) { 
					setCurrentCoordinateX(getCurrentCoordinateX()+1);
					addRouteElement(getCurrentCoordinateX(), getCurrentCoordinateY());
					updateDirectionData(direction);
					return true;
				}
				else {
					return false;
				}
			case NORTH :
				if(!room.isCoordinateObstacle(getCurrentCoordinateX(), getCurrentCoordinateY()-1)) { 
					setCurrentCoordinateY(getCurrentCoordinateY()-1);
					addRouteElement(getCurrentCoordinateX(), getCurrentCoordinateY());
					updateDirectionData(direction);
					return true;
				}
				else {
					return false;
				}
			case WEST :
				if(!room.isCoordinateObstacle(getCurrentCoordinateX()-1, getCurrentCoordinateY())) { 
					setCurrentCoordinateX(getCurrentCoordinateX()-1);
					addRouteElement(getCurrentCoordinateX(), getCurrentCoordinateY());
					updateDirectionData(direction);
					return true;
				}
				else {
					return false;
				}
			default :
				return false;
		}
	}
	
	//Update facing direction and save previous
	private void updateDirectionData(Direction newCurrentDirection) {
		setPreviousDirection(getCurrentDirection());
		setCurrentDirection(newCurrentDirection);
	}
	
	//Rotate Array matrix representing the room, simulating shift in  
	public RoomCoordinate[][] rotateRoomMatrixCounterClockwise(RoomCoordinate[][] oldRoomMatrix) {
		RoomCoordinate[][] newRoomMatrix = new RoomCoordinate[oldRoomMatrix.length][oldRoomMatrix[0].length];
	    int newCoordinateX = 0;
	    int newCoordinateY = 0;
	    
	    for (int oldCoordinateX = oldRoomMatrix.length - 1; oldCoordinateX >= 0; oldCoordinateX--)
	    {
	        newCoordinateX = 0;
	        for (int oldCoordinateY = 0; oldCoordinateY < oldRoomMatrix[0].length; oldCoordinateY++)
	        {
	            newRoomMatrix[newCoordinateX][newCoordinateY] = oldRoomMatrix[oldCoordinateX][oldCoordinateY];
	            newCoordinateX++;
	        }
	        newCoordinateY++;
	    }
	    setCurrentCoordinateX(getCurrentCoordinateX());
	    return newRoomMatrix;
	}
	
	//Calculating the new starting coordinate after matrix rotation
	public void setNewStartingCoordinates() {
		//Iterating through Matrix
		for(int coordinateX = 0; coordinateX < getRoom().getRoomMatrix().length; coordinateX++) {
			for(int coordinateY = 0; coordinateY < getRoom().getRoomMatrix()[0].length; coordinateY++) {
				//Check if this is the robot's current position
				if(getRoom().getRoomMatrix()[coordinateX][coordinateY].isRobovacuumHere()) {
					setCurrentCoordinateX(coordinateX);
					setCurrentCoordinateY(coordinateY);
					setCurrentDirection(Direction.SOUTH);
					
					getRoom().getRoomMatrix()[coordinateX][coordinateY].setRobovacuumHere(false);
				}
			}
		}
	}

	//------------------Getters and Setters------------------
	
	public int getCurrentCoordinateX() {
		return currentCoordinateX;
	}

	

	public void setCurrentCoordinateX(int currentCoordinateX) {
		this.currentCoordinateX = currentCoordinateX;
	}

	public int getCurrentCoordinateY() {
		return currentCoordinateY;
	}

	public void setCurrentCoordinateY(int currentCoordinateY) {
		this.currentCoordinateY = currentCoordinateY;
	}

	public Direction getCurrentDirection() {
		return currentDirection;
	}

	public void setCurrentDirection(Direction currentDirection) {
		this.currentDirection = currentDirection;
	}

	public Direction getPreviousDirection() {
		return previousDirection;
	}
	
	public void setPreviousDirection(Direction previousDirection) {
		this.previousDirection = previousDirection;
	}	


	public AdvancedRoom getRoom() {
		return room;
	}

	public LinkedList<RoomCoordinate> getRouteCoordinateList() {
		return routeCoordinateList;
	}
	
	public void setRouteCoordinateList(LinkedList<RoomCoordinate> routeCoordinateList) {
		this.routeCoordinateList = routeCoordinateList;
	}

}
