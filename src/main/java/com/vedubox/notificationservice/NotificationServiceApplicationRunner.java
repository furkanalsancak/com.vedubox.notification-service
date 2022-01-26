package com.vedubox.notificationservice;

import com.vedubox.commonlibraries.model.enums.Language;
import com.vedubox.commonlibraries.model.enums.MessageType;
import com.vedubox.notificationservice.domain.Template;
import com.vedubox.notificationservice.domain.TemplateId;
import com.vedubox.notificationservice.model.NotificationType;
import com.vedubox.notificationservice.repository.TemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
// @Profile("local")
public class NotificationServiceApplicationRunner implements ApplicationRunner {

    private final TemplateRepository templateRepository;
    @Value("${default-message:N/A}")
    private String defaultMessage;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(templateRepository.count() == 0) {
            var template1 = new Template(
                    TemplateId.builder()
                            .applicationId(0)
                            .notificationType(NotificationType.EMAIL)
                            .messageType(MessageType.LIVELESSON_CREATED_EVENT)
                            .language(Language.EN).build()
            );
            template1.setMessage(defaultMessage);
            templateRepository.save(template1);

            var template2 = new Template(
                    TemplateId.builder()
                            .applicationId(0)
                            .notificationType(NotificationType.EMAIL)
                            .messageType(MessageType.LIVELESSON_CANCELLED_EVENT)
                            .language(Language.EN).build()
            );
            template2.setMessage(defaultMessage);
            templateRepository.save(template2);

            var template3 = new Template(
                    TemplateId.builder()
                            .applicationId(0)
                            .notificationType(NotificationType.SMS)
                            .messageType(MessageType.LIVELESSON_CREATED_EVENT)
                            .language(Language.EN).build()
            );
            template3.setMessage(defaultMessage);
            templateRepository.save(template3);

            var template4 = new Template(
                    TemplateId.builder()
                            .applicationId(0)
                            .notificationType(NotificationType.SMS)
                            .messageType(MessageType.LIVELESSON_CANCELLED_EVENT)
                            .language(Language.EN).build()
            );
            template4.setMessage(defaultMessage);
            templateRepository.save(template4);
        }
    }
}
