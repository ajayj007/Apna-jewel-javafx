package apnajewel.com;



public class pricebreakup {
    private final String description;
    private final String amount;

    public pricebreakup(String description, String amount) {
        this.description = description;
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public String getAmount() {
        return amount;
    }
}
 
