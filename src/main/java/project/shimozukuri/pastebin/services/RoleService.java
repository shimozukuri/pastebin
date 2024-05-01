package project.shimozukuri.pastebin.services;

import project.shimozukuri.pastebin.entities.Role;

public interface RoleService {

    Role getUserRole();

    Role getAdminRole();
}
