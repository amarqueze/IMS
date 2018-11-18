package io.amecodelabs.ims.view.statstage;

public interface PresenterStatsView<T> {
	void getStockProducts(int year);
	void getProviders();
	StatsView<T> getStatsView();
}
