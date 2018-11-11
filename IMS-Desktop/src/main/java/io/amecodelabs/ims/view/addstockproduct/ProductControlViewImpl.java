package io.amecodelabs.ims.view.addstockproduct;

import java.net.URL;
import java.time.LocalDate;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;

import io.amecodelabs.ims.models.utils.ContentValues;
import io.amecodelabs.ims.view.base.PrimaryStage;
import io.amecodelabs.ims.view.productstage.DataModelProduct;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ProductControlViewImpl implements ProductControlView<ContentValues>, Initializable {
	@FXML
    private AnchorPane root;
	@FXML
    private StackPane stackPane;
	@FXML
    private TextField txtProduct;
    @FXML
    private DatePicker txtDate;
    @FXML
    private ComboBox<String> cbAction;
    @FXML
    private TextField txtQuantity;
    @FXML
    private Button btnConfirm;
    @FXML
    private ImageView lblLoad;
    @FXML
    private Label lblUnit;
    
    private PrimaryStage primary;
    private DataModelProduct modelProduct;
    private PresenterProductControlView<ContentValues> presenter;
    private Image load = new Image("/images/load.gif", true);

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		presenter = new PresenterProductControlViewImpl(this);
		cbAction.getItems().addAll("input", "output");
		cbAction.setValue("input");
		
		txtQuantity.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\d*")) {
				StringProperty field = (StringProperty) observable;
				field.setValue(oldValue);
	        }
		});
	}
	
	@Override
	public void setParams(Map<String, Object> params) {
		modelProduct = (DataModelProduct) params.get("product");
		txtProduct.setText(modelProduct.getDescription());
		txtProduct.setDisable(true);
		lblUnit.setText(modelProduct.getUnit());
		txtDate.setValue(LocalDate.now());
	}
	
	@FXML
	void onConfirm(ActionEvent event) {
		if( Integer.parseInt(txtQuantity.getText()) > 0  ) {
			ContentValues stock = ContentValues.newInstanceEmpy("stock");
			ContentValues stockProduct = ContentValues.newInstanceEmpy("stockProduct");
			ContentValues product = ContentValues.newInstanceEmpy("product");
			
			stockProduct.put("_id", UUID.randomUUID().toString());
			stockProduct.put("type", cbAction.getSelectionModel().getSelectedItem());
			stockProduct.put("product", modelProduct.getDescription());
			stockProduct.put("quantity", Integer.parseInt(txtQuantity.getText()));
			stockProduct.put("date", txtDate.getValue().toString());
			
			product.put("_id", modelProduct.getId());
			product.put("available_stock", modelProduct.getAvailableStock());
			
			stock.put(stockProduct);
			stock.put(product);
			presenter.saveStock(stock);
		} else {
			showMessage("Error", "Enter a valid amount");
		}
	}
	
	@Override
	public void showMessage(String title, String msg) {
		Platform.runLater(
			() -> {
				JFXDialogLayout content = new JFXDialogLayout();
				content.setHeading(new Text(title));
				content.setBody(new Text(msg));
				stackPane.toFront();
				JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.TOP);
					
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
	public void update(ContentValues productControl) {
		int newAvailableStock = productControl.getValueInteger("available_stock");
		modelProduct.setAvailableStock(newAvailableStock);
		primary.updateStage(this, productControl);
		onCloseStage(null);
	}
	
	@FXML
    void onCloseStage(MouseEvent event) {
		Platform.runLater(
			() -> {
				((Stage) root.getScene().getWindow()).close();
				primary.updateStage(this, "close");
			}
		);
    }
	
	@Override
	public void showProgress() {
		Platform.runLater(
			() -> {
				btnConfirm.setDisable(true);
				lblLoad.setImage(load);
			}
		);
	}

	@Override
	public void hiddenProgress() {
		Platform.runLater(
			() -> {
				btnConfirm.setDisable(false);
				lblLoad.setImage(null);
			}
		);
	}
	
	@FXML
    void onStackPaneClose(MouseEvent event) {
		stackPane.toBack();
    }
	
	@Override
	public void setPrimaryStage(PrimaryStage primary) {
		this.primary = primary;
	}
	
	@Override
	public String getName() {
		return "ProductControl";
	}

	@Override
	public PresenterProductControlView<ContentValues> getPresenter() {
		return presenter;
	}
	
}
