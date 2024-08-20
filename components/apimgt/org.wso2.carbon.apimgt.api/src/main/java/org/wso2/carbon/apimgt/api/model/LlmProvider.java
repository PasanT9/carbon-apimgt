/*
 * Copyright (c) 2021, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.apimgt.api.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represent a LLM Provider.
 */
public class LlmProvider implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id = null;
    private String name = null;
    private String description = null;
    private String version = null;
    private String apiDefinition = null;
    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> queryParams = new HashMap<>();
    private String totalTokensPath;
    private String requestTokensPath;
    private String responseTokensPath;
    private String modelPath;

    public String getId() {

        return id;
    }

    public void setId(String id) {

        this.id = id;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getVersion() {

        return version;
    }

    public void setVersion(String version) {

        this.version = version;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public String getApiDefinition() {

        return apiDefinition;
    }

    public void setApiDefinition(String apiDefinition) {

        this.apiDefinition = apiDefinition;
    }

    public Map<String, String> getHeaders() {

        return headers;
    }

    public void setHeaders(Map<String, String> headers) {

        this.headers = headers;
    }

    public Map<String, String> getQueryParams() {

        return queryParams;
    }

    public void setQueryParams(Map<String, String> queryParams) {

        this.queryParams = queryParams;
    }

    public String getTotalTokensPath() {

        return totalTokensPath;
    }

    public void setTotalTokensPath(String totalTokensPath) {

        this.totalTokensPath = totalTokensPath;
    }

    public String getRequestTokensPath() {

        return requestTokensPath;
    }

    public void setRequestTokensPath(String requestTokensPath) {

        this.requestTokensPath = requestTokensPath;
    }

    public String getResponseTokensPath() {

        return responseTokensPath;
    }

    public void setResponseTokensPath(String responseTokensPath) {

        this.responseTokensPath = responseTokensPath;
    }

    public String getModelPath() {

        return modelPath;
    }

    public void setModelPath(String modelPath) {

        this.modelPath = modelPath;
    }
}
