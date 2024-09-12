package apnajewel.com;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.storage.Bucket;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

public class jewelary  {
    FilterExample filterExample = new FilterExample();
    ComboBox<String> categoryComboBox;
    ComboBox<String> genderComboBox;
    private ListView<Map<String, Object>> listView;
    VBox dataBox;
    private int clicker = 1;
    Button allcategoryButton;
    Button bestsellerButton;
    Button collectionButton;
    Button weddingButton;
    Button giftingButton;
    Button moreButton;
    BorderPane centerPane;
    final int totalImage = 5;
    private Circle dots [] = new Circle[totalImage];
    private int currentIndex =0;
    private ImageView imageView;
    private CartService cartService;
    private static jewelary instance;
    private Node currentGlowingNode;
    private String userId;
    private Timeline timeline;
   

    public static jewelary getInstance() {
        return instance;
    }
    

    public jewelary(String userId){
        this.userId = userId;
        instance = this;
        System.out.println(userId);
    }

    public void centerMethod(String tag) {
       // centerPane = new BorderPane();
        centerPane.setCenter(createCategoryContent(tag));
        System.out.println("middle");
    }
    
    public void homeMain(Stage primaryStage) {
        // Create the main layout
         try {
            FirebaseInitializer.getInstance();
            centerPane = new BorderPane();
            this.cartService = new CartService(userId);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }


        BorderPane mainLayout = new BorderPane();
        mainLayout.setStyle("-fx-border-color: blue; -fx-border-width: 3;"); // Border for the main layout

        // Create and configure the top layout
        VBox topLayout = new VBox();
       // topLayout.setSpacing(20);
       // topLayout.setStyle("-fx-background-color: #f3ccff;");
          allcategoryButton = new Button();
          bestsellerButton = new Button();
          collectionButton = new Button();
          weddingButton= new Button();
          giftingButton = new Button();
          moreButton = new Button();
          
         VBox allcategory = createVBox("All Jewellary");
         allcategoryButton.setGraphic(allcategory);
         VBox bestseller = createVBox("Bestseller");
         bestsellerButton.setGraphic(bestseller);
         VBox collection = createVBox("Collections");
         collectionButton.setGraphic(collection);
         VBox wedding = createVBox("Wedding");
         weddingButton.setGraphic(wedding);
         VBox gifting = createVBox("Gifting");
         giftingButton.setGraphic(gifting);
         VBox More = createVBox("More");
         moreButton.setGraphic(More);
        
        Region spacerhbox11 = new Region();
        spacerhbox11.setPrefWidth(20);

        Region spacerhbox12 = new Region();
        spacerhbox12.setPrefWidth(20);

        HBox hbox1 = new HBox();
        hbox1.getChildren().addAll(spacerhbox11,allcategory,bestseller,collection,wedding,gifting,More,spacerhbox12);
        hbox1.setSpacing(160);
        hbox1.prefHeight(100);
       // hbox1.setAlignment(Pos.CENTER);
        
        Region spacer = new Region();
        spacer.setPrefWidth(20);

        HBox hbox2 = new HBox();
        hbox2.getChildren().addAll(spacer,Imagelogovbox(), createSearchBar());
        hbox2.setStyle("-fx-background-color: #f3ccff;");
        hbox2.setSpacing(20);
        
       

        // Create and set the default content
        if (centerPane == null) {
            System.out.println("centerPane is null");
        } else {
            centerPane = new BorderPane();
            centerPane.setStyle("-fx-border-color: red; -fx-border-width: 2;");
        }
        
       
        centerPane.setCenter(createHomeContent());
        if(clicker==1){
            timeline.playFromStart();
            clicker++;
        }
        centerPane.setStyle("-fx-border-color: red; -fx-border-width: 2;"); // Border for the center content

        ScrollPane scrollPane = new ScrollPane(centerPane);
        scrollPane.setPannable(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        // Set initial layout
        mainLayout.setTop(topLayout);
        mainLayout.setCenter(scrollPane);

        Image home_iconImage = new Image("home_icon2.png");
        ImageView home_ImageView = creatImageView(home_iconImage);
        Image custmisImage = new Image("custmise_icon.png");
        ImageView custmisImageView = creatImageView(custmisImage);
        Image wishlistImage = new Image("wishlist_icon.png");
        ImageView wishlistImageView = creatImageView(wishlistImage);
        Image cartImage = new Image("cart_icon.png");
        ImageView cartImageView = creatImageView(cartImage);
        Image accountImage = new Image("account_icon.png");
        ImageView accountImageView = new ImageView(accountImage);
       
    
        // Create buttons and their actions
        Button homeButton = createdButton(home_ImageView);
       // homeButton.setGraphic(home_ImageView);
        Button custmizeButton = createdButton(custmisImageView);
        Button wishListButton = createdButton(wishlistImageView);
        Button cartButton = createdButton(cartImageView);
        Button accountButton = createdButton(accountImageView);

         homeButton.setOnMouseClicked(e -> centerPane.setCenter(createHomeContent()));
        //  homeButton.setOnShown(event -> {
        //      System.out.println("Home page shown, starting timeline.");
        //      timeline.playFromStart();  // Start the timeline when the home page is shown
        //  });
    
        //  homeButton.setOnHidden(e -> {
        //      System.out.println("Home page hidden, stopping timeline.");
        //      timeline.stop();  // Stop the timeline when the home page is hidden
        //  });

        cartButton.setOnMouseClicked(e -> centerPane.setCenter(createCartContent()));
        accountButton.setOnMouseClicked(e -> {
            try {
                centerPane.setCenter(createAccountPage());
            } catch (IOException e1) {
                
                e1.printStackTrace();
            }
        });

        allcategory.setOnMouseClicked(e ->{ 
            handleGlowEffect(allcategory);
        centerPane.setCenter(createCategoryContent("all"));
    });
        bestseller.setOnMouseClicked(e ->{
            handleGlowEffect(bestseller); 
        centerPane.setCenter(createCategoryContent("bestseller"));
    });
        collection.setOnMouseClicked(e -> {
            handleGlowEffect(collection);
            centerPane.setCenter(createCategoryContent("collection"));
        });
        wedding.setOnMouseClicked(e -> {
            handleGlowEffect(wedding);
            centerPane.setCenter(createCategoryContent("wedding"));
        });
        gifting.setOnMouseClicked(e -> {
            handleGlowEffect(gifting);
            centerPane.setCenter(createCategoryContent("gift"));
        });
        More.setOnMouseClicked(e -> {
            handleGlowEffect(More);
            centerPane.setCenter(createCategoryContent("more"));
        });
        
        
        

        Label homLabel = new Label("Home");
        VBox homeButtonVbox = new VBox();
        homeButtonVbox.getChildren().addAll(homeButton,homLabel);
        homeButtonVbox.setAlignment(Pos.CENTER);

        Label wishlistLabel = new Label("Wishlist");
        VBox wishListVbox = new VBox(wishListButton,wishlistLabel);
        wishListVbox.prefHeight(20);
        wishListVbox.prefWidth(20);
        wishListVbox.setAlignment(Pos.CENTER); 

        Label custmizeLabel = new Label("Custmise");
        VBox custmizeVbox = new VBox(custmizeButton,custmizeLabel);
        custmizeVbox.prefHeight(20);
        custmizeVbox.prefWidth(20);
        custmizeVbox.setAlignment(Pos.CENTER);

        Label cartLabel = new Label("Cart");
        VBox cartVbox = new VBox(cartButton,cartLabel);
        cartVbox.prefHeight(20);
        cartVbox.prefWidth(20);
        cartVbox.setAlignment(Pos.CENTER);

        Label accountLabel = new Label("Account");
        VBox accountVBox = new VBox(accountButton,accountLabel);
        accountVBox.prefHeight(20);
        accountVBox.prefWidth(20);
        accountVBox.setAlignment(Pos.CENTER);

        

        // Add buttons to a HBox
        HBox buttonBox = new HBox();
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(60);
        buttonBox.getChildren().addAll(homeButtonVbox,wishListVbox,custmizeVbox, cartVbox, accountVBox);
        Region betsearchbutton = new Region();
        betsearchbutton.setPrefWidth(50);
        hbox2.getChildren().addAll(betsearchbutton,buttonBox);

        topLayout.getChildren().addAll(hbox2,hbox1);

        // Create and show the scene
        Scene scene = new Scene(mainLayout, getScreenWidth(), getScreenHeight());
        primaryStage.setTitle("JavaFX Transparent Logo Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    
    private Button clearButton;

    public VBox createCategoryContent(String category) {
        if (categoryComboBox == null) {
            categoryComboBox = new ComboBox<>();
            categoryComboBox.setPromptText("Select Category");
           
            categoryComboBox.setStyle(
                    "-fx-background-color: #f0f0f0; " +
                            
                            "-fx-border-radius: 8px; " +
                             
                            "-fx-font-size: 16px; " +
                            "-fx-font-family: 'Arial';"+
                            "-fx-font-weight: bold;");
        }
        if (genderComboBox == null) {
            genderComboBox = new ComboBox<>();
            genderComboBox.setPromptText("Select Gender");
            
            genderComboBox.setStyle(
                    "-fx-background-color: #f0f0f0; " +
                            "-fx-font-weight: bold;" +
                            "-fx-border-radius: 8px; " +
                            
                            "-fx-font-size: 16px; " +
                            "-fx-font-family: 'Arial';");
        }
    
        if(clearButton == null){
            clearButton = new Button("clear button");
            
            clearButton.setStyle("-fx-font-size: 16;-fx-font-weight: bold;-fx-background-color: #FFC107; -fx-text-fill: black; -fx-border-radius: 10; -fx-background-radius: 10;");
        }

        if (listView == null) {
            listView = new ListView<>();
        }
        if (dataBox == null) {
            dataBox = new VBox(); // Initialize if not already done
        }
        // Populate combo boxes with jewelry-specific categories
        categoryComboBox.getItems().clear();
        genderComboBox.getItems().clear();
        categoryComboBox.getItems().addAll("Rings", "Necklaces", "Bracelets", "Earrings");
        genderComboBox.getItems().addAll("Male", "Female", "Unisex");
    
        
        HBox filterHBox = new HBox(10, categoryComboBox,genderComboBox, clearButton);
    
        // Ensure clearButton is properly initialized
        
    
        listView = new ListView<>();
        dataBox = new VBox(); // VBox to display the jewelry data
        ScrollPane scrollPane1 = new ScrollPane(dataBox);
        VBox vbox = new VBox(filterHBox, scrollPane1);
        
        // Ensure listView is initialized
        
        System.out.println("before setup method");
        setupButtons(categoryComboBox, genderComboBox, listView, dataBox);
        System.out.println("After method ");
        filterExample.fetchAndDisplayData(category, categoryComboBox, genderComboBox, listView, clearButton, dataBox,userId);
        return vbox;
    }
    

    
    
    private void setupButtons(ComboBox<String> categoryComboBox, ComboBox<String> genderComboBox, ListView<Map<String, Object>> listView, VBox dataBox) {
        
    // Setup the layout for the category content
   
        
        allcategoryButton.setOnAction(e -> filterExample.fetchAndDisplayData("all", categoryComboBox, genderComboBox, listView, clearButton, dataBox,userId));
        collectionButton.setOnAction(e -> filterExample.fetchAndDisplayData("collection", categoryComboBox, genderComboBox, listView, clearButton, dataBox,userId));
        bestsellerButton.setOnAction(e -> filterExample.fetchAndDisplayData("bestseller", categoryComboBox, genderComboBox, listView, clearButton, dataBox,userId));
        giftingButton.setOnAction(e -> filterExample.fetchAndDisplayData("gift", categoryComboBox, genderComboBox, listView, clearButton, dataBox,userId));
        weddingButton.setOnAction(e -> filterExample.fetchAndDisplayData("wedding", categoryComboBox, genderComboBox, listView, clearButton, dataBox,userId));
        
        clearButton.setOnAction(e -> {
            categoryComboBox.setValue(null);
            genderComboBox.setValue(null);
            filterExample.fetchAndDisplayData("all", categoryComboBox, genderComboBox, listView, clearButton, dataBox,userId);
        });
    }
    


    // Method to get screen width
    private double getScreenWidth() {
        return Screen.getPrimary().getVisualBounds().getWidth();
    }

    // Method to get screen height
    private double getScreenHeight() {
        return Screen.getPrimary().getVisualBounds().getHeight();
    }

    // Method to create search bar
    private VBox createSearchBar() {
        VBox searchVBox = new VBox();
        TextField searchBar = new TextField();
        searchBar.setPromptText("Search...");
        
        searchBar.setPrefHeight(40);
      //  searchBar.setAlignment(Pos.CENTER);
      searchBar.setStyle("-fx-background-radius: 15; -fx-border-radius: 15; "
      + "-fx-background-color: #ffffff; -fx-border-color: #cccccc; "
      + "-fx-border-width: 2px; -fx-font-family: 'Arial'; -fx-font-size: 14px; "
      + "-fx-padding: 0 10 0 10;");
        searchVBox.getChildren().add(searchBar);
        searchVBox.setPrefWidth(900);
      //  searchVBox.prefWidth(100);
      //  searchVBox.setStyle("-fx-border-color: black; -fx-border-width: 2;");
        searchVBox.setAlignment(Pos.CENTER);
        return searchVBox;
    }

    
    private VBox createHomeContent() {
        Image image[] = new Image[totalImage];
    for (int i = 0; i < totalImage; i++) {
        String imageUrl = "logo" + i + ".png";
        image[i] = new Image(imageUrl);
        
        if (image[i].isError()) {
            System.out.println("Error loading image: " + imageUrl);
        } else {
            System.out.println("Image loaded successfully: " + imageUrl);
        }
    }
    
    if (timeline != null) {
        timeline.stop();  // Stop any previous instance of the timeline
    }

    
    // Debugging for initial image setting
    if (image[currentIndex] != null) {
        System.out.println("Setting initial image for index: " + currentIndex);
        imageView = new ImageView(image[currentIndex]);
    } else {
        System.out.println("Initial image is null for index: " + currentIndex);
    }

    imageView.setFitHeight(700);
    imageView.setFitWidth(1000);

    HBox dotHBox = new HBox();
    dotHBox.setAlignment(Pos.CENTER);
    dotHBox.setSpacing(10);

    for (int i = 0; i < totalImage; i++) {
        dots[i] = new Circle(5, i == currentIndex ? Color.CHOCOLATE : Color.AQUA);
        System.out.println("Creating dot for image index: " + i + " - Initial color: " + (i == currentIndex ? "CHOCOLATE" : "AQUA"));
        dotHBox.getChildren().addAll(dots[i]);
    }

    // Start the image timeline
    timeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> nextImage(image)));
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.play();

    

        Region spacRegion2 = new Region();
        spacRegion2.setPrefHeight(30);
        
        Label categoryLabel = new Label("Select By Categories");
        categoryLabel.setStyle(
                "-fx-font-size: 36px;" + // Increase font size for larger text
                        "-fx-font-weight: bold;" + // Make the font bold
                        "-fx-text-fill: #333333;" + // Set text color to dark gray
                        "-fx-background-color: #FFD700;" + // Add a background color (gold)
                        "-fx-padding: 20px;" + // Add padding around the text
                        "-fx-border-width: 4px;" + // Border thickness
                        "-fx-border-color: linear-gradient(to right, #FF4500, #FFD700);" + // Gradient border color
                        "-fx-border-style: solid inside, dashed outside;" + // Pattern design for border
                        "-fx-border-radius: 15px;" + // Round the corners of the border
                        "-fx-background-radius: 15px;" + // Round the background corners
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 5, 5);" // Drop shadow for depth
        );

        // Wrap the label in an HBox for alignment
        HBox categoryTitleBox = new HBox(categoryLabel);
        categoryTitleBox.setAlignment(Pos.CENTER); // Center the label in the HBox
        categoryTitleBox.setPadding(new Insets(20)); // Add extra padding around the HBox
        categoryTitleBox.setStyle(
                "-fx-background-color: #F0F0F0;" + // Light gray background for the HBox
                        "-fx-border-color: #000000;" + // Black border for the HBox
                        "-fx-border-width: 2px;" + // Border thickness for HBox
                        "-fx-border-style: dotted;" + // Dotted border for the HBox
                        "-fx-border-radius: 20px;" + // Rounded corners for the HBox border
                        "-fx-background-radius: 20px;" // Rounded corners for the HBox background
        );

        Region spacRegion = new Region();
        spacRegion.setPrefHeight(30);

        VBox homeContent = new VBox(spacRegion2,imageView,dotHBox,categoryTitleBox,categoryGridPane(),spacRegion,jewelary.createFooter());
        homeContent.setAlignment(Pos.TOP_CENTER);
        homeContent.setSpacing(30);
        homeContent.setStyle("-fx-border-color: orange; -fx-border-width: 1;"); // Border for the home content
        
        return homeContent;
    }

