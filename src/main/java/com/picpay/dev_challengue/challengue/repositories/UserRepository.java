package com.picpay.dev_challengue.challengue.repositories;

import com.picpay.dev_challengue.challengue.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {

}
