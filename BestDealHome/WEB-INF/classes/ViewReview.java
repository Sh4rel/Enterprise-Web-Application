import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.text.*;

public class ViewReview extends HttpServlet {

  HashMap < String, ArrayList < Review >> reviewHashmap = new HashMap < String, ArrayList < Review >> ();
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
    String itemID = request.getParameter("itemID");
	
   if (userid == null) {
    us = "Login";
   } else {
    us = "Hello, " + userid;
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
    "<li class=" + "" + "><a href=>" + us + "</a></li>");
   out.println("</div></ul></nav>");
 
   //get itemId to get Item details
   CatalogItem it = Catalog.getItem(itemID);
   String pName=it.getName();

  try {
   reviewHashmap= MongoDBDataStoreUtilities.selectReview();
   } catch (Exception e) {}


	out.println(reviewHashmap.size());
 out.println("<TABLE BORDER=1 ALIGN=\"CENTER\">\n" +
    "<TR BGCOLOR=\"#FFAD00\">\n" +
    "  <TH>Username<TH>Product Name<TH> Product Type<TH>ReviewRating<TH>ReviewDate<TH>ReviewText"+
   "<TH>Brand<TH>Cost<TH>Retailer Name<TH>Retailer Zip<TH>Retailer city<TH>Retailer state<TH>RetailerProduct on Sale<TH>Manufacturer Name<TH>Manufacturer Rebate<TH>UserAge<TH>Gender<TH>Occupation\n");
	
	if(reviewHashmap.containsKey(pName))   
	{	
	ArrayList < Review > listReview = reviewHashmap.get(pName);
	for(int i=0;i<listReview.size();i++)
	{
	Review review=listReview.get(i);
	String user=review.getUsername();
	 if (user != null && user.length() != 0)
    user = user.trim();
	
   if(userid.equals(user))
	{
		out.println();
	out.println("<TR>\n" +
    "  <TD> " + user +"\n" +
    "  <TD>" + review.getProductname() + "\n" +
    "  <TD>" + review.getProducttype() + "\n" +
    "  <TD>" + review.getReviewrating() + "\n" +
	"  <TD>" + review.getReviewdate() + "\n" +
	"  <TD>" + review.getReviewtext() + "\n" +
	"  <TD>" + review.getBrand() + "\n" + 
	"  <TD>" + review.getCost() + "\n" + 
	"  <TD>" + review.getRetailername() + "\n" + 
		"  <TD>" + review.getRetailerzip() + "\n" + 
"  <TD>" + review.getRetailercity() + "\n" + 
"  <TD>" + review.getRetailerstate() + "\n" + 
"  <TD>" + review.getProductonsale() + "\n" + 
"  <TD>" + review.getManufacturername() + "\n" + 
"  <TD>" + review.getManufacturerrebate() + "\n" + 
"  <TD>" + review.getUserage() + "\n" + 
"  <TD>" + review.getGender() + "\n" + 
"  <TD>" + review.getOccupation() + "\n"
}

}
	}

   out.println("</div></BODY></HTML>");
  }
 }
}