/*
 * Copyright (c) 2024 WSO2 LLC. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 LLC. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.apimgt.api;

import com.jayway.jsonpath.JsonPath;
import org.osgi.service.component.annotations.Component;
import org.wso2.carbon.apimgt.api.model.LlmProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DefaultLLMPayloadHandler is the default implementation of the LLMPayloadHandler interface.
 * It handles the extraction of metadata from Large Language Model (LLM) payloads.
 */

@Component(
        name = "custom.llm.provider.service",
        immediate = true,
        service = LlmProviderService.class
)
public class CustomLlmProviderService implements LlmProviderService {
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





    @Override
    public String getType() {
        return "Default";
    }

    @Override
    public LlmProvider registerLlmProvider(String organization, String apiDefinitionFilePath) throws APIManagementException {

        return null;
    }

}
