package uuidcode.builder.html;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
        return Utils.joiningWithLineSeparator(this.contentList());
    }

    public List<String> contentList() {
        List<String> contentList = new ArrayList<>();
        contentList.add(Utils.startTag(this.tagName + this.attribute()));
        contentList.addAll(this.getChildContentList());

        if (this.requiresEndTag) {
            contentList.add(Utils.endTag(this.tagName));
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
        if (this.childNodeList.size() <= 1 && list.size() < 3) {
            return list;
        }

        return prependIndent(list);
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

    public T addAttribute(Attribute newAttribute) {
        this.attributeList.stream()
            .filter(attribute -> attribute.getName().equals(newAttribute.getName()))
            .findFirst()
            .map(attribute -> attribute.getValueSet().addAll(newAttribute.getValueSet()))
            .orElseGet(() ->this.attributeList.add(newAttribute));

        return (T) this;
    }

    public Node setId(String id) {
        this.attributeList.add(Attribute.of("id", id));
        return (T) this;
    }

    public T addClass(String className) {
        return this.addAttribute(Attribute.of("class", className));
    }

    public T addStyle(String style) {
        return this.addAttribute(Attribute.of("style", style));
    }

    public Node setType(String type) {
        this.attributeList.add(Attribute.of("type", type));
        return (T) this;
    }

    public Node setName(String name) {
        this.attributeList.add(Attribute.of("name", name));
        return (T) this;
    }

    public Node setValue(String value) {
        this.attributeList.add(Attribute.of("value", value));
        return (T) this;
    }

    public Node setHref(String href) {
        this.attributeList.add(Attribute.of("href", href));
        return (T) this;
    }
}
