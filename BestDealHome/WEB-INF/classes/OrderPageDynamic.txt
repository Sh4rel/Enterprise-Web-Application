

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.text.*;

public class OrderPage extends HttpServlet {
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
		  String checkoutURL,us;
		  
		 HttpSession session = request.getSession();
    ShoppingCart cart;
    synchronized(session) {
		String userid = (String)session.getAttribute("userid");
      cart = (ShoppingCart)session.getAttribute("shoppingCart");
	  
	   if(userid == null)
		   {
			   us="Login";
		   }
		   else{
			   us= "Hello, "+userid;
		   }
	
      if (cart == null) {
        cart = new ShoppingCart();
        session.setAttribute("shoppingCart", cart);
      }

	  String itemID1 = request.getParameter("itemID1");
	  	  
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
          } catch(NumberFormatException nfe) {
            numItems = 1;
          }
          cart.setNumOrdered(itemID, numItems);
        }
      }
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    String title = "Status of Your Order";
    String docType =
      "<!DOCTYPE HTML>\n";
    out.println(docType  +
                "<HTML>\n" +
                "<HEAD><TITLE>" + title + "</TITLE>\n" +
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
			"<div style=float:"+"right"+">" +
			"<li class="+""+"><a href="+"LoginPageServlet"+">"+us+"</a></li>"+
						"<li class="+""+"><a href="+"/BestDealHome/OrderPage"+">Cart("+cart.getItemsOrdered().size()+")</a></li></div>"); 
        out.println("</nav>");
		
		List itemsOrdered = cart.getItemsOrdered();
		
		//to remove items from cart
		if(itemID1 != null){
			for (Iterator<ItemOrder> iter = itemsOrdered.listIterator(); iter.hasNext(); ) 
			{
				
				ItemOrder io = iter.next();
                   String del= io.getItemID();				
				if (del.equals(itemID1))
				{
					iter.remove();
				}
			}
			
		}
		
      if (itemsOrdered.size() == 0) {
        out.println("<H2><I>No items in your cart...</I></H2>");
      } else {
        // If there is at least one item in cart, show table
        // of items ordered.
        out.println
          ("<TABLE BORDER=1 ALIGN=\"CENTER\">\n" +
           "<TR BGCOLOR=\"#FFAD00\">\n" +
           "  <TH>Item ID<TH>Description\n" +
           "  <TH>Unit Cost<TH>Quantity<TH>Total Cost<TH>");
        ItemOrder order;
        NumberFormat formatter =
          NumberFormat.getCurrencyInstance();
        for(int i=0; i<itemsOrdered.size(); i++) {
          order = (ItemOrder)itemsOrdered.get(i);
          out.println
            ("<TR>\n" +
             "  <TD>" + order.getItemID() + "\n" +
            "  <TD>" + order.getName() + "\n" +
             "  <TD>" +
             formatter.format(order.getUnitCost()) + "\n" +
             "  <TD>" +
             "<FORM>\n" +  // Submit to current URL
             "<INPUT TYPE=\"HIDDEN\" NAME=\"itemID\"\n" +
             "       VALUE=\"" + order.getItemID() + "\">\n" +
		      //"VALUE=\"" + order.getName()+ "\">\n" +
             "<INPUT TYPE=\"TEXT\" NAME=\"numItems\"\n" +
             "       SIZE=3 VALUE=\"" + 
             order.getNumItems() + "\">\n" +
             "  <TD>" +
             formatter.format(order.getTotalCost())+
			 "  <TD>" +
				"<FORM>"+
				  "<SMALL>\n" +
				   "<INPUT TYPE=\"HIDDEN\" NAME=\"itemID1\"\n" +
             "       VALUE=\"" + order.getItemID() + "\">\n" +
             "<INPUT TYPE=\"SUBMIT\"\n "+
             "       VALUE=\"Remove Item\">\n" +
			   "</SMALL>\n" +
             "</FORM>\n" );
        }
		
		if(userid == null){
			checkoutURL =
          response.encodeURL("/BestDealHome/LoginPageServlet");
		  
		}
		else{
         checkoutURL =
          response.encodeURL("/BestDealHome/CheckoutServlet");
		
		}
        // "Proceed to Checkout" button below table
        out.println
          ("</TABLE>\n" +
           "<FORM METHOD='GET' ACTION=\"" + checkoutURL + "\">\n" +
           "<BIG><CENTER>\n" +
           "<INPUT TYPE=\"SUBMIT\"\n" +
           "       VALUE=\"Proceed to Checkout\">\n" +
           "</CENTER></BIG></FORM>");
      }
      out.println("</BODY></HTML>");
    }
	
	
  }
}
