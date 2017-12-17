package com.cosm.user.domain.repository;

import com.cosm.common.domain.repository.CommandRepository;
import com.cosm.user.domain.model.User;

/**
 * Created by maliska on 12/16/17.
 */
public interface UserCommandRepository extends CommandRepository<User> {

    void updatePassword(String userName, String newPassword);

}
