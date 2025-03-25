package com.trimble.trimblecars.repositories;

import com.trimble.trimblecars.entities.CarLeaseHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarLeaseHistoryRepository extends JpaRepository<CarLeaseHistory,String> {}
