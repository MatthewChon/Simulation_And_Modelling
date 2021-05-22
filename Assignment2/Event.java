public class Event {
    public static final int lambda = 8;
    public static final int mu = 10;
    private static double sequence = 0.0;

    public double arrival;
    public double end_of_service;
    public double difference;
    public String name;

    public Event(int id) {
        name = "Customer_" + ((int)id+1);
        arrival = sequence + exponential_distribution(lambda);
        sequence = arrival;     // make sures the customer arrives sequentially
        end_of_service = exponential_distribution(mu);
        difference = end_of_service + arrival;
    }
    /**
     * Computes the exponential distribution
     * @param rate rate of service
     * @return The Poisson Point
     */
    public double exponential_distribution (int rate) {
        return arrival + -Math.log(1-Math.random())/rate;
    }
    /**
     * Computes the exponential distribution
     * @param new_arrival the current time
     * @param rate rate of service
     * @return The Poisson Point
     */
    public double exponential_distribution(double new_arrival, int rate) {
        return new_arrival + -Math.log(1-Math.random())/rate;
    }
}