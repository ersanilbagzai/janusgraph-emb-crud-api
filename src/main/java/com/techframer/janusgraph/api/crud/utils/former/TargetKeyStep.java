package com.techframer.janusgraph.api.crud.utils.former;

public interface TargetKeyStep {

	public TargetBuildOrPropertiesStep withKey(Object key);
	public TargetBuildOrPropertiesStep withoutKey();
	
}
