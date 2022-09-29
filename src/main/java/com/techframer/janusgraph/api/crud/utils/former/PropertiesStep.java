package com.techframer.janusgraph.api.crud.utils.former;

import java.util.Map;

public interface PropertiesStep<T> {

	public T withProperty(String key, Object value);

	public T withProperties(Map<String, Object> properties);

}
