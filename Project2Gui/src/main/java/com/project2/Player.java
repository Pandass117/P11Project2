package com.project2;

import java.util.ArrayList;
import java.util.Collections;

public class Player{
    //ArrayList of player classes
    public ArrayList<Support> supports = new ArrayList<>();
    public ArrayList<Tank> tanks = new ArrayList<>();
    public ArrayList<Attack> attacks = new ArrayList<>();
    public ArrayList<String> supportActions = new ArrayList<>();
    public ArrayList<String> tankActions = new ArrayList<>();
    public ArrayList<String> attackActions = new ArrayList<>();
    Player(){
        Collections.addAll(supportActions, "", "","");
        Collections.addAll(tankActions, "", "","");
        Collections.addAll(attackActions, "", "","");
    }



    public static class Support{
        //normal
        private int classNum;
        private int currentHealth;
        private final int maxHealth = 30;
        private int defense;
        private int defenseToAdd;
        private int maxDefense = 3;

        private int poisonEffect;
        private int damageNormal = 5;
        private boolean isStun;
        private boolean gotStunned;
        private boolean alive;
        private boolean isShielded;
        private boolean gotShielded;

        //extra
        private int heal = 5;
        private int poisonInitial = 2;
        private int poisonAfter = 1;

        public Support(Player player){
            currentHealth = maxHealth;
            isStun = false;
            poisonEffect = 0;
            defense = 2;
            defenseToAdd = 0;
            alive = true;
            isShielded = false;

            gotShielded = false;
            gotStunned = false;



            player.supports.add(this);

            classNum = player.supports.indexOf(this);

        }



        Support(Player player, int currentHealth, boolean isStun, int classNum, int poisonEffect, int defense, boolean alive, boolean isShielded){
            this.currentHealth = currentHealth;
            this.isStun = isStun;
            this.poisonEffect = poisonEffect;
            this.classNum = classNum;
            this.defense = defense;
            this.alive = alive;
            this.isShielded = isShielded;
            gotStunned = false;

            player.supports.add(classNum, this);


        }

        public void attack(Player player, String className, int classNum){

            player.damageClass(className, classNum, player, damageNormal);
        }
        private void takeDamage(int damage){

            if (isShielded){


            } else{
                int toDamage = damage-defense;
                if (toDamage<0){
                    toDamage = 0;
                }
                currentHealth -= toDamage;

            }



        }
        public void healAction(Player player, String className, int classNum){
            player.healClass(className, classNum, player, heal);

        }

        private void heal(int amt){
            currentHealth += amt;
            if (currentHealth>maxHealth){
                currentHealth = maxHealth;
            }
            poisonEffect = 0;

        }


        private void addDefense(int Amt){
            defenseToAdd += Amt;

        }

        public void poisonAction(Player player, String className, int classNum){
            player.poisonClass(className, classNum, player, poisonAfter);
            player.damageClass(className, classNum, player, poisonInitial);


        }

        //Requires:
        //Modifies: this
        //Effects: takes poison damage, checks for alive status, shield status, and stun status
        private void checkEffects(){

            //take poison damage
            defense += defenseToAdd;
            if (defense>maxDefense){
                defense = maxDefense;
            }
            defenseToAdd = 0;
            defense -= poisonEffect;



            if (defense<0){
                defense = 0;
            }
            if (currentHealth < 1){
                alive = false;
            }
            if (gotStunned){
                isStun = true;
                gotStunned = false;
            }

            if (isShielded){
                isShielded = false;
            }

            if (gotShielded){
                isShielded = true;
                gotShielded = false;
            }




        }

        public int getCurrentHealth() {
            return currentHealth;
        }

        private void setPoisonEffect(int poisonEffect) {
            if (isShielded){


            } else{
                this.poisonEffect = poisonEffect;
            }
        }





        private void shielded(){
            gotShielded = true;
        }

        public void setStun(boolean gotStunned){
            this.gotStunned = gotStunned;
        }

        public boolean getIsStun() {
            return isStun;
        }
        public void falseIsStun() {
            isStun = false;
        }

        public boolean isAlive() {
            return alive;
        }

