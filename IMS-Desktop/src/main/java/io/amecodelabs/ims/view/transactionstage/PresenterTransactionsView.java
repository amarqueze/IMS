package io.amecodelabs.ims.view.transactionstage;

public interface PresenterTransactionsView<T> {
	void getLoadStock(int skip);
	TransactionsView<T> getTransactionView();
}
