package org.multicoder.diamondbanking.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.UUIDUtil;
import net.minecraft.util.Util;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

public class SignShopData {
    public ItemStack SellItem = ItemStack.EMPTY;

    public int Price = 0;

    public UUID MerchantID = Util.NIL_UUID;

    public BlockPos StockChestPos = BlockPos.ZERO;

    public String Name = "";

    public static final Codec<SignShopData> CODEC = RecordCodecBuilder.create(instance -> instance.group(ItemStack.OPTIONAL_CODEC.fieldOf("sellitem").forGetter(SignShopData::SellItem), Codec.INT.fieldOf("price").forGetter(SignShopData::Price), UUIDUtil.CODEC.fieldOf("merchantid").forGetter(SignShopData::MerchantID), BlockPos.CODEC.fieldOf("stockchestpos").forGetter(SignShopData::StockChestPos), Codec.STRING.fieldOf("name").forGetter(SignShopData::Name)).apply(instance, SignShopData::new));

    public SignShopData(ItemStack SellItem, int Price, UUID MerchantID, BlockPos StockChestPos, String Name) {
        this.SellItem = SellItem;
        this.Price = Price;
        this.MerchantID = MerchantID;
        this.StockChestPos = StockChestPos;
        this.Name = Name;
    }

    public ItemStack SellItem() {
        return this.SellItem;
    }

    public int Price() {
        return this.Price;
    }

    public UUID MerchantID() {
        return this.MerchantID;
    }

    public BlockPos StockChestPos() {
        return this.StockChestPos;
    }

    public String Name() {
        return this.Name;
    }

    public SignShopData() {}

}
