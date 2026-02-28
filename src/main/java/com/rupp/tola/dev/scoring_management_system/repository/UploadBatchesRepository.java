package com.rupp.tola.dev.scoring_management_system.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rupp.tola.dev.scoring_management_system.entity.UploadBatches;

@Repository
public interface UploadBatchesRepository extends JpaRepository<UploadBatches, UUID> {

}
    