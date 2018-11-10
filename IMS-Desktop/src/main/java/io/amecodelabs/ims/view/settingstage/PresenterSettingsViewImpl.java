package io.amecodelabs.ims.view.settingstage;

import java.util.Locale;

import io.amecodelabs.ims.view.context.Session;

public class PresenterSettingsViewImpl implements PresenterSettingsView {
	private SettingsView settingview;
	
	public PresenterSettingsViewImpl(SettingsView settingview) {
		this.settingview = settingview;
	}
	
	@Override
	public void setLanguage(Locale locale) {
		// implement
	}

	@Override
	public void deleteSession() {
		Session.deleteLocalSession();
	}

	@Override
	public SettingsView getView() {
		return settingview;
	}

}
