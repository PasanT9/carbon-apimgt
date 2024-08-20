package org.wso2.carbon.apimgt.rest.api.admin.v1;

import org.wso2.carbon.apimgt.rest.api.admin.v1.*;
import org.wso2.carbon.apimgt.rest.api.admin.v1.dto.*;

import org.apache.cxf.jaxrs.ext.MessageContext;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;

import org.wso2.carbon.apimgt.api.APIManagementException;

import org.wso2.carbon.apimgt.rest.api.admin.v1.dto.ErrorDTO;
import java.io.File;
import org.wso2.carbon.apimgt.rest.api.admin.v1.dto.LLMProviderResponseDTO;
import org.wso2.carbon.apimgt.rest.api.admin.v1.dto.LLMProviderSummaryResponseListDTO;

import java.util.List;

import java.io.InputStream;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;


public interface LlmProvidersApiService {
      public Response addLlmProvider(String name, String id, String version, List<String> headers, List<String> queryParams, String description, InputStream apiDefinitionInputStream, Attachment apiDefinitionDetail, String modelPath, String totalTokensPath, String requestTokensPath, String responseTokensPath, MessageContext messageContext) throws APIManagementException;
      public Response deleteLlmProvider(String llmProviderId, MessageContext messageContext) throws APIManagementException;
      public Response getAllLlmProviders(MessageContext messageContext) throws APIManagementException;
      public Response getLlmProvider(String llmProviderId, MessageContext messageContext) throws APIManagementException;
      public Response updateLlmProvider(String llmProviderId, String name, String id, String version, List<String> headers, List<String> queryParams, String description, InputStream apiDefinitionInputStream, Attachment apiDefinitionDetail, String modelPath, String totalTokensPath, String requestTokensPath, String responseTokensPath, MessageContext messageContext) throws APIManagementException;
}
