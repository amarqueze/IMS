package io.amecodelabs.ims.view.loginstage;

import io.amecodelabs.ims.view.base.AbstractView;

public interface LoginView extends AbstractView {
	void showProgress();
	void hiddenProgress();
	void setErrorMessage(String msg);
	void invokeMainView();
	PresenterLoginView getPresenterView();
}
