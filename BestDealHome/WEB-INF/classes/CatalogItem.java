

/** Describes a catalog item for on-line store. The itemID
 *  uniquely identifies the item, the short description
 *  gives brief info like the book title and author,
 *  the long description describes the item in a couple
 *  of sentences, and the cost gives the current per-item price.
 *  Both the short and long descriptions can contain HTML
 *  markup.
 *  <P>
 *  Taken from Core Servlets and JavaServer Pages 2nd Edition
 *  from Prentice Hall and Sun Microsystems Press,
 *  http://www.coreservlets.com/.
 *  &copy; 2003 Marty Hall; may be freely used or adapted.
 */

public class CatalogItem {
		private String name;
		private String type;
	private double cost;
	private String image;
	private String retailer;
	private String condition;
	private double discount;
	private String itemID;

 public CatalogItem(String itemID,String name,String type, double cost, String image, String retailer, String condition, double discount) {
        this.itemID = itemID;
		this.name = name;
		this.type = type;
        this.cost = cost;
        this.image = image;
        this.retailer = retailer;
        this.condition = condition;
        this.discount = discount;
    }
    
  public String getItemID() {
    return(itemID);
  }

  protected void setItemID(String itemID) {
    this.itemID = itemID;
  }

public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
	
	public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRetailer() {
        return retailer;
    }

    public void setRetailer(String retailer) {
        this.retailer = retailer;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
	}
