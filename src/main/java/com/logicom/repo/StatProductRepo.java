package com.logicom.repo;

import com.logicom.model.StatProduct;
import com.logicom.model.enums.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatProductRepo extends JpaRepository<StatProduct, Long> {
    StatProduct findByIdOrderingDetail(Long id);

    List<StatProduct> findAllByProductStatusAndDateOrderByIdDesc(ProductStatus productStatus, String date);

    List<StatProduct> findAllByOrderByIdDesc();
    List<StatProduct> findAllByProductStatus(ProductStatus productStatus);

    List<StatProduct> findAllByDateOrderByIdDesc(String date);

    List<StatProduct> findAllByProductStatusOrderByIdDesc(ProductStatus productStatus);

    StatProduct findStatProductById(Long id);
}
