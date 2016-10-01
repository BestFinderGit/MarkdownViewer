package com.andressantibanez.markdownviewer;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class MarkdownParserTest {
    @Test
    public void tokenizeMessageTest() {
        String message = "```this is awesome``` how about this **bold** ```loving this every \n minute``` ~~hi man~~ very cool *parser*";

        String tokenizedMessage = MarkdownTokenizer.tokenize(message);
        assertThat(TokenizedStringUtils.isBold(tokenizedMessage), is(true));
        assertThat(TokenizedStringUtils.isSingleLineCode(tokenizedMessage), is(true));
        assertThat(TokenizedStringUtils.isMultiLineCode(tokenizedMessage), is(true));
        assertThat(TokenizedStringUtils.isItalics(tokenizedMessage), is(true));
        assertThat(TokenizedStringUtils.isStrikeThrough(tokenizedMessage), is(true));
    }

    @Test
    public void markdownParserTest() {
        String message = "```this is awesome``` how about this **bold** ```loving this every \n minute``` ~~hi man~~ very cool *parser*";
        MarkdownParser parser = new MarkdownParser(message);

        String messagePart = "";
        int messagePosition = 0;
        while(parser.next()) {
            messagePosition += 1;
            messagePart = parser.contentString();

            if (messagePosition == 1) {
                assertThat("Is Single Line Code", parser.contentIsSingleLineCode(), is(true));
            }

            if (messagePosition == 3) {
                assertThat("Is Bold", parser.contentIsBold(), is(true));
            }

            if (messagePosition == 5) {
                assertThat("Is Multi Line Code", parser.contentIsMultiLineCode(), is(true));
            }

            if (messagePosition == 7) {
                assertThat("Is Strike Through", parser.contentIsStrikeThrough(), is(true));
            }

            if (messagePosition == 9) {
                assertThat("Is Italics", parser.contentIsItalics(), is(true));
            }
        }
    }

    @Test
    public void incompleteMarkdownTest() {
        String markdown = "@andres ```this is awesome``` and **bold** \n```function(){\n  return 1;\n}``` with the test";
        String tokenizedMarkdown = MarkdownTokenizer.tokenize(markdown);
        System.out.println(tokenizedMarkdown);

        MarkdownParser parser = new MarkdownParser(markdown);
        while(parser.next()) {
            System.out.println("- " + parser.contentString());
        }
    }
}