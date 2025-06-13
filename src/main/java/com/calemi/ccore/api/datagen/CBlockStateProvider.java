package com.calemi.ccore.api.datagen;

import com.calemi.ccore.api.block.family.CBlockFamily;
import com.calemi.ccore.api.block.family.CBlockFamilyMember;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.MultiPartBlockStateBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.Arrays;

public abstract class CBlockStateProvider extends BlockStateProvider {

    protected final String modId;

    public CBlockStateProvider(String modId, PackOutput output, ExistingFileHelper fileHelper) {
        super(output, modId, fileHelper);
        this.modId = modId;
    }

    protected void family(CBlockFamily family, CBlockFamily.MemberType type, CBlockFamilyMember member) {

        if (!member.genBlockState()) return;

        if (type.equals(CBlockFamily.MemberType.LOG)) {
            Block wood = family.getBlock(CBlockFamily.MemberType.WOOD);
            if (wood != null) log((RotatedPillarBlock) member.getBlock(), (RotatedPillarBlock) wood);
        }

        if (type.equals(CBlockFamily.MemberType.STRIPPED_LOG)) {
            Block wood = family.getBlock(CBlockFamily.MemberType.STRIPPED_WOOD);
            if (wood != null) log((RotatedPillarBlock) member.getBlock(), (RotatedPillarBlock) wood);
        }

        if (type.equals(CBlockFamily.MemberType.BASE) || type.equals(CBlockFamily.MemberType.CRACKED) || type.equals(CBlockFamily.MemberType.CHISELED)) {
            all(member.getBlock());
        }

        if (type.equals(CBlockFamily.MemberType.PILLAR)) {
            pillar((RotatedPillarBlock) member.getBlock());
        }

        if (type.equals(CBlockFamily.MemberType.STAIRS)) {
            Block base = family.getBlock(CBlockFamily.MemberType.BASE);
            if (base != null) stairs((StairBlock) member.getBlock(), base);
        }

        if (type.equals(CBlockFamily.MemberType.SLAB)) {
            Block base = family.getBlock(CBlockFamily.MemberType.BASE);
            if (base != null) slab((SlabBlock) member.getBlock(), base);
        }

        if (type.equals(CBlockFamily.MemberType.WALL)) {
            Block base = family.getBlock(CBlockFamily.MemberType.BASE);
            if (base != null) wall((WallBlock) member.getBlock(), base);
        }

        if (type.equals(CBlockFamily.MemberType.FENCE)) {
            Block base = family.getBlock(CBlockFamily.MemberType.BASE);
            if (base != null) fence((FenceBlock) member.getBlock(), base);
        }

        if (type.equals(CBlockFamily.MemberType.FENCE_GATE)) {
            Block base = family.getBlock(CBlockFamily.MemberType.BASE);
            if (base != null) fenceGate((FenceGateBlock) member.getBlock(), base);
        }

        if (type.equals(CBlockFamily.MemberType.DOOR)) {
            door((DoorBlock) member.getBlock());
        }

        if (type.equals(CBlockFamily.MemberType.TRAPDOOR)) {
            trapdoor((TrapDoorBlock) member.getBlock());
        }

        if (type.equals(CBlockFamily.MemberType.PRESSURE_PLATE)) {
            Block base = family.getBlock(CBlockFamily.MemberType.BASE);
            if (base != null) pressurePlate((PressurePlateBlock) member.getBlock(), base);
        }

        if (type.equals(CBlockFamily.MemberType.BUTTON)) {
            Block base = family.getBlock(CBlockFamily.MemberType.BASE);
            if (base != null) button((ButtonBlock) member.getBlock(), base);
        }

        if (type.equals(CBlockFamily.MemberType.SIGN)) {
            Block wallSign = family.getBlock(CBlockFamily.MemberType.WALL_SIGN);
            Block base = family.getBlock(CBlockFamily.MemberType.BASE);
            if (base != null) sign((StandingSignBlock) member.getBlock(), (WallSignBlock) wallSign, base);
        }

        if (type.equals(CBlockFamily.MemberType.HANGING_SIGN)) {
            Block wallHangingSign = family.getBlock(CBlockFamily.MemberType.WALL_HANGING_SIGN);
            Block base = family.getBlock(CBlockFamily.MemberType.BASE);
            if (base != null) hangingSign((CeilingHangingSignBlock) member.getBlock(), (WallHangingSignBlock) wallHangingSign, base);
        }
    }

