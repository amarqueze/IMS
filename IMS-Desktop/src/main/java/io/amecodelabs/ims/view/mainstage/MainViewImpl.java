package io.amecodelabs.ims.view.mainstage;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import io.amecodelabs.ims.view.base.SubStage;
import io.amecodelabs.ims.view.context.ApplicationContext;
import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainViewImpl implements MainView, Initializable {
	@FXML
    private VBox root;
    @FXML
    private Label txtUser;
    @FXML
    private JFXButton btnTransactions;
    @FXML
    private JFXButton btnSettings;
    @FXML
    private JFXButton btnHelp;
    @FXML
    private JFXButton btnStats;
    @FXML
    private JFXButton btnProducts;
    @FXML
    private JFXButton btnUsers;
    @FXML
    private JFXButton btnProviders;
    
    private PresenterMainView presenter;
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
		presenter = new PresenterMainViewImpl(this);
	}
	
	@Override
	public void setUserEmailCurrent(String email) {
		txtUser.setText(email);
	}
	
	@FXML
    void onCloseStage(MouseEvent event) {
		System.exit(0);
    }

    @FXML
    void onMinimizeStage(MouseEvent event) {
    	((Stage) root.getScene().getWindow()).setIconified(true);
    }

    @FXML
    void showHelpView(ActionEvent event) {
    	HostServices services = (HostServices) ApplicationContext.getInstance().getAttribute("host-services");
    	services.showDocument("https://github.com/Maxfaider");
    }

    @FXML
    void showProductsView(ActionEvent event) {
    	
    }

    @FXML
    void showProvidersView(ActionEvent event) {
    	
    }

    @FXML
    void showSettingsView(ActionEvent event) {
    	
    }

    @FXML
    void showStatsView(ActionEvent event) {
    	
    }

    @FXML
    void showTransactionsView(ActionEvent event) {
    	
    }

    @FXML
    void showUsersView(ActionEvent event) {
    	
    }
    
    @Override
	public void updateStage(SubStage subStage, Object info) {
    	
	};
	
	@Override
	public PresenterMainView getPresenterView() {
		return presenter;
	}
	
	/* Active & disable Button */

	public void disableBtnTransactions() {
		btnTransactions.setDisable(true);
	}

	public void activeBtnTransactions() {
		btnTransactions.setDisable(false);
	}

	public void disableBtnSettings() {
		btnSettings.setDisable(true);
	}

	public void activeBtnSettings() {
		btnSettings.setDisable(false);
	}

	public void disableBtnStats() {
		btnStats.setDisable(true);
	}

	public void activeBtnStats() {
		btnStats.setDisable(false);
	}

	public void disableBtnProducts() {
		btnProducts.setDisable(true);
	}

	public void activeBtnProducts() {
		btnProducts.setDisable(false);
	}

	public void disableBtnUsers() {
		btnUsers.setDisable(true);
	}

	public void activeBtnUsers() {
		btnUsers.setDisable(false);
	}

	public void disableBtnProviders() {
		btnProviders.setDisable(true);
	}

	public void activeBtnProviders() {
		btnProviders.setDisable(false);
	}
	
}
