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



public class SaxParserBestDealXML extends DefaultHandler {
    Console console;
	static HashMap<String,Console> consoles;

	
    String consoleXmlFileName;
    String elementValueRead;

    public HashMap<String,Console> getConsoles() {
		return consoles;
	}
	
    public SaxParserBestDealXML(String consoleXmlFileName) {
        this.consoleXmlFileName = consoleXmlFileName;
        consoles = new HashMap<String, Console>();
		parseDocument();
		prettyPrint();
    }


    public void parseDocument() {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = factory.newSAXParser();
            parser.parse(consoleXmlFileName, this);
        } catch (ParserConfigurationException e) {
            System.out.println("ParserConfig error");
        } catch (SAXException e) {
            System.out.println("SAXException : xml not well formed");
        } catch (IOException e) {
            System.out.println("IO error");
        }
    }


    public void prettyPrint() {
	HashMap<String, Console> hmPrint = new HashMap<String, Console>();
	  MySqlDataStoreUtilities.delTable();
	   
	 for (String key : consoles.keySet() ) {
		 
		  Console con= consoles.get(key);
		  String itemId=con.getId();
		  String itemName = con.getName(); 
		  String phone_condition = con.getCondition(); 
		  int price=con.getPrice();
		  String phone_img=con.getImage();
		  String retailer=con.getRetailer();
	MySqlDataStoreUtilities.loadProductsWithSAX(itemId, itemName, phone_condition, price, phone_img, retailer);
		  
		
	  }
	
    }

   @Override
    public void startElement(String str1, String str2, String elementName, Attributes attributes) throws SAXException {

        if (elementName.equalsIgnoreCase("console")) {
            console = new Console();
            console.setId(attributes.getValue("id"));
            console.setRetailer(attributes.getValue("retailer"));
        }

    }

    @Override
    public void endElement(String str1, String str2, String element) throws SAXException {
 
        if (element.equals("console")) {
            consoles.put("console id:"+console.getId(), console);
	    return;
        }
        if (element.equalsIgnoreCase("image")) {
            console.setImage(elementValueRead);
	    return;
        }
        if (element.equalsIgnoreCase("name")) {
            console.setName(elementValueRead);
	    return;
        }
		  if (element.equalsIgnoreCase("condition")) {
            console.setCondition(elementValueRead);
	    return;
        }
        if(element.equalsIgnoreCase("accessory")){
           console.getAccessories().add(elementValueRead);
	    return;
        }
        if(element.equalsIgnoreCase("price")){
            console.setPrice(Integer.parseInt(elementValueRead));
	    return;
        }

    }

    @Override
    public void characters(char[] content, int begin, int end) throws SAXException {
        elementValueRead = new String(content, begin, end);
    }


  
}
