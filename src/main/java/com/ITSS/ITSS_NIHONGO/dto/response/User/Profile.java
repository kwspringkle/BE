package com.ITSS.ITSS_NIHONGO.dto.response.User;

import lombok.Builder;

@Builder
public class Profile {
    public String name;
    public String national;
    public String avatar;
    public String email;
}
