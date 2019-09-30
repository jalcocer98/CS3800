import java.net.*; 
import java.io.*; 
import java.text.*;
import java.time.*;
import java.util.*;

public class Server {
	//initialize socket and input stream 
	private Socket socket = null; 
	private ServerSocket server = null; 
	private DataInputStream in = null;
	private DataOutputStream out = null;
	
	public static void main(String args[]) throws IOException
	{ 
		Server server = new Server(5000); 
		
		while(true) {
			Socket connectionSocket = server.acceptCon();
			
			Date today = new Date();
			String response = "HTTP/1.1 200 OK\r\n\r\n" + today;
			connectionSocket.getOutputStream().write(response.getBytes("UTF-8"));
			InputStreamReader isr = new InputStreamReader(connectionSocket.getInputStream());
			BufferedReader in = new BufferedReader(isr);
			String requestHeader = in.readLine();
			String[] headerParts  = requestHeader.split(" ");
			
			if(!headerParts[0].equals("GET")) {
					
			}
			TimeClient tc = new TimeClient("time.nist.gov");
			switch(headerParts[1]) {
				case "/time":
				case "/time?zone=all":
					//filler
					break;
				case "/time?zone=est":
					break;
				case "/time?zone=pst":
					break;
				default:
					break;
			}
			if(headerParts[1].equals("")) {
				
			}
//			while(!line.isEmpty()) {
//				System.out.println(line);
//				line = in.readLine();
//			}
		}
	}
	public Server(int portNumber) throws IOException {
		server = new ServerSocket(portNumber);
		
	}
	public Socket acceptCon() throws IOException {
		return server.accept();
	}
}
