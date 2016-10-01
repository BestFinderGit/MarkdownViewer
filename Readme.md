
WIP

Will document the library soon.

Quick demo
1) Add view to layout
```xml
<com.andressantibanez.markdownviewer.MarkdownView
    android:id="@+id/markdown_view"
    android:layout_width="match_parent"
    android:layout_height="wrap_content" />
```

2) Set markdown programmatically
```java
MarkdownView markdownView = (MarkdownView) findViewById(R.id.markdown_view);
String markdown = "```this is code``` and **bold** here";
markdownView.setMarkdown(markdown);
```
