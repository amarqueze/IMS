package io.amecodelabs.ims.view.addstockproduct;

public interface PresenterProductControlView<T> {
	void saveStock(T stock);
	ProductControlView<T> getProductControlView();
}
