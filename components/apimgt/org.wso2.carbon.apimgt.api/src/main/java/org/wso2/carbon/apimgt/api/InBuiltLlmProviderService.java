package org.wso2.carbon.apimgt.api;

import com.jayway.jsonpath.JsonPath;
import org.wso2.carbon.apimgt.api.model.LlmProvider;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AbstractLlmProviderService handles the common logic for LLM provider services.
 */
public abstract class InBuiltLlmProviderService implements LlmProviderService {

    @Override
    public Map<String, String> getResponseMetadata(String payload, Map<String, String> headers,
                                                   Map<String, String> queryParams, List<LlmProviderMetadata> metadataList)
            throws APIManagementException {

        Map<String, String> extractedMetadata = new HashMap<>();

        try {
            for (LlmProviderMetadata metadata : metadataList) {
                String attributeName = metadata.getAttributeName();
                String inputSource = metadata.getInputSource();
                String attributeIdentifier = metadata.getAttributeIdentifier();

                if ("payload".equalsIgnoreCase(inputSource)) {
                    String extractedValue = JsonPath.read(payload, attributeIdentifier).toString();
                    extractedMetadata.put(attributeName, extractedValue);
                } else {
                    throw new APIManagementException("Only payload input source is supported.");
                }
            }
        } catch (Exception e) {
            throw new APIManagementException("Error extracting metadata from the payload", e);
        }

        return extractedMetadata;
    }

    /**
     * Reads the API definition from the specified file path.
     *
     * @param filePath The path to the API definition file.
     * @return The API definition as a string.
     * @throws APIManagementException if an error occurs while reading the file.
     */
    protected String readApiDefinition(String filePath) throws APIManagementException {
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new APIManagementException("Error reading API definition", e);
        }
    }

    @Override
    public abstract String getType();

    @Override
    public abstract LlmProvider registerLlmProvider(String organization, String apiDefinitionFilePath)
            throws APIManagementException;
}
