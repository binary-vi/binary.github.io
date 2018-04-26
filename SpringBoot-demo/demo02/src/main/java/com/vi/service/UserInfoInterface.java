package com.vi.service;

import com.vi.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoInterface extends JpaRepository<UserInfo, Long> {
}
