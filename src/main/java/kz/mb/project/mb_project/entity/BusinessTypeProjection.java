package kz.mb.project.mb_project.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "list", types = { BusinessType.class })
public interface BusinessTypeProjection {
  @Value("#{@languageValueAccessor.getValueByLanguage(target)}")
  String getValue();
}
