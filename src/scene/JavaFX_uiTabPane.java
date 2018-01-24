package scene;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @web http://java-buddy.blogspot.com/
 */
public class JavaFX_uiTabPane extends Application {

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("http://java-buddy.blogspot.com/");
		Group root = new Group();
		Scene scene = new Scene(root, 400, 300, Color.WHITE);

		TabPane tabPane = new TabPane();
		BorderPane mainPane = new BorderPane();

		// Create Tabs
		Tab tabA = new Tab();
		tabA.setText("SubClass Transformation");
		// Add something in Tab

		Main main = new Main();
		StackPane pane = main.load(primaryStage);

		tabA.setContent(pane);

		tabPane.getTabs().add(tabA);

		Tab tabB = new Tab();
		tabB.setText("Split Tool");
		// Add something in Tab
		StackPane tabB_stack = new StackPane();
		tabB_stack.setAlignment(Pos.CENTER);
		tabB_stack.getChildren().add(new Label("Label@Tab B"));
		tabB.setContent(tabB_stack);
		tabPane.getTabs().add(tabB);

		Tab tabC = new Tab();
		tabC.setText("Validation Tool");
		// Add something in Tab
		VBox tabC_vBox = new VBox();
		tabC_vBox.getChildren().addAll(new Button("Button 1@Tab C"), new Button("Button 2@Tab C"),
				new Button("Button 3@Tab C"), new Button("Button 4@Tab C"));
		tabC.setContent(tabC_vBox);
		tabPane.getTabs().add(tabC);

		Tab tabD = new Tab();
		tabD.setText("Batch tool");
		// Add something in Tab
		VBox tabD_vBox = new VBox();
		tabD_vBox.getChildren().addAll(new Button("Button 1@Tab C"), new Button("Button 2@Tab C"),
				new Button("Button 3@Tab C"), new Button("Button 4@Tab C"));
		tabD.setContent(tabD_vBox);
		tabPane.getTabs().add(tabD);

		Tab tabE = new Tab();
		tabE.setText("One on One Transformation");
		// Add something in Tab
		VBox tabE_vBox = new VBox();
		tabE_vBox.getChildren().addAll(new Button("Button 1@Tab C"), new Button("Button 2@Tab C"),
				new Button("Button 3@Tab C"), new Button("Button 4@Tab C"));
		tabE.setContent(tabE_vBox);
		tabPane.getTabs().add(tabE);

		mainPane.setCenter(tabPane);

		mainPane.prefHeightProperty().bind(scene.heightProperty());
		mainPane.prefWidthProperty().bind(scene.widthProperty());

		root.getChildren().add(mainPane);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}