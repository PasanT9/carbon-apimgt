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

package org.wso2.carbon.apimgt.gateway.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.AbstractSynapseHandler;
import org.apache.synapse.MessageContext;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.apache.synapse.transport.passthru.util.RelayUtils;
import org.json.XML;
import org.wso2.carbon.apimgt.api.APIManagementException;
import org.wso2.carbon.apimgt.gateway.APIMgtGatewayConstants;
import org.wso2.carbon.apimgt.api.LlmProviderService;
import org.wso2.carbon.apimgt.gateway.internal.DataHolder;
import org.wso2.carbon.apimgt.gateway.internal.ServiceReferenceHolder;
import org.wso2.carbon.apimgt.api.LlmProviderConfiguration;
import org.wso2.carbon.apimgt.api.LlmProviderMetadata;

import javax.ws.rs.core.MediaType;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.Map;

/**
 * AiAPIHandler handles AI-specific API requests and responses.
 * It extends the AbstractSynapseHandler to integrate with the Synapse MessageContext.
 */
public class AiApiHandler extends AbstractSynapseHandler {

    private static final Log log = LogFactory.getLog(AiApiHandler.class);

    /**
     * Handles the incoming request flow.
     *
     * @param messageContext the Synapse MessageContext
     * @return true if the handling is successful, otherwise false
     */
    @Override
    public boolean handleRequestInFlow(MessageContext messageContext) {

//        try {
//            return processMessage(messageContext, true);
//        } catch (APIManagementException e) {
//            log.error("Error occurred while processing AI API", e);
//        }
        return true;
    }

    /**
     * Handles the outgoing request flow.
     *
     * @param messageContext the Synapse MessageContext
     * @return true if the handling is successful, otherwise false
     */
    @Override
    public boolean handleRequestOutFlow(MessageContext messageContext) {

        return true;
    }

    /**
     * Handles the incoming response flow.
     *
     * @param messageContext the Synapse MessageContext
     * @return true if the handling is successful, otherwise false
     */
    @Override
    public boolean handleResponseInFlow(MessageContext messageContext) {

        try {
            return processMessage(messageContext);
        } catch (APIManagementException | XMLStreamException | IOException e) {
            log.error("Error occurred while processing AI API", e);
        }
        return true;
    }

    /**
     * Handles the outgoing response flow.
     *
     * @param messageContext the Synapse MessageContext
     * @return true if the handling is successful, otherwise false
     */
    @Override
    public boolean handleResponseOutFlow(MessageContext messageContext) {

        return true;
    }

    /**
     * Processes the message to extract and set LLM metadata.
     *
     * @param messageContext the Synapse MessageContext
     * @return true if the processing is successful, otherwise false
     */
    private boolean processMessage(MessageContext messageContext)
            throws APIManagementException, XMLStreamException, IOException {

        // TODO: Get LLM Provider and version from message context
        String UUID = "";
        String providerConfigurations = DataHolder.getInstance().getLlmProviderConfigurations(UUID);

        LlmProviderConfiguration config = parseLlmProviderConfig(providerConfigurations);
        LlmProviderService payloadHandler = ServiceReferenceHolder.getInstance().getLlmPayloadHandler(config.getPayloadHandler());
        String payload = null;
        org.apache.axis2.context.MessageContext axis2MessageContext =
                ((Axis2MessageContext) messageContext).getAxis2MessageContext();
        for(LlmProviderMetadata metadata:config.getMetadata()) {
            if("payload".equals(metadata.getInputSource())){
                RelayUtils.buildMessage(axis2MessageContext);
                payload = getPayload(axis2MessageContext);
                break;
            }
        }
        Map<String, String> metadataMap = payloadHandler.getResponseMetadata(payload, (Map<String, String>) axis2MessageContext.getProperty(org.apache.axis2.context.MessageContext.TRANSPORT_HEADERS), null, config.getMetadata());

        axis2MessageContext.setProperty("AI_API_METADATA", metadataMap);
        return true;
    }

    public LlmProviderConfiguration parseLlmProviderConfig(String providerConfigurations) throws APIManagementException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(providerConfigurations, LlmProviderConfiguration.class);
        } catch (JsonProcessingException e) {
            throw new APIManagementException("Error occurred while parsing LLM Provider configuration", e);
        }
    }

    /**
     * Extracts the payload from the Axis2 MessageContext based on content type.
     *
     * @param axis2MessageContext the Axis2 MessageContext
     * @return the extracted payload as a String
     * @throws IOException        if an I/O error occurs
     * @throws XMLStreamException if an XML parsing error occurs
     */
    private String getPayload(org.apache.axis2.context.MessageContext axis2MessageContext)
            throws IOException, XMLStreamException {
        RelayUtils.buildMessage(axis2MessageContext);
        String contentType = (String) axis2MessageContext.getProperty(APIMgtGatewayConstants.REST_CONTENT_TYPE);
        if (contentType != null) {
            String normalizedContentType = contentType.toLowerCase();

            if (normalizedContentType.contains(MediaType.APPLICATION_XML) ||
                    normalizedContentType.contains(MediaType.TEXT_XML)) {
                return axis2MessageContext.getEnvelope().getBody().getFirstElement().toString();
            } else if (normalizedContentType.contains(MediaType.APPLICATION_JSON)) {
                String jsonString = axis2MessageContext.getEnvelope().getBody().getFirstElement().toString();
                jsonString = jsonString
                        .substring(jsonString.indexOf(">") + 1, jsonString.lastIndexOf("</jsonObject>"));
                return XML.toJSONObject(jsonString).toString();
            } else if (normalizedContentType.contains(MediaType.TEXT_PLAIN)) {
                return axis2MessageContext.getEnvelope().getBody().getFirstElement().getText();
            }
        } else {
            log.warn("Unrecognized Content-Type. Handling as text/plain");
            return axis2MessageContext.getEnvelope().getBody().getFirstElement().getText();
        }
        return null;
    }
}
