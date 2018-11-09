package io.amecodelabs.ims.view.productstage;

import io.amecodelabs.ims.models.utils.ContentValues;
import io.amecodelabs.ims.service.CategoryService;
import io.amecodelabs.ims.service.ProductService;
import io.amecodelabs.ims.service.ProviderService;

public class PresenterProductViewImpl implements PresenterProductView<ContentValues> {
	private ProductView<ContentValues> productView;
	private ProductService productService;
	private ProviderService providerService;
	private CategoryService categoryService;
	
	public PresenterProductViewImpl(ProductView<ContentValues> productView) {
		this.productView = productView;
		productService = new ProductService();
		providerService = new ProviderService();
		categoryService = new CategoryService();
	}

	@Override
	public void saveProduct(ContentValues product) {
		this.productView.showRegisterProgress();
		productService.createProduct(product, 
			(res, document) -> {
				this.productView.hiddenRegisterProgress();
				if(res.getValueInteger("ok") == 1) {
					this.productView.showMessage("Success", "Product has been successfully added");
					this.productView.clearForm();
				} else {
					this.productView.showMessage("Error", "Product could not be added");
				}
			}, 
			(err) -> {
				this.productView.hiddenRegisterProgress();
				this.productView.showMessage("Error", "Connect failed or interrupted");
			}
		);
	}

	@Override
	public void getProviders() {
		providerService.getProviders(
			(res) -> {
				if(res.getValueInteger("ok") == 1) {
					ContentValues[] providers = res.getArrayContentValues("response");
					if(providers.length != 0) this.productView.loadProviders(providers);
				} 	
			}, 
			(err) -> {}
		);
	}

	@Override
	public void getCategories() {
		categoryService.getCategories(
			(res) -> {
				if(res.getValueInteger("ok") == 1) {
					ContentValues[] categories = res.getArrayContentValues("response");
					if(categories.length != 0) this.productView.loadCategories(categories);
				} 	
			}, 
			(err) -> {}
		);
	}

	@Override
	public ProductView<ContentValues> getProductView() {
		return productView;
	}

}
