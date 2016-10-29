package com.andressantibanez.markdownviewersample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.andressantibanez.markdownviewer.MarkdownView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MarkdownView markdownView = (MarkdownView) findViewById(R.id.markdown_view);

        String markdown = "@andres ```this is awesome``` and **bold** \n```\nfunction(){\n  return 1;\n}\n``` cc @everyone. Here is a link www.google.com";
        markdownView.setMarkdown(markdown);
    }
}
