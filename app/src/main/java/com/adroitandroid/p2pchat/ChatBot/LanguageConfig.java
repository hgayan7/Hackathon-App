package com.adroitandroid.p2pchat.ChatBot;

public class LanguageConfig {
    private final String languageCode;
    private final String accessToken;

    public LanguageConfig(final String languageCode, final String accessToken) {
        this.languageCode = languageCode;
        this.accessToken = accessToken;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public String getAccessToken() {
        return accessToken;
    }

    @Override
    public String toString() {
        return languageCode;
    }
}
