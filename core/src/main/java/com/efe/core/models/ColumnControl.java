package com.efe.core.models;

import java.util.List;

import com.adobe.cq.wcm.core.components.models.Component;

/**
 * The Interface ColumnControl.
 */
public interface ColumnControl extends Component {


	/**
	 * Gets the grid class.
	 *
	 * @return the grid class
	 */
	String getGridClass();

	/**
	 * Gets the columns index.
	 *
	 * @return the columns index
	 */
	List<String> getColumnsIndex();

}
