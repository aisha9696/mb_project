package kz.mb.project.mb_project.entity.projection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import kz.mb.project.mb_project.entity.warehouse.CatalogUnitType;
import kz.mb.project.mb_project.entity.warehouse.ProductCategorySpr;

@Projection(name = "list", types = { ProductCategorySpr.class })
public interface ProductCategoryCustomProjection {
  @Value("#{@languageValueAccessor.getValueByLanguage(target)}")
  String getValue();
  CatalogUnitType getType();
}
