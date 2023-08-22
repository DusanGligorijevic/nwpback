package com.nwp.security.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public enum Role {

  USER(Collections.emptySet()),
  ADMIN(
          Set.of(
                  Permission.can_read_machines,
                  Permission.can_start_machines,
                  Permission.can_stop_machines,
                  Permission.can_destroy_machines,
                  Permission.can_restart_machines,
                  Permission.can_schedule_machines,
                  Permission.can_search_machines,
                  Permission.can_create_machines,
                  Permission.can_delete_machines,
                  Permission.can_update_machines,
                  Permission.can_read_users,
                  Permission.can_search_users,
                  Permission.can_create_users,
                  Permission.can_delete_users,
                  Permission.can_update_users,
                  Permission.ADMIN_READ,
                  Permission.ADMIN_UPDATE,
                  Permission.ADMIN_DELETE,
                  Permission.ADMIN_CREATE,
                  Permission.MANAGER_READ,
                  Permission.MANAGER_UPDATE,
                  Permission.MANAGER_DELETE,
                  Permission.MANAGER_CREATE
          )
  ),
  MANAGER(
          Set.of(
                  Permission.MANAGER_READ,
                  Permission.MANAGER_UPDATE,
                  Permission.MANAGER_DELETE,
                  Permission.MANAGER_CREATE
          )
  )

  ;

  @Getter
  private final Set<Permission> permissions;

  public List<SimpleGrantedAuthority> getAuthorities() {
    var authorities = getPermissions()
            .stream()
            .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
            .collect(Collectors.toList());
    authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
    return authorities;
  }
}
