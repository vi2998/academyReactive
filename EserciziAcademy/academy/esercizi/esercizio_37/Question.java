package academy.esercizi.esercizio_37;

import java.util.Scanner;

public abstract class Question <T,A>{
    T text;
    A answer;

    public Question(T text, A answer) {
        this.text = text;
        this.answer = answer;
    }

    public abstract boolean checkAnswer(A answer);

    public abstract boolean rispondi(Scanner scanner);

    public void display(){
        System.out.println(text);
    }




}
