package org.ieeervce.api.siterearnouveau.repository;

import org.ieeervce.api.siterearnouveau.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImagesRepository extends JpaRepository<Image,Integer> {
    List<Image> findByEventCategory(int eventCategory);
}
