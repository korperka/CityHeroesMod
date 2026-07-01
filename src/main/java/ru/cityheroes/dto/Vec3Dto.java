package ru.cityheroes.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import net.minecraft.world.phys.Vec3;

@AllArgsConstructor @NoArgsConstructor
public class Vec3Dto {
    public double x;
    public double y;
    public double z;

    public Vec3 toVec3() {
        return new Vec3(x, y, z);
    }

    public static Vec3Dto from(Vec3 vec) {
        return new Vec3Dto(vec.x, vec.y, vec.z);
    }
}