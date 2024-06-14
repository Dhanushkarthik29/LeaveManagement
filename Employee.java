package Leave_management;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.mail.PasswordAuthentication;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@SuppressWarnings("unused")
public class Employee {

    static Scanner ob = new Scanner(System.in);
    static long weekdays = 0;
    static String from_date="";
    static String to_date="";
   
    public static void EmailSend(String l) {
	   final String email = "sineka_p2003@outlook.com";
       final String password = "sp232003";
       Properties props = new Properties();
       props.put("mail.smtp.auth", "true");
       props.put("mail.smtp.starttls.enable", "true");
       props.put("mail.smtp.host", "outlook.office365.com");
       props.put("mail.smtp.port", "587");

       Session session = Session.getInstance(props,
         new javax.mail.Authenticator() {
           protected PasswordAuthentication getPasswordAuthentication() {
               return new PasswordAuthentication(email, password);
           }	
         });

       try {
           Message message = new MimeMessage(session);
           message.setFrom(new InternetAddress("sineka_p2003@outlook.com"));
           message.setRecipients(Message.RecipientType.TO,
               InternetAddress.parse("dhanushkrishna_k@solartis.com"));
           if(l.equals("sl"))
           {
        	   message.setSubject("Sick Leave Request");
        	   message.setText("Dear HR,\r\n"
           		+ "\r\n"
           		+ "I am writing to request sick leave from "+ from_date +" to "+to_date+" due to illness. I have attached the required medical documentation for your records.\r\n"
           		+ "\r\n"
           		+ "Thank you for your attention to this matter.\r\n"
           		+ "\r\n"
           		+ "Sincerely,\r\n"
           		+ App.username+ "\r\n");
           }
           else if(l.equals("cl")) {
        	   message.setSubject("Casual Leave Request");
        	   message.setText("Dear HR,\r\n"
           		+ "\r\n"
           		+ "I am writing to request casual leave from "+from_date+" to "+to_date+" . \r\n"
           		+ "\r\n"
           		+ "Thank you for your attention to this matter.\r\n"
           		+ "\r\n"
           		+ "Sincerely,\r\n"
           		+ App.username+ "\r\n");
           }
           else if(l.equals("wfh")) {
        	   message.setSubject("Work From Home Request");
        	   message.setText("Dear HR,\r\n"
        	   		+ "\r\n"
        	   		+ "I am writing to request permission to work from home for "+ from_date+" to "+to_date+".\r\n"
        	   		+ "\r\n"
        	   		+ "Thank you.\r\n"
        	   		+ "\r\n"
        	   		+ "Sincerely,\r\n"
        	   		+ App.username+ "\r\n");
           }
           Transport.send(message);

           System.out.println("EMAIL SENDED SUCESSFULLY");

       } catch (MessagingException e) {
           throw new RuntimeException(e);
       }
   }
   
    public static String convertDateFormat(String dob) {
        try {
        	//System.out.println("Dob"+" "+dob);
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd"); // Corrected date format
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = inputFormat.parse(dob);
            return outputFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Handle the parsing error appropriately in your application
        }
    }

    @SuppressWarnings("static-access")
	public static void calldb(HashMap<String,Object> ref, Connection conn, String type) throws SQLException {
        String sql = "INSERT INTO emp_leave (from_date, to_date, count_leave, type_leave, rollno) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pt = conn.prepareStatement(sql);
        App ap=new App();
        int rl=ap.roll;
        pt.setString(1, convertDateFormat((String)ref.get("from_date")));
        pt.setString(2, convertDateFormat((String)ref.get("to_date")));
        pt.setLong(3, weekdays); // Set the correct data type for count_leave, assuming weekdays is a long
        pt.setString(4, type);
        pt.setInt(5, rl);
        from_date=(String)ref.get("from_date");
        to_date=(String)ref.get("to_date");
        pt.execute();
    }
    
