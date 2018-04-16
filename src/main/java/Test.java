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

                (new Thread(new SerialReader(in))).start();
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

        public SerialReader ( InputStream in )
        {
            this.in = in;
        }

        public void run ()
        {
            byte[] buffer = new byte[1024];
            int len = -1;
            try
            {
                while ( ( len = this.in.read(buffer)) > -1 )
                {
                    System.out.print(new String(buffer,0,len));
                }
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }
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
                TransitRequest transitRequest = new TransitRequest("Politechnika+Wrocławska", "Barlickiego+3,+50-001+Wrocław");
                TransitResult result = JsonReader.getTransit(transitRequest);
                this.out.write(writeToConsole("Do Piotra:"));
                this.out.write(writeToConsole("Name: "+result.getTramName()));
                this.out.write(writeToConsole("Destination: "+result.getDirection()));
                this.out.write(writeToConsole("Time: "+result.getArrivalTime()));

                 transitRequest = new TransitRequest("Politechnika+Wrocławska", "Kiełczowska,Wrocław");
                 result = JsonReader.getTransit(transitRequest);
                this.out.write(writeToConsole("Do Kuby:"));
                this.out.write(writeToConsole("Name: "+result.getTramName()));
                this.out.write(writeToConsole("Destination: "+result.getDirection()));
                this.out.write(writeToConsole("Time: "+result.getArrivalTime()));


            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }
        }
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
            (new Test()).connect("COM7");
        }
        catch ( Exception e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}