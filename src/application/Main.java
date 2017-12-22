package application;

import java.io.File;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.agile.api.APIException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application {

	// TODO implementacion para funcionar con documentos que son urls

	private File metaDataFile;
	private File directoryWithFile;
	private File resultsDirectoryFile;

	FileChooser fileChooser = new FileChooser();
	DirectoryChooser directoryChooser = new DirectoryChooser();

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

			CheckBox checkFilesAreOnSameFolderAsMetadata = new CheckBox("Documents are on the same folder");
			checkFilesAreOnSameFolderAsMetadata.setSelected(true);
			grid.add(checkFilesAreOnSameFolderAsMetadata, 0, 2);

			final Button filesButton = new Button("Open Documents folder");
			HBox filesHbBtn = new HBox(10);
			filesHbBtn.setAlignment(Pos.BOTTOM_RIGHT);
			filesHbBtn.getChildren().add(filesButton);

			TextField documentsPath = new TextField();

			checkFilesAreOnSameFolderAsMetadata.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(final ActionEvent e) {
					if (!checkFilesAreOnSameFolderAsMetadata.isSelected()) {
						grid.add(filesHbBtn, 0, 3);
						grid.add(documentsPath, 1, 3);
					} else {
						grid.getChildren().remove(filesHbBtn);
						grid.getChildren().remove(documentsPath);
					}
				}
			});

			filesButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(final ActionEvent e) {
					directoryWithFile = directoryChooser.showDialog(primaryStage);
					if (directoryWithFile != null) {
						documentsPath.setText(directoryWithFile.getAbsolutePath());
					} else {
						documentsPath.setText("");
					}
				}
			});

			Label userName = new Label("Column name with Files Names:");
			grid.add(userName, 0, 4);

			TextField fileNameColumn = new TextField("info_card_id");
			grid.add(fileNameColumn, 1, 4);

			Label pw = new Label("Column name with file Extensions:");
			grid.add(pw, 0, 5);

			TextField fileExtensionColumn = new TextField("File Extension");
			grid.add(fileExtensionColumn, 1, 5);

			Label splitEach = new Label("Split Metadata file in how many rows:");
			grid.add(splitEach, 0, 7);

			TextField splitMetaDataEachRows = new TextField("10");
			grid.add(splitMetaDataEachRows, 1, 7);

			// Add elements for indexFileCreation
			Label headerTitleBlock = new Label("Index file data, Title Block Columns");

			Label objecTypeLabel = new Label("Type of object");
			ObservableList<String> objecTypeOptions = FXCollections.observableArrayList("CHANGE", "DECLARATION",
					"FILEFOLDER", "ITEM", "MFR", "MFR_PART", "COMMODITY", "PRICE", "PSR", "ACTIVITY", "QCR", "RFQ",
					"RESPONSE", "PROJECT", "SPECIFICATION", "SUBSTANCE", "SUPPLIER");
			final ComboBox<String> objecType = new ComboBox<String>(objecTypeOptions);

			Label numberLabel = new Label("Column name with Title Block Number");
			TextField numberColumn = new TextField();

			Label revisionLabel = new Label("Column name with Title Block Revision");
			TextField revisionColumn = new TextField();

			Label pathToFileFromFileVaultLabel = new Label("Path to file from FileVault");
			TextField pathToFileFromFileVault = new TextField();

			Label importTypeLabel = new Label("Import Type");
			ObservableList<String> importTypeOptions = FXCollections.observableArrayList("FILE", "INPLACE");
			final ComboBox<String> importType = new ComboBox<String>(importTypeOptions);

			Label descriptionLabel = new Label("Column name with Description");
			TextField descriptionColumn = new TextField();

			CheckBox forTesting = new CheckBox("For Testing");

			CheckBox createIndexFile = new CheckBox("Create indexFile");
			createIndexFile.setSelected(false);
			grid.add(createIndexFile, 0, 8);
			createIndexFile.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(final ActionEvent e) {
					if (createIndexFile.isSelected()) {
						grid.add(headerTitleBlock, 0, 9);
						grid.add(objecTypeLabel, 0, 10);
						grid.add(objecType, 1, 10);
						if (!forTesting.isSelected()) {
							grid.add(numberLabel, 0, 11);
							grid.add(numberColumn, 1, 11);
						}
						grid.add(revisionLabel, 0, 12);
						grid.add(revisionColumn, 1, 12);
						grid.add(pathToFileFromFileVaultLabel, 0, 13);
						grid.add(pathToFileFromFileVault, 1, 13);
						grid.add(importTypeLabel, 0, 14);
						grid.add(importType, 1, 14);
						grid.add(descriptionLabel, 0, 15);
						grid.add(descriptionColumn, 1, 15);
					} else {
						grid.getChildren().remove(headerTitleBlock);
						grid.getChildren().remove(objecTypeLabel);
						grid.getChildren().remove(objecType);
						if (!forTesting.isSelected()) {
							grid.getChildren().remove(numberLabel);
							grid.getChildren().remove(numberColumn);
						}
						grid.getChildren().remove(revisionLabel);
						grid.getChildren().remove(revisionColumn);
						grid.getChildren().remove(pathToFileFromFileVaultLabel);
						grid.getChildren().remove(pathToFileFromFileVault);
						grid.getChildren().remove(importTypeLabel);
						grid.getChildren().remove(importType);
						grid.getChildren().remove(descriptionLabel);
						grid.getChildren().remove(descriptionColumn);
					}
				}
			});

			final Button resultsButton = new Button("Select results folder");
			HBox resultsHbBtn = new HBox(10);
			resultsHbBtn.setAlignment(Pos.BOTTOM_RIGHT);
			resultsHbBtn.getChildren().add(resultsButton);

			TextField resultsPath = new TextField();

			resultsButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(final ActionEvent e) {
					resultsDirectoryFile = directoryChooser.showDialog(primaryStage);
					if (directoryWithFile != null) {
						resultsPath.setText(directoryWithFile.getAbsolutePath());
					} else {
						resultsPath.setText("");
					}
				}
			});

			Label agileUserNameLabel = new Label("User name");
			TextField agileUserName = new TextField("kmendoza");

			Label agilePasswordLabel = new Label("Password");
			PasswordField agilePassword = new PasswordField();

			Label agileUrlLabel = new Label("Server Url");
			TextField agileUrl = new TextField("http://a934dev1.kalypsocloud.com:7001/Agile");

			Label agileWorkFlowNameLabel = new Label("Workflow name for CO");
			TextField agileWorkFlowName = new TextField("Pack/Label Change Request");

			CheckBox createChangeOrders = new CheckBox("Create Change Order");
			createChangeOrders.setSelected(false);
			grid.add(createChangeOrders, 2, 8);
			createChangeOrders.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(final ActionEvent e) {
					if (createChangeOrders.isSelected()) {
						grid.add(agileUserNameLabel, 2, 9);
						grid.add(agileUserName, 3, 9);
						grid.add(agilePasswordLabel, 2, 10);
						grid.add(agilePassword, 3, 10);
						grid.add(agileUrlLabel, 2, 11);
						grid.add(agileUrl, 3, 11);
						grid.add(agileWorkFlowNameLabel, 2, 12);
						grid.add(agileWorkFlowName, 3, 12);
					} else {
						grid.getChildren().remove(agileUserNameLabel);
						grid.getChildren().remove(agileUserName);
						grid.getChildren().remove(agilePasswordLabel);
						grid.getChildren().remove(agilePassword);
						grid.getChildren().remove(agileUrlLabel);
						grid.getChildren().remove(agileUrl);
						grid.getChildren().remove(agileWorkFlowNameLabel);
						grid.getChildren().remove(agileWorkFlowName);

					}
				}
			});

			grid.add(forTesting, 0, 16);
			forTesting.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(final ActionEvent e) {
					if (forTesting.isSelected() && !createIndexFile.isSelected()) {
						grid.add(numberLabel, 0, 11);
						grid.add(numberColumn, 1, 11);
					} else if (!forTesting.isSelected() && !createIndexFile.isSelected()) {
						grid.getChildren().remove(numberLabel);
						grid.getChildren().remove(numberColumn);
					}
				}
			});

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
					if (forTesting.isSelected() && numberColumn.getText().equals("")) {
						displayMessage(AlertType.ERROR,
								"When \"For testing\" option is selected you need to selecte a \"Column name with Title Block Number\"");
						return;
					}
					if (!checkFilesAreOnSameFolderAsMetadata.isSelected() && directoryWithFile == null) {
						displayMessage(AlertType.ERROR,
								"You need to specify either the Directory where the files are or that they are on the same folder");
						return;
					}
					if (checkFilesAreOnSameFolderAsMetadata.isSelected()) {
						directoryWithFile = new File(
								metaDataFile.getAbsolutePath().replace(metaDataFile.getName(), ""));
					}

					if (fileExtensionColumn != null && !fileExtensionColumn.getText().equals("")) {
						// TODO error mesaje cuando todo truene
					}
					if (fileNameColumn != null && !fileNameColumn.getText().equals("")) {
						// TODO error mesaje cuando todo truene
					}

					ProgressIndicator pi = new ProgressIndicator();
					VBox box = new VBox(pi);
					box.setAlignment(Pos.CENTER);
					// Grey Background
					grid.setDisable(true);
					root.getChildren().add(box);

					try {
						FormData formData = new FormData(metaDataFile, directoryWithFile, fileExtensionColumn.getText(),
								fileNameColumn.getText(), splitMetaDataEachRows.getText(), objecType.getValue(),
								numberColumn.getText(), revisionColumn.getText(), pathToFileFromFileVault.getText(),
								importType.getValue(), descriptionColumn.getText(), createIndexFile.isSelected(),
								agileUserName.getText(), agilePassword.getText(), agileUrl.getText(),
								agileWorkFlowName.getText(), createChangeOrders.isSelected(), forTesting.isSelected(),
								resultsDirectoryFile);

						try {
							ImportDataProcessor.processData(formData);
						} catch (APIException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						displayMessage(AlertType.INFORMATION, "Run succesfully");

					} catch (InvalidFormatException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					grid.setDisable(false);
					root.getChildren().remove(box);
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

}
