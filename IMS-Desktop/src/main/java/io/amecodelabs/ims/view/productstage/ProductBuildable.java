package io.amecodelabs.ims.view.productstage;

import io.amecodelabs.ims.view.base.BuildableWindow;
import io.amecodelabs.ims.view.base.Window;
import io.amecodelabs.ims.view.base.WindowBuilder;
import io.amecodelabs.ims.view.context.ApplicationContext;

public class ProductBuildable implements BuildableWindow {

	@Override
	public Window build() {
		var app = ApplicationContext.getInstance();
		WindowBuilder builder = new WindowBuilder("/view/StageProduct.fxml");
		
		return builder
			.setIcon(app.getIcon())
			.setTitle(app.getName() + " - " + "product")
			.undecorated()
			.build();
	}

}
