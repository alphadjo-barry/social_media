package com.alphadjo.social_media.entity;

import jakarta.persistence.*;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Utilisateur extends AbstractEntity implements UserDetails {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;

    @Column(nullable = true)
    private String photoOriginalName;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String picturePath;

    private boolean isActive = false;

   @ManyToMany
   @JoinTable(name = "utilisateur_role", joinColumns = @JoinColumn(name = "utilisateur_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
   private Set<Role> roles;

   @ManyToMany
   @JoinTable(name = "utilisateur_adresse", joinColumns = @JoinColumn(name = "utilisateur_id"), inverseJoinColumns = @JoinColumn(name = "adresse_id"))
   private List<Adresse> adresses;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.isActive();
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.isActive();
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }
}
