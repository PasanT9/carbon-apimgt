package org.wso2.carbon.apimgt.api;

import org.osgi.service.component.annotations.Component;
import org.wso2.carbon.apimgt.api.model.LlmProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Mistral AI LLM Provider Service.
 */
@Component(
        name = "mistralAi.llm.provider.service",
        immediate = true,
        service = LlmProviderService.class
)
public class MistralAiInLlmProviderService extends BuiltInLlmProviderService {

    @Override
    public String getType() {

        return APIConstants.AIAPIConstants.LLM_PROVIDER_SERVICE_MISTRALAI_CONNECTOR;
    }

    @Override
    public LlmProvider registerLlmProvider(String organization, String apiDefinitionFilePath)
            throws APIManagementException {

        try {
            LlmProvider llmProvider = new LlmProvider();
            llmProvider.setName(APIConstants.AIAPIConstants.LLM_PROVIDER_SERVICE_MISTRALAI_NAME);
            llmProvider.setApiVersion(APIConstants.AIAPIConstants.LLM_PROVIDER_SERVICE_MISTRALAI_VERSION);
            llmProvider.setOrganization(organization);
            llmProvider.setDescription(APIConstants.AIAPIConstants.LLM_PROVIDER_SERVICE_MISTRALAI_DESCRIPTION);
            llmProvider.setBuiltInSupport(true);

            llmProvider.setApiDefinition(readApiDefinition(
                    apiDefinitionFilePath
                            + APIConstants.AIAPIConstants
                            .LLM_PROVIDER_SERVICE_MISTRALAI_API_DEFINITION_FILE_NAME));

            LlmProviderConfiguration llmProviderConfiguration = new LlmProviderConfiguration();
            List<String> additionalHeader = new ArrayList<>();
            additionalHeader.add(APIConstants.AIAPIConstants.LLM_PROVIDER_SERVICE_MISTRALAI_KEY);
            llmProviderConfiguration.setAdditionalHeaders(additionalHeader);
            llmProviderConfiguration.setConnectorType(this.getType());

            List<LlmProviderMetadata> llmProviderMetadata = new ArrayList<>();
            llmProviderMetadata.add(new LlmProviderMetadata(
                    APIConstants.AIAPIConstants.LLM_PROVIDER_SERVICE_METADATA_MODEL,
                    APIConstants.AIAPIConstants.INPUT_SOURCE_PAYLOAD,
                    APIConstants.AIAPIConstants.LLM_PROVIDER_SERVICE_METADATA_IDENTIFIER_MODEL));
            llmProviderMetadata.add(new LlmProviderMetadata(
                    APIConstants.AIAPIConstants.LLM_PROVIDER_SERVICE_METADATA_PROMPT_TOKEN_COUNT,
                    APIConstants.AIAPIConstants.INPUT_SOURCE_PAYLOAD,
                    APIConstants.AIAPIConstants.LLM_PROVIDER_SERVICE_METADATA_IDENTIFIER_PROMPT_TOKEN_COUNT));
            llmProviderMetadata.add(new LlmProviderMetadata(
                    APIConstants.AIAPIConstants.LLM_PROVIDER_SERVICE_METADATA_COMPLETION_TOKEN_COUNT,
                    APIConstants.AIAPIConstants.INPUT_SOURCE_PAYLOAD,
                    APIConstants.AIAPIConstants
                            .LLM_PROVIDER_SERVICE_METADATA_IDENTIFIER_COMPLETION_TOKEN_COUNT));
            llmProviderMetadata.add(new LlmProviderMetadata(
                    APIConstants.AIAPIConstants.LLM_PROVIDER_SERVICE_METADATA_TOTAL_TOKEN_COUNT,
                    APIConstants.AIAPIConstants.INPUT_SOURCE_PAYLOAD,
                    APIConstants.AIAPIConstants.LLM_PROVIDER_SERVICE_METADATA_IDENTIFIER_TOTAL_TOKEN_COUNT));
            llmProviderConfiguration.setMetadata(llmProviderMetadata);

            llmProvider.setConfigurations(llmProviderConfiguration.toJsonString());
            return llmProvider;
        } catch (Exception e) {
            throw new APIManagementException("Error occurred when registering LLM Provider:" + this.getType());
        }
    }
}
