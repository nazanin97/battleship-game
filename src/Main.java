import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        int inputNum;
        System.out.println("choose:");
        System.out.println("1-one player:");
        System.out.println("2-two player:");
        inputNum = input.nextInt();
        Game newGame;
        switch (inputNum){
            case 1:
                newGame = new Game(1);
                newGame.start(1);
                break;
            case 2:
                newGame = new Game(2);
                newGame.start(2);
                break;
            default:
                System.out.println("Enter correct number");
                break;
        }

    }
}
