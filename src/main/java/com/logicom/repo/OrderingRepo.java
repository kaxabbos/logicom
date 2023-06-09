package com.logicom.repo;

import com.logicom.model.Client;
import com.logicom.model.Ordering;
import com.logicom.model.enums.OrderingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderingRepo extends JpaRepository<Ordering, Long> {
    List<Ordering> findAllByOrderByIdDesc();

    List<Ordering> findAllByOrderingStatus(OrderingStatus orderingStatus);

    List<Ordering> findAllByOrderingStatusOrderByIdDesc(OrderingStatus orderingStatus);

    List<Ordering> findAllByDateOrderByIdDesc(String date);

    List<Ordering> findAllByOrderingStatusAndDateOrderByIdDesc(OrderingStatus orderingStatus, String date);

    int countByOrderingStatus(OrderingStatus orderingStatus);

    Ordering findOrderingById(Long id);

    Ordering findByClientAndDateAndOrderingStatus(Client client, String date, OrderingStatus orderingStatus);
}
