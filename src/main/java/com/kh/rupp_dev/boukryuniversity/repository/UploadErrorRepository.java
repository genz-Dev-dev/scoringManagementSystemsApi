package com.kh.rupp_dev.boukryuniversity.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kh.rupp_dev.boukryuniversity.entity.UploadError;

@Repository
public interface UploadErrorRepository extends JpaRepository<UploadError, UUID> {

}
