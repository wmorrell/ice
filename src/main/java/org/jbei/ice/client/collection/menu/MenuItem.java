package org.jbei.ice.client.collection.menu;

import java.util.ArrayList;

import org.jbei.ice.client.collection.view.OptionSelect;
import org.jbei.ice.lib.shared.dto.AccountInfo;
import org.jbei.ice.lib.shared.dto.folder.FolderType;
import org.jbei.ice.lib.shared.dto.permission.PermissionInfo;

public class MenuItem extends OptionSelect {

    private long count;
    private ArrayList<PermissionInfo> permissions;
    private AccountInfo owner;
    private FolderType type;

    public MenuItem(long id, String name, long count) {
        super(id, name);
        this.count = count;
    }

    public boolean hasSubMenu() {
        return getId() > 0;
    }

    public long getCount() {
        return this.count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public void setPermissions(ArrayList<PermissionInfo> permissions) {
        this.permissions = permissions;
    }

    public ArrayList<PermissionInfo> getPermissions() {
        return permissions;
    }

    public AccountInfo getOwner() {
        return owner;
    }

    public void setOwner(AccountInfo owner) {
        this.owner = owner;
    }

    public FolderType getType() {
        return type;
    }

    public void setType(FolderType type) {
        this.type = type;
    }
}
