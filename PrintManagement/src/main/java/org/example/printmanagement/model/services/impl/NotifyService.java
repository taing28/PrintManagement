package org.example.printmanagement.model.services.impl;

import org.example.printmanagement.model.entities.Notification;
import org.example.printmanagement.model.repositories.NotificationRepo;
import org.example.printmanagement.model.services.INotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class NotifyService implements INotifyService {
    @Autowired
    private NotificationRepo _notificationRepo;

    @Override
    public List<Notification> listByUserId(int id) {
        return _notificationRepo.findAllByUserId(id);
    }
}
