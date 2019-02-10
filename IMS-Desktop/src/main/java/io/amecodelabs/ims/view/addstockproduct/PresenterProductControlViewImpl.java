package io.amecodelabs.ims.view.addstockproduct;

import io.amecodelabs.ims.models.utils.ContentValues;
import io.amecodelabs.ims.service.ProductService;
import io.amecodelabs.ims.service.StockService;

public class PresenterProductControlViewImpl implements PresenterProductControlView<ContentValues> {
	private ProductControlView<ContentValues> productControlView;
	
	public PresenterProductControlViewImpl(ProductControlView<ContentValues> productControlView) {
		this.productControlView = productControlView;
	}

	@Override
	public void saveStock(ContentValues stock) {
		ContentValues product = stock.getContentValues("product");
		ContentValues stockProduct = stock.getContentValues("stockProduct");
		
		if(stockProduct.getValueString("type").equals("input")) inputProduct(product, stockProduct.getValueInteger("quantity"));
		else outputProduct(product, stockProduct.getValueInteger("quantity"));
		
		if(product.getValueInteger("available_stock") >= 0) {
			this.productControlView.showProgress();
			StockService.createStockProduct(stockProduct, 
				(res, document) -> {
					if(res.getValueInteger("ok") == 1) {
						ProductService.editProduct(product, 
						(response) -> {
							this.productControlView.hiddenProgress();
							this.productControlView.update(product);
						}, (err) -> {});
					} 
				}, 
				(err) -> {
					this.productControlView.hiddenProgress();
					this.productControlView.showMessage("Error", "Connect failed or interrupted");	
				}
			);
		} else {
			this.productControlView.showMessage("Error", "Capacity of the stock has been exceeded");	
		}
	}
	
	private void inputProduct(ContentValues product, int inputValue) {
		int available_stock = product.getValueInteger("available_stock");
		available_stock+=inputValue;
		product.put("available_stock", available_stock);
	}
	
	private void outputProduct(ContentValues product, int inputValue) {
		int available_stock = product.getValueInteger("available_stock");
		available_stock-=inputValue;
		product.put("available_stock", available_stock);
	}

	@Override
	public ProductControlView<ContentValues> getProductControlView() {
		return productControlView;
	}

}
