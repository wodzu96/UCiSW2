package Models;

/**
 * Created by wodzu on 05.03.2018.
 */
public class TransitRequest {
    private String orygin, destination;
    private int arrivalTime;

    public TransitRequest(String orygin, String destination) {
        this.orygin = orygin;
        this.destination = destination;
        this.arrivalTime = 0;
    }

    public TransitRequest(String orygin, String destination, int arrivalTime) {
        this.orygin = orygin;
        this.destination = destination;
        this.arrivalTime = arrivalTime;
    }

    public String getOrygin() {
        return orygin;
    }

    public void setOrygin(String orygin) {
        this.orygin = orygin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
}
