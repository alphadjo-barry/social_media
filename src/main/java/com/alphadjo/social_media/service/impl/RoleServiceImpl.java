package com.alphadjo.social_media.service.impl;

import com.alphadjo.social_media.dto.role.RoleDto;
import com.alphadjo.social_media.entity.Role;
import com.alphadjo.social_media.repository.contract.RoleRepository;
import com.alphadjo.social_media.service.contract.RoleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public List<RoleDto> findAll() {

        return this.roleRepository.findAll().stream()
                .map(RoleDto::fromEntity)
                .toList();
    }

    @Override
    public RoleDto findById(Long id) {
        Role role = this.roleRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Role with the id provided is not found"));

        return RoleDto.fromEntity(role);
    }

    @Override
    public RoleDto save(RoleDto roleDto) {

        if(roleRepository.findByName(roleDto.getName()).isPresent()){
            throw new IllegalArgumentException("Role with the same name already exists");
        }

        Role newRole = RoleDto.toEntity(roleDto);
        Role savedRole = roleRepository.save(newRole);

        return RoleDto.fromEntity(savedRole);
    }

    @Override
    public RoleDto update(RoleDto entity, Long id) {

        Role role = roleRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Role with the id provided is not found"));

        Role other = roleRepository.findByName(entity.getName()).orElse(null);

        if(other != null && !other.getId().equals(role.getId())){
            throw new IllegalArgumentException("You can't change the role of another user");
        }

        role.setName(entity.getName());
        Role saved = this.roleRepository.save(role);

        return RoleDto.fromEntity(saved);
    }

    @Override
    public void delete(Long id) {

        if(roleRepository.findById(id).isEmpty()){
            throw new EntityNotFoundException("Role with the id provided is not found");
        }

        roleRepository.deleteById(id);
    }
}
