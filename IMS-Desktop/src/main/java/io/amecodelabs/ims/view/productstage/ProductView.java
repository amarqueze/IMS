package io.amecodelabs.ims.view.productstage;

import io.amecodelabs.ims.view.base.AbstractView;
import io.amecodelabs.ims.view.base.SubStage;

public interface ProductView<T> extends AbstractView, SubStage {
	void loadProviders(T[] providers);
	void loadCategories(T[] categories);
	void showMessage(String title, String text);
	void clearForm();
	void showRegisterProgress();
	void hiddenRegisterProgress();
	PresenterProductView<T> getPresenter();
}
