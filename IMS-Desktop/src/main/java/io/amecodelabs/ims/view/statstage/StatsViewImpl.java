package io.amecodelabs.ims.view.statstage;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import io.amecodelabs.ims.models.utils.ContentValues;
import io.amecodelabs.ims.view.base.PrimaryStage;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class StatsViewImpl implements StatsView<ContentValues>, Initializable {
	/* JavaFx Components */
	@FXML
	private AnchorPane root;
	@FXML
	private FontAwesomeIcon btnClose;
	@FXML
	private StackPane stackPane;
	@FXML
	private Label txtProductsIncome;
	@FXML
	private Label txtOutputProducts;
	@FXML
	private Label txtProviders;
	@FXML
	private BarChart<String, Number> barChart;
	@FXML
	private StackedBarChart<String, Number> stackedBar;
	@FXML
    private TextField txtYear;
    @FXML
    private Button btnFind;	
	/* Fin JavaFx Components */

	private PrimaryStage primary;
	private PresenterStatsView<ContentValues> presenter;
	private DataModelStat[] dataModelStats;
	private XYChart.Series<String, Number>[] seriesBar;
	private XYChart.Series<String, Number>[] seriesStacked;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		presenter = new PresenterStatsViewImpl(this);
		
		txtYear.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\d*")) {
				StringProperty field = (StringProperty) observable;
				field.setValue(oldValue);
	        }
		});
		
        configBarChart();
        
        int yearCurrent = LocalDate.now().getYear();
        txtYear.setText(""+yearCurrent);
        barChart.setTitle("Products Summary " + yearCurrent);
        stackedBar.setTitle("Products relationship I/O " + yearCurrent);
        presenter.getProviders();
        presenter.getStockProducts(yearCurrent);
	}
	
	public void configBarChart() {
		dataModelStats = new DataModelStat[12];
		for (int i = 0; i < dataModelStats.length; i++) {
			dataModelStats[i] = new DataModelStat(Month.values()[i]);
		}
		
		seriesBar = new XYChart.Series[2];
		seriesBar[0] = new XYChart.Series<>();
		seriesBar[0].setName("Products Income");
        seriesBar[1] = new XYChart.Series<>();
        seriesBar[1].setName("Output Products");
        
        barChart.getXAxis().setAnimated(false);
        barChart.getData().add(seriesBar[0]);	
        barChart.getData().add(seriesBar[1]);
        
        seriesStacked = new XYChart.Series[2];
        seriesStacked[0] = new XYChart.Series<>();
		seriesStacked[0].setName("Products Income");
        seriesStacked[1] = new XYChart.Series<>();
        seriesStacked[1].setName("Output Products");
        
        stackedBar.getXAxis().setAnimated(false);
        stackedBar.getData().add(seriesStacked[0]);	
        stackedBar.getData().add(seriesStacked[1]);
	}
	
	@Override
	public void loadStockProducts(ContentValues[] stock) {
		Platform.runLater(() -> {
			if(stock.length > 0) {
				int summaryInputs = 0;
				int summaryOutputs = 0;
				for(ContentValues item: stock) {
					int posMonth = LocalDate.parse(item.getValueString("date")).getMonthValue();
					int quantity = item.getValueInteger("quantity");
					if(item.getValueString("type").equals("input")) {
						summaryInputs += quantity;
						dataModelStats[posMonth - 1].plus(quantity);
					} else {
						summaryOutputs += quantity;
						dataModelStats[posMonth - 1].minus(quantity);
					}
				}

				txtProductsIncome.setText(String.valueOf(summaryInputs));
				txtOutputProducts.setText(String.valueOf(summaryOutputs));
				stackedBar.getYAxis().setAnimated(true);
				for (DataModelStat dataModel : dataModelStats) {
					seriesBar[0].getData().add(new XYChart.Data<String, Number>(dataModel.getMonth().getAcronym(), dataModel.getValueInput()));
					seriesBar[1].getData().add(new XYChart.Data<String, Number>(dataModel.getMonth().getAcronym(), dataModel.getValueOutput()));
					seriesStacked[0].getData().add(new XYChart.Data<String, Number>(dataModel.getMonth().getAcronym(), dataModel.getValueInput()));
					seriesStacked[1].getData().add(new XYChart.Data<String, Number>(dataModel.getMonth().getAcronym(), dataModel.getValueOutput()));
				}
			} else showMessage("Stock empty", "No results found");
		});
	}
	
	public void clearStage() {
		stackedBar.getYAxis().setAnimated(false);
		txtOutputProducts.setText("0");
		txtProductsIncome.setText("0");
		seriesBar[0].getData().clear();
		seriesBar[1].getData().clear();
		seriesStacked[0].getData().clear();
		seriesStacked[1].getData().clear();
		for(DataModelStat dataModel: dataModelStats)
			dataModel.clear();
	}
	
	@Override
	public void loadProviders(ContentValues[] providers) {
		Platform.runLater(() -> txtProviders.setText(String.valueOf(providers.length)));
	}
	
	@FXML
	void onReportYear(ActionEvent event) {
		clearStage();
		int yearCurrent = Integer.parseInt(txtYear.getText());
        barChart.setTitle("Products Summary " + yearCurrent);
        stackedBar.setTitle("Products relationship I/O " + yearCurrent);
		presenter.getStockProducts(yearCurrent);
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
		});
	}
	
	@Override
	public void setPrimaryStage(PrimaryStage primary) {
		this.primary = primary;
	}

	@FXML
	void onCloseStackPane(MouseEvent event) {
		stackPane.toBack();
	}

	@FXML
	void onCloseStage(MouseEvent event) {
		((Stage) root.getScene().getWindow()).close();
		primary.updateStage(this, "close");
	}

	@Override
	public String getName() {
		return "StatsView";
	}

	@Override
	public PresenterStatsView<ContentValues> getPresenter() {
		return presenter;
	}

}
