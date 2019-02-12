package io.amecodelabs.ims.view.dashboardstage;

import io.amecodelabs.ims.view.base.AbstractView;
import io.amecodelabs.ims.view.base.SubStage;

public interface DashBoardView<T> extends AbstractView, SubStage {
	void loadStockProducts(T[] stock, String year);
	void showLoadProgressGraphics();
	void hiddenLoadProgressGraphics();
	
	void loadProducts(T[] products);
	
	void showMessage(String title, String text);
	
	void showNumberProviders(String numberProviders);
	void showLoadProgressProviders();
	void hiddenLoadProgressProviders();
	
	void showNumberProducts(String numberProducts);
	void showLoadProgressProducts();
	void hiddenLoadProgressProducts();
	
	void showNumberTransactions(String numberTransactions);
	void showLoadProgressTransactions();
	void hiddenLoadProgressTransactions();
	
	void showNumberUsers(String numberUsers);
	void showLoadProgressUsers();
	void hiddenLoadProgressUsers();
	
	PresenterDashBoard<T> getPresenterDashBoard();
}
