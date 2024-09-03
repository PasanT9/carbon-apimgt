/*
 * Copyright (c) 2024, WSO2 LLC. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 LLC. licenses this file to you under the Apache License,
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

/**
 * This class represents a LLM (Large Language Model) Provider.
 */
public class LlmProvider implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id = null;
    private String name = null;
    private String description = null;
    private String apiVersion = null;
    private String organization = null;
    private String apiDefinition = null;
    private String configurations = null;
    private boolean builtInSupport = false;

    public boolean isBuiltInSupport() {

        return builtInSupport;
    }

    public void setBuiltInSupport(boolean builtInSupport) {

        this.builtInSupport = builtInSupport;
    }

    public String getConfigurations() {

        return this.configurations;
    }

    public void setConfigurations(String configurations) {

        this.configurations = configurations;
    }

    /**
     * Gets the organization associated with this LLM Provider.
     *
     * @return the organization as a {@link String}
     */
    public String getOrganization() {
        return organization;
    }

    /**
     * Sets the organization associated with this LLM Provider.
     *
     * @param organization the organization to set
     */
    public void setOrganization(String organization) {
        this.organization = organization;
    }

    /**
     * Gets the ID of the LLM Provider.
     *
     * @return the ID as a {@link String}
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the ID of the LLM Provider.
     *
     * @param id the ID to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the name of the LLM Provider.
     *
     * @return the name as a {@link String}
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the LLM Provider.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the version of the LLM Provider.
     *
     * @return the version as a {@link String}
     */
    public String getApiVersion() {
        return apiVersion;
    }

    /**
     * Sets the version of the LLM Provider.
     *
     * @param apiVersion the version to set
     */
    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    /**
     * Gets the description of the LLM Provider.
     *
     * @return the description as a {@link String}
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the LLM Provider.
     *
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the API definition of the LLM Provider.
     *
     * @return the API definition as a {@link String}
     */
    public String getApiDefinition() {
        return apiDefinition;
    }

    /**
     * Sets the API definition of the LLM Provider.
     *
     * @param apiDefinition the API definition to set
     */
    public void setApiDefinition(String apiDefinition) {
        this.apiDefinition = apiDefinition;
    }
}
