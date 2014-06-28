package com.ikeirnez.novelcompression.compress;

import java.util.regex.Pattern;

/**
 * Created by iKeirNez on 28/06/2014.
 */
public enum WordType {

    CAPITALISED(Pattern.compile("^[A-Z]{1,}[a-z_0-9]*[?.,!;:]?$"), "^"), LOWERCASE(Pattern.compile("^[a-z_0-9]+$"), ""), UPPERCASE(Pattern.compile("^[A-Z_0-9]+$"), "!");

    private Pattern pattern;
    private String suffix;

    private WordType(Pattern pattern, String suffix){
        this.pattern = pattern;
        this.suffix = suffix;
    }

    public boolean matches(String s){
        return pattern.matcher(s).matches();
    }

    public String getSuffix() {
        return suffix;
    }

    public static WordType match(String s){
        for (WordType wordType : values()){
            if (wordType.matches(s)){
                return wordType;
            }
        }

        return LOWERCASE;
    }
}
