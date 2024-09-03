package org.wso2.carbon.apimgt.internal.service;

import org.wso2.carbon.apimgt.internal.service.dto.ErrorDTO;
import org.wso2.carbon.apimgt.internal.service.dto.LLMProviderConfigListDTO;
import org.wso2.carbon.apimgt.internal.service.LlmProviderConfigsApiService;
import org.wso2.carbon.apimgt.internal.service.impl.LlmProviderConfigsApiServiceImpl;
import org.wso2.carbon.apimgt.api.APIManagementException;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.inject.Inject;

import io.swagger.annotations.*;
import java.io.InputStream;

import org.apache.cxf.jaxrs.ext.MessageContext;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;

import java.util.Map;
import java.util.List;
import javax.validation.constraints.*;
@Path("/llm-provider-configs")

@Api(description = "the llm-provider-configs API")

@Produces({ "application/json" })


public class LlmProviderConfigsApi  {

  @Context MessageContext securityContext;

LlmProviderConfigsApiService delegate = new LlmProviderConfigsApiServiceImpl();


    @GET
    
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Retrieve persisted per LLM Provider configs", notes = "This retrieve the persisted LLM Provider configs. ", response = LLMProviderConfigListDTO.class, tags={ "LLMProviderConfigs" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Event Received success", response = LLMProviderConfigListDTO.class),
        @ApiResponse(code = 200, message = "Unexpected error", response = ErrorDTO.class) })
    public Response llmProviderConfigsGet() throws APIManagementException{
        return delegate.llmProviderConfigsGet(securityContext);
    }
}
