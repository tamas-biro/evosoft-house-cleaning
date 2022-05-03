package robovacuuming;

import java.util.LinkedList;
import java.util.Stack;

public class BasicRobovacuum extends Robovacuum {
	private int currentCoordinateX;
	private int currentCoordinateY;
	private int roomDimensionX;
	private int roomDimensionY;
	private RoomCoordinate currentObstacleSouthCorner;
	private RoomCoordinate currentObstacleNorthCorner;
	private LinkedList<RoomCoordinate> obstacleOutlineList;
	private Stack<RoomCoordinate> obstacleSouthCornerStack;
	private Stack<RoomCoordinate> obstacleNorthCornerStack;
	private Direction currentDirection;
	private Direction previousDirection;
	private BasicRoom room;
	private boolean isInSouthDetouring;
	private boolean isInNorthDetouring;
	private boolean isDoneDimensionDetermining;
	private boolean isTrapped;
	private boolean isDone;
	
	public BasicRobovacuum(BasicRoom room) {
		this.room = room;
		this.currentCoordinateX = 1;
		this.currentCoordinateY = 1;
		this.currentObstacleNorthCorner = new RoomCoordinate(0, 0);
		this.currentObstacleSouthCorner = new RoomCoordinate(0, 0);
		this.obstacleOutlineList = new LinkedList<RoomCoordinate>();
		
		this.obstacleSouthCornerStack = new Stack<RoomCoordinate>();
		this.obstacleSouthCornerStack.add(new RoomCoordinate(0,0));
		this.obstacleNorthCornerStack = new Stack<RoomCoordinate>();
		this.obstacleNorthCornerStack.add(new RoomCoordinate(0,0));
		
		this.currentDirection = Direction.SOUTH;
		this.previousDirection = Direction.SOUTH;
		this.isInSouthDetouring = false;
		this.isInNorthDetouring = false;
		this.isDoneDimensionDetermining = false;
		this.isTrapped = false;
		this.isDone = false;
	}
	
	public void startVacuuming() {
		while(isDone != true) {
			System.out.println("X: "+getCurrentCoordinateX()+", Y: "+getCurrentCoordinateY()+", Dir: "+getCurrentDirection().toString());
			moveForward();
		}
	}
	
	public void searchWalls() {
		while(!isDoneDimensionDetermining) {
			System.out.println("X: "+getCurrentCoordinateX()+", Y: "+getCurrentCoordinateY()+", Dir: "+getCurrentDirection().toString());
			continueRoomDimensionDetermining();
			if(getCurrentCoordinateX() == 1 && getCurrentCoordinateY() == 1) {
				setDoneDimensionDetermining(true);
			}
		}
		setCurrentDirection(Direction.SOUTH);
		System.out.println(getRoomDimensionX()+", "+getRoomDimensionY());
	}
	
	private void continueRoomDimensionDetermining() {
		switch(currentDirection) {
			case SOUTH :
				if(moveSouth()) {
				}
				else {
					if(moveEast()) {
					}
					else {
						moveNorth();
					}
				}
				break;
			case EAST :
				if(moveSouth()) {
				}
				else {
					if(moveEast()) {
					}
					else {
						if(moveNorth()) {
						}
						else {
							moveWest();
						}
					}
				}
				break;
			case NORTH :
				if(moveEast()) {
				}
				else {
					if(moveNorth()) {
					}
					else {
						if (moveWest()) {
						}
						else {
							moveSouth();
						}
					}
				}
				break;
			case WEST :
				if(moveNorth()) {
				}
				else {
					if(moveWest()) {
					}
					else {
						if(moveEast()) {
						}
						else {
							moveSouth();
						}
					}
				}
				break;
		}
		if(getCurrentCoordinateX() > getRoomDimensionX()) {
			setRoomDimensionX(getRoomDimensionX() + 1);
		}
		if(getCurrentCoordinateY() > getRoomDimensionY()) {
			setRoomDimensionY(getRoomDimensionY() + 1);
		}
	}
	
