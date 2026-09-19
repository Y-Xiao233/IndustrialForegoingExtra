package net.yxiao233.industrialforegoingextra.api.tile;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;

import java.util.function.BiPredicate;

public class SlotElements {
    private String id;
    private int xPos;
    private int yPos;
    private int size;
    private int position;
    private DyeColor color;
    private int[] range;
    private BiPredicate<ItemStack, Integer> outputFilter;
    private BiPredicate<ItemStack, Integer> inputFilter;
    private static final SlotElements DEFAULT = new SlotElements("input",44,22,1,0,DyeColor.LIGHT_BLUE,new int[]{1,1},(stack, integer) -> false,(stack, integer) -> true);
    public SlotElements(){
        this.id = DEFAULT.id;
        this.xPos = DEFAULT.xPos;
        this.yPos = DEFAULT.yPos;
        this.size = DEFAULT.size;
        this.position = DEFAULT.position;
        this.color = DEFAULT.color;
        this.range = DEFAULT.range;
        this.outputFilter = DEFAULT.outputFilter;
        this.inputFilter = DEFAULT.inputFilter;
    }
    public SlotElements(String id, int xPos, int yPos, int size, int position, DyeColor color, int[] range, BiPredicate<ItemStack, Integer> outputFilter,BiPredicate<ItemStack, Integer> inputFilter){
        this.id = id;
        this.xPos = xPos;
        this.yPos = yPos;
        this.size = size;
        this.position = position;
        this.color = color;
        this.range = range;
        this.outputFilter = outputFilter;
        this.inputFilter = inputFilter;
    }

    public SlotElements setId(String id) {
        this.id = id;
        return this;
    }

    public SlotElements setXPos(int xPos) {
        this.xPos = xPos;
        return this;
    }

    public SlotElements setYPos(int yPos) {
        this.yPos = yPos;
        return this;
    }

    public SlotElements setSize(int size) {
        this.size = size;
        return this;
    }


    public SlotElements setPosition(int position) {
        this.position = position;
        return this;
    }

    public SlotElements setColor(DyeColor color) {
        this.color = color;
        return this;
    }

    public SlotElements setRange(int[] range) {
        this.range = range;
        return this;
    }

    public SlotElements setOutputFilter(BiPredicate<ItemStack, Integer> outputFilter) {
        this.outputFilter = outputFilter;
        return this;
    }

    public SlotElements setInputFilter(BiPredicate<ItemStack, Integer> inputFilter) {
        this.inputFilter = inputFilter;
        return this;
    }


    public String getId() {
        return id;
    }

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public int getSize() {
        return size;
    }

    public int getPosition() {
        return position;
    }

    public DyeColor getColor() {
        return color;
    }

    public int[] getRange() {
        return range;
    }

    public BiPredicate<ItemStack, Integer> getOutputFilter() {
        return outputFilter;
    }

    public BiPredicate<ItemStack, Integer> getInputFilter() {
        return inputFilter;
    }
}
