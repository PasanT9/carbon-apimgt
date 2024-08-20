package org.wso2.carbon.apimgt.api.model;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import org.wso2.carbon.apimgt.api.APIManagementException;

public class DefaultLLMPayloadHandler implements LLMPayloadHandler {

    private String totalTokensPath;
    private String requestTokensPath;
    private String responseTokensPath;
    private String modelPath;

    /**
     * Fetches data from a JSON payload using the provided JSONPath.
     *
     * @param payload The JSON payload as a string.
     * @param path    The JSONPath expression to evaluate.
     * @return The extracted data as a string, or null if not found.
     * @throws APIManagementException if there is an error during JSON parsing or path evaluation.
     */
    public String fetchJsonPathData(String payload, String path) throws APIManagementException {

        if (payload == null || path == null) {
            throw new APIManagementException("Payload or path cannot be null.");
        }

        try {
            ReadContext ctx = JsonPath.parse(payload);
            Object data = ctx.read(path);
            return data != null ? data.toString() : null;
        } catch (Exception e) {
            throw new APIManagementException("Error occurred while fetching data from JSON using the provided JSONPath: " + path, e);
        }
    }

    /**
     * Initializes configuration paths for model, token counts, and analytics data extraction.
     *
     * @param modelPath          Path to extract model information.
     * @param requestTokensPath  Path to extract request token count.
     * @param responseTokensPath Path to extract response token count.
     * @param totalTokensPath    Path to extract total token count.
     */
    @Override
    public void initConfiguration(String modelPath, String requestTokensPath, String responseTokensPath, String totalTokensPath) {

        if (modelPath == null || requestTokensPath == null || responseTokensPath == null || totalTokensPath == null) {
            throw new IllegalArgumentException("Configuration paths cannot be null.");
        }

        this.modelPath = modelPath;
        this.requestTokensPath = requestTokensPath;
        this.responseTokensPath = responseTokensPath;
        this.totalTokensPath = totalTokensPath;
    }

    /**
     * Retrieves model information from the JSON payload.
     *
     * @param payload The JSON payload as a string.
     * @return The model information as a string.
     * @throws APIManagementException if there is an error during JSON parsing or path evaluation.
     */
    @Override
    public String getModel(String payload) throws APIManagementException {

        return fetchJsonPathData(payload, this.modelPath);
    }

    /**
     * Retrieves the request token count from the JSON payload.
     *
     * @param payload The JSON payload as a string.
     * @return The request token count.
     * @throws APIManagementException if there is an error during JSON parsing or path evaluation.
     */
    @Override
    public int getRequestTokenCount(String payload) throws APIManagementException {

        return Integer.parseInt(fetchJsonPathData(payload, this.requestTokensPath));
    }

    /**
     * Retrieves the response token count from the JSON payload.
     *
     * @param payload The JSON payload as a string.
     * @return The response token count.
     * @throws APIManagementException if there is an error during JSON parsing or path evaluation.
     */
    @Override
    public int getResponseTokenCount(String payload) throws APIManagementException {

        return Integer.parseInt(fetchJsonPathData(payload, this.responseTokensPath));
    }

    /**
     * Retrieves the total token count from the JSON payload.
     *
     * @param payload The JSON payload as a string.
     * @return The total token count.
     * @throws APIManagementException if there is an error during JSON parsing or path evaluation.
     */
    @Override
    public int getTotalTokenCount(String payload) throws APIManagementException {

        return Integer.parseInt(fetchJsonPathData(payload, this.totalTokensPath));
    }
}
