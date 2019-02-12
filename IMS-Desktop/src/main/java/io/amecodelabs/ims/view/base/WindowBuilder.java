package io.amecodelabs.ims.view.base;

import java.io.IOException;
import java.util.Map;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class WindowBuilder {
	private Stage stage;
	private String fxmlLocation;
	private String title;
	private String iconLocation;
	private boolean isDecorated;
	private PrimaryStage primary;
	private Map<String, Object> params;
	
	public WindowBuilder(String fxmlLocation, Stage stage) {
		this.fxmlLocation = fxmlLocation;
		this.stage = stage;
		isDecorated = false;
	}
	
	public WindowBuilder(String fmxlLocation) {
		this(fmxlLocation, new Stage());
	}
	
	public WindowBuilder setTitle(String title) {
		this.title = title;
		return this;
	}
	
	public WindowBuilder setIcon(String url) {
		this.iconLocation = url;
		return this;
	}
	
	public WindowBuilder undecorated() {
		this.isDecorated = true;
		return this;
	}
	
	public WindowBuilder setPrimaryStage(PrimaryStage primary) {
		this.primary = primary;
		return this;
	}
	
	public WindowBuilder setParams(Map<String, Object> params) {
		this.params = params;
		return this;
	}
	
	public Window build() {
		return (Window) new AbstractWindow(stage, params) {
			@Override
			protected void config(Map<String, Object> params)  {
				FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlLocation));
				Parent sceneGraph;
				
				
				try {
					sceneGraph = loader.load();
					
					if(isDecorated) setUndecoratedWindow(sceneGraph);
					AbstractView viewHandler = (AbstractView) loader.getController();
					
					if(viewHandler instanceof SubStage) ((SubStage)viewHandler).setPrimaryStage(primary);
					if(params != null)  {
						params.put("window", this);
						viewHandler.setParams(params);
					}
					
					Scene scene = new Scene(sceneGraph);
					stage.setScene(scene);
					stage.setTitle(title);
					stage.getIcons().add(new Image(iconLocation));
					this.show();
				} catch (IOException | IllegalArgumentException | SecurityException e) {
					e.printStackTrace();
				} 	
			}
		};
	}
	
}
