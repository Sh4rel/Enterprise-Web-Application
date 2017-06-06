
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.HashMap;
import java.util.Map;


public class NewProduct extends HttpServlet{
	 public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
		  
		  HttpSession session = request.getSession();
		  
		  
		
		  
		  
	synchronized(session){
		
		 String userid = (String) session.getAttribute("userid");
		
		  String itemId = request.getParameter("itemid");
		  String itemName = request.getParameter("itemname");
		  String itemPrice = request.getParameter("cost");
		  int cost=Integer.parseInt(itemPrice);
		  String retailer = request.getParameter("retailer");
		  String itemCondition = request.getParameter("condition");
		  String phone_img=request.getParameter("phone_img");
		
		if(userid != null && userid.length() != 0) {
            userid = userid.trim();
        }
try
{
			MySqlDataStoreUtilities.loadProductsWithSAX(itemId, itemName, itemCondition, cost, phone_img, retailer);
//	MySqlDataStoreUtilities.insertProduct(itemId ,itemName,cost,retailer,itemCondition);

}
catch(Exception e){}
PrintWriter out = response.getWriter();
 String docType =
      "<!DOCTYPE HTML>\n";
       out.println(docType +
                "<HTML>\n" +
                "<br><HEAD><TITLE>Product confirmation</TITLE>\n" +
				"<meta http-equiv="+"Content-Type"+" content="+"text/html; charset=utf-8"+" />\n" +
				"<link rel="+"stylesheet"+" href="+"styles.css"+" type="+"text/css"+" />\n" +
				"</head>" +
				"<body>"+
				"<div id="+"container"+">\n");
				
				out.println("<nav>");	
				 out.println("<ul>");	
       out.println("<li class="+"start selected"+"><a href="+"/BestDealHome/AddProducts"+">Home</a></li></ul>" );
		        out.println("</nav>");
				out.println("<header>"+"Product successfully added!"+"</header><br>");
		 out.println("<div id="+"body"+">\n" +
	
          "</body>");
	   
	out.println("<div class="+"clear"+"></div> \n");
	out.println("</div>");
        out.println("</html>");
	

	
}
	  }
}