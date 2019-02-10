package io.amecodelabs.ims.view.statstage;

import io.amecodelabs.ims.models.utils.ContentValues;
import io.amecodelabs.ims.service.ProviderService;
import io.amecodelabs.ims.service.StockService;

public class PresenterStatsViewImpl implements PresenterStatsView<ContentValues> {
	private StatsView<ContentValues> statsView;
	
	
	public PresenterStatsViewImpl(StatsView<ContentValues> statsView) {
		this.statsView = statsView;
	}

	@Override
	public void getStockProducts(int year) {
		StockService.getStockProducts(year,
			(response) -> {
				if(response.getValueInteger("ok") == 1) {
					this.statsView.loadStockProducts(response.getArrayContentValues("response")); 
				} else {
					this.statsView.showMessage("Error", "Results could not be extracted");
				}
			}, 
			(err) -> {
				this.statsView.showMessage("Error", "Connect failed or interrupted");
			}
		);
	}

	@Override
	public void getProviders() {
		ProviderService.getProviders(
			(response) -> {
				if(response.getValueInteger("ok") == 1)
					this.statsView.loadProviders(response.getArrayContentValues("response")); 
			}, 
			(err) -> {}
		);
	}

	@Override
	public StatsView<ContentValues> getStatsView() {
		return statsView;
	}

}