        //unimportant getters

        public String toString() {
            return Integer.toString(classNum);
        }

        public int getDamageNormal() {
            return damageNormal;
        }

        public int getHeal() {
            return heal;
        }

        public int getPoisonInitial() {
            return poisonInitial;
        }

        public int getDefense() {
            return defense;
        }

        public int getPoisonEffect() {
            return poisonEffect;
        }

        public boolean isShielded() {
            return isShielded;
        }
    }

    public static class Tank{
        //normal
        private int classNum;
        private int currentHealth;
        private final int maxHealth = 50;
        private int defense;
        private int defenseToAdd;
        private int maxDefense = 5;

        private int poisonEffect;
        private int damageNormal = 7;
        private boolean isStun;
        private boolean gotStunned;
        private boolean alive;
        private boolean isShielded;
        private boolean gotShielded;


        //extra
        private int defenseBuff = 1;



        public Tank(Player player){
            currentHealth = maxHealth;
            isStun = false;
            poisonEffect = 0;
            defense = 3;
            defenseToAdd = 0;
            alive = true;
            isShielded = false;
            gotShielded = false;
            gotStunned = false;


            player.tanks.add(this);
            classNum = player.tanks.indexOf(this);

        }

        Tank(Player player, int currentHealth, boolean isStun, int classNum, int poisonEffect, int defense, boolean alive, boolean isShielded){
            this.currentHealth = currentHealth;
            this.isStun = isStun;
            this.poisonEffect = poisonEffect;
            this.classNum = classNum;
            this.defense = defense;
            this.alive = alive;
            this.isShielded = isShielded;
            gotStunned = false;

            player.tanks.add(classNum, this);


        }

        public void attack(Player player, String className, int classNum){

            player.damageClass(className, classNum, player, damageNormal);
        }
        private void takeDamage(int damage){

            if (isShielded){


            } else{
                int toDamage = damage-defense;
                if (toDamage<0){
                    toDamage = 0;
                }
                currentHealth -= toDamage;
            }

        }

        private void heal(int amt){
            currentHealth += amt;

            if (currentHealth>maxHealth){
                currentHealth = maxHealth;
            }
            poisonEffect = 0;

        }

        public void defenseBuff(Player player, String className, int classNum){
            player.buffDefenseClass(className, classNum, player, defenseBuff);

        }

        private void addDefense(int Amt){
            defenseToAdd += Amt;

        }
        //Requires:
        //Modifies: this
        //Effects: takes poison damage, checks for alive status, shield status, and stun status
        private void checkEffects(){

            //take poison damage
            defense += defenseToAdd;
            if (defense>maxDefense){
                defense = maxDefense;
            }
            defenseToAdd = 0;
            defense -= poisonEffect;
            if (defense<0){
                defense = 0;
            }
            if (currentHealth < 1){
                alive = false;
            }
            if (gotStunned){
                isStun = true;
                gotStunned = false;
            }

            if (isShielded){
                isShielded = false;
            }

            if (gotShielded){
                isShielded = true;
                gotShielded = false;
            }


        }
        public void shield(Player player, String className, int classNum){



            player.shieldClass(className, classNum, player);
        }

        public int getCurrentHealth() {
            return currentHealth;
        }

        private void setPoisonEffect(int poisonEffect) {
            if (isShielded){


            } else{
                this.poisonEffect = poisonEffect;
            }
        }


        private void shielded(){
            gotShielded = true;
        }
        public void setStun(boolean gotStunned){
            this.gotStunned = gotStunned;
        }


        public boolean getIsStun() {
            return isStun;
        }

        public void falseIsStun() {
            isStun = false;
        }

        public boolean isAlive() {
            return alive;
        }

        //unimportant getters

        public String toString() {
            return Integer.toString(classNum);
        }

        public int getDamageNormal() {
            return damageNormal;
        }



        public int getDefense() {
            return defense;
        }

        public int getPoisonEffect() {
            return poisonEffect;
        }

        public boolean isShielded() {
            return isShielded;
        }
    }




