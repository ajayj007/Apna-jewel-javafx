package apnajewel.com;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FilterExample {
    private FilteredList<Map<String, Object>> filteredData; // Store the filtered data

    void fetchAndDisplayData(String tag, ComboBox<String> categoryComboBox, ComboBox<String> genderComboBox,
                             ListView<Map<String, Object>> listView, Button clearButton, VBox dataBox,String userId) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            try {
                ObservableList<Map<String, Object>> jewelryList;
                if ("all".equals(tag)) {
                    jewelryList = DataProvider.getJewelry();
                } else {
                    jewelryList = DataProvider.getJewelryWithTag(tag);
                }

                Platform.runLater(() -> {
                    filteredData = new FilteredList<>(jewelryList, p -> true); // Create a new filtered list with the selected data

                    categoryComboBox.valueProperty().addListener((observable, oldValue, newValue) -> updatePredicate(categoryComboBox, genderComboBox));
                    genderComboBox.valueProperty().addListener((observable, oldValue, newValue) -> updatePredicate(categoryComboBox, genderComboBox));

                    listView.setItems(filteredData);
                    printJewelryData(filteredData, dataBox,tag,userId);

                    filteredData.addListener((ListChangeListener<Map<String, Object>>) change -> {
                        while (change.next()) {
                            if (change.wasAdded() || change.wasRemoved()) {
                                printJewelryData(filteredData, dataBox,tag,userId);
                            }
                        }
                    });

                    clearButton.setOnAction(e -> {
                        categoryComboBox.setValue(null);
                        genderComboBox.setValue(null);
                        updatePredicate(categoryComboBox, genderComboBox); // Clear filters within the current dataset
                    });

                    System.out.println("Displayed data for tag: " + tag);
                });
            } catch (InterruptedException | ExecutionException | IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void updatePredicate(ComboBox<String> categoryComboBox, ComboBox<String> genderComboBox) {
        String selectedCategory = categoryComboBox.getValue();
        String selectedGender = genderComboBox.getValue();

        filteredData.setPredicate(jewelry -> {
            if (jewelry == null) return false;

            String category = (String) jewelry.get("Category");
            String gender = (String) jewelry.get("Gender");

            boolean matchesCategory = (selectedCategory == null || selectedCategory.isEmpty()) || (category != null && category.equals(selectedCategory));
            boolean matchesGender = (selectedGender == null || selectedGender.isEmpty()) || (gender != null && gender.equals(selectedGender));

            return matchesCategory && matchesGender;
        });
    }

   public VBox printJewelryData(FilteredList<Map<String, Object>> dataList, VBox dataBox,String tag,String userId) {
        System.out.println(dataBox);
        Platform.runLater(() -> {
            // Clear the VBox first
            dataBox.getChildren().clear();
            showProduct sp = showProduct.getInstance(userId);
            // Create a VBox for all data items
            VBox mainBox = new VBox();
            mainBox.setSpacing(40);
            mainBox.setAlignment(Pos.TOP_CENTER);
            

            HBox rowBox = new HBox();
            rowBox.setSpacing(100);
          //  rowBox.setAlignment(Pos.TOP_CENTER);
            int itemsInRow = 0;

            for (Map<String, Object> item : dataList) {
                String name = (String) item.get("Name");
                long weight = (long) item.get("Weight");
                String imageUrl = (String) item.get("Image");
                String prod_id = (String) item.get("Prod_id");

                double price = weight * 7000;

                // Load image asynchronously
                ImageView imageView = new ImageView();

                new Thread(() -> {
                    try {
                        Image image = new Image(imageUrl, 300, 300, true, true);

                        Platform.runLater(() -> imageView.setImage(image));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();

                // Labels for name and price
                Label nameLabel = new Label("Name: " + name);
                nameLabel.setAlignment(Pos.CENTER);
                nameLabel.setStyle(
                         // Light background color
                                "-fx-text-fill: #333333; " + // Dark text color
                                "-fx-font-size: 18px; " + // Font size
                                "-fx-font-weight: bold; " + // Bold text
                                "-fx-padding: 10px 20px; "  // Padding for spacing inside the label
                                
                );

                // Price Label
                Label priceLabel = new Label("Price: ₹" + price);
                priceLabel.setAlignment(Pos.CENTER);
                priceLabel.setStyle(
                         // Light grey background
                                "-fx-text-fill: #ff4500; " + // Orange text color for price
                                "-fx-font-size: 16px; " + // Font size
                                "-fx-font-weight: bold; " + // Bold text
                                "-fx-padding: 8px 18px; "  // Padding
                                
                );

                // Checkout Button
                Button checkout = new Button("CheckOut");
                checkout.setStyle(
                        "-fx-background-color: #32cd32; " + // Green background
                                "-fx-text-fill: white; " + // White text
                                "-fx-font-size: 16px; " + // Font size
                                "-fx-font-weight: bold; " + // Bold text
                                "-fx-padding: 10px 20px; " + // Padding
                                "-fx-border-radius: 10px; " + // Rounded button
                                "-fx-background-radius: 10px;" // Rounded background
                );

                // Button hover effect
                checkout.setOnMouseEntered(e -> {
                    checkout.setStyle(
                            "-fx-background-color: #228b22; " + // Darker green on hover
                                    "-fx-text-fill: white; " +
                                    "-fx-font-size: 16px; " +
                                    "-fx-font-weight: bold; " +
                                    "-fx-padding: 10px 20px; " +
                                    "-fx-border-radius: 10px;" +
                                    "-fx-background-radius: 10px;");
                });

                checkout.setOnMouseExited(e -> {
                    checkout.setStyle(
                            "-fx-background-color: #32cd32; " + // Original green background
                                    "-fx-text-fill: white; " +
                                    "-fx-font-size: 16px; " +
                                    "-fx-font-weight: bold; " +
                                    "-fx-padding: 10px 20px; " +
                                    "-fx-border-radius: 10px;" +
                                    "-fx-background-radius: 10px;");
                });
                checkout.setOnAction(e->{
                VBox productContent = sp.productBox(dataList,dataBox,name,weight,price,imageUrl,tag,2,prod_id,1);
                dataBox.getChildren().clear();
                dataBox.getChildren().add(productContent);
            });
                // VBox for text content
                VBox textBox = new VBox(nameLabel, priceLabel,checkout);
                textBox.setAlignment(Pos.CENTER);
                textBox.setSpacing(5);
                
                if(itemsInRow==0){
                    Region spacerStart = new Region();
                    spacerStart.setPrefWidth(10);
                    spacerStart.setStyle("-fx-border: 2; -fx-border-color: black;");
                    rowBox.getChildren().add(spacerStart);
                }
                // VBox to combine image and text in a single item box
                VBox itemBox = new VBox(imageView, textBox);
                itemBox.setAlignment(Pos.CENTER);
                itemBox.setSpacing(30);
                itemBox.setStyle("-fx-border-color: red; -fx-padding: 10; -fx-background-color: #f5f5f5; -fx-border-radius: 8;-fx-border: 5;");
                itemBox.setMinWidth(350);

                // Add the item box to the current row
                rowBox.getChildren().add(itemBox);
               // rowBox.setAlignment(Pos.TOP_CENTER);
                rowBox.setSpacing(95);
                itemsInRow++;

                // If four items are added to the row, start a new row
                if (itemsInRow == 4) {
                    Region spacerEnd = new Region();
                    spacerEnd.setPrefWidth(10);
                    spacerEnd.setStyle("-fx-border:2; -fx-border-color: black;");
                    rowBox.getChildren().add(spacerEnd);
                    mainBox.getChildren().add(rowBox);
                    rowBox = new HBox();
                    rowBox.setSpacing(95);
                   // rowBox.setAlignment(Pos.TOP_CENTER);
                    itemsInRow = 0;
                }
            }

            // Add the last row if it's not full
            if (itemsInRow > 0) {
                
            
                mainBox.getChildren().add(rowBox);
            }

            // Add a footer for Apna Jewel brand
            

            
           // mainBox.getChildren().add(jewelary1.createFooter());

            // Add mainBox to the dataBox and center it
            Region spacerMain = new Region();
            spacerMain.setPrefHeight(30);

            Region spacRegion2 = new Region();
            spacRegion2.setPrefHeight(40);

            dataBox.getChildren().addAll(spacerMain,mainBox,spacRegion2,jewelary.createFooter());
            dataBox.setAlignment(Pos.TOP_CENTER);
            dataBox.setPrefWidth(1900);
            dataBox.setStyle("-fx-border: 2; -fx-border-color: black;");
            
        });
        return dataBox;
    }
}
