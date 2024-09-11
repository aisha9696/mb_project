package kz.mb.project.mb_project.repo;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import kz.mb.project.mb_project.entity.BusinessTypeSpr;
import kz.mb.project.mb_project.entity.projection.ProductCategoryCustomProjection;
import kz.mb.project.mb_project.entity.warehouse.ProductCategorySpr;

@RepositoryRestResource(excerptProjection = ProductCategoryCustomProjection.class)
public interface ProductCategoryRepository extends
    JpaRepository<ProductCategorySpr, UUID>, PagingAndSortingRepository<ProductCategorySpr, UUID> {

  Page<ProductCategorySpr> getProductCategoriesByParentIsNull(Pageable pageable);

  Set<ProductCategorySpr> getProductCategoryByIdAndBusinessTypeIn(UUID id,
      Collection<List<BusinessTypeSpr>> businessType);

}