package io.amecodelabs.ims.view.userstage;

import io.amecodelabs.ims.view.base.AbstractView;
import io.amecodelabs.ims.view.base.SubStage;

public interface UsersView<T> extends AbstractView, SubStage {
	void loadUsers(T[] users);
	void update(T user);
	void remove(String id);
	void clearForm();
	void showMessage(String title, String text);
	void showLoadProgress();
	void hiddenloadProgress();
	PresenterUsersView<T> getPresenter();
}
