package com.vedubox.notificationservice.service;

import com.vedubox.commonlibraries.exception.EntityNotFoundException;
import com.vedubox.notificationservice.repository.TemplateRepository;
import com.vedubox.notificationservice.model.request.CreateTemplateRequest;
import com.vedubox.notificationservice.model.request.GetTemplateRequest;
import com.vedubox.notificationservice.domain.Template;
import com.vedubox.notificationservice.domain.TemplateId;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class TemplateService {

    private final Logger log;
    private final TemplateRepository templateRepository;

    public List<Template> getAll(String xCorrelationId) {
        return templateRepository.findAll();
    }

    public Template get(String xCorrelationId, GetTemplateRequest request) {
        log.debug("TemplateService.get: " + xCorrelationId, request);

        var templateId = TemplateId.builder()
                .applicationId(request.getApplicationId())
                .notificationType(request.getNotificationType())
                .messageType(request.getMessageType())
                .language(request.getLanguage())
                .build();

        return templateRepository.findById(templateId)
                .orElseThrow(() -> new EntityNotFoundException("Template not found"));
    }

    public Template save(String xCorrelationId, CreateTemplateRequest request) {
        log.debug("TemplateService.save: " + xCorrelationId, request);

        var template = new Template();
        template.setApplicationId(request.getApplicationId());
        template.setNotificationType(request.getNotificationType());
        template.setMessageType(request.getMessageType());
        template.setLanguage(request.getLanguage());
        template.setMessage(request.getMessage());
        template.setActive(request.isActive());
        template.setCreateUserId(request.getCreateUserId());

        return templateRepository.save(template);
    }
}
