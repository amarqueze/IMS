package io.amecodelabs.ims.view.productstage;

import io.amecodelabs.ims.view.base.AbstractView;
import io.amecodelabs.ims.view.base.PrimaryStage;
import io.amecodelabs.ims.view.base.SubStage;

public interface ProductsView<T> extends AbstractView, PrimaryStage, SubStage {
	void loadProducts(T[] products);
	void showMessage(String title, String text);
	void showProgress();
	void hiddenProgress();
	PresenterProductsView<T> getPresenter(); 
}
