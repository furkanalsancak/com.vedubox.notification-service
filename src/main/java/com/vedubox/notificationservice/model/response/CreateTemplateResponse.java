package com.vedubox.notificationservice.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vedubox.commonlibraries.model.enums.Language;
import com.vedubox.commonlibraries.model.enums.MessageType;
import com.vedubox.notificationservice.model.NotificationType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateTemplateResponse {

    @JsonProperty("ApplicationId")
    private int applicationId;

    @JsonProperty("NotificationType")
    private NotificationType notificationType;

    @JsonProperty("MessageType")
    private MessageType messageType;

    @JsonProperty("Language")
    private Language language;
}
