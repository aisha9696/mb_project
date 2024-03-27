package kz.mb.project.mb_project.entity.projection;

import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import kz.mb.project.mb_project.entity.warehouse.CatalogUnitType;
import kz.mb.project.mb_project.entity.warehouse.ProductCategorySpr;


@Projection(name = "tree", types = { ProductCategorySpr.class })
public interface ProductCategoryProjection {
  @Value("#{@languageValueAccessor.getValueByLanguage(target)}")
  String getValue();
  Set<ProductCategoryProjection> getSubCategory();
  CatalogUnitType getType();
}
