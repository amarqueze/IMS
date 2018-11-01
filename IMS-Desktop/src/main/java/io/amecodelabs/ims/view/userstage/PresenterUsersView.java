package io.amecodelabs.ims.view.userstage;

public interface PresenterUsersView<T> {
	void addUser(T user);
	void getUsers();
	void editUser(T user);
	void deleteUser(String id);
	UsersView<T> getView();
}
