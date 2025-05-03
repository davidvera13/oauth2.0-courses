package eu.dreamlabs.oauth.configs;

import eu.dreamlabs.oauth.entities.users.AuthorityEntity;
import eu.dreamlabs.oauth.entities.users.RoleEntity;
import eu.dreamlabs.oauth.entities.users.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;

@RequiredArgsConstructor
public class UserPrincipal implements UserDetails {
    private final UserEntity userEntity;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new HashSet<>();
        Collection<AuthorityEntity> authorityEntities = new HashSet<>();

        // Get user Roles
        Collection<RoleEntity> roles = userEntity.getRoles();

        if(roles == null) return authorities;

        roles.forEach((role) -> {
            authorities.add(new SimpleGrantedAuthority(role.getName().name()));
            authorityEntities.addAll(role.getAuthorities());
        });

        authorityEntities.forEach((authorityEntity) ->{
            authorities.add(new SimpleGrantedAuthority(authorityEntity.getName()));
        });

        return authorities;
    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return userEntity.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return !userEntity.isAccountExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !userEntity.isAccountLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !userEntity.isCredentialsExpired();
    }

    @Override
    public boolean isEnabled() {
        return !userEntity.isDisabled();
    }
}
