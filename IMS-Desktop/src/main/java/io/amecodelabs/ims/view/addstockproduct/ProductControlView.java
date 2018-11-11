package io.amecodelabs.ims.view.addstockproduct;

import io.amecodelabs.ims.view.base.AbstractView;
import io.amecodelabs.ims.view.base.SubStage;

public interface ProductControlView<T> extends AbstractView, SubStage {
	void update(T productControl);
	void showMessage(String title, String msg);
	void showProgress();
	void hiddenProgress();
	PresenterProductControlView<T> getPresenter();
}
