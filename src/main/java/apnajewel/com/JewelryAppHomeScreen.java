package apnajewel.com;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class JewelryAppHomeScreen {
    @SuppressWarnings("unused")
    private Stage primerStage; 
    
    public void homeScreen(Stage primaryStage) {
        this.primerStage = primaryStage;
        HBox header = createHeader();
        
        // Promotional Banners
        HBox promotionalBanners = createPromotionalBanners();
        
        // Categories Section
        HBox categoriesSection = createCategoriesSection();
        
        // Featured Products
        VBox featuredProducts = createFeaturedProducts();
        
        // Special Offers
        VBox specialOffers = createSpecialOffers();
        
        // New Arrivals
        VBox newArrivals = createNewArrivals();
        
        // Best Sellers
        VBox bestSellers = createBestSellers();
        
        // Personalized Recommendations
        VBox personalizedRecommendations = createPersonalizedRecommendations();
        
        // Footer
        HBox footer = createFooter();
        
        // Main Layout
        VBox mainLayout = new VBox(10, header, promotionalBanners, categoriesSection, 
                                   featuredProducts, specialOffers, newArrivals, 
                                   bestSellers, personalizedRecommendations, footer);
        mainLayout.setPadding(new Insets(10));
        
        Scene scene = new Scene(mainLayout, 2000, 1000);
        primaryStage.setTitle("Jewelry App Home Screen");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private HBox createHeader() {
        HBox header = new HBox(10);
        header.setAlignment(Pos.CENTER_LEFT);
        
        ImageView logo = createImageView("file:apna_jewel/src/main/resources/Backimg1.png", 50, 50);
        TextField searchBar = new TextField();
        searchBar.setPromptText("Search for jewelry...");
        searchBar.setPrefWidth(300);
        
        Button categoriesButton = new Button("Categories");
        Button wishlistButton = new Button("Wishlist");
        Button cartButton = new Button("Cart");
        Button profileButton = new Button("Profile");
        Button notificationsButton = new Button("Notifications");
        
        header.getChildren().addAll(logo, searchBar, categoriesButton, wishlistButton, cartButton, profileButton, notificationsButton);
        return header;
    }
    
    private HBox createPromotionalBanners() {
        HBox promotionalBanners = new HBox(10);
        promotionalBanners.setAlignment(Pos.CENTER);
        
        ImageView banner1 = createImageView("file:apna_jewel/src/main/resources/Backimg1.png", 200, 100);
        ImageView banner2 = createImageView("file:apna_jewel/src/main/resources/Backimg1.png", 200, 100);
        ImageView banner3 = createImageView("file:apna_jewel/src/main/resources/Backimg1.png", 200, 100);
        
        promotionalBanners.getChildren().addAll(banner1, banner2, banner3);
        return promotionalBanners;
    }
    
    private HBox createCategoriesSection() {
        HBox categoriesSection = new HBox(10);
        categoriesSection.setAlignment(Pos.CENTER);
        
        Button ringsButton = createButtonWithImage("Rings", "file:apna_jewel/src/main/resources/Backimg1.png", 50, 50);
        Button necklacesButton = createButtonWithImage("Necklaces", "file:apna_jewel/src/main/resources/Backimg1.png", 50, 50);
        Button braceletsButton = createButtonWithImage("Bracelets", "file:apna_jewel/src/main/resources/Backimg1.png", 50, 50);
        Button earringsButton = createButtonWithImage("Earrings", "file:apna_jewel/src/main/resources/Backimg1.png", 50, 50);
        
        categoriesSection.getChildren().addAll(ringsButton, necklacesButton, braceletsButton, earringsButton);
        return categoriesSection;
    }
    
    private VBox createFeaturedProducts() {
        VBox featuredProducts = new VBox(10);
        featuredProducts.setAlignment(Pos.CENTER_LEFT);
        
        Label title = new Label("Featured Products");
        HBox products = new HBox(10);
        
        ImageView product1 = createImageView("file:apna_jewel/src/main/resources/Backimg1.png", 100, 100);
        ImageView product2 = createImageView("file:apna_jewel/src/main/resources/Backimg1.png", 100, 100);
        ImageView product3 = createImageView("file:apna_jewel/src/main/resources/Backimg1.png", 100, 100);
        
        products.getChildren().addAll(product1, product2, product3);
        featuredProducts.getChildren().addAll(title, products);
        return featuredProducts;
    }
    
    private VBox createSpecialOffers() {
        VBox specialOffers = new VBox(10);
        specialOffers.setAlignment(Pos.CENTER_LEFT);
        
        Label title = new Label("Special Offers");
        HBox offers = new HBox(10);
        
        ImageView offer1 = createImageView("file:apna_jewel/src/main/resources/Backimg1.png", 100, 100);
        ImageView offer2 = createImageView("file:apna_jewel/src/main/resources/Backimg1.png", 100, 100);
        ImageView offer3 = createImageView("file:apna_jewel/src/main/resources/Backimg1.png", 100, 100);
        
        offers.getChildren().addAll(offer1, offer2, offer3);
        specialOffers.getChildren().addAll(title, offers);
        return specialOffers;
    }
    
    private VBox createNewArrivals() {
        VBox newArrivals = new VBox(10);
        newArrivals.setAlignment(Pos.CENTER_LEFT);
        
        Label title = new Label("New Arrivals");
        HBox arrivals = new HBox(10);
        
        ImageView arrival1 = createImageView("file:apna_jewel/src/main/resources/Backimg1.png", 100, 100);
        ImageView arrival2 = createImageView("file:apna_jewel/src/main/resources/Backimg1.png", 100, 100);
        ImageView arrival3 = createImageView("file:apna_jewel/src/main/resources/Backimg1.png", 100, 100);
        
        arrivals.getChildren().addAll(arrival1, arrival2, arrival3);
        newArrivals.getChildren().addAll(title, arrivals);
        return newArrivals;
    }
    
    private VBox createBestSellers() {
        VBox bestSellers = new VBox(10);
        bestSellers.setAlignment(Pos.CENTER_LEFT);
        
        Label title = new Label("Best Sellers");
        HBox sellers = new HBox(10);
        
        ImageView seller1 = createImageView("file:apna_jewel/src/main/resources/Backimg1.png", 100, 100);
        ImageView seller2 = createImageView("file:apna_jewel/src/main/resources/Backimg1.png", 100, 100);
        ImageView seller3 = createImageView("file:apna_jewel/src/main/resources/Backimg1.png", 100, 100);
        
        sellers.getChildren().addAll(seller1, seller2, seller3);
        bestSellers.getChildren().addAll(title, sellers);
        return bestSellers;
    }
    
    private VBox createPersonalizedRecommendations() {
        VBox personalizedRecommendations = new VBox(10);
        personalizedRecommendations.setAlignment(Pos.CENTER_LEFT);
        
        Label title = new Label("Recommended for You");
        HBox recommendations = new HBox(10);
        
        ImageView rec1 = createImageView("file:apna_jewel/src/main/resources/Backimg1.png", 100, 100);
        ImageView rec2 = createImageView("file:apna_jewel/src/main/resources/Backimg1.png", 100, 100);
        ImageView rec3 = createImageView("file:apna_jewel/src/main/resources/Backimg1.png", 100, 100);
        
        recommendations.getChildren().addAll(rec1, rec2, rec3);
        personalizedRecommendations.getChildren().addAll(title, recommendations);
        return personalizedRecommendations;
    }
    
    private HBox createFooter() {
        HBox footer = new HBox(10);
        footer.setAlignment(Pos.CENTER);
        
        Button aboutUsButton = new Button("About Us");
        Button contactUsButton = new Button("Contact Us");
        Button termsButton = new Button("Terms of Service");
        Button privacyButton = new Button("Privacy Policy");
        
        footer.getChildren().addAll(aboutUsButton, contactUsButton, termsButton, privacyButton);
        return footer;
    }
    
    private ImageView createImageView(String path, int width, int height) {
        ImageView imageView = new ImageView(new Image(path));
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        imageView.setPreserveRatio(true);
        return imageView;
    }
    
    private Button createButtonWithImage(String text, String imagePath, int width, int height) {
        Button button = new Button(text);
        ImageView imageView = createImageView(imagePath, width, height);
        button.setGraphic(imageView);
        return button;
        }
}