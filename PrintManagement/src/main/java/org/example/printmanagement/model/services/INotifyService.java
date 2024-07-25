package org.example.printmanagement.model.services;

import org.example.printmanagement.model.entities.Notification;

import java.util.List;

public interface INotifyService {
    List<Notification> listByUserId(int id);
}
