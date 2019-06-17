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

package com.liferay.commerce.account.service.base;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.service.CommerceAccountLocalService;
import com.liferay.commerce.account.service.persistence.CommerceAccountFinder;
import com.liferay.commerce.account.service.persistence.CommerceAccountGroupCommerceAccountRelPersistence;
import com.liferay.commerce.account.service.persistence.CommerceAccountGroupPersistence;
import com.liferay.commerce.account.service.persistence.CommerceAccountGroupRelPersistence;
import com.liferay.commerce.account.service.persistence.CommerceAccountOrganizationRelPersistence;
import com.liferay.commerce.account.service.persistence.CommerceAccountPersistence;
import com.liferay.commerce.account.service.persistence.CommerceAccountUserRelPersistence;
import com.liferay.expando.kernel.service.persistence.ExpandoRowPersistence;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DefaultActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalServiceImpl;
import com.liferay.portal.kernel.service.PersistedModelLocalServiceRegistry;
import com.liferay.portal.kernel.service.persistence.ClassNamePersistence;
import com.liferay.portal.kernel.service.persistence.GroupPersistence;
import com.liferay.portal.kernel.service.persistence.RolePersistence;
import com.liferay.portal.kernel.service.persistence.UserPersistence;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the commerce account local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.commerce.account.service.impl.CommerceAccountLocalServiceImpl}.
 * </p>
 *
 * @author Marco Leo
 * @see com.liferay.commerce.account.service.impl.CommerceAccountLocalServiceImpl
 * @generated
 */
