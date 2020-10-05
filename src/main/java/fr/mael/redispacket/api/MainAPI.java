package fr.mael.redispacket.api;

import lombok.Getter;

@Getter
public class MainAPI {
    private static MainAPI mainAPI;

    public MainAPI() {
        mainAPI = this;
    }

    public static MainAPI getMainAPI() {
        return mainAPI;
    }
}
