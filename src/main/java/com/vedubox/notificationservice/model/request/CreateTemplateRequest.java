package com.vedubox.notificationservice.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vedubox.commonlibraries.model.enums.Language;
import com.vedubox.commonlibraries.model.enums.MessageType;
import com.vedubox.notificationservice.model.*;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Getter
@Setter
public class CreateTemplateRequest {

    @NotNull
    @Min(1)
    private int applicationId;

    @NotNull
    private NotificationType notificationType;

    @NotNull
    private MessageType messageType;

    @NotNull
    private Language language;

    @NotNull
    @NotBlank
    private String message;

    @NotNull
    private boolean isActive = true;

    @NotNull
    @NotBlank
    private String createUserId;
}
