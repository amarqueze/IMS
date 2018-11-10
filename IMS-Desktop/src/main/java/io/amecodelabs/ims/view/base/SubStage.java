package io.amecodelabs.ims.view.base;

public interface SubStage {
	default void setPrimaryStage(PrimaryStage primary) {}
	String getName();
}
