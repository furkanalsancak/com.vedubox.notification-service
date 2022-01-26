package com.vedubox.notificationservice.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import com.vedubox.commonlibraries.model.enums.Language;
import com.vedubox.commonlibraries.model.enums.MessageType;
import com.vedubox.notificationservice.model.NotificationType;
import lombok.*;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;


@Getter
@Setter
@Table(name = "TEMPLATES")
@Entity
@IdClass(TemplateId.class)
public class Template {

    public Template() {};

    public Template(TemplateId templateId) {
        applicationId = templateId.getApplicationId();
        notificationType = templateId.getNotificationType();
        messageType = templateId.getMessageType();
        language = templateId.getLanguage();
    }

    @Id
    @JsonProperty("ApplicationId")
    private int applicationId;
    @Id
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    @JsonProperty("NotificationType")
    private NotificationType notificationType;
    @Id
    @Enumerated(EnumType.STRING)
    @Column(length = 100)
    @JsonProperty("MessageType")
    private MessageType messageType;
    @Id
    @Enumerated(EnumType.STRING)
    @Column(length = 2)
    @JsonProperty("Language")
    private Language language;


    @NotNull
    @Column(nullable = false)
    @JsonProperty("Message")
    private String message;
    @Column(nullable = false)
    @JsonProperty("IsActive")
    private boolean isActive = true;

    @JsonProperty("CreateTime")
    private OffsetDateTime createTime;
    @JsonProperty("UpdateTime")
    private OffsetDateTime updateTime;

    @NotNull
    @Column(nullable = false)
    @JsonProperty("CreateUserId")
    private String createUserId;
    @JsonProperty("UpdateUserId")
    private String updateUserId;

    @PrePersist
    void preInsert() {
        createTime = OffsetDateTime.now(ZoneOffset.UTC);
        createUserId = createUserId == null ? "system" : createUserId;
        updateTime = null;
        updateUserId = null;
    }
    @PreUpdate
    void preUpdate() {
        updateTime = OffsetDateTime.now(ZoneOffset.UTC);
    }
}
