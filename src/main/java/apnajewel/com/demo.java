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

public class demo extends Application {
    final int totalImage = 5;
    final Circle[] dots = new Circle[totalImage];
   
    private int currentIndex = 0;
    
    private ImageView imageView2;
    private ImageView home_ImageView;
    private ImageView custmisImageView;
    private ImageView wishlistImageView;
    private ImageView cartImageView;
    private ImageView accountImageView;


    
    
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
        home_ImageView = creatImageView(home_iconImage);
        Image custmisImage = new Image("custmise_icon.png");
        custmisImageView = creatImageView(custmisImage);
        Image wishlistImage = new Image("wishlist_icon.png");
        wishlistImageView = creatImageView(wishlistImage);
        Image cartImage = new Image("cart_icon.png");
        cartImageView = creatImageView(cartImage);
        Image accountImage = new Image("account_icon.png");
        accountImageView = new ImageView(accountImage);
       
    
        // Create buttons and their actions
        Button homeButton = createdButton(home_ImageView,2);
       // homeButton.setGraphic(home_ImageView);
        Button custmizeButton = createdButton(custmisImageView,2);
        Button wishListButton = createdButton(wishlistImageView,2);
        Button cartButton = createdButton(cartImageView,2);
        Button accountButton = createdButton(accountImageView,2);

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
        Image[] image = new Image[totalImage];
        for (int i = 0; i < totalImage; i++) {
            image[i] = new Image("logo" + i + ".png");
            
            if (image[i].isError()) {
                System.out.println("error is found :" + "logo" + i + ".png");
            }
        }

        imageView2 = new ImageView(image[0]); // Initialize imageView here
        imageView2.setFitHeight(600); // Fixed typo: Changed from prefHeight to setFitHeight
        imageView2.setFitWidth(1600);

        HBox dotHBox = new HBox();
        dotHBox.setAlignment(Pos.CENTER);
        dotHBox.setPrefHeight(50);
        dotHBox.setSpacing(30);

        for (int i = 0; i < totalImage; i++) {
            dots[i] = new Circle(5, i == currentIndex ? Color.CHOCOLATE : Color.AQUA);
            dotHBox.getChildren().addAll(dots[i]);
        }

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> nextImage(image)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        
        Label categoryLabel = new Label("Select By Categories");
        //categoryLabel.setAlignment(Pos.CENTER);
        HBox CategoryName = new HBox(categoryLabel);
        CategoryName.setAlignment(Pos.CENTER);

        Image mangalsutra = new Image("rIcon4.jpg");
        ImageView mangalsutrImageView = createImageviewcategory(mangalsutra);
        mangalsutrImageView.setFitHeight(250);
        mangalsutrImageView.setFitWidth(250);
// mangalsutrImageView.setPreserveRatio(true);
        
        Button mangalsutraButton = createdButton(mangalsutrImageView , 1);
       // mangalsutraButton.setPrefSize(100, 100);
        

        Image chains = new Image("rIcon4.jpg");
        
        ImageView chainsImageView = creatImageView(chains);
        chainsImageView.setFitHeight(250);
        chainsImageView.setFitWidth(250);
        
        Button chainsButton = createdButton(chainsImageView, 1);
        

        Image earringImage = new Image("rIcon4.jpg");
        ImageView earringImageView = creatImageView(earringImage);
        earringImageView.setFitHeight(250);
        earringImageView.setFitWidth(250);
        Button earringButton = createdButton(earringImageView , 1);

