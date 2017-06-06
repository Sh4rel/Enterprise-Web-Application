
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.HashMap;
import java.util.Map;


public class UpdateProductInfo extends HttpServlet{
	 public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
		  
		  HttpSession session = request.getSession();
		  
		  
		
		  
		  
	synchronized(session){
		
		 String userid = (String) session.getAttribute("userid");
		
		  String itemId = request.getParameter("itemid");
		  //String itemName = request.getParameter("itemname");
		  String itemPrice = request.getParameter("cost");
		  double cost=Double.parseDouble(itemPrice);
		  String retailer = request.getParameter("retailer");
		 // String itemCondition = request.getParameter("condition");
		
		if(userid != null && userid.length() != 0) {
            userid = userid.trim();
        }
try
{
	MySqlDataStoreUtilities.updateProducts(itemId ,cost,retailer);
//MySqlDataStoreUtilities.insertProduct("123" ,"Len",12,"len","new");

}
catch(Exception e){}
PrintWriter out = response.getWriter();
 String docType =
      "<!DOCTYPE HTML>\n";
       out.println(docType +
                "<HTML>\n" +
                "<br><HEAD><TITLE>Update confirmation</TITLE>\n" +
				"<meta http-equiv="+"Content-Type"+" content="+"text/html; charset=utf-8"+" />\n" +
				"<link rel="+"stylesheet"+" href="+"styles.css"+" type="+"text/css"+" />\n" +
				"</head>" +
				"<body>"+
				"<div id="+"container"+">\n");
				
				 out.println("<nav>");
   out.println("<ul>");
   out.println("<li class=" + "start selected" + "><a href=" + "/BestDealHome/AddProducts" + ">Home</a></li>" );
    out.println("</nav>");
				//out.println(userid+" "+itemId +" "+cost +" "+retailer);
				out.println("<header>"+"Product updated added!"+"</header><br>");
		 out.println("<div id="+"body"+">\n" +
	
          "</body>");
	   
	out.println("<div class="+"clear"+"></div> \n");
	out.println("</div>");
        out.println("</html>");
	

	
}
	  }
}