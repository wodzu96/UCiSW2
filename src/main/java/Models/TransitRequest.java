package Models;

/**
 * Created by wodzu on 05.03.2018.
 */
public class TransitRequest {
    private String orygin, destination;
    private int departureTime;

    public TransitRequest(String orygin, String destination) {
        this.orygin = orygin;
        this.destination = destination;
        this.departureTime = 0;
    }

    public TransitRequest(String orygin, String destination, int departureTime) {
        this.orygin = orygin;
        this.destination = destination;
        this.departureTime = departureTime;
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

    public int getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(int departureTime) {
        this.departureTime = departureTime;
    }
}
