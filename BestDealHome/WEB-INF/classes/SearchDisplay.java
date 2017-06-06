import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;


public class SearchDisplay  extends HttpServlet{
	   

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
				
				  String us;
		     HttpSession session = request.getSession();
		   
		  ShoppingCart cart;
    synchronized(session) {
		String userid = (String)session.getAttribute("userid");
		String usertype = (String)session.getAttribute("usertype");
      cart = (ShoppingCart)session.getAttribute("shoppingCart");
       if (cart == null) {
        cart = new ShoppingCart();
        session.setAttribute("shoppingCart", cart);
      }
		   
		   if(userid == null)
		   {
			   us="Login";
		   }
		   else{
			   us= "Hello, "+userid;
		   }
		  

        String action = request.getParameter("action");
        String searchId = request.getParameter("searchId");
		
		HashMap < String, Product > tdata;
   tdata = AjaxUtility.getData();
   
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    String docType =
      "<!DOCTYPE HTML>\n";
    out.println(docType +
                "<HTML>\n" +
                "<HEAD><TITLE>SearchResults</TITLE>\n" +
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
		 	"<li class="+""+"><a href="+"/BestDealHome/TvPage"+">TvPage</a></li>");
			if(usertype != null && usertype.equals("customer"))
			out.println("<li class="+""+"><a href="+"/BestDealHome/TrendingServlet"+">Trending</a></li>");
		    else if(usertype != null &&  usertype.equals("storemanager"))
			out.println("<li class="+""+"><a href="+"/BestDealHome/DataAnalyticsServlet"+">Data Analytics</a></li>");
			
			out.println("<div style=float:"+"right"+">" +
			"<li class="+""+"><a href="+"/BestDealHome/LoginPageServlet"+">"+us+"</a></li>");
			if(userid != null){
				 out.println("<li class="+""+"><a href="+"/BestDealHome/LogoutServlet"+">Logout</a></li>");
			
			 out.println("<li class="+""+"><a href="+"/BestDealHome/OrderPage"+">Cart("+cart.getItemsOrdered().size()+")</a></li></div>");
			}
         out.println("</ul>");
        out.println("</nav>");
		 	 out.println("<div id="+"body"+">\n");
		 out.println("<aside class="+"sidebar"+">");
	            out.println("<ul>");	
               out.println("<li>");
                    out.println("<h4>Categories</h4>");
                    out.println("<ul><li class="+""+"><a href="+"/BestDealHome/Phones"+">Phones</a></li>" +           
		  "<li class="+""+"><a href="+"/BestDealHome/Tablets"+">Tablets</a></li>" +           
		   "<li class="+""+"><a href="+"/BestDealHome/Laptops"+">Laptops</a></li>" +           
		 	"<li class="+""+"><a href="+"/BestDealHome/TvPage"+">TV</a></li>" +
			"<li class="+""+"><a href="+"/BestDealHome/Accessories"+">Accessories</a></li>");
                    out.println("</ul>");
                out.println("</li>");
        out.println("</ul>");
	if(userid != null){
	out.println("<ul>");	
               out.println("<li>");
                    out.println("<h4>Other Info</h4>");
                    out.println("<li class="+""+"><a href=/BestDealHome/AccountInfo>Account Info</a></li>"+
		"<li class="+""+"><a href=/BestDealHome/ViewOrderServlet>View Orders</a></li>");
                    out.println("</ul>");
                out.println("</li>");
        out.println("</ul>");
	}
		out.println("</aside> \n" );
 out.println("<section id="+"content"+">\n");
  
  out.println();
        String formURL =
          "/BestDealHome/OrderPageDynamic";
		  String writeURL =
          "/BestDealHome/WriteReview";
		  String viewURL =
          "/BestDealHome/ViewReview";
        // Pass URLs that reference own site through encodeURL.
        formURL = response.encodeURL(formURL);
		
		 Iterator it = tdata.entrySet().iterator();
		 		 
  while (it.hasNext()) {
	  
	  
   Map.Entry pi = (Map.Entry) it.next();
   Product p = (Product) pi.getValue();
   
     if (p.getId().toLowerCase().startsWith(searchId.toLowerCase())) {   
     out.println
          ("<FORM ACTION=\"" + formURL + "\">\n" +
           "<INPUT TYPE=\"HIDDEN\" NAME=\"itemID\" " +
           " VALUE=\"" + p.getId() + "\">\n" +
           "<img src=images/"+ p.getImage() +" height="+"200"+" width="+"200"+"><br>\n" +
			"<H5>" + p.getName() + "\n" +		  
		   "<H5>($" + p.getPrice() + ")</H5>\n" +
           "<INPUT TYPE=\"SUBMIT\" " +
           "VALUE=\"Buy Now\">" +
		   "</FORM>");
		   out.println("<FORM ACTION=" + writeURL + "><INPUT TYPE=\"HIDDEN\" NAME=\"itemID\" " +
           " VALUE=\"" + p.getId() + "\">\n<INPUT TYPE='SUBMIT' VALUE='Reviews'></FORM>");
		   out.println("<FORM ACTION=" + viewURL + "><INPUT TYPE=\"HIDDEN\" NAME=\"itemID\" " +
		   " VALUE=\"" + p.getId() + "\">\n<INPUT TYPE='SUBMIT' VALUE='View Review'></FORM>");
}
else {}
 
  }
  	out.println("</section>");
	out.println("<div class="+"clear"+"></div> \n");
	out.println("</div></div>");
	 out.println("\n</BODY></HTML>");
			}
}
}