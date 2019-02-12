package io.amecodelabs.ims.view.base;

import java.util.Map;

import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public abstract class AbstractWindow implements Window {
	protected Stage stage;
	
	protected double xOffset = 0;
    protected double yOffset = 0;
	
	public AbstractWindow(Stage stage, Map<String, Object> params) {
		this.stage = stage;
		config(params);
	}
	
	public void setUndecoratedWindow(Parent sceneGraph) {
		stage.initStyle(StageStyle.UNDECORATED);
		sceneGraph.setOnMousePressed((event) -> {
			xOffset = event.getSceneX();
			yOffset = event.getSceneY();
		});
		sceneGraph.setOnMouseDragged((event) -> {
			stage.setX(event.getScreenX() - xOffset);
			stage.setY(event.getScreenY() - yOffset);
		});
	}
	
	@Override
	public void show() {
		stage.show();
	}
	
	@Override
	public void hide() {
		stage.hide();
	}
	
	@Override
	public void focus() {
		stage.toFront();
	}
	
	@Override
	public void destroy() {
		stage.close();
	}
	
	@Override
	public Stage getStage() {
		return stage;
	}
	
	protected abstract void config(Map<String, Object> params);
	
}
