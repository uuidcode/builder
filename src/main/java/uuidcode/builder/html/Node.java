package uuidcode.builder.html;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Node<T> {
    public static final String INDENT = "    ";
    private String tagName;
    private List<Node> childNodeList = new ArrayList<>();
    private Node parentNode;

    public List<Node> getChildNodeList() {
        return childNodeList;
    }

    public Node getParentNode() {
        return this.parentNode;
    }

    public Node setParentNode(Node parentNode) {
        this.parentNode = parentNode;
        return this;
    }

    public T add(Node node) {
        if (node == null) {
            return (T) this;
        }

        node.setParentNode(this);
        this.childNodeList.add(node);
        return (T) this;
    }

    public T add(Node... node) {
        for (Node child : node) {
            this.add(child);
        }
        return (T) this;
    }


    public T setTagName(String tagName) {
        this.tagName = tagName;
        return (T) this;
    }

    public String html() {
        return this.contentList().stream()
            .collect(Collectors.joining(System.lineSeparator()));
    }

    public List<String> contentList() {
        List<String> contentList = new ArrayList<>();
        contentList.add("<" + this.tagName + ">");
        contentList.addAll(this.getChildContentList());
        contentList.add("</" + this.tagName + ">");
        return this.shrink(contentList);
    }

    private List<String> getChildContentList() {
        Stream<List<String>> listStream = this.childNodeList.stream()
            .map(Node::contentList);

        return listStream
            .map(this::shrink)
            .map(this::processIndent)
            .flatMap(List::stream)
            .collect(Collectors.toList());
    }

    private List<String> processIndent(List<String> list) {
        if (this.childNodeList.size() <= 1) {
            if (list.size() >= 3) {
                return prependIndent(list);
            }
        } else {
            return prependIndent(list);
        }

        return list;
    }

    private List<String> prependIndent(List<String> list) {
        return list.stream()
            .map(this::prependIndent)
            .collect(Collectors.toList());
    }

    private String prependIndent(String content) {
        return INDENT + content;
    }

    private String joining(List<String> list) {
        return list.stream().collect(Collectors.joining());
    }

    private List<String> joiningAndAsList(List<String> list) {
        return Arrays.asList(this.joining(list));
    }

    public List<String> shrink(List<String> list) {
        if (list.size() <= 3) {
            return this.joiningAndAsList(list);
        }

        return list;
    }
}
