
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.HashMap;
import java.util.Map;


public class Registration extends HttpServlet{
	HashMap<String, User> hm=new HashMap<String, User>();
	String error_msg="";
	
	public void displayRegistration(HttpServletRequest request,
                    HttpServletResponse response, String e,boolean flag)
      throws ServletException, IOException {
		  response.setContentType("text/html");
		   PrintWriter out = response.getWriter();
		  
		  if(!flag){
			   String docType =
      "<!DOCTYPE HTML>\n";
       out.println(docType +
                "<HTML>\n" +
                "<br><HEAD><TITLE><h3>"+e+" Please login<h3></TITLE>\n" +
				"<meta http-equiv="+"Content-Type"+" content="+"text/html; charset=utf-8"+" />\n" +
				"<link rel="+"stylesheet"+" href="+"styles.css"+" type="+"text/css"+" />\n" +
				"</head>" +
				"<body>"+
				"<div id="+"container"+">\n");
				
				out.println("<nav>");	
		        out.println("</nav>");
				out.println("<header>"+e+"</header><br>");
		 out.println("<div id="+"body"+">\n" +
	
		// "<section id="+"content"+">\n" +

"<center>"+
 " <form method="+"post" +" action="+"/BestDealHome/LoginServlet"+">"+
 " <h4>Login to BestDeal</h4>"+
  "<table cellpadding="+'2'+" cellspacing="+'1'+">"+
  
  "<tr><td>User ID</td><td><input type='TEXT' size='15' name='userid'></input></td>"+
    "</tr><tr><td>Password</td><td><input type='PASSWORD' size='15' name='password'/></td></tr>"+
  
       	  "</tr><tr><td>Category Type</td>"+
		  "<td><select name="+"usertype"+" >"+
 "<option value="+"customer"+">Customer</option>"+
  "<option value="+"salesman"+">Salesman</option>"+
  "<option value="+"storemanager"+">StoreManager</option></select></td></tr>"+
  "<tr><td colspan="+'2'+">"+
            "<center><input type="+"submit"+" value="+"Login"+" /></center><br>"+
						"</td></tr></table>"+
	
	  "</form></center></body>");
	   
	out.println("<div class="+"clear"+"></div> \n");
	out.println("</div>");
        out.println("</html>");
		  }
		  else{
			  		   String docType =
      "<!DOCTYPE HTML>\n";
       out.println(docType +
                "<HTML>\n" +
                "<br><HEAD><TITLE>"+e+"</TITLE>\n" +
				"<meta http-equiv="+"Content-Type"+" content="+"text/html; charset=utf-8"+" />\n" +
				"<link rel="+"stylesheet"+" href="+"styles.css"+" type="+"text/css"+" />\n" +
				"</head>" +
				"<body>"+
				"<div id="+"container"+">\n");
				
				out.println("<nav>");	
		        out.println("</nav>");
				out.println("<header><h5>"+e+"<h5></header><br>");
		 out.println("<div id="+"body"+">\n" +
	
		// "<section id="+"content"+">\n" +

"<center>"+
 " <form method="+"post" +" action="+"/BestDealHome/LoginServlet"+">"+
 " <h4>Login to BestDeal</h4>"+
  "<table cellpadding="+'2'+" cellspacing="+'1'+">"+
  
  "<tr><td>User ID</td><td><input type='TEXT' size='15' name='userid'></input></td>"+
    "</tr><tr><td>Password</td><td><input type='PASSWORD' size='15' name='password'/></td></tr>"+
  
       	  "</tr><tr><td>Category Type</td>"+
		  "<td><select name="+"usertype"+" >"+
 "<option value="+"customer"+">Customer</option>"+
  "<option value="+"salesman"+">Salesman</option>"+
  "<option value="+"storemanager"+">StoreManager</option></select></td></tr>"+
  "<tr><td colspan="+'2'+">"+
            "<center><input type="+"submit"+" value="+"Login"+" /></center><br>"+
						"</td></tr></table>"+
	
	  "</form></center></body>");
	out.println("<div class="+"clear"+"></div> \n");
	out.println("</div>");
        out.println("</html>");
		  }
		  
		  
		  
	  }
	
	 public void doPost(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
		  
		  HttpSession session = request.getSession();
		  String userid = request.getParameter("userid");
		  
String password = request.getParameter("password");
String repassword = request.getParameter("repassword");
	String usertype = request.getParameter("usertype");
	
	synchronized(session){
		
		if(userid != null && userid.length() != 0) {
            userid = userid.trim();
        }
		 if(password != null && password.length() != 0) {
            password = password.trim();
        }
		   if(repassword != null && repassword.length() != 0) {
            repassword = repassword.trim();
        }
		  
		  
		 // String userid=session.getAttribute("userid");
	 
	//String username = request.getParameter("userid");
	
	HashMap<String, User> hm=new HashMap<String, User>();
try
{
hm=MySqlDataStoreUtilities.selectUser();
}
catch(Exception e){}
if(hm.containsKey(userid))
{
error_msg = "Username already exist as " + userid;
displayRegistration(request, response, error_msg , false);
}

else
{
//User user = new User(userid,password,usertype);
//hm.put(username, user);
MySqlDataStoreUtilities.insertUser(userid,password,repassword,usertype);

	displayRegistration(request, response, "User created! Please login", true);
}
	
}
	  }
}