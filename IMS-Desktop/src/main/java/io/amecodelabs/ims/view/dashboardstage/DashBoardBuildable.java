package io.amecodelabs.ims.view.dashboardstage;

import io.amecodelabs.ims.view.base.BuildableWindow;
import io.amecodelabs.ims.view.base.PrimaryStage;
import io.amecodelabs.ims.view.base.Window;
import io.amecodelabs.ims.view.base.WindowBuilder;
import io.amecodelabs.ims.view.context.ApplicationContext;

public class DashBoardBuildable implements BuildableWindow  {
	private PrimaryStage primary;
	
	public DashBoardBuildable(PrimaryStage primary) {
		this.primary = primary;
	}
	
	@Override
	public Window build() {
		var app = ApplicationContext.getInstance();
		WindowBuilder builder = new WindowBuilder("/view/StageDashboard.fxml");
		
		return builder
			.setIcon(app.getIcon())
			.setTitle(app.getName() + " - " + "Dashboard")
			.setPrimaryStage(primary)
			.undecorated()
			.build();
	}

}
