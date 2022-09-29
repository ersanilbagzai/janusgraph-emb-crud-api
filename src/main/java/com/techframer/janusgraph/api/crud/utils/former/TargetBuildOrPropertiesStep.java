package com.techframer.janusgraph.api.crud.utils.former;

public interface TargetBuildOrPropertiesStep {

	public TargetBuildOrPropertiesStep withProperty(String key, Object value);
	public EdgeBuildOrPropertiesStep build();
	
}
