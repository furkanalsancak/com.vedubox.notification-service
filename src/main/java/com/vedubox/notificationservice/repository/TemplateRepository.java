package com.vedubox.notificationservice.repository;

import com.vedubox.notificationservice.domain.Template;
import com.vedubox.notificationservice.domain.TemplateId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemplateRepository extends JpaRepository<Template, TemplateId> {
}
