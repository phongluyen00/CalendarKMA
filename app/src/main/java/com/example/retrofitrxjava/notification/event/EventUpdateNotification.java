package com.example.retrofitrxjava.notification.event;

import com.example.retrofitrxjava.main.model.Notification;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by luyenphong on 10/18/2020.
 * 0358844343
 * luyenphong00@gmail.com
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EventUpdateNotification {
    private List<Notification.Data> notifications;
}
