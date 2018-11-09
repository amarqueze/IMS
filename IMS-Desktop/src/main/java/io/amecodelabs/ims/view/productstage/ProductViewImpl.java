package io.amecodelabs.ims.view.productstage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;

import io.amecodelabs.ims.models.utils.ContentValues;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ProductViewImpl implements ProductView<ContentValues>, Initializable {
	/* JavaFx Components */
	@FXML
    private AnchorPane root;
    @FXML
    private StackPane stackPane;
    @FXML
    private TextField txtDescription;
    @FXML
    private TextField txtUnit;
    @FXML
    private TextField txtAvailableStock;
    @FXML
    private TextField txtMinimumStock;
    @FXML
    private TextField txtMaximumStock;
    @FXML
    private Button btnSave;
    @FXML
    private ComboBox<ContentValues> cbCategories;
    @FXML
    private ComboBox<ContentValues> cbProviders;
    @FXML
    private ImageView lblLoad;
	/* Fin JavaFx Components */
	
	private PresenterProductView<ContentValues> presenter;
	private Image load = new Image("/images/load.gif", true);
	ObservableList<ContentValues> optionsProviders = FXCollections.observableArrayList();
	ObservableList<ContentValues> optionsCategories = FXCollections.observableArrayList();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		presenter = new PresenterProductViewImpl(this);
		
		Callback<ListView<ContentValues>, ListCell<ContentValues>> cellFactory = (list) -> {
			return new ListCell<ContentValues>() {
	            @Override
	            protected void updateItem(ContentValues item, boolean empty) {
	                super.updateItem(item, empty);
	                if (item == null || empty) {
	                    setGraphic(null);
	                } else {
	                    setText(item.getValueString("name"));
	                }
	            }
	        } ;
		};
		
		ChangeListener<String> listener = (observable, oldValue, newValue) -> {
			if (!newValue.matches("\\d*")) {
				StringProperty field = (StringProperty) observable;
				field.setValue(oldValue);
	        }
		};
		
		txtAvailableStock.textProperty().addListener(listener);
		txtMaximumStock.textProperty().addListener(listener);
		txtMinimumStock.textProperty().addListener(listener);
		
		cbProviders.setItems(optionsProviders);
		cbProviders.setButtonCell(cellFactory.call(null));
		cbProviders.setCellFactory(cellFactory);
		cbProviders.setVisibleRowCount(8);
		
		cbCategories.setItems(optionsCategories);
		cbCategories.setButtonCell(cellFactory.call(null));
		cbCategories.setCellFactory(cellFactory);
		cbCategories.setVisibleRowCount(8);
		
		presenter.getProviders();
		presenter.getCategories();
	}

	@Override
	public void loadProviders(ContentValues[] providers) {
		Platform.runLater(
			() -> {
				for(ContentValues provider: providers) {
					optionsProviders.add(provider);
				}
				cbProviders.setValue(optionsProviders.get(0));
			}
		);
	}

	@Override
	public void loadCategories(ContentValues[] categories) {
		Platform.runLater(
			() -> {
				for(ContentValues category: categories) {
					optionsCategories.add(category);
				}
				cbCategories.setValue(optionsCategories.get(0));
			}
		);
	}
	
	@Override
	public void clearForm() {
		txtAvailableStock.setText("0");
		txtMinimumStock.setText("15");
		txtMaximumStock.setText("100");
		txtUnit.setText("");
		txtDescription.setText("");
	}
	
	@FXML
    void onSave(ActionEvent event) {
		if(!txtDescription.getText().equals("") &&
				!txtUnit.getText().equals("") && 
				!txtAvailableStock.getText().equals("") &&
				!txtMaximumStock.getText().equals("") && 
				!txtMinimumStock.getText().equals("")) {
			
			ContentValues product = ContentValues.newInstanceEmpy("product");
			product.put("_id", UUID.randomUUID().toString());
			product.put("description", txtDescription.getText());
			product.put("unit", txtUnit.getText());
			product.put("available_stock", txtAvailableStock.getText());
			product.put("maximum_stock", txtMaximumStock.getText());
			product.put("minimum_stock", txtMinimumStock.getText());
			product.put("provider_main", cbProviders.getSelectionModel().getSelectedItem().getValueString("name"));
			product.put("category", cbCategories.getSelectionModel().getSelectedItem().getValueString("_id"));
			
			presenter.saveProduct(product);
		} else {
			showMessage("Empty fields", "Minimum fields required are empty");
		}
    }

	@Override
	public void showMessage(String title, String text) {
		Platform.runLater(
			() -> {
				JFXDialogLayout content = new JFXDialogLayout();
				content.setHeading(new Text(title));
				content.setBody(new Text(text));
				stackPane.toFront();
				JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
				
				JFXButton btnDone = new JFXButton("Done");
				btnDone.setOnAction((event) ->  {
					dialog.close();
					stackPane.toBack();
				});
				content.setActions(btnDone);
					
				dialog.show();
			}
		);
	}

	@Override
	public void showRegisterProgress() {
		Platform.runLater(
			() -> {
				btnSave.setDisable(true);
				lblLoad.setImage(load);
			}
		);
	}

	@Override
	public void hiddenRegisterProgress() {
		Platform.runLater(
			() -> {
				btnSave.setDisable(false);
				lblLoad.setImage(null);
			}
		);
	}
	
	@FXML
    void onCloseStackPane(MouseEvent event) {
		stackPane.toBack();
    }
	
	@FXML
	void onCloseStage(MouseEvent event) {
		((Stage) root.getScene().getWindow()).close();
		//primary.updateStage(this, "close");
	}
	
	@Override
	public String getName() {
		return "ProductView";
	}

	@Override
	public PresenterProductView<ContentValues> getPresenter() {
		return presenter;
	}

}
