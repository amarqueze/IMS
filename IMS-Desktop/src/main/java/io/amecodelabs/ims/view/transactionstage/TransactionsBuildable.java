package io.amecodelabs.ims.view.transactionstage;

import io.amecodelabs.ims.view.base.BuildableWindow;
import io.amecodelabs.ims.view.base.PrimaryStage;
import io.amecodelabs.ims.view.base.Window;
import io.amecodelabs.ims.view.base.WindowBuilder;
import io.amecodelabs.ims.view.context.ApplicationContext;

public class TransactionsBuildable implements BuildableWindow {
	private PrimaryStage primary;
	
	public TransactionsBuildable(PrimaryStage primary) {
		this.primary = primary;
	}

	@Override
	public Window build() {
		var app = ApplicationContext.getInstance();
		WindowBuilder builder = new WindowBuilder("/view/StageTransactions.fxml");
		
		return builder
			.setIcon(app.getIcon())
			.setTitle(app.getName() + " - " + "transaction")
			.setPrimaryStage(primary)
			.undecorated()
			.build();
	}

}
