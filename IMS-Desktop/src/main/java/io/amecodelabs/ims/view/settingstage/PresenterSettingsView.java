package io.amecodelabs.ims.view.settingstage;

import java.util.Locale;

public interface PresenterSettingsView {
	void setLanguage(Locale locale);
	void deleteSession();
	SettingsView getView();
		
}
