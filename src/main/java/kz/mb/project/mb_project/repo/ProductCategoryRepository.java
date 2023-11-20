package kz.mb.project.mb_project.repo;

import java.util.Set;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import kz.mb.project.mb_project.entity.warehouse.ProductCategory;

@RepositoryRestResource
public interface ProductCategoryRepository extends CrudRepository<ProductCategory, UUID> {

}