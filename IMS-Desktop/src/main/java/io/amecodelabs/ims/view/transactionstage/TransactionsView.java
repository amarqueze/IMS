package io.amecodelabs.ims.view.transactionstage;

import io.amecodelabs.ims.view.base.AbstractView;
import io.amecodelabs.ims.view.base.SubStage;

public interface TransactionsView<T> extends AbstractView, SubStage {
	void loadStock(T[] stock);
	void showMessage(String title, String text);
	void showProgress();
	void hiddenProgress();
	PresenterTransactionsView<T> getPresenter();
}
