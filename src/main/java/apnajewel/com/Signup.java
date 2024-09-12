package apnajewel.com;

import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;

public class Signup {

    private String url1;
    private String url2;
    @SuppressWarnings("unused")
    private Stage primaryStage;
    TextField name;
    TextField emailField;
    TextField phoneField;
    TextField password;
    Firestore db;

    public Signup() throws ExecutionException, InterruptedException, IOException {
        FirebaseInitializer firebaseInitializer2 = FirebaseInitializer.getInstance();
        url1 = firebaseInitializer2.getUrl("ImageUrl", "LogoImage1");
        url2 = firebaseInitializer2.getUrl("ImageUrl", "BackImage1");
        System.out.println("Background Image URL: " + url1);
        System.out.println("Local Image URL: " + url2);
    }

    public void sign(Stage primaryStage) throws ExecutionException, InterruptedException, IOException {
        this.primaryStage = primaryStage;

        // Title
        Label title = new Label("Sign Up");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333;");
        VBox titleBox = new VBox(title);
        titleBox.setAlignment(Pos.BASELINE_CENTER);
        titleBox.setPrefSize(50, 50);
       // titleBox.setStyle("-fx-border-color: black; -fx-border-width: 2;");

        // Input fields
        name = new TextField();
        name.setPromptText("Name");
        name.setStyle("-fx-background-color: #ffffff; -fx-border-radius: 4; -fx-padding: 10; -fx-font-size: 16px;");
        
        emailField = new TextField();
        emailField.setPromptText("Enter your Email");
        emailField.setStyle("-fx-background-color: #ffffff; -fx-border-radius: 4; -fx-padding: 10; -fx-font-size: 16px;");

        phoneField = new TextField();
        phoneField.setPromptText("Enter your phone number");
        phoneField.setStyle("-fx-background-color: #ffffff;  -fx-border-radius: 4; -fx-padding: 10; -fx-font-size: 16px;");

        // Add input validation to ensure only digits are entered and length is limited to 10 digits
        phoneField.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (!event.getCharacter().matches("\\d") || phoneField.getText().length() >= 10) {
                event.consume();  // Ignore non-digit input and limit length to 10
            }
        });

        password = new TextField();
        password.setPromptText("Password");
        password.setStyle("-fx-background-color: #ffffff; -fx-padding: 10; -fx-font-size: 16px;");

        VBox napa = new VBox(name, emailField, phoneField, password);
        napa.setAlignment(Pos.CENTER);
        napa.setPrefSize(100, 20);
        napa.setSpacing(20);
      //  napa.setStyle("-fx-border-color: black; -fx-border-width: 2;");

        // Signup button
        Button signupButton = new Button("Sign Up");
        signupButton.setStyle("-fx-background-color: #007BFF; -fx-text-fill: white; -fx-border-radius: 4; -fx-padding: 10 20; -fx-font-size: 16px;");
        signupButton.setOnAction(e -> {
            try {
                String yo = signcheck();

                jewelary jewel = new jewelary(yo);
            System.out.println(yo);

            if(!(yo==null)){
               jewel.homeMain(primaryStage);
            }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        VBox loginBox = new VBox(signupButton);
        loginBox.setAlignment(Pos.CENTER);
       // loginBox.setStyle("-fx-border-color: black; -fx-border-width: 2;");

        Label signupConnect = new Label("Already have an account?");
        signupConnect.setAlignment(Pos.CENTER);

        Button login = new Button("Login");
        Login_page relogin = new Login_page();
        login.setOnAction(e -> {
            try {
                relogin.start(primaryStage);
            } catch (ExecutionException | InterruptedException | IOException e1) {
                e1.printStackTrace();
            }
        });

        HBox signupline = new HBox(signupConnect, login);
        signupline.setAlignment(Pos.CENTER);
      //  signupline.setStyle("-fx-border-color: black; -fx-border-width: 2;");

        // AuthBox
        VBox authBox = new VBox(titleBox, napa, loginBox, signupline);
        authBox.setAlignment(Pos.CENTER);
        authBox.setPrefSize(250, 300);
        authBox.setSpacing(30);
     //   authBox.setStyle("-fx-border-color: black; -fx-border-width: 2;");

        // Namebox
        HBox namebox = new HBox(authBox);
        namebox.setAlignment(Pos.CENTER);
        namebox.setPrefSize(500, 500);
       // namebox.setStyle("-fx-border-color: black; -fx-border-width: 2;");

        // Clip for rounded corners
        Rectangle clip = new Rectangle(600, 630);
        clip.setArcWidth(50);
        clip.setArcHeight(50);
        clip.setFill(Color.ROSYBROWN);

        // Second part with background image
        VBox secpart = new VBox();
        secpart.setAlignment(Pos.CENTER);
        secpart.setPrefSize(600, 200);
        secpart.setStyle("-fx-padding: 10px;");

        // Load background image from URL1
        Image backgroundImage = loadImageFromUrl(url1);
        if (backgroundImage != null) {
            BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, false)
            );
            secpart.setBackground(new Background(background));
        } else {
            System.err.println("Background image from URL1 could not be loaded.");
        }

        // StackPane with rounded corners
        StackPane round = new StackPane();
        round.setPrefSize(600, 500);
        round.getChildren().addAll(clip, namebox);

        // Load local background image from URL2
        Image backgroundImage1 = loadImageFromUrl(url2);
        BackgroundImage background1 = new BackgroundImage(
            backgroundImage1,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true)
        );

        // Main HBox
        HBox mainpartBox = new HBox(round, secpart);
        mainpartBox.setAlignment(Pos.BOTTOM_CENTER);
      //  mainpartBox.setStyle("-fx-border-color: black; -fx-border-width: 2;");

        // Label for page name
        Label Pagename = new Label();
        Pagename.setFont(new Font("Arial", 64));
        Pagename.setTextFill(Color.BLACK);

        // Text to be displayed
        String text = "Welcome To Apna Jewel...!!!";

        // Function to update the label text character by character
        final StringBuilder displayedText = new StringBuilder();
        final int[] currentIndex = {0};

        // Create a PauseTransition for each character
        PauseTransition pauseTransition = new PauseTransition(Duration.millis(100));
        pauseTransition.setOnFinished(event -> {
            if (currentIndex[0] < text.length()) {
                displayedText.append(text.charAt(currentIndex[0]));
                Pagename.setText(displayedText.toString());
                currentIndex[0]++;
                pauseTransition.playFromStart();
            }
        });

        // Start the animation
        pauseTransition.play();

        // Create a layout pane
        HBox pagenameBox = new HBox(Pagename);
        pagenameBox.setAlignment(Pos.BOTTOM_CENTER);
        pagenameBox.setPrefHeight(200);
     //   pagenameBox.setStyle("-fx-border-color: black; -fx-border-width: 2;");
        VBox maiBox = new VBox();
        maiBox.getChildren().addAll(pagenameBox, mainpartBox);
        maiBox.setPrefSize(1600, 800);
        maiBox.setStyle("-fx-border-color: #333; -fx-padding: 20;");
        maiBox.setBackground(new Background(background1));
       // maiBox.setStyle("-fx-border-color: black; -fx-border-width: 2;");

        Scene scene = new Scene(maiBox, 2000, 1000);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Jewelry Web App - Signup");
        primaryStage.show();
    }

    private Image loadImageFromUrl(String url) {
        try {
            if (url != null && !url.isEmpty()) {
                InputStream inputStream = new java.net.URL(url).openStream();
                return new Image(inputStream);
            } else {
                System.err.println("Image URL is null or empty.");
                return null;
            }
        } catch (IOException e) {
            System.err.println("Failed to load image from URL: " + e.getMessage());
            return null;
        }
    }

    private String signcheck() throws IOException {
        String email = emailField.getText();
        String password = this.password.getText();
        String phone = phoneField.getText();

        try {
            UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(email)
                .setPassword(password)
                .setDisabled(false);

            UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
            String userId = userRecord.getUid();
            System.out.println("Successfully created user: " + userRecord.getUid());

            // Now store additional user details in Firestore
            db = FirebaseStorageExample.getFirestore();

            Map<String, Object> userData = new HashMap<>();
            userData.put("name", name.getText());
            userData.put("email", email);
            userData.put("phone", phone);

            DocumentReference docRef = db.collection("users").document(userRecord.getUid());
            docRef.set(userData).get();  // Use get() to block and wait for the result

            showAlert("Success", "User created successfully.");
            return userId;

        } catch (FirebaseAuthException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to create user: " + e.getMessage());
            return null;
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
