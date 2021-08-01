package com.abc.provider.repository;

import com.abc.provider.bean.Depart;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DepartRepository extends JpaRepository<Depart, Integer> {
}
