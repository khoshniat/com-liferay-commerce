/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.headless.commerce.admin.site.setting.resource.v1_0;

import com.liferay.headless.commerce.admin.site.setting.dto.v1_0.CatalogRule;
import com.liferay.portal.kernel.model.Company;

import javax.annotation.Generated;

import javax.ws.rs.core.Response;

/**
 * To access this resource, run:
 *
 *     curl -u your@email.com:yourpassword -D - http://localhost:8080/o/headless-commerce-admin-site-setting/v1.0
 *
 * @author Zoltán Takács
 * @generated
 */
@Generated("")
public interface CatalogRuleResource {

	public Response deleteCatalogRule(Long id) throws Exception;

	public Response getCatalogRule(Long id) throws Exception;

	public Response updateMediaType1CatalogRule(
			Long id, CatalogRule catalogRule)
		throws Exception;

	public Response updateMediaType2CatalogRule(
			Long id, CatalogRule catalogRule)
		throws Exception;

	public Response getCatalogRuleCategories(Long id) throws Exception;

	public Response getCatalogRuleUserSegments(Long id) throws Exception;

	public Response getCatalogRules(Long groupId) throws Exception;

	public Response upsertMediaType1CatalogRule(
			Long groupId, CatalogRule catalogRule)
		throws Exception;

	public Response upsertMediaType2CatalogRule(
			Long groupId, CatalogRule catalogRule)
		throws Exception;

	public void setContextCompany(Company contextCompany);

}