@ProviderType
public abstract class CommerceAccountLocalServiceBaseImpl
	extends BaseLocalServiceImpl
	implements CommerceAccountLocalService, IdentifiableOSGiService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Use <code>CommerceAccountLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>com.liferay.commerce.account.service.CommerceAccountLocalServiceUtil</code>.
	 */

	/**
	 * Adds the commerce account to the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceAccount the commerce account
	 * @return the commerce account that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceAccount addCommerceAccount(CommerceAccount commerceAccount) {
		commerceAccount.setNew(true);

		return commerceAccountPersistence.update(commerceAccount);
	}

	/**
	 * Creates a new commerce account with the primary key. Does not add the commerce account to the database.
	 *
	 * @param commerceAccountId the primary key for the new commerce account
	 * @return the new commerce account
	 */
	@Override
	@Transactional(enabled = false)
	public CommerceAccount createCommerceAccount(long commerceAccountId) {
		return commerceAccountPersistence.create(commerceAccountId);
	}

	/**
	 * Deletes the commerce account with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceAccountId the primary key of the commerce account
	 * @return the commerce account that was removed
	 * @throws PortalException if a commerce account with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public CommerceAccount deleteCommerceAccount(long commerceAccountId)
		throws PortalException {

		return commerceAccountPersistence.remove(commerceAccountId);
	}

	/**
	 * Deletes the commerce account from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceAccount the commerce account
	 * @return the commerce account that was removed
	 * @throws PortalException
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public CommerceAccount deleteCommerceAccount(
			CommerceAccount commerceAccount)
		throws PortalException {

		return commerceAccountPersistence.remove(commerceAccount);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(
			CommerceAccount.class, clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return commerceAccountPersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.commerce.account.model.impl.CommerceAccountModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return commerceAccountPersistence.findWithDynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.commerce.account.model.impl.CommerceAccountModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return commerceAccountPersistence.findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return commerceAccountPersistence.countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		DynamicQuery dynamicQuery, Projection projection) {

		return commerceAccountPersistence.countWithDynamicQuery(
			dynamicQuery, projection);
	}

	@Override
	public CommerceAccount fetchCommerceAccount(long commerceAccountId) {
		return commerceAccountPersistence.fetchByPrimaryKey(commerceAccountId);
	}

	/**
	 * Returns the commerce account with the matching external reference code and company.
	 *
	 * @param companyId the primary key of the company
	 * @param externalReferenceCode the commerce account's external reference code
	 * @return the matching commerce account, or <code>null</code> if a matching commerce account could not be found
	 */
	@Override
	public CommerceAccount fetchCommerceAccountByReferenceCode(
		long companyId, String externalReferenceCode) {

		return commerceAccountPersistence.fetchByC_ERC(
			companyId, externalReferenceCode);
	}

	/**
	 * Returns the commerce account with the primary key.
	 *
	 * @param commerceAccountId the primary key of the commerce account
	 * @return the commerce account
	 * @throws PortalException if a commerce account with the primary key could not be found
	 */
	@Override
	public CommerceAccount getCommerceAccount(long commerceAccountId)
		throws PortalException {

		return commerceAccountPersistence.findByPrimaryKey(commerceAccountId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery =
			new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(commerceAccountLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(CommerceAccount.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("commerceAccountId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(
			commerceAccountLocalService);
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());
		indexableActionableDynamicQuery.setModelClass(CommerceAccount.class);

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName(
			"commerceAccountId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {

		actionableDynamicQuery.setBaseLocalService(commerceAccountLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(CommerceAccount.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("commerceAccountId");
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {

		return commerceAccountLocalService.deleteCommerceAccount(
			(CommerceAccount)persistedModel);
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return commerceAccountPersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns a range of all the commerce accounts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.commerce.account.model.impl.CommerceAccountModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce accounts
	 * @param end the upper bound of the range of commerce accounts (not inclusive)
	 * @return the range of commerce accounts
	 */
	@Override
	public List<CommerceAccount> getCommerceAccounts(int start, int end) {
		return commerceAccountPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of commerce accounts.
	 *
	 * @return the number of commerce accounts
	 */
	@Override
	public int getCommerceAccountsCount() {
		return commerceAccountPersistence.countAll();
	}

	/**
	 * Updates the commerce account in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param commerceAccount the commerce account
	 * @return the commerce account that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceAccount updateCommerceAccount(
		CommerceAccount commerceAccount) {

		return commerceAccountPersistence.update(commerceAccount);
	}

	/**
	 * Returns the commerce account local service.
	 *
	 * @return the commerce account local service
	 */
	public CommerceAccountLocalService getCommerceAccountLocalService() {
		return commerceAccountLocalService;
	}

	/**
	 * Sets the commerce account local service.
	 *
	 * @param commerceAccountLocalService the commerce account local service
	 */
	public void setCommerceAccountLocalService(
		CommerceAccountLocalService commerceAccountLocalService) {

		this.commerceAccountLocalService = commerceAccountLocalService;
	}

	/**
	 * Returns the commerce account persistence.
	 *
	 * @return the commerce account persistence
	 */
	public CommerceAccountPersistence getCommerceAccountPersistence() {
		return commerceAccountPersistence;
	}

	/**
	 * Sets the commerce account persistence.
	 *
	 * @param commerceAccountPersistence the commerce account persistence
	 */
	public void setCommerceAccountPersistence(
		CommerceAccountPersistence commerceAccountPersistence) {

		this.commerceAccountPersistence = commerceAccountPersistence;
	}

	/**
	 * Returns the commerce account finder.
	 *
	 * @return the commerce account finder
	 */
	public CommerceAccountFinder getCommerceAccountFinder() {
		return commerceAccountFinder;
	}

	/**
	 * Sets the commerce account finder.
	 *
	 * @param commerceAccountFinder the commerce account finder
	 */
	public void setCommerceAccountFinder(
		CommerceAccountFinder commerceAccountFinder) {

		this.commerceAccountFinder = commerceAccountFinder;
	}

	/**
	 * Returns the commerce account group local service.
	 *
	 * @return the commerce account group local service
	 */
	public com.liferay.commerce.account.service.CommerceAccountGroupLocalService
		getCommerceAccountGroupLocalService() {

		return commerceAccountGroupLocalService;
	}

	/**
	 * Sets the commerce account group local service.
	 *
	 * @param commerceAccountGroupLocalService the commerce account group local service
	 */
	public void setCommerceAccountGroupLocalService(
		com.liferay.commerce.account.service.CommerceAccountGroupLocalService
			commerceAccountGroupLocalService) {

		this.commerceAccountGroupLocalService =
			commerceAccountGroupLocalService;
	}

	/**
	 * Returns the commerce account group persistence.
	 *
	 * @return the commerce account group persistence
	 */
	public CommerceAccountGroupPersistence
		getCommerceAccountGroupPersistence() {

		return commerceAccountGroupPersistence;
	}

	/**
	 * Sets the commerce account group persistence.
	 *
	 * @param commerceAccountGroupPersistence the commerce account group persistence
	 */
	public void setCommerceAccountGroupPersistence(
		CommerceAccountGroupPersistence commerceAccountGroupPersistence) {

		this.commerceAccountGroupPersistence = commerceAccountGroupPersistence;
	}

	/**
	 * Returns the commerce account group commerce account rel local service.
	 *
	 * @return the commerce account group commerce account rel local service
	 */
	public com.liferay.commerce.account.service.
		CommerceAccountGroupCommerceAccountRelLocalService
			getCommerceAccountGroupCommerceAccountRelLocalService() {

		return commerceAccountGroupCommerceAccountRelLocalService;
	}

	/**
	 * Sets the commerce account group commerce account rel local service.
	 *
	 * @param commerceAccountGroupCommerceAccountRelLocalService the commerce account group commerce account rel local service
	 */
	public void setCommerceAccountGroupCommerceAccountRelLocalService(
		com.liferay.commerce.account.service.
			CommerceAccountGroupCommerceAccountRelLocalService
				commerceAccountGroupCommerceAccountRelLocalService) {

		this.commerceAccountGroupCommerceAccountRelLocalService =
			commerceAccountGroupCommerceAccountRelLocalService;
	}

	/**
	 * Returns the commerce account group commerce account rel persistence.
	 *
	 * @return the commerce account group commerce account rel persistence
	 */
	public CommerceAccountGroupCommerceAccountRelPersistence
		getCommerceAccountGroupCommerceAccountRelPersistence() {

		return commerceAccountGroupCommerceAccountRelPersistence;
	}

	/**
	 * Sets the commerce account group commerce account rel persistence.
	 *
	 * @param commerceAccountGroupCommerceAccountRelPersistence the commerce account group commerce account rel persistence
	 */
	public void setCommerceAccountGroupCommerceAccountRelPersistence(
		CommerceAccountGroupCommerceAccountRelPersistence
			commerceAccountGroupCommerceAccountRelPersistence) {

		this.commerceAccountGroupCommerceAccountRelPersistence =
			commerceAccountGroupCommerceAccountRelPersistence;
	}

	/**
	 * Returns the commerce account group rel local service.
	 *
	 * @return the commerce account group rel local service
	 */
	public
		com.liferay.commerce.account.service.CommerceAccountGroupRelLocalService
			getCommerceAccountGroupRelLocalService() {

		return commerceAccountGroupRelLocalService;
	}

	/**
	 * Sets the commerce account group rel local service.
	 *
	 * @param commerceAccountGroupRelLocalService the commerce account group rel local service
	 */
	public void setCommerceAccountGroupRelLocalService(
		com.liferay.commerce.account.service.CommerceAccountGroupRelLocalService
			commerceAccountGroupRelLocalService) {

		this.commerceAccountGroupRelLocalService =
			commerceAccountGroupRelLocalService;
	}

	/**
	 * Returns the commerce account group rel persistence.
	 *
	 * @return the commerce account group rel persistence
	 */
	public CommerceAccountGroupRelPersistence
		getCommerceAccountGroupRelPersistence() {

		return commerceAccountGroupRelPersistence;
	}

	/**
	 * Sets the commerce account group rel persistence.
	 *
	 * @param commerceAccountGroupRelPersistence the commerce account group rel persistence
	 */
	public void setCommerceAccountGroupRelPersistence(
		CommerceAccountGroupRelPersistence commerceAccountGroupRelPersistence) {

		this.commerceAccountGroupRelPersistence =
			commerceAccountGroupRelPersistence;
	}

	/**
	 * Returns the commerce account organization rel local service.
	 *
	 * @return the commerce account organization rel local service
	 */
	public com.liferay.commerce.account.service.
		CommerceAccountOrganizationRelLocalService
			getCommerceAccountOrganizationRelLocalService() {

		return commerceAccountOrganizationRelLocalService;
	}

	/**
	 * Sets the commerce account organization rel local service.
	 *
	 * @param commerceAccountOrganizationRelLocalService the commerce account organization rel local service
	 */
	public void setCommerceAccountOrganizationRelLocalService(
		com.liferay.commerce.account.service.
			CommerceAccountOrganizationRelLocalService
				commerceAccountOrganizationRelLocalService) {

		this.commerceAccountOrganizationRelLocalService =
			commerceAccountOrganizationRelLocalService;
	}

	/**
	 * Returns the commerce account organization rel persistence.
	 *
	 * @return the commerce account organization rel persistence
	 */
	public CommerceAccountOrganizationRelPersistence
		getCommerceAccountOrganizationRelPersistence() {

		return commerceAccountOrganizationRelPersistence;
	}

	/**
	 * Sets the commerce account organization rel persistence.
	 *
	 * @param commerceAccountOrganizationRelPersistence the commerce account organization rel persistence
	 */
	public void setCommerceAccountOrganizationRelPersistence(
		CommerceAccountOrganizationRelPersistence
			commerceAccountOrganizationRelPersistence) {

		this.commerceAccountOrganizationRelPersistence =
			commerceAccountOrganizationRelPersistence;
	}

	/**
	 * Returns the commerce account user rel local service.
	 *
	 * @return the commerce account user rel local service
	 */
	public
		com.liferay.commerce.account.service.CommerceAccountUserRelLocalService
			getCommerceAccountUserRelLocalService() {

		return commerceAccountUserRelLocalService;
	}

	/**
	 * Sets the commerce account user rel local service.
	 *
	 * @param commerceAccountUserRelLocalService the commerce account user rel local service
	 */
	public void setCommerceAccountUserRelLocalService(
		com.liferay.commerce.account.service.CommerceAccountUserRelLocalService
			commerceAccountUserRelLocalService) {

		this.commerceAccountUserRelLocalService =
			commerceAccountUserRelLocalService;
	}

	/**
	 * Returns the commerce account user rel persistence.
	 *
	 * @return the commerce account user rel persistence
	 */
	public CommerceAccountUserRelPersistence
		getCommerceAccountUserRelPersistence() {

		return commerceAccountUserRelPersistence;
	}

	/**
	 * Sets the commerce account user rel persistence.
	 *
	 * @param commerceAccountUserRelPersistence the commerce account user rel persistence
	 */
	public void setCommerceAccountUserRelPersistence(
		CommerceAccountUserRelPersistence commerceAccountUserRelPersistence) {

		this.commerceAccountUserRelPersistence =
			commerceAccountUserRelPersistence;
	}

	/**
	 * Returns the counter local service.
	 *
	 * @return the counter local service
	 */
	public com.liferay.counter.kernel.service.CounterLocalService
		getCounterLocalService() {

		return counterLocalService;
	}

	/**
	 * Sets the counter local service.
	 *
	 * @param counterLocalService the counter local service
	 */
	public void setCounterLocalService(
		com.liferay.counter.kernel.service.CounterLocalService
			counterLocalService) {

		this.counterLocalService = counterLocalService;
	}

	/**
	 * Returns the class name local service.
	 *
	 * @return the class name local service
	 */
	public com.liferay.portal.kernel.service.ClassNameLocalService
		getClassNameLocalService() {

		return classNameLocalService;
	}

	/**
	 * Sets the class name local service.
	 *
	 * @param classNameLocalService the class name local service
	 */
	public void setClassNameLocalService(
		com.liferay.portal.kernel.service.ClassNameLocalService
			classNameLocalService) {

		this.classNameLocalService = classNameLocalService;
	}

	/**
	 * Returns the class name persistence.
	 *
	 * @return the class name persistence
	 */
	public ClassNamePersistence getClassNamePersistence() {
		return classNamePersistence;
	}

	/**
	 * Sets the class name persistence.
	 *
	 * @param classNamePersistence the class name persistence
	 */
	public void setClassNamePersistence(
		ClassNamePersistence classNamePersistence) {

		this.classNamePersistence = classNamePersistence;
	}

	/**
	 * Returns the group local service.
	 *
	 * @return the group local service
	 */
	public com.liferay.portal.kernel.service.GroupLocalService
		getGroupLocalService() {

		return groupLocalService;
	}

	/**
	 * Sets the group local service.
	 *
	 * @param groupLocalService the group local service
	 */
	public void setGroupLocalService(
		com.liferay.portal.kernel.service.GroupLocalService groupLocalService) {

		this.groupLocalService = groupLocalService;
	}

	/**
	 * Returns the group persistence.
	 *
	 * @return the group persistence
	 */
	public GroupPersistence getGroupPersistence() {
		return groupPersistence;
	}

	/**
	 * Sets the group persistence.
	 *
	 * @param groupPersistence the group persistence
	 */
	public void setGroupPersistence(GroupPersistence groupPersistence) {
		this.groupPersistence = groupPersistence;
	}

	/**
	 * Returns the resource local service.
	 *
	 * @return the resource local service
	 */
	public com.liferay.portal.kernel.service.ResourceLocalService
		getResourceLocalService() {

		return resourceLocalService;
	}

	/**
	 * Sets the resource local service.
	 *
	 * @param resourceLocalService the resource local service
	 */
	public void setResourceLocalService(
		com.liferay.portal.kernel.service.ResourceLocalService
			resourceLocalService) {

		this.resourceLocalService = resourceLocalService;
	}

	/**
	 * Returns the role local service.
	 *
	 * @return the role local service
	 */
	public com.liferay.portal.kernel.service.RoleLocalService
		getRoleLocalService() {

		return roleLocalService;
	}

	/**
	 * Sets the role local service.
	 *
	 * @param roleLocalService the role local service
	 */
	public void setRoleLocalService(
		com.liferay.portal.kernel.service.RoleLocalService roleLocalService) {

		this.roleLocalService = roleLocalService;
	}

	/**
	 * Returns the role persistence.
	 *
	 * @return the role persistence
	 */
	public RolePersistence getRolePersistence() {
		return rolePersistence;
	}

	/**
	 * Sets the role persistence.
	 *
	 * @param rolePersistence the role persistence
	 */
	public void setRolePersistence(RolePersistence rolePersistence) {
		this.rolePersistence = rolePersistence;
	}

	/**
	 * Returns the user local service.
	 *
	 * @return the user local service
	 */
	public com.liferay.portal.kernel.service.UserLocalService
		getUserLocalService() {

		return userLocalService;
	}

	/**
	 * Sets the user local service.
	 *
	 * @param userLocalService the user local service
	 */
	public void setUserLocalService(
		com.liferay.portal.kernel.service.UserLocalService userLocalService) {

		this.userLocalService = userLocalService;
	}

	/**
	 * Returns the user persistence.
	 *
	 * @return the user persistence
	 */
	public UserPersistence getUserPersistence() {
		return userPersistence;
	}

	/**
	 * Sets the user persistence.
	 *
	 * @param userPersistence the user persistence
	 */
	public void setUserPersistence(UserPersistence userPersistence) {
		this.userPersistence = userPersistence;
	}

	/**
	 * Returns the expando row local service.
	 *
	 * @return the expando row local service
	 */
	public com.liferay.expando.kernel.service.ExpandoRowLocalService
		getExpandoRowLocalService() {

		return expandoRowLocalService;
	}

	/**
	 * Sets the expando row local service.
	 *
	 * @param expandoRowLocalService the expando row local service
	 */
	public void setExpandoRowLocalService(
		com.liferay.expando.kernel.service.ExpandoRowLocalService
			expandoRowLocalService) {

		this.expandoRowLocalService = expandoRowLocalService;
	}

	/**
	 * Returns the expando row persistence.
	 *
	 * @return the expando row persistence
	 */
	public ExpandoRowPersistence getExpandoRowPersistence() {
		return expandoRowPersistence;
	}

	/**
	 * Sets the expando row persistence.
	 *
	 * @param expandoRowPersistence the expando row persistence
	 */
	public void setExpandoRowPersistence(
		ExpandoRowPersistence expandoRowPersistence) {

		this.expandoRowPersistence = expandoRowPersistence;
	}

	public void afterPropertiesSet() {
		persistedModelLocalServiceRegistry.register(
			"com.liferay.commerce.account.model.CommerceAccount",
			commerceAccountLocalService);
	}

	public void destroy() {
		persistedModelLocalServiceRegistry.unregister(
			"com.liferay.commerce.account.model.CommerceAccount");
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return CommerceAccountLocalService.class.getName();
	}

	protected Class<?> getModelClass() {
		return CommerceAccount.class;
	}

	protected String getModelClassName() {
		return CommerceAccount.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = commerceAccountPersistence.getDataSource();

			DB db = DBManagerUtil.getDB();

			sql = db.buildSQL(sql);
			sql = PortalUtil.transformSQL(sql);

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(
				dataSource, sql);

			sqlUpdate.update();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(type = CommerceAccountLocalService.class)
	protected CommerceAccountLocalService commerceAccountLocalService;

	@BeanReference(type = CommerceAccountPersistence.class)
	protected CommerceAccountPersistence commerceAccountPersistence;

	@BeanReference(type = CommerceAccountFinder.class)
	protected CommerceAccountFinder commerceAccountFinder;

	@BeanReference(
		type = com.liferay.commerce.account.service.CommerceAccountGroupLocalService.class
	)
	protected
		com.liferay.commerce.account.service.CommerceAccountGroupLocalService
			commerceAccountGroupLocalService;

	@BeanReference(type = CommerceAccountGroupPersistence.class)
	protected CommerceAccountGroupPersistence commerceAccountGroupPersistence;

	@BeanReference(
		type = com.liferay.commerce.account.service.CommerceAccountGroupCommerceAccountRelLocalService.class
	)
	protected com.liferay.commerce.account.service.
		CommerceAccountGroupCommerceAccountRelLocalService
			commerceAccountGroupCommerceAccountRelLocalService;

	@BeanReference(
		type = CommerceAccountGroupCommerceAccountRelPersistence.class
	)
	protected CommerceAccountGroupCommerceAccountRelPersistence
		commerceAccountGroupCommerceAccountRelPersistence;

	@BeanReference(
		type = com.liferay.commerce.account.service.CommerceAccountGroupRelLocalService.class
	)
	protected
		com.liferay.commerce.account.service.CommerceAccountGroupRelLocalService
			commerceAccountGroupRelLocalService;

	@BeanReference(type = CommerceAccountGroupRelPersistence.class)
	protected CommerceAccountGroupRelPersistence
		commerceAccountGroupRelPersistence;

	@BeanReference(
		type = com.liferay.commerce.account.service.CommerceAccountOrganizationRelLocalService.class
	)
	protected com.liferay.commerce.account.service.
		CommerceAccountOrganizationRelLocalService
			commerceAccountOrganizationRelLocalService;

	@BeanReference(type = CommerceAccountOrganizationRelPersistence.class)
	protected CommerceAccountOrganizationRelPersistence
		commerceAccountOrganizationRelPersistence;

	@BeanReference(
		type = com.liferay.commerce.account.service.CommerceAccountUserRelLocalService.class
	)
	protected
		com.liferay.commerce.account.service.CommerceAccountUserRelLocalService
			commerceAccountUserRelLocalService;

	@BeanReference(type = CommerceAccountUserRelPersistence.class)
	protected CommerceAccountUserRelPersistence
		commerceAccountUserRelPersistence;

	@ServiceReference(
		type = com.liferay.counter.kernel.service.CounterLocalService.class
	)
	protected com.liferay.counter.kernel.service.CounterLocalService
		counterLocalService;

	@ServiceReference(
		type = com.liferay.portal.kernel.service.ClassNameLocalService.class
	)
	protected com.liferay.portal.kernel.service.ClassNameLocalService
		classNameLocalService;

	@ServiceReference(type = ClassNamePersistence.class)
	protected ClassNamePersistence classNamePersistence;

	@ServiceReference(
		type = com.liferay.portal.kernel.service.GroupLocalService.class
	)
	protected com.liferay.portal.kernel.service.GroupLocalService
		groupLocalService;

	@ServiceReference(type = GroupPersistence.class)
	protected GroupPersistence groupPersistence;

	@ServiceReference(
		type = com.liferay.portal.kernel.service.ResourceLocalService.class
	)
	protected com.liferay.portal.kernel.service.ResourceLocalService
		resourceLocalService;

	@ServiceReference(
		type = com.liferay.portal.kernel.service.RoleLocalService.class
	)
	protected com.liferay.portal.kernel.service.RoleLocalService
		roleLocalService;

	@ServiceReference(type = RolePersistence.class)
	protected RolePersistence rolePersistence;

	@ServiceReference(
		type = com.liferay.portal.kernel.service.UserLocalService.class
	)
	protected com.liferay.portal.kernel.service.UserLocalService
		userLocalService;

	@ServiceReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;

	@ServiceReference(
		type = com.liferay.expando.kernel.service.ExpandoRowLocalService.class
	)
	protected com.liferay.expando.kernel.service.ExpandoRowLocalService
		expandoRowLocalService;

	@ServiceReference(type = ExpandoRowPersistence.class)
	protected ExpandoRowPersistence expandoRowPersistence;

	@ServiceReference(type = PersistedModelLocalServiceRegistry.class)
	protected PersistedModelLocalServiceRegistry
		persistedModelLocalServiceRegistry;

}