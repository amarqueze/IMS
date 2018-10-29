package io.amecodelabs.ims.view.base;

import java.util.HashMap;
import java.util.Map;

public class BuildWindowDirector {
	private static BuildWindowDirector director;
	
	private Map<String, BuildableWindow> buildables;
	
	public static BuildWindowDirector getDirector() {
		if(director == null) return director = new BuildWindowDirector();
		return director;
	}
	
	public BuildWindowDirector() {
		buildables = new HashMap<>();
	}
	
	public Window create(BuildableWindow buildableWindow) {
		return buildableWindow.build();
	}
	
	public Window create(String nameBuildable) {
		return create( get(nameBuildable) );
	}
	
	public BuildWindowDirector prepare(String nameBuildable, BuildableWindow buildableWindow) {
		buildables.put(nameBuildable, buildableWindow);
		return this;
	}
	
	public BuildableWindow get(String nameBuildable) {
		return buildables.get(nameBuildable);
	}
}
