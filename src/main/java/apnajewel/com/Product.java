package apnajewel.com;

import java.util.Date;

public class Product {
    private String name;
    private double price;
    private int quantity;
    private String imageUrl;
    private String tag;
    private double weight;
    private String prod_id;
    private Date date;

    // No-argument constructor
    public Product() {
    }

    public Product(String name, double price, int quantity, String imageUrl,String tag,double weight,String prod_id,Date date) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
        this.weight = weight;
        this.tag = tag;
        this.prod_id = prod_id;
        this.date = date;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public Date getDate(){
        return date;
    }
    public void setDate(Date date){
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getProd_id(){
        return prod_id;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
