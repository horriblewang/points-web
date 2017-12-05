package com.points.osp.common.config;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.util.StringValueResolver;

import com.points.osp.common.service.PropertyAccessor;

public class AppConfig extends PropertyPlaceholderConfigurer implements
		Map<String, String>,PropertyAccessor {

	Map<String, String> props = new HashMap();
	final Properties sysProperties = System.getProperties();

	protected void processProperties(
			ConfigurableListableBeanFactory beanFactory, Properties props)
			throws BeansException {
		this.props.clear();
		for (Map.Entry e : props.entrySet()) {
			this.props.put(e.getKey().toString(), e.getValue().toString());
			this.sysProperties.put(e.getKey().toString(), e.getValue()
					.toString());
		}
		super.processProperties(beanFactory, props);
	}

	protected void doProcessProperties(
			ConfigurableListableBeanFactory beanFactoryToProcess,
			StringValueResolver valueResolver) {
		super.doProcessProperties(beanFactoryToProcess, valueResolver);

		for (Map.Entry e : this.props.entrySet())
			e.setValue(valueResolver.resolveStringValue((String) e.getValue()));
	}

	public Set<String> keySet() {
		return this.props.keySet();
	}

	public Set<Map.Entry<String, String>> entrySet() {
		return this.props.entrySet();
	}

	public Collection<String> values() {
		return this.props.values();
	}

	public int size() {
		return this.props.size();
	}

	public boolean isEmpty() {
		return this.props.isEmpty();
	}

	public boolean containsValue(Object value) {
		return this.props.containsValue(value);
	}

	public boolean containsKey(Object key) {
		return this.props.containsKey(key);
	}

	public String get(Object key) {
		return (String) this.props.get(key);
	}

	public void clear() {
		throw new UnsupportedOperationException();
	}

	public String put(String key, String value) {
		throw new UnsupportedOperationException();
	}

	public String remove(Object key) {
		throw new UnsupportedOperationException();
	}

	public void putAll(Map<? extends String, ? extends String> t) {
		throw new UnsupportedOperationException();
	}

	public String getProperty(String key) {
		return get(key);
	}
}
