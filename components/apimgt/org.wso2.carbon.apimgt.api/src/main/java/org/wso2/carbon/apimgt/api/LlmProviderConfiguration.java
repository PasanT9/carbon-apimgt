package org.wso2.carbon.apimgt.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.wso2.carbon.apimgt.api.APIManagementException;
import org.wso2.carbon.apimgt.api.LlmProviderMetadata;

import java.io.IOException;
import java.util.List;

public class LlmProviderConfiguration {

    public LlmProviderConfiguration() {}

    @JsonCreator
    public LlmProviderConfiguration(
            @JsonProperty("payloadHandler") String payloadHandler,
            @JsonProperty("metadata") List<LlmProviderMetadata> metadata,
            @JsonProperty("additionalHeaders") List<String> additionalHeaders,
            @JsonProperty("additionalQueryParams") List<String> additionalQueryParams) {
        this.payloadHandler = payloadHandler;
        this.metadata = metadata;
        this.additionalHeaders = additionalHeaders;
        this.additionalQueryParams = additionalQueryParams;
    }

    @JsonProperty("payloadHandler")
    private String payloadHandler;

    @JsonProperty("metadata")
    private List<LlmProviderMetadata> metadata;

    @JsonProperty("additionalHeaders")
    private List<String> additionalHeaders;

    @JsonProperty("additionalQueryParams")
    private List<String> additionalQueryParams;

    public String getPayloadHandler() {

        return payloadHandler;
    }

    public void setPayloadHandler(String payloadHandler) {

        this.payloadHandler = payloadHandler;
    }

    public List<LlmProviderMetadata> getMetadata() {

        return metadata;
    }

    public void setMetadata(List<LlmProviderMetadata> metadata) {

        this.metadata = metadata;
    }

    public List<String> getAdditionalHeaders() {

        return additionalHeaders;
    }

    public void setAdditionalHeaders(List<String> additionalHeaders) {

        this.additionalHeaders = additionalHeaders;
    }

    public List<String> getAdditionalQueryParams() {

        return additionalQueryParams;
    }

    public void setAdditionalQueryParams(List<String> additionalQueryParams) {

        this.additionalQueryParams = additionalQueryParams;
    }

    public String toJsonString() throws APIManagementException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (IOException e) {
            throw new APIManagementException("Error occurred while parsing LLM Provider configuration");
        }
    }
}
