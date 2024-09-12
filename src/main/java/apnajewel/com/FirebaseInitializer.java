package apnajewel.com;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.cloud.StorageClient;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseInitializer {
    
    private static FirebaseInitializer instance;
    private FirebaseInitializer() throws IOException {
        initialize();
    }

    public static FirebaseInitializer getInstance() throws IOException {
        if (instance == null) {
            instance = new FirebaseInitializer();
        }
        return instance;
    }

    public static void initialize() throws IOException {
        FileInputStream serviceAccount = new FileInputStream("apna_jewel/src/main/resources/apnajewel.json");
        final String BUCKET_NAME = "apnajewel-a2466.appspot.com";

        @SuppressWarnings("deprecation")
        FirebaseOptions options = new FirebaseOptions.Builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .setDatabaseUrl("https://apnajewel-a2466.firebaseio.com") // Update with your Firebase Database URL
            .setProjectId("apnajewel-a2466") // Update with your Firebase Project ID
            .setStorageBucket(BUCKET_NAME)
            .build();

        FirebaseApp.initializeApp(options);
        System.out.println("Firebase Initialized");
    }

    public static Firestore getFirestore() throws IOException {
        return FirestoreClient.getFirestore();
    }
    
    public static FirebaseDatabase getDatabase() {
        return FirebaseDatabase.getInstance();
    }

    public static Bucket getStorageBucket() {
        return StorageClient.getInstance().bucket();
    }

    public String getUrl(String collectionName, String documentName) throws ExecutionException, InterruptedException, IOException {
        //FirebaseStorageExample.initializeFirebase();
        Firestore db = getFirestore();
        DocumentReference docRef = db.collection(collectionName).document(documentName);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        if (document.exists()) {
            return document.getString("url");
        } else {
            System.out.println("No such document!");
            return null;
        }
    }
}
