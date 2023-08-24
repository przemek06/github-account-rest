package com.empik.githubaccountrest.repository;

import com.empik.githubaccountrest.entity.LoginCount;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginCountRepository extends JpaRepository<LoginCount, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<LoginCount> getByLogin(String login);
}
