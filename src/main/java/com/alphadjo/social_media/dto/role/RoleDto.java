package com.alphadjo.social_media.dto.role;

import com.alphadjo.social_media.entity.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RoleDto {

    private Long id;

    @NotBlank(message = "Name is required")
    private String name;
    private boolean isActive = false;

    public static Role toEntity(RoleDto roleDto){

        if(roleDto == null) return null;

        return Role.builder()
                .name(roleDto.name)
                .isActive(roleDto.isActive)
                .build();
    }

    public static RoleDto fromEntity(Role role){

        if(role == null) return null;

        return RoleDto.builder()
                .id(role.getId())
                .name("ROLE_"+role.getName())
                .isActive(role.isActive())
                .build();
    }
}
