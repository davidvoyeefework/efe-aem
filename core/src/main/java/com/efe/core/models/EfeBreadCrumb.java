package com.efe.core.models;

import com.adobe.cq.wcm.core.components.models.Breadcrumb;

/**
 * The Interface MapDirection Model.
 */

public interface EfeBreadCrumb extends Breadcrumb {


	/**
	 * Method to return the JSON LD Schema.
	 *
	 * @return json ld
	 */
	String getJsonLd();

   }
