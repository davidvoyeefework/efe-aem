package com.efe.core.utils;

import java.util.Objects;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Value;
import javax.jcr.ValueFactory;

/**
 * The NodePropertyManagerUtil.
 *
 */
public class NodePropertyManagerUtil { 

	/**
	 * Setting the Property.
	 * 
	 * @param locationMasterNode
	 * @param propertyName
	 * @param value
	 * @throws RepositoryException
	 */
	public static void setPropertyIfNonNull(Node locationMasterNode, String propertyName, Object value) throws RepositoryException {
	    if (Objects.nonNull(value) && Objects.nonNull(locationMasterNode)) {
	        ValueFactory valueFactory = locationMasterNode.getSession().getValueFactory();
	        Value jcrValue;
	        if (value instanceof Boolean) {
	            jcrValue = valueFactory.createValue((Boolean) value);
	        } else if (value instanceof Integer) {
	            jcrValue = valueFactory.createValue((Integer) value);
	        } else if (value instanceof String) {
	            jcrValue = valueFactory.createValue((String) value);
	        } else {
	            throw new IllegalArgumentException("Unsupported value type: " + value.getClass().getName());
	        }
	        locationMasterNode.setProperty(propertyName, jcrValue);
	    }
	}
}