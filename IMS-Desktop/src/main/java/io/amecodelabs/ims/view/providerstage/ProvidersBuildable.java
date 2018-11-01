package io.amecodelabs.ims.view.providerstage;

import io.amecodelabs.ims.view.base.BuildableWindow;
import io.amecodelabs.ims.view.base.Window;
import io.amecodelabs.ims.view.base.WindowBuilder;
import io.amecodelabs.ims.view.context.ApplicationContext;

public class ProvidersBuildable implements BuildableWindow {

	@Override
	public Window build() {
		var app = ApplicationContext.getInstance();
		
		WindowBuilder builder = new WindowBuilder("/view/StageProvider.fxml");
		
		return builder
			.setIcon(app.getIcon())
			.setTitle(app.getName() + " - " + "providers")
			.undecorated()
			.build();
	}

}
