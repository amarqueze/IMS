package io.amecodelabs.ims.view.productstage;

public interface PresenterProductsView<T> {
	void getLoadProducts(int skip);
	ProductsView<T> getProductView();
}
