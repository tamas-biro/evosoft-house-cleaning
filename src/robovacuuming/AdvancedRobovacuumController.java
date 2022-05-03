package robovacuuming;

import java.util.LinkedList;

public class AdvancedRobovacuumController {
	private final AdvancedRobovacuum robovacuum;
	
	private final int requiredCycleAmount;
	private int doneCycleAmount;
	private boolean isDoneCurrentCycle;
	private boolean isDoneVacuuming;
	
	
	
	public AdvancedRobovacuumController(AdvancedRobovacuum robovacuum, int requiredCycleAmount) {
		this.robovacuum = robovacuum;
		
		this.requiredCycleAmount = requiredCycleAmount;
		this.doneCycleAmount = 0;
		this.isDoneCurrentCycle = false;
		this.isDoneVacuuming = false;
	}
	
	//Start the vacuuming process and repeat for the required amount
	public void startVacuuming() {
		System.out.println("Start vacuuming! Required cycles: "+getRequiredCycleAmount());
		while(!isDoneVacuuming()) {
			startCurrentCycle();
			
			//Prepare for the next cycle after the current is finished
			getRobovacuum().getRoom().setRoomMatrix(getRobovacuum().rotateRoomMatrixCounterClockwise(getRobovacuum().getRoom().getRoomMatrix())); //Rotate the room simulating the shift in interpreting directions
			getRobovacuum().setNewStartingCoordinates(); //Setting the rotated starting position based on the isObstacle flag of RoomCoordinate
			//Emptying and initializing route data
			getRobovacuum().setRouteCoordinateList(new LinkedList<RoomCoordinate>()); 
			getRobovacuum().addRouteElement(getRobovacuum().getCurrentCoordinateX(), getRobovacuum().getCurrentCoordinateY());
			
			System.out.println("Vacuuming status: Done cycles: "+getDoneCycleAmount()+", Remaining cycles: "+(getRequiredCycleAmount()-getDoneCycleAmount()));
			
			if(getDoneCycleAmount() == getRequiredCycleAmount()) {
				setDoneVacuuming(true);
			}
		}
		System.out.println("Done vacuuming!");
	}
	
	//Carrying out the current cycle
	public void startCurrentCycle() {
		setDoneCurrentCycle(false);
		
		//Moving and vacuuming until the cycle is done
		while(isDoneCurrentCycle() != true) {
			System.out.println("X: "+getRobovacuum().getCurrentCoordinateX()+", Y: "+getRobovacuum().getCurrentCoordinateY()+", Dir: "+getRobovacuum().getCurrentDirection().toString());
			continueVacuuming(); //Making movement based on direction, previous route and obstacle data
		}
		
		getRobovacuum().getRoom().getRoomMatrix()[getRobovacuum().getCurrentCoordinateX()][getRobovacuum().getCurrentCoordinateY()].setRobovacuumHere(true); //Setting the last coordinate in the cycle for the beginning of the next cycle
		setDoneCycleAmount(getDoneCycleAmount()+1);
		System.out.println(getDoneCycleAmount()+". vacuuming cycle is done!");
	}
	
	//Making movement based on direction, previous route and obstacle data
	private void continueVacuuming() {
		switch(getRobovacuum().getCurrentDirection()) {
			case SOUTH :
				//If hasn't been on the West coordinate and it's not an obstacle, turn, otherwise continue in the South/East direction 
				if(!getRobovacuum().getRoom().isCoordinateObstacle(getRobovacuum().getCurrentCoordinateX()-1, getRobovacuum().getCurrentCoordinateY()) && !getRobovacuum().isCoordinateInRouteList(getRobovacuum().getCurrentCoordinateX()-1, getRobovacuum().getCurrentCoordinateY())) {
					getRobovacuum().move(Direction.WEST);					
				}
				else {
					if (getRobovacuum().move(Direction.SOUTH)) {
					}
					else if(getRobovacuum().move(Direction.EAST)) {
					}
					else {
						getRobovacuum().move(Direction.NORTH);
					}
				}
				break;
			case EAST :
				//If coming from South, move North, otherwise move South
				if(getRobovacuum().getPreviousDirection().equals(Direction.SOUTH)) {
					if(getRobovacuum().move(Direction.NORTH)) {
					}
					else if(getRobovacuum().move(Direction.EAST)) {
					}
					else {
						getRobovacuum().move(Direction.WEST);
					}
				}
				else {
					if(getRobovacuum().move(Direction.SOUTH)) {						
					}
					else if(getRobovacuum().move(Direction.NORTH)) {
					}
					else {
						getRobovacuum().move(Direction.EAST);
					}
				}
				break;
			case NORTH :
				//If hasn't been on the West coordinate and it's not an obstacle, turn, otherwise continue in the North/East direction
				if(!getRobovacuum().getRoom().isCoordinateObstacle(getRobovacuum().getCurrentCoordinateX()-1, getRobovacuum().getCurrentCoordinateY()) && !getRobovacuum().isCoordinateInRouteList(getRobovacuum().getCurrentCoordinateX()-1, getRobovacuum().getCurrentCoordinateY())) {
					getRobovacuum().move(Direction.WEST);					
				}
				else {
					if(getRobovacuum().move(Direction.NORTH)) {
					}
					else if(getRobovacuum().move(Direction.EAST)) {
					}
					else {
						setDoneCurrentCycle(true);
					}
				}
				
				break;
			case WEST :
				//Move West until hitting an obstacle or a previously cleaned coordinate
				if(!getRobovacuum().getRoom().isCoordinateObstacle(getRobovacuum().getCurrentCoordinateX()-1, getRobovacuum().getCurrentCoordinateY()) && !getRobovacuum().isCoordinateInRouteList(getRobovacuum().getCurrentCoordinateX()-1, getRobovacuum().getCurrentCoordinateY())) {
					if(getRobovacuum().move(Direction.WEST)) {
					}
					else if(getRobovacuum().move(Direction.SOUTH)) {
					}
					else {
						getRobovacuum().move(Direction.EAST);
					}
				}
				else {
					if(!getRobovacuum().isCoordinateInRouteList(getRobovacuum().getCurrentCoordinateX(), getRobovacuum().getCurrentCoordinateY()-1)) {
						if(getRobovacuum().move(Direction.NORTH)) {
						}
						else if(getRobovacuum().move(Direction.SOUTH)) {
						}
						else {
							getRobovacuum().move(Direction.EAST);
						}
					}
					else {
						if(getRobovacuum().move(Direction.SOUTH)) {
						}
						else {
							getRobovacuum().move(Direction.EAST);
						}
					}
				}
				break;
		}
	}

	public AdvancedRobovacuum getRobovacuum() {
		return robovacuum;
	}

	public int getDoneCycleAmount() {
		return doneCycleAmount;
	}

	public void setDoneCycleAmount(int doneCycleAmount) {
		this.doneCycleAmount = doneCycleAmount;
	}

	public int getRequiredCycleAmount() {
		return requiredCycleAmount;
	}

	public boolean isDoneCurrentCycle() {
		return isDoneCurrentCycle;
	}

	public void setDoneCurrentCycle(boolean isDoneCurrentCycle) {
		this.isDoneCurrentCycle = isDoneCurrentCycle;
	}

	public boolean isDoneVacuuming() {
		return isDoneVacuuming;
	}

	public void setDoneVacuuming(boolean isDoneVacuuming) {
		this.isDoneVacuuming = isDoneVacuuming;
	}
	
	
	
}
