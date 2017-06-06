import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.text.*;


public class CheckoutServlet extends HttpServlet {


  
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
		  
		  // String userid = request.getParameter("userid");
		  
    HttpSession session = request.getSession();
    ShoppingCart cart;
	
    synchronized(session) {
		String userid = (String)session.getAttribute("userid");
      cart = (ShoppingCart)session.getAttribute("shoppingCart");
	   String itemID1 = request.getParameter("itemID1");
	  if(itemID1 != null)
		  cart.deleteFromCart(itemID1);
	  
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
  
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    String docType =
      "<!DOCTYPE HTML>\n";
    out.println(docType +
                "<HTML>\n" +
                "<HEAD><TITLE>Checkout</TITLE>\n" +
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
			"<li class="+""+"><a >Hi,"+userid+"</a></li>"+
			"<li class="+""+"><a href="+"/BestDealHome/"+" >Logout</a></li>"+
			
			"<li class="+""+"><a href="+"/BestDealHome/"+">Cart("+cart.getItemsOrdered().size()+")</a></li></div>");
        out.println("</nav>");
		
		out.println("<header><h3>Place the order<h3></header>");
		 out.println("<div id="+"body"+">\n" );
	
		List itemsOrdered = cart.getItemsOrdered(); 
		 out.println
          ("<TABLE BORDER=1 ALIGN=\"CENTER\">\n" +
           "<TR BGCOLOR=\"#FFAD00\">\n" +
           "  <TH>Item ID<TH>Description\n" +
           "  <TH>Unit Cost<TH>Quantity<TH>Total Cost");
        ItemOrder order;
       double Total=0;
	   
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
             "</FORM>\n" +
             "  <TD>" +
             formatter.format(order.getTotalCost()));
			 Total = Total + order.getTotalCost();
        }
		//out.println("<section id="+"content"+">\n");
		session.setAttribute("Total", Total); 
		 		 
		  String checkoutOrderURL =
          response.encodeURL("/BestDealHome/CompleteOrderServlet");
        // "Proceed to Checkout" button below table METHOD="+"GET"+"
		
 out.println
          ("</TABLE>\n" +
           "<FORM METHOD='POST'  ACTION=\"" + checkoutOrderURL + "\">\n" +
		   
           "<table cellpadding="+'2'+" cellspacing="+'1'+">\n" +
		   "<div align='right'><a ><h5>Total Payable: $"+Total+"</h5></a></div>"+

		   
		  "<tr><td>CreditCard Number</td>"+
		     "<td><INPUT TYPE=\"TEXT\"\n" +
           "       NAME=\"cardnumber\"></td></tr>\n" +
		    "<td>Customer Address</td>"+
			
           "<td><INPUT TYPE=\"TEXT\"\n" +
           "       NAME=\"address\"></td></tr>\n" +
		    "<td><centre><INPUT TYPE=\"SUBMIT\"\n" +
           "       VALUE=\"SUBMIT\"></center></td>\n" +
           " </tr></table></FORM>");
 
	//out.println("</section>");
	out.println("<div class="+"clear"+"></div> \n");
	out.println("</div>");
	 out.println("\n</BODY></HTML>");
    }
  }
}