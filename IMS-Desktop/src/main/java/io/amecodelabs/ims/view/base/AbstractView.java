package io.amecodelabs.ims.view.base;

import java.util.Map;

public interface AbstractView {
	default void setParams(Map<String, Object> params) {}
}
