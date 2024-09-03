package org.wso2.carbon.apimgt.rest.api.publisher.v1.impl;

import org.wso2.carbon.apimgt.api.APIAdmin;
import org.wso2.carbon.apimgt.api.APIManagementException;
import org.wso2.carbon.apimgt.api.model.LlmProvider;
import org.wso2.carbon.apimgt.impl.APIAdminImpl;
import org.wso2.carbon.apimgt.rest.api.common.RestApiCommonUtil;
import org.wso2.carbon.apimgt.rest.api.publisher.v1.*;
import org.wso2.carbon.apimgt.rest.api.publisher.v1.dto.*;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.MessageContext;

import org.wso2.carbon.apimgt.rest.api.publisher.v1.dto.ErrorDTO;
import org.wso2.carbon.apimgt.rest.api.publisher.v1.dto.LLMProviderSummaryResponseListDTO;
import org.wso2.carbon.apimgt.rest.api.util.utils.RestApiUtil;

import java.util.List;

import java.io.InputStream;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import static org.wso2.carbon.apimgt.impl.utils.APIUtil.handleException;

public class LlmProvidersApiServiceImpl implements LlmProvidersApiService {

    public Response getLlmProviders(MessageContext messageContext) throws APIManagementException {
//        String organization = RestApiCommonUtil.getLoggedInUserTenantDomain();
//        APIAdmin apiAdmin = new APIAdminImpl();
//
//        try {
//            List<LlmProvider> LlmProviderList = apiAdmin.getLlmProvidersByOrg(organization);
//            LLMProviderSummaryResponseListDTO providerListDTO =
//                    LLMProviderMappingUtil.fromProviderSummaryListToProviderSummaryListDTO(LlmProviderList);
//            return Response.ok().entity(providerListDTO).build();
//        } catch (APIManagementException e) {
//            handleException("Error while retrieving all LLM Providers", e);
//        }
        return null;
    }
}
