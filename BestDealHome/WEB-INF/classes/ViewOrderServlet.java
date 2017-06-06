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


public class ViewOrderServlet extends HttpServlet {
 HashMap < Integer, ArrayList < OrderPayment >> orderPayments = new HashMap < Integer, ArrayList < OrderPayment >> ();
 String user;
 public void doGet(HttpServletRequest request,
  HttpServletResponse response)
 throws ServletException, IOException {


  HttpSession session = request.getSession();
  ShoppingCart cart;
  synchronized(session) {
   String userid = (String) session.getAttribute("userid");

   if (userid != null && userid.length() != 0)
    userid = userid.trim();

   cart = (ShoppingCart) session.getAttribute("shoppingCart");
   String itemID = request.getParameter("itemID");
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


   response.setContentType("text/html");
   PrintWriter out = response.getWriter();
   String docType =
    "<!DOCTYPE HTML>\n";
   out.println(docType +
    "<HTML>\n" +
    "<HEAD><TITLE>View Order</TITLE>\n" +
    "<meta http-equiv=" + "Content-Type" + " content=" + "text/html; charset=utf-8" + " />\n" +
    "<link rel=" + "stylesheet" + " href=" + "styles.css" + " type=" + "text/css" + " />\n" +
    "</head>" +
    "<body>" +
    "<div id=" + "container" + ">\n");
   out.println("<nav>");
   out.println("<ul>");
   out.println("<li class=" + "start selected" + "><a href=" + "/BestDealHome/HomeServlet" + ">Home</a></li>" +
    "<li class=" + "" + "><a href=" + "/BestDealHome/Phones" + ">Phones</a></li>" +
    "<li class=" + "" + "><a href=" + "/BestDealHome/Tablets" + ">Tablets</a></li>" +
    "<li class=" + "" + "><a href=" + "/BestDealHome/Laptops" + ">Laptops</a></li>" +
    "<li class=" + "" + "><a href=" + "/BestDealHome/TvPage" + ">TvPage</a></li>" +
    "<div style=float:" + "right" + ">" +
    "<li class=" + "" + "><a href=" + "" + ">Hello," + userid + "</a></li>" +
    "<li class=" + "" + "><a href=" + "/BestDealHome/OrderPage" + ">Cart(" + cart.getItemsOrdered().size() + ")</a></li></div>");
   out.println("</nav>");
   out.println("<div id=" + "body" + ">\n" +

    "<section id=" + "content" + ">\n");
   //Dispaly orders here
   try {
    orderPayments = MySqlDataStoreUtilities.selectOrder();
   } catch (Exception e) {}

  

   String checkoutOrderURL =
    response.encodeURL("/BestDealHome/CancelOrderServlet");
	
 out.println("<TABLE BORDER=1 >\n" +
    "<TR BGCOLOR=\"#FFAD00\">\n" +
    "  <TH>Order Id<TH>Delivery date<TH> Order Price<TH>Delivery Address<TH><TH>\n");
	
 
   Set s = orderPayments.keySet();
   for (Iterator i = s.iterator(); i.hasNext();) {
    int key = (int) i.next();
    ArrayList < OrderPayment > listOrderPayment = orderPayments.get(key);
    for (int j = 0; j < listOrderPayment.size(); j++) {
     OrderPayment od = listOrderPayment.get(j);
     user = od.getUserName();
     if (user.equals(userid)) {
		 out.println("<FORM  ACTION=\"" + checkoutOrderURL + "\">\n");
		 out.println("<INPUT TYPE=\"HIDDEN\" NAME=\"orderID\" " +
           " VALUE=\"" + od.getOrderId() + "\">\n" );
      out.println("<TR>\n" +
       "  <TD> " + od.getOrderId() + "\n" +
       "  <TD>" + od.getOrderName() + "\n" +
       "  <TD>" + od.getOrderPrice() + "\n" +
       "  <TD>" + od.getUserAddress() + "\n" +
    //   "  <TD>" + od.getDate() + "\n" +
       "<TD><centre><INPUT TYPE=\"SUBMIT\"\n" +
     "VALUE=\"Cancel Order\"></center></td>\n" +
	" </tr></FORM>");
	 
	 }
    }
   }

	
   out.println("</TABLE>\n" );
   out.println("</section>");
   out.println("<div class=" + "clear" + "></div> \n");
   out.println("</div>");
   out.println("\n</BODY></HTML>");
  }
 }
}