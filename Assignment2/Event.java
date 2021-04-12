package Assignment2;

/**
 * The {@code Event} class simulates the stochastic states of
 * the M/M/1 queue.
 * 
 * <p>
 *      This class records the transition between interarrival time,
 *      in other terms the transition of moving from and to the other
 *      states.
 * </p>
 * @author Matthew Chon
 */
public class Event {
    // rates of state transitions
    private final int LAMBDA = 8;   // 8 customer per second
    private final int MU = 10;      // 10 customer per second

    // arrival and departure time of event
    private double arrive;
    private double departure;
    
    // consistency checkers
    private static double current_time = 0;

    public Event() {
        arrive = current_time + exponential_distribution(LAMBDA);
        current_time = arrive;
        departure = exponential_distribution(MU);
    }
    /**
     * Returns the arrival time of the event
     * @return arrival time of event
     */
    public double arrive() { return arrive; }

    /**
     * Returns the departure time of the event
     * @return departure time of event
     */
    public double departure() { return departure; }
    /**
     * The exponential distribution is calculated using the Poisson process.
     * @param val   The rate of each distribution
     * @return      The exponential distribution (Poisson process)
     */
    private double exponential_distribution(int val) {
        return -Math.log(Math.random())/val;
    }
}
