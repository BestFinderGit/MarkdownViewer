package com.andressantibanez.markdownviewer;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rx.Observable;
import rx.functions.Func1;

public class MarkdownTokenizer {

    public static final String SEPARATOR = "[SEPARATOR]";
    public static final String SEPARATOR_PATTERN = "\\[SEPARATOR\\]";

    //Tokenizers
    private static Func1<String, String> tokenizeSingleLineCode() {
        Pattern pattern = Configurations.Patterns.SINGLE_LINE_CODE;
        String identifier = Configurations.Identifiers.SINGLE_LINE_CODE;
        String token = Configurations.Tokens.SINGLE_LINE_CODE;
        return tokenizeWith(pattern, identifier, token);
    }

    private static Func1<String, String> tokenizeMultiLineCode() {
        Pattern pattern = Configurations.Patterns.MULTI_LINE_CODE;
        String identifier = Configurations.Identifiers.MULTI_LINE_CODE;
        String token = Configurations.Tokens.MULTI_LINE_CODE;
        return tokenizeWith(pattern, identifier, token);
    }

    private static Func1<String, String> tokenizeBold() {
        Pattern pattern = Configurations.Patterns.BOLD;
        String identifier = Configurations.Identifiers.BOLD;
        String token = Configurations.Tokens.BOLD;
        return tokenizeWith(pattern, identifier, token);
    }

    private static Func1<String, String> tokenizeItalics() {
        Pattern pattern = Configurations.Patterns.ITALICS;
        String identifier = Configurations.Identifiers.ITALICS;
        String token = Configurations.Tokens.ITALICS;
        return tokenizeWith(pattern, identifier, token);
    }

    private static Func1<String, String> tokenizeStrikeThrough() {
        Pattern pattern = Configurations.Patterns.STRIKE_THROUGH;
        String identifier = Configurations.Identifiers.STRIKE_THROUGH;
        String token = Configurations.Tokens.STRIKE_THROUGH;
        return tokenizeWith(pattern, identifier, token);
    }

    private static Func1<String, String> tokenizeMention() {
        Pattern pattern = Configurations.Patterns.MENTION;
        String identifier = Configurations.Identifiers.MENTION;
        String token = Configurations.Tokens.MENTION;
        return tokenizeWith(pattern, identifier, token);
    }

    private static Func1<String, String> tokenizeWith(final Pattern pattern,
                                                      final String identifier,
                                                      final String token) {
        return new Func1<String, String>() {
            @Override
            public String call(String tokenizedMesage) {
                Matcher matcher = pattern.matcher(tokenizedMesage);
                while (matcher.find()) {
                    String currentSelection = matcher.group();
                    String newSelection = currentSelection.replace(identifier, "");
                    newSelection = String.format("%s%s%s%s", SEPARATOR, token, newSelection, SEPARATOR);
                    tokenizedMesage = tokenizedMesage.replace(currentSelection, newSelection);
                }

                return tokenizedMesage;
            }
        };
    }

    public static String tokenize(String message) {
        return Observable.just(message)
                .map(tokenizeSingleLineCode())
                .map(tokenizeMultiLineCode())
                .map(tokenizeBold())
                .map(tokenizeItalics())
                .map(tokenizeStrikeThrough())
                .map(tokenizeMention())
                .toBlocking()
                .first();
    }
}