    public static class Attack{
        //normal
        private int classNum;
        private int currentHealth;
        private final int maxHealth = 40;
        private int defense;
        private int defenseToAdd;
        private int maxDefense = 3;

        private int poisonEffect;
        private int damageNormal = 10;
        private boolean isStun;
        private boolean gotStunned;
        private boolean alive;
        private boolean isShielded;
        private boolean gotShielded;



        //extra
        private int multiDamage = 4;
        private final int multiNum = 3;
        private int stunDamage = 3;

        public Attack(Player player){
            currentHealth = maxHealth;
            isStun = false;
            poisonEffect = 0;
            defense = 2;
            defenseToAdd = 0;
            alive = true;
            isShielded = false;
            gotShielded = false;
            gotStunned = false;


            player.attacks.add(this);
            classNum = player.attacks.indexOf(this);




        }
        Attack(Player player, int currentHealth, boolean isStun, int classNum, int poisonEffect, int defense, boolean alive, boolean isShielded){
            this.currentHealth = currentHealth;
            this.isStun = isStun;
            this.poisonEffect = poisonEffect;
            this.classNum = classNum;
            this.defense = defense;
            this.alive = alive;
            this.isShielded = isShielded;
            gotStunned = false;

            player.attacks.add(classNum, this);


        }

        public void attack(Player player, String className, int classNum){

            player.damageClass(className, classNum, player, damageNormal);
        }
        private void takeDamage(int damage){

            if (isShielded){


            } else{
                int toDamage = damage-defense;
                if (toDamage<0){
                    toDamage = 0;
                }
                currentHealth -= toDamage;
            }

        }

        private void heal(int amt){
            currentHealth += amt;
            if (currentHealth>maxHealth){
                currentHealth = maxHealth;
            }
            poisonEffect = 0;

        }



        private void addDefense(int Amt){
            defenseToAdd += Amt;

        }

        public void multiAttack(Player player, ArrayList<String> classNames, ArrayList<Integer> classNums){

            for (int i = 0; i<multiNum; i++){
                String className = classNames.get(i);
                int classNum = classNums.get(i);
                player.damageClass(className, classNum, player, multiDamage);

            }

        }

        public int getMultiNum() {
            return multiNum;
        }

        public void stunAttack(Player player, String className, int classNum){
            player.damageClass(className, classNum, player, stunDamage);
            player.stunClass(className, classNum, player);

        }
        //Requires:
        //Modifies: this
        //Effects: takes poison damage, checks for alive status, shield status, and stun status
        private void checkEffects(){

            //take poison damage
            defense += defenseToAdd;
            if (defense>maxDefense){
                defense = maxDefense;
            }
            defenseToAdd = 0;
            defense -= poisonEffect;
            if (defense<0){
                defense = 0;
            }
            if (currentHealth < 1){
                alive = false;
            }
            if (gotStunned){
                isStun = true;
                gotStunned = false;
            }

            if (isShielded){
                isShielded = false;
            }

            if (gotShielded){
                isShielded = true;
                gotShielded = false;
            }


        }

        public int getCurrentHealth() {
            return currentHealth;
        }

        private void setPoisonEffect(int poisonEffect) {
            if (isShielded){


            } else{
                this.poisonEffect = poisonEffect;
            }

        }



        private void shielded(){
            gotShielded = true;
        }


        public void setStun(boolean gotStunned){
            this.gotStunned = gotStunned;
        }

        public boolean getIsStun() {
            return isStun;
        }

        public void falseIsStun() {
            isStun = false;
        }

        public boolean isAlive() {
            return alive;
        }

        //unimportant getters

        public String toString() {
            return Integer.toString(classNum);
        }

        public int getDamageNormal() {
            return damageNormal;
        }

        public int getMultiDamage() {
            return multiDamage;
        }

        public int getStunDamage() {
            return stunDamage;
        }

        public int getDefense() {
            return defense;
        }

        public int getPoisonEffect() {
            return poisonEffect;
        }

        public boolean isShielded() {
            return isShielded;
        }
    }

