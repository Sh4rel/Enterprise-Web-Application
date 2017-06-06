import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.text.*;
import java.util.Date;

public class DataAnalyticsServlet extends HttpServlet 
{

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
   out.println("<li class=" + "start selected" + "><a href=" + "/BestDealHome/HomeServlet" + ">Home</a></li>" +
  /*  "<li class=" + "" + "><a href=" + "/BestDealHome/Phones" + ">Phones</a></li>" +
    "<li class=" + "" + "><a href=" + "/BestDealHome/Tablets" + ">Tablets</a></li>" +
    "<li class=" + "" + "><a href=" + "/BestDealHome/Laptops" + ">Laptops</a></li>" +
    "<li class=" + "" + "><a href=" + "/BestDealHome/TvPage" + ">TvPage</a></li>" +*/
    "<div style=float:" + "right" + ">" +
    "<li class=" + "" + "><a href=" + "LoginPageServlet" + ">" + us + "</a></li>");
   out.println("</nav>");
    out.println(" <form  action='/BestDealHome/DataAnalyticsPerform'>");
  out.println("<table cellpadding="+'2'+" cellspacing="+'1'+">"+
    "<td><input type='checkbox' name='select1' value="+
       	  "<tr><td>Product Name</td>"+
		  "<td><select name="+"product"+" >"+
 "<option value='iPhone 7'>iPhone 7</option>"+
  "<option value='Macbook Air'>Macbook Air</option>"+
  "<option value='S7 Edge'>S7 Edge</option></select></td></tr>"+
    "</td><br>"+
	///price
   "<td><input type='checkbox' name='select2' value= "+
       	  "<tr><td>Price</td><td><input type='TEXT' size='15' name='price'></input></td></tr>"+
		   "</td><br>"+
	//review rating
		 "</tr><td><input type='checkbox' name='compareRating' value="+   
		    "<tr><td>Review Rating</td>"+
		  "<td><select name="+"reviewRating"+" >"+
 "<option value=1>1</option>"+
  "<option value=2>2</option>"+
   "<option value=3>3</option>"+
  "<option value=4>4</option>"+
  "<option value=5>5</option></select></td></tr>"+
   "</td><br>"+
  ///retailer city
   "<td><input type='checkbox' name='select4' value= "+
       	  "<tr><td>Retailer City</td><td><input type='TEXT' size='15' name='city'></input></td></tr>"+
		   "</td><br>"+
    //city
	 "<td><input type='checkbox' name='select5' value="+
       	  "<tr><td>Group By</td>"+
		  "<td><select name='groupby' >"+
 "<option value='productName'>productName</option>"+
  "<option value='city'>city</option>"+
  "<option value='Review'>Review</option></select></td></tr>"+
    "</td><br>"+
  "<tr><td colspan="+'2'+">"+
            "<center><input type="+"submit"+" value="+"GetData"+" /></center><br>"+
						       "</td></tr>"+
	   "</table></form>");
   out.println("</BODY></HTML>");
  }
 }
}