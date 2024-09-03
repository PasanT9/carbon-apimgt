package org.wso2.carbon.apimgt.impl.notifier.events;

public class LlmProviderEvent extends Event {

    private String llmProviderId;
    private String configurations;

    public LlmProviderEvent(String eventId, long timeStamp, String type, int tenantId, String tenantDomain,
                            String llmProviderId, String configurations) {

        this.eventId = eventId;
        this.timeStamp = timeStamp;
        this.type = type;
        this.tenantId = tenantId;
        this.tenantDomain = tenantDomain;
        this.llmProviderId = llmProviderId;
        this.configurations = configurations;
    }

}
