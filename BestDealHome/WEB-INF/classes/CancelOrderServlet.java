

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Collection;
import java.util.Iterator;
import java.lang.*;
import java.util.*;
import java.text.*;


public class CancelOrderServlet extends HttpServlet  {
String us, msg;
long diff;

public static Date StringToDate(String strDate)  {
    Date dtReturn = null;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");
    try {
        dtReturn = simpleDateFormat.parse(strDate);
    } catch (ParseException e) {
        e.printStackTrace();
    }
    return dtReturn;
}

  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
	HttpSession session = request.getSession();
    ShoppingCart cart;
	
    synchronized(session) {
     String userid = (String)session.getAttribute("userid");
	 int orderId = Integer.parseInt(request.getParameter("orderID"));
	 
      cart = (ShoppingCart)session.getAttribute("shoppingCart");
	   if(userid == null)
		   {
			   us="Login";
		   }
		   else{
			   us= "Hello, "+userid;
		   }
       String itemID = request.getParameter("itemID");
      if (itemID != null) {
        String numItemsString = request.getParameter("numItems");
        if (numItemsString == null) {
         
        } else {
         
          int numItems;
          try {
            numItems = Integer.parseInt(numItemsString);
          } catch(NumberFormatException nfe) {
            numItems = 1;
          }
          cart.setNumOrdered(itemID, numItems);
        }
      }
	  
	  
	  
	  
  try{
	  
	 String delDate= MySqlDataStoreUtilities.getDeliveryDate(orderId);
	 Date delivery=StringToDate(delDate);
	 
	Date cDate=new Date();
	
	diff =  delivery.getDate() - cDate.getDate();

	if(diff < 5 )
	{
		msg ="You can't cancel this order!";		
	}
	
	else{
		msg="Congratulations! Your order with orderID:"+orderId+" has been cancelled successfully!";
	MySqlDataStoreUtilities.deleteOrder(orderId);
	}
		  
  }
  catch(Exception e){}
  
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    String docType =
      "<!DOCTYPE HTML>\n";
    out.println(docType +
                "<HTML>\n" +
                "<HEAD><TITLE>Completion</TITLE>\n" +
				"<meta http-equiv="+"Content-Type"+" content="+"text/html; charset=utf-8"+" />\n" +
				"<link rel="+"stylesheet"+" href="+"styles.css"+" type="+"text/css"+" />\n" +
				"</head>" +
				"<body>"+
				"<div id="+"container"+">\n");
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
			
		
			// out.println("<li class="+""+"><a href="+"/BestDealHome/OrderPage"+">Cart("+cart.getItemsOrdered().size()+")</a></li></div>");
			}
        out.println("</div></nav>");
		 out.println("<div id="+"body"+">\n" +
	
		 "<section id="+"content"+">\n");
		 
		out.println("Days left for delivery:"+diff);
 out.println("<h3>"+msg+"</h3><br>");
 

 
 
	out.println("</section>");
	out.println("<div class="+"clear"+"></div> \n");
	out.println("</div>");
	 out.println("\n</BODY></HTML>");
    }
}
}
