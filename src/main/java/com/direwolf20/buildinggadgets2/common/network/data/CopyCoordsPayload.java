package com.direwolf20.buildinggadgets2.common.network.data;

import com.direwolf20.buildinggadgets2.BuildingGadgets2;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record CopyCoordsPayload(
        BlockPos startPos,
        BlockPos endPos
) implements CustomPacketPayload {
    public static final Type<CopyCoordsPayload> TYPE = new Type<>(new ResourceLocation(BuildingGadgets2.MODID, "copy_coords_payload"));

    @Override
    public Type<CopyCoordsPayload> type() {
        return TYPE;
    }

    public static final StreamCodec<FriendlyByteBuf, CopyCoordsPayload> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC, CopyCoordsPayload::startPos,
            BlockPos.STREAM_CODEC, CopyCoordsPayload::endPos,
            CopyCoordsPayload::new
    );
}
