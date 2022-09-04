import static org.junit.Assert.*;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.*;

import org.junit.*;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import com.project2.Player;
import com.project2.Player2;


//OVER AND UNDER LIMITS
//new system requirements
//auto values, completely change input system

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;


public class GameTest {

    Player2 player2;
    ArrayList<String> toAttackName;
    ArrayList<Integer> toAttackIndex;

    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;

    private ByteArrayInputStream testIn;
    private ByteArrayOutputStream testOut;



    Player.Support SOne;
    Player.Support STwo;
    Player.Tank TOne;
    Player.Tank TTwo;
    Player.Attack AOne;
    Player.Attack ATwo;

    /*
    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;
    private ByteArrayInputStream testIn;
    private ByteArrayOutputStream testOut;

    Player2 player2 = new Player2();

    ArrayList<String> toAttackName = new ArrayList<>();
    ArrayList<Integer> toAttackIndex =  new ArrayList<>();



    Player.Support SOne = new Player.Support(player2.player1);
    Player.Support STwo = new Player.Support(player2.player2);
    Player.Tank TOne = new Player.Tank(player2.player1);
    Player.Tank TTwo = new Player.Tank(player2.player2);
    Player.Attack AOne = new Player.Attack(player2.player1);
    Player.Attack ATwo = new Player.Attack(player2.player2);

     */



    @After
    public void restoreSystemInputOutput() {
        toAttackIndex.clear();
        toAttackName.clear();
        System.setIn(systemIn);
        System.setOut(systemOut);
    }



    @Before