    protected void all(Block block) {

        String blockName = name(block);

        String blockPath = "block/" + blockName;

        ModelFile model = models().cubeAll(blockName, rl(blockPath));

        simpleBlockWithItem(block, model);
    }

    protected void allCutout(Block block) {

        String blockName = name(block);

        String blockPath = "block/" + blockName;

        ModelFile model = models().cubeAll(blockName, rl(blockPath)).renderType("cutout");

        simpleBlockWithItem(block, model);
    }

    protected void log(RotatedPillarBlock log, RotatedPillarBlock wood) {

        String logName = name(log);
        String woodName = name(wood);

        String logPath = "block/" + logName;

        ModelFile logModel = models().cubeColumn(logName, rl(logPath), rl(logPath + "_top"));
        ModelFile logHorizontalModel = models().cubeColumn(logName + "_horizontal", rl(logPath), rl(logPath + "_top"));

        ModelFile woodModel = models().cubeColumn(woodName, rl(logPath), rl(logPath));

        axisBlock(log, logModel, logHorizontalModel);
        simpleBlockItem(log, logModel);

        axisBlock(wood, woodModel, woodModel);
        simpleBlockItem(wood, woodModel);
    }

    protected void stairs(StairBlock block, Block blockMaterial) {

        ResourceLocation modelTexture = texture(blockMaterial, "block/");
        stairsBlock(block, modelTexture);

        String itemModelName = name(block);
        ModelFile itemModel = models().stairs(itemModelName, modelTexture, modelTexture, modelTexture);
        simpleBlockItem(block, itemModel);
    }

    protected void slab(SlabBlock block, Block blockMaterial) {

        ResourceLocation modelTexture = texture(blockMaterial, "block/");
        slabBlock(block, modelTexture, modelTexture);

        String itemModelName = name(block);
        ModelFile itemModel = models().slab(itemModelName, modelTexture, modelTexture, modelTexture);
        simpleBlockItem(block, itemModel);
    }

    protected void wall(WallBlock block, Block blockMaterial) {

        ResourceLocation modelTexture = texture(blockMaterial, "block/");
        wallBlock(block, modelTexture);

        String itemModelName = name(block);
        ModelFile itemModel = models().wallInventory(itemModelName, modelTexture);
        simpleBlockItem(block, itemModel);
    }

    protected void pillar(RotatedPillarBlock block) {

        ResourceLocation modelTexture = texture(block, "block/");
        ResourceLocation modelTextureTop = texture(block, "block/", "_top");

        axisBlock(block, modelTexture, modelTextureTop);

        String itemModelName = name(block);
        ModelFile itemModel = models().cubeColumn(itemModelName, modelTexture, modelTextureTop);

        simpleBlockItem(block, itemModel);
    }

    protected void fence(FenceBlock block, Block blockMaterial) {

        String blockName = name(block);
        String blockMaterialName = name(blockMaterial);

        String blockMaterialPath = "block/" + blockMaterialName;
        ResourceLocation blockMaterialRL = rl(blockMaterialPath);

        ModelFile itemModel = models().fenceInventory(blockName, blockMaterialRL);

        fenceBlock(block, blockMaterialRL);
        simpleBlockItem(block, itemModel);
    }

    protected void fenceGate(FenceGateBlock block, Block blockMaterial) {

        String blockName = name(block);
        String blockMaterialName = name(blockMaterial);

        String blockMaterialPath = "block/" + blockMaterialName;
        ResourceLocation blockMaterialRL = rl(blockMaterialPath);

        ModelFile itemModel = models().fenceGate(blockName, blockMaterialRL);

        fenceGateBlock(block, blockMaterialRL);
        simpleBlockItem(block, itemModel);
    }

    protected void door(DoorBlock block) {

        String blockName = name(block);

        String blockPath = "block/" + blockName;
        ResourceLocation bottomBlockRL = rl(blockPath + "_bottom");
        ResourceLocation topBlockRL = rl(blockPath + "_top");

        doorBlockWithRenderType(block, bottomBlockRL, topBlockRL, "cutout");
        flatItem(blockName, "item");
    }

