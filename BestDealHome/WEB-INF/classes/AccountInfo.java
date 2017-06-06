import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.text.*;

public class AccountInfo extends HttpServlet {

  HashMap < String, User> hm = new HashMap < String, User> ();
 public void doGet(HttpServletRequest request,
  HttpServletResponse response)
 throws ServletException, IOException {
  String checkoutURL, us;

  HttpSession session = request.getSession();
  ShoppingCart cart;
  synchronized(session) {
   String userid = (String) session.getAttribute("userid");
   
   if (userid != null && userid.length() != 0)
    userid = userid.trim();
	
   cart = (ShoppingCart) session.getAttribute("shoppingCart");
   if (userid == null) {
    us = "Login";
   } else {
    us = "Hello, " + userid;
   }

   if (cart == null) {
    cart = new ShoppingCart();
    session.setAttribute("shoppingCart", cart);
   }
   String itemID = request.getParameter("itemID");
   if (itemID != null) {
    String numItemsString =
     request.getParameter("numItems");
    if (numItemsString == null) {
     cart.addItem(itemID);
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
   Date date = new Date();
   String title = "View Review";
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
    "<li class=" + "" + "><a href=" + "/BestDealHome/Phones" + ">Phones</a></li>" +
    "<li class=" + "" + "><a href=" + "/BestDealHome/Tablets" + ">Tablets</a></li>" +
    "<li class=" + "" + "><a href=" + "/BestDealHome/Laptops" + ">Laptops</a></li>" +
    "<li class=" + "" + "><a href=" + "/BestDealHome/TvPage" + ">TvPage</a></li>" +
    "<div style=float:" + "right" + ">" +
    "<li class=" + "" + "><a href=" + "LoginPageServlet" + ">" + us + "</a></li>" +
    "<li class=" + "" + "><a href=" + "/BestDealHome/" + ">Cart(" + cart.getItemsOrdered().size() + ")</a></li></div>");
   out.println("</nav>");
 
  try
{
hm=MySqlDataStoreUtilities.selectUser();
}
catch(Exception e){}

	
 out.println("<TABLE BORDER=1 ALIGN=\"CENTER\">\n" +
    "<TR BGCOLOR=\"#FFAD00\">\n" +
    "  <TH>Username<TH>Password<TH> UserType\n");
	if(hm.containsKey(userid))   
	{	
	User user = hm.get(userid);

	out.println("<TR>\n" +
    "  <TD> " + user.getUsername() +"\n" +
    "  <TD>" + user.getPassword() + "\n" +
    "  <TD>" + user.getType() + "\n");

}
	

   out.println("</BODY></HTML>");
  }


 }
}