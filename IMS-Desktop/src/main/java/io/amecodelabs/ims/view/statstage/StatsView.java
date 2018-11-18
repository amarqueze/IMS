package io.amecodelabs.ims.view.statstage;

import io.amecodelabs.ims.view.base.AbstractView;
import io.amecodelabs.ims.view.base.SubStage;

public interface StatsView<T> extends AbstractView, SubStage {
	void showMessage(String title, String text);
	void loadStockProducts(T[] stock);
	void loadProviders(T[] providers);
	PresenterStatsView<T> getPresenter();
}