    protected void trapdoor(TrapDoorBlock block) {

        String blockName = name(block);

        String blockPath = "block/" + blockName;
        ResourceLocation blockRL = rl(blockPath);

        ModelFile itemModel = models().trapdoorBottom(blockName, blockRL);

        trapdoorBlockWithRenderType(block, blockRL, true, "cutout");
        simpleBlockItem(block, itemModel);
    }

    protected void pressurePlate(PressurePlateBlock block, Block blockMaterial) {

        String blockName = name(block);
        String blockMaterialName = name(blockMaterial);

        String blockMaterialPath = "block/" + blockMaterialName;
        ResourceLocation blockMaterialRL = rl(blockMaterialPath);

        ModelFile itemModel = models().pressurePlate(blockName, blockMaterialRL);

        pressurePlateBlock(block, blockMaterialRL);
        simpleBlockItem(block, itemModel);
    }

    protected void button(ButtonBlock block, Block blockMaterial) {

        String blockName = name(block);
        String blockMaterialName = name(blockMaterial);

        String blockMaterialPath = "block/" + blockMaterialName;
        ResourceLocation blockMaterialRL = rl(blockMaterialPath);

        buttonBlock(block, blockMaterialRL);

        ModelFile itemModel = models().buttonInventory(blockName + "_inventory", blockMaterialRL);

        simpleBlockItem(block, itemModel);
    }

    protected void sign(StandingSignBlock blockSign, WallSignBlock blockWallSign, Block blockMaterial) {

        String blockSignName = name(blockSign);
        String blockMaterialName = name(blockMaterial);

        String blockMaterialPath = "block/" + blockMaterialName;
        ResourceLocation blockMaterialRL = rl(blockMaterialPath);

        signBlock(blockSign, blockWallSign, blockMaterialRL);
        flatItem(blockSignName, "item");
    }

    protected void hangingSign(CeilingHangingSignBlock blockHangingSign, WallHangingSignBlock blockHangingWallSign, Block blockMaterial) {

        String blockHangingSignName = name(blockHangingSign);
        String blockMaterialName = name(blockMaterial);

        String blockMaterialPath = "block/" + blockMaterialName;
        ResourceLocation blockMaterialRL = rl(blockMaterialPath);

        hangingSignBlock(blockHangingSign, blockHangingWallSign, blockMaterialRL);

        flatItem(blockHangingSignName, "item");
    }

    protected ModelFile petalModel(String blockName, int petalAmount) {
        return models().withExistingParent(blockName + "_" + petalAmount, "minecraft:block/flowerbed_" + petalAmount)
                .texture("flowerbed", "block/" + blockName)
                .texture("stem", "block/" + blockName + "_stem")
                .renderType("cutout");
    }

    protected void deepslate(RotatedPillarBlock block) {

        String blockName = name(block);

        String blockSidePath = "block/" + blockName;
        String blockTopPath = "block/" + blockName + "_top";

        String parent = "block/cube_column_mirrored";

        ModelFile model = models().cubeColumn(blockName, rl(blockSidePath), rl(blockTopPath));
        ModelFile mirroredModel = models().
                withExistingParent(blockName + "_mirrored", parent)
                .texture("end", blockTopPath)
                .texture("side", blockSidePath);

        getVariantBuilder(block)
                .partialState().with(RotatedPillarBlock.AXIS, Direction.Axis.X).modelForState()
                .modelFile(model).rotationX(90).rotationY(90).nextModel()
                .modelFile(mirroredModel).rotationX(90).rotationY(90).addModel()
                .partialState().with(RotatedPillarBlock.AXIS, Direction.Axis.Y).modelForState()
                .modelFile(model).nextModel()
                .modelFile(mirroredModel).nextModel()
                .modelFile(model).rotationY(180).nextModel()
                .modelFile(mirroredModel).rotationY(180).addModel()
                .partialState().with(RotatedPillarBlock.AXIS, Direction.Axis.Z).modelForState()
                .modelFile(model).rotationX(90).nextModel()
                .modelFile(mirroredModel).rotationX(90).nextModel()
                .modelFile(model).rotationX(90).rotationY(180).nextModel()
                .modelFile(mirroredModel).rotationX(90).rotationY(180).addModel();

        simpleBlockItem(block, model);
    }

