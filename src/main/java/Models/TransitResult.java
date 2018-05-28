package Models;

/**
 * Created by wodzu on 05.03.2018.
 */
public class TransitResult {
    private String tramName, direction, arrivalTime;
    private int arrivalTimeName;


    public TransitResult(String tramName, String direction, String arrivalTime, int arrivalTimeName) {
        this.tramName = tramName;
        this.direction = direction;
        this.arrivalTime = arrivalTime;
        this.arrivalTimeName = arrivalTimeName;
    }

    public int getArrivalTimeName() {
        return arrivalTimeName;
    }

    public void setArrivalTimeName(int arrivalTimeName) {
        this.arrivalTimeName = arrivalTimeName;
    }

    public void print(){
        System.out.print("Name: "+this.getTramName());
        System.out.println();
        System.out.print("Destination: "+this.getDirection());
        System.out.println();
        System.out.print("Time: "+this.getArrivalTime());
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
