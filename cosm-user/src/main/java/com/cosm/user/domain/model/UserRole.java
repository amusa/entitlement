/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosm.user.domain.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 *
 * @author Ayemi
 */
@Entity
@Table(name = "user_role")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserRole.findAll", query = "SELECT u FROM UserRole u"),
    @NamedQuery(name = "UserRole.findByUserName", query = "SELECT u FROM UserRole u WHERE u.userRolePK.userName = :userName"),
    @NamedQuery(name = "UserRole.findByRole", query = "SELECT u FROM UserRole u WHERE u.userRolePK.role = :role")})
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UserRolePK userRolePK;
    @JoinColumn(name = "user_name", referencedColumnName = "user_name", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user;
    @JoinColumn(name = "role", referencedColumnName = "role", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Role role;

    public UserRole() {
    }

    public UserRole(UserRolePK userRolePK) {
        this.userRolePK = userRolePK;
    }

    public UserRole(String userName, String role) {
        this.userRolePK = new UserRolePK(userName, role);
    }

    public UserRolePK getUserRolePK() {
        return userRolePK;
    }

    public void setUserRolePK(UserRolePK userRolePK) {
        this.userRolePK = userRolePK;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userRolePK != null ? userRolePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserRole)) {
            return false;
        }
        UserRole other = (UserRole) object;
        if ((this.userRolePK == null && other.userRolePK != null) || (this.userRolePK != null && !this.userRolePK.equals(other.userRolePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nnpcgroup.cosm.entity.user.UserRole[ userRolePK=" + userRolePK + " ]";
    }

}
