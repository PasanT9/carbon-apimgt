package org.wso2.carbon.apimgt.impl;

import org.json.JSONObject;
import org.wso2.carbon.apimgt.api.APIManagementException;
import org.wso2.carbon.apimgt.api.model.LlmProvider;
import org.wso2.carbon.apimgt.impl.notifier.events.LlmProviderEvent;
import org.wso2.carbon.apimgt.impl.utils.APIUtil;

import java.util.UUID;

public class LlmProviderNotificationSender {

    public void notify(String llmProviderId, String organization, String configurations, String action) throws APIManagementException {

        int tenantId = APIUtil.getInternalOrganizationId(organization);
        LlmProviderEvent llmProviderEvent = new LlmProviderEvent(UUID.randomUUID().toString(),
                System.currentTimeMillis(), action, tenantId, organization, llmProviderId, configurations);
        APIUtil.sendNotification(llmProviderEvent, APIConstants.NotifierType.LLM_PROVIDER.name());
    }
}