	private void moveForward() {
		switch(currentDirection) {
			case SOUTH :
				if(getCurrentCoordinateY() <= getObstacleNorthCornerStack().peek().getCoordinateY() || !isInNorthDetouring() ) {
					if(moveSouth()) {
					}
					else {
						if(!isInNorthDetouring() && !isCoordinateInOutlineList(getCurrentCoordinateX(), getCurrentCoordinateY()) && getCurrentCoordinateY() < getRoomDimensionY() && getCurrentCoordinateY() != getObstacleNorthCornerStack().peek().getCoordinateY()) {
							//getCurrentObstacleNorthCorner().setCoordinateX(getCurrentCoordinateX());
							//getCurrentObstacleNorthCorner().setCoordinateY(getCurrentCoordinateY());
							getObstacleNorthCornerStack().push(new RoomCoordinate(getCurrentCoordinateX(), getCurrentCoordinateY()));
							setInNorthDetouring(true);
						}
						addObsticleOutlineElement(getCurrentCoordinateX(), getCurrentCoordinateY());
						
						if(moveEast()) {
						}
						else {
							// TODO
							if(getCurrentCoordinateX() == getRoomDimensionX()) {
								setInNorthDetouring(false);
							}
							moveNorth();
						}
					}
				}
				else if(!isCoordinateInOutlineList(getCurrentCoordinateX(), getCurrentCoordinateY())) {
					if(moveWest()) {
					}
					else {
						if(moveSouth()) {
						}
						else {
							if(moveEast()) {
							}
							else {
								moveNorth();
							}
						}
					}
				}
				else {
					if(moveSouth()) {
					}
					else {
						if(moveEast()) {
						}
						else {
							moveNorth();
						}
					}
				}
				break;
			case EAST :
				if(getPreviousDirection().equals(Direction.SOUTH)) {
					if(moveNorth()) {
					}
					else {
						if(moveEast()) {
						}
						else {
							moveWest();
						}
					}
				}
				else {
					if(moveSouth()) {						
					}
					else {
						if(moveNorth()) {
						}
						else {
							moveEast();
						}
					}
				}
				break;
			case NORTH :
				if(getCurrentCoordinateY() >= getObstacleSouthCornerStack().peek().getCoordinateY() || !isInSouthDetouring() ) {
					if(moveNorth()) {
						setTrapped(false);
					}
					else {
						if((!isInSouthDetouring() || getCurrentCoordinateY() != getObstacleSouthCornerStack().peek().getCoordinateY()) && !isCoordinateInOutlineList(getCurrentCoordinateX(), getCurrentCoordinateY()) && getCurrentCoordinateY() > 1) {
							//getCurrentObstacleSouthCorner().setCoordinateX(getCurrentCoordinateX());
							//getCurrentObstacleSouthCorner().setCoordinateY(getCurrentCoordinateY());
							getObstacleSouthCornerStack().push(new RoomCoordinate(getCurrentCoordinateX(), getCurrentCoordinateY()));
							setInSouthDetouring(true);
						}
						addObsticleOutlineElement(getCurrentCoordinateX(), getCurrentCoordinateY());
						if(moveEast()) {
						}
						else {
							setTrapped(true);
							if(moveWest()) {
							}
							else {
								moveSouth();
							}
						}
					}
				}
				else if(!isCoordinateInOutlineList(getCurrentCoordinateX(), getCurrentCoordinateY())) {
					if(moveWest()) {
					}
					else {
						if(moveNorth()) {
						}
						else {
							if(moveEast()) {
							}
							else {
								moveSouth();
							}
						}
					}
				}
				else {
					if(moveNorth()) {
					}
					else {
						if(moveEast()) {
						}
						else {
							if(moveWest()) {
								
							}
							else {
								moveSouth();
							}
						}
					}
				}
				
				break;
			case WEST :
				if(getCurrentCoordinateX() > getObstacleNorthCornerStack().peek().getCoordinateX() && isInNorthDetouring()) {
					if(moveWest()) {
						
					}
					else {
						if(moveSouth()) {
						}
						else {
							moveEast();
						}
					}
				}
				else if(getCurrentCoordinateX() > getObstacleSouthCornerStack().peek().getCoordinateX() && isInSouthDetouring()) {
					if(moveWest()) {
						
					}
					else {
						if(moveNorth()) {
						}
						else {
							moveEast();
						}
					}
				}
				else if (!isTrapped()) {
					//setInSouthDetouring(false);
					//setInNorthDetouring(false);
					getObstacleSouthCornerStack().pop();
					if(moveSouth()) {
					}
					else {
						if(moveNorth()) {
						}
						else {
							moveEast();
						}
					}
					
				}
				else {
					if(isInSouthDetouring()) {
						if(moveNorth()) {
							setInSouthDetouring(false);
						}
						else {
							moveWest();
						}
					}
					else if(isInNorthDetouring()) {
						if(moveSouth()) {
							setInNorthDetouring(false);
							getObstacleNorthCornerStack().pop();
						}
						else {
							moveWest();
						}
					}
				}
				break;
		}
	}
	
