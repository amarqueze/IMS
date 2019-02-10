package io.amecodelabs.ims.view.productstage;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;

import io.amecodelabs.ims.models.utils.ContentValues;
import io.amecodelabs.ims.view.addproduct.AddProductBuildable;
import io.amecodelabs.ims.view.addstockproduct.ProductControlBuildable;
import io.amecodelabs.ims.view.base.BuildWindowDirector;
import io.amecodelabs.ims.view.base.PrimaryStage;
import io.amecodelabs.ims.view.base.SubStage;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ProductsViewImpl implements ProductsView<ContentValues>, Initializable {
	/* JavaFx Components */
	@FXML
    private AnchorPane root;
    @FXML
    private StackPane stackPane;
    @FXML
    private Pane panelPagination;
    @FXML
    private JFXButton btnNewProduct;
    @FXML
    private TableView<DataModelProduct> tbProducts;
    @FXML
    private TableColumn<DataModelProduct, String> colDescription;
    @FXML
    private TableColumn<DataModelProduct, Integer> colAvailableStock;
    @FXML
    private TableColumn<DataModelProduct, String> colUnit;
    @FXML
    private TableColumn<DataModelProduct, String> colProviderMain;
    @FXML
    private TableColumn<DataModelProduct, String> colCategory;
    @FXML
    private TableColumn<DataModelProduct, Integer> colStockMin;
    @FXML
    private TableColumn<DataModelProduct, Integer> colStockMax;
    @FXML
    private TableColumn<DataModelProduct, DataModelProduct> colControl;
    @FXML
    private ImageView lblLoad;
    @FXML
    private Pagination pagination;
    /* Fin JavaFx Components */
    
    private PrimaryStage primary;
    private PresenterProductsView<ContentValues> presenter;
    private final ObservableList<DataModelProduct> data = FXCollections.observableArrayList();
    private Image load = new Image("/images/load.gif", true);
    
    private class ButtonCell extends TableCell<DataModelProduct, DataModelProduct> {
		Group group = new Group();
        final Button btnControl = new Button("Entry");
         
        public ButtonCell(){
        	btnControl.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                	Map<String, Object> params = new HashMap<>();
                	params.put("product", getItem());
                	BuildWindowDirector.getDirector().create(new ProductControlBuildable(params, presenter.getProductView()));
                }
            });
        	
        	HBox hbox = new HBox();
        	hbox.getChildren().add(btnControl);
        	hbox.setAlignment(Pos.CENTER);
        	group.getChildren().add(hbox);
        }
        
        @Override
        protected void updateItem(DataModelProduct t, boolean empty) {
        	super.updateItem(t, empty);
            if(!empty && t != null) {
                setGraphic(group);
            } else {
            	setText(null);
            	setGraphic(null);
            }
        }
    }
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	presenter = new PresenterProductsViewImpl(this);
    	
    	colDescription.setCellValueFactory(
    		new PropertyValueFactory<DataModelProduct, String>("description")
    	);
    	colAvailableStock.setCellValueFactory(
        	new PropertyValueFactory<DataModelProduct, Integer>("availableStock")
        );
    	
    	colUnit.setCellValueFactory(
        	new PropertyValueFactory<DataModelProduct, String>("unit")
        );
    	colProviderMain.setCellValueFactory(
        	new PropertyValueFactory<DataModelProduct, String>("providerMain")
        );
    	colCategory.setCellValueFactory(
            new PropertyValueFactory<DataModelProduct, String>("category")
        );
    	colStockMin.setCellValueFactory(
            new PropertyValueFactory<DataModelProduct, Integer>("minimumStock")
        );
    	colStockMax.setCellValueFactory(
            new PropertyValueFactory<DataModelProduct, Integer>("maximumStock")
        );
    	
    	colControl.setCellValueFactory( (p) -> new SimpleObjectProperty<DataModelProduct>(p.getValue()));
    	colControl.setCellFactory( (p) -> new ButtonCell());
    	
    	tbProducts.setItems(data);
    	pagination.setPageFactory((pageIndex) -> {
    		presenter.getLoadProducts(pageIndex * 11);
    		return panelPagination;
    	});
	}
    
    @Override
	public void loadProducts(ContentValues[] products) {
    	Platform.runLater(
    		() -> {
    			int size = products.length;
    			DataModelProduct[] dataModels = new DataModelProduct[size];
    			for(int i=0; i<products.length; i++) {
    				DataModelProduct dataModel = new DataModelProduct(products[i]);
    				dataModels[i] = dataModel;
    			}
    			data.setAll(dataModels);
    		}
    	);
	}
    
	public void update(ContentValues product) {
		Platform.runLater(
	    	() -> {
	    		DataModelProduct dataModel = new DataModelProduct(product);
	    		data.add(dataModel);
	    	}
	    );
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
				btnNewProduct.setDisable(true);
				lblLoad.setImage(load);
			}
		);
	}

	@Override
	public void hiddenProgress() {
		Platform.runLater(
			() -> {
				btnNewProduct.setDisable(false);
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
		if(primary != null) primary.updateStage(this, "close");
    }

    @FXML
    void onNewProduct(ActionEvent event) {
    	BuildWindowDirector.getDirector().create(new AddProductBuildable(this));
		btnNewProduct.setDisable(true);
    }
	
	@Override
	public void setPrimaryStage(PrimaryStage primary) {
		this.primary = primary;
	}
	
	@Override
	public void updateStage(SubStage subStage, Object newness) {
		Platform.runLater(
			() -> {
				if(subStage.getName().equals("AddProductView")) {
					if(newness instanceof String) {
						btnNewProduct.setDisable(false);
					} else {
						ContentValues product = (ContentValues) newness;
						update(product);
					}
				} else {
					if(newness instanceof Object[]) {
						int newAvailableStock = (Integer) ((Object[])newness)[0];
						DataModelProduct product = (DataModelProduct) ((Object[])newness)[1];
						product.setAvailableStock(newAvailableStock);
						tbProducts.refresh();
						showMessage("Update", "Updated product: " + product.getDescription());
					}
				}
			}
		);
	}
	
	@Override
	public String getName() {
		return "ProductsView";
	}

	@Override
	public PresenterProductsView<ContentValues> getPresenter() {
		return presenter;
	}

}
