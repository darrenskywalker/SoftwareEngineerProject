package com.hs1;

import lombok.Getter;

@Getter
public class Subscriptions {
    private String name;
    private boolean isSubscribed;

    public Subscriptions(boolean isSubscribed) {
        this.isSubscribed = isSubscribed;
    }


}
