package io.amecodelabs.ims.view.userstage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;

import io.amecodelabs.ims.models.utils.ContentValues;
import io.amecodelabs.ims.models.utils.Digest;
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
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;
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

public class UsersViewImpl implements UsersView<ContentValues>, Initializable {
	/* JavaFx Components */
	@FXML
    private AnchorPane root;
	@FXML
    private StackPane stackPane;
    @FXML
    private TreeView<String> tvPrivileges;
    @FXML
    private Button btnClose;
    @FXML
    private Button btnNewUser;
    @FXML
    private Button btnReset;
    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtLastName;
    @FXML
    private TextField txtDNI;
    @FXML
    private TextField txtPhone;
    @FXML
    private TextField txtAddress;
    @FXML
    private TableView<DataModelUsers> tbUsers;
    @FXML
    private TableColumn<DataModelUsers, String> colFirstName;
    @FXML
    private TableColumn<DataModelUsers, String> colLastName;
    @FXML
    private TableColumn<DataModelUsers, String> colEmail;
    @FXML
    private TableColumn<DataModelUsers, String> colDNI;
    @FXML
    private TableColumn<DataModelUsers, String> colPhone;
    @FXML
    private TableColumn<DataModelUsers, String> colAddress;
    @FXML
    private TableColumn<DataModelUsers, DataModelUsers> colEdit;
    @FXML
    private ImageView lblLoad;
    /* Fin JavaFX Components */
    
    private Image load = new Image("/images/load.gif", true);
    private final Node rootIcon =  new ImageView(new Image("/images/key.png", true));
    private final Node userIcon =  new ImageView(new Image("/images/group-profile-users.png", true));
    private final Node providerIcon =  new ImageView(new Image("/images/delivery-truck.png", true));
    private final Node productIcon =  new ImageView(new Image("/images/product.png", true));
    
    private final ObservableList<DataModelUsers> data = FXCollections.observableArrayList();
    private PresenterUsersView<ContentValues> presenter;
    private boolean userPrivileges;
    private boolean providerPrivileges;
    private boolean productPrivileges;
    
    private class ButtonCell extends TableCell<DataModelUsers, DataModelUsers> {
		Group group = new Group();
        final Button btnEdit = new Button("Edit");
        final Button btnDelete = new Button("Delete");
         
