package io.amecodelabs.ims.view.transactionstage;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;

import io.amecodelabs.ims.models.utils.ContentValues;
import io.amecodelabs.ims.view.base.PrimaryStage;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TransactionsViewImpl implements TransactionsView<ContentValues>, Initializable {
	/* JavaFx Components */
	@FXML
    private AnchorPane root;
	@FXML
    private Pane panelPagination;
    @FXML
    private StackPane stackPane;
    @FXML
    private TableView<DataModelTransaction> tbTransactions;
    @FXML
    private TableColumn<DataModelTransaction, String> colDescription;
    @FXML
    private TableColumn<DataModelTransaction, Integer> colQuantity;
    @FXML
    private TableColumn<DataModelTransaction, String> colDate;
    @FXML
    private ImageView loadTransactions;
    @FXML
    private Pagination pagination;
    
    private PrimaryStage primary;
    private PresenterTransactionsView<ContentValues> presenter;
    private final ObservableList<DataModelTransaction> data = FXCollections.observableArrayList();
    private Image load = new Image("/images/load.gif", true);
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		presenter = new PresenterTransactionsViewImpl(this);
		
		colDescription.setCellValueFactory(
	    	new PropertyValueFactory<DataModelTransaction, String>("description")
	    );
		colQuantity.setCellValueFactory(
	        new PropertyValueFactory<DataModelTransaction, Integer>("quantity")
	    );	
		colDate.setCellValueFactory(
	        new PropertyValueFactory<DataModelTransaction, String>("date")
	    );
		
		tbTransactions.setItems(data);
		pagination.setPageFactory((pageIndex) -> {
    		presenter.getLoadStock(pageIndex * 11);
    		return panelPagination;
    	});
	}
	
	@Override
	public void setPrimaryStage(PrimaryStage primary) {
		this.primary = primary;
	}

	@FXML
    void onClose(MouseEvent event) {
		((Stage) root.getScene().getWindow()).close();
		if(primary != null) primary.updateStage(this, "close");
    }
	
	@Override
	public void loadStock(ContentValues[] stock) {
		int size = stock.length;
		DataModelTransaction dataModels[] = new DataModelTransaction[size];
		for(int i=0; i<size; i++) {
			DataModelTransaction dataModel = new DataModelTransaction(stock[i]);
			dataModels[i] = dataModel;
		}
		
		data.setAll(dataModels);
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
	public void showProgress() {
		Platform.runLater(
			() -> {
				loadTransactions.setImage(load);
			}
		);
	}

	@Override
	public void hiddenProgress() {
		Platform.runLater(
			() -> {
				loadTransactions.setImage(null);
			}
		);
	}
	
	@Override
	public PresenterTransactionsView<ContentValues> getPresenter() {
		return presenter;
	}
	
	@Override
	public String getName() {
		return "TransactionsView";
	}

}
