package com.nnpcgroup.cosm.entity;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * Created by maliska on 9/24/16.
 */

public class AuditListener {


    @PrePersist
    public void auditCreation(Auditable auditable) {
        auditable.getAuditInfo().prePersist();

    }

    @PreUpdate
    public void auditUpdate(Auditable auditable) {
        auditable.getAuditInfo().preUpdate();
    }


}