    private void nextImage(Image[] images) {
        currentIndex = (currentIndex + 1) % totalImage; // Cycle through the images
    
        System.out.println("Switching to image index: " + currentIndex);
        
        if (images[currentIndex] != null) {
            imageView.setImage(images[currentIndex]);
            System.out.println("Image successfully set for index: " + currentIndex);
        } else {
            System.out.println("Image at index " + currentIndex + " is null!");
        }
        
        updateDots(); // Update the dot indicators
    }
    
    
    private void updateDots() {
        for (int i = 0; i < totalImage; i++) {
            if (dots[i] == null) {
                System.out.println("dots[" + i + "] is null! Skipping dot update.");
                continue;
            }
            System.out.println("Updating dot at index " + i + ", currentIndex: " + currentIndex);
            dots[i].setFill(i == currentIndex ? Color.CHOCOLATE : Color.AQUA);
        }
    }
    

    @SuppressWarnings("unused")
    public VBox createCartContent() {
        VBox cartContent = new VBox();
        cartContent.setAlignment(Pos.CENTER);
        cartContent.setSpacing(20); // Increased spacing for better layout
        cartContent.setStyle("-fx-padding: 20px; -fx-border-color: purple; -fx-border-width: 2px; -fx-background-color: #f9f9f9;"); // Border and background
    
        if (cartService != null) {
            // Load the cart items asynchronously
            CompletableFuture.supplyAsync(() -> {
                try {
                    ApiFuture<QuerySnapshot> future = cartService.loadCartItems();
                    QuerySnapshot snapshots = future.get();
                    return snapshots;
                } catch (Exception e) {
                    Platform.runLater(() -> {
                        System.err.println("Error loading cart items: " + e.getMessage());
                    });
                    return null;
                }
            }).thenAccept(snapshots -> {
                if (snapshots != null) {
                    Platform.runLater(() -> {
                        cartContent.getChildren().clear(); // Clear previous content
    
                        VBox productsBox = new VBox();
                        productsBox.setSpacing(15); // Increased spacing for product entries
    
                        // Process each document
                        List<QueryDocumentSnapshot> documents = snapshots.getDocuments();
                        for (QueryDocumentSnapshot doc : documents) {
                            Product product = doc.toObject(Product.class);
                            if (product != null) {
                                HBox productBox = new HBox();
                               // productBox.setAlignment(Pos.TOP_LEFT);
                                productBox.setSpacing(40);
                                productBox.setStyle("-fx-padding: 30px; -fx-background-color: #ffffff; -fx-border-radius: 20px; -fx-background-radius: 20px; -fx-border-color: #ccc; -fx-border-width: 1px;");
    
                                // Image
                                Image image = new Image(product.getImageUrl());
                                ImageView imageView = new ImageView(image);
                                imageView.setFitWidth(200);
                                imageView.setFitHeight(200);
                                imageView.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0, 0, 0);"); // Drop shadow for image
    
                                // Product Details
                                VBox detailsBox = new VBox();
                                detailsBox.setSpacing(20);
    
                                Label nameLabel = new Label("Name: " + product.getName());
                                nameLabel.setStyle("-fx-font-size: 32px; -fx-font-weight: bold;");
    
                                Label priceLabel = new Label("Price: $" + product.getPrice());
                                priceLabel.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: #2a9d8f;");
    
                                Label quantityLabel = new Label("Quantity: " + product.getQuantity());
                                quantityLabel.setStyle("-fx-font-size: 28px;");
                                
                                if(product.getQuantity()>1){
                                    product.setPrice(product.getPrice()/product.getQuantity());
                                    product.setWeight(product.getWeight()/product.getQuantity());
                                    product.setQuantity(1);
                                }
                                Button showProductButton = new Button("Show Product");
                                showProductButton.setStyle("-fx-background-color: #264653; -fx-text-fill: white; -fx-padding: 16px 32px; -fx-background-radius: 30px;");
                                showProductButton.setOnAction(event -> {
                                    // Prepare the data for showProduct
                                    FilteredList<Map<String, Object>> dummyDataList = new FilteredList<>(FXCollections.observableArrayList());
                                    System.out.println(dummyDataList);
                                    VBox productVBox = new VBox();
    
                                    // Call the productBox method here
                                    showProduct showProduct1 =showProduct.getInstance(userId);
                                    VBox productDetailVBox = showProduct1.productBox(dummyDataList, productVBox, product.getName(), product.getWeight(), product.getPrice(), product.getImageUrl(), product.getTag(), 1, product.getProd_id(),product.getQuantity());
                                    cartContent.getChildren().clear();
                                    cartContent.getChildren().add(productDetailVBox);
                                });
    
                                detailsBox.getChildren().addAll(nameLabel, priceLabel, quantityLabel, showProductButton);
    
                                // Adding image and details box into the product box
                                productBox.getChildren().addAll(imageView, detailsBox);
                                productsBox.getChildren().add(productBox);
                                productsBox.setAlignment(Pos.BASELINE_LEFT);
                            } else {
                                System.err.println("Product is null for document: " + doc.getId());
                            }
                        }
    
                        // Adding the products box to the cart content
                        cartContent.getChildren().addAll(productsBox,jewelary.createFooter());
                    });
                } else {
                    Platform.runLater(() -> {
                        System.out.println("No cart items found.");
                    });
                }
            });
        } else {
            Platform.runLater(() -> {
                System.err.println("CartService is null.");
            });
        }
    
