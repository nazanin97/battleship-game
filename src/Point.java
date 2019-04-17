import java.util.Random;

public class Point {

    private int x;
    private int y;
    private char sign;

    /**
     *
     * @param x
     * @param y
     */
    public Point(int x, int y){
        sign = ' ';
        this.x = x;
        this.y = y;
    }

    /**
     *
     * @return x of point
     */
    public int getX() {
        return x;
    }

    /**
     *
     * @return y of point
     */
    public int getY() {
        return y;
    }

    /**
     *
     * @return sign
     */
    public char getSign() {
        return sign;
    }

    /**
     *
     * @param sign
     */
    public void setSign(char sign) {
        this.sign = sign;
    }

    /**
     *
     * @param x
     * @param y
     * @return new point which is neighbor to previous point
     */
    public static Point neighbor(int x, int y){

        Random rand = new Random();

        int num1;
        int num2;

        do {
            num1 = rand.nextInt(3) + x - 1;
        }while (!(num1 <= 9 && num1 >= 0));

        do {
            num2 = rand.nextInt(3) + y - 1;
        }while (!(num2 <= 9 && num2 >= 0));

        return (new Point(num1, num2));

    }

    /**
     *
     * @param p1 is first point
     * @param p2 is second point
     * @return true if two points are neighbor if not return false
     */
    public static boolean isNeighbor(Point p1, Point p2){

        if (((p1.getX() == p2.getX()) && (Math.abs(p1.getY()-p2.getY()) == 1))
                || ((p1.getY() == p2.getY())) && (Math.abs(p1.getX()-p2.getX()) == 1)){

            return true;
        }
        return false;
    }
}
