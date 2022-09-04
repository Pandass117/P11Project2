package com.project2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;
import java.util.*;

public class Controller {
    public static Player player1 = new Player();
    public static Player player2 = new Player();
//player 1
    public Button Support1E1;
    public Button Tank1E1;
    public Button Attack1E1;

    public Button Support2E1;
    public Button Tank2E1;
    public Button Attack2E1;

    public Button Support3E1;
    public Button Tank3E1;
    public Button Attack3E1;
//player 2
    public Button Support1E2;
    public Button Tank1E2;
    public Button Attack1E2;

    public Button Support2E2;
    public Button Tank2E2;
    public Button Attack2E2;

    public Button Support3E2;
    public Button Tank3E2;
    public Button Attack3E2;
    public VBox Top1;
    public VBox Top2;
    public VBox Top3;
    public VBox Top4;
    public VBox Top5;
    public VBox Bottom1;
    public VBox Bottom2;
    public VBox Bottom3;
    public VBox Bottom4;
    public VBox Bottom5;
    public Button btnNewGame;
    public Label lblTroopName;
    public Button option1;
    public Button option2;
    public Button option3;
    public Label lblDescription;
    public Button btnChoose;
    public Label lblDescriptionContent;
    public Label lblOutcome;
    public Button btnTie;
    public Button btnLoad;

    private Button optionChosen;

    private Stage stage;
    private Scene scene;
    private Parent root;
    private static boolean isTitle = true;

    public static ArrayList<String> player1Troops = new ArrayList<>();
    public static ArrayList<String> player2Troops = new ArrayList<>();

    public static ArrayList<Player.Support> supportsPending1 = new ArrayList<>();
    public static ArrayList<Player.Tank> tanksPending1 = new ArrayList<>();
    public static ArrayList<Player.Attack> attacksPending1 = new ArrayList<>();

    public static ArrayList<Player.Support> supportsPending2 = new ArrayList<>();
    public static ArrayList<Player.Tank> tanksPending2 = new ArrayList<>();
    public static ArrayList<Player.Attack> attacksPending2 = new ArrayList<>();

    public static ArrayList<VBox> topBox = new ArrayList<>();
    public static ArrayList<VBox> bottomBox = new ArrayList<>();


    private ArrayList<Button> btns = new ArrayList<>();
    private boolean nextTroop = true;
    private boolean gameEnd = false;
    private static boolean loading = false;
    private String multiAttackActions = "";

    @FXML

