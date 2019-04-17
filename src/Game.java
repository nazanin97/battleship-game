import java.util.Random;
import java.util.Scanner;

public class Game {

    private Board firstPlayer;
    private Board secondPlayer;

    /**
     *
     * @param number is play mode (one or two)
     */
    public Game(int number){
        firstPlayer = new Board("firstPlayer");

        if (number == 1)
            secondPlayer = new Board("computer");

        else
            secondPlayer = new Board("secondPlayer");
    }

    /**
     *
     * @param player
     * @return false if the input choice is not correct
     */
    public boolean chooseArrow(Board player){

        Scanner input = new Scanner(System.in);

        System.out.println(player.name + " choose your arrow:");
        System.out.println("1-regular\n2-irregular");

        int inputNum = input.nextInt();
        if (inputNum == 2){
            player.arrow = 2;
        }
        else if (inputNum != 1){
            System.out.println("Enter correct number!");
            return false;
        }
        return true;
    }

    /**
     *
     * @param playerNumber is number of players (one or two)
     */
    public void start(int playerNumber) {

        if(!chooseArrow(firstPlayer)){
            return;
        }

        System.out.println("**PLACE YOUR SHIPS**\nfirst player:");
        Board.drawBoard(firstPlayer.battleShips);
        firstPlayer.drawShips();

        if (playerNumber == 2){
            if (!chooseArrow(secondPlayer)){
                return;
            }
            System.out.println("**PLACE YOUR SHIPS**\nsecond player:");
            Board.drawBoard(secondPlayer.battleShips);
        }

        secondPlayer.drawShips();

        //start the game with first player
        System.out.println("**GAME STARTS**");
        start(firstPlayer);
    }

    /**
     *
     * @param currentPlayer
     */
    public void start(Board currentPlayer){

        Board another;
        Scanner stream = new Scanner(System.in);

        if (currentPlayer.name.equals("firstPlayer")){
            another = secondPlayer;
        }
        else
            another = firstPlayer;

        System.out.println("\n" + currentPlayer.name + " turn:");

        if (!currentPlayer.name.equals("computer")){
            Board.drawBoard(currentPlayer.battleShips);
            System.out.println("your grid\n");
            Board.drawBoard(currentPlayer.checkedAll);
            System.out.println("opponent's grid\n");
        }

        //for input numbers(coordinate)
        int num1;
        int num2;

        //if the player wasn't computer get coordinate from user
        if (!currentPlayer.name.equals("computer")){
            System.out.print("x: ");
            num1 = stream.nextInt();
            System.out.print("y: ");
            num2 = stream.nextInt();
        }

        //if player was computer generate random coordinate
        else {
            Random random = new Random();
            num1 = random.nextInt(10);
            num2 = random.nextInt(10);
        }


        //you can continue if the coordinate is acceptable
        if (num1<=9 && num1 >=0 && num2<=9 && num2 >=0) {

            Point newPoint = new Point(num1, num2);

            //if the arrow type was irregular choose a point from point's neighbors
            if (currentPlayer.arrow == 2){
                newPoint = Point.neighbor(num1, num2);
            }

            //check if the coordinate hasn't been chosen yet, if it was, get new coordinate!
            boolean chosenBefore = false;
            for (Point point:currentPlayer.checkedAll) {
                if ((point.getX() == newPoint.getX()) && (point.getY() == newPoint.getY()))
                    chosenBefore = true;
            }

            if (chosenBefore)
                start(currentPlayer);

            else {
                for (Point point : another.battleShips) {

                    //if current player chose correct coordinate
                    if ((point.getX() == newPoint.getX()) && (point.getY() == newPoint.getY())) {
                        newPoint.setSign('&');
                        point.setSign('#');
                        currentPlayer.checkedTrue.add(newPoint);
                        currentPlayer.checkedAll.add(newPoint);

                        if (!currentPlayer.name.equals("computer")){
                            Board.drawBoard(currentPlayer.battleShips);
                            System.out.println("your grid\n");
                        }

                        Board.drawBoard(currentPlayer.checkedAll);
                        System.out.println("opponent's grid\n\n");

                        if (currentPlayer.checkedTrue.size() == another.battleShips.size()) {
                            System.out.println("\u001B[31m" + currentPlayer.name + " Won!");
                            return;
                        }

                        //get coordinate again for his prize! :)
                        start(currentPlayer);
                    }
                }

                //if current player chose wrong coordinate
                if (newPoint.getSign() != '&') {
                    newPoint.setSign('X');
                    currentPlayer.checkedAll.add(newPoint);

                    if (!currentPlayer.name.equals("computer")){
                        Board.drawBoard(currentPlayer.battleShips);
                        System.out.println("your grid\n");
                    }
                    Board.drawBoard(currentPlayer.checkedAll);
                    System.out.println("opponent's grid\n\n");

                    //start with another player
                    start(another);
                }
            }
        }

        //if the coordinate wasn't acceptable, current player should enter another coordinate
        else {
            System.out.println("wrong choice!");
            start(currentPlayer);
        }
    }
}
