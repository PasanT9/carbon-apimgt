package org.wso2.carbon.apimgt.rest.api.admin.v1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.*;


import io.swagger.annotations.*;
import java.util.Objects;

import javax.xml.bind.annotation.*;
import org.wso2.carbon.apimgt.rest.api.common.annotations.Scope;
import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.Valid;



public class LLMProviderDTO   {
  
    private String id = null;
    private String name = null;
    private String version = null;
    private List<String> headers = new ArrayList<String>();
    private List<String> queryParams = new ArrayList<String>();
    private String description = null;
    private File apiDefinition = null;
    private String modelPath = null;
    private String totalTokensPath = null;
    private String requestTokensPath = null;
    private String responseTokensPath = null;

  /**
   **/
  public LLMProviderDTO id(String id) {
    this.id = id;
    return this;
  }

  
  @ApiModelProperty(example = "ece92bdc-e1e6-325c-b6f4-656208a041e9", value = "")
  @JsonProperty("id")
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }

  /**
   **/
  public LLMProviderDTO name(String name) {
    this.name = name;
    return this;
  }

  
  @ApiModelProperty(example = "open-ai", required = true, value = "")
  @JsonProperty("name")
  @NotNull
 @Pattern(regexp="^[a-zA-Z0-9_-]+$") @Size(min=1,max=255)  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  /**
   **/
  public LLMProviderDTO version(String version) {
    this.version = version;
    return this;
  }

  
  @ApiModelProperty(example = "1.0.0", value = "")
  @JsonProperty("version")
 @Size(min=1,max=255)  public String getVersion() {
    return version;
  }
  public void setVersion(String version) {
    this.version = version;
  }

  /**
   **/
  public LLMProviderDTO headers(List<String> headers) {
    this.headers = headers;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("headers")
  public List<String> getHeaders() {
    return headers;
  }
  public void setHeaders(List<String> headers) {
    this.headers = headers;
  }

  /**
   **/
  public LLMProviderDTO queryParams(List<String> queryParams) {
    this.queryParams = queryParams;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("queryParams")
  public List<String> getQueryParams() {
    return queryParams;
  }
  public void setQueryParams(List<String> queryParams) {
    this.queryParams = queryParams;
  }

  /**
   **/
  public LLMProviderDTO description(String description) {
    this.description = description;
    return this;
  }

  
  @ApiModelProperty(example = "OpenAI LLM Provider", value = "")
  @JsonProperty("description")
 @Size(max=1023)  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * OpenAPI specification
   **/
  public LLMProviderDTO apiDefinition(File apiDefinition) {
    this.apiDefinition = apiDefinition;
    return this;
  }

  
  @ApiModelProperty(value = "OpenAPI specification")
  @JsonProperty("apiDefinition")
  public File getApiDefinition() {
    return apiDefinition;
  }
  public void setApiDefinition(File apiDefinition) {
    this.apiDefinition = apiDefinition;
  }

  /**
   **/
  public LLMProviderDTO modelPath(String modelPath) {
    this.modelPath = modelPath;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("modelPath")
 @Size(min=1,max=255)  public String getModelPath() {
    return modelPath;
  }
  public void setModelPath(String modelPath) {
    this.modelPath = modelPath;
  }

  /**
   **/
  public LLMProviderDTO totalTokensPath(String totalTokensPath) {
    this.totalTokensPath = totalTokensPath;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("totalTokensPath")
 @Size(min=1,max=255)  public String getTotalTokensPath() {
    return totalTokensPath;
  }
  public void setTotalTokensPath(String totalTokensPath) {
    this.totalTokensPath = totalTokensPath;
  }

  /**
   **/
  public LLMProviderDTO requestTokensPath(String requestTokensPath) {
    this.requestTokensPath = requestTokensPath;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("requestTokensPath")
 @Size(min=1,max=255)  public String getRequestTokensPath() {
    return requestTokensPath;
  }
  public void setRequestTokensPath(String requestTokensPath) {
    this.requestTokensPath = requestTokensPath;
  }

  /**
   **/
  public LLMProviderDTO responseTokensPath(String responseTokensPath) {
    this.responseTokensPath = responseTokensPath;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("responseTokensPath")
 @Size(min=1,max=255)  public String getResponseTokensPath() {
    return responseTokensPath;
  }
  public void setResponseTokensPath(String responseTokensPath) {
    this.responseTokensPath = responseTokensPath;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LLMProviderDTO llMProvider = (LLMProviderDTO) o;
    return Objects.equals(id, llMProvider.id) &&
        Objects.equals(name, llMProvider.name) &&
        Objects.equals(version, llMProvider.version) &&
        Objects.equals(headers, llMProvider.headers) &&
        Objects.equals(queryParams, llMProvider.queryParams) &&
        Objects.equals(description, llMProvider.description) &&
        Objects.equals(apiDefinition, llMProvider.apiDefinition) &&
        Objects.equals(modelPath, llMProvider.modelPath) &&
        Objects.equals(totalTokensPath, llMProvider.totalTokensPath) &&
        Objects.equals(requestTokensPath, llMProvider.requestTokensPath) &&
        Objects.equals(responseTokensPath, llMProvider.responseTokensPath);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, version, headers, queryParams, description, apiDefinition, modelPath, totalTokensPath, requestTokensPath, responseTokensPath);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LLMProviderDTO {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
    sb.append("    headers: ").append(toIndentedString(headers)).append("\n");
    sb.append("    queryParams: ").append(toIndentedString(queryParams)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    apiDefinition: ").append(toIndentedString(apiDefinition)).append("\n");
    sb.append("    modelPath: ").append(toIndentedString(modelPath)).append("\n");
    sb.append("    totalTokensPath: ").append(toIndentedString(totalTokensPath)).append("\n");
    sb.append("    requestTokensPath: ").append(toIndentedString(requestTokensPath)).append("\n");
    sb.append("    responseTokensPath: ").append(toIndentedString(responseTokensPath)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

