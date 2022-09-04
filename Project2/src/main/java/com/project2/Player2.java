package com.project2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Player2 {
    public static Player player1 = new Player();
    public static Player player2 = new Player();


    public static void main(String[] args) {
        /*
        Player player1 = new Player();
        Player player2 = new Player();

        ArrayList<String> toAttackName = new ArrayList<>();
        ArrayList<Integer> toAttackIndex =  new ArrayList<>();
        Collections.addAll(toAttackIndex, 0, 0);
        Collections.addAll(toAttackName, "support", "tank");

        Player.Support one = new Player.Support(player1);
        Player.Support two = new Player.Support(player2);
        Player.Tank TOne = new Player.Tank(player1);
        Player.Attack ATwo = new Player.Attack(player2);

        System.out.println(two.getCurrentHealth());

        one.attack(player2, "support", 0, 10);

        System.out.println("after: "+two.getCurrentHealth());

        two.healAction(player2, "support", 0, 5);

        System.out.println("after heal: "+two.getCurrentHealth());

        System.out.println("One health: "+one.getCurrentHealth());
        TOne.defenseBuff(player1, "support", 0, 1);
        two.attack(player1, "support", 0, 5);
        System.out.println("One health after: "+one.getCurrentHealth());

        System.out.println("Support then tank"+one.getCurrentHealth()+"    "+TOne.getCurrentHealth());
        ATwo.multiAttack(player1, toAttackName, toAttackIndex, 5);
        System.out.println("Support then tank"+one.getCurrentHealth()+"    "+TOne.getCurrentHealth());

         */

        //turn starts, enables player1 to enter attack info
        //then allows player to enter info
        //turn ends and and the game saves
        //while winner = null or something

        //effects apply until magic person heals them
        //at end of fight phase poison activates and breaks defense
        //when choosing attacks, stunned minions skip,  but then sets to false

        //end of turn function that changes all







        ArrayList<String> toAttackName = new ArrayList<>();
        ArrayList<Integer> toAttackIndex =  new ArrayList<>();

        Collections.addAll(toAttackIndex, 0, 0);
        Collections.addAll(toAttackName, "support", "tank");

        player1.supports.clear();
        player1.tanks.clear();
        player1.attacks.clear();
        player2.supports.clear();
        player2.tanks.clear();
        player2.attacks.clear();

        Player.Support SOne = new Player.Support(player1);
        Player.Support STwo = new Player.Support(player2);
        Player.Tank TOne = new Player.Tank(player1);
        Player.Tank TTwo = new Player.Tank(player2);
        Player.Attack AOne = new Player.Attack(player1);
        Player.Attack ATwo = new Player.Attack(player2);

        for (int i = 0; i<5; i++){
            startOfTurn(player1, player1.supportActions, player1.tankActions, player1.attackActions);
            startOfTurn(player2, player2.supportActions, player2.tankActions, player2.attackActions);
            fight(player1, player1.supportActions, player1.tankActions, player1.attackActions);
            fight(player2, player2.supportActions, player2.tankActions, player2.attackActions);

            endOfTurn(SOne, TOne, AOne, STwo, TTwo, ATwo);
        }



        //startOfTurn(player2.supportActions, player2.tankActions, player2.attackActions);
        //fight(player2.supportActions, player2.tankActions, player2.attackActions);
/*
        System.out.println("Player 2 support health"+STwo.getCurrentHealth());
        System.out.println("Player 2 tank health"+TTwo.getCurrentHealth());
        System.out.println("Player 2 attack health"+ATwo.getCurrentHealth());

 */

    }

    public static void startOfTurn(Player player, ArrayList<String> supportActions, ArrayList<String> tankActions, ArrayList<String> attackActions){

        Scanner scan = new Scanner(System.in);

        supportActions.clear();
        tankActions.clear();
        attackActions.clear();
        //add if there are supports and such

        System.out.println(player.supports.size());
        if (player.supports.size() >0){
            for (Player.Support s : player.supports){
                if (s.getIsStun() || !s.isAlive()){
                    System.out.println("skipped: "+ s);

                } else{
                    System.out.println("what action\r");
                    String actionType = scan.next();
                    System.out.println("what class\r");
                    String targetName = scan.next();
                    System.out.println("what number\r");
                    String targetNumber = scan.next();

                    supportActions.add(actionType+","+targetName+","+targetNumber);
                }



            }
        }

        if (player.tanks.size() >0){
            for (Player.Tank t : player.tanks){
                if (t.getIsStun() || !t.isAlive()){
                    System.out.println("skipped: "+ t);

                } else{
                    System.out.println("what action\r");
                    String actionType = scan.next();
                    System.out.println("what class\r");
                    String targetName = scan.next();
                    System.out.println("what number\r");
                    String targetNumber = scan.next();

                    tankActions.add(actionType+","+targetName+","+targetNumber);
                }



            }
        }

        if (player.attacks.size() >0){
            for (Player.Attack a : player.attacks){
                if (a.getIsStun() || !a.isAlive()){
                    System.out.println("skipped: "+ a);

                } else{
                    System.out.println("what action\r");
                    String actionType = scan.next();

                    if (actionType.equals("multi-attack")){
                        String toAdd = actionType;
                        for(int i = 0; i<a.getMultiNum(); i++){
                            System.out.println("what class\r");
                            toAdd += ","+scan.next();

                            System.out.println("what number\r");
                            toAdd += ","+scan.next();

                        }
                        attackActions.add(toAdd);
                    } else{
                        System.out.println("what class\r");
                        String targetName = scan.next();
                        System.out.println("what number\r");
                        String targetNumber = scan.next();

                        attackActions.add(actionType+","+targetName+","+targetNumber);
                    }
                }





            }
        }

        scan.close();




    }
    private static Player reversePlayer(Player player){
        Player otherPlayer;
        if(player.equals(player1)){
            otherPlayer = player2;
        } else{
            otherPlayer = player1;
        }
        return otherPlayer;
    }

    public static void fight(Player player, ArrayList<String> supportActions, ArrayList<String> tankActions, ArrayList<String> attackActions){
        Player otherPlayer = reversePlayer(player);


        if (player.supports.size() >0){
            for (Player.Support s : player.supports){
                if (s.getIsStun() || !s.isAlive()){
                    System.out.println("skipped: "+ s);
                    s.falseIsStun();
                } else{
                    parseSupportMethod(s, otherPlayer, supportActions.get(player.supports.indexOf(s)));
                }

            }
        }


        if (player.tanks.size() >0){
            for (Player.Tank t : player.tanks){
                if (t.getIsStun() || !t.isAlive()){
                    System.out.println("skipped: "+ t);
                    t.falseIsStun();
                } else{
                    parseTankMethod(t, otherPlayer, tankActions.get(player.tanks.indexOf(t)));
                }

            }
        }


        if (player.attacks.size() >0){
            for (Player.Attack a : player.attacks){
                if (a.getIsStun() || !a.isAlive()){
                    System.out.println("skipped: "+ a);
                    a.falseIsStun();
                } else{
                    parseAttackMethod(a, otherPlayer, attackActions.get(player.attacks.indexOf(a)));
                }

            }
        }

        //change to account for heal and defense and shield

    }
    public static void endOfTurn(Player.Support SOne, Player.Tank TOne, Player.Attack AOne, Player.Support STwo, Player.Tank TTwo, Player.Attack ATwo){
        //add check effects
        System.out.println("Player 1 support health "+SOne.getCurrentHealth());
        System.out.println("Player 1 tank health "+TOne.getCurrentHealth());
        System.out.println("Player 1 attack health "+AOne.getCurrentHealth());
        System.out.println("Player 2 support health "+STwo.getCurrentHealth());
        System.out.println("Player 2 tank health "+TTwo.getCurrentHealth());
        System.out.println("Player 2 attack health "+ATwo.getCurrentHealth());

        player1.finishTurn();
        player2.finishTurn();

        System.out.println("Player 1 support stun "+SOne.getIsStun());
        System.out.println("Player 1 tank stun "+TOne.getIsStun());
        System.out.println("Player 1 attack stun "+AOne.getIsStun());
        System.out.println("Player 2 support stun "+STwo.getIsStun());
        System.out.println("Player 2 tank stun "+TTwo.getIsStun());
        System.out.println("Player 2 attack stun "+ATwo.getIsStun());

        System.out.println("Player 1 support defense "+SOne.getDefense());
        System.out.println("Player 1 tank defense "+TOne.getDefense());
        System.out.println("Player 1 attack defense "+AOne.getDefense());
        System.out.println("Player 2 support defense "+STwo.getDefense());
        System.out.println("Player 2 tank defense "+TTwo.getDefense());
        System.out.println("Player 2 attack defense "+ATwo.getDefense());

        System.out.println("Player 1 support poison "+SOne.getPoisonEffect());
        System.out.println("Player 1 tank poison "+TOne.getPoisonEffect());
        System.out.println("Player 1 attack poison "+AOne.getPoisonEffect());
        System.out.println("Player 2 support poison "+STwo.getPoisonEffect());
        System.out.println("Player 2 tank poison "+TTwo.getPoisonEffect());
        System.out.println("Player 2 attack poison "+ATwo.getPoisonEffect());

        System.out.println("Player 1 support shield "+SOne.isShielded());
        System.out.println("Player 1 tank shield "+TOne.isShielded());
        System.out.println("Player 1 attack shield "+AOne.isShielded());
        System.out.println("Player 2 support shield "+STwo.isShielded());
        System.out.println("Player 2 tank shield "+TTwo.isShielded());
        System.out.println("Player 2 attack shield "+ATwo.isShielded());

    }

    private static void parseSupportMethod( Player.Support support, Player player, String toParse){
        String action;
        String toDo;
        int toDoNum;
        ArrayList<Integer> commas = new ArrayList<>();

        for (int i = 0; i<toParse.length(); i++){
            if (toParse.substring(i,i+1).equals(",")){
                commas.add(i);
            }
        }
        action = toParse.substring(0,commas.get(0));
        toDo = toParse.substring(commas.get(0)+1,commas.get(1));
        toDoNum = Integer.parseInt(toParse.substring(commas.get(1)+1));

        switch (action) {
            case "heal":
                support.healAction(reversePlayer(player), toDo, toDoNum, 2);

                break;
            case "attack":
                support.attack(player, toDo, toDoNum, 3);

                break;
            case "poison":
                support.poisonAction(player, toDo, toDoNum, 1, 1);

                break;
        }

    }
    private static void parseTankMethod(Player.Tank tank, Player player, String toParse){
        String action;
        String toDo;
        int toDoNum;
        ArrayList<Integer> commas = new ArrayList<>();

        for (int i = 0; i<toParse.length(); i++){
            if (toParse.substring(i,i+1).equals(",")){
                commas.add(i);
            }
        }
        action = toParse.substring(0,commas.get(0));
        toDo = toParse.substring(commas.get(0)+1,commas.get(1));
        toDoNum = Integer.parseInt(toParse.substring(commas.get(1)+1));

        switch (action) {
            case "defend":
                tank.defenseBuff(reversePlayer(player), toDo, toDoNum, 1);

                break;
            case "attack":
                tank.attack(player, toDo, toDoNum, 3);

                break;
            case "shield":
                tank.shield(reversePlayer(player), toDo, toDoNum);


                break;
        }


    }
    private static void parseAttackMethod(Player.Attack attack, Player player, String toParse){
        String action;
        String toDo = "";
        ArrayList<String> arrayToDo = new ArrayList<>();
        ArrayList<Integer> arrayDoNum = new ArrayList<>();
        int toDoNum = -1;
        ArrayList<Integer> commas = new ArrayList<>();

        for (int i = 0; i<toParse.length(); i++){
            if (toParse.substring(i,i+1).equals(",")){
                commas.add(i);
            }
        }
        commas.add(toParse.length());
        action = toParse.substring(0,commas.get(0));

        if (action.equals("multi-attack")){
            for (int i = 0; i<attack.getMultiNum(); i++){
                arrayToDo.add(toParse.substring(commas.get(i*2)+1,commas.get(i*2+1)));
                arrayDoNum.add(Integer.parseInt(toParse.substring(commas.get(i*2+1)+1, commas.get(i*2+2))));
            }
            
        } else{
            toDo = toParse.substring(commas.get(0)+1,commas.get(1));
            toDoNum = Integer.parseInt(toParse.substring(commas.get(1)+1));

        }


        switch (action) {
            case "stun":
                attack.stunAttack(player, toDo, toDoNum, 1);


                break;
            case "attack":
                attack.attack(player, toDo, toDoNum, 3);

                break;
            case "multi-attack":
                attack.multiAttack(player, arrayToDo, arrayDoNum, 3);

                break;
        }
    }
}
