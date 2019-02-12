package io.amecodelabs.ims.view.base;

import javafx.stage.Stage;

public interface Window {
	void show();
	void hide();
	void focus();
	void destroy();
	Stage getStage();
}
