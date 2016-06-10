package com.happy.hipok.web.rest.app.dto;

import com.happy.hipok.validation.annotations.ValidDeclarationTypeRefId;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Dahwoud on 16/02/2016.
 */
public class AppDeclarationDTO {

    @NotNull(message="{com.happy.hipok.declarationTypeRefId.nullid}")
    @ValidDeclarationTypeRefId
    private Long declarationTypeRefId;

    @Size(max = 3000)
    private String description;

    public Long getDeclarationTypeRefId() {
        return declarationTypeRefId;
    }

    public void setDeclarationTypeRefId(Long declarationTypeRefId) {
        this.declarationTypeRefId = declarationTypeRefId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
