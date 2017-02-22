/*
 * Zed Attack Proxy (ZAP) and its related class files.
 * 
 * ZAP is an HTTP/HTTPS proxy for assessing web application security.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0 
 *   
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License. 
 */
package org.zaproxy.clientapi.core;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.w3c.dom.Node;

public class ApiResponseSet extends ApiResponse {
	
	private String[] attributes = null;
	private final Map<String, String> valuesMap;
	private ApiResponseSet childSet = null;

	/**
	 * Constructs an {@code ApiResponseSet} with the given name and attributes.
	 *
	 * @param name the name of the API response
	 * @param attributes the attributes
	 * @deprecated (TODO add version) Unused, there's no replacement.
	 */
	@Deprecated
	public ApiResponseSet(String name, String[] attributes) {
		super(name);
		this.attributes = attributes;
		this.valuesMap = Collections.emptyMap();
	}

	public ApiResponseSet(String name, Map<String, String> values) {
		super(name);
		this.valuesMap = Collections.unmodifiableMap(new HashMap<>(values));
	}

	public ApiResponseSet(Node node) throws ClientApiException {
		super(node.getNodeName());
		Node child = node.getFirstChild();
		Map<String, String> values = new HashMap<>();
		while (child != null) {
			ApiResponse childResponse =  ApiResponseFactory.getResponse(child);
			if (childResponse instanceof ApiResponseSet) {
				childSet = (ApiResponseSet) ApiResponseFactory.getResponse(child);
			} else {
				ApiResponseElement elem = (ApiResponseElement) ApiResponseFactory.getResponse(child);
				values.put(elem.getName(), elem.getValue());
			}
			child = child.getNextSibling();
		}
		this.valuesMap = Collections.unmodifiableMap(values);
	}

	/**
	 * Gets the attributes.
	 *
	 * @return the attributes, might be {@code null}.
	 * @deprecated (TODO add version) Unused, there's no replacement.
	 * @see #getValues()
	 */
	@Deprecated
	public String[] getAttributes() {
		return attributes;
	}
	
	/**
	 * Gets the value for the given {@code key}.
	 *
	 * @param key the key of the value
	 * @return the value, or {@code null} if no value exists for the given {@code key}.
	 * @deprecated (TODO add version) Use {@link #getValue(String)} instead.
	 */
	@Deprecated
	public String getAttribute(String key) {
		return getValue(key);
	}

	/**
	 * Gets the value for the given {@code key}.
	 *
	 * @param key the key of the value
	 * @return the value, or {@code null} if no value exists for the given {@code key}.
	 * @since TODO add version
	 * @see #getKeys()
	 */
	public String getValue(String key) {
		return valuesMap.get(key);
	}

	/**
	 * Gets a {@code Map} with the keys and values.
	 * <p>
	 * The returned {@code Map} is unmodifiable, any attempt to modify it will result in an
	 * {@code UnsupportedOperationException}.
	 *
	 * @return the map with the keys/values, never {@code null}.
	 * @since TODO add version
	 */
	public Map<String, String> getValuesMap() {
		return valuesMap;
	}

	/**
	 * Gets the keys of the values.
	 * <p>
	 * The returned {@code Set} is unmodifiable, any attempt to modify it will result in an
	 * {@code UnsupportedOperationException}.
	 *
	 * @return the keys, never {@code null}.
	 * @since TODO add version
	 * @see #getValue(String)
	 * @see #getValues()
	 * @see #getValuesMap()
	 */
	public Set<String> getKeys() {
		return valuesMap.keySet();
	}

	/**
	 * Gets the values.
	 * <p>
	 * The returned {@code Collection} is unmodifiable, any attempt to modify it will result in an
	 * {@code UnsupportedOperationException}.
	 *
	 * @return the values, never {@code null}.
	 * @since TODO add version
	 * @see #getValue(String)
	 */
	public Collection<String> getValues() {
		return valuesMap.values();
	}

	/**
	 * Gets the childSet.
	 * 
	 * @return the childSet, might be {@code null}.
	 */
	public ApiResponseSet getChildSet() {
		return childSet;
	}

	@Override
	public String toString(int indent) {
		StringBuilder sb = new StringBuilder();
		for (int i=0 ; i < indent; i++) {
			sb.append("\t");
		}
		sb.append("ApiResponseSet ");
		sb.append(this.getName());
		sb.append(" : [\n");
		for (Entry<String, String> val  : valuesMap.entrySet()) {
			for (int i=0 ; i < indent+1; i++) {
				sb.append("\t");
			}
			sb.append(val.getKey());
			sb.append(" = ");
			sb.append(val.getValue());
			sb.append("\n");
		}
		for (int i=0 ; i < indent; i++) {
			sb.append("\t");
		}
		sb.append("]\n");
		return sb.toString();
	}
}
