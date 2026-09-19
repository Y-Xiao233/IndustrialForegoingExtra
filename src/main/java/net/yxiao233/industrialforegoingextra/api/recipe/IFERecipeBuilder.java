package net.yxiao233.industrialforegoingextra.api.recipe;

import com.buuz135.industrial.recipe.LaserDrillRarity;
import com.buuz135.industrial.recipe.data.EntityData;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.yxiao233.industrialforegoingextra.IndustrialForegoingExtra;
import net.yxiao233.industrialforegoingextra.api.registry.DeferredRecipe;

import java.util.*;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public abstract class IFERecipeBuilder {
    private final ArrayList<String> structure = new ArrayList<>();
    private final HashMap<Character, ItemStack> defineMap = new HashMap<>();
    private final HashMap<Character, TagKey<Item>> defineTagMap = new HashMap<>();
    private List<Ingredient> inputs;
    private ItemStack input;
    private FluidStack inputFluid;
    private SizedFluidIngredient inputIngredientFluid;
    private SizedFluidIngredient outputIngredientFluid;
    private FluidStack[] inputFluids;
    private BlockState result;
    private Optional<EntityData> entity;
    private float breakChance;
    private Ingredient inputBlock;
    private Ingredient catalyst;
    private List<LaserDrillRarity> rarity;
    private FluidStack outputFluid;
    private String id;
    private String nameSpace;
    private boolean isDefaultRecipe;
    private int time;
    private final ItemStack output;
    private float chance;
    public IFERecipeBuilder(ItemStack output){
        this.output = output;
    }

    public IFERecipeBuilder(ItemStack output, String id){
        this.output = output;
        this.id = id;
    }

    public IFERecipeBuilder id(String id){
        this.id = id;
        return this;
    }

    public IFERecipeBuilder inputFluid(FluidStack fluid){
        this.inputFluid = fluid;
        return this;
    }

    public IFERecipeBuilder inputFluid(Fluid fluid, int amount){
        this.inputFluid = new FluidStack(fluid,amount);
        return this;
    }

    public IFERecipeBuilder inputFluid(DeferredHolder<Fluid,Fluid> fluid, int amount){
        this.inputFluid = new FluidStack(fluid,amount);
        return this;
    }

    protected IFERecipeBuilder inputFluid(SizedFluidIngredient inputFluid){
        this.inputIngredientFluid = inputFluid;
        return this;
    }
    public IFERecipeBuilder inputFluids(FluidStack... inputFluids){
        this.inputFluids = inputFluids;
        return this;
    }

    public IFERecipeBuilder outputFluid(FluidStack fluid){
        this.outputFluid = fluid;
        return this;
    }

    public IFERecipeBuilder outputFluid(SizedFluidIngredient fluid){
        this.outputIngredientFluid = fluid;
        return this;
    }
    public IFERecipeBuilder chance(float chance){
        this.chance = chance;
        return this;
    }

    public IFERecipeBuilder inputs(Ingredient... inputs){
        this.inputs = List.of(inputs);
        return this;
    }

    public IFERecipeBuilder catalyst(Ingredient catalyst){
        this.catalyst = catalyst;
        return this;
    }

    public IFERecipeBuilder isDefault(boolean isDefault){
        this.isDefaultRecipe = isDefault;
        return this;
    }

    public IFERecipeBuilder breakChance(float breakChance){
        this.breakChance = breakChance;
        return this;
    }

    public IFERecipeBuilder resultBlockState(BlockState result){
        this.result = result;
        return this;
    }

    public IFERecipeBuilder entity(Optional<EntityData> entityData){
        this.entity = entityData;
        return this;
    }

    public IFERecipeBuilder rarity(LaserDrillRarity... rarity){
        this.rarity = List.of(rarity);
        return this;
    }

    public IFERecipeBuilder input(ItemStack stack){
        this.input = stack;
        return this;
    }

    public IFERecipeBuilder inputBlock(Ingredient input){
        this.inputBlock = input;
        return this;
    }

    public IFERecipeBuilder processingTime(int time){
        this.time = time;
        return this;
    }

    public IFERecipeBuilder pattern(String s){
        structure.add(s);
        return this;
    }

    public IFERecipeBuilder define(char symbol, ItemStack itemStack){
        this.defineMap.put(symbol,itemStack);
        return this;
    }

    public IFERecipeBuilder define(char symbol, TagKey<Item> itemTag){
        this.defineTagMap.put(symbol,itemTag);
        return this;
    }

    public IFERecipeBuilder define(char symbol, DeferredHolder<Item,Item> item){
        define(symbol,item.get().getDefaultInstance());
        return this;
    }
    public abstract void save(RecipeOutput output);

    protected ArrayList<String> getStructure() {
        if(structure.isEmpty()){
            String[] s = new String[9];
            Arrays.fill(s," ");
            return new ArrayList<>(Arrays.asList(s));
        }
        return structure;
    }

    public HashMap<Character, ItemStack> getDefineMap() {
        return defineMap;
    }
    protected FluidStack getInputFluid() {
        return inputFluid;
    }
    protected SizedFluidIngredient getInputIngredientFluid() {
        return inputIngredientFluid;
    }
    protected FluidStack getOutputFluid() {
        return outputFluid;
    }
    protected SizedFluidIngredient getOutputIngredientFluid() {
        return outputIngredientFluid;
    }

    protected int getTime() {
        return Math.max(time, 0);
    }

    protected HashMap<Character, TagKey<Item>> getDefineTagMap() {
        return defineTagMap;
    }

    protected String getId() {
        if(id == null || id.isEmpty()){
            return BuiltInRegistries.ITEM.getKey(output.getItem()).getPath();
        }
        return id;
    }

    protected String getNameSpace(){
        if(nameSpace == null || nameSpace.isEmpty()){
            return IndustrialForegoingExtra.MODID;
        }
        return nameSpace;
    }

    protected List<Ingredient> getInputs(){
        return this.inputs;
    }
    protected ItemStack getInput(){
        return input;
    }

    protected Optional<EntityData> getEntity(){
        return this.entity;
    }

    protected Ingredient getCatalyst(){
        return catalyst;
    }

    protected List<LaserDrillRarity> getRarity(){
        return rarity;
    }

    protected ResourceLocation getLocation(){
        return ResourceLocation.fromNamespaceAndPath(getNameSpace(),getId());
    }
    protected ResourceLocation getLocation(DeferredRecipe<?> recipe){
        return recipe.rl().withPath(path -> path + "/" + this.getId());
    }
    protected Ingredient getInputBlock(){
        return this.inputBlock;
    }
    protected BlockState getResult(){
        return this.result;
    }
    protected float getBreakChance(){
        return this.breakChance;
    }

    protected boolean isDefault(){
        return this.isDefaultRecipe;
    }

    protected FluidStack[] getInputFluids(){
        return this.inputFluids;
    }

    public ItemStack getOutput() {
        return output;
    }
    protected float getChance(){
        return chance <= 0 ? 1.0F : chance;
    }
}