    //Requires: name of troop to affect, index of troop to affect in its ArrayList, Player of troop, amount of damage to inflict
    //Modifies: this
    //Effects: finds troop from required info, activate takeDamage for that troop
    public void damageClass(String className, int index, Player player, int damage){

        switch (className) {
            case "Support" -> {

                Support classReturn = player.supports.get(index);

                classReturn.takeDamage(damage);


            }
            case "Tank" -> {
                Tank classReturn = player.tanks.get(index);

                classReturn.takeDamage(damage);


            }
            case "Attack" -> {
                Attack classReturn = player.attacks.get(index);

                classReturn.takeDamage(damage);


            }
        }
    }

    //Requires: name of troop to affect, index of troop to affect in its ArrayList, Player of troop, amount of damage to heal
    //Modifies: this
    //Effects: finds troop from required info, activates heal method for that troop
    public void healClass(String className, int index, Player player, int healAmt){

        switch (className) {
            case "Support" -> {

                Support classReturn = player.supports.get(index);

                classReturn.heal(healAmt);



            }
            case "Tank" -> {
                Tank classReturn = player.tanks.get(index);

                classReturn.heal(healAmt);



            }
            case "Attack" -> {
                Attack classReturn = player.attacks.get(index);

                classReturn.heal(healAmt);



            }
        }

    }

    //Requires: name of troop to affect, index of troop to affect in its ArrayList, Player of troop, amount of defense to buff
    //Modifies: this
    //Effects: finds troop from required info, activates addDefense for that troop
    public void buffDefenseClass(String className, int index, Player player, int buffAmt){

        switch (className) {
            case "Support" -> {

                Support classReturn = player.supports.get(index);

                classReturn.addDefense(buffAmt);

            }
            case "Tank" -> {
                Tank classReturn = player.tanks.get(index);

                classReturn.addDefense(buffAmt);


            }
            case "Attack" -> {
                Attack classReturn = player.attacks.get(index);

                classReturn.addDefense(buffAmt);


            }
        }

    }

    //Requires: name of troop to affect, index of troop to affect in its ArrayList, Player of troop, amount of poison
    //Modifies: this
    //Effects: finds troop from required info, activates setPoisonEffect for that troop
    public void poisonClass(String className, int index, Player player, int poisonAmt){
        switch (className) {
            case "Support" -> {

                Support classReturn = player.supports.get(index);

                classReturn.setPoisonEffect(poisonAmt);


            }
            case "Tank" -> {
                Tank classReturn = player.tanks.get(index);

                classReturn.setPoisonEffect(poisonAmt);


            }
            case "Attack" -> {
                Attack classReturn = player.attacks.get(index);

                classReturn.setPoisonEffect(poisonAmt);


            }
        }
    }

    //Requires: name of troop to affect, index of troop to affect in its ArrayList, Player of troop
    //Modifies: this
    //Effects: finds troop from required info, activates setStun for that troop
    public void stunClass(String className, int index, Player player){
        switch (className) {
            case "Support" -> {

                Support classReturn = player.supports.get(index);

                classReturn.setStun(true);


            }
            case "Tank" -> {
                Tank classReturn = player.tanks.get(index);

                classReturn.setStun(true);


            }
            case "Attack" -> {
                Attack classReturn = player.attacks.get(index);

                classReturn.setStun(true);


            }
        }
    }

    //Requires: name of troop to affect, index of troop to affect in its ArrayList, Player of troop
    //Modifies: this
    //Effects: finds troop from required info, activates shielded method for that troop
    public void shieldClass(String className, int index, Player player){

        switch (className) {
            case "Support" -> {

                Support classReturn = player.supports.get(index);

                classReturn.shielded();


            }
            case "Tank" -> {
                Tank classReturn = player.tanks.get(index);

                classReturn.shielded();


            }
            case "Attack" -> {
                Attack classReturn = player.attacks.get(index);

                classReturn.shielded();


            }
        }

    }

    //Requires:
    //Modifies: this
    //Effects: checks all supports, tanks, and attacks for effects
    public void finishTurn(){
        for (Support s : supports){
            s.checkEffects();
        }
        for (Tank t : tanks){
            t.checkEffects();
        }
        for (Attack a : attacks){
            a.checkEffects();
        }
    }
}
