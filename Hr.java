package Leave_management;


import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.util.Scanner;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.HashMap;
import java.util.Properties;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Hr {
	
	
	static Scanner ob=new Scanner(System.in);
	
	public static void EmailSend(int reg,String leave,String status) {
		   final String username = "sineka_p2003@outlook.com";
	       final String password = "sp232003";

	       Properties props = new Properties();
	       props.put("mail.smtp.auth", "true");
	       props.put("mail.smtp.starttls.enable", "true");
	       props.put("mail.smtp.host", "outlook.office365.com");
	       props.put("mail.smtp.port", "587");

	       Session session = Session.getInstance(props,
	         new javax.mail.Authenticator() {
	           protected PasswordAuthentication getPasswordAuthentication() {
	               return new PasswordAuthentication(username, password);
	           }	
	         });
	       try {
	           Message message = new MimeMessage(session);
	           message.setFrom(new InternetAddress("sineka_p2003@outlook.com"));
	           message.setRecipients(Message.RecipientType.TO,
	               InternetAddress.parse("dhanushkrishna_k@solartis.com"));
	           if((status.charAt(0)=='A' || status.charAt(0)=='a') && (leave.charAt(0)=='s' | leave.charAt(0)=='S'))
	           {
	        	   message.setSubject("Approval of Sick Leave ");
	        	   message.setText("\r\n"
	        	   		+ "I hope this message finds you in good health.\r\n"
	        	   		+ "\r\n"
	        	   		+ "After careful consideration, we are pleased to inform you that your recent sick leave request has been approved by the HR department. Your well-being is our priority, and we trust that this time off will allow you to focus on your recovery and return to work feeling refreshed and revitalized.\r\n"
	        	   		+ "\r\n"
	        	   		+ "Please ensure that you follow the company's policies and procedures for reporting sick leave absences, including providing any necessary documentation upon your return to work.\r\n"
	        	   		+ "\r\n"
	        	   		+ "If you have any questions or require further assistance, please feel free to reach out to me or the HR team. We are here to support you during this time.\r\n"
	        	   		+ "\r\n"
	        	   		+ "Wishing you a speedy recovery,");
	           }
	           else if((status.charAt(0)=='A' || status.charAt(0)=='a') && (leave.charAt(0)=='c' || leave.charAt(0)=='C')) {
	        	   message.setSubject("Approval of Casual Leave");
	        	   message.setText( "\r\n"
	        	   		+ "I hope this message finds you in good health.\r\n"
	        	   		+ "\r\n"
	        	   		+ "After careful consideration, we are pleased to inform you that your recent Casual leave request has been approved by the HR department. Your well-being is our priority, and we trust that this time off will allow you to focus on your recovery and return to work feeling refreshed and revitalized.\r\n"
	        	   		+ "\r\n"
	        	   		+ "Please ensure that you follow the company's policies and procedures for reporting cCsual leave absences, including providing any necessary documentation upon your return to work.\r\n"
	        	   		+ "\r\n"
	        	   		+ "If you have any questions or require further assistance, please feel free to reach out to me or the HR team. We are here to support you during this time.\r\n"
	        	   		+ "\r\n");
	           }
	           else if((status.charAt(0)=='A' || status.charAt(0)=='a') && (leave.charAt(0)=='w' || leave.charAt(0)=='W')) {
	        	   message.setSubject("Approval of Work From Home");
	        	   message.setText("I trust this message finds you well.\r\n"
	        	   		+ "\r\n"
	        	   		+ "After reviewing your recent request for a work-from-home arrangement, I am pleased to inform you that your request has been approved by the HR department.\r\n"
	        	   		+ "\r\n"
	        	   		+ "We understand the importance of flexibility in today's work environment, and we believe that this arrangement will allow you to maintain productivity while balancing your personal and professional responsibilities.\r\n"
	        	   		+ "\r\n"
	        	   		+ "Please ensure that you adhere to the company's work-from-home policies and guidelines during this period. Your commitment to maintaining communication and meeting deadlines remotely is greatly appreciated.\r\n"
	        	   		+ "\r\n"
	        	   		+ "If you have any questions or require further assistance regarding your work-from-home arrangement, please do not hesitate to reach out to me or your immediate supervisor.\r\n"
	        	   		+ "\r\n"
	        	   		+ "Thank you for your understanding and cooperation.\r\n"
	        	   		+ "\r\n"
	        	   		+ "Best regards,");
	           }
	           else if((status.charAt(0)=='R' || status.charAt(0)=='r') && (leave.charAt(0)=='s' || leave.charAt(0)=='S')) {
	        	   message.setSubject("Rejection of Sick Leave");
	        	   message.setText("\r\n"
	        	   		+ "I hope this message finds you well.\r\n"
	        	   		+ "\r\n"
	        	   		+ "After careful consideration, I regret to inform you that your recent sick leave request has been reviewed and unfortunately, it has been declined by the HR department.\r\n"
	        	   		+ "\r\n"
	        	   		+ "Please be assured that your health and well-being are of utmost importance to us. However, based on the information provided and the current operational requirements of the company, we are unable to approve your request for sick leave at this time.\r\n"
	        	   		+ "\r\n"
	        	   		+ "We understand that unforeseen circumstances may arise, and we encourage you to reach out to your immediate supervisor to discuss any alternative arrangements or support that may be available to you during this period.\r\n"
	        	   		+ "\r\n"
	        	   		+ "If you have any questions or concerns regarding this decision, please feel free to contact me or the HR team. We are here to assist you in any way we can.\r\n"
	        	   		+ "\r\n"
	        	   		+ "Thank you for your understanding.\r\n"
	        	   		+ "\r\n"
	        	   		+ "Best regards,\r\n");
	           }
	           else if((status.charAt(0)=='R' || status.charAt(0)=='r') && (leave.charAt(0)=='c' || leave.charAt(0)=='C')) {
	        	   message.setSubject("Rejection of Casual Leave");
	        	   message.setText("\r\n"
	        	   		+ "I hope this message finds you well.\r\n"
	        	   		+ "\r\n"
	        	   		+ "After careful consideration, I regret to inform you that your recent request for casual leave has been reviewed, and unfortunately, it has been declined by the HR department.\r\n"
	        	   		+ "\r\n"
	        	   		+ "While we understand the importance of work-life balance and the need for occasional time off, we must balance these requests with the operational needs of the company. At this time, we are unable to approve your request for casual leave due to [provide brief reason if necessary, such as staffing requirements, project deadlines, etc.].\r\n"
	        	   		+ "\r\n"
	        	   		+ "We appreciate your understanding in this matter and encourage you to resubmit your request for leave with as much advance notice as possible, allowing us to better accommodate your needs and plan accordingly.\r\n"
	        	   		+ "\r\n"
	        	   		+ "If you have any questions or concerns regarding this decision, please don't hesitate to reach out to me or the HR team. We are here to support you and address any further inquiries you may have.\r\n"
	        	   		+ "\r\n"
	        	   		+ "Thank you for your understanding and cooperation.\r\n"
	        	   		+ "\r\n"
	        	   		+ "Best regards,");
	           }
	           else if((status.charAt(0)=='R' || status.charAt(0)=='r') && (leave.charAt(0)=='W' || leave.charAt(0)=='w')) {
	        	   message.setSubject("Rejection of Work From Home");
	        	   message.setText("I trust this email finds you well.\r\n"
	        	   		+ "\r\n"
	        	   		+ "After careful consideration, I regret to inform you that your recent request for a work-from-home arrangement has been reviewed and, unfortunately, it has been declined by the HR department.\r\n"
	        	   		+ "\r\n"
	        	   		+ "While we understand the benefits of remote work and appreciate your desire for flexibility, we must balance these requests with the operational needs of the company. At this time, we are unable to approve your request for work from home due to [provide brief reason if necessary, such as staffing requirements, project deadlines, etc.].\r\n"
	        	   		+ "\r\n"
	        	   		+ "Please be assured that your health and well-being are important to us, and we encourage you to explore alternative ways to manage your work-life balance within the office environment.\r\n"
	        	   		+ "\r\n"
	        	   		+ "If you have any questions or concerns regarding this decision, please do not hesitate to reach out to me or the HR team. We are here to support you and address any further inquiries you may have.\r\n"
	        	   		+ "\r\n"
	        	   		+ "Thank you for your understanding and cooperation.\r\n"
	        	   		+ "\r\n"
	        	   		+ "Best regards,");
	           }
	           Transport.send(message);
	           System.out.println("EMAIL SENTED SUCCESSFULLY");
	       } catch (MessagingException e) {
	           throw new RuntimeException(e);
	       }
	   }
	
	public static HashMap<String, Object> parseJSON(String file) throws IOException, ParseException, org.json.simple.parser.ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(file));
        JSONObject jsonObject = (JSONObject) obj;
        
        HashMap<String, Object> userData2 = new HashMap<>();
        userData2.put("userId", (Object) jsonObject.get("userId"));
        userData2.put("username", (Object) jsonObject.get("username"));
        userData2.put("role", (Object) jsonObject.get("role"));
        return userData2;
    }
	public static HashMap<String, Object> parseJSON1(String file) throws IOException, ParseException, org.json.simple.parser.ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(file));
        JSONObject jsonObject = (JSONObject) obj;
        
        HashMap<String, Object> userData2 = new HashMap<>();
        userData2.put("userid", (Object) jsonObject.get("userid"));
        return userData2;
    }
	
    public static void remove(Connection conn) throws SQLException, IOException, ParseException, org.json.simple.parser.ParseException {
    	HashMap<String, Object> userData3 = parseJSON("C:\\Users\\dhanushkrishna_k\\Desktop\\study material\\Task\\backend\\Leave_management\\src\\json\\hr.json");
    	int userid = ((Long)(userData3.get("userId"))).intValue();
        String sql = "DELETE FROM signup WHERE rollno = ? ";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, userid);
        pstmt.executeUpdate();
        System.out.println("---------------------------------");
        System.out.println("---Removed User Successfully---");
        System.out.println("---------------------------------");
    }
	
    public static void lop(Connection conn) throws IOException, ParseException, org.json.simple.parser.ParseException, SQLException {
        System.out.println("1.Particular LOP");
        System.out.println("2.Show all LOP lists");
        System.out.println("---------------------------------");
        System.out.println("Enter Your Choice Here:");
        int choice = ob.nextInt();
        switch (choice) {
            case 1:
                System.out.println("Enter The Userid for the particular LossofPay:");
                int userids = ob.nextInt();
                String sql = "SELECT * FROM emp_leave WHERE rollno = ?";
                PreparedStatement psmt = conn.prepareStatement(sql);
                psmt.setInt(1, userids);
                ResultSet rs = psmt.executeQuery();
                while (rs.next()) {
                    int date = rs.getInt(3);
                    if (date > 10) {
                        System.out.println(rs.getDate(1) + " " + rs.getDate(2) + " " + rs.getInt(3) + " " + rs.getString(4) + " " + rs.getInt(5) + " " + rs.getString(6));
                    }
                }
                break;
            case 2:
                System.out.println("All LOP lists:");
                String sql2 = "SELECT * FROM emp_leave";
                PreparedStatement pt = conn.prepareStatement(sql2);
                ResultSet rss = pt.executeQuery();
                while (rss.next()) {
                    int dates = rss.getInt(3);
                    //if(dates > 10) {
                    System.out.println(rss.getInt(5) + " " + rss.getDate(1) + " " + rss.getDate(2) + " " + rss.getInt(3) + " " + rss.getString(4) + " " + rss.getString(6));
                    //}
                }
                break;
            default:
                System.out.println("Invalid choice");
                break;
        }
    }
    
	public static void status(Connection conn) throws SQLException, IOException, ParseException, org.json.simple.parser.ParseException {
		String sql = "SELECT * FROM emp_leave WHERE Email_status = 'pending'";
		Statement stmt=conn.createStatement();
		ResultSet rs=stmt.executeQuery(sql);
		while(rs.next()) {
			String status =rs.getString(6);
			if(status.charAt(0)=='p' || status.charAt(0)=='P') {
				System.out.println("---------------------------------");
				System.out.println("Status are Pending for the Employees:");
				//System.out.println(rs.getInt(3)+" "+rs.getString(4)+" "+rs.getInt(5)+" "+"Email Status "+rs.getString(6));
				System.out.println("----------------------------------");
				System.out.println("1.Approved");
				System.out.println("2.Rejected");
				System.out.println("---------------------------------");
				System.out.println("Enter Your Choice:");
				int choice=ob.nextInt();
				switch(choice) {
				case 1:
//				    HashMap<String, Object> userData = parseJSON("C:\\Users\\dhanushkrishna_k\\Desktop\\study material\\Task\\backend\\Leave_management\\src\\json\\Approved.json");
//				    JSONObject approvedData = (JSONObject) userData.get("Approved");
//				    int userid = ((Long) approvedData.get("user_id")).intValue();
					System.out.println("Enter a userid:");
					int userid=ob.nextInt();
					ob.nextLine();
					System.out.println("Enter a Leave:");
					String leav=ob.nextLine();
				    String sqlupdate = "UPDATE emp_leave SET Email_status = 'Approved' WHERE rollno = ? AND type_leave = ?";
				    PreparedStatement pstm=conn.prepareStatement(sqlupdate);
				    pstm.setInt(1, userid);
				    pstm.setString(2, leav);
				    pstm.executeUpdate();
				    System.out.println("Status is Updated as Approved for User ID: " + userid);
				    EmailSend(userid,leav,"Approved");
				    break;

				case 2:
					System.out.println("Enter a userid:");
					int userid2=ob.nextInt();
					ob.nextLine();
					System.out.println("Enter a Leave:");
					String leave=ob.nextLine();
					String sqlupdates="UPDATE emp_leave SET Email_status= 'Rejected' WHERE rollno = ? AND type_leave = ?";
					PreparedStatement pst=conn.prepareStatement(sqlupdates);
					pst.setInt(1, userid2);
					pst.setString(2, leave);
					pst.executeUpdate();
					System.out.println("Status is Updated as Rejected for"+" "+userid2);
					EmailSend(userid2,leave,"Rejected");
					break;
				default:
					System.out.println("Enter Your Choice Correctly...");
					status(conn);
					break;
				}
			}
			else {
				System.out.println("No pending list is there");
			}
		}

	}
	
	public static void hrdetails(Connection conn) throws SQLException, IOException, ParseException, org.json.simple.parser.ParseException {
		System.out.println("---------------------------------");
		System.out.println("1.Remove Employee");
		System.out.println("2.Loss of Pay List");
		System.out.println("3.Status List");
		System.out.println("---------------------------------");
		System.out.println("Enter Your Choice");
		int choice=ob.nextInt();
		switch(choice) {
		case 1:
			remove(conn);
			break;
		case 2:
			lop(conn);
			break;
		case 3:
			status(conn);
			break;
		default:
			System.out.println("Enter Your Choice Correctly within 1-3...");
			hrdetails(conn);
			break;
	}
}
}
