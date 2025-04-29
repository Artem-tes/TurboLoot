package ru.web.TurboLoot.backend.services.interfaceservices;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public interface SettingsService {
    Map<String,Object> updateUserPassword(Map<String,Object> data, HttpServletRequest request);
    Map<String,Object> sendUserDataToSettingsPage(HttpServletRequest request);
    Map<String,Object> updateDataUserSettings(Map<String,Object> data, HttpServletRequest request);
}
