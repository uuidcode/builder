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

        Stream<String> childNodeContentStream = listStream
            .map(this::shrink)
            .map(list -> {
                if (this.childNodeList.size() <= 1) {
                    if (list.size() >= 3) {
                        return processIndent(list);
                    }
                } else {
                    return processIndent(list);
                }

                return list;
            })
            .flatMap(List::stream);

        List<String> list = childNodeContentStream.collect(Collectors.toList());
        return list;
    }

    private List<String> processIndent(List<String> list) {
        return list.stream().map(this::prependIndent).collect(Collectors.toList());
    }

    private String prependIndent(String content) {
        return generate(INDENT, 1) + content;
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

    public String generate(String text, int size) {
        return IntStream.range(0, size)
            .mapToObj(i -> text)
            .collect(Collectors.joining());
    }
}
