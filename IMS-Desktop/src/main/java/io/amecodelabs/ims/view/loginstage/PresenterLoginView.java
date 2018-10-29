package io.amecodelabs.ims.view.loginstage;

public interface PresenterLoginView {
	void authenticate(String email, String password, boolean remember);
	LoginView getLoginview();
}
