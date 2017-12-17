/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.user.domain.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Ayemi
 */
@Entity
@Table(name = "role")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Role.findAll", query = "SELECT r FROM Role r"),
    @NamedQuery(name = "Role.findByRole", query = "SELECT r FROM Role r WHERE r.role = :role"),
    @NamedQuery(name = "Role.findByDescription", query = "SELECT r FROM Role r WHERE r.description = :description")})
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "role")
    private String role;
    @Size(max = 45)
    @Column(name = "description")
    private String description;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "roleList")
//    @JoinTable(name="user_role", joinColumns = 
//            {@JoinColumn(name="role", updatable = false, insertable = false)}, 
//            inverseJoinColumns = {@JoinColumn(name="user_name", updatable = false, insertable = false)}
//               
//    )
    private List<User> userList;

    public Role() {
    }

    public Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (role != null ? role.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Role)) {
            return false;
        }
        Role other = (Role) object;
        if ((this.role == null && other.role != null) || (this.role != null && !this.role.equals(other.role))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return role;
    }

}