    //Requires:
    //Modifies: this,
    //Effects: sets up screen depending on if the scene is the title or the game, if the scene is the game, game is started
    public void initialize() throws IOException {

        if (isTitle){
            Collections.addAll(btns, Support1E1, Tank1E1, Attack1E1, Support2E1, Tank2E1, Attack2E1, Support3E1, Tank3E1, Attack3E1, Support1E2, Tank1E2, Attack1E2, Support2E2, Tank2E2, Attack2E2, Support3E2, Tank3E2, Attack3E2);
            for (Button btn:btns){
                btn.setBackground(new Background(new BackgroundFill(Color.WHITESMOKE, null, null)));
                btn.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(10), null)));


            }
            FileReader fr = new FileReader("Save.txt");
            BufferedReader br = new BufferedReader(fr);
            if (br.readLine() == null){
                btnLoad.setDisable(true);
            } else{
                btnLoad.setDisable(false);
            }
            br.close();
            btnNewGame.setDisable(true);

        } else {
            Collections.addAll(topBox, Top1, Top2, Top3, Top4, Top5);
            Collections.addAll(bottomBox, Bottom1, Bottom2, Bottom3, Bottom4, Bottom5);
            if (loading){
                SaveFile.createFromFile(player1, player2);

            } else{

                newCreateCharacter(player1Troops, player2Troops);
            }

            gameTurn();

        }



    }



    //Requires: "new game" button to be pressed
    //Modifies: this
    //Effects: sets up the scene using game.fxml, sets loading to false
    public void newGame(ActionEvent event) throws IOException{
        loading = false;
        isTitle = false;
        root = FXMLLoader.load(getClass().getResource("game.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);

        stage.setScene(scene);
        stage.show();

    }
    //Requires: "load game" button to be pressed
    //Modifies: this
    //Effects: sets up the scene using game.fxml, sets loading to true
    public void loadGame(ActionEvent actionEvent) throws IOException{
        loading = true;
        isTitle = false;
        root = FXMLLoader.load(getClass().getResource("game.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
    //Requires: ArrayList of player 1 troops and player 2 troops, with the strings being "Support", "Tank", or "Attack"
    //Modifies: this, supports, tanks, attacks
    //Effects: creates new support, tank, and attack troops with the default values
    private static void newCreateCharacter(ArrayList<String> player1Troops, ArrayList<String> player2Troops){
        for (String s : player1Troops){
            switch (s) {
                case "Support" -> {
                    new Player.Support(player1);
                }
                case "Tank" -> {
                    new Player.Tank(player1);

                }
                case "Attack" -> {
                    new Player.Attack(player1);
                }
            }
        }
        for (String s : player2Troops){
            switch (s) {
                case "Support" -> {
                    new Player.Support(player2);
                }
                case "Tank" -> {
                    new Player.Tank(player2);

                }
                case "Attack" -> {
                    new Player.Attack(player2);
                }
            }
        }
        resetTroopPends();

    }
    //Requires: title screen troop buttons to be pressed
    //Modifies: this
    //Effects: adds chosen buttons to player 1 or 2 ArrayLists, adds max of 5 troops for each player, enables new game button when max is reached
    public void chooseChar(ActionEvent actionEvent) {

        int playerNum;
        String troopType;
        Button btn = (Button) actionEvent.getSource();
        String btnName = btn.getText();
        Color color = (Color)btn.getBackground().getFills().get(0).getFill();

        playerNum = Integer.parseInt(btnName.substring(btnName.length()-1));
        troopType = btnName.substring(0, btnName.length()-1);

        if (color != Color.CYAN){
            if (playerNum == 1){
                if (player1Troops.size() < 5){
                    player1Troops.add(troopType);
                    btn.setBackground(new Background(new BackgroundFill(Color.CYAN, null, null)));

                }
            } else{
                if (player2Troops.size() < 5) {
                    player2Troops.add(troopType);
                    btn.setBackground(new Background(new BackgroundFill(Color.CYAN, null, null)));

                }
            }
        } else{
            if (playerNum == 1){

                player1Troops.remove(troopType);
                btn.setBackground(new Background(new BackgroundFill(Color.WHITESMOKE, null, null)));


            } else{

                player2Troops.remove(troopType);

                btn.setBackground(new Background(new BackgroundFill(Color.WHITESMOKE, null, null)));
            }

        }

        if (player1Troops.size() > 4 && player2Troops.size() > 4){
            btnNewGame.setDisable(false);
        } else{
            btnNewGame.setDisable(true);
        }
    }

    //Requires:
    //Modifies: this, supportActions, tankActions, attackActions
    //Effects: resets supportActions, tankActions, attackActions for player1 and player2, starts choose actions sequence
    public void startOfTurn() throws IOException {

        player1.supportActions.clear();
        Collections.addAll(player1.supportActions, "", "","");
        player1.tankActions.clear();
        Collections.addAll(player1.tankActions, "", "","");
        player1.attackActions.clear();
        Collections.addAll(player1.attackActions, "", "","");

        player2.supportActions.clear();
        Collections.addAll(player2.supportActions, "", "","");
        player2.tankActions.clear();
        Collections.addAll(player2.tankActions, "", "","");
        player2.attackActions.clear();
        Collections.addAll(player2.attackActions, "", "","");

        resetTroopPends();
        nextTroop(player1);
        btnChoose.setDisable(true);

    }
    //early test for detecting VBoxes
    //Requires: select buttons to be pressed
    //Modifies: this
    //Effects: returns the VBox that the pressed button is in
    private VBox detectVBox(ActionEvent actionEvent){
        Button btn = (Button) actionEvent.getSource();

        for (VBox box: topBox){
            Button button = (Button) box.getChildren().get(2);
            if (button.equals(btn)){
                return box;
            }
        }


        for (VBox box: bottomBox){
            Button button = (Button) box.getChildren().get(0);
            if (button.equals(btn)){
                return box;
            }
        }


        return null;
    }


    //Requires: index of troop in supports and the number of the player
    //Modifies: this
    //Effects: shows information of troop selected and shows possible actions of troop
    private void showSupportScreen(int troopNumber, int playerNum){
        lblTroopName.setText("Support"+playerNum+" "+troopNumber);
        option1.setText("Heal");
        option2.setText("Attack");
        option3.setText("Poison");
        Player.Support troop;
        if (playerNum == 1){
            troop = player1.supports.get(troopNumber);
        } else {
            troop = player2.supports.get(troopNumber);
        }


        lblDescriptionContent.setText("Poison Effect:\t"+troop.getPoisonEffect()+
                "\nDefense Level:\t"+troop.getDefense()+"\nStats:"+
                "\nAttack Damage:\t"+troop.getDamageNormal()+
                "\nPoison Damage:\t"+troop.getPoisonInitial()+
                "\nHeal Amount:\t"+troop.getHeal());

    }
    //Requires: index of troop in tanks and the number of the player
    //Modifies: this
    //Effects: shows information of troop selected and shows possible actions of troop
    private void showTankScreen(int troopNumber, int playerNum){
        lblTroopName.setText("Tank"+playerNum+" "+troopNumber);
        option1.setText("Defend");
        option2.setText("Attack");
        option3.setText("Shield");
        Player.Tank troop;
        if (playerNum == 1){
            troop = player1.tanks.get(troopNumber);
        } else {
            troop = player2.tanks.get(troopNumber);
        }


        lblDescriptionContent.setText("Poison Effect:\t"+troop.getPoisonEffect()+
                "\nDefense Level:\t"+troop.getDefense()+"\nStats:"+
                "\nAttack Damage:\t"+troop.getDamageNormal());

    }
    //Requires: index of troop in attacks and the number of the player
    //Modifies: this
    //Effects: shows information of troop selected and shows possible actions of troop
    private void showAttackScreen(int troopNumber, int playerNum){
        lblTroopName.setText("Attack"+playerNum+" "+troopNumber);
        option1.setText("Stun");
        option2.setText("Attack");
        option3.setText("Multi-attack");
        Player.Attack troop;
        if (playerNum == 1){
            troop = player1.attacks.get(troopNumber);
        } else {
            troop = player2.attacks.get(troopNumber);
        }


        lblDescriptionContent.setText("Poison Effect:\t"+troop.getPoisonEffect()+
                "\nDefense Level:\t"+troop.getDefense()+"\nStats:"+
                "\nAttack Damage:\t"+troop.getDamageNormal()+
                "\nStun Damage:\t"+troop.getStunDamage()+
                "\nMulti-attack Damage (per troop):\t"+troop.getMultiDamage());

    }
    //Requires: troop name to be "Support", "Tank", or "Attack"
    //index of troop in its ArrayList, and the number of the player
    //Modifies: this
    //Effects: shows information of troop selected and shows possible actions of troop
    public void showScreen(String name, int troopNumber, int playerNum){
        switch (name) {
            case "Support":
                showSupportScreen(troopNumber, playerNum);

                break;

            case "Tank":
                showTankScreen(troopNumber, playerNum);
                break;

            case "Attack":
                showAttackScreen(troopNumber, playerNum);

                break;
        }
    }


    //Requires:
    //Modifies: this
    //Effects: refreshes troop information: health, name, alive, stunned, poisoned, and shielded
    public void gameTurn() throws IOException {

        int temp = 0;
        if (player1.supports.size() >0){
            for (Player.Support s : player1.supports){
                ((Label)topBox.get(temp).getChildren().get(0)).setText("Support"+player1.supports.indexOf(s));
                ((Label)topBox.get(temp).getChildren().get(1)).setText(Integer.toString(s.getCurrentHealth()));
                temp++;
            }
        }
        if (player1.tanks.size() >0){

            for (Player.Tank t : player1.tanks){
                ((Label)topBox.get(temp).getChildren().get(0)).setText("Tank"+player1.tanks.indexOf(t));
                ((Label)topBox.get(temp).getChildren().get(1)).setText(Integer.toString(t.getCurrentHealth()));
                temp++;
            }
        }
        if (player1.attacks.size() >0){
            for (Player.Attack a : player1.attacks){
                ((Label)topBox.get(temp).getChildren().get(0)).setText("Attack"+player1.attacks.indexOf(a));
                ((Label)topBox.get(temp).getChildren().get(1)).setText(Integer.toString(a.getCurrentHealth()));
                temp++;
            }

        }

        temp = 0;
        if (player2.supports.size() >0){
            for (Player.Support s : player2.supports){
                ((Label)bottomBox.get(temp).getChildren().get(2)).setText("Support"+player2.supports.indexOf(s));
                ((Label)bottomBox.get(temp).getChildren().get(1)).setText(Integer.toString(s.getCurrentHealth()));
                temp++;
            }
        }
        if (player2.tanks.size() >0){

            for (Player.Tank t : player2.tanks){
                ((Label)bottomBox.get(temp).getChildren().get(2)).setText("Tank"+player2.tanks.indexOf(t));
                ((Label)bottomBox.get(temp).getChildren().get(1)).setText(Integer.toString(t.getCurrentHealth()));
                temp++;
            }
        }
        if (player2.attacks.size() >0){
            for (Player.Attack a : player2.attacks){
                ((Label)bottomBox.get(temp).getChildren().get(2)).setText("Attack"+player2.attacks.indexOf(a));
                ((Label)bottomBox.get(temp).getChildren().get(1)).setText(Integer.toString(a.getCurrentHealth()));
                temp++;
            }

        }

        //aesthetic
        for (VBox box : topBox){
            ((Label) box.getChildren().get(1)).setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
            ((Label) box.getChildren().get(0)).setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
            ((Label) box.getChildren().get(0)).setTextFill(Color.BLACK);
        }

        for (VBox box : bottomBox){
            ((Label) box.getChildren().get(1)).setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
            ((Label) box.getChildren().get(2)).setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
            ((Label) box.getChildren().get(2)).setTextFill(Color.BLACK);
        }
        if (player1.supports.size() >0){

            for (Player.Support s : player1.supports){
                VBox tempBox = findFromName("Support", player1.supports.indexOf(s), player1);
                if (s.getIsStun()){

                    ((Label) tempBox.getChildren().get(1)).setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, null, null)));
                }
                if(s.getPoisonEffect()>0){
                    ((Label) tempBox.getChildren().get(0)).setBackground(new Background(new BackgroundFill(Color.MEDIUMPURPLE, null, null)));
                }
                if(s.isShielded()){
                    ((Label) tempBox.getChildren().get(0)).setTextFill(Color.ORANGERED);

                }
                if(!s.isAlive()){
                    ((Label) tempBox.getChildren().get(1)).setBackground(new Background(new BackgroundFill(Color.DARKGREY, null, null)));
                }

            }
        }


        if (player1.tanks.size() >0){

            for (Player.Tank t : player1.tanks){
                VBox tempBox = findFromName("Tank", player1.tanks.indexOf(t), player1);
                if (t.getIsStun()){

                    ((Label) tempBox.getChildren().get(1)).setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, null, null)));
                }
                if(t.getPoisonEffect()>0){
                    ((Label) tempBox.getChildren().get(0)).setBackground(new Background(new BackgroundFill(Color.MEDIUMPURPLE, null, null)));
                }
                if(t.isShielded()){
                    ((Label) tempBox.getChildren().get(0)).setTextFill(Color.ORANGERED);

                }
                if(!t.isAlive()){
                    ((Label) tempBox.getChildren().get(1)).setBackground(new Background(new BackgroundFill(Color.DARKGREY, null, null)));
                }
            }
        }


        if (player1.attacks.size() >0){

            for (Player.Attack a : player1.attacks){
                VBox tempBox = findFromName("Attack", player1.attacks.indexOf(a), player1);
                if (a.getIsStun()){

                    ((Label) tempBox.getChildren().get(1)).setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, null, null)));
                }
                if(a.getPoisonEffect()>0){
                    ((Label) tempBox.getChildren().get(0)).setBackground(new Background(new BackgroundFill(Color.MEDIUMPURPLE, null, null)));
                }
                if(a.isShielded()){
                    ((Label) tempBox.getChildren().get(0)).setTextFill(Color.ORANGERED);

                }
                if(!a.isAlive()){
                    ((Label) tempBox.getChildren().get(1)).setBackground(new Background(new BackgroundFill(Color.DARKGREY, null, null)));
                }
            }
        }

        if (player2.supports.size() >0){

            for (Player.Support s : player2.supports){
                VBox tempBox = findFromName("Support", player2.supports.indexOf(s), player2);
                if (s.getIsStun()){

                    ((Label) tempBox.getChildren().get(1)).setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, null, null)));
                }
                if(s.getPoisonEffect()>0){
                    ((Label) tempBox.getChildren().get(2)).setBackground(new Background(new BackgroundFill(Color.MEDIUMPURPLE, null, null)));
                }
                if(s.isShielded()){
                    ((Label) tempBox.getChildren().get(2)).setTextFill(Color.ORANGERED);

                }
                if(!s.isAlive()){
                    ((Label) tempBox.getChildren().get(1)).setBackground(new Background(new BackgroundFill(Color.DARKGREY, null, null)));
                }

            }
        }


        if (player2.tanks.size() >0){

            for (Player.Tank t : player2.tanks){
                VBox tempBox = findFromName("Tank", player2.tanks.indexOf(t), player2);
                if (t.getIsStun()){

                    ((Label) tempBox.getChildren().get(1)).setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, null, null)));
                }
                if(t.getPoisonEffect()>0){
                    ((Label) tempBox.getChildren().get(2)).setBackground(new Background(new BackgroundFill(Color.MEDIUMPURPLE, null, null)));
                }
                if(t.isShielded()){
                    ((Label) tempBox.getChildren().get(2)).setTextFill(Color.ORANGERED);

                }
                if(!t.isAlive()){
                    ((Label) tempBox.getChildren().get(1)).setBackground(new Background(new BackgroundFill(Color.DARKGREY, null, null)));
                }
            }
        }


        if (player2.attacks.size() >0){

            for (Player.Attack a : player2.attacks){
                VBox tempBox = findFromName("Attack", player2.attacks.indexOf(a), player2);
                if (a.getIsStun()){

                    ((Label) tempBox.getChildren().get(1)).setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, null, null)));
                }
                if(a.getPoisonEffect()>0){
                    ((Label) tempBox.getChildren().get(2)).setBackground(new Background(new BackgroundFill(Color.MEDIUMPURPLE, null, null)));
                }
                if(a.isShielded()){
                    ((Label) tempBox.getChildren().get(2)).setTextFill(Color.ORANGERED);

                }
                if(!a.isAlive()){
                    ((Label) tempBox.getChildren().get(1)).setBackground(new Background(new BackgroundFill(Color.DARKGREY, null, null)));
                }
            }
        }
        startOfTurn();
    }

    //Requires: "choose" button to be pressed
    //Modifies: this
    //Effects: disables itself, moves on to the next troop
    public void chooseAction(ActionEvent actionEvent) throws IOException {
        btnChoose.setDisable(true);

        nextTroop(player1);

    }

    //Requires: player 1 or 2
    //Modifies: this
    //Effects: finds current troop and indicates by changing colour
    //if all troops have gone then move on to attack sequence and then the end of turn
    //save the current states, if there is a game outcome then reset save file, and end game
    private void nextTroop(Player player) throws IOException {
        multiAttackActions = "";
        disableVBoxes(true, true);
        disableVBoxes(true, false);

        if (player.equals(player1)) {
            if (supportsPending1.size() >0){

                Comparator<Player.Support> comparator = new Comparator<Player.Support>() {
                    @Override
                    public int compare(Player.Support o1, Player.Support o2) {
                        return 0;
                    }
                };
                supportsPending1.sort(comparator);

                if (supportsPending1.get(0).getIsStun() || !supportsPending1.get(0).isAlive()){

                    supportsPending1.remove(0);

                    chooseAction(new ActionEvent());
                } else{
                    showScreen("Support", player1.supports.indexOf(supportsPending1.get(0)), 1);
                    resetColourBox();
                    VBox temp = findFromName("Support", player1.supports.indexOf(supportsPending1.get(0)), player1);
                    temp.setBackground(new Background(new BackgroundFill(Color.GOLD, null, null)));
                    supportsPending1.remove(0);

                }

            }


            else if (tanksPending1.size() >0){

                Comparator<Player.Tank> comparator = new Comparator<Player.Tank>() {
                    @Override
                    public int compare(Player.Tank o1, Player.Tank o2) {
                        return 0;
                    }
                };
                tanksPending1.sort(comparator);

                if (tanksPending1.get(0).getIsStun() || !tanksPending1.get(0).isAlive()){

                    tanksPending1.remove(0);
                    chooseAction(new ActionEvent());
                } else{
                    showScreen("Tank", player1.tanks.indexOf(tanksPending1.get(0)), 1);
                    resetColourBox();
                    VBox temp = findFromName("Tank", player1.tanks.indexOf(tanksPending1.get(0)), player1);
                    temp.setBackground(new Background(new BackgroundFill(Color.GOLD, null, null)));
                    tanksPending1.remove(0);
                }

            }




            else if (attacksPending1.size() >0){
                Comparator<Player.Attack> comparator = new Comparator<Player.Attack>() {
                    @Override
                    public int compare(Player.Attack o1, Player.Attack o2) {
                        return 0;
                    }
                };
                attacksPending1.sort(comparator);

                if (attacksPending1.get(0).getIsStun() || !attacksPending1.get(0).isAlive()){

                    attacksPending1.remove(0);
                    chooseAction(new ActionEvent());
                } else{
                    showScreen("Attack", player1.attacks.indexOf(attacksPending1.get(0)), 1);
                    resetColourBox();
                    VBox temp = findFromName("Attack", player1.attacks.indexOf(attacksPending1.get(0)), player1);
                    temp.setBackground(new Background(new BackgroundFill(Color.GOLD, null, null)));
                    attacksPending1.remove(0);
                }


            }
            else{
                nextTroop(player2);
            }
        }
        else{
            if (supportsPending2.size() >0){
                Comparator<Player.Support> comparator = new Comparator<Player.Support>() {
                    @Override
                    public int compare(Player.Support o1, Player.Support o2) {
                        return 0;
                    }
                };
                supportsPending2.sort(comparator);
                if (supportsPending2.get(0).getIsStun() || !supportsPending2.get(0).isAlive()){

                    supportsPending2.remove(0);
                    chooseAction(new ActionEvent());
                } else{
                    showScreen("Support", player2.supports.indexOf(supportsPending2.get(0)), 2);
                    resetColourBox();
                    VBox temp = findFromName("Support", player2.supports.indexOf(supportsPending2.get(0)), player2);
                    temp.setBackground(new Background(new BackgroundFill(Color.GOLD, null, null)));
                    supportsPending2.remove(0);
                }



            }


            else if (tanksPending2.size() >0){

                Comparator<Player.Tank> comparator = new Comparator<Player.Tank>() {
                    @Override
                    public int compare(Player.Tank o1, Player.Tank o2) {
                        return 0;
                    }
                };
                tanksPending2.sort(comparator);
                if (tanksPending2.get(0).getIsStun() || !tanksPending2.get(0).isAlive()){

                    tanksPending2.remove(0);
                    chooseAction(new ActionEvent());
                } else{
                    showScreen("Tank", player2.tanks.indexOf(tanksPending2.get(0)), 2);
                    resetColourBox();
                    VBox temp = findFromName("Tank", player2.tanks.indexOf(tanksPending2.get(0)), player2);
                    temp.setBackground(new Background(new BackgroundFill(Color.GOLD, null, null)));
                    tanksPending2.remove(0);
                }

            }

            else if (attacksPending2.size() >0){
                Comparator<Player.Attack> comparator = new Comparator<Player.Attack>() {
                    @Override
                    public int compare(Player.Attack o1, Player.Attack o2) {
                        return 0;
                    }
                };
                attacksPending2.sort(comparator);
                if (attacksPending2.get(0).getIsStun() || !attacksPending2.get(0).isAlive()){

                    attacksPending2.remove(0);
                    chooseAction(new ActionEvent());
                } else{
                    showScreen("Attack", player2.attacks.indexOf(attacksPending2.get(0)), 2);
                    resetColourBox();
                    VBox temp = findFromName("Attack", player2.attacks.indexOf(attacksPending2.get(0)), player2);
                    temp.setBackground(new Background(new BackgroundFill(Color.GOLD, null, null)));
                    attacksPending2.remove(0);
                }


            }
            else{

                Game.fight(player1, player1.supportActions, player1.tankActions, player1.attackActions);
                Game.fight(player2, player2.supportActions, player2.tankActions, player2.attackActions);
                endOfTurn();
                checkForEnd();

                //check for wins and ties
                SaveFile.saveToFile(player1, player2);
                //add visual cues
                if (!gameEnd){
                    gameTurn();
                }
                else{
                    SaveFile.resetFile();

                }
            }
        }
    }

    //Requires:
    //Modifies: this
    //Effects: sets supportsPending1, tanksPending1, attacksPending1, supportsPending2, tanksPending2, attacksPending2
    //to the objects in player1.supports, player1.tanks, etc.
    private static void resetTroopPends(){
        if (player1.supports.size() >0){
            supportsPending1.clear();
            for (Player.Support s : player1.supports){
                supportsPending1.add(s);

            }
        }


        if (player1.tanks.size() >0){
            tanksPending1.clear();
            for (Player.Tank t : player1.tanks){
                tanksPending1.add(t);
            }
        }


        if (player1.attacks.size() >0){
            attacksPending1.clear();
            for (Player.Attack a : player1.attacks){
                attacksPending1.add(a);
            }
        }

        if (player2.supports.size() >0){
            supportsPending2.clear();
            for (Player.Support s : player2.supports){
                supportsPending2.add(s);

            }
        }


        if (player2.tanks.size() >0){
            tanksPending2.clear();
            for (Player.Tank t : player2.tanks){
                tanksPending2.add(t);
            }
        }


        if (player2.attacks.size() >0){
            attacksPending2.clear();
            for (Player.Attack a : player2.attacks){
                attacksPending2.add(a);
            }
        }

    }

    //Requires: option buttons to be pressed
    //Modifies: this
    //Effects: sets optionChosen to self, disables and enables "Select" buttons depending on actions
    public void selectAction(ActionEvent actionEvent) {
        int playerNum;

        Button btn = (Button) actionEvent.getSource();
        optionChosen = btn;

        String troopName = lblTroopName.getText();

        playerNum = Integer.parseInt(troopName.substring(troopName.length()-3, troopName.length()-2));

        disableVBoxes(false, true);
        disableVBoxes(false, false);
        lblDescription.setText(btn.getText());
        btnChoose.setDisable(true);
        disableSelectables(btn.getText(), playerNum);

    }

    //Requires: "Select" buttons to be pressed
    //Modifies: this, supportActions, tankActions, attackActions
    //Effects: gets troop name, number, and player of VBox that contains pressed button (1st info)
    //if actionType is Multi-attack, three "Select" buttons must be chosen, otherwise enable choose button
    //gets troop name, number, and player on info screen, uses info to find corresponding troop
    //adds 1st info to corresponding "actions" ArrayList along with the actionType
    public void selectTroop(ActionEvent actionEvent) {
        int playerNum;
        int troopNumber;
        String troopType;
        String actionType = optionChosen.getText();

        Button btn = (Button) actionEvent.getSource();

        String targetName = "";
        int targetNum = 0;


        for (VBox box: topBox){
            Button button = (Button) box.getChildren().get(2);
            if (button.equals(btn)){

                targetName = ((Label) box.getChildren().get(0)).getText();
                targetNum = Integer.parseInt(targetName.substring(targetName.length()-1));
                targetName = targetName.substring(0, targetName.length()-1);

                if (actionType.equals("Multi-attack")){
                    multiAttackActions += targetName+","+targetNum+",";
                    ArrayList<Integer> commas = new ArrayList<>();

                    commas.add(0);
                    for (int i = 0; i<multiAttackActions.length(); i++){
                        if (multiAttackActions.substring(i,i+1).equals(",")){
                            commas.add(i);
                        }
                    }


                    if (commas.size() > 7){
                        multiAttackActions = multiAttackActions.substring(commas.get(commas.size()-7)+1);
                    }
                    if (commas.size() == 7||commas.size() == 9){
                        btnChoose.setDisable(false);
                    } else if (commas.size() < 7){
                        btnChoose.setDisable(true);
                    }
                    lblDescription.setText(multiAttackActions);
                }
                else{
                    btnChoose.setDisable(false);
                }
            }
        }


        for (VBox box: bottomBox){
            Button button = (Button) box.getChildren().get(0);
            if (button.equals(btn)){

                targetName = ((Label) box.getChildren().get(2)).getText();
                targetNum = Integer.parseInt(targetName.substring(targetName.length()-1));
                targetName = targetName.substring(0, targetName.length()-1);
                if (actionType.equals("Multi-attack")){

                    multiAttackActions += targetName+","+targetNum+",";
                    ArrayList<Integer> commas = new ArrayList<>();

                    commas.add(0);
                    for (int i = 0; i<multiAttackActions.length(); i++){
                        if (multiAttackActions.substring(i,i+1).equals(",")){
                            commas.add(i);
                        }
                    }


                    if (commas.size() > 7){
                        multiAttackActions = multiAttackActions.substring(commas.get(commas.size()-7)+1);
                    }
                    if (commas.size() == 7|| commas.size() == 9){
                        btnChoose.setDisable(false);
                    } else if (commas.size() < 7){
                        btnChoose.setDisable(true);
                    }
                    lblDescription.setText(multiAttackActions);

                }
                else{
                    btnChoose.setDisable(false);
                }
            }
        }

        String troopName = lblTroopName.getText();

        troopNumber = Integer.parseInt(troopName.substring(troopName.length()-1));
        playerNum = Integer.parseInt(troopName.substring(troopName.length()-3, troopName.length()-2));
        troopType = troopName.substring(0, troopName.length()-3);

        if (playerNum == 1){
            switch (troopType) {
                case "Support":

                    player1.supportActions.remove(troopNumber);

                    player1.supportActions.add(troopNumber,actionType+","+targetName+","+targetNum);

                    break;

                case "Tank":

                    player1.tankActions.remove(troopNumber);


                    player1.tankActions.add(troopNumber,actionType+","+targetName+","+targetNum);
                    break;

                case "Attack":

                    player1.attackActions.remove(troopNumber);


                    if (actionType.equals("Multi-attack")){
                        player1.attackActions.add(troopNumber,actionType+","+multiAttackActions);
                    } else{
                        player1.attackActions.add(troopNumber,actionType+","+targetName+","+targetNum);
                    }

                    break;
            }
        }
        else{
            switch (troopType) {
                case "Support":

                    player2.supportActions.remove(troopNumber);


                    player2.supportActions.add(troopNumber,actionType+","+targetName+","+targetNum);
                    break;

                case "Tank":

                    player2.tankActions.remove(troopNumber);


                    player2.tankActions.add(troopNumber,actionType+","+targetName+","+targetNum);
                    break;

                case "Attack":

                    player2.attackActions.remove(troopNumber);


                    if (actionType.equals("Multi-attack")){
                        player2.attackActions.add(troopNumber,actionType+","+multiAttackActions);
                    } else{
                        player2.attackActions.add(troopNumber,actionType+","+targetName+","+targetNum);
                    }
                    break;
            }
        }




    }

    //Requires: boolean for enabling or disabling, boolean for doing to top or bottom row
    //Modifies: this
    //Effects: disables or enables top or bottom row of "Select" buttons
    //disables "Select" button if corresponding troop is not alive
    private void disableVBoxes(boolean yesNo, boolean doTop){
        if (doTop){
            Top1.getChildren().get(2).setDisable(yesNo);
            Top2.getChildren().get(2).setDisable(yesNo);
            Top3.getChildren().get(2).setDisable(yesNo);
            Top4.getChildren().get(2).setDisable(yesNo);
            Top5.getChildren().get(2).setDisable(yesNo);
        }
        else{
            Bottom1.getChildren().get(0).setDisable(yesNo);
            Bottom2.getChildren().get(0).setDisable(yesNo);
            Bottom3.getChildren().get(0).setDisable(yesNo);
            Bottom4.getChildren().get(0).setDisable(yesNo);
            Bottom5.getChildren().get(0).setDisable(yesNo);
        }
        for(VBox box : topBox){
            String temp = ((Label) box.getChildren().get(0)).getText();
            String troopName = temp.substring(0, temp.length()-1);
            int troopNum = Integer.parseInt(temp.substring((temp.length()-1)));
            switch (troopName){
                case "Support":
                    if (!player1.supports.get(troopNum).isAlive()){
                        ((Button) box.getChildren().get(2)).setDisable(true);
                    }
                    break;
                case "Tank":
                    if (!player1.tanks.get(troopNum).isAlive()){
                        ((Button) box.getChildren().get(2)).setDisable(true);
                    }
                    break;
                case "Attack":
                    if (!player1.attacks.get(troopNum).isAlive()){
                        ((Button) box.getChildren().get(2)).setDisable(true);
                    }
                    break;
            }

        }
        for(VBox box : bottomBox){
            String temp = ((Label) box.getChildren().get(2)).getText();
            String troopName = temp.substring(0, temp.length()-1);
            int troopNum = Integer.parseInt(temp.substring((temp.length()-1)));
            switch (troopName){
                case "Support":
                    if (!player2.supports.get(troopNum).isAlive()){
                        ((Button) box.getChildren().get(0)).setDisable(true);
                    }
                    break;
                case "Tank":
                    if (!player2.tanks.get(troopNum).isAlive()){
                        ((Button) box.getChildren().get(0)).setDisable(true);
                    }
                    break;
                case "Attack":
                    if (!player2.attacks.get(troopNum).isAlive()){
                        ((Button) box.getChildren().get(0)).setDisable(true);
                    }
                    break;
            }

        }

    }

    //Requires:
    //Modifies: this
    //Effects: resets all VBox backgrounds to Transparent
    private void resetColourBox(){
        for (VBox vBox : Arrays.asList(Top1, Top2, Top3, Top4, Top5, Bottom1, Bottom2, Bottom3, Bottom4, Bottom5)) {
            vBox.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
        }

    }
    //Requires: player 1 or 2
    //Modifies: this
    //Effects: if player is 1 return player 2 and vice versa
    public static Player reversePlayer(Player player){
        Player otherPlayer;
        if(player.equals(player1)){
            otherPlayer = player2;
        } else{
            otherPlayer = player1;
        }
        return otherPlayer;
    }
    //Requires: troopName of troop, troopNumber of troop, Player of troop
    //Modifies: this
    //Effects: finds and returns VBox of player indicated by required info
    private VBox findFromName(String troopName, int troopNumber, Player player){
        VBox toReturn = null;
        if (player.equals(player1)){
            for (VBox box : topBox){

                String text = ((Label) box.getChildren().get(0)).getText();

                String compareName = text.substring(0, text.length()-1);
                int compareNumber = Integer.parseInt(text.substring(text.length()-1));
                if (compareName.equals(troopName) && compareNumber == troopNumber){

                    return box;

                }
            }
        } else {
            for (VBox box : bottomBox){
                String text = ((Label) box.getChildren().get(2)).getText();
                String compareName = text.substring(0, text.length()-1);
                int compareNumber = Integer.parseInt(text.substring(text.length()-1));
                if (compareName.equals(troopName) && compareNumber == troopNumber){
                    return box;
                }
            }
        }

        return toReturn;
    }

    //Requires:
    //Modifies: this
    //Effects: runs finishTurn of player 1 and 2, refreshes troop information: health, name, alive, stunned, poisoned, shielded,
    private void endOfTurn(){


        player1.finishTurn();
        player2.finishTurn();
        for (VBox box : topBox){
            ((Label) box.getChildren().get(1)).setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
            ((Label) box.getChildren().get(0)).setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
            ((Label) box.getChildren().get(0)).setTextFill(Color.BLACK);
        }

        for (VBox box : bottomBox){
            ((Label) box.getChildren().get(1)).setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
            ((Label) box.getChildren().get(2)).setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
            ((Label) box.getChildren().get(2)).setTextFill(Color.BLACK);
        }
        if (player1.supports.size() >0){

            for (Player.Support s : player1.supports){
                VBox temp = findFromName("Support", player1.supports.indexOf(s), player1);
                if (s.getIsStun()){

                    ((Label) temp.getChildren().get(1)).setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, null, null)));
                }
                if(s.getPoisonEffect()>0){
                    ((Label) temp.getChildren().get(0)).setBackground(new Background(new BackgroundFill(Color.MEDIUMPURPLE, null, null)));
                }
                if(s.isShielded()){
                    ((Label) temp.getChildren().get(0)).setTextFill(Color.ORANGERED);

                }
                if(!s.isAlive()){
                    ((Label) temp.getChildren().get(1)).setBackground(new Background(new BackgroundFill(Color.DARKGREY, null, null)));
                }

            }
        }


        if (player1.tanks.size() >0){

            for (Player.Tank t : player1.tanks){
                VBox temp = findFromName("Tank", player1.tanks.indexOf(t), player1);
                if (t.getIsStun()){

                    ((Label) temp.getChildren().get(1)).setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, null, null)));
                }
                if(t.getPoisonEffect()>0){
                    ((Label) temp.getChildren().get(0)).setBackground(new Background(new BackgroundFill(Color.MEDIUMPURPLE, null, null)));
                }
                if(t.isShielded()){
                    ((Label) temp.getChildren().get(0)).setTextFill(Color.ORANGERED);

                }
                if(!t.isAlive()){
                    ((Label) temp.getChildren().get(1)).setBackground(new Background(new BackgroundFill(Color.DARKGREY, null, null)));
                }
            }
        }


        if (player1.attacks.size() >0){

            for (Player.Attack a : player1.attacks){
                VBox temp = findFromName("Attack", player1.attacks.indexOf(a), player1);
                if (a.getIsStun()){

                    ((Label) temp.getChildren().get(1)).setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, null, null)));
                }
                if(a.getPoisonEffect()>0){
                    ((Label) temp.getChildren().get(0)).setBackground(new Background(new BackgroundFill(Color.MEDIUMPURPLE, null, null)));
                }
                if(a.isShielded()){
                    ((Label) temp.getChildren().get(0)).setTextFill(Color.ORANGERED);

                }
                if(!a.isAlive()){
                    ((Label) temp.getChildren().get(1)).setBackground(new Background(new BackgroundFill(Color.DARKGREY, null, null)));
                }
            }
        }

        if (player2.supports.size() >0){

            for (Player.Support s : player2.supports){
                VBox temp = findFromName("Support", player2.supports.indexOf(s), player2);
                if (s.getIsStun()){

                    ((Label) temp.getChildren().get(1)).setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, null, null)));
                }
                if(s.getPoisonEffect()>0){
                    ((Label) temp.getChildren().get(2)).setBackground(new Background(new BackgroundFill(Color.MEDIUMPURPLE, null, null)));
                }
                if(s.isShielded()){
                    ((Label) temp.getChildren().get(2)).setTextFill(Color.ORANGERED);

                }
                if(!s.isAlive()){
                    ((Label) temp.getChildren().get(1)).setBackground(new Background(new BackgroundFill(Color.DARKGREY, null, null)));
                }

            }
        }


        if (player2.tanks.size() >0){

            for (Player.Tank t : player2.tanks){
                VBox temp = findFromName("Tank", player2.tanks.indexOf(t), player2);
                if (t.getIsStun()){

                    ((Label) temp.getChildren().get(1)).setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, null, null)));
                }
                if(t.getPoisonEffect()>0){
                    ((Label) temp.getChildren().get(2)).setBackground(new Background(new BackgroundFill(Color.MEDIUMPURPLE, null, null)));
                }
                if(t.isShielded()){
                    ((Label) temp.getChildren().get(2)).setTextFill(Color.ORANGERED);

                }
                if(!t.isAlive()){
                    ((Label) temp.getChildren().get(1)).setBackground(new Background(new BackgroundFill(Color.DARKGREY, null, null)));
                }
            }
        }


        if (player2.attacks.size() >0){

            for (Player.Attack a : player2.attacks){
                VBox temp = findFromName("Attack", player2.attacks.indexOf(a), player2);
                if (a.getIsStun()){

                    ((Label) temp.getChildren().get(1)).setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, null, null)));
                }
                if(a.getPoisonEffect()>0){
                    ((Label) temp.getChildren().get(2)).setBackground(new Background(new BackgroundFill(Color.MEDIUMPURPLE, null, null)));
                }
                if(a.isShielded()){
                    ((Label) temp.getChildren().get(2)).setTextFill(Color.ORANGERED);

                }
                if(!a.isAlive()){
                    ((Label) temp.getChildren().get(1)).setBackground(new Background(new BackgroundFill(Color.DARKGREY, null, null)));
                }
            }
        }
    }

    //Requires: action type of troop, player number of troop
    //Modifies: this
    //Effects: disables and enables "Select" buttons depending on if the action type if attack based or defense based and if it is player 1 or 2
    private void disableSelectables(String action, int playerNumber){
        disableVBoxes(true, true);
        disableVBoxes(true, false);
        if (playerNumber == 1){
            switch (action){
                case "Heal":
                case "Defend":
                case "Shield":
                    disableVBoxes(false, true);
                    //enable top
                    break;
                case "Attack":
                case "Poison":
                case "Stun":
                case "Multi-attack":
                    disableVBoxes(false, false);
                    //enable bottom
                    break;
            }
        }
        else {
            switch (action){
                case "Heal":
                case "Defend":
                case "Shield":
                    disableVBoxes(false, false);
                    //enable top
                    break;
                case "Attack":
                case "Poison":
                case "Stun":
                case "Multi-attack":
                    disableVBoxes(false, true);
                    //enable bottom
                    break;
            }
        }
    }

    //Requires:
    //Modifies: this
    //Effects: checks for outcomes: player1 wins, player2 wins, or tie
    //if there is an outcome, then disable all buttons
    private void checkForEnd() throws IOException {

        boolean player1Lose = true;
        boolean player2Lose = true;
        if (player1.supports.size() >0){

            for (Player.Support s : player1.supports){
                if (s.isAlive()){
                    player1Lose = false;
                }

            }
        }


        if (player1.tanks.size() >0){

            for (Player.Tank t : player1.tanks){
                if (t.isAlive()){
                    player1Lose = false;
                }
            }
        }


        if (player1.attacks.size() >0){

            for (Player.Attack a : player1.attacks){
                if (a.isAlive()){
                    player1Lose = false;
                }
            }
        }

        if (player2.supports.size() >0){

            for (Player.Support s : player2.supports){
                if (s.isAlive()){
                    player2Lose = false;
                }

            }
        }


        if (player2.tanks.size() >0){

            for (Player.Tank t : player2.tanks){
                if (t.isAlive()){
                    player2Lose = false;
                }
            }
        }


        if (player2.attacks.size() >0){

            for (Player.Attack a : player2.attacks){
                if (a.isAlive()){
                    player2Lose = false;
                }
            }
        }
        if (player1Lose && player2Lose) {
            tie(new ActionEvent());
            gameEnd = true;
        }
        else if (player1Lose){
            disableVBoxes(true, true);
            disableVBoxes(true, false);
            btnChoose.setDisable(true);
            option1.setDisable(true);
            option2.setDisable(true);
            option3.setDisable(true);
            btnTie.setDisable(true);
            lblOutcome.setText("Player 2 Wins!");
            gameEnd = true;

        }
        else if (player2Lose){
            disableVBoxes(true, true);
            disableVBoxes(true, false);
            btnChoose.setDisable(true);
            option1.setDisable(true);
            option2.setDisable(true);
            option3.setDisable(true);
            btnTie.setDisable(true);
            lblOutcome.setText("Player 1 Wins!");
            gameEnd = true;
        }
    }


    public void tie(ActionEvent actionEvent) throws IOException {
        disableVBoxes(true, true);
        disableVBoxes(true, false);
        btnChoose.setDisable(true);
        option1.setDisable(true);
        option2.setDisable(true);
        option3.setDisable(true);
        btnTie.setDisable(true);
        lblOutcome.setText("Tie");
        SaveFile.resetFile();

    }

}
