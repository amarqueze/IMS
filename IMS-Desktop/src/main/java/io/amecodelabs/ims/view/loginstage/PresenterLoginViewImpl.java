package io.amecodelabs.ims.view.loginstage;

import io.amecodelabs.ims.models.utils.ContentValues;
import io.amecodelabs.ims.models.utils.JSONExportException;
import io.amecodelabs.ims.models.utils.JSONImportException;
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
		
		try {
			this.loginView.showProgress();
			authService.authenticate(user);
		} catch (JSONExportException e) {
			e.printStackTrace();
		}
	}

	protected void onSuccess(String response) {
		this.loginView.hiddenProgress();
		try {
			ContentValues responseJson = ContentValues.newInstanceOfImportJSON("response", response);
			if(responseJson.getValueInteger("ok") == 0) 
				this.loginView.setErrorMessage(responseJson.getValueString("message"));
			else {
				createSession(responseJson.getValueString("token"));
				this.loginView.invokeMainView();
			}
		} catch (JSONImportException e) {
			e.printStackTrace();
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
