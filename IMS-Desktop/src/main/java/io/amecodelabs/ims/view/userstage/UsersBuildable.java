package io.amecodelabs.ims.view.userstage;

import io.amecodelabs.ims.view.base.BuildableWindow;
import io.amecodelabs.ims.view.base.PrimaryStage;
import io.amecodelabs.ims.view.base.Window;
import io.amecodelabs.ims.view.base.WindowBuilder;
import io.amecodelabs.ims.view.context.ApplicationContext;

public class UsersBuildable implements BuildableWindow {
	private PrimaryStage primary;
	
	public UsersBuildable(PrimaryStage primary) {
		this.primary = primary;
	}

	@Override
	public Window build() {
		var app = ApplicationContext.getInstance();
		
		WindowBuilder builder = new WindowBuilder("/view/StageUsers.fxml");
		
		return builder
			.setIcon(app.getIcon())
			.setTitle(app.getName() + " - " + "Users")
			.setPrimaryStage(primary)
			.undecorated()
			.build();
	}
}
