package io.amecodelabs.ims.view.dashboardstage;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;

import io.amecodelabs.ims.models.utils.ContentValues;
import io.amecodelabs.ims.view.base.PrimaryStage;
import io.amecodelabs.ims.view.statstage.Month;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class DashBoardViewImpl implements DashBoardView<ContentValues>, Initializable {
	/* JavaFx Components */
	@FXML
	private AnchorPane root;
	@FXML
	private StackPane stackPane;
	@FXML
	private AreaChart<String, Number> chartConsumed;
	@FXML
	private Label txtProviders;
	@FXML
	private ImageView loadProviders;
	@FXML
	private Label txtSuppliers;
	@FXML
	private ImageView loadSuppliers;
	@FXML
	private Label txtTransactions;
	@FXML
	private ImageView loadTransactions;
	@FXML
	private Label txtUsers;
	@FXML
	private ImageView loadUsers;
	@FXML
	private TextField txtYearNow;
	@FXML
	private TextField txtYearComparable;
	@FXML
	private ComboBox<String> cbSuppliers;
	@FXML
	private BarChart<String, Number> chartBarProduct;
	@FXML
	private LineChart<String, Number> chartPurchased;
	@FXML
	private ImageView loadCharts;


	private PrimaryStage primary;
	private PresenterDashBoard<ContentValues> presenter;
	private Image load = new Image("/images/load.gif", true);
	private XYChart.Series<String, Number> seriesBar;
	private XYChart.Series<String, Number> seriesLine;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		presenter = new PresenterDashBoardImpl(this);
		
		presenter.getNumberProviders();
		presenter.getNumberProducts();
		presenter.getNumberTransactions();
		presenter.getNumberUsers();
		presenter.getProducts();
		
		configCharts();
	}
	
	@SuppressWarnings("unchecked")
	private void configCharts() {
		seriesBar = new XYChart.Series<>();
        chartBarProduct.setTitle("Summary of Supply - I/O");
        chartBarProduct.getXAxis().setAnimated(false);
        chartBarProduct.getData().add(seriesBar);
        
        seriesBar.getData().add(new XYChart.Data<String, Number>("Product Incomes", 80));
        seriesBar.getData().add(new XYChart.Data<String, Number>("Outputs of Product", 30));
        
        //-----
        
        seriesLine = new XYChart.Series<>();
        chartPurchased.setTitle("Purchased Supplies Per Month");
        chartPurchased.getXAxis().setAnimated(false);
        chartPurchased.getData().add(seriesLine);
        
        for(int i=0; i<Month.values().length; i++) {
        	Month month = Month.values()[i];
        	seriesLine.getData().add(new XYChart.Data<String, Number>(month.getAcronym(), Math.random() * 1000)); 
        }
        
        //------
        chartConsumed.setTitle("Supplies consumption During One Year");
        chartConsumed.getXAxis().setAnimated(false);
        
        for(int i=2004; i<=2005; i++) {
        	XYChart.Series<String, Number> seriesArea = new XYChart.Series<>(); 
        	seriesArea.setName("" + i);
        	for(int j=0; j<Month.values().length; j++) {
        		Month month = Month.values()[j];
        		seriesArea.getData().add(new XYChart.Data<String, Number>(month.getAcronym(), Math.random() * 1000)); 
        	}
        	chartConsumed.getData().add(seriesArea);
        }
        
        
	}
	
	@FXML
    void onSuppliesChanged(ActionEvent event) {
		System.out.println((String) cbSuppliers.getValue());
    }

	@Override
	public void setPrimaryStage(PrimaryStage primary) {
		this.primary = primary;
	}

	@Override
	public void loadStockProducts(ContentValues[] stock) {
		
	}
	
	@Override
	public void loadProducts(ContentValues[] products) {
		Platform.runLater(() -> {
			cbSuppliers.setValue("None");
			
			for(var product: products) {
				cbSuppliers.getItems().add(product.getValueString("description"));
			}
		});
	}

	@Override
	public void showNumberProviders(String numberProviders) {
		Platform.runLater(() -> txtProviders.setText(numberProviders));
	}

	@Override
	public void showNumberProducts(String numberProducts) {
		Platform.runLater(() -> txtSuppliers.setText(numberProducts));
	}

	@Override
	public void showNumberTransactions(String numberTransactions) {
		Platform.runLater(() -> txtTransactions.setText(numberTransactions));
	}

	@Override
	public void showNumberUsers(String numberUsers) {
		Platform.runLater(() -> txtUsers.setText(numberUsers));
	}
	
	@Override
	public void showMessage(String title, String text) {
		Platform.runLater(() -> {
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

	@FXML
	void onClose(MouseEvent event) {
		((Stage) root.getScene().getWindow()).close();
		if(primary != null) primary.updateStage(this, "close");
	}
	
	@Override
	public void showLoadProgressGraphics() {
		Platform.runLater(() -> loadCharts.setImage(load));
	}

	@Override
	public void hiddenLoadProgressGraphics() {
		Platform.runLater(() -> loadCharts.setImage(null));
	}

	@Override
	public void showLoadProgressProviders() {
		Platform.runLater(() -> loadProviders.setImage(load));
	}

	@Override
	public void hiddenLoadProgressProviders() {
		Platform.runLater(() -> loadProviders.setImage(null));
	}

	@Override
	public void showLoadProgressProducts() {
		Platform.runLater(() -> loadSuppliers.setImage(load));
	}

	@Override
	public void hiddenLoadProgressProducts() {
		Platform.runLater(() -> loadSuppliers.setImage(null));
	}

	@Override
	public void showLoadProgressTransactions() {
		Platform.runLater(() -> loadTransactions.setImage(load));
	}

	@Override
	public void hiddenLoadProgressTransactions() {
		Platform.runLater(() -> loadTransactions.setImage(null));
	}

	@Override
	public void showLoadProgressUsers() {
		Platform.runLater(() -> loadUsers.setImage(load));
	}

	@Override
	public void hiddenLoadProgressUsers() {
		Platform.runLater(() -> loadUsers.setImage(null));
	}

	@Override
	public String getName() {
		return "DashBoardView";
	}

	@Override
	public PresenterDashBoard<ContentValues> getPresenterDashBoard() {
		return presenter;
	}

}
