package io.amecodelabs.ims.view.dashboardstage;

public interface PresenterDashBoard<T> {
	void getStockProduct(String description, int year);
	void getProducts();
	void getNumberProviders();
	void getNumberProducts();
	void getNumberTransactions();
	void getNumberUsers();
	DashBoardView<T> getDashBoardView();
}
