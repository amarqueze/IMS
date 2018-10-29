package io.amecodelabs.ims.view.loginstage;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import io.amecodelabs.ims.view.base.BuildWindowDirector;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginViewImpl implements LoginView, Initializable {
	@FXML
    private AnchorPane root;
	@FXML
    private JFXPasswordField txtPassword;
    @FXML
    private JFXButton btnSignIn;
    @FXML
    private JFXTextField txtEmail;
    @FXML
    private JFXCheckBox cbRemember;
    @FXML
    private ImageView lblLoad;
    @FXML
    private Label lblMessage;
    
    private Image load = new Image("/images/load.gif", true);
    
    private StringBuffer msgError = new StringBuffer("");
    
    private PresenterLoginView presenter;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		presenter = new PresenterLoginViewImpl(this);
	}

	@Override
	public void showProgress() {
		Platform.runLater(
			() -> {
				lblLoad.setImage(load);
			}
		);
	}

	@Override
	public void hiddenProgress() {
		Platform.runLater(
			() -> {
				lblLoad.setImage(null);
			}
		);
	}

	@Override
	public void setErrorMessage(String msg) {
		Platform.runLater(
			() -> {
				if(msgError.length() == 0) msgError.append(msg);
				else msgError.append(" & " + msg);
				
				lblMessage.setText(msgError.toString());
			}
		);
	}
	
	@Override
	public void invokeMainView() {
		Platform.runLater(
			() -> {
				((Stage) root.getScene().getWindow()).close();
				BuildWindowDirector.getDirector().create("main");
			}
		);
	}
	
	public void clearMessage() {
		msgError = new StringBuffer("");
		lblMessage.setText("");
	}
	
	@FXML
    void onCloseStage(MouseEvent event) {
		System.exit(0);
    }

    @FXML
    void onSignIn(ActionEvent event) {
    	boolean isEmail = validateEmail(txtEmail.getText());
    	boolean isPassword = validatePassword(txtPassword.getText());
    	
    	if( isEmail && isPassword ) {
    		clearMessage();
    		presenter.authenticate(txtEmail.getText(), txtPassword.getText(), cbRemember.isSelected());
    	}
    }
    
    private boolean validateEmail(String email) {
    	if(email.equals("")) {
    		setErrorMessage("Email empty");
    		return false;
    	}
    	return true;
     }
    
    private boolean validatePassword(String password) {
    	if(password.equals("")) {
    		setErrorMessage("Password empty");
    		return false;
    	}
    	return true;
    }

	@Override
	public PresenterLoginView getPresenterView() {
		return presenter;
	}
}
