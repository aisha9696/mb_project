package kz.mb.project.mb_project.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import kz.mb.project.mb_project.entity.File;

@RepositoryRestResource
public interface FileRepository extends CrudRepository<File, Long> {

}

