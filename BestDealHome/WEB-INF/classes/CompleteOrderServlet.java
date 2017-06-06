import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Date;
import java.util.*;
import java.text.*;
import java.util.Iterator;
import java.lang.*;
import java.sql.*;
import java.util.Calendar;

import java.util.concurrent.atomic.AtomicInteger.*;
//implements Serializable

public class CompleteOrderServlet extends HttpServlet {
  int nextVal; 
 String userid,us;
 double Total;
 
 //List<Integer> ints = new ArrayList<Integer>();
 


 public void storePayment(int orderId, String orderName, double orderPrice, String userAddress, String creditCardNo) { 
  HashMap < Integer, ArrayList < OrderPayment >> orderPayments = new HashMap < Integer,
   ArrayList < OrderPayment >> ();
   
     
  try {
   orderPayments = MySqlDataStoreUtilities.selectOrder();
  } catch (Exception e) {
   e.printStackTrace();
  }

  if (!orderPayments.containsKey(orderId)) {
   ArrayList < OrderPayment > arr = new ArrayList < OrderPayment > ();
   orderPayments.put(orderId, arr);
  }
  ArrayList < OrderPayment > listOrderPayment = orderPayments.get(orderId);
  OrderPayment orderpayment = new OrderPayment(orderId, userid, orderName, orderPrice, userAddress, creditCardNo); //
  listOrderPayment.add(orderpayment);
  try {
   MySqlDataStoreUtilities.insertOrder(orderId, userid, orderName, orderPrice, userAddress, creditCardNo); //,date
  } catch (Exception e) {
   e.printStackTrace();
  }
 }


 public void doPost(HttpServletRequest request,
  HttpServletResponse response)
 throws ServletException, IOException {

 //nextVal= 33;//ints.size()+1;
 //ints.add(nextVal);
 
  HttpSession session = request.getSession();
  ShoppingCart cart;

  synchronized(session) {
   String userAddress = request.getParameter("address");
   String creditCardNo = request.getParameter("cardnumber");
   userid = (String) session.getAttribute("userid");
   Total = (double) session.getAttribute("Total");
   cart = (ShoppingCart) session.getAttribute("shoppingCart");
   String itemID = request.getParameter("itemID");
   
    if(userid == null)
		   {
			   us="Login";
		   }
		   else{
			   us= "Hello, "+userid;
		   }
		  
   
   if (itemID != null) {
    String numItemsString = request.getParameter("numItems");
    if (numItemsString == null) {

    } else {

     int numItems;
     try {
      numItems = Integer.parseInt(numItemsString);
     } catch (NumberFormatException nfe) {
      numItems = 1;
     }
     cart.setNumOrdered(itemID, numItems);
    }
   }
    String t1 = "Delivery Date and Time";
   Date date1 = new Date();
  

Calendar cal = Calendar.getInstance();
cal.setTime(date1);
cal.add(Calendar.DATE,14); // add 10 days
 
//date = cal.getTime();

 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
String date = sdf.format(cal.getTime());


 //java.util.Date today = new java.util.Date();
   // java.sql.Date d2=  new java.sql.Date(today.getTime());


//get the orderId for this order
int odId=MySqlDataStoreUtilities.getOrderId();
nextVal = odId +1;

  
   storePayment(nextVal, nextVal + userid, Total, userAddress, creditCardNo);
 // storePayment(nextVal, nextVal + userid, Total, userAddress, creditCardNo);
   
   
//Once order is placed, discard the old cart
  cart = new ShoppingCart();
   session.setAttribute("shoppingCart", cart);
   
 response.setContentType("text/html");
   PrintWriter out = response.getWriter();
  
   
   
   
   String docType =
    "<!DOCTYPE HTML>\n";
   out.println(docType +
    "<HTML>\n" +
    "<HEAD><TITLE>Completion</TITLE>\n" +
    "<meta http-equiv=" + "Content-Type" + " content=" + "text/html; charset=utf-8" + " />\n" +
    "<link rel=" + "stylesheet" + " href=" + "styles.css" + " type=" + "text/css" + " />\n" +
    "</head>" +
    "<body>" +
    "<div id=" + "container" + ">\n");
  out.println("<nav>");	
		 out.println("<ul>");	
       out.println("<li class="+"start selected"+"><a href="+"/BestDealHome/HomeServlet"+">Home</a></li>" +
			"<li class="+""+"><a href="+"/BestDealHome/Phones"+">Phones</a></li>" +           
		  "<li class="+""+"><a href="+"/BestDealHome/Tablets"+">Tablets</a></li>" +           
		   "<li class="+""+"><a href="+"/BestDealHome/Laptops"+">Laptops</a></li>" +           
		 	"<li class="+""+"><a href="+"/BestDealHome/TvPage"+">TvPage</a></li>"+
			//LoginPageServlet
			"<div style=float:"+"right"+">" +
			"<li class="+""+"><a href="+"/BestDealHome/LoginPageServlet"+">"+us+"</a></li>");
			if(userid != null){
				 out.println("<li class="+""+"><a href="+"/BestDealHome/LogoutServlet"+">Logout</a></li>");
						 out.println("<li class="+""+"><a href="+"/BestDealHome/OrderPage"+">Cart("+cart.getItemsOrdered().size()+")</a></li></div>");
			}
        out.println("</nav>");
   out.println("<div id=" + "body" + ">\n" +

    "<section id=" + "content" + ">\n");


   out.println("<h5>Congratuations! Your items have been placed successfully!</h5><br>");

   out.println("<h5>Your Order Id is: " + nextVal + "</h5>");
   out.println("<h5>Delivery Date:" + date + "</h5><br>");


   out.println("</section>");
   out.println("<div class=" + "clear" + "></div> \n");
   out.println("</div>");
   out.println("\n</BODY></HTML>");
  }
 }
}