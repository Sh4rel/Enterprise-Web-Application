import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.text.*;
import java.util.Date;

public class WriteReview extends HttpServlet {

 public void doGet(HttpServletRequest request,
  HttpServletResponse response)
 throws ServletException, IOException {
  String checkoutURL, us;

  HttpSession session = request.getSession();
  ShoppingCart cart;
  synchronized(session) {
   String userid = (String) session.getAttribute("userid");
    String itemID = request.getParameter("itemID");
   cart = (ShoppingCart) session.getAttribute("shoppingCart");
   if (userid == null) {
    us = "Login";
   } else {
    us = "Hello, " + userid;
   }

/*
   if (cart == null) {
    cart = new ShoppingCart();
    session.setAttribute("shoppingCart", cart);
   }
  
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
   }*/
   response.setContentType("text/html");
   PrintWriter out = response.getWriter();
   DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
   Date date = new Date();
   String title = "Write Review";
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
    "<li class=" + "" + "><a href=" + "/BestDealHome/OrderPage" + ">Cart(" + cart.getItemsOrdered().size() + ")</a></li></div>");
   out.println("</nav>");

   //get itemId to get Item details
    CatalogItem it= Catalog.getItem(itemID);

 /*  out.println("<TABLE BORDER=1 ALIGN=\"CENTER\">\n" +
    "<TR BGCOLOR=\"#FFAD00\">\n" +
    "  <TH>Product Name<TH>Product Type<TH> Brand\n");
   out.println("<TR>\n" +
    "  <TD>"+ it.getName() +"\n"+ 
   	"  <TD>"+ it.getType() +"\n" +
	 "  <TD> "+ it.getItemID() +"\n"); 




   out.println("</TABLE>\n" +*/
    out.println("<FORM Action='/BestDealHome/SaveReview'>\n" +
    "<INPUT TYPE=\"HIDDEN\" NAME=\"productname\" " +
           " VALUE=\"" + it.getName() + "\">\n "+  
		   "<INPUT TYPE=\"HIDDEN\" NAME=\"producttype\" " +
           " VALUE=\"" + it.getType() + "\">\n "+  
  "<br>" +
	 "<table cellpadding="+'2'+" cellspacing="+'1'+">"+
	  "<tr><td>Product Name</td><td><input type='TEXT' value ="+it.getName() +" name='productname'></input></td>"+
	   "<tr><td>Product Type</td><td><input type='TEXT' value ="+it.getType()+" name='producttype'></input></td>"+
	    "<tr><td>Brand</td><td><input type='TEXT' value ="+it.getItemID()+" name='brand'></input></td>"+
	  "<tr><td>Price</td><td><input type='TEXT' value ="+it.getCost()+" name='cost'></input></td>"+
	      "<tr><td>Review Date</td><td><input type='date' value ="+dateFormat.format(date)+" name='date'></input></td>"+
    "</tr><tr><td>Review Text</td><td><textarea name='reviewtext' rows='5' cols='30'/></textarea></td></tr>"+
	  "</tr><tr><td>Review Rating</td>"+
		  "<td><select name="+"ratings"+" >"+
 "<option value=1>1</option>"+
  "<option value=2>2</option>"+
   "<option value=3>3</option>"+
  "<option value=4>4</option>"+
  "<option value=5>5</option></select></td></tr>"+
  
    "<tr><td>RetailerName</td><td><input type='text' value ='' name='retailername'></input></td>"+
	 "<tr><td>RetailerZip</td><td><input type='text' value ='' name='retailerzip'></input></td>"+
	  "<tr><td>RetailerCity</td><td><input type='text' value ='' name='retailercity'></input></td>"+
	   "<tr><td>RetailerState</td><td><input type='text' value ='' name='retailerstate'></input></td>"+
	    "</tr><tr><td>ProductOnSale</td>"+
		  "<td><select name="+"productonsale"+" >"+
		"<option value='yes'>Yes</option>"+
		"<option value='no'>No</option>"+
		 "<tr><td>ManufacturerName</td><td><input type='text' value ='' name='manufacturername'></input></td>"+
		  "<tr><td>ManufacturerRebate</td><td><input type='text' value ='' name='manufacturerrebate'></input></td>"+
		   	"<tr><td>UserAge</td><td><input type='text' value ='' name='userage'></input></td>"+
			 "</tr><tr><td>Gender</td>"+
		  "<td><select name="+"gender"+" >"+
 "<option value='male'>Male</option>"+
 "<option value='female'>Female</option>"+
			"<tr><td>Occupation</td><td><input type='text' value ='' name='occupation'></input></td>"+
       	
  "<tr><td colspan="+'2'+">"+
            "<center><input type="+"submit"+" value="+"SubmitReview"+" /></center><br>"+
						"</td></tr></table>"+
	
	  "</form>");
   out.println("</BODY></HTML>");
  }


 }
}