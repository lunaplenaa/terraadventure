package net.lunaplenaa.terraadventure.entity.mobs.bitey;// Made with Blockbench 5.0.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.lunaplenaa.terraadventure.entity.animations.ModAnimationsDefinitions;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class BiteyModel<T extends BiteyEntity> extends HierarchicalModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	//public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "bitey"), "main");
	private final ModelPart bitey;
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart body2;
	private final ModelPart tail;
	private final ModelPart feet;
	private final ModelPart brfoot;
	private final ModelPart blfoot;
	private final ModelPart frfoot;
	private final ModelPart flfoot;
	private final ModelPart drool;
	private final ModelPart wings;
	private final ModelPart leftwing;
	private final ModelPart rightwing;

	public BiteyModel(ModelPart root) {
		this.bitey = root.getChild("bitey");
		this.body = this.bitey.getChild("body");
		this.head = this.body.getChild("head");
		this.body2 = this.body.getChild("body2");
		this.tail = this.body.getChild("tail");
		this.feet = this.body.getChild("feet");
		this.brfoot = this.feet.getChild("brfoot");
		this.blfoot = this.feet.getChild("blfoot");
		this.frfoot = this.feet.getChild("frfoot");
		this.flfoot = this.feet.getChild("flfoot");
		this.drool = this.body.getChild("drool");
		this.wings = this.body.getChild("wings");
		this.leftwing = this.wings.getChild("leftwing");
		this.rightwing = this.wings.getChild("rightwing");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bitey = partdefinition.addOrReplaceChild("bitey", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition body = bitey.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 42).addBox(-4.0F, -10.0F, -1.0F, 9.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.0F, -8.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(4, 25).addBox(-5.0F, -8.2F, -3.0F, 11.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(66, 0).addBox(-6.0F, -10.0F, 1.0F, 13.0F, 10.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -9.0F, 0.0F));

		PartDefinition body2 = body.addOrReplaceChild("body2", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -11.9F, -12.6F, 13.0F, 12.0F, 12.0F, new CubeDeformation(0.0F))
		.texOffs(40, 27).addBox(-5.0F, -19.0F, -1.0F, 11.0F, 19.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.9F, 13.6F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 2.3F, 15.8F));

		PartDefinition tail_r1 = tail.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(46, 14).addBox(-2.0F, -2.0F, -1.0F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.4F, -0.1F, 0.2618F, 0.0F, 0.0F));

		PartDefinition feet = body.addOrReplaceChild("feet", CubeListBuilder.create(), PartPose.offset(2.0F, 7.0F, 3.0F));

		PartDefinition brfoot = feet.addOrReplaceChild("brfoot", CubeListBuilder.create().texOffs(38, 50).addBox(1.0F, -4.0F, -1.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, 0.0F, 8.0F));

		PartDefinition blfoot = feet.addOrReplaceChild("blfoot", CubeListBuilder.create().texOffs(38, 50).addBox(1.0F, -4.0F, -1.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 8.0F));

		PartDefinition frfoot = feet.addOrReplaceChild("frfoot", CubeListBuilder.create().texOffs(38, 50).addBox(1.0F, -4.0F, -1.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, 0.0F, 0.0F));

		PartDefinition flfoot = feet.addOrReplaceChild("flfoot", CubeListBuilder.create().texOffs(38, 50).addBox(1.0F, -4.0F, -1.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition drool = body.addOrReplaceChild("drool", CubeListBuilder.create().texOffs(3, 2).addBox(-3.0F, -2.3F, -0.3F, 1.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(3, 2).addBox(-1.0F, -2.6F, -0.2F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, -8.0F, -2.0F));

		PartDefinition wings = body.addOrReplaceChild("wings", CubeListBuilder.create(), PartPose.offset(7.1F, 0.3F, 5.9F));

		PartDefinition leftwing = wings.addOrReplaceChild("leftwing", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2618F, 0.1396F, 0.0F));

		PartDefinition cube_r1 = leftwing.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 56).addBox(0.0F, -3.0F, -1.0F, 1.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.48F, 0.0F, 0.0F));

		PartDefinition cube_r2 = leftwing.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 65).addBox(0.0F, -3.0F, -1.0F, 1.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.1F, 0.9F, -1.4F, -0.3054F, 0.0F, 0.0F));

		PartDefinition rightwing = wings.addOrReplaceChild("rightwing", CubeListBuilder.create(), PartPose.offsetAndRotation(-14.0F, 0.0F, 0.0F, 0.2618F, -0.1396F, 0.0F));

		PartDefinition cube_r3 = rightwing.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 56).addBox(0.0F, -3.0F, -1.0F, 1.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.48F, 0.0F, 0.0F));

		PartDefinition cube_r4 = rightwing.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 65).addBox(0.0F, -3.0F, -1.0F, 1.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.1F, 0.9F, -1.4F, -0.3054F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		this.animateWalk(ModAnimationsDefinitions.BITEYWALK, limbSwing, limbSwingAmount, 2f, 2.5f);
		this.animate(((BiteyEntity) entity).idleAnimationState, ModAnimationsDefinitions.BITEYIDLE, ageInTicks, 1f);
		this.animate(((BiteyEntity) entity).attackAnimationState, ModAnimationsDefinitions.BITEYATTACK, ageInTicks, 1f);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		bitey.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return bitey;
	}
}