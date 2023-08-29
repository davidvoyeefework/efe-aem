package com.efe.core.models;

import java.util.List;
import java.util.Map;

import com.adobe.cq.wcm.core.components.models.Component;

/**
 * The Interface ColumnControl.
 */
public interface ColumnControl extends Component {

	/**
	 * Gets the columns list.
	 *
	 * @return the columns list
	 */
	List<Map<String, String>> getColumnsList();
}
