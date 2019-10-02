import java.net.*; 
import java.io.*; 
import java.text.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Server {
	//initialize socket and input stream 
	private Socket socket = null; 
	private ServerSocket server = null; 
	private InputStream in = null;
	private OutputStream out = null;
	private static final String HTML_STARTER = "<html><head><title>Timezones</title></head><body>";
	private static final String HTML_ENDER = "</body></html>";
	private static final String OUTPUT_HEADER = "HTTP/1.1 200 OK\r\n";
//	private static final String HEADER_STATUS_404 = "404 Not Found\r\n";
//	private static final String HEADER_STATUS_200 = "200 OK\r\n";
	private static final String HEADER_INFO =
				    "Content-Type: text/html\r\n" + 
				    "Content-Length: ";
	private static final String OUTPUT_HEADER_ENDER = "\r\n\r\n";
	public String getGMTString() {
		//convert date to GMT string
		return "test GMT timezone";
	}
	public String getPSTString() {
		//convert date to PST String
		return "test PST timezone";
	}
	public String getESTString() {
		//convert date to EST String
		return "test EST timezone";
	}
	public String[] parseDateString(String dateString) {
		String formattedDate = "test";
		String idealFormat = "M/dd/yy, h:mm a";
		DateFormat newformat = new SimpleDateFormat(idealFormat);
		DateFormat oldformat = new SimpleDateFormat("yy-MM-dd hh:mm");
		try {
			System.out.println(oldformat.parse(dateString));
			formattedDate = newformat.format(oldformat.parse(dateString));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		System.out.println(formattedDate);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(idealFormat);
		LocalDateTime ldt = LocalDateTime.parse(formattedDate, DateTimeFormatter.ofPattern(idealFormat));
		ZonedDateTime utcTime = ldt.atZone(ZoneId.of("UTC"));
		System.out.println("time in UTC " + utcTime);
		System.out.println(formatter.format(utcTime));
		
		String[] times = new String[3];
		times[0] = formatter.format(utcTime);
		times[1] = formatter.format(utcTime.withZoneSameInstant(ZoneId.of("GMT-05:00")));
		times[2] = formatter.format(utcTime.withZoneSameInstant(ZoneId.of("GMT-07:00")));
		ZonedDateTime pstTime = utcTime.withZoneSameInstant(ZoneOffset.UTC);
		return times;
	}
	public void startServer() {
//		Server server;
		try {
			while(true) {
				socket = server.accept();
				this.InitializeServerElements(socket);
				System.out.println("connected");
				BufferedReader inStream = new BufferedReader(new InputStreamReader(in));
				String requestHeader = inStream.readLine();
				System.out.println(requestHeader);
				String[] headerParts  = requestHeader.split(" ");
				
				if(!headerParts[0].equals("GET")) {
					String errorMessage = "Only get requests accepted";
					socket.getOutputStream().write(errorMessage.getBytes("UTF-8"));
					socket.close();
				}
				//else {
					TimeClient tc = new TimeClient("time.nist.gov");
					String[] times = this.parseDateString(tc.getDateString());
					
					String htmlBody = "";
					switch(headerParts[1]) {
						case "/time":
						case "/time?zone=all":
							//filler
							htmlBody += "<h1>GMT Date/Time: "+times[0] +"</h1>";
							htmlBody += "<h1>\nEST Date/Time: "+times[1] +"</h1>";
							htmlBody += "<h1>\nPST Date/Time: "+times[2] +"</h1>";
							break;
						case "/time?zone=est":
							htmlBody += "<h1>EST Date/Time: "+times[1] +"</h1>";
							break;
						case "/time?zone=pst":
							htmlBody += "<h1>PST Date/Time: "+times[2]+"</h1>";
							break;
						default:
							htmlBody += "<h1>Invalid request</h1>";
							break;
					}
					int contentLength = HTML_STARTER.length() + htmlBody.length() + HTML_ENDER.length();
					String httpResponse = OUTPUT_HEADER + HEADER_INFO + 
										+ contentLength + 
										OUTPUT_HEADER_ENDER + HTML_STARTER + htmlBody + HTML_ENDER;
					System.out.println(httpResponse);
					out.write(httpResponse.getBytes("UTF-8"));
					System.out.println(httpResponse.getBytes("UTF-8"));
					socket.close();
				}
			//}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	public Server(int portNumber) {
		try {
			server = new ServerSocket(5000);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void InitializeServerElements(Socket connection) {
		try {
			in = connection.getInputStream();
			out = connection.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public Socket acceptCon() {
		Socket socket = null;
		try {
			socket = server.accept();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return socket;
	}
	public static void main(String args[]) { 
		Server server = new Server(5000);
		server.startServer(); 
	}
}
