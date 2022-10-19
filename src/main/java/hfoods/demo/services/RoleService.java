package hfoods.demo.services;

import hfoods.demo.dto.FoodDTO;
import hfoods.demo.dto.RoleDTO;
import hfoods.demo.repositories.FoodRepository;
import hfoods.demo.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public Page<RoleDTO> findAll(Pageable pageable) {
        var foods = roleRepository.findAll(pageable);

        return foods.map(RoleDTO::new);
    }
}
