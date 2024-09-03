/*
 * Copyright (c) 2024 WSO2 LLC. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 LLC. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.apimgt.rest.api.admin.v1.impl;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.apimgt.api.APIAdmin;
import org.wso2.carbon.apimgt.api.APIManagementException;
import org.wso2.carbon.apimgt.api.model.LlmProvider;
import org.wso2.carbon.apimgt.impl.APIAdminImpl;
import org.wso2.carbon.apimgt.impl.APIConstants;
import org.wso2.carbon.apimgt.impl.LlmProviderRegistrationService;
import org.wso2.carbon.apimgt.impl.utils.APIUtil;
import org.wso2.carbon.apimgt.rest.api.admin.v1.LlmProvidersApiService;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.MessageContext;

import org.wso2.carbon.apimgt.rest.api.admin.v1.dto.LLMProviderResponseDTO;
import org.wso2.carbon.apimgt.rest.api.admin.v1.dto.LLMProviderSummaryResponseListDTO;
import org.wso2.carbon.apimgt.rest.api.admin.v1.utils.mappings.LLMProviderMappingUtil;
import org.wso2.carbon.apimgt.rest.api.common.RestApiCommonUtil;
import org.wso2.carbon.apimgt.rest.api.common.RestApiConstants;
import org.wso2.carbon.apimgt.rest.api.util.utils.RestApiUtil;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.io.InputStream;
import javax.ws.rs.core.Response;

public class LlmProvidersApiServiceImpl implements LlmProvidersApiService {

    private static final Log log = LogFactory.getLog(LlmProvidersApiServiceImpl.class);

    @Override
    public Response addLlmProvider(String id, String name, String apiVersion, String description, String configurations, InputStream apiDefinitionInputStream, Attachment apiDefinitionDetail, MessageContext messageContext) throws APIManagementException {

        APIAdmin apiAdmin = new APIAdminImpl();
        try {
            LlmProvider provider = new LlmProvider();
            provider.setName(name);
            provider.setApiVersion(apiVersion);
            provider.setOrganization(RestApiUtil.getValidatedOrganization(messageContext));
            provider.setDescription(description);
            provider.setBuiltInSupport(false);
            if (apiDefinitionInputStream != null) {
                provider.setApiDefinition(IOUtils.toString(apiDefinitionInputStream, StandardCharsets.UTF_8));
            }
            provider.setConfigurations(configurations);
            LLMProviderResponseDTO result =
                    LLMProviderMappingUtil.fromProviderToProviderResponseDTO(apiAdmin.addLlmProvider(provider));
            URI location = new URI(RestApiConstants.RESOURCE_PATH_LLM_PROVIDER + "/" + result.getId());
            return Response.created(location).entity(result).build();
        } catch (IOException e) {
            handleException("Error while reading API definition InputStream", e);
        } catch (URISyntaxException e) {
            handleException("Error while creating URI for new LLM Provider", e);
        }
        return null;
    }

    /**
     * Deletes a Large Language Model (LLM) provider by its ID.
     *
     * @param llmProviderId  The ID of the LLM provider to be deleted.
     * @param messageContext The message context containing necessary information for the operation.
     * @return A Response object indicating the result of the delete operation.
     * @throws APIManagementException If an error occurs while deleting the LLM provider.
     */
    @Override
    public Response deleteLlmProvider(String llmProviderId, MessageContext messageContext)
            throws APIManagementException {

        APIAdmin apiAdmin = new APIAdminImpl();
        String organization = RestApiUtil.getValidatedOrganization(messageContext);
        try {
            apiAdmin.deleteLlmProvider(organization, llmProviderId);
            String info = "{'id':'" + llmProviderId + "'}";
            APIUtil.logAuditMessage(APIConstants.AuditLogConstants.GATEWAY_ENVIRONMENTS, info,
                    APIConstants.AuditLogConstants.DELETED, RestApiCommonUtil.getLoggedInUsername());
            return Response.ok().build();
        } catch (APIManagementException e) {
            handleException("Error while deleting LLM Provider with id: " + llmProviderId, e);
        }
        return null;
    }

    /**
     * Retrieves a list of all Large Language Model (LLM) providers for the specified organization.
     *
     * @param messageContext The message context containing necessary information for the operation.
     * @return A Response object containing the list of LLM providers.
     * @throws APIManagementException If an error occurs while retrieving the LLM providers.
     */
    @Override
    public Response getLlmProviders(MessageContext messageContext) throws APIManagementException {

        APIAdmin apiAdmin = new APIAdminImpl();
        String organization = RestApiUtil.getValidatedOrganization(messageContext);
        try {
            List<LlmProvider> LlmProviderList = apiAdmin.getLlmProvidersByOrg(organization);
            LLMProviderSummaryResponseListDTO providerListDTO =
                    LLMProviderMappingUtil.fromProviderSummaryListToProviderSummaryListDTO(LlmProviderList);
            return Response.ok().entity(providerListDTO).build();
        } catch (APIManagementException e) {
            handleException("Error while retrieving all LLM Providers", e);
        }
        return null;
    }

    @Override
    public Response updateLlmProvider(String llmProviderId, String id, String name, String apiVersion, String description, String configurations, InputStream apiDefinitionInputStream, Attachment apiDefinitionDetail, MessageContext messageContext) throws APIManagementException {

        APIAdmin apiAdmin = new APIAdminImpl();
        try {
            LlmProvider provider = new LlmProvider();
            provider.setId(llmProviderId);
            provider.setDescription(description);
            if (apiDefinitionInputStream != null) {
                provider.setApiDefinition(IOUtils.toString(apiDefinitionInputStream, StandardCharsets.UTF_8));
            }
            provider.setOrganization(RestApiUtil.getValidatedOrganization(messageContext));
            provider.setConfigurations(configurations);
            LLMProviderResponseDTO result =
                    LLMProviderMappingUtil.fromProviderToProviderResponseDTO(apiAdmin.updateLlmProvider(provider));
            URI location = new URI(RestApiConstants.RESOURCE_PATH_LLM_PROVIDER + "/" + result.getId());
            String info = "{'id':'" + llmProviderId + "'}";
            APIUtil.logAuditMessage(APIConstants.AuditLogConstants.GATEWAY_ENVIRONMENTS, info,
                    APIConstants.AuditLogConstants.UPDATED, RestApiCommonUtil.getLoggedInUsername());
            return Response.ok(location).entity(result).build();
        } catch (IOException e) {
            handleException("Error while reading API definition InputStream", e);
        } catch (URISyntaxException e) {
            handleException("Error while creating URI for updated LLM Provider", e);
        } catch (APIManagementException e) {
            handleException("Error while updating LLM Provider with id: " + llmProviderId, e);
        }
        return null;
    }

    /**
     * Retrieves a specific Large Language Model (LLM) provider by its ID for the specified organization.
     *
     * @param llmProviderId  The ID of the LLM provider to be retrieved.
     * @param messageContext The message context containing necessary information for the operation.
     * @return A Response object containing the LLM provider details.
     * @throws APIManagementException If an error occurs while retrieving the LLM provider.
     */
    @Override
    public Response getLlmProvider(String llmProviderId, MessageContext messageContext) throws APIManagementException {

        APIAdmin apiAdmin = new APIAdminImpl();
        String organization = RestApiUtil.getValidatedOrganization(messageContext);
        try {
            LLMProviderResponseDTO result =
                    LLMProviderMappingUtil.fromProviderToProviderResponseDTO(apiAdmin.getLlmProvider(organization,
                            llmProviderId));
            return Response.ok().entity(result).build();
        } catch (APIManagementException e) {
            handleException("Error while retrieving LLM Provider with id: " + llmProviderId, e);
        }
        return null;
    }

    /**
     * Handles exceptions by logging the error message and throwing an {@link APIManagementException}.
     *
     * @param msg The error message to be logged and included in the exception.
     * @param t   The throwable cause of the exception.
     * @throws APIManagementException The exception to be thrown after logging the error.
     */
    private void handleException(String msg, Throwable t) throws APIManagementException {

        log.error(msg, t);
        throw new APIManagementException(msg, t);
    }
}
