package org.wso2.carbon.apimgt.api;

import org.osgi.service.component.annotations.Component;
import org.wso2.carbon.apimgt.api.model.LlmProvider;
import java.util.ArrayList;
import java.util.List;

/**
 * OpenAiLlmProviderService for OpenAI-specific LLM provider registration.
 */
@Component(
        name = "openAi.llm.provider.service",
        immediate = true,
        service = LlmProviderService.class
)
public class OpenAiLlmProviderService extends InBuiltLlmProviderService {

    @Override
    public String getType() {
        return "openAi";
    }

    @Override
    public LlmProvider registerLlmProvider(String organization, String apiDefinitionFilePath) throws APIManagementException {

        LlmProvider llmProvider = new LlmProvider();
        llmProvider.setName("OpenAI");
        llmProvider.setApiVersion("1.0.0");
        llmProvider.setOrganization(organization);
        llmProvider.setDescription("OpenAI service");
        llmProvider.setBuiltInSupport(true);

        llmProvider.setApiDefinition(readApiDefinition(apiDefinitionFilePath + "openai_api.yaml"));

        LlmProviderConfiguration llmProviderConfiguration = new LlmProviderConfiguration();
        List<String> additionalHeader = new ArrayList<>();
        additionalHeader.add("Authorization");
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
