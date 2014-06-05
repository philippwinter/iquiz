/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2014 Philipp Winter, Marcel Toerschen & Jan Geissler         *
 ******************************************************************************/

package iquiz.main.model.game;

import iquiz.main.model.Helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by philipp on 08.05.14.
 */
public class Player implements Serializable {

    private static final HashMap<String, Player> playerPool;
    private static long nextPlayerId = 0;

    private long id;
    private String username;
    private String name;
    private String passwordHash;
    private String email;
    private Sex sex;
    private Language language;
    private Statistic statistic;
    private Date birthday;

    private ArrayList<Game> lastGames;

    static {
        playerPool = new HashMap<>();
        Player playerOne = new Player("phisa", "Philipp Winter", Helper.generateHash("lol"), "philipp.winter11@googlemail.com", Sex.MALE, Language.ENGLISH, new Date(1998, 5, 22));
        playerPool.put(playerOne.getUsername(), playerOne);
    }

    private ArrayList<Game> runningGames;

    public static Player get(String username) {
        return Player.playerPool.get(username);
    }

    public static Player factory(String username, String name, String passwordHash, String email, Sex sex, Language language, Date birthday) {
        if (playerPool.containsKey(username)) {
            return null; // Player already exists
        } else {
            return new Player(username, name, passwordHash, email, sex, language, birthday);
        }
    }

    private Player(String username, String name, String passwordHash, String email, Sex sex, Language language, Date birthday) {
        this(++nextPlayerId, username, name, passwordHash, email, sex, language, birthday);
    }

    private Player(long id, String username, String name, String passwordHash, String email, Sex sex, Language language, Date birthday) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.passwordHash = passwordHash;
        this.email = email;
        this.sex = sex;
        this.language = language;
        this.birthday = birthday;

        this.statistic = new Statistic();
        this.lastGames = new ArrayList<>();
        this.runningGames = new ArrayList<>();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(400);

        sb.append("ID:\t");
        sb.append(id);
        sb.append(";\n");

        sb.append("Username:\t");
        sb.append(username);
        sb.append(";\n");

        sb.append("Password Hash:\t");
        sb.append(passwordHash);
        sb.append(";\n");

        sb.append("Email:\t");
        sb.append(email);
        sb.append(";\n");

        sb.append("Sex:\t");
        sb.append(sex);
        sb.append(";\n");

        sb.append("Language:\t");
        sb.append(language);
        sb.append(";\n");

        sb.append("Birthday:\t");
        sb.append(birthday);
        sb.append(";\n");

        sb.append("Statistic:\t");
        sb.append(statistic);
        sb.append(";\n");

        sb.append("Last Games:\t");
        sb.append(lastGames);
        sb.append(";\n");

        sb.append("Running Games:\t");
        sb.append(runningGames);
        sb.append(";\n");

        return sb.toString();
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public Sex getSex() {
        return sex;
    }

    public Language getLanguage() {
        return language;
    }

    public Statistic getStatistic() {
        return statistic;
    }

    public Date getBirthday() {
        return birthday;
    }

    public ArrayList<Game> getLastGames() {
        return lastGames;
    }

    public ArrayList<Game> getRunningGames() {
        return runningGames;
    }

    public enum Sex {
        MALE,
        FEMALE,
        UNKNOWN
    }
}
