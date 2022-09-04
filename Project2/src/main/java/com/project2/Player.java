package com.project2;

import java.util.ArrayList;

public class Player{
    //ArrayList of player classes
    public ArrayList<Support> supports = new ArrayList<>();
    public ArrayList<Tank> tanks = new ArrayList<>();
    public ArrayList<Attack> attacks = new ArrayList<>();
    public ArrayList<String> supportActions = new ArrayList<>();
    public ArrayList<String> tankActions = new ArrayList<>();
    public ArrayList<String> attackActions = new ArrayList<>();



    public static class Support{
        //normal
        public static int classNums = 0;
        private int classNum;
        private int currentHealth;
        private final int maxHealth = 50;
        private int defense;
        private int defenseToAdd;
        private int maxDefense = 5;

        private int poisonEffect;
        private int damageNormal = 15;
        private boolean isStun;
        private boolean gotStunned;
        private boolean alive;
        private boolean isShielded;
        private boolean gotShielded;

        //extra
        private int heal = 5;
        private int poisonInitial = 1;
        private int poisonAfter = 11;

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

            classNum = classNums;
            classNums++;
            player.supports.add(this);

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

        public void attack(Player player, String className, int classNum, int damage){
            //className = "support"; choose
            //classNum = 1; choose
            //damage = 5;choose damage type
            player.damageClass(className, classNum, player, damage);
        }
        private void takeDamage(int damage){
            if (isShielded){
                System.out.println("shielded");

            } else{
                int toDamage = damage-defense;
                if (toDamage<0){
                    toDamage = 0;
                }
                currentHealth -= toDamage;

            }



        }
        public void healAction(Player player, String className, int classNum, int healAmt){
            player.healClass(className, classNum, player, healAmt);

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

        public void poisonAction(Player player, String className, int classNum, int damage, int poisonAmt){
            player.poisonClass(className, classNum, player, poisonAmt);
            player.damageClass(className, classNum, player, damage);


        }

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
                System.out.println("shielded");

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
        public static int classNums = 0;
        private int classNum;
        private int currentHealth;
        private final int maxHealth = 50;
        private int defense;
        private int defenseToAdd;
        private int maxDefense = 5;

        private int poisonEffect;
        private int damageNormal = 15;
        private boolean isStun;
        private boolean gotStunned;
        private boolean alive;
        private boolean isShielded;
        private boolean gotShielded;


        //extra
        private int defenseBuff;



        public Tank(Player player){
            currentHealth = maxHealth;
            isStun = false;
            poisonEffect = 0;
            defense = 2;
            defenseToAdd = 0;
            alive = true;
            isShielded = false;
            gotShielded = false;
            gotStunned = false;

            classNum = classNums;
            classNums++;
            player.tanks.add(this);

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

        public void attack(Player player, String className, int classNum, int damage){

            player.damageClass(className, classNum, player, damage);
        }
        private void takeDamage(int damage){
            if (isShielded){
                System.out.println("shielded");

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

        public void defenseBuff(Player player, String className, int classNum, int buffAmt){
            player.buffDefenseClass(className, classNum, player, buffAmt);

        }

        private void addDefense(int Amt){
            defenseToAdd += Amt;

        }
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
                System.out.println("shielded");

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
        public static int classNums = 0;
        private int classNum;
        private int currentHealth;
        private final int maxHealth = 50;
        private int defense;
        private int defenseToAdd;
        private int maxDefense = 5;

        private int poisonEffect;
        private int damageNormal = 15;
        private boolean isStun;
        private boolean gotStunned;
        private boolean alive;
        private boolean isShielded;
        private boolean gotShielded;



        //extra
        private int multiDamage;
        private final int multiNum = 3;
        private int stunDamage;

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

            classNum = classNums;
            classNums++;
            player.attacks.add(this);




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

        public void attack(Player player, String className, int classNum, int damage){

            player.damageClass(className, classNum, player, damage);
        }
        private void takeDamage(int damage){
            if (isShielded){
                System.out.println("shielded");

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

        public void multiAttack(Player player, ArrayList<String> classNames, ArrayList<Integer> classNums, int damage){

            for (int i = 0; i<multiNum; i++){
                String className = classNames.get(i);
                int classNum = classNums.get(i);
                player.damageClass(className, classNum, player, damage);

            }

        }

        public int getMultiNum() {
            return multiNum;
        }

        public void stunAttack(Player player, String className, int classNum, int damage){
            player.damageClass(className, classNum, player, damage);
            player.stunClass(className, classNum, player);

        }
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
                System.out.println("shielded");

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
    public void damageClass(String className, int index, Player player, int damage){

        switch (className) {
            case "support" -> {

                Support classReturn = player.supports.get(index);

                classReturn.takeDamage(damage);


            }
            case "tank" -> {
                Tank classReturn = player.tanks.get(index);

                classReturn.takeDamage(damage);


            }
            case "attack" -> {
                Attack classReturn = player.attacks.get(index);

                classReturn.takeDamage(damage);


            }
        }
    }

    public void healClass(String className, int index, Player player, int healAmt){

        switch (className) {
            case "support" -> {

                Support classReturn = player.supports.get(index);

                classReturn.heal(healAmt);



            }
            case "tank" -> {
                Tank classReturn = player.tanks.get(index);

                classReturn.heal(healAmt);



            }
            case "attack" -> {
                Attack classReturn = player.attacks.get(index);

                classReturn.heal(healAmt);



            }
        }

    }

    public void buffDefenseClass(String className, int index, Player player, int buffAmt){

        switch (className) {
            case "support" -> {

                Support classReturn = player.supports.get(index);

                classReturn.addDefense(buffAmt);

            }
            case "tank" -> {
                Tank classReturn = player.tanks.get(index);

                classReturn.addDefense(buffAmt);


            }
            case "attack" -> {
                Attack classReturn = player.attacks.get(index);

                classReturn.addDefense(buffAmt);


            }
        }

    }
    public void poisonClass(String className, int index, Player player, int poisonAmt){
        switch (className) {
            case "support" -> {

                Support classReturn = player.supports.get(index);

                classReturn.setPoisonEffect(poisonAmt);


            }
            case "tank" -> {
                Tank classReturn = player.tanks.get(index);

                classReturn.setPoisonEffect(poisonAmt);


            }
            case "attack" -> {
                Attack classReturn = player.attacks.get(index);

                classReturn.setPoisonEffect(poisonAmt);


            }
        }
    }

    public void stunClass(String className, int index, Player player){
        switch (className) {
            case "support" -> {

                Support classReturn = player.supports.get(index);

                classReturn.setStun(true);


            }
            case "tank" -> {
                Tank classReturn = player.tanks.get(index);

                classReturn.setStun(true);


            }
            case "attack" -> {
                Attack classReturn = player.attacks.get(index);

                classReturn.setStun(true);


            }
        }
    }
    public void shieldClass(String className, int index, Player player){

        switch (className) {
            case "support" -> {

                Support classReturn = player.supports.get(index);

                classReturn.shielded();


            }
            case "tank" -> {
                Tank classReturn = player.tanks.get(index);

                classReturn.shielded();


            }
            case "attack" -> {
                Attack classReturn = player.attacks.get(index);

                classReturn.shielded();


            }
        }

    }
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
