package org.wso2.carbon.apimgt.gateway;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.wso2.carbon.apimgt.api.APIManagementException;
import org.wso2.carbon.apimgt.api.LlmProviderConfiguration;
import org.wso2.carbon.apimgt.gateway.internal.DataHolder;
import org.wso2.carbon.apimgt.gateway.internal.ServiceReferenceHolder;
import org.wso2.carbon.apimgt.impl.APIConstants;
import org.wso2.carbon.apimgt.impl.dto.EventHubConfigurationDto;
import org.wso2.carbon.apimgt.impl.utils.APIUtil;
import org.wso2.carbon.utils.multitenancy.MultitenantConstants;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.wso2.carbon.apimgt.gateway.APIMgtGatewayConstants.UTF8;

public class LlmProviderManager {

    private final EventHubConfigurationDto eventHubConfigurationDto;
    private static final LlmProviderManager llmProviderManager = new LlmProviderManager();

    private static final Log log = LogFactory.getLog(LlmProviderManager.class);

    public static LlmProviderManager getInstance() {
        return llmProviderManager;
    }


    public LlmProviderManager() {
        this.eventHubConfigurationDto = ServiceReferenceHolder.getInstance().getApiManagerConfigurationService()
                .getAPIManagerConfiguration().getEventHubConfigurationDto();
    }

    public void initializeLlmProviderConfigurations() {
        try {
            String responseString = invokeService("/llm-provider-configs");
            JSONObject responseJson = new JSONObject(responseString);

            JSONArray llmProviderConfigArray = responseJson.getJSONArray("apis");

            ObjectMapper objectMapper = new ObjectMapper();
            for (int i = 0; i < llmProviderConfigArray.length(); i++) {
                JSONObject apiObj = llmProviderConfigArray.getJSONObject(i);
                String llmProviderId = apiObj.getString("llmProviderId");
                String configurations = apiObj.getString("configurations");
                DataHolder.getInstance().addLlmProviderConfigurations(llmProviderId, configurations);
            }
            if (log.isDebugEnabled()) {
                log.debug("Response : " + responseJson);
            }
        } catch (IOException | APIManagementException ex) {
            log.error("Error while calling internal service API", ex);
        }
    }

    private String invokeService(String path) throws IOException, APIManagementException {

        String serviceURLStr = eventHubConfigurationDto.getServiceUrl().concat(APIConstants.INTERNAL_WEB_APP_EP);
        HttpGet method = new HttpGet(serviceURLStr + path);

        URL serviceURL = new URL(serviceURLStr + path);
        byte[] credentials = getServiceCredentials(eventHubConfigurationDto);
        int servicePort = serviceURL.getPort();
        String serviceProtocol = serviceURL.getProtocol();
        method.setHeader(APIConstants.AUTHORIZATION_HEADER_DEFAULT, APIConstants.AUTHORIZATION_BASIC
                + new String(credentials, StandardCharsets.UTF_8));
        HttpClient httpClient = APIUtil.getHttpClient(servicePort, serviceProtocol);
        try (CloseableHttpResponse httpResponse = APIUtil.executeHTTPRequestWithRetries(method, httpClient)){
            return EntityUtils.toString(httpResponse.getEntity(), UTF8);
        } catch (APIManagementException e) {
            throw new APIManagementException("Error while calling internal service", e);
        }
    }

    private byte[] getServiceCredentials(EventHubConfigurationDto eventHubConfigurationDto) {

        String username = eventHubConfigurationDto.getUsername();
        String pw = eventHubConfigurationDto.getPassword();
        return Base64.encodeBase64((username + APIConstants.DELEM_COLON + pw).getBytes
                (StandardCharsets.UTF_8));
    }

}