	private void addObsticleOutlineElement(int coordinateX, int coordinateY) {
		if(!isCoordinateInOutlineList(coordinateX, coordinateY)) {
			getObstacleOutlineList().add(new RoomCoordinate(coordinateX, coordinateY));
		}
	}

	private boolean isCoordinateInOutlineList(int coordinateX, int coordinateY) {
		for(RoomCoordinate listElement : getObstacleOutlineList()) {
			if(listElement.getCoordinateX() == coordinateX && listElement.getCoordinateY() == coordinateY) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean move(Direction direction) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean moveSouth() {
		if(!room.isCoordinateObstacle(getCurrentCoordinateX(), getCurrentCoordinateY()+1)) {
			setCurrentCoordinateY(getCurrentCoordinateY()+1);
			updateDirectionData(Direction.SOUTH);
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean moveEast() {
		if(!room.isCoordinateObstacle(getCurrentCoordinateX()+1, getCurrentCoordinateY())) {
			setCurrentCoordinateX(getCurrentCoordinateX()+1);
			updateDirectionData(Direction.EAST);
			return true;
		}
		else {
			addObsticleOutlineElement(getCurrentCoordinateX(), getCurrentCoordinateY());
			return false;
		}
	}
	
	public boolean moveNorth() {
		if(!room.isCoordinateObstacle(getCurrentCoordinateX(), getCurrentCoordinateY()-1)) {
			setCurrentCoordinateY(getCurrentCoordinateY()-1);
			updateDirectionData(Direction.NORTH);
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean moveWest() {
		if(!room.isCoordinateObstacle(getCurrentCoordinateX()-1, getCurrentCoordinateY())) {
			setCurrentCoordinateX(getCurrentCoordinateX()-1);
			updateDirectionData(Direction.WEST);
			return true;
		}
		else {
			addObsticleOutlineElement(getCurrentCoordinateX(), getCurrentCoordinateY());
			return false;
		}
	}
	
	private void updateDirectionData(Direction newCurrentDirection) {
		setPreviousDirection(getCurrentDirection());
		setCurrentDirection(newCurrentDirection);
	}

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

	public boolean isInSouthDetouring() {
		return isInSouthDetouring;
	}

	public void setInSouthDetouring(boolean isInSouthDetouring) {
		this.isInSouthDetouring = isInSouthDetouring;
	}

	public boolean isInNorthDetouring() {
		return isInNorthDetouring;
	}

	public void setInNorthDetouring(boolean isInNorthDetouring) {
		this.isInNorthDetouring = isInNorthDetouring;
	}

	public boolean isDone() {
		return isDone;
	}

	public void setDone(boolean isDone) {
		this.isDone = isDone;
	}

	public BasicRoom getRoom() {
		return room;
	}

	public RoomCoordinate getCurrentObstacleSouthCorner() {
		return currentObstacleSouthCorner;
	}

	public void setCurrentObstacleSouthCorner(RoomCoordinate currentObstacleSouthCorner) {
		this.currentObstacleSouthCorner = currentObstacleSouthCorner;
	}

	public RoomCoordinate getCurrentObstacleNorthCorner() {
		return currentObstacleNorthCorner;
	}

	public void setCurrentObstacleNorthCorner(RoomCoordinate currentObstacleNorthCorner) {
		this.currentObstacleNorthCorner = currentObstacleNorthCorner;
	}

	public LinkedList<RoomCoordinate> getObstacleOutlineList() {
		return obstacleOutlineList;
	}

	public boolean isDoneDimensionDetermining() {
		return isDoneDimensionDetermining;
	}

	public void setDoneDimensionDetermining(boolean isDoneDimensionDetermining) {
		this.isDoneDimensionDetermining = isDoneDimensionDetermining;
	}

	public int getRoomDimensionX() {
		return roomDimensionX;
	}

	public void setRoomDimensionX(int roomDimensionX) {
		this.roomDimensionX = roomDimensionX;
	}

	public int getRoomDimensionY() {
		return roomDimensionY;
	}

	public void setRoomDimensionY(int roomDimensionY) {
		this.roomDimensionY = roomDimensionY;
	}

	public boolean isTrapped() {
		return isTrapped;
	}

	public void setTrapped(boolean isTrapped) {
		this.isTrapped = isTrapped;
	}

	public Stack<RoomCoordinate> getObstacleSouthCornerStack() {
		return obstacleSouthCornerStack;
	}

	public Stack<RoomCoordinate> getObstacleNorthCornerStack() {
		return obstacleNorthCornerStack;
	}

	
}
