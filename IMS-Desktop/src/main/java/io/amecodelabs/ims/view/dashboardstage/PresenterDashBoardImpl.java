package io.amecodelabs.ims.view.dashboardstage;

import io.amecodelabs.ims.models.utils.ContentValues;
import io.amecodelabs.ims.service.ProductService;
import io.amecodelabs.ims.service.ProviderService;
import io.amecodelabs.ims.service.StockService;
import io.amecodelabs.ims.service.UserService;

public class PresenterDashBoardImpl implements PresenterDashBoard<ContentValues> {
	private DashBoardView<ContentValues> dashBoardView;
	
	public PresenterDashBoardImpl(DashBoardView<ContentValues> dashBoardView) {
		this.dashBoardView = dashBoardView;
	}

	@Override
	public void getStockProduct(String description, String year) {
		dashBoardView.showLoadProgressGraphics();
		StockService.getStockProducts(description, year, (res) -> {
			dashBoardView.hiddenLoadProgressGraphics();
				if(res.getValueInteger("ok") == 1) {
					this.dashBoardView.loadStockProducts(res.getArrayContentValues("response"), year);
				} else {
					this.dashBoardView.showMessage("Error", "Stock have not been downloaded");
				}
			},
			(err) ->  {
				dashBoardView.hiddenLoadProgressGraphics();
				this.dashBoardView.showMessage("Error", "Connect failed or interrupted");
			}
		);
	}
	
	@Override
	public void getProducts() {
		ProductService.findAll((res) -> {
				if(res.getValueInteger("ok") == 1) {
					this.dashBoardView.loadProducts(res.getArrayContentValues("response"));
				} else {
					this.dashBoardView.showMessage("Error", "Products have not been downloaded");
				}
			},
			(err) -> this.dashBoardView.showMessage("Error", "Connect failed or interrupted")
		);
	}

	@Override
	public void getNumberProviders() {
		dashBoardView.showLoadProgressProviders();
		ProviderService.getNumberProviders(
			(response) -> {
				dashBoardView.hiddenLoadProgressProviders();
				if(response.getValueInteger("ok") == 1) {
					dashBoardView.showNumberProviders(String.valueOf(response.getValueInteger("response")));
				} else {
					dashBoardView.showNumberProviders("0");
				}
			}, 
			(err) -> { 
				dashBoardView.hiddenLoadProgressProviders();
				dashBoardView.showNumberProviders("Not Connected");
			}
		);
	}

	@Override
	public void getNumberProducts() {
		dashBoardView.showLoadProgressProducts();
		ProductService.getNumberProducts(
			(response) -> {
				dashBoardView.hiddenLoadProgressProducts();
				if(response.getValueInteger("ok") == 1) {
					dashBoardView.showNumberProducts(String.valueOf(response.getValueInteger("response")));
				} else {
					dashBoardView.showNumberProducts("0");
				}
			}, 
			(err) ->  {
				dashBoardView.hiddenLoadProgressProducts();
				dashBoardView.showNumberProducts("Not Connected");
			}
		);
	}

	@Override
	public void getNumberTransactions() {
		dashBoardView.showLoadProgressTransactions();
		StockService.getNumberTransactions(
			(response) -> {
				dashBoardView.hiddenLoadProgressTransactions();
				if(response.getValueInteger("ok") == 1) {
					dashBoardView.showNumberTransactions(String.valueOf(response.getValueInteger("response")));
				} else {
					dashBoardView.showNumberTransactions("0");
				}
			}, 
			(err) -> {
				dashBoardView.hiddenLoadProgressTransactions();
				dashBoardView.showNumberTransactions("Not Connected");
			}
		);
	}

	@Override
	public void getNumberUsers() {
		dashBoardView.showLoadProgressUsers();
		UserService.getNumberUsers(
			(response) -> {
				dashBoardView.hiddenLoadProgressUsers();
				if(response.getValueInteger("ok") == 1) {
					dashBoardView.showNumberUsers(String.valueOf(response.getValueInteger("response")));
				} else {
					dashBoardView.showNumberUsers("0");
				}
			}, 
			(err) ->  {
				dashBoardView.hiddenLoadProgressUsers();
				dashBoardView.showNumberUsers("Not Connected");
			}
		);
	}

	@Override
	public DashBoardView<ContentValues> getDashBoardView() {
		return dashBoardView;
	}

}
