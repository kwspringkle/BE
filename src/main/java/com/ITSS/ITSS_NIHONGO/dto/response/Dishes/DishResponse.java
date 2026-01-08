package com.ITSS.ITSS_NIHONGO.dto.response.Dishes;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DishResponse {
    public int id;
    public String name;
    public String imageUrl;
    public int likes;
    public String description;
}
