package com.project2;

import java.io.*;
import java.util.ArrayList;

public class SaveFile {

    //Requires: player1 and player2
    //Modifies: this
    //Effects: writes each troop's info in "Save.txt"
    public static void saveToFile(Player player1, Player player2) throws IOException {
        FileWriter fw = new FileWriter("Save.txt", false);
        BufferedWriter bw = new BufferedWriter(fw);
        if (player1.supports.size() >0){
            for (Player.Support s : player1.supports){
                bw.write("Support,\r");
                bw.write("player1,\r");
                bw.write(s.getCurrentHealth()+",\r");
                bw.write(s.getIsStun()+",\r");
                bw.write(player1.supports.indexOf(s)+",\r");
                bw.write(s.getPoisonEffect()+",\r");
                bw.write(s.getDefense()+",\r");
                bw.write(s.isAlive()+",\r");
                bw.write(s.isShielded()+"\r");
                bw.write(";\r");
            }
        }

        if (player1.tanks.size() >0){
            for (Player.Tank t : player1.tanks){
                bw.write("Tank,\r");
                bw.write("player1,\r");
                bw.write(t.getCurrentHealth()+",\r");
                bw.write(t.getIsStun()+",\r");
                bw.write(player1.tanks.indexOf(t)+",\r");
                bw.write(t.getPoisonEffect()+",\r");
                bw.write(t.getDefense()+",\r");
                bw.write(t.isAlive()+",\r");
                bw.write(t.isShielded()+"\r");
                bw.write(";\r");
            }
        }

        if (player1.attacks.size() >0){
            for (Player.Attack a : player1.attacks){
                bw.write("Attack,\r");
                bw.write("player1,\r");
                bw.write(a.getCurrentHealth()+",\r");
                bw.write(a.getIsStun()+",\r");
                bw.write(player1.attacks.indexOf(a)+",\r");
                bw.write(a.getPoisonEffect()+",\r");
                bw.write(a.getDefense()+",\r");
                bw.write(a.isAlive()+",\r");
                bw.write(a.isShielded()+"\r");
                bw.write(";\r");
            }
        }

        if (player2.supports.size() >0){
            for (Player.Support s : player2.supports){
                bw.write("Support,\r");
                bw.write("player2,\r");
                bw.write(s.getCurrentHealth()+",\r");
                bw.write(s.getIsStun()+",\r");
                bw.write(player2.supports.indexOf(s)+",\r");
                bw.write(s.getPoisonEffect()+",\r");
                bw.write(s.getDefense()+",\r");
                bw.write(s.isAlive()+",\r");
                bw.write(s.isShielded()+"\r");
                bw.write(";\r");
            }
        }

        if (player2.tanks.size() >0){
            for (Player.Tank t : player2.tanks){
                bw.write("Tank,\r");
                bw.write("player2,\r");
                bw.write(t.getCurrentHealth()+",\r");
                bw.write(t.getIsStun()+",\r");
                bw.write(player2.tanks.indexOf(t)+",\r");
                bw.write(t.getPoisonEffect()+",\r");
                bw.write(t.getDefense()+",\r");
                bw.write(t.isAlive()+",\r");
                bw.write(t.isShielded()+"\r");
                bw.write(";\r");
            }
        }

        if (player2.attacks.size() >0){
            for (Player.Attack a : player2.attacks){
                bw.write("Attack,\r");
                bw.write("player2,\r");
                bw.write(a.getCurrentHealth()+",\r");
                bw.write(a.getIsStun()+",\r");
                bw.write(player2.attacks.indexOf(a)+",\r");
                bw.write(a.getPoisonEffect()+",\r");
                bw.write(a.getDefense()+",\r");
                bw.write(a.isAlive()+",\r");
                bw.write(a.isShielded()+"\r");
                bw.write(";\r");
            }
        }

        bw.close();
    }

    //Requires: player1 and player2
    //Modifies: this
    //Effects: reads info for each troop and sends info to parseProduct
    public static void createFromFile(Player player1, Player player2) throws IOException {
        FileReader fr = new FileReader("Save.txt");
        BufferedReader br = new BufferedReader(fr);
        String line;
        String troopInfo = "";

        while((line = br.readLine()) != null){
            if(!line.equals(";")){
                troopInfo += line;
            } else{
                parseProduct(troopInfo, player1, player2);
                troopInfo = "";
            }
        }

        br.close();
    }
    public static void resetFile() throws IOException{
        FileWriter fw = new FileWriter("Save.txt", false);
        fw.close();

    }

    //Requires: info of troop separated by commas, player1, and player2
    //Modifies: this, supports, tanks, attacks
    //Effects: creates troops based on info from troopInfo
    private static void parseProduct(String troopInfo, Player player1, Player player2) {
        String troopType;
        String player;
        int currentHealth;
        boolean isStun;
        int classNum; //Number of troop in arraylist
        int poisonEffect;
        int defense;
        boolean alive;
        boolean isShielded;

        ArrayList<Integer> commas = new ArrayList<>();


        for (int i = 0; i<troopInfo.length(); i++){

            String current = troopInfo.substring(i,i+1);
            if (current.equals(",")){
                commas.add(i);
            }
        }
        troopType = troopInfo.substring(0, commas.get(0));
        player = troopInfo.substring(commas.get(0)+1, commas.get(1));
        currentHealth = Integer.parseInt(troopInfo.substring(commas.get(1)+1, commas.get(2)));
        isStun = Boolean.parseBoolean(troopInfo.substring(commas.get(2)+1, commas.get(3)));
        classNum = Integer.parseInt(troopInfo.substring(commas.get(3)+1, commas.get(4))); //Number of troops in arraylist
        poisonEffect = Integer.parseInt(troopInfo.substring(commas.get(4)+1, commas.get(5)));
        defense = Integer.parseInt(troopInfo.substring(commas.get(5)+1, commas.get(6)));
        alive = Boolean.parseBoolean(troopInfo.substring(commas.get(6)+1, commas.get(7)));
        isShielded = Boolean.parseBoolean(troopInfo.substring(commas.get(7)+1));

        if (player.equals("player1")){
            switch (troopType) {
                case "Support" -> {
                    new Player.Support(player1, currentHealth, isStun, classNum, poisonEffect, defense, alive, isShielded);
                }
                case "Tank" -> {
                    new Player.Tank(player1, currentHealth, isStun, classNum, poisonEffect, defense, alive, isShielded);

                }
                case "Attack" -> {
                    new Player.Attack(player1, currentHealth, isStun, classNum, poisonEffect, defense, alive, isShielded);
                }
            }
        }
        else if (player.equals("player2")){
            switch (troopType) {
                case "Support" -> {
                    new Player.Support(player2, currentHealth, isStun, classNum, poisonEffect, defense, alive, isShielded);
                }
                case "Tank" -> {
                    new Player.Tank(player2, currentHealth, isStun, classNum, poisonEffect, defense, alive, isShielded);

                }
                case "Attack" -> {
                    new Player.Attack(player2, currentHealth, isStun, classNum, poisonEffect, defense, alive, isShielded);
                }
            }
        }

    }

}
