import Models.TransitRequest;
import Models.TransitResult;
import Utils.JsonReader;
import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class Test
{

    public Test()
    {
        super();
    }

    void connect ( String portName ) throws Exception
    {
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
        if ( portIdentifier.isCurrentlyOwned() )
        {
            System.out.println("Error: Port is currently in use");
        }
        else
        {
            CommPort commPort = portIdentifier.open(this.getClass().getName(),2000);

            if ( commPort instanceof SerialPort )
            {
                SerialPort serialPort = (SerialPort) commPort;
                serialPort.setSerialPortParams(115200,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);

                InputStream in = serialPort.getInputStream();
                OutputStream out = serialPort.getOutputStream();

                (new Thread(new SerialReader(in ,out))).start();
                (new Thread(new SerialWriter(out))).start();

            }
            else
            {
                System.out.println("Error: Only serial ports are handled by this example.");
            }
        }
    }

    /** */
    public static class SerialReader implements Runnable
    {
        InputStream in;
        OutputStream out;

        public SerialReader ( InputStream in, OutputStream out )
        {
            this.in = in;
            this.out = out;
        }

        public void run ()
        {
            byte[] buffer = new byte[1024];
            int len = -1;
            try
            {
                String last = "";
                while ( ( len = this.in.read(buffer)) > -1 )
                {
                  //  System.out.println(new String(buffer, 0, len));
                    if((new String(buffer, 0, len)).equals("6") && last.equals("1"))
                        writeTo("Do Piotra", "Barlickiego+3,+50-001+Wrocław");
                    else if((new String(buffer, 0, len)).equals("E") && last.equals("1"))
                        writeTo("Do Kuby", "Kiełczowska,Wrocław");
                    last = new String(buffer, 0, len);
                }
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }
        }

        void writeTo(String to, String destination) throws IOException {
            this.out.write("qw".getBytes());
            this.out.write(writeToConsole(to));
            TransitRequest transitRequest = new TransitRequest("Politechnika+Wrocławska", destination);
            for(int i = 0; i<4; ++i) {
                TransitResult result = JsonReader.getTransit(transitRequest);
                printResult(this.out, result);
                transitRequest.setDepartureTime(result.getArrivalTimeName());
            }
            this.out.write(writeToConsole("1 -  do Piotra"));
            this.out.write(writeToConsole("2 -  do Kuby"));
        }
    }

    /** */
    public static class SerialWriter implements Runnable
    {
        OutputStream out;


        public SerialWriter ( OutputStream out )
        {
            this.out = out;
 
        }

        public void run ()
        {
            try
            {
                int c = 0;
                this.out.write(writeToConsole("Do Piotra:"));
                TransitRequest transitRequest = new TransitRequest("Politechnika+Wrocławska", "Barlickiego+3,+50-001+Wrocław");
                for(int i = 0; i<3; ++i) {
                    TransitResult result = JsonReader.getTransit(transitRequest);
                    printResult(this.out, result);
                    transitRequest.setDepartureTime(result.getArrivalTimeName());
                }
               /* for(int i = 0; i<5; ++i) {
                    TransitRequest transitRequest = new TransitRequest("Politechnika+Wrocławska", "Kiełczowska,Wrocław");
                    TransitResult result = JsonReader.getTransit(transitRequest);
                    this.out.write(writeToConsole("Do Kuby:"));
                    printResult(this, result);
                }*/

            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }
        }
    }
    public static void printResult(OutputStream out, TransitResult result) throws IOException {
        out.write(writeToConsole("Name: "+result.getTramName()));
        out.write(writeToConsole("Destination: "+result.getDirection()));
        out.write(writeToConsole("Time: "+result.getArrivalTime()));
        out.write(writeToConsole(" "));
    }
    public static byte[] writeToConsole(String oneLine){

        oneLine = oneLine.replace("ą","a");
        oneLine = oneLine.replace("ć","c");
        oneLine = oneLine.replace("ę","e");
        oneLine = oneLine.replace("ń","n");
        oneLine = oneLine.replace("ó","o");
        oneLine = oneLine.replace("ł","l");
        oneLine = oneLine.replace("ś","s");
        oneLine = oneLine.replace("ź","z");
        oneLine = oneLine.replace("ż","z");
        oneLine = oneLine.replace("Ą","A");
        oneLine = oneLine.replace("Ć","C");
        oneLine = oneLine.replace("Ę","E");
        oneLine = oneLine.replace("Ń","N");
        oneLine = oneLine.replace("Ó","O");
        oneLine = oneLine.replace("Ł","L");
        oneLine = oneLine.replace("Ś","S");
        oneLine = oneLine.replace("Ź","Z");
        oneLine = oneLine.replace("Ż","Z");
        return (oneLine+new String(new char[48-(oneLine.length()%48)]).replace('\0', ' ')).getBytes();
    }

    public static void main ( String[] args )
    {
        String libPathProperty = System.getProperty("java.library.path");
        System.out.println("Path to lib: "+ libPathProperty);

        try
        {
            (new Test()).connect("COM3");
        }
        catch ( Exception e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}