package com.kwpugh.powder_power.items.tokens;

import java.util.List;

import javax.annotation.Nullable;

import com.kwpugh.powder_power.util.EnableUtil;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TokenHaste extends Item
{

	public TokenHaste(Properties properties)
	{
		super(properties);
	}

	public void inventoryTick(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
	{		
		if(entity instanceof PlayerEntity && !world.isRemote && EnableUtil.isEnabled(stack))
		{
			PlayerEntity player = (PlayerEntity)entity;
	
			if (player.ticksExisted % 180 == 0)
			{
				player.addPotionEffect(new EffectInstance(Effects.HASTE, 260, 2, false, false));
			} 
		}
	}	

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand)
	{
		ItemStack stack = player.getHeldItem(hand);
	
		if(!world.isRemote && player.isCrouching())
		{
			EnableUtil.changeEnabled(player, hand);
			player.sendMessage((new TranslationTextComponent("item.powder_power.token_haste.line2", EnableUtil.isEnabled(stack)).applyTextStyle(TextFormatting.BOLD)));
			return new ActionResult<ItemStack>(ActionResultType.SUCCESS, player.getHeldItem(hand));
		}
		return super.onItemRightClick(world, player, hand);
	}
  
	@OnlyIn(Dist.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
	{
		super.addInformation(stack, worldIn, tooltip, flagIn);
		tooltip.add((new TranslationTextComponent("item.powder_power.token_haste.line1").applyTextStyle(TextFormatting.GREEN)));
		tooltip.add((new TranslationTextComponent("item.powder_power.token_haste.line2", EnableUtil.isEnabled(stack)).applyTextStyle(TextFormatting.RED)));
		tooltip.add((new TranslationTextComponent("item.powder_power.token.general2").applyTextStyle(TextFormatting.AQUA)));
	}
}
