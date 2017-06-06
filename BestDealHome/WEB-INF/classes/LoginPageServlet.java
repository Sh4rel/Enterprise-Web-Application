

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class LoginPageServlet extends HttpServlet {
 
  
 
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
		  
		  
		   HttpSession session = request.getSession();
		   
		  ShoppingCart cart;
    synchronized(session) {
  
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    String docType =
      "<!DOCTYPE HTML>\n";
       out.println(docType +
                "<HTML>\n" +
                "<br><HEAD><TITLE>Login for BestDeal</TITLE>\n" +
				"<meta http-equiv="+"Content-Type"+" content="+"text/html; charset=utf-8"+" />\n" +
				"<link rel="+"stylesheet"+" href="+"styles.css"+" type="+"text/css"+" />\n" +
				"</head>" +
				"<body>"+
				"<div id="+"container"+">\n");
				out.println("<nav>");	
		        out.println("</nav>");
		 out.println("<div id="+"body"+">\n" +
	
		

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
						"<center><a href="+"/BestDealHome/NewLoginServlet"+">New User? Sign in"+"</a></center>"+
       "</td></tr></table>"+
	
	  "</form></center></body>");
	   
	 
	out.println("<div class="+"clear"+"></div> \n");
	out.println("</div>");
        out.println("</html>");
    }
  }
}
