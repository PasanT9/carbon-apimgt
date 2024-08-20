package org.wso2.carbon.apimgt.rest.api.admin.v1.utils.mappings;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.apimgt.api.APIManagementException;
import org.wso2.carbon.apimgt.api.model.LlmProvider;
import org.wso2.carbon.apimgt.rest.api.admin.v1.dto.LLMProviderResponseDTO;
import org.wso2.carbon.apimgt.rest.api.admin.v1.dto.LLMProviderSummaryResponseDTO;
import org.wso2.carbon.apimgt.rest.api.admin.v1.dto.LLMProviderSummaryResponseListDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LlmProviderMappingUtil {

    private static final Log log = LogFactory.getLog(LlmProviderMappingUtil.class);

    private static void handleException(String msg, Throwable t) throws APIManagementException {

        log.error(msg, t);
        throw new APIManagementException(msg, t);
    }

    public static LLMProviderSummaryResponseListDTO fromProviderSummaryListToProviderSummaryListDTO(
            List<LlmProvider> llmProviderList) {

        LLMProviderSummaryResponseListDTO providerListDTO = new LLMProviderSummaryResponseListDTO();
        if (llmProviderList != null) {
            providerListDTO.setCount(llmProviderList.size());
            providerListDTO.setList(llmProviderList.stream()
                    .map(LlmProviderMappingUtil::fromProviderToProviderSummaryDTO).collect(Collectors.toList()));
        } else {
            providerListDTO.setCount(0);
            providerListDTO.setList(new ArrayList<>());
        }
        return providerListDTO;
    }

    public static LLMProviderResponseDTO fromProviderToProviderResponseDTO(LlmProvider llmProvider) {

        if (llmProvider == null) {
            return null;
        }

        LLMProviderResponseDTO llmProviderResponseDTO = new LLMProviderResponseDTO();
        llmProviderResponseDTO.setId(llmProvider.getId());
        llmProviderResponseDTO.setName(llmProvider.getName());
        llmProviderResponseDTO.setVersion(llmProvider.getVersion());
        llmProviderResponseDTO.setApiDefinition(llmProvider.getApiDefinition());
        llmProviderResponseDTO.setHeaders(fromMapToProviderParameterDTO(llmProvider.getHeaders()));
        llmProviderResponseDTO.setQueryParams(fromMapToProviderParameterDTO(llmProvider.getQueryParams()));
        llmProviderResponseDTO.setDescription(llmProvider.getDescription());
        llmProviderResponseDTO.setModelPath(llmProvider.getModelPath());
        llmProviderResponseDTO.setRequestTokensPath(llmProvider.getRequestTokensPath());
        llmProviderResponseDTO.setResponseTokensPath(llmProvider.getResponseTokensPath());
        llmProviderResponseDTO.setTotalTokensPath(llmProvider.getTotalTokensPath());
        return llmProviderResponseDTO;
    }

    public static Map<String, String> fromProviderParameterDTOToMap(List<String> parameterList)
            throws APIManagementException {

        Map<String, String> parameterMap = new HashMap<>();
        if (parameterList == null) {
            return parameterMap;
        }

        for (String parameter : parameterList) {
            try {
                JsonObject jsonObject = JsonParser.parseString(parameter).getAsJsonObject();
                String name = jsonObject.get("name").getAsString();
                String value = jsonObject.get("value").getAsString();
                parameterMap.put(name, value);
            } catch (JsonSyntaxException | IllegalStateException | NullPointerException e) {
                handleException("Error while converting parameter to map: " + parameter, e);
            }
        }
        return parameterMap;
    }

    public static List<String> fromMapToProviderParameterDTO(Map<String, String> parameterMap) {

        List<String> parameterList = new ArrayList<>();
        if (parameterMap == null) {
            return parameterList;
        }

        for (Map.Entry<String, String> entry : parameterMap.entrySet()) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("name", entry.getKey());
            jsonObject.addProperty("value", entry.getValue());
            parameterList.add(jsonObject.toString());
        }
        return parameterList;
    }

    public static LLMProviderSummaryResponseDTO fromProviderToProviderSummaryDTO(LlmProvider llmProvider) {

        if (llmProvider == null) {
            return null;
        }

        LLMProviderSummaryResponseDTO llmProviderSummaryDTO = new LLMProviderSummaryResponseDTO();
        llmProviderSummaryDTO.setId(llmProvider.getId());
        llmProviderSummaryDTO.setName(llmProvider.getName());
        llmProviderSummaryDTO.setVersion(llmProvider.getVersion());
        llmProviderSummaryDTO.setDescription(llmProvider.getDescription());
        return llmProviderSummaryDTO;
    }

}
