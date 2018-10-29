package io.amecodelabs.ims.view.mainstage;

import io.amecodelabs.ims.view.context.Session;

public class PresenterMainViewImpl implements PresenterMainView {
	private MainView mainView;
	
	public PresenterMainViewImpl(MainView mainView) {
		this.mainView = mainView;
		getUserEmailCurrent();
	}
	
	@Override
	public void getUserEmailCurrent() {
		this.mainView.setUserEmailCurrent(Session.getSession().getEmail());
	}

	@Override
	public MainView getMainView() {
		return mainView;
	}

}
