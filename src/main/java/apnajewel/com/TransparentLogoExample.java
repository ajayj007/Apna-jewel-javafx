package apnajewel.com;

import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TransparentLogoExample extends Application {

    
    final int totalImage = 5;
    final Circle dots [] = new Circle[totalImage];
    private int currentIndex = 0;
    private ImageView imageView;

    @Override
    public void start(Stage primaryStage) {
        // Create the main layout
        BorderPane mainLayout = new BorderPane();
        mainLayout.setStyle("-fx-border-color: blue; -fx-border-width: 3;"); // Border for the main layout

        // Create and configure the top layout
        VBox topLayout = new VBox();
       // topLayout.setSpacing(20);
       // topLayout.setStyle("-fx-background-color: #f3ccff;");
        
        VBox allcategory = createVBox("All Jewellary");
        VBox bestseller = createVBox("Bestseller");
        VBox collection = createVBox("Collections");
        VBox wedding = createVBox("Wedding");
        VBox gifting = createVBox("Gifting");
        VBox More = createVBox("More");
        
        Region spacerhbox11 = new Region();
        spacerhbox11.setPrefWidth(20);

        Region spacerhbox12 = new Region();
        spacerhbox12.setPrefWidth(20);

        HBox hbox1 = new HBox();
        hbox1.getChildren().addAll(spacerhbox11,allcategory,bestseller,collection,wedding,gifting,More,spacerhbox12);
        hbox1.setSpacing(150);
        hbox1.prefHeight(200);
       // hbox1.setAlignment(Pos.CENTER);
        
        Region spacer = new Region();
        spacer.setPrefWidth(20);

        HBox hbox2 = new HBox();
        hbox2.getChildren().addAll(spacer,Imagelogovbox(), createSearchBar());
        hbox2.setStyle("-fx-background-color: #f3ccff;");
        hbox2.setSpacing(20);
        
       

        // Create and set the default content
        BorderPane centerPane = new BorderPane();
        centerPane.setStyle("-fx-border-color: red; -fx-border-width: 2;"); // Border for the center content
        centerPane.setCenter(createHomeContent());

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

        homeButton.setOnAction(e -> centerPane.setCenter(createHomeContent()));
        cartButton.setOnAction(e -> centerPane.setCenter(createCartContent()));
        accountButton.setOnAction(e -> centerPane.setCenter(createAccountContent()));
        
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
        for(int i = 0 ; i<totalImage; i++){
            image[i]= new Image("logo"+i+".png");
            if(image[i].isError()){
                System.out.println("error is found :"+"logo"+i+".png");
            }
        }

        imageView = new ImageView(image[0]);
        imageView.setFitHeight(700);
        imageView.setFitWidth(1000);
       // imageView.setPreserveRatio(true);
        
        HBox dotHBox = new HBox();
        dotHBox.setAlignment(Pos.CENTER);
        dotHBox.setSpacing(10);

        for(int i =0 ; i<totalImage; i++){

            dots[i]= new Circle(5,i==currentIndex ? Color.CHOCOLATE : Color.AQUA);
            dotHBox.getChildren().addAll(dots[i]); 
        }
        
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2),e-> nextImage(image)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        

        VBox homeContent = new VBox(imageView,dotHBox,categoryGridPane());
        homeContent.setAlignment(Pos.TOP_CENTER);
        homeContent.setSpacing(30);
        homeContent.setStyle("-fx-border-color: orange; -fx-border-width: 1;"); // Border for the home content
        
        return homeContent;
    }

    private VBox createCartContent() {
        VBox cartContent = new VBox();
        cartContent.setAlignment(Pos.CENTER);
        cartContent.setSpacing(10);
        cartContent.setStyle("-fx-border-color: purple; -fx-border-width: 1;"); // Border for the cart content
        cartContent.getChildren().add(new Label("Cart Content"));
        return cartContent;
    }

    private VBox createAccountContent() {
        VBox accountContent = new VBox();
        accountContent.setAlignment(Pos.CENTER);
        accountContent.setSpacing(10);
        accountContent.setStyle("-fx-border-color: pink; -fx-border-width: 1;"); // Border for the account content
        accountContent.getChildren().add(new Label("Account Content"));
        return accountContent;
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
       ScaleTransition stEnlarge = new ScaleTransition(Duration.millis(200), button);
        stEnlarge.setToX(1.1);
        stEnlarge.setToY(1.1);

        ScaleTransition stShrink = new ScaleTransition(Duration.millis(200), button);
        stShrink.setToX(1);
        stShrink.setToY(1);

        button.setOnMouseEntered(e -> stEnlarge.playFromStart());
        button.setOnMouseExited(e -> stShrink.playFromStart());
        return button;

    }

    private ImageView creatImageView(Image image){
        ImageView imageView = new ImageView(image);
        imageView.prefHeight(20);
        imageView.prefWidth(20);
        return imageView;
    }

    private VBox createVBox(String str){
        Label label = new Label(str);
        label.setStyle("-fx-font-size: 32px; -fx-font-weight: bold;");
        
        VBox vBox = new VBox(label);
        vBox.setPrefHeight(70);
        vBox.setAlignment(Pos.CENTER);
       // vBox.setStyle("-fx-background-color: black; -fx-background-width: 2;");
        return vBox;
    }

    private void nextImage(Image[] images){
        
        currentIndex = (currentIndex + 1)% 5;
    
        imageView.setImage(images[currentIndex]);
        updateDots();
    }

    private void updateDots(){
        for(int i = 0; i< totalImage; i++){
            dots[i].setFill(i==currentIndex ? Color.CHOCOLATE : Color.AQUA);
        }
    }

    private VBox createcategoryVBox(Label label,Button button){
        Label explorLabel = new Label("Expolre");
        explorLabel.setAlignment(Pos.CENTER);
        VBox vBox = new VBox(button,label,explorLabel);
        vBox.setSpacing(10);
        vBox.setStyle("-fx-border-color: gold;");
        vBox.setAlignment(Pos.CENTER);

        return vBox;
    }

    private GridPane categoryGridPane(){
        Image magalsutrImage = new Image("mangalslideshow/rIcon2.jpg");
        ImageView mangalsutrImageView = creatImageView(magalsutrImage);
        mangalsutrImageView.setFitHeight(300);
        mangalsutrImageView.setFitWidth(250);
        Button mangalsutraButton = createdButton(mangalsutrImageView);
        Label mangalsutrLabel = new Label("Mangalsutra");
        mangalsutrLabel.setAlignment(Pos.CENTER);
        
        Image ringsImage = new Image("mangalslideshow/rIcon2.jpg");
        ImageView ringsImageView = creatImageView(ringsImage);
        ringsImageView.setFitHeight(300);
        ringsImageView.setFitWidth(250);
        Button ringsButton = createdButton(ringsImageView);
        //Label mangalsutrLabel = new Label("Mangalsutra");
          
        Image neckwearImage = new Image("mangalslideshow/rIcon2.jpg");
        ImageView neckwearImageView = creatImageView(neckwearImage);
        neckwearImageView.setFitHeight(300);
        neckwearImageView.setFitWidth(250);
        Button neckwearButton = createdButton(neckwearImageView);
       // Label mangalsutrLabel = new Label("Mangalsutra");
        
        Image chainsImage = new Image("mangalslideshow/rIcon2.jpg");
        ImageView chainsImageView = creatImageView(chainsImage);
        chainsImageView.setFitHeight(300);
        chainsImageView.setFitWidth(250);
        Button chainsButton = createdButton(chainsImageView);
       // Label mangalsutrLabel = new Label("Mangalsutra");
         
       Image bracletsImage = new Image("mangalslideshow/rIcon2.jpg");
        ImageView bracletsImageView = creatImageView(bracletsImage);
        bracletsImageView.setFitHeight(300);
        bracletsImageView.setFitWidth(250);
        Button bracletsButton = createdButton(bracletsImageView);
       // Label mangalsutrLabel = new Label("Mangalsutra");
        
       Image earringImage = new Image("mangalslideshow/rIcon2.jpg");
        ImageView earringImageView = creatImageView(earringImage);
        earringImageView.setFitHeight(300);
        earringImageView.setFitWidth(250);
        Button earringButton = createdButton(earringImageView);
       // Label mangalsutrLabel = new Label("Mangalsutra");
         
       Image banglesImage = new Image("mangalslideshow/rIcon2.jpg");
        ImageView banglesImageView = creatImageView(banglesImage);
        banglesImageView.setFitHeight(300);
        banglesImageView.setFitWidth(250);
        Button banglesButton = createdButton(banglesImageView);
       // Label mangalsutrLabel = new Label("Mangalsutra");
         
       Image coinsImage = new Image("mangalslideshow/rIcon2.jpg");
        ImageView coinsImageView = creatImageView(coinsImage);
        coinsImageView.setFitHeight(300);
        coinsImageView.setFitWidth(250);
        Button coinsButton = createdButton(coinsImageView);
       // Label mangalsutrLabel = new Label("Mangalsutra");


        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.add(createcategoryVBox(mangalsutrLabel, mangalsutraButton), 1, 0);
        gridPane.add(ringsButton, 2, 0);
        gridPane.add(neckwearButton, 3, 0);
        gridPane.add(chainsButton, 4, 0);
        gridPane.add(bracletsButton, 1, 1);
        gridPane.add(banglesButton, 2, 1);
        gridPane.add(earringButton, 3, 1);
        gridPane.add(coinsButton, 4, 1);

        gridPane.setVgap(70);
        gridPane.setHgap(80);

        return gridPane;


    }

    
    public static void main(String[] args) {
        launch(args);
    }
}