package net.golikov.springdddexample.ddd.model.json.to;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.List;

public interface Json {
    JsonNode toJson() throws Exception;

    static ArrayNode collectArray(ObjectMapper objectMapper, List<? extends Json> students) throws Exception {
        ArrayNode studentsNode = objectMapper.createArrayNode();
        for (Json student : students) {
            studentsNode.add(student.toJson());
        }
        return studentsNode;
    }
}
