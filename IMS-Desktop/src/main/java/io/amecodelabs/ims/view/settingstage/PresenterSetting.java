package io.amecodelabs.ims.view.settingstage;

import java.util.Locale;

public interface PresenterSetting {
	void setLanguage(Locale locale);
	void deleteSession();
	SettingView getView();
		
}
