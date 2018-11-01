package io.amecodelabs.ims.view.settingstage;

import java.util.Locale;

import io.amecodelabs.ims.view.context.Session;

public class PresenterSettingImpl implements PresenterSetting {
	private SettingView settingview;
	
	public PresenterSettingImpl(SettingView settingview) {
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
	public SettingView getView() {
		return settingview;
	}

}
