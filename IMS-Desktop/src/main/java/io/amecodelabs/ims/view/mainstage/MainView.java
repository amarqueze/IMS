package io.amecodelabs.ims.view.mainstage;

import io.amecodelabs.ims.view.base.AbstractView;
import io.amecodelabs.ims.view.base.PrimaryStage;

public interface MainView extends AbstractView, PrimaryStage {
	void setUserEmailCurrent(String email);
	PresenterMainView getPresenterView();
}
