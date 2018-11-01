package io.amecodelabs.ims.view.providerstage;

import io.amecodelabs.ims.view.base.AbstractView;
import io.amecodelabs.ims.view.base.SubStage;

public interface ProvidersView<T> extends AbstractView, SubStage {
	void loadProviders(T[] providers);
	void update(T provider);
	void remove(String id);
	void clearForm();
	void showMessage(String title, String text);
	void showRegisterProgress();
	void hiddenRegisterProgress();
	void showLoadProgress();
	void hiddenloadProgress();
	PresenterProvidersView<T> getPresenter();
}
