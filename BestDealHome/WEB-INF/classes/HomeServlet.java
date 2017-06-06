import java.util.HashMap;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;



public class HomeServlet extends HttpServlet {
	
	String success="FAIL";
	
	public void init() throws ServletException {
		 success= "PASS";
       new SaxParserBestDealXML("Products.xml");
	
	  
}
	
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
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
		  
		   
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    String docType =
      "<!DOCTYPE HTML>\n";
    out.println(docType  +
                "<HTML>\n" +
                "<HEAD><TITLE>HomePage</TITLE>\n" +
				"<meta http-equiv="+"Content-Type"+" content="+"text/html; charset=utf-8"+" />\n" +
				"<script type='text/javascript' src='scripts/autocomplete.js'></script>\n" +
				"<link rel="+"stylesheet"+" href="+"styles.css"+" type="+"text/css"+" />\n" +
				"</head>" +
				"<body onload='init()'>"+ 
				"<div id="+"container"+">\n");
				out.println("<header><h1><a href="+"/BestDealHome/HomeServlet"+">"+"Best"+"<span>"+"Deal"+"</span></a></h1>\n");
                out.println("<h2>Cheapest Price Guaranteed...</h2></header>\n");
				out.println("<div style=float:right><div name='autofillform'>" +
"<input type='text' name='searchId' value='' class='input' id='searchId' onkeyup='doCompletion()'" +
"placeholder='Search here..' style='padding: 5px; font-size: 16px;' />" +
"<div id='auto-row'>"+
"<table id='complete-table' class='gridtable' style='position: absolute; width: 250px;background:white'></table>"+  
"</div></div></div><br>");
	out.println("<br><nav>");	
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
			
			//LoginPageServlet
			out.println("<div style=float:"+"right"+">" +
			"<li class="+""+"><a href="+"/BestDealHome/LoginPageServlet"+">"+us+"</a></li>");
			if(userid != null){
				 out.println("<li class="+""+"><a href="+"/BestDealHome/LogoutServlet"+">Logout</a></li>");
			
			 out.println("<li class="+""+"><a href="+"/BestDealHome/OrderPage"+">Cart("+cart.getItemsOrdered().size()+")</a></li></div>");
			}
        out.println("</nav>");
		
		 out.println("<div id="+"body"+">\n"+
		"<section id="+"content"+">\n"+ 
		"<img src="+"images/internet-shopping.jpg"+" alt="+"Phones"+" height="+"300" +"width="+"1500" +"/>\n");
		out.println("</section>");
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
		/* out.println("<section id="+"content"+">\n");
		
	out.println("</section>");*/
	
	out.println("<div class="+"clear"+"></div> \n");
	out.println("<footer><div class='footer-content'><div class='clear'></div>");
       out.println("</div><div class='footer-bottom'>");
          out.println("<p>&copy; Prices and offers are subject to change. © 2016 Best Deal. All rights reserved. BEST DEAL, the BEST DEAL logo, the tag design, MY BEST Deal, and BESTDEAL.COM are trademarks of Best Buy and its affiliated companies.</p>");
         out.println("</div></footer>");
	
	out.println("</div>");
	 out.println("\n</BODY></HTML>");
    }
 }
}


