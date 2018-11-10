package io.amecodelabs.ims.view.addproduct;

public interface PresenterAddProductView<T> {
	void saveProduct(T product);
	void getProviders();
	void getCategories();
	AddProductView<T> getProductView();
}
