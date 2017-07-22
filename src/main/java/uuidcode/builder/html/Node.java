package uuidcode.builder.html;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Node<T extends Node> {
    public static final String INDENT = "    ";

    private List<Node> childNodeList = new ArrayList<>();
    private List<Attribute> attributeList = new ArrayList<>();
    private String tagName;
    private Node parentNode;
    private boolean requiresEndTag = true;

    public boolean getRequiresEndTag() {
        return this.requiresEndTag;
    }

    public T setRequiresEndTag(boolean requiresEndTag) {
        this.requiresEndTag = requiresEndTag;
        return (T) this;
    }

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
        return add(Arrays.asList(node));
    }

    public T add(List<Node> nodeList) {
        nodeList.stream().forEach(this::add);
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
        contentList.add("<" + this.tagName + this.attribute() + ">");
        contentList.addAll(this.getChildContentList());

        if (this.requiresEndTag) {
            contentList.add("</" + this.tagName + ">");
        }

        return this.shrink(contentList);
    }

    private String attribute() {
        if (this.attributeList.size() == 0) {
            return "";
        }

        return " " + this.attributeList.stream()
            .map(Attribute::toString)
            .collect(Collectors.joining(" "));
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

    public Node setId(String id) {
        this.attributeList.add(Attribute.of("id", id));
        return (T) this;
    }
}
