package org.wso2.carbon.apimgt.rest.api.admin.v1.impl;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.apimgt.api.APIAdmin;
import org.wso2.carbon.apimgt.api.APIManagementException;
import org.wso2.carbon.apimgt.api.model.LlmProvider;
import org.wso2.carbon.apimgt.impl.APIAdminImpl;
import org.wso2.carbon.apimgt.impl.APIConstants;
import org.wso2.carbon.apimgt.impl.utils.APIUtil;
import org.wso2.carbon.apimgt.rest.api.admin.v1.*;
import org.wso2.carbon.apimgt.rest.api.admin.v1.dto.*;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.MessageContext;

import org.wso2.carbon.apimgt.rest.api.admin.v1.utils.mappings.LlmProviderMappingUtil;
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

    private void handleException(String msg, Throwable t) throws APIManagementException {

        log.error(msg, t);
        throw new APIManagementException(msg, t);
    }

    @Override
    public Response addLlmProvider(String name, String id, String version, List<String> headers,
                                   List<String> queryParams, String description,
                                   InputStream apiDefinitionInputStream, Attachment apiDefinitionDetail,
                                   String modelPath, String totalTokensPath, String requestTokensPath,
                                   String responseTokensPath, MessageContext messageContext)
            throws APIManagementException {

        APIAdmin apiAdmin = new APIAdminImpl();
        String organization = RestApiUtil.getValidatedOrganization(messageContext);

        try (InputStream inputStream = apiDefinitionInputStream) {  // Proper resource management
            LlmProvider provider = new LlmProvider();
            provider.setName(name);
            provider.setVersion(version);
            provider.setDescription(description);
            provider.setApiDefinition(IOUtils.toString(inputStream, StandardCharsets.UTF_8));

            if (headers != null) {
                provider.setHeaders(LlmProviderMappingUtil.fromProviderParameterDTOToMap(headers));
            }
            if (queryParams != null) {
                provider.setQueryParams(LlmProviderMappingUtil.fromProviderParameterDTOToMap(queryParams));
            }

            provider.setModelPath(modelPath);
            provider.setRequestTokensPath(requestTokensPath);
            provider.setResponseTokensPath(responseTokensPath);
            provider.setTotalTokensPath(totalTokensPath);

            LLMProviderResponseDTO result = LlmProviderMappingUtil
                    .fromProviderToProviderResponseDTO(apiAdmin.addLlmProvider(organization, provider));
            URI location = new URI(RestApiConstants.RESOURCE_PATH_LLM_PROVIDER + "/" + result.getId());
            return Response.created(location).entity(result).build();
        } catch (IOException e) {
            handleException("Error while reading API definition InputStream", e);
        } catch (URISyntaxException e) {
            handleException("Error while creating URI for new LLM Provider", e);
        }
        return null;
    }

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

    @Override
    public Response getAllLlmProviders(MessageContext messageContext) throws APIManagementException {

        APIAdmin apiAdmin = new APIAdminImpl();
        String organization = RestApiUtil.getValidatedOrganization(messageContext);

        try {
            List<LlmProvider> llmProviderList = apiAdmin.getAllLlmProviders(organization);
            LLMProviderSummaryResponseListDTO providerListDTO = LlmProviderMappingUtil
                    .fromProviderSummaryListToProviderSummaryListDTO(llmProviderList);
            return Response.ok().entity(providerListDTO).build();
        } catch (APIManagementException e) {
            handleException("Error while retrieving all LLM Providers", e);
        }
        return null;
    }

    @Override
    public Response getLlmProvider(String llmProviderId, MessageContext messageContext)
            throws APIManagementException {

        APIAdmin apiAdmin = new APIAdminImpl();
        String organization = RestApiUtil.getValidatedOrganization(messageContext);

        try {
            LLMProviderResponseDTO result = LlmProviderMappingUtil
                    .fromProviderToProviderResponseDTO(apiAdmin.getLlmProvider(organization, llmProviderId));
            return Response.ok().entity(result).build();
        } catch (APIManagementException e) {
            handleException("Error while retrieving LLM Provider with id: " + llmProviderId, e);
        }
        return null;
    }

    @Override
    public Response updateLlmProvider(String llmProviderId, String name, String id, String version,
                                      List<String> headers, List<String> queryParams, String description,
                                      InputStream apiDefinitionInputStream, Attachment apiDefinitionDetail,
                                      String modelPath, String totalTokensPath, String requestTokensPath,
                                      String responseTokensPath, MessageContext messageContext)
            throws APIManagementException {

        APIAdmin apiAdmin = new APIAdminImpl();
        String organization = RestApiUtil.getValidatedOrganization(messageContext);

        try (InputStream inputStream = apiDefinitionInputStream) {
            LlmProvider provider = new LlmProvider();
            provider.setId(llmProviderId);
            provider.setName(name);
            provider.setVersion(version);
            provider.setDescription(description);
            provider.setApiDefinition(IOUtils.toString(inputStream, StandardCharsets.UTF_8));

            if (headers != null) {
                provider.setHeaders(LlmProviderMappingUtil.fromProviderParameterDTOToMap(headers));
            }
            if (queryParams != null) {
                provider.setQueryParams(LlmProviderMappingUtil.fromProviderParameterDTOToMap(queryParams));
            }

            provider.setModelPath(modelPath);
            provider.setRequestTokensPath(requestTokensPath);
            provider.setResponseTokensPath(responseTokensPath);
            provider.setTotalTokensPath(totalTokensPath);

            LLMProviderResponseDTO result = LlmProviderMappingUtil.
                    fromProviderToProviderResponseDTO(apiAdmin.updateLlmProvider(organization, provider));
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
}
