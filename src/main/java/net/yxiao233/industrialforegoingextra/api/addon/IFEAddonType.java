package net.yxiao233.industrialforegoingextra.api.addon;

public enum IFEAddonType implements IAddonType{
    APPLE("apple"),
    HEAL("heal"),
    ENERGY("energy"),
    CREATIVE("creative"),
    SPEED("speed"),
    PROCESSING("processing"),
    EFFICIENCY("efficiency"),
    THREAD("thread"),
    LOOTING("looting"),
    FORTUNE("fortune")
    ;
    final String id;
    IFEAddonType(String id){
        this.id = id;
    }

    @Override
    public String getTypeId() {
        return id;
    }
}
