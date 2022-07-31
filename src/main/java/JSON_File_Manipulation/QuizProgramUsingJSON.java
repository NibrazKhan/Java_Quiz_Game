
package JSON_File_Manipulation;

import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
//import org.json.simple.parser.*;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class QuizProgramUsingJSON {
    public static void main(String[] args) throws IOException, ParseException {
        System.out.println("Select One of the two options from below:\n" +
                "1. Add Quiz\n" +
                "2. Start Quiz");
        Scanner sc=new Scanner(System.in);
        int option=sc.nextInt();
        if(option==1){
            addingQuestion();
        }
        else{
            startQuiz();
        }
    }
    public static void addingQuestion() throws IOException, ParseException {
        char ch = 'y';
        String filename = "./src/main/resources/QuizBank.json";
        Scanner sc=new Scanner(System.in);
        do {
//            JSONArray jsonArray=new JSONArray();
            JSONParser jsonParser = new JSONParser();
            Object obj = jsonParser.parse(new FileReader(filename));
            JSONObject jsonObject = new JSONObject();
            System.out.println("Please add a question here:");
            String question = sc.nextLine();
            jsonObject.put("Question", question);
            System.out.println("Input Options:");
            for (int i = 97; i < 101; i++) {
                String questionOption = "Option " + (char) (i) + ":";
                System.out.println(questionOption);
                String inputQuestionOption = sc.nextLine();
                jsonObject.put(questionOption, inputQuestionOption);
            }
            System.out.println("Please input the correct answer: ");
            String answer = sc.nextLine();
            jsonObject.put("answer", answer);
            JSONArray jsonArray = (JSONArray) obj;
            jsonArray.add(jsonObject);
            FileWriter file = new FileWriter(filename);
            file.write(jsonArray.toJSONString());
            file.flush();
            file.close();
            System.out.println("Quiz saved at the database. Do you want to add more? (y/n)");
            ch = sc.nextLine().charAt(0);
        }
        while (ch != 'n');
        sc.close();
    }
    public static void startQuiz() throws IOException, ParseException {
        System.out.println("You will be asked 5 questions, each questions has 1 marks");
        int correct=0;
        Scanner sc=new Scanner(System.in);
        Random random = new Random();

        for(int i=1;i<=5;i++){
            int pos= random.nextInt(20);
//            int pos=0;
            System.out.println("Question "+i+":");
            String filename="./src/main/resources/QuizBank.json";
            JSONParser jsonParser=new JSONParser();
            Object obj= jsonParser.parse(new FileReader(filename));
            JSONArray jsonArray=(JSONArray) obj;
//            System.out.println(jsonArray);
            JSONObject jsonObject=(JSONObject) jsonArray.get(pos);
            System.out.println((String)jsonObject.get("Question"));
            for (int j = 97; j < 101; j++) {
                String questionOption = "Option " + (char) (j) + ":";
                System.out.println(questionOption+(String)jsonObject.get(questionOption));
            }
            System.out.println("Input your answer");
            String answer=sc.nextLine();
            String actualAnswer= (String)jsonObject.get("answer");
//            char actualAnswer=actualAnswerString.charAt(0);

            if(answer.equals(actualAnswer)){
                System.out.println("Correct!");
                correct+=1;
            }
            else
                System.out.println("Not correct");
        }
        System.out.printf("Result:You got: %d out of 5",correct);
    }
}
