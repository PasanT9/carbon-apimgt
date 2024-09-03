package org.wso2.carbon.apimgt.gateway;

public class AiApiMetadata {

    String model;
    String completionTokenCount;
    String promptTokenCount;
    String totalTokenCount;

    public String getModel() {

        return model;
    }

    public void setModel(String model) {

        this.model = model;
    }

    public String getCompletionTokenCount() {

        return completionTokenCount;
    }

    public void setCompletionTokenCount(String completionTokenCount) {

        this.completionTokenCount = completionTokenCount;
    }

    public String getPromptTokenCount() {

        return promptTokenCount;
    }

    public void setPromptTokenCount(String promptTokenCount) {

        this.promptTokenCount = promptTokenCount;
    }

    public String getTotalTokenCount() {

        return totalTokenCount;
    }

    public void setTotalTokenCount(String totalTokenCount) {

        this.totalTokenCount = totalTokenCount;
    }
}
