package restAssuredAPI;

import lombok.Builder;
import lombok.Data;

@Builder
public class User {
    private String name;
    private String job;
}