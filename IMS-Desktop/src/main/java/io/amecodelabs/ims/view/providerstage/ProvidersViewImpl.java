package io.amecodelabs.ims.view.providerstage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import io.amecodelabs.ims.models.utils.ContentValues;
import io.amecodelabs.ims.view.base.PrimaryStage;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ProvidersViewImpl implements ProvidersView<ContentValues>, Initializable {
	/* JavaFX Components  */
	@FXML
	private AnchorPane root;
	@FXML
    private StackPane stackPane;
	@FXML
	private JFXTextField txtNameProvider;
	@FXML
	private JFXTextField txtPhone;
	@FXML
	private JFXTextField txtEmail;
	@FXML
	private JFXTextArea txtComments;
	@FXML
	private JFXTextField txtAddress;
	@FXML
	private JFXTextField txtCity;
	@FXML
	private Button btnSave;
	@FXML
	private Button btnReset;
	@FXML
	private Button btnClose;
	@FXML
	private Button btnPrint;
    @FXML
    private JFXTextField txtFiltered;
	@FXML
	private TableView<DataModelProvider> tbProviders;
	@FXML
	private TableColumn<DataModelProvider, String> colProvider;
	@FXML
	private TableColumn<DataModelProvider, String> colPhone;
	@FXML
	private TableColumn<DataModelProvider, String> colEmail;
	@FXML
	private TableColumn<DataModelProvider, String> colAddress;
	@FXML
	private TableColumn<DataModelProvider, String> colCity;
	@FXML
	private TableColumn<DataModelProvider, String> colComments;
	@FXML
	private TableColumn<DataModelProvider, DataModelProvider> colEdit;
	@FXML
    private ImageView lblLoadSave;
	@FXML
    private ImageView lblLoadProvider;
	/* Fin JavaFX Components  */

	private PrimaryStage primary;
	private Image load = new Image("/images/load.gif", true);
	private final ObservableList<DataModelProvider> data = FXCollections.observableArrayList();
	private PresenterProvidersView<ContentValues> presenter;
	
	private class ButtonCell extends TableCell<DataModelProvider, DataModelProvider> {
		Group group = new Group();
        final Button btnEdit = new Button("Edit");
        final Button btnDelete = new Button("Delete");
         
        public ButtonCell(){
        	btnEdit.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                	ContentValues provider = ContentValues.newInstanceEmpy("provider");
                	provider.put("_id", getItem().getId());
                	provider.put("name", getItem().getProvider());
                	provider.put("email", getItem().getEmail());
                	provider.put("phone", getItem().getPhone());
                	provider.put("city", getItem().getCity());
                	provider.put("address", getItem().getAddress());
                	provider.put("observations", getItem().getComments());
                	
                	presenter.editProvider(provider);
                }
            });
        	btnDelete.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                	JFXDialogLayout content = new JFXDialogLayout();
    				content.setHeading(new Text("Delete"));
    				content.setBody(new Text("¿Do you want to delete this provider?"));
    				stackPane.toFront();
    				JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
    				
    				JFXButton btnDone = new JFXButton("Sure");
    				btnDone.setOnAction((event) ->  {
    					dialog.close();
    					stackPane.toBack();
    					presenter.deleteProvider(getItem().getId());
    				});
    				
    				JFXButton btnNo = new JFXButton("No");
    				btnNo.setOnAction((event) ->  {
    					dialog.close();
    					stackPane.toBack();
    				});
    				content.setActions(btnDone, btnNo);
    				
    				dialog.show();
                }
            });
        	
        	HBox hbox = new HBox();
        	hbox.getChildren().add(btnEdit);
        	hbox.getChildren().add(btnDelete);
        	hbox.setAlignment(Pos.CENTER);
        	
        	group.getChildren().add(hbox);
        }
        
        @Override
        protected void updateItem(DataModelProvider t, boolean empty) {
        	super.updateItem(t, empty);
            if(!empty && t != null) {
                setGraphic(group);
            } else {
            	setText(null);
            	setGraphic(null);
            }
        }
    }

	private class EditingCell extends TableCell<DataModelProvider, String> {
        private TextField textField;
          
        public EditingCell() {}
          
        @Override
        public void startEdit() {
              
            super.startEdit();
              
            if (textField == null) {
                createTextField();
            }
              
            setGraphic(textField);
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            textField.selectAll();
        }
          
        @Override
        public void cancelEdit() {
            super.cancelEdit();
              
            setText(String.valueOf(getItem()));
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        }
          
        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
              
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString());
                    }
                    setGraphic(textField);
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                } else {
                    setText(getString());
                    setContentDisplay(ContentDisplay.TEXT_ONLY);
                }
            }
        }
          
        private void createTextField() {
            textField = new TextField(getString());
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap()*2);
            textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent t) {
                    if (t.getCode() == KeyCode.ENTER) {
                        commitEdit(textField.getText());
                    } else if (t.getCode() == KeyCode.ESCAPE) {
                        cancelEdit();
                    }
                }
            });
        }
          
        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
    }
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.presenter = new PresenterProvidersViewImpl(this);
		
		Callback<TableColumn<DataModelProvider, String>, TableCell<DataModelProvider, String>> cellFactory = (p) -> new EditingCell();
		
		colProvider.setCellValueFactory(
			new PropertyValueFactory<DataModelProvider, String>("provider")
		);
		colProvider.setCellFactory(cellFactory);
		colProvider.setOnEditCommit( event -> event.getRowValue().setProvider(event.getNewValue()) );
		
		colPhone.setCellValueFactory(
			new PropertyValueFactory<DataModelProvider, String>("phone")
		);
		colPhone.setCellFactory(cellFactory);
		colPhone.setOnEditCommit( event -> event.getRowValue().setPhone(event.getNewValue()) );
		
		colEmail.setCellValueFactory(
			new PropertyValueFactory<DataModelProvider, String>("email")
		);
		colEmail.setCellFactory(cellFactory);
		colEmail.setOnEditCommit( event -> event.getRowValue().setEmail(event.getNewValue()) );
		
		colAddress.setCellValueFactory(
			new PropertyValueFactory<DataModelProvider, String>("address")
		);
		colAddress.setCellFactory(cellFactory);
		colAddress.setOnEditCommit( event -> event.getRowValue().setAddress(event.getNewValue()) );
		
		colCity.setCellValueFactory(
			new PropertyValueFactory<DataModelProvider, String>("city")
		);
		colCity.setCellFactory(cellFactory);
		colCity.setOnEditCommit( event -> event.getRowValue().setCity(event.getNewValue()) );
		
		colComments.setCellValueFactory(
			new PropertyValueFactory<DataModelProvider, String>("comments")
		);
		colComments.setCellFactory(cellFactory);
		colComments.setOnEditCommit( event -> event.getRowValue().setComments(event.getNewValue()) );
		
		colEdit.setCellValueFactory( (p) -> new SimpleObjectProperty<DataModelProvider>(p.getValue()));
		colEdit.setCellFactory( (p) -> new ButtonCell());
		
		configFiltered();
		
		presenter.getProviders();
	}
	
	private void configFiltered() {
		FilteredList<DataModelProvider> filteredData = new FilteredList<>(data, p -> true);
		
        txtFiltered.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(dataModel -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                
                String lowerCaseFilter = newValue.toLowerCase();
                if (dataModel.getProvider().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } 
                return false;
            });
        });
        
        SortedList<DataModelProvider> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tbProviders.comparatorProperty());
        tbProviders.setItems(sortedData);
	}
	
	@Override
	public void loadProviders(ContentValues[] providers) {
		Platform.runLater(
			() -> {
				for(ContentValues provider: providers) {
					DataModelProvider dataModel = new DataModelProvider(provider);
					data.add(dataModel);
				}
			}
		);
	}
	
	@Override
	public void update(ContentValues provider) {
		Platform.runLater(
			() -> {
				DataModelProvider dataModel = new DataModelProvider(provider);
				data.add(dataModel);
			}
		);
	}
	
	@Override
	public void remove(String id) {
		Platform.runLater(
			() -> {
				data.removeIf((model) -> model.getId().equals(id));
			}
		);
	}
	
	@FXML
	void onSave(ActionEvent event) {
		if(!txtNameProvider.getText().equals("") && 
				!txtPhone.getText().equals("") && 
				!txtEmail.getText().equals("")) {
		
			ContentValues provider = ContentValues.newInstanceEmpy("provider");
			provider.put("_id", UUID.randomUUID().toString());
			provider.put("name", txtNameProvider.getText());
			provider.put("phone", txtPhone.getText());
			provider.put("email", txtEmail.getText());
			provider.put("address", txtAddress.getText());
			provider.put("city", txtCity.getText());
			provider.put("observations", txtComments.getText());
		
			presenter.saveProvider(provider);
			
		} else {
			showMessage("Empty fields", "Minimum fields required are empty");
		}
	}
	
	@FXML
	void onPrint(ActionEvent event) {
		presenter.exportProviders();
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
				lblLoadSave.setImage(load);
			}
		);
	}

	@Override
	public void hiddenRegisterProgress() {
		Platform.runLater(
			() -> {
				btnSave.setDisable(false);
				lblLoadSave.setImage(null);
			}
		);
	}
	
	@Override
	public void showLoadProgress() {
		Platform.runLater(
			() -> {
				txtFiltered.setDisable(true);
				lblLoadProvider.setImage(load);
			}
		);
	}

	@Override
	public void hiddenloadProgress() {
		Platform.runLater(
			() -> {
				txtFiltered.setDisable(false);
				lblLoadProvider.setImage(null);
			}
		);
	}

	@FXML
	void onReset(ActionEvent event) {
		clearForm();
	}
	
	@Override
	public void clearForm() {
		Platform.runLater(
			() -> {
				txtNameProvider.setText("");
				txtPhone.setText("");
				txtEmail.setText("");
				txtAddress.setText("");
				txtCity.setText("");
				txtComments.setText("");
			}
		);
	}
	
	@FXML
    void toBackStackPane(MouseEvent event) {
		stackPane.toBack();
    }

	@FXML
	void onCloseStage(ActionEvent event) {
		((Stage) root.getScene().getWindow()).close();
		primary.updateStage(this, "close");
	}
	
	@Override
	public void setPrimaryStage(PrimaryStage primary) {
		this.primary = primary;
	}
	
	@Override
	public String getName() {
		return "ProvidersView";
	}

	@Override
	public PresenterProvidersView<ContentValues> getPresenter() {
		return presenter;
	}
	
}
