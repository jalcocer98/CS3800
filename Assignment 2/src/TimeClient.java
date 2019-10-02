import java.net.*;
import java.io.*;

public class TimeClient {
	private Socket socket = null; 
	private DataInputStream input = null;
   
	
	public static void main(String args[]) { 
		TimeClient client;
		client = new TimeClient("time.nist.gov");
		client.getDateString();
	}
	public TimeClient(String hostName) {
		try {
			socket = new Socket(hostName, 13);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public String getDateString() {
		String date = "";
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			date = "";
			for(int i=0; i<7; i++) {
				in.read();
			}
			for(int i=0; i<14; i++) {
				date += (char)(in.read());
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
		System.out.println(date);
		return date;
	}
}
