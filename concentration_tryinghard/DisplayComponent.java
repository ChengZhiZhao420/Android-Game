package com.example.concentration_tryinghard;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

//use for storing component that about to display
public class DisplayComponent {
        //variables for saved data
        private static final String JSON_BUTTON_VISIBILITY = "Button_Visibility";
        private static final String JSON_TEXT_VISIBILITY = "Text_Visibility";
        private static final String JSON_ANSWER = "Answer";
        private static final String JSON_CHECK_STACK = "CheckStack";
        private static final String JSON_WRONG_STACK = "WrongStack";
        private static final String JSON_CORRECT_STACK = "CorrectStack";

        private Button button;
        private TextView textView;
        private boolean buttonVisibility;
        private boolean textVisibility;
        private boolean checking;
        private boolean wrong;
        private boolean correct;
        private String answer;

        public DisplayComponent(Button b, TextView t){
            this.button = b;
            this.textView = t;
            this.buttonVisibility = false;
            this.textVisibility = false;
            this.checking = false;
            this.wrong = false;
            this.correct = false;
            this.answer = null;
        }

        //pass in a JSONObject to set the saved data
        public DisplayComponent(JSONObject json) throws JSONException {
            this.button = null;
            this.textView = null;
            this.buttonVisibility = json.getBoolean(JSON_BUTTON_VISIBILITY);
            this.textVisibility = json.getBoolean(JSON_TEXT_VISIBILITY);
            this.checking = json.getBoolean(JSON_CHECK_STACK);
            this.wrong = json.getBoolean(JSON_WRONG_STACK);
            this.correct = json.getBoolean(JSON_CORRECT_STACK);
            this.answer = json.getString(JSON_ANSWER);
        }

        public Button getButton(){//getter for button
            return button;
        }

        public void setButton(Button b){
            button = b;
        }

        public TextView getTextView(){
            return textView;
        }

        public void setTextView(TextView t){
            textView = t;
        }

        public boolean getButtonVisibility(){
            return buttonVisibility;
        }

        public void setButtonVisibility(boolean state){
            buttonVisibility = state;
            if(state){
                button.setVisibility(View.VISIBLE);
            }
            else{
                button.setVisibility(View.GONE);
            }
        }

        public boolean getTextVisibility(){
            return textVisibility;
        }

        public void setTextVisibility(boolean state){
            textVisibility = state;
            if(state){
                textView.setVisibility(View.VISIBLE);
            }
            else{
                textView.setVisibility(View.GONE);
            }
        }

        public void setChecking(boolean state){
            checking = state;
        }

        public boolean isChecking(){
            return checking;
        }

        public void setWrong(boolean state){
            wrong = state;
        }

        public boolean isWrong(){
            return wrong;
        }

        public void setCorrect(boolean state){
            correct = state;
        }

        public boolean isCorrect(){
            return correct;
        }

        public void setAnswer(String answer){
            this.answer = answer;
            textView.setText(this.answer);

        }

        public String getAnswer(){
            return answer;
        }

        //store the data into json file
        public JSONObject toJSON() throws JSONException {
            JSONObject json = new JSONObject();
            json.put(JSON_BUTTON_VISIBILITY, buttonVisibility);
            json.put(JSON_TEXT_VISIBILITY, textVisibility);
            json.put(JSON_ANSWER, answer);
            json.put(JSON_CHECK_STACK, checking);
            json.put(JSON_WRONG_STACK, wrong);
            json.put(JSON_CORRECT_STACK, correct);

            return json;
        }
}
