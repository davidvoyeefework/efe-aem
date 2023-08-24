package com.efe.core.models.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
@Model(adaptables = { SlingHttpServletRequest.class },
	   adapters = { ColumnControl.class, ComponentExporter.class },
	   resourceType = { ColumnControlImpl.RESOURCE_TYPE },
	   defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class ColumnControlImpl extends AbstractComponentImpl implements ColumnControl {

	/** The Constant RESOURCE_TYPE. */
	public static final String RESOURCE_TYPE = "efe/fe-components/content/columncontrol";

	/** The columns. */
	@ValueMapValue
	private int columns;
	
	/** The columns index. */
	private List<String> columnsIndex;
	
	/** The grid class. */
	private String gridClass;
	
	/**
	 * Inits the.
	 */
	@PostConstruct
	protected void init() {
		
		if(columns <= 0) {
			return;
		}
		
		columnsIndex = new ArrayList<>();
		
		for(int i = 0; i < columns; i++) {
			columnsIndex.add("container"+i);
		}
		
		gridClass = "aem-GridColumn--phone--12 aem-GridColumn--default--"+ (12 / columns);
		
	}

	/**
	 * Gets the grid class.
	 *
	 * @return the grid class
	 */
	@Override
	public String getGridClass() {
		return gridClass;
	}
	
	/**
	 * Gets the columns index.
	 *
	 * @return the columns index
	 */
	@Override
	public List<String> getColumnsIndex(){
		if (Objects.nonNull(columnsIndex)) {
			return new ArrayList<>(columnsIndex);
		}
		return Collections.emptyList();
	}
}
