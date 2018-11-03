package io.amecodelabs.ims.view.loginstage;

import io.amecodelabs.ims.models.utils.ContentValues;
import io.amecodelabs.ims.models.utils.Digest;
import io.amecodelabs.ims.service.AuthenticationService;
import io.amecodelabs.ims.view.context.Session;

public class PresenterLoginViewImpl implements PresenterLoginView {
	AuthenticationService authService;
	private LoginView loginView;
	private boolean remember;
	
	public PresenterLoginViewImpl(LoginView loginView) {
		this.loginView = loginView;
		this.authService = new AuthenticationService(this::onSuccess, this::onError);
	}
	
	@Override
	public void authenticate(String email, String password, boolean remember) {
		this.remember = remember;
		ContentValues user = ContentValues.newInstanceEmpy("user");
		user.put("email", email);
		user.put("password", Digest.encrypt(password));
		
		this.loginView.showProgress();
		authService.authenticate(user);
	}

	protected void onSuccess(ContentValues response) {
		this.loginView.hiddenProgress();
		if(response.getValueInteger("ok") == 0) {
			this.loginView.setErrorMessage(response.getValueString("message"));
		} else {
			createSession(response.getContentValues("response"), response.getValueString("token"));
			this.loginView.invokeMainView();
		}
	}
	
	private void createSession(ContentValues user, String token) {
		if(this.remember)
			Session.createNewSessionLocal(user, token);
		else 
			Session.createNewSession(user, token);
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
