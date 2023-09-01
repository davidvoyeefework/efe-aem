package com.efe.core.models.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.PostConstruct;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.adobe.cq.export.json.ComponentExporter;
import com.adobe.cq.export.json.ExporterConstants;
import com.adobe.cq.wcm.core.components.util.AbstractComponentImpl;
import com.efe.core.models.ColumnControl;

/**
 * The Class ColumnControlImpl.
 */
@Model(adaptables = { SlingHttpServletRequest.class }, adapters = { ColumnControl.class,
		ComponentExporter.class }, resourceType = {
				ColumnControlImpl.RESOURCE_TYPE }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class ColumnControlImpl extends AbstractComponentImpl implements ColumnControl {

	/** The Constant RESOURCE_TYPE. */
	public static final String RESOURCE_TYPE = "efe/fe-components/content/columncontrol";

	/** The Constant GRID_CLASS_PREFIX. */
	private static final String GRID_CLASS_PREFIX = "aem-GridColumn--phone--12 aem-GridColumn--default--";

	/** The Constant CLASSVALUE. */
	protected static final String CLASSVALUE = "classvalue";

	protected static final String INDEX = "index";

	/** The Constant FOUR_COLUMNS. */
	private static final String FOUR_COLUMNS = "4";

	/** The Constant SIX_COLUMNS. */
	private static final String SIX_COLUMNS = "6";

	/** The Constant EIGHT_COLUMNS. */
	private static final String EIGHT_COLUMNS = "8";

	/** The columns. */
	@ValueMapValue
	private int columns;

	/** The split option. */
	@ValueMapValue
	private String splitOption;

	/** The columns index. */
	private List<Map<String, String>> columnsList;

	/**
	 * Inits the model.
	 */
	@PostConstruct
	protected void init() {

		if (columns <= 0) {
			return;
		}

		columnsList = new ArrayList<>();

		for (int i = 0; i < columns; i++) {
			Map<String, String> columnMap = new HashMap<>();
			columnMap.put(INDEX, "container" + i);
			columnsList.add(columnMap);
		}

		// 2 Columns class value logic
		if (columns == 2) {
			if ("split-1".equals(splitOption)) {
				columnsList.get(0).put(CLASSVALUE, GRID_CLASS_PREFIX + EIGHT_COLUMNS);
				columnsList.get(1).put(CLASSVALUE, GRID_CLASS_PREFIX + FOUR_COLUMNS);
			} else if ("split-2".equals(splitOption)) {
				columnsList.get(0).put(CLASSVALUE, GRID_CLASS_PREFIX + FOUR_COLUMNS);
				columnsList.get(1).put(CLASSVALUE, GRID_CLASS_PREFIX + EIGHT_COLUMNS);
			} else {
				columnsList.get(0).put(CLASSVALUE, GRID_CLASS_PREFIX + SIX_COLUMNS);
				columnsList.get(1).put(CLASSVALUE, GRID_CLASS_PREFIX + SIX_COLUMNS);
			}
		}

		if (columns == 3) {
			columnsList.get(0).put(CLASSVALUE, GRID_CLASS_PREFIX + FOUR_COLUMNS);
			columnsList.get(1).put(CLASSVALUE, GRID_CLASS_PREFIX + FOUR_COLUMNS);
			columnsList.get(2).put(CLASSVALUE, GRID_CLASS_PREFIX + FOUR_COLUMNS);
		}
	}

	/**
	 * Gets the columns list.
	 *
	 * @return the columns list
	 */
	@Override
	public List<Map<String, String>> getColumnsList() {
		if (Objects.nonNull(columnsList)) {
			return new ArrayList<>(columnsList);
		}
		return Collections.emptyList();
	}
}
