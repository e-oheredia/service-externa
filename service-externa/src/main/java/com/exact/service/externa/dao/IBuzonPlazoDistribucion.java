package com.exact.service.externa.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.exact.service.externa.entity.BuzonPlazoDistribucion;

@Repository
public interface IBuzonPlazoDistribucion extends CrudRepository<BuzonPlazoDistribucion, Long> {

}
