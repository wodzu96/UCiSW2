import Models.TransitRequest;
import Models.TransitResult;
import Utils.JsonReader;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

/**
 * Created by wodzu on 05.03.2018.
 */
public class Main {
    public static void main(String[] argv){
        TransitRequest transitRequest = new TransitRequest("Politechnika+Wrocławska", "Barlickiego+3,+50-001+Wrocław");
        TransitResult result = JsonReader.getTransit(transitRequest);
        System.out.print("Name: "+result.getTramName());
        System.out.println();
        System.out.print("Destination: "+result.getDirection());
        System.out.println();
        System.out.print("Time: "+result.getArrivalTime());
    }
}
