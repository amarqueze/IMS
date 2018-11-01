package io.amecodelabs.ims.view.settingstage;

import io.amecodelabs.ims.view.base.BuildableWindow;
import io.amecodelabs.ims.view.base.Window;
import io.amecodelabs.ims.view.base.WindowBuilder;
import io.amecodelabs.ims.view.context.ApplicationContext;

public class SettingBuildable implements BuildableWindow {

	@Override
	public Window build() {
		var app = ApplicationContext.getInstance();
		
		WindowBuilder builder = new WindowBuilder("/view/StageSettings.fxml");
		
		return builder
			.setIcon(app.getIcon())
			.setTitle(app.getName() + " - " + "Setting")
			.undecorated()
			.build();
	}

}