    public void setUp(){
        player2 = new Player2();
        player2.player1.supports.clear();
        player2.player1.tanks.clear();
        player2.player1.attacks.clear();
        player2.player2.supports.clear();
        player2.player2.tanks.clear();
        player2.player2.attacks.clear();

        toAttackName = new ArrayList<>();
        toAttackIndex =  new ArrayList<>();

        Collections.addAll(toAttackIndex, 0, 0);
        Collections.addAll(toAttackName, "support", "tank");

        SOne = new Player.Support(player2.player1);
        STwo = new Player.Support(player2.player2);
        TOne = new Player.Tank(player2.player1);
        TTwo = new Player.Tank(player2.player2);
        AOne = new Player.Attack(player2.player1);
        ATwo = new Player.Attack(player2.player2);





    }
    private void provideInput(String data) {
        testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    private String getOutput() {
        return testOut.toString();
    }


    private String stringToInput(String[] strings){

        String toReturn = "";
        if(strings.length > 0){
            for (String s: strings){
                toReturn += s+"\n";
            }
        }
        return toReturn;
    }


    @Test
    public void defend(){


        assertEquals(2, AOne.getDefense());
        assertEquals(50, AOne.getCurrentHealth());
        assertEquals(50, ATwo.getCurrentHealth());



        provideInput(stringToInput(new String[]{"attack", "attack", "0", "defend", "attack", "0", "attack", "attack", "0"}));


        //provideInput(stringToInput(new String[]{"attack", "attack", "0", "defend", "attack", "0", "attack", "attack", "0");

        player2.startOfTurn(player2.player1, player2.player1.supportActions, player2.player1.tankActions, player2.player1.attackActions);

        provideInput(stringToInput(new String[]{"attack", "attack", "0", "attack", "attack", "0", "attack", "attack", "0"}));
        //provideInput(stringToInput(new String[]{"attack", "attack", "0", "attack", "attack", "0", "attack", "attack", "0");
        player2.startOfTurn(player2.player2, player2.player2.supportActions, player2.player2.tankActions, player2.player2.attackActions);
        player2.fight(player2.player1, player2.player1.supportActions, player2.player1.tankActions, player2.player1.attackActions);
        player2.fight(player2.player2, player2.player2.supportActions, player2.player2.tankActions, player2.player2.attackActions);
        player2.endOfTurn(SOne, TOne, AOne, STwo, TTwo, ATwo);

        assertEquals(3, AOne.getDefense());
        assertEquals(2, ATwo.getDefense());
        assertEquals(47, AOne.getCurrentHealth());
        assertEquals(48, ATwo.getCurrentHealth());


        provideInput(stringToInput(new String[]{"attack", "attack", "0", "attack", "attack", "0", "attack", "attack", "0"}));
        //provideInput(stringToInput(new String[]{"attack", "attack", "0", "attack", "attack", "0", "attack", "attack", "0");
        player2.startOfTurn(player2.player1, player2.player1.supportActions, player2.player1.tankActions, player2.player1.attackActions);

        provideInput(stringToInput(new String[]{"attack", "attack", "0", "attack", "attack", "0", "attack", "attack", "0"}));
        //provideInput(stringToInput(new String[]{"attack", "attack", "0", "attack", "attack", "0", "attack", "attack", "0");
        player2.startOfTurn(player2.player2, player2.player2.supportActions, player2.player2.tankActions, player2.player2.attackActions);
        player2.fight(player2.player1, player2.player1.supportActions, player2.player1.tankActions, player2.player1.attackActions);
        player2.fight(player2.player2, player2.player2.supportActions, player2.player2.tankActions, player2.player2.attackActions);
        player2.endOfTurn(SOne, TOne, AOne, STwo, TTwo, ATwo);

        assertEquals(3, AOne.getDefense());
        assertEquals(2, ATwo.getDefense());
        assertEquals(47, AOne.getCurrentHealth());
        assertEquals(45, ATwo.getCurrentHealth());

    }




    @Test
    public void heal(){





        assertEquals(50, AOne.getCurrentHealth());
        assertEquals(50, ATwo.getCurrentHealth());

        provideInput(stringToInput(new String[]{"attack", "attack", "0", "attack", "attack", "0", "attack", "attack", "0"}));
        player2.startOfTurn(player2.player1, player2.player1.supportActions, player2.player1.tankActions, player2.player1.attackActions);
        provideInput(stringToInput(new String[]{"attack", "attack", "0", "attack", "attack", "0", "attack", "attack", "0"}));
        player2.startOfTurn(player2.player2, player2.player2.supportActions, player2.player2.tankActions, player2.player2.attackActions);
        player2.fight(player2.player1, player2.player1.supportActions, player2.player1.tankActions, player2.player1.attackActions);
        player2.fight(player2.player2, player2.player2.supportActions, player2.player2.tankActions, player2.player2.attackActions);
        player2.endOfTurn(SOne, TOne, AOne, STwo, TTwo, ATwo);

        assertEquals(47, AOne.getCurrentHealth());
        assertEquals(47, ATwo.getCurrentHealth());

        provideInput(stringToInput(new String[]{"heal", "attack", "0", "attack", "attack", "0", "attack", "attack", "0"}));
        player2.startOfTurn(player2.player1, player2.player1.supportActions, player2.player1.tankActions, player2.player1.attackActions);
        provideInput(stringToInput(new String[]{"heal", "tank", "0", "attack", "attack", "0", "attack", "attack", "0"}));
        player2.startOfTurn(player2.player2, player2.player2.supportActions, player2.player2.tankActions, player2.player2.attackActions);
        player2.fight(player2.player1, player2.player1.supportActions, player2.player1.tankActions, player2.player1.attackActions);
        player2.fight(player2.player2, player2.player2.supportActions, player2.player2.tankActions, player2.player2.attackActions);
        player2.endOfTurn(SOne, TOne, AOne, STwo, TTwo, ATwo);

        assertEquals(47, AOne.getCurrentHealth());
        assertEquals(45, ATwo.getCurrentHealth());


    }



    @Test
    public void poisonAttack(){


        assertEquals(50, AOne.getCurrentHealth());
        assertEquals(50, ATwo.getCurrentHealth());
        assertEquals(2, AOne.getDefense());
        assertEquals(2, ATwo.getDefense());

        provideInput(stringToInput(new String[]{"poison", "attack", "0", "attack", "attack", "0", "attack", "attack", "0"}));
        player2.startOfTurn(player2.player1, player2.player1.supportActions, player2.player1.tankActions, player2.player1.attackActions);
        provideInput(stringToInput(new String[]{"heal", "support", "0", "attack", "attack", "0", "attack", "attack", "0"}));
        player2.startOfTurn(player2.player2, player2.player2.supportActions, player2.player2.tankActions, player2.player2.attackActions);
        player2.fight(player2.player1, player2.player1.supportActions, player2.player1.tankActions, player2.player1.attackActions);
        player2.fight(player2.player2, player2.player2.supportActions, player2.player2.tankActions, player2.player2.attackActions);
        player2.endOfTurn(SOne, TOne, AOne, STwo, TTwo, ATwo);

        assertEquals(48, AOne.getCurrentHealth());
        assertEquals(48, ATwo.getCurrentHealth());
        assertEquals(2, AOne.getDefense());
        assertEquals(1, ATwo.getDefense());

        provideInput(stringToInput(new String[]{"attack", "attack", "0", "attack", "attack", "0", "attack", "attack", "0"}));
        player2.startOfTurn(player2.player1, player2.player1.supportActions, player2.player1.tankActions, player2.player1.attackActions);
        provideInput(stringToInput(new String[]{"heal", "attack", "0", "attack", "attack", "0", "attack", "attack", "0"}));
        player2.startOfTurn(player2.player2, player2.player2.supportActions, player2.player2.tankActions, player2.player2.attackActions);
        player2.fight(player2.player1, player2.player1.supportActions, player2.player1.tankActions, player2.player1.attackActions);
        player2.fight(player2.player2, player2.player2.supportActions, player2.player2.tankActions, player2.player2.attackActions);
        player2.endOfTurn(SOne, TOne, AOne, STwo, TTwo, ATwo);

        assertEquals(46, AOne.getCurrentHealth());
        assertEquals(44, ATwo.getCurrentHealth());
        assertEquals(2, AOne.getDefense());
        assertEquals(1, ATwo.getDefense());

    }

    @Test
    public void shield(){



        assertEquals(50, AOne.getCurrentHealth());
        assertEquals(50, ATwo.getCurrentHealth());

        provideInput(stringToInput(new String[]{"attack", "attack", "0", "shield", "attack", "0", "attack", "attack", "0"}));
        player2.startOfTurn(player2.player1, player2.player1.supportActions, player2.player1.tankActions, player2.player1.attackActions);
        provideInput(stringToInput(new String[]{"attack", "attack", "0", "attack", "attack", "0", "attack", "attack", "0"}));
        player2.startOfTurn(player2.player2, player2.player2.supportActions, player2.player2.tankActions, player2.player2.attackActions);
        player2.fight(player2.player1, player2.player1.supportActions, player2.player1.tankActions, player2.player1.attackActions);
        player2.fight(player2.player2, player2.player2.supportActions, player2.player2.tankActions, player2.player2.attackActions);
        player2.endOfTurn(SOne, TOne, AOne, STwo, TTwo, ATwo);

        assertEquals(47, AOne.getCurrentHealth());
        assertEquals(48, ATwo.getCurrentHealth());
        assertTrue(AOne.isShielded());


        provideInput(stringToInput(new String[]{"attack", "attack", "0", "attack", "attack", "0", "attack", "attack", "0"}));
        player2.startOfTurn(player2.player1, player2.player1.supportActions, player2.player1.tankActions, player2.player1.attackActions);
        provideInput(stringToInput(new String[]{"attack", "attack", "0", "attack", "attack", "0", "attack", "attack", "0"}));
        player2.startOfTurn(player2.player2, player2.player2.supportActions, player2.player2.tankActions, player2.player2.attackActions);
        player2.fight(player2.player1, player2.player1.supportActions, player2.player1.tankActions, player2.player1.attackActions);
        player2.fight(player2.player2, player2.player2.supportActions, player2.player2.tankActions, player2.player2.attackActions);
        player2.endOfTurn(SOne, TOne, AOne, STwo, TTwo, ATwo);

        assertEquals(47, AOne.getCurrentHealth());
        assertEquals(45, ATwo.getCurrentHealth());
        assertFalse(AOne.isShielded());


    }

    @Test
    public void stunAttack(){


        assertEquals(50, AOne.getCurrentHealth());
        assertEquals(50, ATwo.getCurrentHealth());


        provideInput(stringToInput(new String[]{"attack", "attack", "0", "attack", "attack", "0", "stun", "attack", "0"}));
        player2.startOfTurn(player2.player1, player2.player1.supportActions, player2.player1.tankActions, player2.player1.attackActions);
        provideInput(stringToInput(new String[]{"heal", "support", "0", "attack", "attack", "0", "attack", "attack", "0"}));
        player2.startOfTurn(player2.player2, player2.player2.supportActions, player2.player2.tankActions, player2.player2.attackActions);
        player2.fight(player2.player1, player2.player1.supportActions, player2.player1.tankActions, player2.player1.attackActions);
        player2.fight(player2.player2, player2.player2.supportActions, player2.player2.tankActions, player2.player2.attackActions);
        player2.endOfTurn(SOne, TOne, AOne, STwo, TTwo, ATwo);

        assertEquals(48, AOne.getCurrentHealth());
        assertEquals(48, ATwo.getCurrentHealth());
        assertTrue(ATwo.getIsStun());

        provideInput(stringToInput(new String[]{"attack", "attack", "0", "attack", "attack", "0", "attack", "attack", "0"}));
        player2.startOfTurn(player2.player1, player2.player1.supportActions, player2.player1.tankActions, player2.player1.attackActions);
        provideInput(stringToInput(new String[]{"attack", "attack", "0", "attack", "attack", "0"}));
        player2.startOfTurn(player2.player2, player2.player2.supportActions, player2.player2.tankActions, player2.player2.attackActions);
        player2.fight(player2.player1, player2.player1.supportActions, player2.player1.tankActions, player2.player1.attackActions);
        player2.fight(player2.player2, player2.player2.supportActions, player2.player2.tankActions, player2.player2.attackActions);
        player2.endOfTurn(SOne, TOne, AOne, STwo, TTwo, ATwo);

        assertEquals(46, AOne.getCurrentHealth());
        assertEquals(45, ATwo.getCurrentHealth());
        assertFalse(ATwo.getIsStun());


    }

    @Test
    public void multiAttack(){


        assertEquals(50, AOne.getCurrentHealth());
        assertEquals(50, ATwo.getCurrentHealth());


        provideInput(stringToInput(new String[]{"attack", "attack", "0", "attack", "attack", "0", "multi-attack", "attack", "0", "support", "0", "tank", "0"}));
        player2.startOfTurn(player2.player1, player2.player1.supportActions, player2.player1.tankActions, player2.player1.attackActions);
        provideInput(stringToInput(new String[]{"heal", "support", "0", "attack", "attack", "0", "attack", "attack", "0"}));
        player2.startOfTurn(player2.player2, player2.player2.supportActions, player2.player2.tankActions, player2.player2.attackActions);
        player2.fight(player2.player1, player2.player1.supportActions, player2.player1.tankActions, player2.player1.attackActions);
        player2.fight(player2.player2, player2.player2.supportActions, player2.player2.tankActions, player2.player2.attackActions);
        player2.endOfTurn(SOne, TOne, AOne, STwo, TTwo, ATwo);

        assertEquals(50, STwo.getCurrentHealth());
        assertEquals(49, TTwo.getCurrentHealth());
        assertEquals(47, ATwo.getCurrentHealth());


        provideInput(stringToInput(new String[]{"attack", "attack", "0", "attack", "attack", "0", "attack", "attack", "0"}));
        player2.startOfTurn(player2.player1, player2.player1.supportActions, player2.player1.tankActions, player2.player1.attackActions);
        provideInput(stringToInput(new String[]{"attack", "attack", "0", "attack", "attack", "0", "multi-attack", "attack", "0", "attack", "0", "tank", "0"}));
        player2.startOfTurn(player2.player2, player2.player2.supportActions, player2.player2.tankActions, player2.player2.attackActions);
        player2.fight(player2.player1, player2.player1.supportActions, player2.player1.tankActions, player2.player1.attackActions);
        player2.fight(player2.player2, player2.player2.supportActions, player2.player2.tankActions, player2.player2.attackActions);
        player2.endOfTurn(SOne, TOne, AOne, STwo, TTwo, ATwo);

        assertEquals(50, SOne.getCurrentHealth());
        assertEquals(49, TOne.getCurrentHealth());
        assertEquals(44, AOne.getCurrentHealth());
        assertEquals(50, STwo.getCurrentHealth());
        assertEquals(49, TTwo.getCurrentHealth());
        assertEquals(44, ATwo.getCurrentHealth());



    }
    @Test
    public void troopDie(){
        assertEquals(50, SOne.getCurrentHealth());
        assertEquals(50, TOne.getCurrentHealth());
        assertEquals(50, AOne.getCurrentHealth());
        assertEquals(50, STwo.getCurrentHealth());
        assertEquals(50, TTwo.getCurrentHealth());
        assertEquals(50, ATwo.getCurrentHealth());

        for (int i = 0; i < 50; i++){
            provideInput(stringToInput(new String[]{"attack", "support", "0", "attack", "tank", "0", "attack", "attack", "0"}));
            player2.startOfTurn(player2.player1, player2.player1.supportActions, player2.player1.tankActions, player2.player1.attackActions);
            provideInput(stringToInput(new String[]{"attack", "support", "0", "attack", "tank", "0", "attack", "attack", "0"}));
            player2.startOfTurn(player2.player2, player2.player2.supportActions, player2.player2.tankActions, player2.player2.attackActions);
            player2.fight(player2.player1, player2.player1.supportActions, player2.player1.tankActions, player2.player1.attackActions);
            player2.fight(player2.player2, player2.player2.supportActions, player2.player2.tankActions, player2.player2.attackActions);
            player2.endOfTurn(SOne, TOne, AOne, STwo, TTwo, ATwo);

            assertEquals(49-i, SOne.getCurrentHealth());
            assertEquals(49-i, TOne.getCurrentHealth());
            assertEquals(49-i, AOne.getCurrentHealth());
            assertEquals(49-i, STwo.getCurrentHealth());
            assertEquals(49-i, TTwo.getCurrentHealth());
            assertEquals(49-i, ATwo.getCurrentHealth());
        }
        assertFalse(SOne.isAlive());
        assertFalse(TOne.isAlive());
        assertFalse(AOne.isAlive());
        assertFalse(STwo.isAlive());
        assertFalse(TTwo.isAlive());
        assertFalse(ATwo.isAlive());

        provideInput(stringToInput(new String[]{}));
        player2.startOfTurn(player2.player1, player2.player1.supportActions, player2.player1.tankActions, player2.player1.attackActions);
        provideInput(stringToInput(new String[]{}));
        player2.startOfTurn(player2.player2, player2.player2.supportActions, player2.player2.tankActions, player2.player2.attackActions);
        player2.fight(player2.player1, player2.player1.supportActions, player2.player1.tankActions, player2.player1.attackActions);
        player2.fight(player2.player2, player2.player2.supportActions, player2.player2.tankActions, player2.player2.attackActions);
        player2.endOfTurn(SOne, TOne, AOne, STwo, TTwo, ATwo);

        assertEquals(0, SOne.getCurrentHealth());
        assertEquals(0, TOne.getCurrentHealth());
        assertEquals(0, AOne.getCurrentHealth());
        assertEquals(0, STwo.getCurrentHealth());
        assertEquals(0, TTwo.getCurrentHealth());
        assertEquals(0, ATwo.getCurrentHealth());

        assertFalse(SOne.isAlive());
        assertFalse(TOne.isAlive());
        assertFalse(AOne.isAlive());
        assertFalse(STwo.isAlive());
        assertFalse(TTwo.isAlive());
        assertFalse(ATwo.isAlive());



    }






}