    protected void grass(Block grassBlock, Block dirtBlock) {

        String blockName = name(grassBlock);

        ResourceLocation modelTextureSide = texture(grassBlock, "block/", "_side");
        ResourceLocation modelTextureTop = texture(grassBlock, "block/", "_top");
        ResourceLocation modelTextureBottom = texture(dirtBlock, "block/");

        ModelFile model = models().cubeBottomTop(blockName, modelTextureSide, modelTextureBottom, modelTextureTop);

        simpleBlockWithItem(grassBlock, model);
    }

    protected void sapling(Block block) {

        String blockName = name(block);

        String blockPath = "block/" + blockName;

        ModelFile model = models().cross(blockName, rl(blockPath)).renderType("cutout");

        simpleBlock(block, model);
        flatItem(blockName, "block");
    }

    protected void crystal(Block block) {

        String blockName = name(block);
        String blockCrystalPath = "block/" + blockName;

        String modelParent = "minecraft:block/cross";
        ResourceLocation modelTexture = texture(block, "block/");

        ModelFile model = models().withExistingParent(blockCrystalPath, modelParent).texture("cross", modelTexture).renderType("cutout");

        getVariantBuilder(block)
                .partialState().with(BlockStateProperties.FACING, Direction.DOWN)
                .modelForState().modelFile(model).rotationX(180).addModel()
                .partialState().with(BlockStateProperties.FACING, Direction.EAST)
                .modelForState().modelFile(model).rotationX(90).rotationY(90).addModel()
                .partialState().with(BlockStateProperties.FACING, Direction.NORTH)
                .modelForState().modelFile(model).rotationX(90).addModel()
                .partialState().with(BlockStateProperties.FACING, Direction.SOUTH)
                .modelForState().modelFile(model).rotationX(90).rotationY(180).addModel()
                .partialState().with(BlockStateProperties.FACING, Direction.UP)
                .modelForState().modelFile(model).addModel()
                .partialState().with(BlockStateProperties.FACING, Direction.WEST)
                .modelForState().modelFile(model).rotationX(90).rotationY(270).addModel();

        flatItem(blockName, "block");
    }

    protected void petal(Block block) {

        String blockName = name(block);

        Direction[] directions = {Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
        int[] flowerAmounts = {1, 2, 3, 4};
        ModelFile[] models = {
                petalModel(blockName, 1),
                petalModel(blockName, 2),
                petalModel(blockName, 3),
                petalModel(blockName, 4)
        };

        MultiPartBlockStateBuilder builder = getMultipartBuilder(block);

        for (int i = 0; i < models.length; i++) {

            for (Direction direction : directions) {

                int[] applicableAmounts = Arrays.copyOfRange(flowerAmounts, i, flowerAmounts.length);

                builder.part().modelFile(models[i])
                        .rotationY(direction.getCounterClockWise().getCounterClockWise().get2DDataValue() * 90)
                        .addModel()
                        .condition(BlockStateProperties.HORIZONTAL_FACING, direction)
                        .condition(BlockStateProperties.FLOWER_AMOUNT, Arrays.stream(applicableAmounts).boxed().toArray(Integer[]::new))
                        .end();
            }
        }

        flatItem(blockName, "item");
    }

    protected void flowerPot(Block blockPottedFlower, Block blockFlower) {

        String pottedFlowerName = name(blockPottedFlower);
        String flowerName = name(blockFlower);

        String pottedFlowerPath = "block/" + pottedFlowerName;

        String modelParent = "minecraft:block/flower_pot_cross";
        ResourceLocation modelTextureFlower = texture(blockFlower, "block/");

        ModelFile model = models().withExistingParent(pottedFlowerPath, modelParent).texture("plant", modelTextureFlower).renderType("cutout");

        simpleBlock(blockPottedFlower, model);
    }

    protected void flatItem(String name, String textureSource) {
        itemModels().getBuilder(name)
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", rl(textureSource + "/" + name));
    }

    protected ResourceLocation texture(Block block, String pathPrefix) {
        return texture(block, pathPrefix, "");
    }

    protected ResourceLocation texture(Block block, String pathPrefix, String pathSuffix) {
        ResourceLocation blockKey = key(block);
        return ResourceLocation.fromNamespaceAndPath(blockKey.getNamespace(), pathPrefix + blockKey.getPath() + pathSuffix);
    }

    protected ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath(modId, path);
    }

    protected ResourceLocation key(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
    }

    protected String name(Block block) {
        return key(block).getPath();
    }
}
