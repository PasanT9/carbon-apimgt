package org.wso2.carbon.apimgt.internal.service.impl;

import org.wso2.carbon.apimgt.api.APIAdmin;
import org.wso2.carbon.apimgt.api.APIManagementException;
import org.wso2.carbon.apimgt.api.model.LlmProvider;
import org.wso2.carbon.apimgt.impl.APIAdminImpl;
import org.wso2.carbon.apimgt.internal.service.*;

import org.apache.cxf.jaxrs.ext.MessageContext;
import org.wso2.carbon.apimgt.internal.service.dto.LLMProviderConfigDTO;
import org.wso2.carbon.apimgt.internal.service.dto.LLMProviderConfigListDTO;

import java.util.List;
import java.util.stream.Collectors;
import javax.ws.rs.core.Response;

public class LlmProviderConfigsApiServiceImpl implements LlmProviderConfigsApiService {

    public Response llmProviderConfigsGet(MessageContext messageContext) throws APIManagementException {

        APIAdmin admin = new APIAdminImpl();
        List<LlmProvider> llmProviderList = admin.getLlmProviderConfigurations();

        List<LLMProviderConfigDTO> llmProviderConfigDTOs = llmProviderList.stream()
                .map(llmProvider -> {
                    LLMProviderConfigDTO llmProviderConfigDTO = new LLMProviderConfigDTO();
                    llmProviderConfigDTO.setLlmProviderId(llmProvider.getId());
                    llmProviderConfigDTO.setConfigurations(llmProvider.getConfigurations());
                    return llmProviderConfigDTO;
                })
                .collect(Collectors.toList());

        LLMProviderConfigListDTO llmProviderConfigListDTO = new LLMProviderConfigListDTO();
        llmProviderConfigListDTO.setApis(llmProviderConfigDTOs);

        return Response.ok().entity(llmProviderConfigListDTO).build();
    }
}
