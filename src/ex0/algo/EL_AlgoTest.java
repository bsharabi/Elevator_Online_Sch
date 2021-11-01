package ex0.algo;
import ex0.Building;
import ex0.CallForElevator;
import ex0.simulator.*;


import java.text.MessageFormat;

import static org.junit.jupiter.api.Assertions.*;

class EL_AlgoTest {

    //Building b= new Building_A(int Min_floor,int Max_floor, int number_of elevators, String name;
    Building b = new Builging_A(3, 10, 2, "Sagi_is your_boss");

    //  Call_A c1 = new Call_A(int state, int src, int dest);
    CallForElevator c1 = new Call_A(0, 3, 4);// 3--->5
    CallForElevator c2 = new Call_A(0, 3, 4);//3-->4
    CallForElevator c3 = new Call_A(0, 10, 3);//10---3
    CallForElevator c4 = new Call_A(0, 3, 9);//3---9


    EL_Algo case1 = new EL_Algo(b);


    @org.junit.jupiter.api.Test
    void getBuilding() {
        assertSame(b , case1.getBuilding());
    }

    @org.junit.jupiter.api.Test
    void algoName() {
        assertEquals(case1.algoName(), "Ex0_OOP_AlgoBest");
    }

    @org.junit.jupiter.api.Test
    void allocateAnElevator() {
        System.out.println(b.minFloor());
        System.out.printf("dest:%d %nsrc:%d  %nstate:%d %n", c1.getDest(), c1.getSrc(), c1.getState());
        System.out.println(MessageFormat.format("dest:{1} \nsrc:{1}  \nstate:{1} ", c1.getDest(), c1.getSrc(), c1.getState()));
        int t1 = case1.allocateAnElevator(c1);
        int t2 = case1.allocateAnElevator(c2);
        int t3 = case1.allocateAnElevator(c3);
        int t4 = case1.allocateAnElevator(c4);
        System.out.printf("t1 %d \nt2: %d \nt3:%d \nt4:%d", t1, t2, t3, t4);

        assertEquals(t1, t2);
        assertEquals(t2, t3);
        assertEquals(t1, t3);
        assertNotEquals(t3, t4);
    }

    @org.junit.jupiter.api.Test
    void cmdElevator() {
    }
}