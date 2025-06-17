package com.sparrows.board.kafka.properties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "kafka")
@Getter
@Setter
public class KafkaProperties {

    private AggregateType aggregateType;
    private Topic topic;
    private GroupId groupId;

    @Data
    public static class AggregateType{
        private String user;
        private String post;
        private String board;
        private String school;
    }

    @Data
    public static class Topic{
        private User user;
        private Post post;
        private Board board;
        private School school;
    }

    @Data
    public static class GroupId{
        private String search;
        private String school;
        private String board;
    }

    @Data
    public static class User{
        private String create;
        private String update;
        private String delete;
    }

    @Data
    public static class School{
        private String create;
        private String update;
        private String delete;
    }

    @Data
    public static class Board{
        private String create;
        private String update;
        private String delete;
    }

    @Data
    public static class Post{
        private String create;
        private String update;
        private String delete;
    }
}
