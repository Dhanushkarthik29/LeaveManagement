package Leave_management;

import java.util.Scanner;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class App {
	
	static Scanner ob=new Scanner(System.in);
	public static int roll=0;
	public static String username="";
	public static void logindetails(Connection conn) throws SQLException, IOException, ParseException, java.text.ParseException {
		System.out.println("---------------------------------");
		System.out.println("----Your In a Login Page----");
		System.out.println("---------------------------------");
		System.out.println("Enter Your Username:");
		username=ob.next();
		ob.nextLine();
		System.out.println("Enter Your userid:");
		roll=ob.nextInt();
		ob.nextLine();
		System.out.println("Enter Your password");
		String password=ob.next();
		System.out.println("Enter Your Role:(Employee,HR)");
		String role=ob.next();
		String sql="select password from signup where rollno=? AND password=? AND role=?";
		PreparedStatement pt=conn.prepareStatement(sql);
		pt.setInt(1, roll);
		pt.setString(2, password);
		pt.setString(3, role);
		ResultSet rs=pt.executeQuery();
		if(rs.next()) {	
			if(role.charAt(0)=='h' || role.charAt(0)=='H')
			{
				System.out.println("---------------------------------");
				System.out.println("Your'e Successfully Logged in!...");
				System.out.println("---------------------------------");
				System.out.println("---YOUR IN A HR PAGE---");
				Hr h=new Hr();
				h.hrdetails(conn);
			}
			else if(role.charAt(0)=='E' || role.charAt(0)=='e') {
				System.out.println("---------------------------------");
				System.out.println("Your'e Successfully Logged in!...");
				System.out.println("---------------------------------");
				System.out.println("---YOUR IN A EMPLOYEE PAGE---");
				Employee emp=new Employee();
				emp.leaveDetails(conn);
			}
			else {
				System.out.println("Give Ur Role Properly:");
				logindetails(conn);
			}
		}
		else {
			System.out.println("Your username and password not correct...");
			System.out.println("Try again!");
			logindetails(conn);
		}
	}
	public static void signdetails(Connection conn) throws SQLException, IOException, ParseException, java.text.ParseException {
	    System.out.println("-------------------------------");
	    System.out.println("---YOUR IN A SIGNUP PAGE---");
	    System.out.println("-------------------------------");
	    System.out.println("Enter Your username:");
	    username=ob.next();
	    ob.nextLine();
	    System.out.println("Enter Your Roll No:");
	    roll=ob.nextInt();
	    System.out.println("Enter Your Phone Number:");
	    long pno=ob.nextLong();
	    System.out.println("Enter Your Email:");
	    ob.nextLine();
	    String email=ob.nextLine();
	    System.out.println("Enter Your Role:(Employee,HR)");
	    String role=ob.next();
	    System.out.println("Enter Your New Password:");
	    String password=ob.next();
	    System.out.println("Enter Your Re-Password:");
	    String repassword=ob.next();
	    if(password.length()>=8 && password.equals(repassword))
	    {
	        String sql="Insert Into signup(name,rollno,phonenumber,emailid,role,password)values(?,?,?,?,?,?)";
	        PreparedStatement pt=conn.prepareStatement(sql);
	        pt.setString(1,username);
	        pt.setInt(2, roll);
	        pt.setLong(3, pno);
	        pt.setString(4, email);
	        pt.setString(5, role);
	        pt.setString(6, password);
	        pt.execute(); // Execute the prepared statement with parameters
	        System.out.println("---------------------------------");
	        System.out.println("---Signup Successfully---");
	        System.out.println("---------------------------------");
	        logindetails(conn); // After signup, proceed to login
	    }
	    else {
	        System.out.println("Plse Enter Your Password!...correctly");
	        signdetails(conn);
	        return;
	    }
	}

	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/leave_management","root","password");
			System.out.println("---------------------------------");
			System.out.println("---WELCOME TO LEAVE MANAGEMENT SYSTEM---");
			System.out.println("---------------------------------");
			System.out.println("1.Login page");
			System.out.println("2.Signup page");
			System.out.println("---------------------------------");
			System.out.println("Enter Your Choice Here:");
			int pin=ob.nextInt();
			switch(pin) {
				case 1:
					logindetails(conn);
					break;
				case 2:
					signdetails(conn);
					break;
				default:
					System.out.println("Enter a Number 1 or 2");			
			}
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}	
		
	}

}
