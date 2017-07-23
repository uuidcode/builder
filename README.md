# builder

```java
 @Test
public void selectBox() {
    List<String> nameList = IntStream.range(0, 3)
        .mapToObj(String::valueOf)
        .collect(Collectors.toList());

    Div div = div(
        input(),
        a(
            text("heart"),
            span().addClass("ico_comm")
        ),
        ul(this.createLiList(nameList)),
        script(text("var i = 'Hello, World!';"),
            text("console.log(i);"))
    );

    div.setId("projectTypeContainer").addClass("opt_comm4");
    div.getChildNodeList().get(0)
        .setType("hidden")
        .setName("projectType")
        .setId("projectType")
        .setValue("HEART");

    div.getChildNodeList().get(1).setId("projectTypeLabel")
        .addClass("link_selected");
    div.getChildNodeList().get(2).addClass("list_opt");

    this.assertHtml(div.html(), "selectBox");
}

```

```html
<div id="projectTypeContainer" class="opt_comm4">
    <input type="hidden" name="projectType" id="projectType" value="HEART">
    <a id="projectTypeLabel" class="link_selected">
        heart
        <span class="ico_comm"></span>
    </a>
    <ul class="list_opt">
        <li><a id="type_0" href="http://www.google.com?q=0">0</a></li>
        <li><a id="type_1" href="http://www.google.com?q=1">1</a></li>
        <li><a id="type_2" href="http://www.google.com?q=2">2</a></li>
    </ul>
    <script>
        var i = 'Hello, World!';
        console.log(i);
    </script>
</div>
```