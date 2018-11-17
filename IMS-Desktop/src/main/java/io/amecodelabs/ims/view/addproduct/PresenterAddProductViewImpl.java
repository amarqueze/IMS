package io.amecodelabs.ims.view.addproduct;

import io.amecodelabs.ims.models.utils.ContentValues;
import io.amecodelabs.ims.service.CategoryService;
import io.amecodelabs.ims.service.ProductService;
import io.amecodelabs.ims.service.ProviderService;

public class PresenterAddProductViewImpl implements PresenterAddProductView<ContentValues> {
	private AddProductView<ContentValues> addProductView;
	private ProductService productService;
	private ProviderService providerService;
	private CategoryService categoryService;
	
	public PresenterAddProductViewImpl(AddProductView<ContentValues> productView) {
		this.addProductView = productView;
		productService = new ProductService();
		providerService = new ProviderService();
		categoryService = new CategoryService();
	}

	@Override
	public void saveProduct(ContentValues product) {
		this.addProductView.showRegisterProgress();
		productService.createProduct(product, 
			(res, document) -> {
				this.addProductView.hiddenRegisterProgress();
				if(res.getValueInteger("ok") == 1) {
					this.addProductView.showMessage("Success", "Product has been successfully added");
					this.addProductView.updateProduct(document);
					this.addProductView.clearForm();
				} else {
					this.addProductView.showMessage("Error", "Product could not be added");
				}
			}, 
			(err) -> {
				this.addProductView.hiddenRegisterProgress();
				this.addProductView.showMessage("Error", "Connect failed or interrupted");
			}
		);
	}

	@Override
	public void getProviders() {
		providerService.getProviders(
			(res) -> {
				if(res.getValueInteger("ok") == 1) {
					ContentValues[] providers = res.getArrayContentValues("response");
					if(providers.length != 0) this.addProductView.loadProviders(providers);
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
					if(categories.length != 0) this.addProductView.loadCategories(categories);
				} 	
			}, 
			(err) -> {}
		);
	}

	@Override
	public AddProductView<ContentValues> getProductView() {
		return addProductView;
	}

}
