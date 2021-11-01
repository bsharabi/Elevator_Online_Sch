package ex0.algo;

import ex0.Building;
import ex0.CallForElevator;
import ex0.simulator.Builging_A;
import ex0.simulator.Call_A;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ElevatorSchTest {


    //Building b= new Building_A(int Min_floor,int Max_floor, int number_of elevators, String name;
    Building b = new Builging_A(3, 10, 1, "Sagi_is your_boss");
    Building b2 = new Builging_A(3, 10, 2, "Sagi_is your_boss too");

    //  Call_A c1 = new Call_A(int state, int src, int dest);
    CallForElevator c1 = new Call_A(0, 3, 4);// 3--->5
    CallForElevator c2 = new Call_A(0, 3, 4);//3-->4
    CallForElevator c3 = new Call_A(0, 10, 3);//10---3
    CallForElevator c4 = new Call_A(0, 3, 9);//3---9


    EL_Algo case1 = new EL_Algo(b);
    EL_Algo case2 =  new EL_Algo(b2);

    ElevatorSch basic1 = new ElevatorSch(b.getElevetor(0), b.maxFloor() - b.minFloor(),1);
    //-------------------------------- same building ,two different elevators.
    ElevatorSch basic2 =  new ElevatorSch(b2.getElevetor(1), b2.maxFloor() - b2.minFloor(),2);
    ElevatorSch basic3 =  new ElevatorSch(b2.getElevetor(0), b2.maxFloor() - b2.minFloor(),2);

    @Test
    void action() {
    }

    @Test
    void getTimeToComplete() {
        assertEquals(11, basic1.getTimeToComplete(c1));
        basic1.setToQueue(c1);
        assertEquals(17, basic1.getTimeToComplete(c1));
        basic1.setToQueue(c2);
        basic1.setToQueue(c3);
        basic1.setToQueue(c4);
        System.out.println(basic1.getTimeToComplete(c1));
        assertEquals(47, basic1.getTimeToComplete(c1));



        System.out.println("-------------------------basic 2------------------------");

        System.out.println(basic2.getTimeToComplete(c1));
        basic2.setToQueue(c1);
        // we gave the elevator1 the -c1 call, and after that we checked the differences between the two elevators time to complete
        assertEquals(11, basic3.getTimeToComplete(c1));
        assertEquals(17, basic2.getTimeToComplete(c1));
//        System.out.println(basic2.getTimeToComplete(c1));
//        System.out.println(basic3.getTimeToComplete(c1));

//        System.out.println(basic2.getTimeToComplete(c1));
//        System.out.println(basic2.getTimeToComplete(c1));


    }

    @Test
    void setToQueue() {
        basic1.setToQueue(c1);
        System.out.println(basic1._queue.peek());

        assertEquals(c1,basic1._queue.peek());
    }
}