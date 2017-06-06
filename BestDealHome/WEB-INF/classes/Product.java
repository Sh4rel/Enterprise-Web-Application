import java.util.ArrayList;
import java.util.List;


public class Product {
    String retailer;
    String name;
    String id;
    String image;
    String condition;
    int price;
   
    public Product(String name, String id, int price, String image){
  this.name=name;
  this.id=id;
 this.price=price;
 this.image=image;
	}


	void setId(String id) {
	this.id = id;
}

public String getId() {
		return id;
	}

public String getRetailer() {
		return retailer;
	}

void setRetailer(String retailer) {
	this.retailer = retailer;
}

public String getImage() {
		return image;
	}

void setImage(String image) {
	this.image = image;
}

void setCondition(String condition) {
	this.condition = condition;
}


public String getCondition() {
	return condition;
}

public int getPrice() {
		return price;
	}
void setPrice(int price) {
	this.price = price;
}

public String getName() {
		return name;
	}

void setName(String name) {
	this.name = name;
}
}