        return cartContent;
    }
    
    private HBox createAccountPage() throws IOException {
        // Main container (HBox with left buttons and right content)
        HBox accountPage = new HBox();
        accountPage.setSpacing(80); // Space between left and right sections
        accountPage.setPadding(new Insets(50));
        accountPage.setStyle("-fx-background-color: #f4f4f9;"); // Light background color
        
        // Left side: VBox for buttons
        VBox buttonBox = new VBox();
        buttonBox.setSpacing(40); // Space between buttons
        buttonBox.setAlignment(Pos.TOP_LEFT); // Align buttons to the top left
        buttonBox.setPrefWidth(300); // Width of the left side
    
        // Style for buttons (common for all)
        String buttonStyle = "-fx-background-color: #ff9999; -fx-text-fill: white; "
                           + "-fx-font-size: 36px; -fx-font-weight: bold; "
                           + "-fx-border-color: #ff6666; -fx-border-radius: 16px; "
                           + "-fx-background-radius: 16px; -fx-padding: 20px; "
                           + "-fx-pref-width: 250px; -fx-pref-height: 50px;"; // Fixed size for all buttons
        
        // Create buttons
        Button profileButton = new Button("Profile");
        profileButton.setStyle(buttonStyle);
        
        Button ordersButton = new Button("My Orders");
        ordersButton.setStyle(buttonStyle);
        
        Button settingsButton = new Button("Settings");
        settingsButton.setStyle(buttonStyle);
        
        Button helpButton = new Button("Help");
        helpButton.setStyle(buttonStyle);
        
        // Add buttons to VBox
        buttonBox.getChildren().addAll(profileButton, ordersButton, settingsButton, helpButton);
    
        // Right side: VBox for dynamic content
        VBox contentBox = new VBox();
        contentBox.setAlignment(Pos.TOP_CENTER);
        contentBox.setPrefWidth(1200); // Width of the right side
        contentBox.setPadding(new Insets(20));
        contentBox.setStyle("-fx-background-color: white; -fx-border-color: #cccccc; -fx-border-width: 1px;"); // Border and background
        
        
        // Show profile content by default
    
         showProfile(contentBox,userId);
    
        // // Button actions: Update right side content when clicked
         profileButton.setOnAction(e -> {
            try {
                
                showProfile(contentBox,userId);

            } catch (IOException e1) {
                
                e1.printStackTrace();
            }
        });
         ordersButton.setOnAction(e ->{
             
             showOrders(contentBox);
            });
        // settingsButton.setOnAction(e -> showSettings(contentBox));
        // helpButton.setOnAction(e -> showHelp(contentBox));
    
        // Add left and right side to the HBox
        
        accountPage.getChildren().addAll(buttonBox, contentBox);
        
        return accountPage;
    }
    
    private void showProfile(VBox content, String userId) throws IOException {
        content.getChildren().clear(); // Clear existing content
    
        Firestore db = FirebaseInitializer.getFirestore();
        DocumentReference profileRef = db.collection("users").document(userId);
    
        // Asynchronously retrieve the profile document
        ApiFuture<DocumentSnapshot> future = profileRef.get();
    
        future.addListener(() -> {
            try {
                DocumentSnapshot document = future.get();
    
                if (document.exists()) {
                    String name = document.getString("name");
                    String address = document.getString("address");
                    String email = document.getString("email");
                    String phoneNumber = document.getString("phone");
                    String profileImageUrl = document.getString("profileImageUrl");
                    
                    Platform.runLater(() -> {
                        // Profile Image
                        ImageView profileImageView = new ImageView();
                        profileImageView.setFitWidth(150);
                        profileImageView.setFitHeight(150);
                        profileImageView.setClip(new Circle(75, 75, 75)); // Circular shape for the image
    
                        if (profileImageUrl != null && !profileImageUrl.isEmpty()) {
                            Image profileImage = new Image(profileImageUrl, true);
                            profileImageView.setImage(profileImage);
                        } else {
                            Image defaultImage = new Image("cart_icon.png");
                            profileImageView.setImage(defaultImage);
                        }
    
                        // UI Enhancement for each field
                        Label nameLabel = createStyledLabel("Name: " + name);
                        Label addressLabel = createStyledLabel("Address: " + address);
                        Label emailLabel = createStyledLabel("Email: " + email);
                        Label phoneLabel = createStyledLabel("Phone: " + phoneNumber);
    
                        // Edit Buttons
                        Button editNameButton = new Button("Edit Name");
                        editNameButton.setOnAction(e -> showEditNameDialog(profileRef, nameLabel));
    
                        Button editAddressButton = new Button("Edit Address");
                        editAddressButton.setOnAction(e -> showEditAddressDialog(profileRef, addressLabel));
    
                        Button editImageButton = new Button("Edit Image");
                        editImageButton.setOnAction(e -> showEditImageDialog(profileRef, profileImageView));
    
                        // Organize fields into boxes
                        VBox profileContent = new VBox(
                            createFieldBox(profileImageView, editImageButton),
                            createFieldBox(nameLabel, editNameButton),
                            createFieldBox(addressLabel, editAddressButton),
                            createFieldBox(emailLabel, null),
                            createFieldBox(phoneLabel, null)
                        );
    
                        profileContent.setSpacing(20);
                        content.getChildren().add(profileContent);
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, Executors.newSingleThreadExecutor());
    }
    
    // Method to create a label with enhanced style
    private Label createStyledLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #333;");
        return label;
    }
    
    // Method to create a box for each field
    private HBox createFieldBox(Node label, Button editButton) {
        HBox fieldBox = new HBox();
        fieldBox.setAlignment(Pos.CENTER_LEFT);
        fieldBox.setSpacing(10);
        fieldBox.setStyle("-fx-border-color: #ccc; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-padding: 15px; -fx-background-color: #f0f0f0;");
        
        if (editButton != null) {
            fieldBox.getChildren().addAll(label, editButton);
        } else {
            fieldBox.getChildren().add(label);
        }
    
        return fieldBox;
    }
     

    private void showEditImageDialog(DocumentReference profileRef, ImageView profileImageView) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Select New Profile Image");

    // Open the file chooser
    File selectedFile = fileChooser.showOpenDialog(null);
    if (selectedFile != null) {
        // Assuming you have a method to upload the file to Firebase Storage and get the URL
        String imageUrl = uploadImageToFirebaseStorage(selectedFile);

        // Update the profile with the new image URL
        profileRef.update("profileImageUrl", imageUrl).addListener(() -> {
            Platform.runLater(() -> {
                Image newProfileImage = new Image(imageUrl, true); // Load new image
                profileImageView.setImage(newProfileImage); // Update the UI
            });
        }, Executors.newSingleThreadExecutor());
    }
}
    
 public String uploadImageToFirebaseStorage(File file) {
        try {
            Bucket bucket = FirebaseInitializer.getStorageBucket();

            // Create a unique file name for the upload
            String fileName = "profile_images/" + file.getName();
            
            // Read file bytes using FileInputStream
            byte[] fileBytes = readFileToByteArray(file);
            
            // Upload the file to Firebase Storage
            @SuppressWarnings("unused")
            com.google.cloud.storage.Blob blob = bucket.create(fileName, fileBytes);
            
            // Return the public URL of the uploaded image
            return "https://storage.googleapis.com/" + bucket.getName() + "/" + fileName;
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Helper method to read file into byte array
    private byte[] readFileToByteArray(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            return data;
        }
    }

    private void showEditNameDialog(DocumentReference profileRef, Label nameLabel) {
        // Create a new stage (pop-up window)
        Stage stage = new Stage();
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        
        Label nameLabelPrompt = new Label("Enter new name:");
        TextField nameField = new TextField();
        
        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            String newName = nameField.getText();
            if (!newName.isEmpty()) {
                profileRef.update("name", newName).addListener(() -> {
                    Platform.runLater(() -> {
                        nameLabel.setText("Name: " + newName); // Update the UI
                        stage.close(); // Close the dialog
                    });
                }, Executors.newSingleThreadExecutor());
            }
        });
    
        vbox.getChildren().addAll(nameLabelPrompt, nameField, saveButton);
        Scene scene = new Scene(vbox, 300, 150);
        stage.setScene(scene);
        stage.show();
    }

    private void showEditAddressDialog(DocumentReference profileRef, Label addressLabel) {
        // Similar to the edit name dialog, but for the address
        Stage stage = new Stage();
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        
        Label addressLabelPrompt = new Label("Enter new address:");
        TextField addressField = new TextField();
        
        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            String newAddress = addressField.getText();
            if (!newAddress.isEmpty()) {
                profileRef.update("address", newAddress).addListener(() -> {
                    Platform.runLater(() -> {
                        addressLabel.setText("Address: " + newAddress); // Update the UI
                        stage.close();
                    });
                }, Executors.newSingleThreadExecutor());
            }
        });
    
        vbox.getChildren().addAll(addressLabelPrompt, addressField, saveButton);
        Scene scene = new Scene(vbox, 300, 150);
        stage.setScene(scene);
        stage.show();
    }
    
    @SuppressWarnings("unused")
    private void showOrders(VBox contentVBox){
            contentVBox.getChildren().clear();

            
            contentVBox.setAlignment(Pos.CENTER);
            contentVBox.setSpacing(20); // Increased spacing for better layout
            contentVBox.setStyle("-fx-padding: 20px; -fx-border-color: purple; -fx-border-width: 2px; -fx-background-color: #f9f9f9;"); // Border and background
        
            if (cartService != null) {
                // Load the cart items asynchronously
                CompletableFuture.supplyAsync(() -> {
                    try {
                        ApiFuture<QuerySnapshot> future = cartService.loadPurchasedItems();
                        QuerySnapshot snapshots = future.get();
                        return snapshots;
                    } catch (Exception e) {
                        Platform.runLater(() -> {
                            System.err.println("Error loading cart items: " + e.getMessage());
                        });
                        return null;
                    }
                }).thenAccept(snapshots -> {
                    if (snapshots != null) {
                        Platform.runLater(() -> {
                            contentVBox.getChildren().clear(); // Clear previous content
        
                            VBox productsBox = new VBox();
                            productsBox.setSpacing(15); // Increased spacing for product entries
        
                            // Process each document
                            List<QueryDocumentSnapshot> documents = snapshots.getDocuments();
                            for (QueryDocumentSnapshot doc : documents) {
                                Product product = doc.toObject(Product.class);
                                if (product != null) {
                                    HBox productBox = new HBox();
                                   // productBox.setAlignment(Pos.TOP_LEFT);
                                    productBox.setSpacing(40);
                                    productBox.setStyle("-fx-padding: 30px; -fx-background-color: #ffffff; -fx-border-radius: 20px; -fx-background-radius: 20px; -fx-border-color: #ccc; -fx-border-width: 1px;");
        
                                    // Image
                                    Image image = new Image(product.getImageUrl());
                                    ImageView imageView = new ImageView(image);
                                    imageView.setFitWidth(200);
                                    imageView.setFitHeight(200);
                                    imageView.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0, 0, 0);"); // Drop shadow for image
        
                                    // Product Details
                                    VBox detailsBox = new VBox();
                                    detailsBox.setSpacing(20);
        
                                    Label nameLabel = new Label("Name: " + product.getName());
                                    nameLabel.setStyle("-fx-font-size: 32px; -fx-font-weight: bold;");
        
                                    Label priceLabel = new Label("Price: $" + product.getPrice());
                                    priceLabel.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: #2a9d8f;");
        
                                    Label quantityLabel = new Label("Quantity: " + product.getQuantity());
                                    quantityLabel.setStyle("-fx-font-size: 28px;");
                                    
                                    Label dateLabel = new Label("Date of purchase: " + product.getDate());
                                    dateLabel.setStyle("-fx-font-size: 28px;");

                                    Button showProductButton = new Button("Show Product");
                                    showProductButton.setStyle("-fx-background-color: #264653; -fx-text-fill: white; -fx-padding: 16px 32px; -fx-background-radius: 30px;");
                                    showProductButton.setOnAction(event -> {
                                        // Prepare the data for showProduct
                                        FilteredList<Map<String, Object>> dummyDataList = new FilteredList<>(FXCollections.observableArrayList());
                                        System.out.println(dummyDataList);
                                        VBox productVBox = new VBox();
        
                                        // Call the productBox method here
                                        showProduct showProduct2 = showProduct.getInstance(userId);
                                        VBox vBox=showProduct2.productBox(dummyDataList, productVBox, product.getName(), product.getWeight(), product.getPrice(), product.getImageUrl(), product.getTag(), 1, product.getProd_id(), product.getQuantity());
                                        centerPane.setCenter(vBox);
                                        // contentVBox.getChildren().clear();
                                        // contentVBox.getChildren().add(productDetailVBox);
                                    });
        
                                    detailsBox.getChildren().addAll(nameLabel, priceLabel, quantityLabel,dateLabel, showProductButton);
        
                                    // Adding image and details box into the product box
                                    productBox.getChildren().addAll(imageView, detailsBox);
                                    productsBox.getChildren().add(productBox);
                                    productsBox.setAlignment(Pos.BASELINE_LEFT);
                                } else {
                                    System.err.println("Product is null for document: " + doc.getId());
                                }
                            }
        
                            // Adding the products box to the cart content
                            contentVBox.getChildren().add(productsBox);
                        });
                    } else {
                        Platform.runLater(() -> {
                            System.out.println("No cart items found.");
                        });
                    }
                });
            } else {
                Platform.runLater(() -> {
                    System.err.println("CartService is null.");
                });
            }
        
            
        
    }

    private VBox Imagelogovbox() {
        VBox logoVbox = new VBox();
        logoVbox.setAlignment(Pos.BOTTOM_CENTER); // Align the logo in the center
        Image logoImage = new Image("Brand_logo5.png"); // Replace with your logo image path
        ImageView logoImageView = new ImageView(logoImage);
        logoImageView.setFitHeight(100);
        logoImageView.setFitWidth(300);
       // logoVbox.setStyle("-fx-background-color: #f3ccff; -fx-border-color: pink; -fx-border-width: 2;"); // Match the background color with topLayout
        logoVbox.getChildren().add(logoImageView);
        return logoVbox;
    }

    private Button createdButton(ImageView icon){
          
        Button button=new Button();
        button.setGraphic(icon);
        button.setStyle("-fx-background-color: transparent; -fx-background-border: transparent; -fx-padding:0;");
       // button.setPrefWidth(100);

        button.setOnAction(e -> handleGlowEffect(button));

        return button;

    }

    private ImageView creatImageView(Image image){
        ImageView imageView = new ImageView(image);
        imageView.prefHeight(20);
        imageView.prefWidth(20);
        return imageView;
    }

    private VBox createVBox(String labelText) {
        Label label = new Label(labelText);
        label.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        VBox vBox = new VBox(label);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(10));
      //  vBox.setStyle("-fx-border-color: black; -fx-border-width: 2;");

        // Event handling for the VBox
        
        return vBox;
    }

    private void handleGlowEffect(Node node) {
        // Remove glow and reset styles from the previous glowing node
        if (currentGlowingNode != null) {
            currentGlowingNode.setEffect(null); // Remove glow effect
            if (currentGlowingNode instanceof VBox) {
                ((VBox) currentGlowingNode).setStyle(""); // Reset border style
            } else if (currentGlowingNode instanceof Button) {
                ((Button) currentGlowingNode).setStyle("-fx-background-color: transparent;"); // Reset button background
            }
        }
    
        // Apply glow effect to the new node
        DropShadow glow = new DropShadow();
        glow.setColor(Color.LIGHTBLUE);
        glow.setRadius(10); // Increased radius for visibility
        node.setEffect(glow);
    
        if (node instanceof VBox) {
            ((VBox) node).setStyle("-fx-border-color: yellow; -fx-border-width: 3;"); // Yellow border
        } else if (node instanceof Button) {
            ((Button) node).setStyle("-fx-border-color: yellow; -fx-border-width: 3; -fx-text-fill: black;"); // Yellow background for button
        }
    
        // Update the reference to the current glowing node
        currentGlowingNode = node;
    }
    

    
    

    private GridPane categoryGridPane() {
        // Common settings for ImageView
        double imageHeight = 300;
        double imageWidth = 250;
    
        // Create a styled VBox for each category
        VBox mangalsutraBox = createCategoryBox("mangalslideshow/rIcon2.jpg", "Mangalsutra", imageHeight, imageWidth);
        VBox ringsBox = createCategoryBox("mangalslideshow/rIcon2.jpg", "Rings", imageHeight, imageWidth);
        VBox neckwearBox = createCategoryBox("mangalslideshow/rIcon2.jpg", "Neckwear", imageHeight, imageWidth);
        VBox chainsBox = createCategoryBox("mangalslideshow/rIcon2.jpg", "Chains", imageHeight, imageWidth);
        VBox braceletsBox = createCategoryBox("mangalslideshow/rIcon2.jpg", "Bracelets", imageHeight, imageWidth);
        VBox banglesBox = createCategoryBox("mangalslideshow/rIcon2.jpg", "Bangles", imageHeight, imageWidth);
        VBox earringsBox = createCategoryBox("mangalslideshow/rIcon2.jpg", "Earrings", imageHeight, imageWidth);
        VBox coinsBox = createCategoryBox("mangalslideshow/rIcon2.jpg", "Coins", imageHeight, imageWidth);
    
        // Creating the GridPane and setting the layout
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
    
        // Add the VBoxes to the GridPane
        gridPane.add(mangalsutraBox, 1, 0);
        gridPane.add(ringsBox, 2, 0);
        gridPane.add(neckwearBox, 3, 0);
        gridPane.add(chainsBox, 4, 0);
    
        gridPane.add(braceletsBox, 1, 1);
        gridPane.add(banglesBox, 2, 1);
        gridPane.add(earringsBox, 3, 1);
        gridPane.add(coinsBox, 4, 1);
    
        // Set vertical and horizontal gaps between the boxes
        gridPane.setVgap(20);
        gridPane.setHgap(50);
    
        return gridPane;
    }
    
    // Helper method to create a VBox with an ImageView and Button, with styling
    private VBox createCategoryBox(String imagePath, String buttonText, double imageHeight, double imageWidth) {
        // Create ImageView
        Image image = new Image(imagePath);
        ImageView imageView = creatImageView(image);
        imageView.setFitHeight(imageHeight);
        imageView.setFitWidth(imageWidth);
    
        // Create Button
        Button button = new Button(buttonText);
        button.setStyle("-fx-font-size: 16px; -fx-padding: 10px; -fx-background-color: #FFD700;-fx-border-radius: 15; -fx-background-radius:15;");
    
        // Create VBox to hold the ImageView and Button
        VBox categoryBox = new VBox(10); // 10 is the spacing between ImageView and Button
        categoryBox.setAlignment(Pos.CENTER);
        categoryBox.getChildren().addAll(imageView, button);
    
        // Add styling to the VBox (like a border and padding)
        categoryBox.setStyle("-fx-border-color: red; "
                + "-fx-border-width: 2px; "
                + "-fx-padding: 15px; "
                + "-fx-background-color: #f0f0f0; "
                + "-fx-border-radius: 10px; "
                + "-fx-background-radius: 10px;");
    
        return categoryBox;
    }
    

    public static VBox createFooter(){
        Label brandNameLabel = new Label("Apna Jewel");
        brandNameLabel.setStyle(
                "-fx-font-size: 24px;" + // Increase font size for brand name
                        "-fx-font-weight: bold;" + // Make the font bold
                        "-fx-text-fill: #FFD700;" + // Gold text color for elegance
                        "-fx-padding: 10px;" // Padding around the label
        );

        // Create a label for the copyright text
        Label copyrightLabel = new Label("© 2024 Apna Jewel. All rights reserved.");
        copyrightLabel.setStyle(
                "-fx-font-size: 14px;" + // Smaller font size for copyright
                        "-fx-text-fill: #FFFFFF;" + // White text color
                        "-fx-padding: 10px;" // Padding around the copyright
        );

        // Create a container for the footer
        VBox footerBox = new VBox(brandNameLabel, copyrightLabel);
        footerBox.setAlignment(Pos.CENTER); // Center the content in the footer
        footerBox.setPadding(new Insets(20)); // Padding around the VBox

        // Footer background color and styling
        footerBox.setStyle(
                "-fx-background-color: #1E3A5F;" + // Dark gray background for the footer
                        "-fx-border-color: #FFD700;" + // Gold border for the footer
                        "-fx-border-width: 3px;" + // Border thickness
                        "-fx-border-radius: 10px;" + // Rounded corners for the border
                        "-fx-background-radius: 10px;" // Rounded background corners
        );

        // Optional: Add shadow for depth
        footerBox.setEffect(new DropShadow(10, Color.BLACK));
        return footerBox;
    }

    
    




    
}