        Image bangles = new Image("rIcon4.jpg");
        ImageView banglesImageView = creatImageView(bangles);
        banglesImageView.setFitHeight(250);
        banglesImageView.setFitWidth(250);
        Button banglesButton = createdButton(banglesImageView,1);
        Image bracletsImage = new Image("rIcon4.jpg");
        ImageView braclestsImageView = creatImageView(bracletsImage);
        braclestsImageView.setFitHeight(250);
        braclestsImageView.setFitWidth(250);
        Button braclestsButton = createdButton(braclestsImageView,1);
        Image rings = new Image("rIcon4.jpg");
        ImageView ringsImageView = creatImageView(rings);
        ringsImageView.setFitHeight(250);
        ringsImageView.setFitWidth(250);
        Button ringsButton = createdButton(ringsImageView,1);
        Image neckwearImage = new Image("rIcon4.jpg");
        ImageView neckwearImageView = creatImageView(neckwearImage);
        neckwearImageView.setFitHeight(250);
        neckwearImageView.setFitWidth(250);
        Button neckwearButton = createdButton(neckwearImageView,1);
        Image goldcoinImage = new Image("rIcon4.jpg");
        ImageView goldcoinImageView = creatImageView(goldcoinImage);
        goldcoinImageView.setFitHeight(250);
        goldcoinImageView.setFitWidth(250);
        Button goldcoiButton = createdButton(goldcoinImageView,1);
        
        
        GridPane categoryGridPane = new GridPane();
        categoryGridPane.setAlignment(Pos.CENTER);
        categoryGridPane.setVgap(50);
        categoryGridPane.setHgap(50);

        
        categoryGridPane.add(chainsButton, 0, 0);
        categoryGridPane.add(earringButton, 1, 0);
        categoryGridPane.add(banglesButton,2,0);
        categoryGridPane.add(braclestsButton,3,0);
        categoryGridPane.add(ringsButton,0,1);
        categoryGridPane.add(mangalsutraButton,1,1);
        categoryGridPane.add(neckwearButton,2,1);
        categoryGridPane.add(goldcoiButton,3,1);
        
        Label selectbygender = new Label("Select By Gender");
        HBox selectbygenderhBox = new HBox(selectbygender);
        selectbygenderhBox.setAlignment(Pos.CENTER);

        Region spacelast = new Region();
        spacelast.setPrefHeight(100);




        
        VBox homeContent = new VBox(imageView2,dotHBox,CategoryName,categoryGridPane,selectbygenderhBox,spacelast);
        homeContent.setAlignment(Pos.TOP_CENTER);
        homeContent.setSpacing(20);
        homeContent.setStyle("-fx-border-color: orange; -fx-border-width: 1;"); // Border for the home content
        
        return homeContent;
    }

    private void nextImage(Image [] images){
         currentIndex =(currentIndex+1)% totalImage;
         imageView2.setImage(images[currentIndex]);
         updates();
    }

    private void updates(){
       for(int i = 0; i<totalImage; i++){
        dots[i].setFill(i==currentIndex ? Color.CHOCOLATE: Color.AQUA);
       }
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

    private Button createdButton(ImageView icon,int i){
          
        Button button=new Button();
        button.setGraphic(icon);
        if(i==2){                                 
        button.setStyle("-fx-background-color: transparent; -fx-background-border: transparent; -fx-padding:0;");
        } else {
            button.setStyle("-fx-background-border: transparent; -fx-background-color: transparent; -fx-background-radius:15; -fx-border-radius:15;");
        }
       // button.setPrefWidth(100);
       ScaleTransition stEnlarge = new ScaleTransition(Duration.millis(200), button);
        stEnlarge.setToX(1.2);
        stEnlarge.setToY(1.2);

        ScaleTransition stShrink = new ScaleTransition(Duration.millis(200), button);
        stShrink.setToX(1);
        stShrink.setToY(1);

        button.setOnMouseEntered(e -> stEnlarge.playFromStart());
        button.setOnMouseExited(e -> stShrink.playFromStart());
        return button;

    }

    private ImageView creatImageView(Image image){
        ImageView imageView1 = new ImageView(image);
        imageView1.prefHeight(20);
        imageView1.prefWidth(20);
        return imageView1;
    }

    private ImageView createImageviewcategory(Image image){
        ImageView imageView = new ImageView(image);
        imageView.prefHeight(50);
        imageView.prefWidth(50);
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

    
    public static void main(String[] args) {
        launch(args);
    }
}
