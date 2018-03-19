package Models;

/**
 * Created by wodzu on 05.03.2018.
 */
public class TransitResult {
    private String tramName, direction, arrivalTime;

    public TransitResult(String tramName, String direction, String arrivalTime) {
        this.tramName = tramName;
        this.direction = direction;
        this.arrivalTime = arrivalTime;
    }

    public String getTramName() {
        return tramName;
    }

    public void setTramName(String tramName) {
        this.tramName = tramName;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
}
