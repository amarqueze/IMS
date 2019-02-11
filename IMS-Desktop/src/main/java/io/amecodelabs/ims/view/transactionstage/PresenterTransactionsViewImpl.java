package io.amecodelabs.ims.view.transactionstage;

import io.amecodelabs.ims.models.utils.ContentValues;
import io.amecodelabs.ims.service.StockService;

public class PresenterTransactionsViewImpl implements PresenterTransactionsView<ContentValues> {
	TransactionsView<ContentValues> transactionsView;
	
	public PresenterTransactionsViewImpl(TransactionsView<ContentValues> transactionsView) {
		this.transactionsView = transactionsView;
	}

	@Override
	public void getLoadStock(int skip) {
		transactionsView.showProgress();
		StockService.getStockbyInputs(skip, 
			(res) -> {
				transactionsView.hiddenProgress();
				if(res.getValueInteger("ok") == 1) {
					transactionsView.loadStock(res.getArrayContentValues("response"));
				} else {
					transactionsView.showMessage("Error", "Transactions have not been downloaded");
				}
			},
			(err) -> {
				transactionsView.hiddenProgress();
				transactionsView.showMessage("Error", "Connect failed or interrupted");
			}
		);
	}

	@Override
	public TransactionsView<ContentValues> getTransactionView() {
		return transactionsView;
	}
	
}
