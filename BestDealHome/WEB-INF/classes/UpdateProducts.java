import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.text.*;
import java.util.Date;

public class UpdateProducts extends HttpServlet {

 public void doGet(HttpServletRequest request,
  HttpServletResponse response)
 throws ServletException, IOException {
  String checkoutURL, us;

  HttpSession session = request.getSession();
  
  synchronized(session) {
   String userid = (String) session.getAttribute("userid");
   if (userid == null) {
    us = "Login";
   } else {
    us = "Hello, " + userid;
   }


   response.setContentType("text/html");
   PrintWriter out = response.getWriter();
      Date date = new Date();
   String title = "Manager";
   String docType =
    "<!DOCTYPE HTML>\n";
   out.println(docType +
    "<HTML>\n" +
    "<HEAD><TITLE>" + title + "</TITLE>\n" +
    "<meta http-equiv=" + "Content-Type" + " content=" + "text/html; charset=utf-8" + " />\n" +
    "<link rel=" + "stylesheet" + " href=" + "styles.css" + " type=" + "text/css" + " />\n" +
    "</head>" +
    "<body>" +
    "<div id=" + "container" + ">\n");
   out.println("<nav>");
   out.println("<ul>");
   out.println("<li class=" + "start selected" + "><a href=" + "/BestDealHome/" + ">Home</a></li>" +
      "<div style=float:" + "right" + ">" +
    "<li class=" + "" + "><a href=" + "LoginPageServlet" + ">" + us + "</a></li>");
   out.println("</nav>");
  out.println(" <form method="+"get" +" action="+"/BestDealHome/UpdateProductInfo"+">"+
 "<h5>Fill in the details below to update the product</h5>"+
 "<center>"+
  "<table cellpadding="+'2'+" cellspacing="+'1'+">"+
  
  "<tr><td>Item ID</td><td><input type='TEXT' size='15' name='itemid'></input></td>"+
	
    "</tr><tr><td>Cost</td><td><input type='NUMBER' size='15' name='cost'/></td></tr>"+
   "</tr><tr><td>Retailer</td><td><input type='TEXT' size='15' name='retailer'/></td></tr>"+
    	 
   "<tr><td colspan="+'2'+">"+
            "<center><input type="+"submit"+" value="+"UpdateProduct"+" /></center><br>"+
						       "</td></tr></table>"+
	
	  "</form></center></body>");
   out.println("</BODY></HTML>");
  }


 }
}