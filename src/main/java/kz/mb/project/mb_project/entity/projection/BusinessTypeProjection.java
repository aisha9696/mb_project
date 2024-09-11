package kz.mb.project.mb_project.entity.projection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import kz.mb.project.mb_project.entity.BusinessTypeSpr;

@Projection(name = "list", types = { BusinessTypeSpr.class })
public interface BusinessTypeProjection {
  @Value("#{@languageValueAccessor.getValueByLanguage(target)}")
  String getValue();
}
