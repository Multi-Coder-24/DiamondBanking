package org.multicoder.diamondbanking.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.UUIDUtil;

import java.util.UUID;

public class PlayerAccount {
    public UUID playerID;
    private int Balance;

    public static final Codec<PlayerAccount>  CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("balance").forGetter(PlayerAccount::getBalance),
            UUIDUtil.CODEC.fieldOf("playerID").forGetter(PlayerAccount::getPlayerID)
    ).apply(instance,PlayerAccount::new));

    public PlayerAccount(int Balance,UUID playerID) {
        this.playerID = playerID;
        this.Balance = Balance;
    }
    public PlayerAccount(UUID playerID) {
        this.playerID = playerID;
        this.Balance = 0;
    }

    public PlayerAccount() {
    }

    public int getBalance() {
        return Balance;
    }
    public UUID getPlayerID() {
        return playerID;
    }

    public void IncreaseBalance(int amount) {
        this.Balance += amount;
    }
    public boolean DecreaseBalance(int amount) {
        if(this.Balance < amount) {
            return false;
        }
        else {
            this.Balance -= amount;
            return true;
        }
    }
}
