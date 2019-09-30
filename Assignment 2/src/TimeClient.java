import java.net.*;
import java.io.*;

public class TimeClient {
	private Socket socket = null; 
	private DataInputStream input = null;
   
	
	public static void main(String args[]) throws IOException { 
		TimeClient client = new TimeClient("time.nist.gov");
		String test = client.getDate();
		System.out.println(test);
	}
	public TimeClient(String hostName) throws IOException {
		socket = new Socket(hostName, 13);
	}
	public String getDate() throws IOException {
		InputStream in = socket.getInputStream();
		String date = "";
		for(int i=0; i<7; i++) {
			in.read();
		}
		for(int i=0; i<17; i++) {
			char c = (char)(in.read());
			date += c;
		}
		return date;
	}
}
