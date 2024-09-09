package org.wso2.carbon.apimgt.impl.notifier;

import org.wso2.carbon.apimgt.impl.APIConstants;
import org.wso2.carbon.apimgt.impl.notifier.events.Event;
import org.wso2.carbon.apimgt.impl.notifier.exceptions.NotifierException;

public class LLMProviderNotifier extends AbstractNotifier {

    @Override
    public boolean publishEvent(Event event) throws NotifierException {

        publishEventToEventHub(event);
        return true;
    }

    @Override
    public String getType() {

        return APIConstants.NotifierType.LLM_PROVIDER.name();
    }
}
