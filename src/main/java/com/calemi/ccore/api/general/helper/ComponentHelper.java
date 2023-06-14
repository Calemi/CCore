package com.calemi.ccore.api.general.helper;

import com.calemi.ccore.main.CCoreRef;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class ComponentHelper {

    /**
     * @param count The count to use.
     * @return A message containing the number counted by stacks.
     */
    public static MutableComponent countByStacks(int count) {

        int remainder = (count % 64);

        MutableComponent msg = Component.literal(StringHelper.insertCommas(count));
        msg.append(" ");
        msg.append(Component.translatable(CCoreRef.MOD_ID + ".blocks"));

        if (count > 64) {

            msg.append(" (" + ((int) Math.floor((float) count / 64)));
            msg.append(" ");
            msg.append(Component.translatable(CCoreRef.MOD_ID + ".stacks"));

            if (remainder > 0) {
                msg.append(" + " + remainder + " ");
                msg.append(Component.translatable(CCoreRef.MOD_ID + ".blocks"));
            }

            msg.append(")");
        }

        return msg;
    }
}
