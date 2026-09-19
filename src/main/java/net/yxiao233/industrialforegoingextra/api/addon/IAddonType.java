package net.yxiao233.industrialforegoingextra.api.addon;

import com.hrznstudio.titanium.api.augment.IAugmentType;
import org.apache.commons.lang3.StringUtils;

public interface IAddonType {
    String getTypeId();
    default IAugmentType getAugmentType(){
        return () -> StringUtils.capitalize(getTypeId());
    }
}
