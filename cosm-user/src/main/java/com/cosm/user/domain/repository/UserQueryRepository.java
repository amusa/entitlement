package com.cosm.user.domain.repository;

import com.cosm.common.domain.repository.QueryRepository;
import com.cosm.user.domain.model.User;

/**
 * Created by maliska on 12/16/17.
 */
public interface UserQueryRepository extends QueryRepository<User> {
    boolean authenticate(String userName, String password);
}
