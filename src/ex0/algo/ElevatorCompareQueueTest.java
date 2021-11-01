package ex0.algo;

import ex0.Building;
import ex0.CallForElevator;
import ex0.simulator.Builging_A;
import ex0.simulator.Call_A;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ElevatorCompareQueueTest {

    //Building b= new Building_A(int Min_floor,int Max_floor, int number_of elevators, String name;
    Building b = new Builging_A(3, 10, 2, "Sagi_is your_boss");

    //  Call_A c1 = new Call_A(int state, int src, int dest);
    CallForElevator c1 = new Call_A(0, 3, 4);// 3--->5
    CallForElevator c2 = new Call_A(0, 3, 4);//3-->4
    CallForElevator c3 = new Call_A(0, 10, 3);//10---3
    CallForElevator c4 = new Call_A(3000, 3, 9);//3---9
    ElevatorCompareQueue x = new ElevatorCompareQueue();

    EL_Algo case1 = new EL_Algo(b);



    @Test
    void compare() {

        assertEquals(-1, x.compare(c1, c4));
        assertEquals(0, x.compare(c1, c2));
    }
}