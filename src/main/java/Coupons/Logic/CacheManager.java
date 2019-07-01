package Coupons.Logic;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class CacheManager implements ICacheManager{
	
	private Map<Object, Object> map;
	
	public CacheManager() {
		this.map = new HashMap<Object, Object>();
	}

	@Override
	public void deleteFromMap(Integer key) {

		map.remove(key);

	}
	public void put(Object key, Object value) {
		this.map.put(key, value);
	}

	
	public Object get(Object key) {
		return this.map.get(key);
	}
}
