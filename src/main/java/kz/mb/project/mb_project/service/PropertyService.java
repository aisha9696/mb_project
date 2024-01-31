package kz.mb.project.mb_project.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.Nonnull;
import kz.mb.project.mb_project.entity.ProjectConfiguration;
import kz.mb.project.mb_project.exception.ErrorMessage;
import kz.mb.project.mb_project.exception.ProjectException;
import kz.mb.project.mb_project.repo.ProjectConfigurationRepository;

@Service
public class PropertyService {
  private final ProjectConfigurationRepository projectConfigurationRepository;
  private Map<String, List<String>> properties;

  @Autowired
  public PropertyService(ProjectConfigurationRepository projectConfigurationRepository) {
    this.projectConfigurationRepository = projectConfigurationRepository;
    loadPropertiedFromDB();
  }

  public void loadPropertiedFromDB() {
    properties = StreamSupport.stream(projectConfigurationRepository.findAll().spliterator(), false)
        .collect(
            Collectors.groupingBy(
                ProjectConfiguration::getConfigurationName,
                Collectors.mapping(ProjectConfiguration::getConfigurationValue, Collectors.toList())
            )
        );
  }

  @Nonnull
  public String get(@Nonnull final String name) throws ProjectException {
    List<String> properties = this.properties.get(name);
    if (properties == null || properties.isEmpty()) {
      throw new ProjectException(ErrorMessage.PROJECT_CONFIGURATION_IS_NOT_FOUND);
    }
    return properties.get(0);
  }
}
