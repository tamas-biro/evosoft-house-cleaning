package robovacuuming;

import java.util.LinkedList;

public class RobovacuumStart {

	public static void main(String[] args) {
		//Define obstacles for simulation
		LinkedList<RoomCoordinate> myRoomObstacleList = new LinkedList<RoomCoordinate>();
		
		// Corner cabinet
		myRoomObstacleList.add(new RoomCoordinate(0, 12)); 
		myRoomObstacleList.add(new RoomCoordinate(0, 13)); 
		myRoomObstacleList.add(new RoomCoordinate(0, 14));
		myRoomObstacleList.add(new RoomCoordinate(1, 12)); 
		myRoomObstacleList.add(new RoomCoordinate(1, 13)); 
		myRoomObstacleList.add(new RoomCoordinate(1, 14));
		myRoomObstacleList.add(new RoomCoordinate(2, 12)); 
		myRoomObstacleList.add(new RoomCoordinate(2, 13)); 
		myRoomObstacleList.add(new RoomCoordinate(2, 14));
		 
		//L shaped couch
		myRoomObstacleList.add(new RoomCoordinate(7, 3)); 
		myRoomObstacleList.add(new RoomCoordinate(7, 4)); 
		myRoomObstacleList.add(new RoomCoordinate(8, 3));
		myRoomObstacleList.add(new RoomCoordinate(8, 4)); 
		myRoomObstacleList.add(new RoomCoordinate(9, 3)); 
		myRoomObstacleList.add(new RoomCoordinate(9, 4));
		myRoomObstacleList.add(new RoomCoordinate(10, 3)); 
		myRoomObstacleList.add(new RoomCoordinate(10, 4));
		
		myRoomObstacleList.add(new RoomCoordinate(7, 5));
		myRoomObstacleList.add(new RoomCoordinate(8, 5));
		myRoomObstacleList.add(new RoomCoordinate(7, 6));
		myRoomObstacleList.add(new RoomCoordinate(8, 6));
		myRoomObstacleList.add(new RoomCoordinate(7, 7));
		myRoomObstacleList.add(new RoomCoordinate(8, 7));
		myRoomObstacleList.add(new RoomCoordinate(7, 8));
		myRoomObstacleList.add(new RoomCoordinate(8, 8));
		
		// Little cabinet behind the couch
		myRoomObstacleList.add(new RoomCoordinate(6, 3)); 
		myRoomObstacleList.add(new RoomCoordinate(6, 4)); 
		
		//Chair
		myRoomObstacleList.add(new RoomCoordinate(9, 10));
		myRoomObstacleList.add(new RoomCoordinate(10, 10));
		myRoomObstacleList.add(new RoomCoordinate(9, 11));
		myRoomObstacleList.add(new RoomCoordinate(10, 11));
		
		//TV unit
		myRoomObstacleList.add(new RoomCoordinate(13, 5));
		myRoomObstacleList.add(new RoomCoordinate(14, 5));
		myRoomObstacleList.add(new RoomCoordinate(13, 6));
		myRoomObstacleList.add(new RoomCoordinate(14, 6));
		myRoomObstacleList.add(new RoomCoordinate(13, 7));
		myRoomObstacleList.add(new RoomCoordinate(14, 7));
		myRoomObstacleList.add(new RoomCoordinate(13, 8));
		myRoomObstacleList.add(new RoomCoordinate(14, 8));
		myRoomObstacleList.add(new RoomCoordinate(13, 9));
		myRoomObstacleList.add(new RoomCoordinate(14, 9));
		
		//Hi-Fi speaker 1
		myRoomObstacleList.add(new RoomCoordinate(13, 1));
		myRoomObstacleList.add(new RoomCoordinate(14, 1));
		myRoomObstacleList.add(new RoomCoordinate(13, 2));
		myRoomObstacleList.add(new RoomCoordinate(14, 2));
		
		//Hi-Fi speaker 2
		myRoomObstacleList.add(new RoomCoordinate(13, 12));
		myRoomObstacleList.add(new RoomCoordinate(14, 12));
		myRoomObstacleList.add(new RoomCoordinate(13, 13));
		myRoomObstacleList.add(new RoomCoordinate(14, 13));
		
		//Instantiate Room object with obstacle
		AdvancedRoom myAdvancedRoom = new AdvancedRoom(15, 15, myRoomObstacleList);
		//Instantiate Robovacuum object with starting coordinates and required cycle amount
		AdvancedRobovacuum myAdvancedRobovacuum = new AdvancedRobovacuum(myAdvancedRoom,0,0);
		// Instantiate Robovacuum controller, which calculates movements
		AdvancedRobovacuumController myAdvancedRobovacuumController = new AdvancedRobovacuumController(myAdvancedRobovacuum, 4);
		
		//Start vacuuming
		myAdvancedRobovacuumController.startVacuuming();
	}

}
