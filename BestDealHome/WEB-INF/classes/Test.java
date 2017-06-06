import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Collection;
import java.util.Iterator;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class Test {

public static void main(String[] args) {
	
	
     SaxParserBestDealXML sx=  new SaxParserBestDealXML("Products.xml");
	HashMap<String, Console> hmPrint = sx.getConsoles();
	 for (String key : hmPrint.keySet() ) {
		 
		 
		 System.out.println(key);
		  Console con= hmPrint.get(key);
		  String itemId=con.getId();
		  String itemName = con.getName(); 
		  String phone_condition = con.getCondition(); 
		  int price=con.getPrice();
		  String phone_img=con.getImage();
		  String retailer=con.getRetailer();
		  
	    System.out.println(itemId +"\n"+ itemName +"\n"+ phone_condition +"\n"+price +"\n"+ phone_img +"\n"+ retailer);
		  
		  
		
		
	  }

    }

}