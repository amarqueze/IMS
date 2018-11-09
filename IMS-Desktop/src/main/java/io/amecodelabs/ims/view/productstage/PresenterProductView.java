package io.amecodelabs.ims.view.productstage;

public interface PresenterProductView<T> {
	void saveProduct(T product);
	void getProviders();
	void getCategories();
	ProductView<T> getProductView();
}
