package io.amecodelabs.ims.view.productstage;

import io.amecodelabs.ims.models.utils.ContentValues;
import io.amecodelabs.ims.service.ProductService;

public class PresenterProductsViewImpl implements PresenterProductsView<ContentValues> {
	private ProductsView<ContentValues> productsView;
	private ProductService productService;
	
	public PresenterProductsViewImpl(ProductsView<ContentValues> productsView) {
		this.productsView = productsView;
		this.productService = new ProductService();
	}

	@Override
	public void getLoadProducts(int skip) {
		this.productsView.showProgress();
		productService.getProduct(skip, 
			(res) -> {
				this.productsView.hiddenProgress();
				if(res.getValueInteger("ok") == 1) {
					this.productsView.loadProducts(res.getArrayContentValues("response"));
				} else {
					this.productsView.showMessage("Error", "Products have not been downloaded");
				}
			},
			(err) -> {
				this.productsView.hiddenProgress();
				this.productsView.showMessage("Error", "Connect failed or interrupted");
			}
		);
	}

	@Override
	public ProductsView<ContentValues> getProductView() {
		return productsView;
	}
	
}
