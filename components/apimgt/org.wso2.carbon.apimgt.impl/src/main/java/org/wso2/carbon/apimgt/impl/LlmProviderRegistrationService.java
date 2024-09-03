package org.wso2.carbon.apimgt.impl;

import org.wso2.carbon.apimgt.api.APIAdmin;
import org.wso2.carbon.apimgt.api.APIManagementException;
import org.wso2.carbon.apimgt.api.InBuiltLlmProviderService;
import org.wso2.carbon.apimgt.api.LlmProviderService;
import org.wso2.carbon.apimgt.api.model.LlmProvider;
import org.wso2.carbon.apimgt.impl.internal.ServiceReferenceHolder;
import org.wso2.carbon.utils.CarbonUtils;

import java.util.List;
import java.util.Map;

public class LlmProviderRegistrationService {

    public static void registerDefaultLLMProviders(String organization) throws APIManagementException {

        APIAdmin apiAdmin = new APIAdminImpl();
        List<String> predefinedProviders = apiAdmin.getInBuiltPayloadHandlers(organization);
        Map<String, LlmProviderService> llmPayloadHandlerMap =
                ServiceReferenceHolder.getInstance().getLlmPayloadHandlerMap();
        String apiDefinitionFilePath = CarbonUtils.getCarbonHome() + APIConstants.AIAPI.AI_API_DEFINITION_FILE_PATH;
        for (Map.Entry<String, LlmProviderService> entry : llmPayloadHandlerMap.entrySet()) {
            LlmProviderService llmProviderService = entry.getValue();
            if (llmProviderService instanceof InBuiltLlmProviderService && !predefinedProviders.contains(llmProviderService.getType())) {
                LlmProvider llmProvider = llmProviderService.registerLlmProvider(organization, apiDefinitionFilePath);
                apiAdmin.addLlmProvider(llmProvider);
            } else {
                System.out.println("Skipping non-AbstractLlmProviderService: " + llmProviderService.getClass().getName());
            }
        }
    }
}
