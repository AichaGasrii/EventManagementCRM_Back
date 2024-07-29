package com.example.gestionauth.repositories;

import com.example.gestionauth.persistence.entity.UserMail;

public interface IUserEmailRepository {
    public void sendCodeByMail(UserMail mail);
}