    @SuppressWarnings("unchecked")
    //parse a JSON file into a HashMap Object as a obj
	public static HashMap<String, Object> parseJSON(String file) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(file));
        return (HashMap<String, Object>) obj;
    }
    
    //calculateweekdays - count of a saturday and sunday 
    public static void calculateWeekdays(LocalDate fromDate, LocalDate toDate, Connection conn) throws SQLException {
        LocalDate current = fromDate;
        while (!current.isAfter(toDate)) { // Including the end date for counting
            if (current.getDayOfWeek() != DayOfWeek.SATURDAY && current.getDayOfWeek() != DayOfWeek.SUNDAY) {
                // Check if the current date is a special holiday
                if (!isSpecialHoliday(current, conn)) {
                    weekdays++;
                }
            }
            current = current.plusDays(1);
        }
    }
    //isSpecialHoliday - boolean function returns true if the special leave applicable when the spl_leave database have a table of monthyr which is have a special leave else return false
    public static boolean isSpecialHoliday(LocalDate date, Connection conn) throws SQLException {
        String sql = "SELECT * FROM spl_leave WHERE monthyr = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, date.toString()); // Match the date format directly
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            LocalDate holidayDate = LocalDate.parse(rs.getString("monthyr"));
            DayOfWeek holidayDayOfWeek = DayOfWeek.valueOf(rs.getString("days").toUpperCase()); // Convert day to DayOfWeek enum
            if (date.equals(holidayDate) && date.getDayOfWeek() == holidayDayOfWeek) {
                return true;
            }
        }
        return false;
    }

    public static void details(Connection conn) throws SQLException, IOException, ParseException {
    	App ap=new App();
    	int rl=ap.roll;
    	String sqld="select * from emp_leave where rollno=?";
    	PreparedStatement ps=conn.prepareStatement(sqld);
    	ps.setInt(1, rl);
    	ResultSet rs=ps.executeQuery();
    	int count_cl=0;
    	int count_sl=0;
    	int count_wf=0;
    	while(rs.next()) {
    		String check=rs.getString(5);
    		String leaves=rs.getString(4);
    		if((leaves.charAt(0)=='C' || leaves.charAt(0)=='c') && (check.equalsIgnoreCase("Approved"))) {
    			count_cl=count_cl+rs.getInt(3);
    		}
   		    else if((leaves.charAt(0)=='S' || leaves.charAt(0)=='s') && (check.equalsIgnoreCase("Approved"))) {
    			count_sl=count_sl+rs.getInt(3);
    		}
    		else if((leaves.charAt(0)=='w' || leaves.charAt(0)=='W') && (check.equalsIgnoreCase("Approved"))) {
    			count_wf=count_wf+rs.getInt(3);
    		}
    	}
    	leaveDetails(conn);
    	
    }
    public static void leaveDetails(Connection conn) throws IOException, ParseException, SQLException {
    	System.out.println("---------------------------------");
        System.out.println("1. Sick Leave");
        System.out.println("2. Casual Leave");
        System.out.println("3. Work from Home");
        System.out.println("4. Leave Details(Count of sick leave,casual leave,work from home)");
        System.out.println("---------------------------------");
        System.out.println("Enter Your Choice:");
        int choice = ob.nextInt();
        String jsonFilePath = "C:\\Users\\dhanushkrishna_k\\Desktop\\study material\\Task\\backend\\Leave_management\\src\\json\\employee.json";
        HashMap<String, Object> leaveData = parseJSON(jsonFilePath);
     // Inside the leaveDetails method
        switch (choice) {
            case 1:
                HashMap<String, Object> sl = (HashMap<String, Object>) leaveData.get("sickleave");
                calculateWeekdays(LocalDate.parse((String) sl.get("from_date")), LocalDate.parse((String) sl.get("to_date")), conn);
                calldb(sl, conn, "Sick Leave");
                EmailSend("sl");
                break;
            case 2:
                HashMap<String, Object> cl = (HashMap<String, Object>) leaveData.get("casualleave");
                calculateWeekdays(LocalDate.parse((String) cl.get("from_date")), LocalDate.parse((String) cl.get("to_date")), conn);
                calldb(cl, conn, "Casual Leave");
                EmailSend("cl");
                break;
            case 3:
                HashMap<String, Object> wf = (HashMap<String, Object>) leaveData.get("workfrom");
                calculateWeekdays(LocalDate.parse((String) wf.get("from_date")), LocalDate.parse((String) wf.get("to_date")), conn);
                calldb(wf, conn, "Work from Home");
                EmailSend("wfh");
                break;
            case 4:
                details(conn);
                break;
            default:
                System.out.println("Invalid Choice");
                break;
        }
    }
}
