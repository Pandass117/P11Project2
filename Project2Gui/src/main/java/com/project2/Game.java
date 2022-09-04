package com.project2;

import java.util.ArrayList;


public class Game {
    public static Player player1;
    public static Player player2;




    public Game(Player player1, Player player2){
        Game.player1 = player1;
        Game.player2 = player2;

    }

    //Requires: player1 or player2, actions for supports, tanks, and attacks
    //Modifies: this, supports, tanks, attacks
    //Effects: parses actions that correspond with the troop, skips stunned and dead troops
    public static void fight(Player player, ArrayList<String> supportActions, ArrayList<String> tankActions, ArrayList<String> attackActions){
        Player otherPlayer = Controller.reversePlayer(player);


        if (player.supports.size() >0){

            for (Player.Support s : player.supports){
                if (s.getIsStun() || !s.isAlive()){

                    s.falseIsStun();
                } else{

                    parseSupportMethod(s, otherPlayer, supportActions.get(player.supports.indexOf(s)));
                }

            }
        }


        if (player.tanks.size() >0){
            for (Player.Tank t : player.tanks){
                if (t.getIsStun() || !t.isAlive()){

                    t.falseIsStun();
                } else{

                    parseTankMethod(t, otherPlayer, tankActions.get(player.tanks.indexOf(t)));
                }

            }
        }


        if (player.attacks.size() >0){
            for (Player.Attack a : player.attacks){
                if (a.getIsStun() || !a.isAlive()){

                    a.falseIsStun();
                } else{

                    parseAttackMethod(a, otherPlayer, attackActions.get(player.attacks.indexOf(a)));
                }

            }
        }



    }

    //Requires: a support troop, player of the troop, string that holds action
    //Modifies: this
    //Effects: does action to a troop based on toParse
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
            case "Heal":

                support.healAction(Controller.reversePlayer(player), toDo, toDoNum);

                break;
            case "Attack":

                support.attack(player, toDo, toDoNum);

                break;
            case "Poison":
                support.poisonAction(player, toDo, toDoNum);

                break;
        }

    }
    //Requires: a tank troop, player of the troop, string that holds action
    //Modifies: this
    //Effects: does action to a troop based on toParse
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
            case "Defend":
                tank.defenseBuff(Controller.reversePlayer(player), toDo, toDoNum);

                break;
            case "Attack":

                tank.attack(player, toDo, toDoNum);

                break;
            case "Shield":
                tank.shield(Controller.reversePlayer(player), toDo, toDoNum);


                break;
        }


    }
    //Requires: an attack troop, player of the troop, string that holds action
    //Modifies: this
    //Effects: does action to a troop based on toParse
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

        if (action.equals("Multi-attack")){
            for (int i = 0; i<attack.getMultiNum(); i++){
                arrayToDo.add(toParse.substring(commas.get(i*2)+1,commas.get(i*2+1)));
                arrayDoNum.add(Integer.parseInt(toParse.substring(commas.get(i*2+1)+1, commas.get(i*2+2))));
            }

            
        } else{
            toDo = toParse.substring(commas.get(0)+1,commas.get(1));
            toDoNum = Integer.parseInt(toParse.substring(commas.get(1)+1));

        }


        switch (action) {
            case "Stun":
                attack.stunAttack(player, toDo, toDoNum);


                break;
            case "Attack":

                attack.attack(player, toDo, toDoNum);

                break;
            case "Multi-attack":
                attack.multiAttack(player, arrayToDo, arrayDoNum);

                break;
        }
    }
}
