package io.amecodelabs.ims.view.mainstage;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import io.amecodelabs.ims.view.base.BuildWindowDirector;
import io.amecodelabs.ims.view.base.SubStage;
import io.amecodelabs.ims.view.context.ApplicationContext;
import io.amecodelabs.ims.view.context.Session;
import io.amecodelabs.ims.view.dashboardstage.DashBoardBuildable;
import io.amecodelabs.ims.view.productstage.ProductsBuildable;
import io.amecodelabs.ims.view.providerstage.ProvidersBuildable;
import io.amecodelabs.ims.view.settingstage.SettingsBuildable;
import io.amecodelabs.ims.view.transactionstage.TransactionsBuildable;
import io.amecodelabs.ims.view.userstage.UsersBuildable;
import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainViewImpl implements MainView, Initializable {
	/* JavaFx Components */
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
    /* Fin JavaFx Components */
    
    private PresenterMainView presenter;
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
		presenter = new PresenterMainViewImpl(this);
		
		BuildWindowDirector.getDirector()
			.prepare("StatsView", new DashBoardBuildable(this))
			.prepare("SettingsView", new SettingsBuildable(this))
			.prepare("TransactionsView", new TransactionsBuildable(this));
		
		disableBtnProducts();
		disableBtnProviders();
		disableBtnUsers();
		configPermits();
	}
    
    private void configPermits() {
    	Session session = Session.getSession();
    	if(session.isManagerProduct()) {
    		activeBtnProducts();
    		BuildWindowDirector.getDirector().prepare("ProductsView", new ProductsBuildable(this));
    	}
    	if(session.isManagerProviders()) {
    		activeBtnProviders();
    		BuildWindowDirector.getDirector().prepare("ProvidersView", new ProvidersBuildable(this));
    	}
    	if(session.isManagerUsers()) {
    		activeBtnUsers();
    		BuildWindowDirector.getDirector().prepare("UsersView", new UsersBuildable(this));
    	}
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
    	BuildWindowDirector.getDirector().create("ProductsView");
    	disableBtnProducts();
    }

    @FXML
    void showProvidersView(ActionEvent event) {
    	BuildWindowDirector.getDirector().create("ProvidersView");
    	disableBtnProviders();
    }

    @FXML
    void showSettingsView(ActionEvent event) {
    	BuildWindowDirector.getDirector().create("SettingsView");
    	disableBtnSettings();
    }

    @FXML
    void showStatsView(ActionEvent event) {
    	BuildWindowDirector.getDirector().create("StatsView");
    	disableBtnStats();
    }

    @FXML
    void showTransactionsView(ActionEvent event) {
    	BuildWindowDirector.getDirector().create("TransactionsView");
    	disableBtnTransactions();
    }

    @FXML
    void showUsersView(ActionEvent event) {
    	BuildWindowDirector.getDirector().create("UsersView");
    	disableBtnUsers();
    }
    
    @Override
	public void updateStage(SubStage subStage, Object newness) {
    	String msg = (String) newness;
    	if(msg.equals("close")) {
    		switch(subStage.getName()) {
    			case "StatsView":  
    				activeBtnStats();
    				break;
    			case "ProductsView": 
    				activeBtnProducts();
    				break;
    			case "UsersView": 
    				activeBtnUsers();
    				break;
    			case "ProvidersView":
    				activeBtnProviders();
    				break;
    			case "TransactionsView":
    				activeBtnTransactions();
    				break;
    			case "SettingsView":
    				activeBtnSettings();
    				break;
    		}
    	}
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
