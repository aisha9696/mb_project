package kz.mb.project.mb_project.dto;

import lombok.Data;

@Data
public class UserLockedResponse {
    private Integer numFailures;
    private Boolean disabled;
    private String lastIPFailure;
    private Long lastFailure;
}
