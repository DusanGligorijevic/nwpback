package com.nwp.security.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    can_read_users("can_read_users"),
    can_create_users("can_create_users"),
    can_delete_users("can_delete_users"),
    can_update_users("can_update_users"),
    can_search_users("can_search_users"),
    can_read_machines("can_read_machines"),
    can_start_machines("can_start_machines"),
    can_stop_machines("can_stop_machines"),
    can_destroy_machines("can_destroy_machines"),
    can_restart_machines("can_restart_machines"),
    can_schedule_machines("can_schedule_machines"),
    can_create_machines("can_create_machines"),
    can_delete_machines("can_delete_machines"),
    can_update_machines("can_update_machines"),
    can_search_machines("can_search_machines"),
    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    MANAGER_READ("management:read"),
    MANAGER_UPDATE("management:update"),
    MANAGER_CREATE("management:create"),
    MANAGER_DELETE("management:delete")

    ;

    @Getter
    private final String permission;
}
