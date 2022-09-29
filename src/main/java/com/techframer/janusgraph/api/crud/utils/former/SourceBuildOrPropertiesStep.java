package com.techframer.janusgraph.api.crud.utils.former;

public interface SourceBuildOrPropertiesStep {

	public SourceBuildOrPropertiesStep withProperty(String key, Object value);
	public TargetStep build();
	
}
