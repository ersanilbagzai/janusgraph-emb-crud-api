package com.techframer.janusgraph.api.crud.utils.former;

public interface KeyStep<T> {

	public T withKey(Object key);

	public T withoutKey();

}
