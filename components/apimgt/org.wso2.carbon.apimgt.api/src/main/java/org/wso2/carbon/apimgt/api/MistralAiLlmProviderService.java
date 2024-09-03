package org.wso2.carbon.apimgt.api;

import org.osgi.service.component.annotations.Component;
import org.wso2.carbon.apimgt.api.model.LlmProvider;
import java.util.ArrayList;
import java.util.List;

/**
 * MistralAiLlmProviderService for Mistral AI-specific LLM provider registration.
 */
@Component(
        name = "mistralAi.llm.provider.service",
        immediate = true,
        service = LlmProviderService.class
)
public class MistralAiLlmProviderService extends InBuiltLlmProviderService {

    @Override
    public String getType() {
        return "mistralAi";
    }

    @Override
    public LlmProvider registerLlmProvider(String organization, String apiDefinitionFilePath) throws APIManagementException {

        LlmProvider llmProvider = new LlmProvider();
        llmProvider.setName("MistralAI");
        llmProvider.setApiVersion("1.0.0");
        llmProvider.setOrganization(organization);
        llmProvider.setDescription("Mistral AI service");
        llmProvider.setBuiltInSupport(true);

        llmProvider.setApiDefinition(readApiDefinition(apiDefinitionFilePath + "mistral_api.yaml"));

        LlmProviderConfiguration llmProviderConfiguration = new LlmProviderConfiguration();
        List<String> additionalHeader = new ArrayList<>();
        additionalHeader.add("ApiKey");
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
