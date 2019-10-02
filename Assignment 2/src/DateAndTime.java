
public class DateAndTime {
	private int hour;
	private int minutes;
	private int month;
	private int day;
	private int year;
	private String timezone;
	
	public DateAndTime(String dateString) {
		year = Integer.parseInt(dateString.substring(0, 2));
		month = Integer.parseInt(dateString.substring(3, 5));
		day = Integer.parseInt(dateString.substring(6, 8));
		hour = Integer.parseInt(dateString.substring(9, 11));
		minutes = Integer.parseInt(dateString.substring(12, 14));
	}
	public int getYear() {
		return year; 
	}
	public int getMonth() {
		return month;
	}
	public int getDay() {
		return day;
	}
	public int getHour() {
		return hour;
	}
	public int getMinutes() {
		return minutes;
	}
//	@Override
//	public String toString() {
//
//	}
	public static void main(String[] args) {
		DateAndTime date = new DateAndTime("19-10-01 01:02:48");
		System.out.println(date.getMonth());
		System.out.println(date.getDay());
		System.out.println(date.getYear());
		System.out.println(date.getHour());
		System.out.println(date.getMinutes());
	}
}

