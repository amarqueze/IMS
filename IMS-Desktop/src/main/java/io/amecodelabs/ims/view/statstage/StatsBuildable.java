package io.amecodelabs.ims.view.statstage;

import io.amecodelabs.ims.view.base.BuildableWindow;
import io.amecodelabs.ims.view.base.PrimaryStage;
import io.amecodelabs.ims.view.base.Window;
import io.amecodelabs.ims.view.base.WindowBuilder;
import io.amecodelabs.ims.view.context.ApplicationContext;

public class StatsBuildable implements BuildableWindow {
	private PrimaryStage primary;
	
	public StatsBuildable(PrimaryStage primary) {
		this.primary = primary;
	}

	@Override
	public Window build() {
		var app = ApplicationContext.getInstance();
		WindowBuilder builder = new WindowBuilder("/view/StageStats.fxml");
		
		return builder
			.setIcon(app.getIcon())
			.setTitle(app.getName() + " - " + "stats")
			.setPrimaryStage(primary)
			.undecorated()
			.build();
	}
	
}
