package Assignment3.montecarlo;

import java.util.ArrayList;
import java.io.*;

public class MonteCarlo {
    ////////////////////////////////////////////////////////
    //
    //  Set this variable to see x amount of points for
    //  respected function
    //
    public static int DEBUG_OUTPUT = 0;

    //------------- Point Class ----------------------
    public class Point {
        private double  x,
                        y;
        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
        /**
         * Returns the x-coordinates of the point
         * @return  the x-coordinate
         */
        public double x() { return x; }
        /**
         * Returns the y-coordinate of the point
         * @return the y-coordinate
         */
        public double y() { return y; }

        public String toString() {
            return "( " + x + ", " + y + " )";
        }
    }//----------- end of Point class ----------------
    private double area;
    private double hitpoints;
    private ArrayList<Point> points;
    private int limit;

    public MonteCarlo(int limit) {
        area = limit * Math.exp(0); // rectangular area of the curve
        hitpoints = 0.0;
        this.limit = limit;
        points = new ArrayList<>();
    }
    /**
     * Generates a new point which then is evaluted
     * If the point is under the function curve, then we count it
     * Otherwise we skip it.
     */
    public void generate() {
        double  x = Math.random()*10 % limit,
                y = Math.random();  // Math.random()*10 % limit seems reasonable but Math.random() gives me the expected value

        Point point = new Point(x, y);
        // counts for when the point hit under the curve
        if (point.y() < Math.exp(-point.x())) {
            points.add(point);
            hitpoints += 1.0;
        }
    }
    /**
     * Generates the estimate value of the function using the
     * monte carlo method
     * @return estimate result of the monte carlo
     */
    public double estimate() {
        return area*hitpoints/10000;
    }
    public static void main(String [] args) throws IOException {
        final int MAX_ATTEMPT = 10000;
        String filename = "MatthewChon_output.txt";
        FileWriter out = new FileWriter(filename);

        MonteCarlo mc = new MonteCarlo(1);
        MonteCarlo mc2 = new MonteCarlo(2);

        for (int hit = 0; hit < MAX_ATTEMPT; ++hit) {
            mc.generate();
            mc2.generate();
        }

        ///////////////////////////////////////////////////////////////
        //  DEBUGGING
        //
        if (DEBUG_OUTPUT > 0) {
            out.append("Points for 0 to 1:\n");
            for (int i = 0; i < DEBUG_OUTPUT; ++i) {
                out.append(i+1 + " : " + mc.points.get(i).toString() + "\n");
            }
            out.append("\n");
        }

        if (DEBUG_OUTPUT > 0) {
            out.append("Points for 0 to 2:\n");
            for (int i = 0; i < DEBUG_OUTPUT; ++i) {
                out.append(i+1 + " : " + mc2.points.get(i).toString() + "\n");
            }
            out.append("\n");
        }

        out.append("The estimate value from 0 to 1 is : " + mc.estimate() + "\n");
        out.append("The estimate value from 0 to 2 is : " + mc2.estimate() + "\n");
        out.close();
    }
}
