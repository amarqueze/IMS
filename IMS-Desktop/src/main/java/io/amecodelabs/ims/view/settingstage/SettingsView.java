package io.amecodelabs.ims.view.settingstage;

import io.amecodelabs.ims.view.base.AbstractView;
import io.amecodelabs.ims.view.base.SubStage;

public interface SettingsView extends AbstractView, SubStage {
	void setMessage(String title, String content);
	PresenterSettingsView getPresenter();
}
