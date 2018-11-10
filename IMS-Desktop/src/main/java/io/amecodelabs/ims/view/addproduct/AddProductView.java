package io.amecodelabs.ims.view.addproduct;

import io.amecodelabs.ims.view.base.AbstractView;
import io.amecodelabs.ims.view.base.SubStage;

public interface AddProductView<T> extends AbstractView, SubStage {
	void loadProviders(T[] providers);
	void loadCategories(T[] categories);
	void updateProduct(T product);
	void showMessage(String title, String text);
	void clearForm();
	void showRegisterProgress();
	void hiddenRegisterProgress();
	PresenterAddProductView<T> getPresenter();
}
