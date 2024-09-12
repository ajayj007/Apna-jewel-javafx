package apnajewel.com;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;

public class Login_page extends Application {

    private String url1;
    private String url2;
    private TextField password;
    private TextField emailField;

    public Login_page() throws ExecutionException, InterruptedException, IOException {
        FirebaseInitializer firebaseInitializer1 = FirebaseInitializer.getInstance();
        url1 = firebaseInitializer1.getUrl("ImageUrl", "LogoImage1");
        url2 = firebaseInitializer1.getUrl("ImageUrl", "BackImage1");
        System.out.println("Background Image URL: " + url1);
        System.out.println("Local Image URL: " + url2);
    }

    @Override
    public void start(Stage primaryStage) throws ExecutionException, InterruptedException, IOException {
        // Title
        Label title = new Label("Login");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333;");
        VBox titleBox = new VBox(title);
        titleBox.setAlignment(Pos.BASELINE_CENTER);
        titleBox.setPrefSize(50, 50);

        // Input fields
        emailField = new TextField();
        emailField.setPromptText("Name");
        emailField.setStyle("-fx-background-color: #ffffff; -fx-border-color: #007BFF; -fx-border-width: 2px; -fx-border-radius: 4; -fx-padding: 10; -fx-font-size: 16px;");

        password = new TextField();
        password.setPromptText("Password");
        password.setStyle("-fx-background-color: #ffffff; -fx-border-color: #007BFF; -fx-border-width: 2px; -fx-border-radius: 4; -fx-padding: 10; -fx-font-size: 16px;");

        VBox napa = new VBox(emailField, password);
        napa.setAlignment(Pos.CENTER);
        napa.setPrefSize(100, 20);
        napa.setSpacing(30);

        // Login button
        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #007BFF; -fx-text-fill: white; -fx-border-radius: 4; -fx-padding: 10 20; -fx-font-size: 16px;");
        VBox loginBox = new VBox(loginButton);
        loginBox.setAlignment(Pos.CENTER);
        loginButton.setOnAction(e -> {
            String ans = loginCheck();
            jewelary jewel = new jewelary(ans);
            System.out.println(ans);

            if(!(ans==null)){
               jewel.homeMain(primaryStage);
            }
            
        });

        Label Signupconnect = new Label("Don't have an account?");
        Signupconnect.setAlignment(Pos.CENTER);

        Button signup = new Button("Sign Up");
        signup.setAlignment(Pos.CENTER);
        Signup signu = new Signup();
        signup.setOnAction(e -> {
            try {
                signu.sign(primaryStage);
            } catch (ExecutionException | InterruptedException | IOException e1) {
                e1.printStackTrace();
            }
        });

        HBox signupline = new HBox(Signupconnect, signup);
        signupline.setAlignment(Pos.CENTER);

        // AuthBox
        VBox authBox = new VBox(titleBox, napa, loginBox, signupline);
        authBox.setAlignment(Pos.CENTER);
        authBox.setPrefSize(250, 200);
        authBox.setSpacing(60);

        // Namebox
        HBox namebox = new HBox(authBox);
        namebox.setAlignment(Pos.CENTER);
        namebox.setPrefSize(500, 400);

        // Clip for rounded corners
        Rectangle clip = new Rectangle(600, 630);
        clip.setArcWidth(50);
        clip.setArcHeight(50);
        clip.setFill(Color.ROSYBROWN);

        // Second part with background image
        VBox secpart = new VBox();
        secpart.setAlignment(Pos.CENTER);
        secpart.setPrefSize(600, 150);
        secpart.setStyle("-fx-padding: 10px; ");

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
        round.setPrefSize(600, 450);
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
        mainpartBox.setAlignment(Pos.CENTER);
        mainpartBox.setPrefSize(1600, 600);
        mainpartBox.setStyle(" -fx-alignment: center;");

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

        VBox maiBox = new VBox();
        maiBox.getChildren().addAll(pagenameBox, mainpartBox);
        maiBox.setPrefSize(1600, 800);
        maiBox.setStyle("-fx-border-color: #333; -fx-padding: 20;");
        maiBox.setBackground(new Background(background1));

        Scene scene = new Scene(maiBox, 2000, 1000);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Jewelry Web App - Login");
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

    
private String loginCheck() {
    String email = emailField.getText();
    String password = this.password.getText();

    try {
        String apiKey = "AIzaSyB21-MYouRxYS8Gi5YLVfFOroE1GZDKG6c";
        URL url = new URL("https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=" + apiKey);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        conn.setDoInput(true);
        conn.setDoOutput(true);

        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put("email", email);
        jsonRequest.put("password", password);
        jsonRequest.put("returnSecureToken", true);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonRequest.toString().getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        int responseCode = conn.getResponseCode();
        InputStream responseStream = (responseCode == 200) ? conn.getInputStream() : conn.getErrorStream();
        String response = convertStreamToString(responseStream);

        if (responseCode == 200) {
            //showAlert("Welcome", "Login Successfully");
            JSONObject jsonResponse = new JSONObject(response);
            String userId = jsonResponse.getString("localId");

            return userId;
        } else {
            JSONObject jsonResponse = new JSONObject(response);
            String errorMessage = jsonResponse.has("error") ? jsonResponse.getJSONObject("error").getJSONArray("errors").getJSONObject(0).getString("message") : response;
            showAlert("Invalid Login", "Error: " + errorMessage);
            System.err.println("Error response: " + response); // Print the full response for debugging
            return null;
        }
    } catch (Exception e) {
        e.printStackTrace();
        showAlert("Error", "An unexpected error occurred.");
        return null;
    }
}

private String convertStreamToString(InputStream is) throws IOException {
    StringBuilder sb = new StringBuilder();
    BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
    String line;
    while ((line = reader.readLine()) != null) {
        sb.append(line);
    }
    return sb.toString();
}


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
