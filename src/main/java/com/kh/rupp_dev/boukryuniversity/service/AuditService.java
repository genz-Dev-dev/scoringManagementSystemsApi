package com.kh.rupp_dev.boukryuniversity.service;

import com.kh.rupp_dev.boukryuniversity.dto.response.AuditResponse;

import java.util.List;

public interface AuditService {

    List<AuditResponse> getAll();

}
