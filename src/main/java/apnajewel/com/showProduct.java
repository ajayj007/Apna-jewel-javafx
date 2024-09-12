package apnajewel.com;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class showProduct {
    
    @SuppressWarnings("unused")
    private FilteredList<Map<String, Object>> dataList;
    private final int NUM_STARS = 5;
    private int rating = 0;
    private int quantity = 1;
    private double price;
    @SuppressWarnings("unused")
    private String name;
    private double weight;
    private String imgUrl;
    private String prod_id;
    private int type ;
    @SuppressWarnings("unused")
    private double newWeight;
    private VBox productVBox;
    private String tag;
    private Label quantityValueLabel;
    private Label priceValueLabel;
    private Label goldWeightValue;
    private Label quantityValueLabel2;
    private Label grandTotalValue;
    private  Product selectedProduct;
    private static showProduct instance;
    private String userId;
    
    public showProduct(String userId) {
        this.userId = userId;
    }
    public static showProduct getInstance(String userId) {
        if (instance == null) {
            instance = new showProduct(userId);
        }
        return instance;
        
    }
    public VBox productBox(FilteredList<Map<String, Object>> dataList, VBox productVBox, String name, double weight, double price, String imgUrl,String tag,int type,String prod_id,int quantity) {
        this.dataList = dataList;
        this.name = name;
        this.productVBox = productVBox;
        this.weight = weight;
        this.price = price;
        this.imgUrl =imgUrl;
        this.tag = tag; 
        this.newWeight = weight;
        this.type = type;
        this.prod_id = prod_id;
        this.quantity = quantity;

         System.out.println("bye");
        VBox vBox = new VBox();
        Region upperSpacer = new Region();
        upperSpacer.setPrefHeight(30);

        // Left side
        VBox leftVBox = createLeftVBox(imgUrl);

        // Right side
        VBox rightVBox = createRightVBox(name);

        // Combine left and right VBox in the main HBox
        HBox leftAndRightHBox = createLeftAndRightHBox(leftVBox, rightVBox);
        leftAndRightHBox.setSpacing(50);

        // Create back button
        Button backButton = createBackButton(dataList);
        backButton.setAlignment(Pos.CENTER);

        // Create the grid for price breakup
        VBox labelGridpaneVBox = createlabelandgridpane();
        labelGridpaneVBox.setAlignment(Pos.CENTER);
        
        Region lowerRegion = new Region();
        lowerRegion.setPrefHeight(40);
        // Add everything to the main VBox
       // jewelary jewelary2 = new jewelary();
        vBox.getChildren().addAll(upperSpacer, leftAndRightHBox, labelGridpaneVBox, backButton,lowerRegion,jewelary.createFooter());
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(30);
        return vBox;
    }

    private VBox createLeftVBox(String imgUrl) {
        VBox leftVBox = new VBox();
        Image image = new Image(imgUrl, 570, 570, true, true);
        ImageView imageView = new ImageView(image);
        HBox imageViewHBox = new HBox(imageView);
        imageViewHBox.setAlignment(Pos.CENTER);
        imageViewHBox.setPadding(new Insets(30, 30, 30, 30));

        leftVBox.getChildren().add(imageViewHBox);
        leftVBox.setMinWidth(600);
        leftVBox.setMinHeight(600);
        leftVBox.setStyle("-fx-border:2; -fx-border-color: pink;-fx-background-radius: 30; -fx-border-radius: 30;-fx-padding: 15;");

        return leftVBox;
    }

    private VBox createRightVBox(String name) {
        VBox rightVBox = new VBox();
        Region rightSpacer = new Region();
        rightSpacer.setPrefHeight(10);

        // Create name label and star rating
        HBox nameAndStarHBox = createNameAndStarHBox(name);

        // Create weight and quantity section
        HBox weightAndQuantityHBox = createWeightAndQuantityHBox();

        // Create price section
        HBox priceHBox = createPriceHBox();

        // Create buttons section
        Date currentDate = new Date();
        selectedProduct = new Product(name, price, quantity, imgUrl,tag,weight,prod_id,currentDate);
        System.out.println(this.quantity);
        HBox cartAndBuyHBox = createCartAndBuyButtonsHBox(selectedProduct);

        rightVBox.getChildren().addAll(rightSpacer, nameAndStarHBox, new Line(), weightAndQuantityHBox, priceHBox, cartAndBuyHBox);
        rightVBox.setSpacing(35);
        rightVBox.setStyle("-fx-border:2; -fx-border-color: pink;-fx-background-radius: 30; -fx-border-radius: 30;-fx-padding: 30;");
        rightVBox.setMinWidth(600);
        return rightVBox;
    }

    private HBox createNameAndStarHBox(String name) {
        Label nameLabel = new Label(name);
        nameLabel.setStyle("-fx-letter-spacing: 4;");
        nameLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 64));
        nameLabel.setTextFill(Color.RED);

        HBox starsHBox = createStarsHBox();

        VBox labelAndStarsVBox = new VBox(nameLabel, starsHBox);
        Region nameAndStarRegion = new Region();
        nameAndStarRegion.setPrefWidth(60);

        return new HBox(nameAndStarRegion, labelAndStarsVBox);
    }

    private HBox createStarsHBox() {
        HBox starsHBox = new HBox();

        Image emptyStar = new Image("Star.png");
        Image filledStar = new Image("filled_star.png");
        ImageView[] stars = new ImageView[NUM_STARS];

        for (int i = 0; i < NUM_STARS; i++) {
            final int starIndex = i;
            stars[i] = new ImageView(emptyStar);

            // Handle Click Event
            stars[i].setOnMouseClicked((MouseEvent event) -> {
                rating = starIndex + 1;
                updateStars(stars, filledStar, emptyStar);
            });

            // Handle Hover Event
            stars[i].setOnMouseEntered((MouseEvent event) -> {
                updateStarsOnHover(stars, starIndex, filledStar, emptyStar);
            });

            stars[i].setOnMouseExited((MouseEvent event) -> {
                updateStars(stars, filledStar, emptyStar);
            });

            starsHBox.getChildren().add(stars[i]);
        }

        return starsHBox;
    }

    private HBox createWeightAndQuantityHBox() {
        VBox weightVBox = createWeightVBox();
        VBox quantityVBox = createQuantityVBox();

        HBox weightAndQuantityHBox = new HBox(weightVBox, quantityVBox);
        weightAndQuantityHBox.setSpacing(60);
        weightAndQuantityHBox.setAlignment(Pos.CENTER);

        return weightAndQuantityHBox;
    }

    private VBox createWeightVBox() {
        Label weightLabel = new Label("Weight");
        weightLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 36));
        weightLabel.setTextFill(Color.DARKSLATEBLUE);

        Label weightValueLabel = new Label(weight + " gm");
        weightValueLabel.setAlignment(Pos.CENTER);
        weightValueLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 36));
        weightValueLabel.setTextFill(Color.WHITESMOKE);
        weightValueLabel.setStyle(
                "-fx-background-color: #FF6F61;" +
                "-fx-background-radius: 30;" + // Circular edges
                "-fx-border-radius: 30;" +
                "-fx-padding: 15;" +
                "-fx-border-color: #FF6F61;" +
                "-fx-border-width: 2;");

        VBox weightVBox = new VBox(weightLabel, weightValueLabel);
        weightVBox.setSpacing(15);
        weightVBox.setPadding(new Insets(20, 20, 20, 20));
        weightVBox.setAlignment(Pos.CENTER);
        weightVBox.setStyle("-fx-background-color: #f5f5f5; -fx-background-radius: 15;");
        DropShadow vboxShadow = new DropShadow();
        vboxShadow.setColor(Color.GRAY);
        vboxShadow.setRadius(20);
        weightVBox.setEffect(vboxShadow);

        return weightVBox;
    }

    private VBox createQuantityVBox() {
        Label quantityLabel = new Label("Quantity");
        quantityLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 36));
        quantityLabel.setTextFill(Color.DARKBLUE);

        quantityValueLabel = new Label("" + this.quantity);
        quantityValueLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 36));
        quantityValueLabel.setTextFill(Color.DARKGREEN);
        quantityValueLabel.setAlignment(Pos.CENTER);
        quantityValueLabel.setStyle(
                "-fx-background-color: lightgray; -fx-border-color: green; -fx-border-radius: 15; -fx-background-radius: 15; -fx-padding: 10;");

        Button plusButton = createPlusButton();
        Button minusButton = createMinusButton();

        HBox quantityHBox = new HBox(minusButton, quantityValueLabel, plusButton);
        quantityHBox.setSpacing(10);

        VBox quantityVBox = new VBox(quantityLabel, quantityHBox);
        quantityVBox.setSpacing(25);
        quantityVBox.setPadding(new Insets(20, 20, 20, 20));
        quantityVBox.setStyle("-fx-background-color: #f5f5f5; -fx-background-radius: 15;");
        DropShadow vboxShadow2 = new DropShadow();
        vboxShadow2.setColor(Color.GRAY);
        vboxShadow2.setRadius(20);
        quantityVBox.setEffect(vboxShadow2);

        return quantityVBox;
    }

    private Button createPlusButton() {
        Button plusButton = new Button("+");
        plusButton.setFont(Font.font("Verdana", FontWeight.BOLD, 24));
        plusButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-border-radius: 10; -fx-background-radius: 10; -fx-padding: 10;");
        plusButton.setOnAction(event -> {
            this.quantity++;
            double weight2 = weight * quantity;

            this.price = (weight2*7000);
            System.out.println(price +""+ weight);
            selectedProduct.setQuantity(this.quantity);
            selectedProduct.setPrice(this.price);
            selectedProduct.setWeight(weight2);
            quantityValueLabel.setText("" + quantity);
            updatePrice();
        });
        return plusButton;
    }

    private Button createMinusButton() {
        Button minusButton = new Button("-");
        minusButton.setFont(Font.font("Verdana", FontWeight.BOLD, 24));
        minusButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-border-radius: 10; -fx-background-radius: 10; -fx-padding: 10;");
        minusButton.setOnAction(event -> {
            if (this.quantity > 1) {
                this.quantity--;
                double weight2 = weight * quantity;
   
            this.price = (weight2*7000);
            System.out.println(price);
            selectedProduct.setQuantity(this.quantity);
            selectedProduct.setPrice(this.price);
            selectedProduct.setWeight(weight2);
            quantityValueLabel.setText("" + quantity);
            updatePrice();
            }
        });
        return minusButton;
    }

    private HBox createPriceHBox() {
        Label priceLabel = new Label("Price");
        priceLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 36));
        priceLabel.setTextFill(Color.DARKSLATEGRAY);

        priceValueLabel = new Label(String.format("%.2f", price) + " INR");
        priceValueLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 36));
        priceValueLabel.setTextFill(Color.DARKGRAY);

        HBox priceHBox = new HBox(priceLabel, priceValueLabel);
        priceHBox.setSpacing(50);
        priceHBox.setAlignment(Pos.CENTER);

        return priceHBox;
    }

    private HBox createCartAndBuyButtonsHBox(Product selectedProduct) {
        
      //  System.out.println("hi Ajay "+ selectedProduct);
       // System.out.println(quantity);
        Button addCartButton = createAddCartButton(selectedProduct);
        Button buyButton = createBuyButton(selectedProduct);

        HBox cartAndBuyHBox = new HBox(addCartButton, buyButton);
        cartAndBuyHBox.setSpacing(30);
        cartAndBuyHBox.setAlignment(Pos.CENTER);

        return cartAndBuyHBox;
    }

    private Button createAddCartButton(Product selectedProduct) {
        System.out.println(selectedProduct);
        selectedProduct.getQuantity();
        Button addCartButton = new Button("Add to Cart");
        addCartButton.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        addCartButton.setStyle(
                "-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-border-radius: 10; -fx-background-radius: 10; -fx-padding: 15;");
        addCartButton.setOnAction(event -> {
            CartService cartService;
            try {
                cartService = new CartService(userId);
                cartService.addToCart(selectedProduct);
            } catch (IOException e) {
                
                e.printStackTrace();
            }
            
            System.out.println("Product added to cart");
        });
        return addCartButton;
    }

    private Button createBuyButton(Product selectedProduct) {
        Button buyButton = new Button("Buy Now");
        
        buyButton.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        buyButton.setStyle(
                "-fx-background-color: #FF5722; -fx-text-fill: white; -fx-border-radius: 10; -fx-background-radius: 10; -fx-padding: 15;");
        buyButton.setOnAction(event -> {
            CartService cartService;
            try {
                
                cartService = new CartService(userId);
                selectedProduct.setQuantity(this.quantity);
                System.out.println(selectedProduct);
                cartService.addtoBuy(selectedProduct);
            } catch (IOException e) {
                
                e.printStackTrace();
            }
            System.out.println("Buy Now button clicked");
        });
        return buyButton;
    }
    
    private HBox createLeftAndRightHBox(VBox leftVBox, VBox rightVBox) {
        HBox leftAndRightHBox = new HBox(leftVBox, rightVBox);
        leftAndRightHBox.setAlignment(Pos.CENTER);
        leftAndRightHBox.setSpacing(30);
        return leftAndRightHBox;
    }

    private Button createBackButton(FilteredList<Map<String, Object>> dataList) {
        Button backButton = new Button("Back");
       // backButton.setAlignment(Pos.CENTER);
    

        backButton.setStyle("-fx-background-color: #FFC107;-fx-font-size:20;-fx-font-weight: bold; -fx-text-fill: black; -fx-border-radius: 10; -fx-background-radius: 10; -fx-padding: 15;");
        backButton.setOnAction(event -> {
            if(type == 1){
                this.dataList = null;
                System.out.println(dataList);
            }

            if((dataList.isEmpty()) ){
                
             jewelary jewelaryInstance = jewelary.getInstance();
            System.out.println("Calling createCategoryContent with tag: " + tag);
            jewelaryInstance.centerMethod(tag);
            }
            FilterExample Filter = new FilterExample();
            productVBox.getChildren().clear();
            System.out.println(dataList);
            Filter.printJewelryData(dataList,productVBox,tag,userId);
            System.out.println("Back button clicked");
        });
        return backButton;
    }

    private VBox createlabelandgridpane() {
        // Create the grid pane for price breakup
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label goldWeightLabel = new Label("Gold Weight:");
        goldWeightValue = new Label(weight + " gm");
        Label quantityLabel = new Label("Quqntity:");
        quantityValueLabel2 = new Label(this.quantity + " N");
        Label sgstLabel = new Label("SGST(%)");
        Label sgstValue = new Label("0.0");
        Label cgstLabel = new Label("CGST(%)");
        Label cgstValue = new Label("0.0");
        Label totaltaxLabel = new Label("Total Tax");// total tax = sgst + cgst
        Label totaltaxvaluLabel = new Label("0.0");
        Label totalmakingLabel = new Label("Total making charges");
        Label totalmakingPriceValue = new Label(String.format("0"));
        Label grandTotalLabel = new Label("Grand Total:");
        grandTotalValue = new Label(String.format("%.2f INR", price * quantity));

        gridPane.add(goldWeightLabel, 0, 0);
        gridPane.add(goldWeightValue, 1, 0);
        gridPane.add(quantityLabel, 0, 1);
        gridPane.add(quantityValueLabel2, 1, 1);
        gridPane.add(sgstLabel, 0, 2);
        gridPane.add(sgstValue, 1, 2);
        gridPane.add(cgstLabel, 0, 3);
        gridPane.add(cgstValue, 1, 3);
        gridPane.add(totaltaxLabel, 0, 4);
        gridPane.add(totaltaxvaluLabel, 1, 4);
        gridPane.add(totalmakingLabel, 0, 5);
        gridPane.add(totalmakingPriceValue, 1, 5);
        gridPane.add(grandTotalLabel, 0, 6);
        gridPane.add(grandTotalValue, 1, 6);

        VBox vbox = new VBox();
        Label titleLabel = new Label("Price Breakup");
        titleLabel.setAlignment(Pos.CENTER);
        titleLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
        titleLabel.setTextFill(Color.DARKORANGE);

        vbox.getChildren().addAll(titleLabel, gridPane);
        vbox.setSpacing(20);
        vbox.setPadding(new Insets(20, 20, 20, 20));
        vbox.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #e0e0e0; -fx-border-width: 1; -fx-border-radius: 10;");
        return vbox;
    }

    private void updateStars(ImageView[] stars, Image filledStar, Image emptyStar) {
        for (int i = 0; i < NUM_STARS; i++) {
            if (i < rating) {
                stars[i].setImage(filledStar);
            } else {
                stars[i].setImage(emptyStar);
            }
        }
    }

    private void updateStarsOnHover(ImageView[] stars, int hoverIndex, Image filledStar, Image emptyStar) {
        for (int i = 0; i < NUM_STARS; i++) {
            if (i <= hoverIndex) {
                stars[i].setImage(filledStar);
            } else {
                stars[i].setImage(emptyStar);
            }
        }
    }

    private void updatePrice() {
        quantityValueLabel.setText( ""+this.quantity);
        goldWeightValue.setText( ""+this.quantity * weight);
        quantityValueLabel2.setText( ""+this.quantity);
        priceValueLabel.setText(String.format("%.2f", price ) + " INR");
        
        grandTotalValue.setText(String.format("%.2f INR", price ));
    }
}
