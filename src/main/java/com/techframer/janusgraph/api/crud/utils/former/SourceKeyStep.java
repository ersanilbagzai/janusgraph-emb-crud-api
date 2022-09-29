package com.techframer.janusgraph.api.crud.utils.former;

public interface SourceKeyStep {

	public SourceBuildOrPropertiesStep withKey(Object key);
	public SourceBuildOrPropertiesStep withoutKey();
	
}
