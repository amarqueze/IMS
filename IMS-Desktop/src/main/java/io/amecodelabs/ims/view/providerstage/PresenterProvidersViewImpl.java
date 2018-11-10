package io.amecodelabs.ims.view.providerstage;

import io.amecodelabs.ims.models.utils.ContentValues;
import io.amecodelabs.ims.service.ProviderService;

public class PresenterProvidersViewImpl implements PresenterProvidersView<ContentValues> {
	private ProviderService service;
	private ProvidersView<ContentValues> providerView;
	
	public PresenterProvidersViewImpl(ProvidersView<ContentValues> providerView) {
		this.providerView = providerView;
		service = new ProviderService();
	}
	
	@Override
	public void saveProvider(ContentValues providers) {
		this.providerView.showRegisterProgress();
		service.createProvider(providers, 
			(response, document) -> {
				this.providerView.hiddenRegisterProgress();
				if(response.getValueInteger("ok") == 1) {
					this.providerView.showMessage("Success", "Provider has been successfully added");
					this.providerView.update(document);
					this.providerView.clearForm();
				} else {
					this.providerView.showMessage("Error", "Provider could not be added");
				}
			},
			(err) -> {
				this.providerView.hiddenRegisterProgress();
				this.providerView.showMessage("Error", "Connect failed or interrupted");
			}
		);
	}

	@Override
	public void exportProviders() {
		this.providerView.showMessage("Error", "Feature not supported");
	}

	@Override
	public void getProviders() {
		this.providerView.showLoadProgress();
		service.getProviders(
			(response) -> {
				this.providerView.hiddenloadProgress();
					if(response.getValueInteger("ok") == 1) {
						this.providerView.loadProviders(response.getArrayContentValues("response"));
					} else {
						this.providerView.showMessage("Error", "Providers have not been downloaded");
					}
				}, 
			(err) -> {
				this.providerView.hiddenloadProgress();
				this.providerView.showMessage("Error", "Connect failed or interrupted");
			}
		);
	}
	
	@Override
	public void editProvider(ContentValues updateProvider) {
		this.providerView.showLoadProgress();
		service.EditProvider(updateProvider, 
			(response) -> {
				this.providerView.hiddenloadProgress();
				if(response.getValueInteger("ok") == 1) {
					this.providerView.showMessage("Update", "Provider has been updated");
				} else {
					this.providerView.showMessage("Error", "Provider has not been updated");
				}
			}, 
			(err) -> {
				this.providerView.hiddenloadProgress();
				this.providerView.showMessage("Error", "Connect failed or interrupted");
			}
		);
	}

	@Override
	public void deleteProvider(String id) {
		this.providerView.showLoadProgress();
		service.deleteProviders(id, 
			(response) -> {
				this.providerView.hiddenloadProgress();
				if(response.getValueInteger("ok") == 1) {
					this.providerView.showMessage("Update", "Provider has been removed");
					this.providerView.remove(id);
				} else {
					this.providerView.showMessage("Error", "Provider has not been removed");
				}
			},
			(err) -> {
				this.providerView.hiddenloadProgress();
				this.providerView.showMessage("Error", "Connect failed or interrupted");
			}
		);
	}

	@Override
	public ProvidersView<ContentValues> getProviderView() {
		return providerView;
	}

}
