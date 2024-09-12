package apnajewel.com;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class DataProvider {
    public static ObservableList<Map<String, Object>> getJewelry() throws InterruptedException, ExecutionException, IOException {
        ObservableList<Map<String, Object>> jewelryList = FXCollections.observableArrayList();

        Firestore db = FirebaseInitializer.getFirestore();
        CollectionReference jewelry = db.collection("jewellary");

        ApiFuture<QuerySnapshot> querySnapshot = jewelry.get();

        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            Map<String, Object> item = document.getData();
            jewelryList.add(item);
        }

        return jewelryList;
    }

    public static ObservableList<Map<String, Object>> getJewelryWithTag(String tag) throws InterruptedException, ExecutionException, IOException {
        ObservableList<Map<String, Object>> jewelryList = FXCollections.observableArrayList();
    
        Firestore db = FirebaseInitializer.getFirestore();
        CollectionReference jewellary = db.collection("jewellary");
    
        // Query to fetch documents where the 'tags' array contains the specified tag
        ApiFuture<QuerySnapshot> querySnapshot = jewellary.whereArrayContains("Tags", tag).get();
    
        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            Map<String, Object> item = document.getData();
            jewelryList.add(item);
        }
    
        return jewelryList;
    }
    
}
