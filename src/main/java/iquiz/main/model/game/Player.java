/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2014 Philipp Winter, Marcel Toerschen & Jan Geissler         *
 ******************************************************************************/

package iquiz.main.model.game;

import iquiz.main.model.Helper;
import iquiz.main.model.game.question.BasicSolution;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

/**
 * Created by philipp on 08.05.14.
 */
public class Player implements Serializable {

    private static final HashMap<String, Player> playerPool;
    private static long nextPlayerId = 0;

    private long id;
    private String username;
    private String nickname;
    private String passwordHash;
    private String email;
    private Sex sex;
    private Language language;
    private Statistic statistic;
    private Date birthday;

    private Vector<Game> games;

    static {
        playerPool = new HashMap<>();

        Player playerOne = new Player("phisa", "Philipp Winter", Helper.generateHash("lol"), "philipp.winter11@googlemail.com", Sex.MALE, Language.ENGLISH, new Date(1998, 5, 22));
        Player playerTwo = new Player("jan", "Janus Schleudermän", Helper.generateHash("aha"), "janus@anus.com", Sex.MALE, Language.ENGLISH, new Date(1996, 1, 3));
        Player playerThree = new Player("marshall", "Marcel Törschen", Helper.generateHash("yoyo"), "marceldamuddafckin@gmail.com", Sex.MALE, Language.ENGLISH, new Date(1995, 1, 1));

        put(playerOne);
        put(playerTwo);
        put(playerThree);

        Game g1 = Game.factory(playerOne, playerTwo);
        Game g2 = Game.factory(playerOne, playerThree);

        for(int i = 0; i < Game.QUESTIONS_PER_GAME; i++){
            ArrayList<BasicSolution> solutions = g1.getQuestions().get(i).getSolutions();

            if(solutions.size() != 0){
                BasicSolution solution1 = solutions.get((int) (solutions.size() * Math.random()));
                BasicSolution solution2 = solutions.get((int) (solutions.size() * Math.random()));

                g1.getQuestions().get(i).getChosenAnswers().put(playerOne, solution1);
                g1.getQuestions().get(i).getChosenAnswers().put(playerTwo, solution2);
            }
        }
    }

    public static Player get(String username) {
        return Player.playerPool.get(username);
    }

    private static void put(Player player){
        playerPool.put(player.getUsername(), player);
    }

    public static Player factory(String username, String name, String passwordHash, String email, Sex sex, Language language, Date birthday) {
        if (playerPool.containsKey(username)) {
            return null; // Player already exists
        } else {
            return new Player(username, name, passwordHash, email, sex, language, birthday);
        }
    }

    private Player(String username, String nickname, String passwordHash, String email, Sex sex, Language language, Date birthday) {
        this(++nextPlayerId, username, nickname, passwordHash, email, sex, language, birthday);
    }

    private Player(long id, String username, String nickname, String passwordHash, String email, Sex sex, Language language, Date birthday) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.passwordHash = passwordHash;
        this.email = email;
        this.sex = sex;
        this.language = language;
        this.birthday = birthday;

        this.statistic = new Statistic();
        this.games = new Vector<>();
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
        sb.append("Running Games:\t");
        sb.append(games);
        sb.append(";\n");

        return sb.toString();
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getNickname() {
        return nickname;
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

    public Vector<Game> getGames() {
        return games;
    }

    public void setGames(Vector<Game> games) {
        this.games = games;
    }

    public enum Sex {
        MALE,
        FEMALE,
        UNKNOWN
    }
}
