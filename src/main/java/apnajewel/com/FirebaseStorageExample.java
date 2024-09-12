package apnajewel.com;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class FirebaseStorageExample {

    private static final String SERVICE_ACCOUNT_PATH = "apna_jewel/src/main/resources/apnajewel.json";
    private static final String BUCKET_NAME = "apnajewel-a2466.appspot.com";
    private static final String COLLECTION_NAME = "ImageUrl";

    private static FirebaseStorageExample instance;
  //  private static final String FIREBASE_CONFIG_PATH = "path/to/your/firebase-config.json";

    // private FirebaseStorageExample() throws IOException {
    //     initializeFirebase();
    // }

    public static FirebaseStorageExample getInstance() throws IOException {
        if (instance == null) {
            instance = new FirebaseStorageExample();
        }
        return instance;
    }

    // public static void initializeFirebase() throws IOException {
    //     FileInputStream serviceAccount = new FileInputStream(SERVICE_ACCOUNT_PATH);
    //     @SuppressWarnings("deprecation")
    //     FirebaseOptions options = new FirebaseOptions.Builder()
    //             .setCredentials(GoogleCredentials.fromStream(serviceAccount))
    //             .setStorageBucket(BUCKET_NAME)
    //             .build();
    //     FirebaseApp.initializeApp(options);
    // }

    public static Firestore getFirestore() throws IOException {
        return FirestoreClient.getFirestore();
    }

    public static void uploadImage(String filePath, String uploadPath) throws IOException, ExecutionException, InterruptedException {
        byte[] bytes = Files.readAllBytes(Paths.get(filePath));

        BlobId blobId = BlobId.of(BUCKET_NAME, uploadPath);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("image/jpeg").build();

        Storage storage = StorageOptions.newBuilder()
                .setCredentials(GoogleCredentials.fromStream(new FileInputStream(SERVICE_ACCOUNT_PATH)))
                .build()
                .getService();

        @SuppressWarnings("unused")
        Blob blob = storage.create(blobInfo, bytes);

        // Generate and return the URL of the uploaded image
        String imageUrl = "https://storage.googleapis.com/" + BUCKET_NAME + "/" + uploadPath;
        System.out.println(imageUrl);

        FirebaseStorageExample firebaseStorageExample = new FirebaseStorageExample();
        firebaseStorageExample.addUrl(COLLECTION_NAME, "BackImage1", imageUrl);
    }

    public void addUrl(String collectionName, String documentName, String url) throws ExecutionException, InterruptedException, IOException {
        Firestore db = getFirestore();
        DocumentReference docRef = db.collection(collectionName).document(documentName);
        Map<String, Object> data = new HashMap<>();
        data.put("url", url);
        ApiFuture<WriteResult> result = docRef.set(data);
        System.out.println("Update time: " + result.get().getUpdateTime());
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

    public InputStream downloadImage(String imagePath) throws IOException {
        Storage storage = StorageOptions.newBuilder()
                .setCredentials(GoogleCredentials.fromStream(new FileInputStream(SERVICE_ACCOUNT_PATH)))
                .build()
                .getService();

        Blob blob = storage.get(BlobId.of(BUCKET_NAME, imagePath));
        if (blob != null) {
            byte[] content = blob.getContent();
            return new ByteArrayInputStream(content);
        } else {
            System.err.println("Image not found in storage.");
            return null;
        }
    }
}
