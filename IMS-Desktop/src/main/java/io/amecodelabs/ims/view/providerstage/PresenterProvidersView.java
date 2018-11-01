package io.amecodelabs.ims.view.providerstage;

public interface PresenterProvidersView<T> {
	void exportProviders();
	void saveProvider(T providers);
	void editProvider(T providers);
	void deleteProvider(String id);
	void getProviders();
	ProvidersView<T> getProviderView();
}
