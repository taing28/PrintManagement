package org.example.printmanagement.model.repositories;

import org.example.printmanagement.model.entities.ConfirmEmail;
import org.example.printmanagement.model.entities.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmEmailRepo extends JpaRepository<ConfirmEmail, Integer> {
    ConfirmEmail findConfirmEmailByConfirmCodeEquals(String confirmCode);

    ConfirmEmail findConfirmEmailByUserIdAndConfirmCode(int userId, String cfCode);
}
