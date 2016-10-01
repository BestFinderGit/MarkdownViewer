package com.andressantibanez.markdownviewer;

public class MarkdownParser {

    private String message;
    private String tokenizedMessage;
    private String[] tokenizedMessageParts;
    private int totalTokenizedMessageParts;

    private int currentTokenizedMessagePartIndex;
    private String currentTokenizedMessagePart;

    public MarkdownParser(String message) {
        this.message = message;
        this.tokenizedMessage = MarkdownTokenizer.tokenize(message);
        this.tokenizedMessageParts = this.tokenizedMessage.split(MarkdownTokenizer.SEPARATOR_PATTERN);
        this.totalTokenizedMessageParts = this.tokenizedMessageParts.length;

        this.currentTokenizedMessagePartIndex = -1;
        this.currentTokenizedMessagePart = "";
    }

    public boolean next() {
        currentTokenizedMessagePartIndex += 1;
        if (currentTokenizedMessagePartIndex >= totalTokenizedMessageParts)
            return false;

        move(currentTokenizedMessagePartIndex);

        return currentTokenizedMessagePart.length() != 0 || next();
    }

    private void move(int index) {
        this.currentTokenizedMessagePartIndex = index;
        this.currentTokenizedMessagePart = this.tokenizedMessageParts[index];
    }

    public String contentString() {
        String messageContent = this.currentTokenizedMessagePart;

        //Strip all tokens
        messageContent = messageContent.replace(Configurations.Tokens.BOLD, "");
        messageContent = messageContent.replace(Configurations.Tokens.ITALICS, "");
        messageContent = messageContent.replace(Configurations.Tokens.STRIKE_THROUGH, "");
        messageContent = messageContent.replace(Configurations.Tokens.SINGLE_LINE_CODE, "");
        messageContent = messageContent.replace(Configurations.Tokens.MULTI_LINE_CODE, "");
        messageContent = messageContent.replace(Configurations.Tokens.MENTION, "");

        return messageContent;
    }

    public boolean contentIsBold() {
        return TokenizedStringUtils.isBold(this.currentTokenizedMessagePart);
    }

    public boolean contentIsItalics() {
        return TokenizedStringUtils.isItalics(this.currentTokenizedMessagePart);
    }

    public boolean contentIsSingleLineCode() {
        return TokenizedStringUtils.isSingleLineCode(this.currentTokenizedMessagePart);
    }

    public boolean contentIsMultiLineCode() {
        return TokenizedStringUtils.isMultiLineCode(this.currentTokenizedMessagePart);
    }

    public boolean contentIsStrikeThrough() {
        return TokenizedStringUtils.isStrikeThrough(this.currentTokenizedMessagePart);
    }

    public boolean contentIsMention() {
        return TokenizedStringUtils.isMention(this.currentTokenizedMessagePart);
    }

    public boolean contentWithNoStyle() {
        return !contentIsBold() &&
                !contentIsItalics() &&
                !contentIsSingleLineCode() &&
                !contentIsMultiLineCode() &&
                !contentIsStrikeThrough() &&
                !contentIsMention();
    }


}
