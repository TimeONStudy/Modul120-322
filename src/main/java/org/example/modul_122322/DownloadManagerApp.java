package org.example.modul_122322;

import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;  // Import for Color class
import javafx.scene.shape.Rectangle;  // Import for Rectangle class
import javafx.scene.layout.StackPane;  // Import for StackPane class
import javafx.geometry.Insets;  // Import for Insets
import javafx.scene.control.Label;  // Import for Label class
import javafx.scene.layout.VBox;  // Import for VBox
import javafx.scene.layout.HBox;  // Import for HBox
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;  // Import for ImageView if needed
import javafx.scene.image.Image;  // Import for Image class if needed
public class DownloadManagerApp extends Application {

    private Stage primaryStage;
    private List<String> savedFiles = new ArrayList<>();
    private SimpleBooleanProperty isSidebarVisible = new SimpleBooleanProperty(true);

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Download Manager");
        showMainView();
    }

    private void showMainView() {
        BorderPane root = new BorderPane();

        VBox sidebar = new VBox(10);
        sidebar.getStyleClass().add("sidebar");

        Button downloadsButton = createStyledButton("Downloads", null);
        Button savedButton = createStyledButton("Saved", null);
        savedButton.setOnAction(e -> showSavedView());
        Button contactButton = createStyledButton("Contact", null);
        contactButton.setOnAction(e -> showContactPopup());
        sidebar.getChildren().addAll(
                downloadsButton,
                savedButton,
                contactButton,
                createStyledButton("Update", null)
        );

        // Toggle Sidebar Button
        Button toggleSidebarButton = createStyledButton(null, "/icons/bars-solid.svg");
        toggleSidebarButton.getStyleClass().add("toggle-button");
        toggleSidebarButton.setOnAction(e -> isSidebarVisible.set(!isSidebarVisible.get()));
        toggleSidebarButton.setPrefWidth(50);
        toggleSidebarButton.setPrefHeight(50);

        VBox leftContainer = new VBox(10, toggleSidebarButton, sidebar);
        leftContainer.setAlignment(Pos.TOP_LEFT);

        // Search Bar
        TextField searchBar = new TextField();
        searchBar.setPromptText("Search files...");
        searchBar.getStyleClass().add("search-bar");
        searchBar.setPrefWidth(300);

        HBox topBar = new HBox(20, searchBar);
        topBar.getStyleClass().add("top-bar");
        topBar.setStyle("-fx-alignment: center;");

        // Scrollable Content Area
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.getStyleClass().add("scroll-pane");

        FlowPane contentArea = new FlowPane();
        contentArea.getStyleClass().add("content-area");
        for (int i = 1; i <= 18; i++) {
            contentArea.getChildren().add(createFileCard("File " + i));
        }

        scrollPane.setContent(contentArea);
        root.setTop(topBar);
        root.setLeft(leftContainer);
        root.setCenter(scrollPane);

        // Bind Sidebar Visibility
        sidebar.managedProperty().bind(isSidebarVisible);
        sidebar.visibleProperty().bind(isSidebarVisible);

        Scene scene = new Scene(root, 1200, 800);

        // Load Stylesheet
        java.net.URL stylesheet = getClass().getResource("/styles.css");
        if (stylesheet != null) {
            scene.getStylesheets().add(stylesheet.toExternalForm());
        } else {
            System.err.println("styles.css not found!");
        }

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showContactPopup() {
        Alert contactAlert = new Alert(Alert.AlertType.INFORMATION);
        contactAlert.setTitle("Contact Information");
        contactAlert.setHeaderText("Get in Touch");
        contactAlert.setContentText("Email: example@email.com\nPhone: +123 456 7890");
        contactAlert.showAndWait();
    }

    private void showInfoView(String fileName) {
        BorderPane infoView = new BorderPane();
        infoView.getStyleClass().add("info-view");

        // Content VBox for displaying the elements
        VBox content = new VBox(15);
        content.setAlignment(Pos.TOP_CENTER);
        content.setPadding(new Insets(20));

        // Image placeholder (you can replace it with actual images)
        StackPane imagePlaceholder = new StackPane();
        Rectangle imageBox = new Rectangle(200, 200, Color.GRAY); // Placeholder for image
        imageBox.setArcHeight(10);  // Rounded corners
        imageBox.setArcWidth(10);   // Rounded corners
        Label imageLabel = new Label("Image");
        imageLabel.setTextFill(Color.WHITE);
        imagePlaceholder.getChildren().addAll(imageBox, imageLabel);  // Correct usage of addAll
        imagePlaceholder.getStyleClass().add("image-placeholder");

        // Title
        Label title = new Label("Details for " + fileName);
        title.getStyleClass().add("label");

        // Description
        Label description = new Label("Description: This is a detailed view of the file.");
        description.getStyleClass().add("label");

        // Rating stars (5 stars)
        HBox ratingStars = new HBox(5);
        for (int i = 0; i < 5; i++) {
            Button starButton = createStyledButton("★", null);
            starButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #FFD700; -fx-font-size: 24px;");
            ratingStars.getChildren().add(starButton);
        }
        ratingStars.setAlignment(Pos.CENTER);

        // Action button to save
        Button saveButton = createStyledButton("Save", null);
        saveButton.getStyleClass().add("save-button");

        // Action button to leave the info view
        Button leaveButton = createStyledButton("Leave", null);
        leaveButton.setOnAction(e -> showMainView()); // Navigate back to the main view

        // Action Buttons HBox
        HBox actionButtons = new HBox(10, saveButton, leaveButton);
        actionButtons.setAlignment(Pos.CENTER);

        // When Save button is clicked, add the file to the savedFiles list
        saveButton.setOnAction(e -> {
            // Add the file to the savedFiles list
            if (!savedFiles.contains(fileName)) {
                savedFiles.add(fileName);
                System.out.println(fileName + " has been saved.");
            }
        });

        // Assemble all content into the main content VBox
        content.getChildren().addAll(imagePlaceholder, title, description, ratingStars, actionButtons);

        // Set content in the center of the BorderPane
        infoView.setCenter(content);

        // Set up scene
        Scene scene = new Scene(infoView, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm()); // Ensure the CSS is applied
        primaryStage.setScene(scene);
    }




    private void showSavedView() {
        // Create the main container for saved files
        BorderPane savedView = new BorderPane();
        savedView.getStyleClass().add("saved-view");

        // Search Bar
        TextField searchBar = new TextField();
        searchBar.setPromptText("Search saved files...");
        searchBar.getStyleClass().add("search-bar");
        searchBar.setPrefWidth(300);

        HBox topBar = new HBox(20, searchBar);
        topBar.getStyleClass().add("top-bar");
        topBar.setStyle("-fx-alignment: center;");

        // Scrollable Content Area for Saved Files
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.getStyleClass().add("scroll-pane");

        FlowPane contentArea = new FlowPane();
        contentArea.getStyleClass().add("content-area");

        // Create file cards for each saved file
        for (String file : savedFiles) {
            VBox fileCard = createFileCard(file);
            fileCard.getStyleClass().add("file-card"); // Apply file-card style to each file card

            // Remove button for each saved file
            Button unfavoriteButton = createStyledButton("Remove", null);
            unfavoriteButton.getStyleClass().add("unfavorite-button");
            unfavoriteButton.setOnAction(e -> {
                savedFiles.remove(file);
                showSavedView(); // Refresh view after removing file
            });

            fileCard.getChildren().add(unfavoriteButton);
            contentArea.getChildren().add(fileCard); // Add the file card to content area
        }

        scrollPane.setContent(contentArea);
        savedView.setTop(topBar);
        savedView.setCenter(scrollPane);

        // Enhanced Back Button
        Button backButton = createStyledButton("Back to Main", null);
        backButton.getStyleClass().add("back-button");
        backButton.setOnAction(e -> showMainView());

        HBox backButtonContainer = new HBox(backButton);
        backButtonContainer.setAlignment(Pos.CENTER);
        backButtonContainer.setStyle("-fx-padding: 10px;");
        savedView.setBottom(backButtonContainer);

        // Add the CSS stylesheet and set the scene
        Scene scene = new Scene(savedView, 1200, 800);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm()); // Ensure CSS is applied
        primaryStage.setScene(scene);
    }


    private VBox createFileCard(String fileName) {
        VBox fileCard = new VBox(5);
        fileCard.getStyleClass().add("file-card");

        ImageView imageView = new ImageView();
        imageView.setFitWidth(150);
        imageView.setFitHeight(100);
        imageView.getStyleClass().add("image-placeholder");

        Button rateButton = createStyledButton(null, "/icons/star-regular.svg");
        Tooltip rateTooltip = new Tooltip("Rate");
        rateButton.setTooltip(rateTooltip);

        Button downloadButton = createStyledButton(null, "/icons/download-solid.svg");
        Tooltip downloadTooltip = new Tooltip("Download");
        downloadButton.setTooltip(downloadTooltip);

        Button infoButton = createStyledButton(null, "/icons/info-solid.svg");
        Tooltip infoTooltip = new Tooltip("Info");
        infoButton.setTooltip(infoTooltip);

        Button saveButton = createStyledButton(null, "/icons/bookmark-regular.svg");
        Tooltip saveTooltip = new Tooltip("Save");
        saveButton.setTooltip(saveTooltip);

        infoButton.setOnAction(e -> showInfoView(fileName));
        saveButton.setOnAction(e -> {
            if (!savedFiles.contains(fileName)) {
                savedFiles.add(fileName);
            }
        });

        HBox actionButtons = new HBox(5, rateButton, downloadButton, infoButton, saveButton);

        fileCard.getChildren().addAll(imageView, new Label(fileName), actionButtons);
        return fileCard;
    }

    private Button createStyledButton(String text, String iconPath) {
        Button button = new Button();

        if (iconPath != null) {
            ImageView icon = new ImageView(SVGLoader.loadSVG(iconPath, 24, 24)); // Adjust width and height as needed
            icon.setFitHeight(24);
            icon.setFitWidth(24);
            button.setGraphic(icon); // Set the icon as the graphic
        } else {
            button.setText(text); // Fallback to text if no icon is provided
        }

        button.getStyleClass().add("button");
        button.setMaxWidth(Double.MAX_VALUE);
        return button;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
