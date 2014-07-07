/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2014 Philipp Winter, Marcel Toerschen & Jan Geissler         *
 ******************************************************************************/

package iquiz.main.model.game.question;

import iquiz.main.model.Logging;

import java.io.*;
import java.util.*;

/**
 * Created by philipp on 27.06.14.
 */
public class QuestionPool {

    private static QuestionPool instance;

    private HashMap<String, EstimationQuestion> estimationQuestions;
    private HashMap<String, MultipleChoiceQuestion> multipleChoiceQuestions;

    public static QuestionPool getInstance() {
        if(instance == null){
            instance = new QuestionPool();
        }
        return instance;
    }

    public QuestionPool() {
        estimationQuestions = new HashMap<>();
        multipleChoiceQuestions = new HashMap<>();
        this.loadFromFile();
    }

    private void loadFromFile() {
        Logging.log(Logging.Priority.MESSAGE, "Loading questions from file");
        InputStream stream = Class.class.getResourceAsStream("/server/questionpool.dat");

        if(stream == null){
            throw new RuntimeException("Couldn't find file where questions are stored.");
        }

        BufferedInputStream inputStream = new BufferedInputStream(stream);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String line = null;
        try {
            line = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        do{
            try {
                StringTokenizer stringTokenizer = new StringTokenizer(line, ";");

                Category category = Category.valueOf(stringTokenizer.nextToken().toUpperCase());
                String questionType = stringTokenizer.nextToken();
                String questionMessage = stringTokenizer.nextToken();
                String correctAnswer = stringTokenizer.nextToken();
                BasicQuestion question;

                switch (questionType) {
                    case "MC": {
                        String wrongAnswer1 = stringTokenizer.nextToken();
                        String wrongAnswer2 = stringTokenizer.nextToken();
                        String wrongAnswer3 = stringTokenizer.nextToken();
                        question = new MultipleChoiceQuestion(category);
                        ArrayList<BasicSolution> solutions = question.getSolutions();
                        solutions.add(new TextSolution(correctAnswer, true));
                        solutions.add(new TextSolution(wrongAnswer1, false));
                        solutions.add(new TextSolution(wrongAnswer2, false));
                        solutions.add(new TextSolution(wrongAnswer3, false));

                        question.setQuestionText(questionMessage);

                        multipleChoiceQuestions.put(questionMessage, (MultipleChoiceQuestion) question);
                        break;
                    }
                    case "ES": {
                        question = new EstimationQuestion(category);
                        question.getSolutions().add(new NumberSolution(Double.parseDouble(correctAnswer), true));
                        question.setQuestionText(questionMessage);

                        estimationQuestions.put(questionMessage, (EstimationQuestion) question);
                        break;
                    }
                    default:
                        throw new RuntimeException("No correct questionType specified: " + questionType);
                }
                Collections.shuffle(question.getSolutions());
                Logging.log(Logging.Priority.MESSAGE, "Created question", question);
            }catch(Exception e){
                Logging.log(Logging.Priority.ERROR, "Corrupt line", line);
                e.printStackTrace();
            }finally{
                try {
                    line = reader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new Error("Cannot continue properly");
                }
            }
        }while(line != null && line.length() > 0);

        Logging.log(Logging.Priority.MESSAGE, "Loaded", estimationQuestions.size(), "estimation questions and", multipleChoiceQuestions.size(), "multiple choice questions!");

        try {
            stream.close();
            inputStream.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BasicQuestion getRandom() {
        Random random = new Random();
        Collection<MultipleChoiceQuestion> mcs = multipleChoiceQuestions.values();
        try {
            return new ArrayList<>(mcs).get(random.nextInt(mcs.size())).clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public HashMap<String, EstimationQuestion> getEstimationQuestions() {
        return estimationQuestions;
    }

    public HashMap<String, MultipleChoiceQuestion> getMultipleChoiceQuestions() {
        return multipleChoiceQuestions;
    }
}
