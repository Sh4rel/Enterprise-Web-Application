

import java.util.ArrayList;
import java.util.List;


public class Console {
    String retailer;
    String name;
    String id;
    String image;
    String condition;
    int price;
    List<String> accessories;
    public Console(){
        accessories=new ArrayList<String>();
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

List getAccessories() {
	return accessories;
}

public String getName() {
		return name;
	}

void setName(String name) {
	this.name = name;
}





}
