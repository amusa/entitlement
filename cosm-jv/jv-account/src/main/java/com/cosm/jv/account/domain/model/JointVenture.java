/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.jv.account.domain.model;

import com.nnpcgroup.cosm.common.util.AuditInfo;
import com.nnpcgroup.cosm.common.util.Auditable;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 *
 * @author 18359
 */
@Entity
public class JointVenture implements Auditable {

    private static final long serialVersionUID = 7758288081166549749L;

    private Long id;
    private String title;
    private Company operator;
    private List<Contract> contracts;
    protected EquityType equityType;

    private AuditInfo auditInfo = new AuditInfo();

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull
    @Column(name = "TITLE")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @ManyToOne
    @JoinColumn(name = "OPERATOR_ID")
    @NotNull
    public Company getOperator() {
        return operator;
    }

    public void setOperator(Company operator) {
        this.operator = operator;
    }

    public void setCurrentUser(String user) {
//        auditInfo.setCurrentUser(user);
        auditInfo.setLastModifiedBy(user);
    }


    @OneToOne
    @JoinColumn(name="EQUITY_TYPE_ID", referencedColumnName = "ID")
    public EquityType getEquityType() {
        return equityType;
    }

    public void setEquityType(EquityType equityType) {
        this.equityType = equityType;
    }

    @OneToMany(mappedBy = "fiscalArrangement", fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    public List<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(List<Contract> contracts) {
        this.contracts = contracts;
    }

    public void addContract(Contract contract) {
        if (contracts == null) {
            contracts = new ArrayList<>();
        }
        contracts.add(contract);
    }

    @Embedded
    public AuditInfo getAuditInfo() {
        return auditInfo;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final JointVenture other = (JointVenture) obj;
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
