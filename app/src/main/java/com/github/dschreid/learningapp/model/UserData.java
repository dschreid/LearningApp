package com.github.dschreid.learningapp.model;

import java.util.Set;

import lombok.Data;

/**
 * Nutzerdaten Modell
 *
 * @author dschreid
 */
@Data
public class UserData {
    private String language;
    private int points;
    private int minutesLearned;
    private Set<String> boughtItems;
}
