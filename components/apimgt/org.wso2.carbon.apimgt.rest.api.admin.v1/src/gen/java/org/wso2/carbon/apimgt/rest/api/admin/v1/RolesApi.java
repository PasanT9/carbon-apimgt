package org.wso2.carbon.apimgt.rest.api.admin.v1;

import org.wso2.carbon.apimgt.rest.api.admin.v1.dto.ErrorDTO;
import org.wso2.carbon.apimgt.rest.api.admin.v1.impl.RolesApiServiceImpl;
import org.wso2.carbon.apimgt.api.APIManagementException;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import io.swagger.annotations.*;

import org.apache.cxf.jaxrs.ext.MessageContext;
@Path("/roles")

@Api(description = "the roles API")




public class RolesApi  {

  @Context MessageContext securityContext;

RolesApiService delegate = new RolesApiServiceImpl();


    @HEAD
    @Path("/{roleId}")
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Check Whether Given Role Name already Exist", notes = "Using this operation, user can check a given role name exists or not. ", response = Void.class, authorizations = {
        @Authorization(value = "OAuth2Security", scopes = {
            @AuthorizationScope(scope = "apim:admin", description = "Manage all admin operations"),
            @AuthorizationScope(scope = "apim:role_manage", description = "Manage system roles")
        })
    }, tags={ "Roles" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK. Requested role name exists.", response = Void.class),
        @ApiResponse(code = 404, message = "Not Found. The specified resource does not exist.", response = ErrorDTO.class),
        @ApiResponse(code = 500, message = "Internal Server Error.", response = ErrorDTO.class) })
    public Response validateSystemRole(@ApiParam(value = "The Base 64 URL encoded role name with domain. If the given role is in PRIMARY user-store, role ID should be derived as Base64URLEncode(role-name). If the given role is in secondary user-store, role ID should be derived as Base64URLEncode({user-store-name}/{role-name}). ",required=true) @PathParam("roleId") String roleId) throws APIManagementException{
        return delegate.validateSystemRole(roleId, securityContext);
    }
}
