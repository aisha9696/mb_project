package kz.mb.project.mb_project.repo;



import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import kz.mb.project.mb_project.entity.BusinessTypeSpr;
import kz.mb.project.mb_project.entity.projection.BusinessTypeProjection;

@RepositoryRestResource(excerptProjection = BusinessTypeProjection.class)
public interface BusinessTypeRepository extends CrudRepository<BusinessTypeSpr, UUID> {

}
