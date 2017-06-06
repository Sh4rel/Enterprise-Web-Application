import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.HashMap;



public abstract class DisplayFromSAX extends HttpServlet {
  private CatalogItem[] items;
  private String[] itemIDs;
  private String title;


  
  protected void setItems(String[] itemIDs) {
    this.itemIDs = itemIDs;
    items = new CatalogItem[itemIDs.length];
    for(int i=0; i<items.length; i++) {
      items[i] = Catalog.getItem(itemIDs[i]);
    }
  }

   
  protected void setTitle(String title) {
    this.title = title;
  }
  
  SaxParserBestDealXML sx=  new SaxParserBestDealXML("Products.xml");
	HashMap<String, Console> hmPrint = sx.getConsoles();
	 
		  

  
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
    if (items == null) {
      response.sendError(response.SC_NOT_FOUND,
                         "Missing Items.");
      return;
    }
	
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    String docType =
      "<!DOCTYPE HTML>\n";
    out.println(docType +
                "<HTML>\n" +
                "<HEAD><TITLE>" + title + "</TITLE>\n" +
				"<meta http-equiv="+"Content-Type"+" content="+"text/html; charset=utf-8"+" />\n" +
				"<link rel="+"stylesheet"+" href="+"styles.css"+" type="+"text/css"+" />\n" +
				"</head>" +
				"<body>"+
				"<div id="+"container"+">\n");
				out.println("<nav>");	
		 out.println("<ul>");	
        out.println("<li><a href=/BestDealHome"+""+">BESTDEAL</a></li>");	
               out.println("</ul>");
        out.println("</nav>");
		 out.println("<div id="+"body"+">\n");
		out.println("<aside class="+"sidebar"+">");
	            out.println("<ul>");	
               out.println("<li>");
                    out.println("<h4>Categories</h4>");
                    out.println("<ul>");
					if (title == "Phones"){
						 out.println("<li><a href='ApplePhone'>Apple</a></li>");
                        out.println("<li><a href='SamsungPhone'>Samsung</a></li>");
                        out.println("<li><a href=>Lenovo</a></li>");
                        out.println("<li><a href=>Xioami</a></li>");						
					}
					else if (title == "Tablets"){
						 out.println("<li><a href='AppTab'>Apple</a></li>");
                        out.println("<li><a href='SamsungTab'>Samsung</a></li>");
                        out.println("<li><a href=>Xioami</a></li>");
					}
                       else if (title == "Laptops"){
						 out.println("<li><a href='AppLap'>Apple</a></li>");
                        out.println("<li><a href='LenovoLap'>Lenovo</a></li>");
                        out.println("<li><a href=>DELL</a></li>");
					}
					else if (title == "TV"){
						 out.println("<li><a href='SonyTv'>Bravia</a></li>");
                        out.println("<li><a href='XiTv'>Xiaomi</a></li>");
                        out.println("<li><a href=>Samsung</a></li>");
					}
					else if (title == "Accessories"){
						out.println("<li><a href=>JBL</a></li>");
                        out.println("<li><a href=>Sony</a></li>");
                        out.println("<li><a href=>Xiaomi</a></li>");
					}
										
                    out.println("</ul>");
                out.println("</li>");
                                             
        out.println("</ul>");
		out.println("</aside> \n" +
		 "<section id="+"content"+">\n");
		 
		 for (String key : hmPrint.keySet() ) {
		 
		 
		  Console con= hmPrint.get(key);
		  String itemId=con.getId();
		  String itemName = con.getName(); 
		  String phone_condition = con.getCondition(); 
		  int price=con.getPrice();
		  String phone_img=con.getImage();
		  
		  		  out.println(itemId +"\n"+ itemName +"\n"+ phone_condition +"\n"+price +"\n"+ phone_img);


	  }
		 
	 CatalogItem item;
    for(int i=0; i<items.length; i++) {
      item = items[i];
      // Show error message if subclass lists item ID
      // that's not in the catalog.
      if (item == null) {
        out.println("Unknown item ID " + itemIDs[i]);
      } else {
		  
		//  out.println("hashmap size:"+hmPrint.size());
		  
		 /* for (String key : hmt.keySet() )
		  {
			  
			   Console con= hmt.get(key);
			   //
		    if (item.getItemID().equals(con.getId()))
			{*/
		  
        out.println();
        String formURL =
          "/BestDealHome/OrderPage";
		  String writeURL =
          "/BestDealHome/WriteReview";
		  String viewURL =
          "/BestDealHome/ViewReview";
        // Pass URLs that reference own site through encodeURL.
        formURL = response.encodeURL(formURL);
        out.println
          ("<FORM ACTION=\"" + formURL + "\">\n" +
           "<INPUT TYPE=\"HIDDEN\" NAME=\"itemID\" " +
           " VALUE=\"" + item.getItemID() + "\">\n" +
           "<img src="+ item.getImage() +" height="+"200"+" width="+"200"+"><br>\n" +
			"<H5>" + item.getName() + "\n" +		  
		   "<H5>($" + item.getCost() + ")</H5>\n" +
           "<INPUT TYPE=\"SUBMIT\" " +
           "VALUE=\"Buy Now\">" +
		   "</FORM>");
		   out.println("<FORM ACTION=" + writeURL + "><INPUT TYPE=\"HIDDEN\" NAME=\"itemID\" " +
           " VALUE=\"" + item.getItemID() + "\">\n<INPUT TYPE='SUBMIT' VALUE='Reviews'></FORM>");
		   out.println("<FORM ACTION=" + viewURL + "><INPUT TYPE=\"HIDDEN\" NAME=\"itemID\" " +
		   " VALUE=\"" + item.getItemID() + "\">\n<INPUT TYPE='SUBMIT' VALUE='View Review'></FORM>");
      }
    }
	out.println("</section>");
	out.println("<div class="+"clear"+"></div> \n");
	out.println("</div>");
	 out.println("\n</BODY></HTML>");
    }
	/*}
	  }*/
}
