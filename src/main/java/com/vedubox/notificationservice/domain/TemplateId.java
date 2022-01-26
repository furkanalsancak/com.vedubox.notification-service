package com.vedubox.notificationservice.domain;

import com.vedubox.commonlibraries.model.enums.Language;
import com.vedubox.commonlibraries.model.enums.MessageType;
import com.vedubox.notificationservice.model.NotificationType;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemplateId implements Serializable {

    private int applicationId;
    private NotificationType notificationType;
    private MessageType messageType;
    private Language language;
}
