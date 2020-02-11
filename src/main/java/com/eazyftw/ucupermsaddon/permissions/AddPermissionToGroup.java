package com.eazyftw.ucupermsaddon.permissions;

import com.eazyftw.ultracustomizerplus.addon.ElementUCP;
import me.TechsCode.UltraCustomizer.UltraCustomizer;
import me.TechsCode.UltraCustomizer.scriptSystem.objects.*;
import me.TechsCode.UltraCustomizer.scriptSystem.objects.datatypes.DataType;
import me.TechsCode.UltraCustomizer.tpl.XMaterial;
import me.TechsCode.UltraPermissions.UltraPermissions;
import me.TechsCode.UltraPermissions.UltraPermissionsAPI;
import me.TechsCode.UltraPermissions.storage.objects.Group;

public class AddPermissionToGroup extends ElementUCP {

    public AddPermissionToGroup(UltraCustomizer ultraCustomizer) {
        super(ultraCustomizer);
    }

    @Override
    public String getName() {
        return "Add Permission to Group";
    }

    @Override
    public String getRequiredPlugin() {
        return "UltraPermissions";
    }

    @Override
    public String getInternalName() {
        return "add-permission-to-group";
    }

    @Override
    public boolean isHidingIfNotCompatible() {
        return false;
    }

    @Override
    public XMaterial getMaterial() {
        return XMaterial.TRIPWIRE_HOOK;
    }

    @Override
    public String[] getDescription() {
        return new String[]{"Allows you to add permissions to a group.", "(UltraPermissions)"};
    }

    @Override
    public Argument[] getArguments(ElementInfo elementInfo) {
        return new Argument[] { new Argument("group", "Group", DataType.STRING, elementInfo), new Argument("permission", "Permission", DataType.STRING, elementInfo) };
    }

    @Override
    public OutcomingVariable[] getOutcomingVariables(ElementInfo elementInfo) {
        return new OutcomingVariable[]{new OutcomingVariable("success", "Success", DataType.BOOLEAN, elementInfo)};
    }

    @Override
    public Child[] getConnectors(ElementInfo elementInfo) {
        return new Child[] { new DefaultChild(elementInfo, "next") };
    }

    @Override
    public void run(ElementInfo info, ScriptInstance instance) {
        try {
            UltraPermissionsAPI ultraPermissionsAPI = UltraPermissions.getAPI();

            String groupName = (String) this.getArguments(info)[0].getValue(instance);
            String permission = (String) this.getArguments(info)[1].getValue(instance);

            Group group = ultraPermissionsAPI.getGroups().name(groupName);
            group.newPermission(permission).create();

            this.getOutcomingVariables(info)[0].register(instance, new DataRequester() {
                public Object request() {
                    return true;
                }
            });
        } catch (Exception ex) {
            this.getOutcomingVariables(info)[0].register(instance, new DataRequester() {
                public Object request() {
                    return false;
                }
            });
        }
        this.getConnectors(info)[0].run(instance);
    }

    @Override
    public String getAuthor() {
        return "EazyFTW";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }
}
