package joonas.tuni.fi.tictactoe;

import android.graphics.Paint;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


public class TicTacToe extends MyBaseActivity {
    int DISPLAY_WIDHT;
    int DISPLAY_HEIGHT;
    char nextChar;
    TextView nextTurn;
    Button[][] table;
    boolean gameIsOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Debug.loadDebug(this);
        Debug.print(TAG,"onCreate started", 1);
        table = new Button[3][3];
        gameIsOn = true;

        super.onCreate(savedInstanceState);
        DISPLAY_WIDHT = getResources().getDisplayMetrics().widthPixels;
        DISPLAY_HEIGHT = getResources().getDisplayMetrics().heightPixels;
        nextChar = 'X';

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        TextView title = new TextView(this);
        title.setText("Tic Tac Toe");
        title.setGravity(Gravity.CENTER);
        layout.addView(title);

        nextTurn = new TextView(this);
        nextTurn.setText("Next move: " + nextChar);
        nextTurn.setTextSize(30);
        nextTurn.setGravity(Gravity.CENTER);
        layout.addView(nextTurn);

        TableLayout tlayout = new TableLayout(this);
        layout.addView(tlayout);
        tlayout.setStretchAllColumns(true);


        for(int i = 0; i < 3; i++){
            TableRow row = new TableRow(this);
            row.setMinimumHeight(DISPLAY_WIDHT / 3);
            Debug.print(TAG, "Creating row " + i, 2);
            for(int j = 0; j < 3; j++){
                Debug.print(TAG,"Creating column" + j + " in row " + i, 3);
                Button butt = new Button(this);
                butt.setText("");
                row.addView(butt);
                butt.setHeight(DISPLAY_WIDHT / 3);
                butt.setOnClickListener((e) -> handleButtonPress(butt));
                table[i][j] = butt;
            }
            tlayout.addView(row);
        }

        Button reset = new Button(this);
        reset.setText("Reset");
        reset.setOnClickListener((e) -> resetTable());
        layout.addView(reset);


        setContentView(layout);

        Debug.print(TAG, "onCreate finished", 1);
    }

    private void handleButtonPress(Button butt) {
        Debug.print(TAG, "Button pressed", 1);
        if(butt.getText().equals("") && gameIsOn){
            butt.setText(nextChar + "");
            checkWinCondition(nextChar);
            switchTurn();
        } else if(!(gameIsOn)) {
            Debug.print(TAG, "Game already ended", 2);
        } else {
            Debug.print(TAG, "Place already occupied", 2);
        }
    }

    private void switchTurn(){
        if(nextChar == 'X'){
            nextChar = 'O';
        } else {
            nextChar = 'X';
        }
        nextTurn.setText("Next move: " + nextChar);
        Debug.print(TAG, "Next character changed to " + nextChar, 1);
    }

    private void resetTable(){
        Debug.print(TAG, "Reset button pressed", 1);
        for(int i = 0; i < table.length; i++){
            for(int j = 0; j < table.length; j++){
                table[i][j].setText("");
                nextChar = 'X';
                nextTurn.setText("Next move: " + nextChar);
                Debug.print(TAG, "Resetting [" + i + "][" + j + "] in table", 3);
            }
        }
        gameIsOn = true;
        Debug.print(TAG, "Table reset", 1);
    }

    private void checkWinCondition(char a){
        Debug.print(TAG, "Checking win condition with char " + a, 2);
        Toast wiener = Toast.makeText(this, "Winner is " + nextChar, Toast.LENGTH_LONG);

        if(checkHorizontal(a) || checkVertical(a) || checkAscending(a) ||checkDescending(a)){
            gameIsOn = false;
            wiener.show();
            Debug.print(TAG, "Game won by" + nextChar, 2);
        }

    }

    private boolean checkHorizontal(char a){
        for(int i = 0; i < table.length; i++){
            int amount = 0;
            for(int j = 0; j < table[0].length; j++){
                if(table[i][j].getText().equals(a + "")){
                    amount++;
                }
            }
            if(amount == 3){
                return true;
            }
            Debug.print(TAG, "Row: " + i + " Char: " + a + " Amount: " + amount, 2);
        }
        return false;
    }

    private boolean checkVertical(char a){
        for(int i = 0; i < table.length; i++){
            int amount = 0;
            for(int j = 0; j < table[0].length; j++){
                if(table[j][i].getText().equals(a + "")){
                    amount++;
                }
            }
            if(amount == 3){
                return true;

            }
            Debug.print(TAG, "Column: " + i + " Char: " + a + " Amount: " + amount, 2);
        }
        return false;
    }

    private boolean checkDescending(char a){
        int amount = 0;
        for(int i = 0; i < table.length; i++){
            if(table[i][i].getText().equals(a + "")){
                amount++;
            }
        }
        Debug.print(TAG, "Ascending row, char" + a + " Amount: " + amount, 2);
        if(amount == 3){
            return true;
        }
        return false;
    }

    private boolean checkAscending(char a){
        int amount = 0;
        for(int i = 0; i < table.length; i++){
            if(table[2 - i][i].getText().equals(a + "")){
                amount++;
            }
        }
        Debug.print(TAG, "Descending row, char" + a + " Amount: " + amount, 2);
        if(amount == 3){
            return true;
        }
        return false;
    }
}
