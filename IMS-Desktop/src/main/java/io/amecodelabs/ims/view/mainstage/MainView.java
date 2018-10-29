package io.amecodelabs.ims.view.mainstage;

import io.amecodelabs.ims.view.base.AbstractView;

public interface MainView extends AbstractView {
	void setUserEmailCurrent(String email);
	PresenterMainView getPresenterView();
}
