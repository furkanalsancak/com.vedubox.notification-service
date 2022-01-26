package com.vedubox.notificationservice.controller;

import com.vedubox.commonlibraries.model.SuccessResponse;
import com.vedubox.notificationservice.domain.Template;
import com.vedubox.notificationservice.model.request.CreateTemplateRequest;
import com.vedubox.notificationservice.model.request.GetTemplateRequest;
import com.vedubox.notificationservice.model.response.CreateTemplateResponse;
import com.vedubox.notificationservice.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1/templates")
@RequiredArgsConstructor
public class TemplateController {

    private final Logger log;
    private final TemplateService templateService;

    @GetMapping(path = "/all", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    public SuccessResponse getAll(/*@ApiParam(value = "", required = true)*/ @RequestHeader("x-correlation-id") String xCorrelationId) {
        log.debug("TemplateController.getAll: " + xCorrelationId);
        return SuccessResponse.builder()
                .xCorrelationId(xCorrelationId)
                .data(templateService.getAll(xCorrelationId))
                .build();
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    public SuccessResponse get(@Valid GetTemplateRequest request,
                        /*@ApiParam(value = "", required = true)*/
                        @RequestHeader(value = "x-correlation-id", required = true) String xCorrelationId) {
        log.debug("TemplateController.get:" + xCorrelationId);
        return SuccessResponse.builder()
                .xCorrelationId(xCorrelationId)
                .data(templateService.get(xCorrelationId, request))
                .build();
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public SuccessResponse createTemplate(@Valid @RequestBody CreateTemplateRequest request,
                                    /*@ApiParam(value = "", required = true)*/
                                   @RequestHeader(value = "x-correlation-id", required = true) String xCorrelationId) {
        log.debug("TemplateController.create:" + xCorrelationId);

        var template = templateService.save(xCorrelationId, request);

        return SuccessResponse.builder()
                .xCorrelationId(xCorrelationId)
                .data(CreateTemplateResponse.builder()
                        .applicationId(template.getApplicationId())
                        .messageType(template.getMessageType())
                        .notificationType(template.getNotificationType())
                        .language(template.getLanguage())
                        .build())
                .build();
    }
}
