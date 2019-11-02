package com.qv.learn.scrapper;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ScrappedDataRepository extends JpaRepository<ScrappedData,Long> {
}
