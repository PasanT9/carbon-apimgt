package org.wso2.carbon.apimgt.api;

import org.osgi.service.component.annotations.Component;
import org.wso2.carbon.apimgt.api.model.LlmProvider;
import java.util.ArrayList;
import java.util.List;

/**
 * AzureOpenAiLlmProviderService for Azure OpenAI-specific LLM provider registration.
 */
@Component(
        name = "azureOpenAi.llm.provider.service",
        immediate = true,
        service = LlmProviderService.class
)
public class AzureOpenAiLlmProviderService extends InBuiltLlmProviderService {

    @Override
    public String getType() {
        return "azureOpenAi";
    }

    @Override
    public LlmProvider registerLlmProvider(String organization, String apiDefinitionFilePath) throws APIManagementException {

        LlmProvider llmProvider = new LlmProvider();
        llmProvider.setName("AzureOpenAI");
        llmProvider.setApiVersion("1.0.0");
        llmProvider.setOrganization(organization);
        llmProvider.setDescription("Azure OpenAI service");
        llmProvider.setBuiltInSupport(true);

        llmProvider.setApiDefinition(readApiDefinition(apiDefinitionFilePath + "azure_api.yaml"));

        LlmProviderConfiguration llmProviderConfiguration = new LlmProviderConfiguration();
        List<String> additionalHeader = new ArrayList<>();
        additionalHeader.add("api-key");
        llmProviderConfiguration.setAdditionalHeaders(additionalHeader);
        llmProviderConfiguration.setPayloadHandler(this.getType());

        List<LlmProviderMetadata> llmProviderMetadata = new ArrayList<>();
        llmProviderMetadata.add(new LlmProviderMetadata("model", "payload", "$.model"));
        llmProviderMetadata.add(new LlmProviderMetadata("promptTokenCount", "payload", "$.usage.prompt_tokens"));
        llmProviderMetadata.add(new LlmProviderMetadata("completionTokenCount", "payload", "$.usage.completion_tokens"));
        llmProviderMetadata.add(new LlmProviderMetadata("totalTokenCount", "payload", "$.usage.total_tokens"));
        llmProviderConfiguration.setMetadata(llmProviderMetadata);

        llmProvider.setConfigurations(llmProviderConfiguration.toJsonString());
        return llmProvider;
    }
}
