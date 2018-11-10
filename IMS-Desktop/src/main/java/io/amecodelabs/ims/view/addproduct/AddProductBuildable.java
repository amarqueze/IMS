package io.amecodelabs.ims.view.addproduct;

import io.amecodelabs.ims.view.base.BuildableWindow;
import io.amecodelabs.ims.view.base.PrimaryStage;
import io.amecodelabs.ims.view.base.Window;
import io.amecodelabs.ims.view.base.WindowBuilder;
import io.amecodelabs.ims.view.context.ApplicationContext;

public class AddProductBuildable implements BuildableWindow {
	private PrimaryStage primary;
	
	public AddProductBuildable(PrimaryStage primary) {
		this.primary = primary;
	}
	
	@Override
	public Window build() {
		var app = ApplicationContext.getInstance();
		WindowBuilder builder = new WindowBuilder("/view/StageAddProduct.fxml");
		
		return builder
			.setIcon(app.getIcon())
			.setTitle(app.getName() + " - " + "product")
			.setPrimaryStage(primary)
			.undecorated()
			.build();
	}

}
