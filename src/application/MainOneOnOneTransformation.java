package application;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainOneOnOneTransformation extends Application {

	private File metaDataFile;
	private File resultsFile;

	FileChooser fileChooser = new FileChooser();
	FileChooser resultsChooser = new FileChooser();

	@Override
	public void start(Stage primaryStage) {
		try {

			StackPane root = new StackPane();

			primaryStage.setTitle("Kalypso Agile Rapid Loader Optimizer");

			GridPane grid = new GridPane();
			grid.setAlignment(Pos.CENTER);
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(50, 50, 50, 50));

			Text scenetitle = new Text("Please load all fields");
			scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
			grid.add(scenetitle, 0, 0, 2, 1);

			final Button metadataButton = new Button("Open MetaData file");
			HBox hbBtn = new HBox(10);
			hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
			hbBtn.getChildren().add(metadataButton);
			grid.add(hbBtn, 0, 1);

			TextField metaDataPath = new TextField();
			grid.add(metaDataPath, 1, 1);

			metadataButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(final ActionEvent e) {
					configureFileChooser(fileChooser);
					metaDataFile = fileChooser.showOpenDialog(primaryStage);
					if (metaDataFile != null) {
						metaDataPath.setText(metaDataFile.getName());
					} else {
						metaDataPath.setText("");
					}
				}
			});

			Label sheetLabel = new Label("Sheet with transformations");
			TextField sheet = new TextField("");
			grid.add(sheetLabel, 0, 2);
			grid.add(sheet, 1, 2);
			Tooltip sheetTooltip = new Tooltip(
					"The sheet where all the one on one transformation are, they should be in the order of transformFrom and transformTo");
			Tooltip.install(sheet, sheetTooltip);

			Label splitLabel = new Label("Character used for splitting cells");
			TextField split = new TextField("");
			grid.add(splitLabel, 0, 3);
			grid.add(split, 1, 3);
			Tooltip splitTooltip = new Tooltip(
					"If there are multiple stuff to transform in a single cell, this value is used to split those and transform every single result");
			Tooltip.install(split, splitTooltip);

			final Button processButton = new Button("Process");
			HBox processHbBtn = new HBox(10);
			processHbBtn.setAlignment(Pos.BOTTOM_RIGHT);
			processHbBtn.getChildren().add(processButton);
			grid.add(processHbBtn, 3, 16);
			processButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(final ActionEvent e) {
					if (metaDataFile == null) {
						// TODO send error message when it all breaks
					}

					try {
						FormData formData = new FormData(metaDataFile, split.getText(), sheet.getText());

						OneOnOneTransformations.processData(formData);
						displayMessage(AlertType.INFORMATION, "Run succesfully");

					} catch (InvalidFormatException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});

			root.getChildren().add(grid);
			Scene scene = new Scene(root, 800, 700);
			primaryStage.setScene(scene);

			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void displayMessage(AlertType severity, String message) {
		Alert alert = new Alert(severity);
		alert.setContentText(message);
		alert.showAndWait();
	}

	private static void configureFileChooser(final FileChooser fileChooser) {
		fileChooser.setTitle("Open Files");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		fileChooser.getExtensionFilters()
				.addAll(new FileChooser.ExtensionFilter("All Files", "*.xlsx", "*.xls", "*.csv"));
	}

	public static void main(String[] args) {
		launch(args);
	}

	public static void hackTooltipStartTiming(Tooltip tooltip) {
		try {
			Field fieldBehavior = tooltip.getClass().getDeclaredField("BEHAVIOR");
			fieldBehavior.setAccessible(true);
			Object objBehavior = fieldBehavior.get(tooltip);

			Field fieldTimer = objBehavior.getClass().getDeclaredField("activationTimer");
			fieldTimer.setAccessible(true);
			Timeline objTimer = (Timeline) fieldTimer.get(objBehavior);

			objTimer.getKeyFrames().clear();
			objTimer.getKeyFrames().add(new KeyFrame(new Duration(250)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
