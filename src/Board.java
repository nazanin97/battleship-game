import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Board {

    //points which specifies player's battle ships
    public ArrayList<Point>battleShips;

    //points which player chose right among the another player battle ships
    public ArrayList<Point>checkedTrue;

    //all points which were chosen by player
    public ArrayList<Point>checkedAll;

    //name of the player board
    public String name;

    //type of selected arrow
    public int arrow;

    /**
     *
     * @param name is board's name which is first or second player
     */
    public Board(String name){
        this.name = name;
        arrow = 1;
        checkedTrue = new ArrayList<>();
        checkedAll = new ArrayList<>();
        battleShips = new ArrayList<>();
    }

    /**
     *
     * @param addPoint is a point which we want to have it as battle ship
     * @return true if we can add the point else return false
     */
    private boolean addShip(Point addPoint){

        for (Point p:battleShips) {
            if (p.getY() == addPoint.getY() && p.getX() == addPoint.getX()){
                System.out.println("You have chosen this point before!");
                return false;
            }
        }
        addPoint.setSign('@');
        battleShips.add(addPoint);

        if (!name.equals("computer")){
            drawBoard(battleShips);
        }
        return true;
    }

    //placing ships in a table
    public void drawShips(){

        int num;
        for (int i = 0; i < 5; i++) {

            //if play mode is "one player" we should generate random numbers for ship length
            if (name.equals("computer")){
                Random random = new Random();
                num = random.nextInt(4) + 2;
                drawShips(num);
            }
            else {
                System.out.println("choose a battle ship number:");
                System.out.println("1-@@");
                System.out.println("2-@@@");
                System.out.println("3-@@@@");
                System.out.println("4-@@@@@");

                Scanner stream = new Scanner(System.in);
                num = stream.nextInt();
                switch (num){
                    case 1:
                        drawShips(2);
                        break;

                    case 2:
                        drawShips(3);
                        break;

                    case 3:
                        drawShips(4);
                        break;

                    case 4:
                        drawShips(5);
                        break;

                    default:
                        System.out.println("Please enter correct number!");
                        i--;
                        break;
                }
            }
        }
    }

    /**
     *
     * @param length is ship's length
     */
    private void drawShips(int length){
        int count = 0;
        ArrayList<Point>previous = new ArrayList<>();
        Scanner stream = new Scanner(System.in);

        do {
            int num1;
            int num2;

            //if the player is computer we need random numbers to choose coordinate
            if (name.equals("computer")){
                Random randomNumber = new Random();
                num1 = randomNumber.nextInt(10);
                num2 = randomNumber.nextInt(10);
            }

            else {
                System.out.println("Choose point and enter coordinate:");
                System.out.print("x: ");
                num1 = stream.nextInt();
                System.out.print("y: ");
                num2 = stream.nextInt();
            }

            //if the coordinate is acceptable
            if (num1<=9 && num1 >=0 && num2<=9 && num2 >=0) {
                Point newPoint = new Point(num1, num2);

                //if we didn't place a ship in that point before
                if (count == 0 && (addShip(newPoint))) {
                    previous.add(newPoint);
                    count++;
                }

                //if the point is next to previous point we can choose it
                else if (count == 1 && (Point.isNeighbor(newPoint, previous.get(0)))) {

                    //if the coordinate is free
                    if (addShip(newPoint)){
                        previous.add(newPoint);
                        count++;
                    }
                }
                else if (count >= 2){

                    /*if the last points that we added has common 'x' the new point should has same 'x'
                    and it should be neighbor to previous points
                    */
                    if (previous.get(0).getX() == previous.get(previous.size() - 1).getX()) {

                        //counting min 'y' in previous points
                        int minY = previous.get(0).getY();

                        for (int i = 1; i < previous.size(); i++) {
                            if (previous.get(i).getY() < previous.get(0).getY()) {
                                minY = previous.get(i).getY();
                            }
                        }

                        //counting max 'y' in previous points
                        int maxY = previous.get(0).getY();

                        for (int i = 1; i < previous.size(); i++) {
                            if (previous.get(i).getY() > previous.get(0).getY()) {
                                maxY = previous.get(i).getY();
                            }
                        }

                        if (newPoint.getX() == previous.get(0).getX() && ((newPoint.getY() - maxY == 1) || (minY - newPoint.getY() == 1))) {
                            if (addShip(newPoint)) {
                                previous.add(newPoint);
                                count++;
                            }
                        }
                    }

                    /*if the last points that we added has common 'y' the new point should has same 'y'
                    and it should be neighbor to previous points
                    */
                    else {
                        int minX = previous.get(0).getX();
                        for (int i = 1; i < previous.size(); i++) {
                            if (previous.get(i).getX() < previous.get(0).getX()) {
                                minX = previous.get(i).getX();
                            }
                        }

                        int maxX = previous.get(0).getX();
                        for (int i = 1; i < previous.size(); i++) {
                            if (previous.get(i).getX() > previous.get(0).getX()) {
                                maxX = previous.get(i).getX();
                            }
                        }

                        if (newPoint.getY() == previous.get(0).getY() && ((newPoint.getX() - maxX == 1) || minX - newPoint.getX() == 1)) {
                            if (addShip(newPoint)) {
                                previous.add(newPoint);
                                count++;
                            }
                        }
                    }
                }
            }

            //if the coordinate wasn't acceptable, choose again!
            else
                System.out.println("wrong choice!");

        }while (count != length);
    }

    /**
     *
     * @param group is collection of points which we want to draw in table
     */
    public static void drawBoard(ArrayList<Point>group) {
        System.out.println();
        System.out.print("   | ");

        for (int i = 0; i < 10; i++) {
            System.out.print(i + " | ");
        }

        for (int i = 0; i < 10; i++) {
            System.out.println();

            for (int j = 0; j < 11; j++) {
                System.out.print("---+");
            }

            System.out.print("\n " + i + " |");

            for (int j = 0; j < 10; j++) {
                boolean line = false;

                for (Point p : group) {
                    if (j == p.getX() && i == p.getY()) {

                        //colors
                        final String ANSI_RESET = "\u001B[0m";
                        final String ANSI_RED_BACKGROUND = "\u001B[41m";
                        final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
                        final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
                        final String ANSI_BLUE_BACKGROUND = "\u001B[44m";

                        if (p.getSign() == 'X') {
                            System.out.print(ANSI_YELLOW_BACKGROUND + "   " + ANSI_RESET + "|");
                        }
                        else if (p.getSign() == '&') {
                            System.out.print(ANSI_GREEN_BACKGROUND + "   " + ANSI_RESET + "|");
                        }
                        else if (p.getSign() == '@') {
                            System.out.print(ANSI_BLUE_BACKGROUND + "   " + ANSI_RESET + "|");
                        }
                        else if (p.getSign() == '#') {
                            System.out.print(ANSI_RED_BACKGROUND + "   " + ANSI_RESET + "|");
                        }
                        line = true;
                    }
                }
                if (!line) {
                    System.out.print("   |");
                }
            }
        }
        System.out.println();
    }
}
