package io.amecodelabs.ims.view.dashboardstage;

import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.Map;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;

import io.amecodelabs.ims.models.utils.ContentValues;
import io.amecodelabs.ims.view.base.PrimaryStage;
import io.amecodelabs.ims.view.base.Window;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
    private TextField txtYear;
    @FXML
    private TextField txtYearComparable;
	@FXML
	private ComboBox<String> cbSuppliers;
	@FXML
	private BarChart<String, Number> chartBarProduct;
	@FXML
	private LineChart<String, Number> chartPurchased;
	
	private Map<String, Object> params;

	private PrimaryStage primary;
	private PresenterDashBoard<ContentValues> presenter;
	private String suppliesSelected = "None";
	private String yearSelected = "";
	private String yearComparableSelected = "";
	private Image load = new Image("/images/load.gif", true);
	private boolean isActiveMessage;
	DataModelStats[] dataModelStats;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		presenter = new PresenterDashBoardImpl(this);
		
		ChangeListener<String> listener = (observable, oldValue, newValue) -> {
			if (!newValue.matches("\\d*")) {
				StringProperty field = (StringProperty) observable;
				field.setValue(oldValue);
	        }
		};
		txtYear.textProperty().addListener(listener);
		txtYearComparable.textProperty().addListener(listener);
		
		presenter.getNumberProviders();
		presenter.getNumberProducts();
		presenter.getNumberTransactions();
		presenter.getNumberUsers();
		presenter.getProducts();
		
		configCharts();
	}
	
	private void configCharts() {
		Month[] months = Month.values();
		dataModelStats = new DataModelStats[12];
		for(int i=0; i<months.length; i++)
			dataModelStats[i] = new DataModelStats(months[i].toString());
			
        chartBarProduct.setTitle("Summary of Supply - I/O");
        chartBarProduct.getXAxis().setAnimated(false);
        
        chartPurchased.setTitle("Purchased Supplies Per Month");
        chartPurchased.getXAxis().setAnimated(false);
        
        chartConsumed.setTitle("Supplies consumption During One Year");
        chartConsumed.getXAxis().setAnimated(false);
	}
	
	@FXML
    void onSuppliesChanged(ActionEvent event) {
		
    }
	
	@FXML
    void onEnter(KeyEvent event) {
		if(event.getCode() == KeyCode.ENTER) {
			suppliesSelected = cbSuppliers.getValue();
			yearSelected = txtYear.getText();
			boolean isUpdateCharts = false;
			
			if( yearSelected.length() > 3  && !suppliesSelected.equals("None") ) {
				clearStats();
				presenter.getStockProduct(suppliesSelected, yearSelected);
				isUpdateCharts = true;
			} 
			
			String yearComparableSelectedbefore = yearComparableSelected;
			yearComparableSelected = txtYearComparable.getText();
			
			if( ( yearComparableSelected.length() > 3 && !yearComparableSelected.equals(yearComparableSelectedbefore) 
					&& !yearComparableSelected.equals(yearSelected) ) 
					|| ( isUpdateCharts && yearComparableSelected.length() > 3 && !yearComparableSelected.equals(yearSelected) ) ) {
				
				presenter.getStockProduct(suppliesSelected, yearComparableSelected);
			}
		}
    }

	private void clearStats() {
		chartConsumed.getData().clear();
		chartPurchased.getData().clear();
		chartBarProduct.getData().clear();
	}

	@Override
	public void loadStockProducts(ContentValues[] stock, String year) {
		Platform.runLater(() -> {
			if(stock.length > 0) {
				int summaryInputs = 0;
				int summaryOutputs = 0;
				
				for(var dataModel: dataModelStats)
					dataModel.clear();
				
				XYChart.Series<String, Number> seriesLine = new XYChart.Series<>(); 
				seriesLine.setName(year);
				chartPurchased.getData().add(seriesLine);
				
				XYChart.Series<String, Number> seriesArea = new XYChart.Series<>();
				seriesArea.setName(year);
				chartConsumed.getData().add(seriesArea);
				for(var item: stock) {
					int posMonth = LocalDate.parse(item.getValueString("date")).getMonth().getValue();
					int quantity = item.getValueInteger("quantity");
					if(item.getValueString("type").equals("input")) {
						dataModelStats[posMonth - 1].plus(quantity);
						summaryInputs += quantity;
					} else {
						dataModelStats[posMonth - 1].minus(quantity);
						summaryOutputs += quantity;
					}
				}
				
				for(var stats: dataModelStats) {
					seriesArea.getData().add(new XYChart.Data<String, Number>(stats.getMonth(), stats.getValueOutput()));
					seriesLine.getData().add(new XYChart.Data<String, Number>(stats.getMonth(), stats.getValueInput()));
				}
				
				XYChart.Series<String, Number> seriesBar = new XYChart.Series<>();
				seriesBar.setName(year);
				chartBarProduct.getData().add(seriesBar);
				seriesBar.getData().add(new XYChart.Data<String, Number>("Product Incomes", summaryInputs));
		        seriesBar.getData().add(new XYChart.Data<String, Number>("Outputs of Product", summaryOutputs));
			} else 
				showMessage("Stock empty", "No results found " + year);
		});
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
	public void setPrimaryStage(PrimaryStage primary) {
		this.primary = primary;
	}
	
	@Override
	public void setParams(Map<String, Object> params) {
		this.params = params;
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
				if(!isActiveMessage) {
					isActiveMessage = true;
				JFXDialogLayout content = new JFXDialogLayout();
				content.setHeading(new Text(title));
				content.setBody(new Text(text));
				stackPane.toFront();
				JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
					
				JFXButton btnDone = new JFXButton("Done");
				btnDone.setOnAction((event) ->  {
					dialog.close();
					isActiveMessage = false;
					stackPane.toBack();
				});
				content.setActions(btnDone);
					
				dialog.show();
				}
			}
		);
	}
	
	@FXML
    void toBackStackPane(MouseEvent event) {
		isActiveMessage = false;
		stackPane.toBack();
    }

	@FXML
	void onClose(MouseEvent event) {
		((Stage) root.getScene().getWindow()).close();
		if(primary != null) primary.updateStage(this, "close");
	}
	
	public void showWaitCursor() {
		Window window = (Window) params.get("window");
		window.getStage().getScene().setCursor(Cursor.WAIT);
	}
	
	public void hiddenWaitCursor() {
		Window window = (Window) params.get("window");
		window.getStage().getScene().setCursor(Cursor.DEFAULT);
	}
	
	@Override
	public void showLoadProgressGraphics() {
		Platform.runLater(() -> showWaitCursor());
	}

	@Override
	public void hiddenLoadProgressGraphics() {
		Platform.runLater(() -> hiddenWaitCursor());
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
