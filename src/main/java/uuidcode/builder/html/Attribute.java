package uuidcode.builder.html;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Attribute {
    private String name;
    private List<String> valueList = new ArrayList<>();

    public static Attribute of(String name) {
        return new Attribute().setName(name);
    }

    public static Attribute of(String name, String value) {
        return new Attribute().setName(name).addValue(value);
    }

    public Attribute addValue(String value) {
        this.valueList.add(value);
        return this;
    }

    public List<String> getValueList() {
        return this.valueList;
    }

    public String getName() {
        return this.name;
    }
    
    public Attribute setName(String name) {
        this.name = name;
        return this;
    }
    
    public String toString() {
        String value = this.valueList.stream()
            .collect(Collectors.joining(" "));

        if (this.valueList.size() == 0) {
            return this.name;
        }

        return this.name + "=\"" + value + "\"";
    }
}
