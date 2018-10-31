package io.amecodelabs.ims.view.loginstage;

import io.amecodelabs.ims.models.utils.ContentValues;
import io.amecodelabs.ims.service.AuthenticationService;
import io.amecodelabs.ims.view.context.Session;

public class PresenterLoginViewImpl implements PresenterLoginView {
	AuthenticationService authService;
	private LoginView loginView;
	private boolean remember;
	private ContentValues user;
	
	public PresenterLoginViewImpl(LoginView loginView) {
		this.loginView = loginView;
		this.authService = new AuthenticationService(this::onSuccess, this::onError);
		this.user = ContentValues.newInstanceEmpy("user");
	}
	
	@Override
	public void authenticate(String email, String password, boolean remember) {
		this.remember = remember;
		user.put("email", email);
		user.put("password", password);
		
		this.loginView.showProgress();
		authService.authenticate(user);
	}

	protected void onSuccess(ContentValues response) {
		this.loginView.hiddenProgress();
		
		if(response.getValueInteger("ok") == 0) 
			this.loginView.setErrorMessage(response.getValueString("message"));
		else {
			createSession(response.getValueString("token"));
			this.loginView.invokeMainView();
		}
	}
	
	private void createSession(String token) {
		if(this.remember)
			Session.createNewSessionLocal(user.getValueString("email"), user.getValueString("password"), token);
		else 
			Session.createNewSession(user.getValueString("email"), user.getValueString("password"), token);
	}

	protected void onError(String err) {
		this.loginView.hiddenProgress();
		loginView.setErrorMessage("failed connect to server");
	}
	
	@Override
	public LoginView getLoginview() {
		return loginView;
	}

}