        public ButtonCell(){
        	btnEdit.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                	ContentValues userRoot = ContentValues.newInstanceEmpy("user");
                	userRoot.put("_id", getItem().getId());
                	userRoot.put("email", getItem().getEmail());
                	userRoot.put("firstname", getItem().getFirstName());
                	userRoot.put("lastname", getItem().getLastName());
                	userRoot.put("dni", getItem().getDni());
                	userRoot.put("phone", getItem().getPhone());
                	userRoot.put("address", getItem().getAddress());
                	
                	presenter.editUser(userRoot);
                }
            });
        	btnDelete.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                	JFXDialogLayout content = new JFXDialogLayout();
    				content.setHeading(new Text("Delete"));
    				content.setBody(new Text("¿Do you want to delete this User?"));
    				stackPane.toFront();
    				JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.TOP);
    				
    				JFXButton btnDone = new JFXButton("Sure");
    				btnDone.setOnAction((event) ->  {
    					dialog.close();
    					stackPane.toBack();
    					presenter.deleteUser(getItem().getId());
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
        protected void updateItem(DataModelUsers t, boolean empty) {
        	super.updateItem(t, empty);
            if(!empty && t != null) {
                setGraphic(group);
            } else {
            	setText(null);
            	setGraphic(null);
            }
        }
    }
    
    private class EditingCell extends TableCell<DataModelUsers, String> {
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
    	presenter = new PresenterUsersViewImpl(this);
    	
    	Callback<TableColumn<DataModelUsers, String>, TableCell<DataModelUsers, String>> cellFactory = (p) -> new EditingCell();
		
    	colFirstName.setCellValueFactory(
			new PropertyValueFactory<DataModelUsers, String>("firstName")
		);
    	colFirstName.setCellFactory(cellFactory);
    	colFirstName.setOnEditCommit( event -> event.getRowValue().setFirstName(event.getNewValue()) );
		
    	colLastName.setCellValueFactory(
			new PropertyValueFactory<DataModelUsers, String>("lastName")
		);
    	colLastName.setCellFactory(cellFactory);
    	colLastName.setOnEditCommit( event -> event.getRowValue().setLastName(event.getNewValue()) );
		
		colEmail.setCellValueFactory(
			new PropertyValueFactory<DataModelUsers, String>("email")
		);
		colEmail.setCellFactory(cellFactory);
		colEmail.setOnEditCommit( event -> event.getRowValue().setEmail(event.getNewValue()) );
		
		colAddress.setCellValueFactory(
			new PropertyValueFactory<DataModelUsers, String>("address")
		);
		colAddress.setCellFactory(cellFactory);
		colAddress.setOnEditCommit( event -> event.getRowValue().setAddress(event.getNewValue()) );
		
		colDNI.setCellValueFactory(
			new PropertyValueFactory<DataModelUsers, String>("dni")
		);
		colDNI.setCellFactory(cellFactory);
		colDNI.setOnEditCommit( event -> event.getRowValue().setDni(event.getNewValue()) );
		
		colPhone.setCellValueFactory(
			new PropertyValueFactory<DataModelUsers, String>("phone")
		);
		colPhone.setCellFactory(cellFactory);
		colPhone.setOnEditCommit( event -> event.getRowValue().setPhone(event.getNewValue()) );
		
		colEdit.setCellValueFactory( (p) -> new SimpleObjectProperty<DataModelUsers>(p.getValue()));
		colEdit.setCellFactory( (p) -> new ButtonCell());
		
		tbUsers.setItems(data);
		
		configPrivileges();
		
		presenter.getUsers();
	}
    
    private void configPrivileges() {
    	TreeItem<String> rootItem = new CheckBoxTreeItem<>("All privileges", rootIcon);
        rootItem.setExpanded(true);
        
        tvPrivileges.setCellFactory(CheckBoxTreeCell.forTreeView());
        
        CheckBoxTreeItem<String> userPrivilege = new CheckBoxTreeItem<>("Create, Edit, Delete Users", userIcon);
        userPrivilege.selectedProperty().addListener((obs, oldVal, newVal) -> {
        	this.userPrivileges = newVal;
        });
        
        CheckBoxTreeItem<String> providerPrivilege = new CheckBoxTreeItem<>("Create, Edit, Delete Providers", providerIcon);
        providerPrivilege.selectedProperty().addListener((obs, oldVal, newVal) -> {
        	this.providerPrivileges = newVal;
        });
        
        CheckBoxTreeItem<String> productPrivilege = new CheckBoxTreeItem<>("Create, Edit, Delete Products", productIcon);
        productPrivilege.selectedProperty().addListener((obs, oldVal, newVal) -> {
        	this.productPrivileges = newVal;
        });
        
        rootItem.getChildren().addAll(userPrivilege, providerPrivilege, productPrivilege);
        tvPrivileges.setRoot(rootItem);
    }
     
    @FXML
    void onNewUser(ActionEvent event) {
    	if(!txtEmail.getText().equals("") && 
    			!txtPassword.getText().equals("") &&
    			!txtFirstName.getText().equals("") &&
    			!txtLastName.getText().equals("")) {
    		
    		ContentValues userRoot = ContentValues.newInstanceEmpy("");
    		userRoot.put("_id", UUID.randomUUID().toString());
    		userRoot.put("email", txtEmail.getText());
    		userRoot.put("password", Digest.encrypt(txtPassword.getText()));
    		userRoot.put("firstname", txtFirstName.getText());
    		userRoot.put("lastname", txtLastName.getText());
    		userRoot.put("dni", txtDNI.getText());
    		userRoot.put("phone", txtPhone.getText());
    		userRoot.put("address", txtAddress.getText());
    		
    		ContentValues privileges = ContentValues.newInstanceEmpy("privileges");
    		ContentValues userP = ContentValues.newInstanceEmpy("user");
    		userP.put("create", userPrivileges);
    		userP.put("edit", userPrivileges);
    		userP.put("delete", userPrivileges);
    		
    		ContentValues productP = ContentValues.newInstanceEmpy("product");
    		productP.put("create", productPrivileges);
    		productP.put("edit", productPrivileges);
    		productP.put("delete", productPrivileges);
    		
    		ContentValues providerP = ContentValues.newInstanceEmpy("provider");
    		providerP.put("create", providerPrivileges);
    		providerP.put("edit", providerPrivileges);
    		providerP.put("delete", providerPrivileges);
    		
    		privileges.put(userP);
    		privileges.put(productP);
    		privileges.put(providerP);
    		userRoot.put(privileges);
    		
    		presenter.addUser(userRoot);
    	} else {
    		showMessage("Empty fields", "Minimum fields required are empty");
    	}
    }
    
    @Override
   	public void loadUsers(ContentValues[] users) {
       	Platform.runLater(
       		() -> {
       			for(ContentValues user: users) {
       				DataModelUsers dataModel = new DataModelUsers(user);
       				data.add(dataModel);
       			}
       		}
       	);
   	}

	@Override
	public void update(ContentValues user) {
		Platform.runLater(
	       	() -> {
	       		DataModelUsers dataModel = new DataModelUsers(user);
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
    void onReset(ActionEvent event) {
		clearForm();
    }

	@Override
	public void clearForm() {
		txtEmail.setText("");
		txtPassword.setText("");
		txtFirstName.setText("");
		txtLastName.setText("");
		txtDNI.setText("");
		txtAddress.setText("");
		txtPhone.setText("");
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
	public void showLoadProgress() {
		Platform.runLater(
			() -> {
				btnNewUser.setDisable(true);
				lblLoad.setImage(load);
			}
		);
	}

	@Override
	public void hiddenloadProgress() {
		Platform.runLater(
			() -> {
				btnNewUser.setDisable(false);
				lblLoad.setImage(null);
			}
		);
	}
	
	@FXML
    void onCloseStackPane(MouseEvent event) {
		stackPane.toBack();
    }

    @FXML
    void onCloseStage(ActionEvent event) {
    	((Stage) root.getScene().getWindow()).close();
    }
	
	@Override
	public String getName() {
		return "UsersView";
	}

	@Override
	public PresenterUsersView<ContentValues> getPresenter() {
		return presenter;
	}

}
