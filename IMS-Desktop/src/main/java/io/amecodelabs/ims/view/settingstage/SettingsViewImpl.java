package io.amecodelabs.ims.view.settingstage;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;

import io.amecodelabs.ims.view.base.PrimaryStage;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SettingsViewImpl implements SettingsView, Initializable {
	@FXML
    private AnchorPane root;
	@FXML
    private StackPane stackPane;
    @FXML
    private ComboBox<String> cbLanguage;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnDeleteSession;
    
    private PrimaryStage primary;
    private PresenterSettingsView presenter;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		presenter = new PresenterSettingsViewImpl(this);
		
		cbLanguage.getItems().addAll("English");
		cbLanguage.setValue("English");
	}
	
	@Override
	public void setMessage(String title, String text) {
		Platform.runLater(
			() -> {
				JFXDialogLayout content = new JFXDialogLayout();
				content.setHeading(new Text(title));
				content.setBody(new Text(text));
				stackPane.toFront();
				
				JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.BOTTOM);
					
				JFXButton btnDone = new JFXButton("Sure");
				btnDone.setOnAction((event) ->  {
					presenter.setLanguage(null);
					dialog.close();
					stackPane.toBack();
					onCloseStage(null);
				});
				JFXButton btnNo = new JFXButton("No");
				btnNo.setOnAction((event) ->  {
					dialog.close();
					stackPane.toBack();
				});
				content.setActions(btnDone, btnNo);
					
				dialog.show();
			}
		);
	}
	
	@FXML
    void onCloseStackPane(MouseEvent event) {
		stackPane.toBack();
    }
	
    @FXML
    void onDeleteSession(ActionEvent event) {
    	btnDeleteSession.setDisable(true);
    	presenter.deleteSession();
    }
    
    @FXML
    void onSave(ActionEvent event) {
    	setMessage("Setting", "¿Apply changes?");
    }
    
    @FXML
    void onCloseStage(MouseEvent event) {
    	((Stage) root.getScene().getWindow()).close();
    	if(primary != null) primary.updateStage(this, "close");
    }
    
    @Override
    public void setPrimaryStage(PrimaryStage primary) {
    	this.primary = primary;
    }
    
	@Override
	public String getName() {
		return "SettingsView";
	}

	@Override
	public PresenterSettingsView getPresenter() {
		return presenter;
	}

}
