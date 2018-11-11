package io.amecodelabs.ims.view.addstockproduct;

import java.util.Map;

import io.amecodelabs.ims.view.base.BuildableWindow;
import io.amecodelabs.ims.view.base.PrimaryStage;
import io.amecodelabs.ims.view.base.Window;
import io.amecodelabs.ims.view.base.WindowBuilder;
import io.amecodelabs.ims.view.context.ApplicationContext;

public class ProductControlBuildable implements BuildableWindow {
	private PrimaryStage primary;
	private Map<String, Object> params;
	
	public ProductControlBuildable(Map<String, Object> params, PrimaryStage primary) {
		this.params = params;
		this.primary = primary;
	}
	
	@Override
	public Window build() {
		var app = ApplicationContext.getInstance();
		WindowBuilder builder = new WindowBuilder("/view/StageProductControl.fxml");
		
		return builder
			.setIcon(app.getIcon())
			.setTitle(app.getName() + " - " + "product")
			.setParams(params)
			.setPrimaryStage(primary)
			.undecorated()
			.build();
	}
	
}
