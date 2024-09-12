package apnajewel.com;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CartService {
    private Firestore db;
    private CollectionReference cartRef;
    private CollectionReference purchased_cartRef;
    private String userId;

    public CartService(String userId) throws IOException {
        db = FirebaseInitializer.getFirestore(); // Method to get Firestore instance
        this.userId = userId;
        cartRef = db.collection("users").document(userId).collection("cart");
        purchased_cartRef = db.collection("users").document(userId).collection("purchased_cart");
    }

    public void addToCart(Product product) {
    System.out.println("Attempting to add product: " + product);

    // Query the cart collection to see if the product already exists
    ApiFuture<QuerySnapshot> future = cartRef.whereEqualTo("prod_id", product.getProd_id()).get();
    System.out.println(product.getProd_id());

    try {
        QuerySnapshot querySnapshot = future.get();
        System.out.println(querySnapshot);
        if (!querySnapshot.isEmpty()) {
            // Product already exists in the cart, retrieve and update it
            DocumentSnapshot existingProductDoc = querySnapshot.getDocuments().get(0);
            String docId = existingProductDoc.getId(); // Get the document ID

            // Retrieve existing values for quantity, price, and weight
            Product existingProduct = existingProductDoc.toObject(Product.class);
            @SuppressWarnings({ "null", "unused" })
            int existingQuantity = existingProduct.getQuantity();
            double existingPrice = existingProduct.getPrice();
            double existingWeight = existingProduct.getWeight();

            // Update quantity, price, and weight
            int newQuantity = product.getQuantity();
            double newPrice =  product.getPrice(); // Adjust as needed for your pricing logic
            double newWeight = product.getWeight();

            // Create a map for the updated fields
            Map<String, Object> updates = new HashMap<>();
            updates.put("quantity", newQuantity);
            updates.put("price", newPrice);
            updates.put("weight", newWeight);

            // Update the existing product in Firestore
            cartRef.document(docId).update(updates).get(); // Wait for the update to complete
            showMessage("Update valuse of Product successfully.", "Values Updated");
            System.out.println("Product updated in the cart with new values.");
        } else {
            // Product doesn't exist in the cart, add it
            ApiFuture<DocumentReference> addedDocRef = cartRef.add(product);
            addedDocRef.get(); // Wait for the write to complete
            showMessage("Product " + product.getName() + " added cart successfully.", "Added to cart");
        }
    } catch (Exception e) {
        System.err.println("Error adding/updating product in the cart: " + e.getMessage());
    }
}


    public void addtoBuy(Product product){
        System.out.println("System attempting to buy product :");
        product.setDate(new Date());

        @SuppressWarnings("unused")
        ApiFuture<DocumentReference> buy = purchased_cartRef.add(product);
        
        cartRef = db.collection("users").document("cc3RBWiOhEUf1eeNGc2ZK2CVm7f1").collection("cart");
        ApiFuture<QuerySnapshot> future = cartRef.whereEqualTo("prod_id", product.getProd_id()).get();
        QuerySnapshot querySnapshot;
        try {
            querySnapshot = future.get();
            showMessage("Product " + product.getName() + "bought successfully.", "Successfully Bought");
            if(!querySnapshot.isEmpty()){
                System.out.println("go to delete");
                deleteProductById(product.getProd_id());
            }
        } catch (Exception e) {
            
            e.printStackTrace();
        }
        
    }

    public void deleteProductById(String prodId) {
        System.out.println("Attempting to delete product with ID: " + prodId);
        
        // Query the cart collection for the document with the specified prod_id field value
        ApiFuture<QuerySnapshot> future = cartRef.whereEqualTo("prod_id", prodId).get();
    
        try {
            QuerySnapshot querySnapshot = future.get();
            if (!querySnapshot.isEmpty()) {
                for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                    // Get the actual document ID
                    DocumentReference productRef = document.getReference();
                    System.out.println("Deleting document with ID: " + productRef.getId());
    
                    // Delete the document
                    ApiFuture<WriteResult> writeResult = productRef.delete();
                    writeResult.get(); // Wait for the delete operation to complete
                   // showMessage("Product with ID " + prodId + " deleted successfully.", "Product Deleted");
                    System.out.println("Product with ID " + prodId + " deleted successfully.");
                }
            } else {
                System.out.println("No document found with prod_id: " + prodId);
            }
        } catch (Exception e) {
            System.err.println("Error deleting product: " + e.getMessage());
        }
    }
    
    
    public ApiFuture<QuerySnapshot> loadCartItems() {
        return cartRef.get();
    }

    public ApiFuture<QuerySnapshot> loadPurchasedItems(){
        return purchased_cartRef.get();
    }

   public void showMessage(String message, String title) {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
   }
}
    