package uuidcode.builder.html;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Node<T> {
    private String tagName;

    public T setTagName(String tagName) {
        this.tagName = tagName;
        return (T) this;
    }

    public String html() {
        return this.contentList().stream().collect(Collectors.joining());
    }

    public List<String> contentList() {
        List<String> contentList = new ArrayList<>();
        contentList.add("<" + this.tagName + ">");
        contentList.add("</" + this.tagName + ">");
        return contentList;
    }
}
