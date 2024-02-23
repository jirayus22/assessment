package com.kbtg.bootcamp.posttest.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users", schema = "public", catalog = "Lotteries")
public class UsersEntity implements UserDetails {

    @Id
    @Column(name = "user_id", nullable = false, length = 20)
    private Long userId;
    @Basic
    @Column(name = "username", nullable = false, length = 20)
    private String username;
    @Basic
    @Column(name = "password", nullable = false, length = 100)
    private String password;
    @Basic
    @Column(name = "name", nullable = true, length = 20)
    private String name;
    @Basic
    @Column(name = "create_at", nullable = true)
    private Timestamp createAt;
    @Basic
    @Column(name = "create_by", nullable = true)
    private Integer createBy;
    @Basic
    @Column(name = "update_at", nullable = true)
    private Timestamp updateAt;
    @Basic
    @Column(name = "update_by", nullable = true)
    private Integer updateBy;
    @Basic
    @Column(name = "role_id", nullable = true)
    private Integer roleId;


    @Getter
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", insertable = false,updatable = false)
    private RoleEntity roleEntity;

    public void setRoleEntity(RoleEntity roleEntity) {
        this.roleEntity = roleEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE_"+getRoleEntity().getRoleName()));
        return authorityList;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername(){
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
