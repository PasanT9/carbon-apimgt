package org.wso2.carbon.apimgt.api.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.apimgt.api.APIManagementException;

public interface LLMPayloadHandler {

    Log log = LogFactory.getLog(LLMPayloadHandler.class);

    void initConfiguration(String modelPath, String requestTokensPath, String responseTokensPath, String totalTokensPath);

    String getModel(String payload) throws APIManagementException;

    int getRequestTokenCount(String payload) throws APIManagementException;

    int getResponseTokenCount(String payload) throws APIManagementException;

    int getTotalTokenCount(String payload) throws APIManagementException;
}
