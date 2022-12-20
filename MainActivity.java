package com.example.test1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    String xo = "";
    char[][] mat = new char[3][3];
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        clickEvents();
    }

    private void clickEvents()
    {
        Button cross = (Button) findViewById(R.id.cross);
        Button zero = (Button) findViewById(R.id.zero);
        Button retry = findViewById(R.id.yes);
        Button exit = findViewById(R.id.no);
        cross.setOnClickListener(this);
        zero.setOnClickListener(this);
        TextView top1 = (TextView) findViewById(R.id.top1);
        TextView top2 = (TextView) findViewById(R.id.top2);
        TextView top3 = (TextView) findViewById(R.id.top3);
        TextView mid1 = (TextView) findViewById(R.id.mid1);
        TextView mid2 = (TextView) findViewById(R.id.mid2);
        TextView mid3 = (TextView) findViewById(R.id.mid3);
        TextView bottom1 = (TextView) findViewById(R.id.bottom1);
        TextView bottom2 = (TextView) findViewById(R.id.bottom2);
        TextView bottom3 = (TextView) findViewById(R.id.bottom3);
        top1.setOnClickListener(this);
        top2.setOnClickListener(this);
        top3.setOnClickListener(this);
        mid1.setOnClickListener(this);
        mid2.setOnClickListener(this);
        mid3.setOnClickListener(this);
        bottom1.setOnClickListener(this);
        bottom2.setOnClickListener(this);
        bottom3.setOnClickListener(this);
        retry.setOnClickListener(this);
        exit.setOnClickListener(this);
    }


    @Override
    public void onClick(View view)
    {
        TextView choice = findViewById(R.id.choose);
        switch(view.getId())
        {
            case R.id.cross:
                xo = "X";
                playStart(view);
                return;
            case R.id.zero:
                xo = "O";
                playStart(view);
                opponentMove('X');
                return;
            case R.id.yes:
                Log.e("yes","testing");
                reset();
                return;
            case R.id.no:
                this.finishAffinity();
                return;
        }
        int val = playerMove(view);
        Log.e("play",String.valueOf(val));
        if(checkGameOver(val,xo.charAt(0)))
        {
            return;
        }
        char opp = '0';
        if(xo == "X")
        {
            opp = 'O';
        }
        else
        {
            opp = 'X';
        }
        val = opponentMove(opp);
        if(checkGameOver(val,opp))
        {
            return;
        }
    }


    private boolean checkGameOver(int val,char opp)
    {
        TextView choice = findViewById(R.id.choose);
        if(val == -1)
        {
            choice.setText("Illegal move");
            choice.setTextColor(Color.parseColor("#FFCA0707"));
            return false;
        }
        else if(val == -2)
        {
            choice.setText("Box is occupied");
            choice.setTextColor(Color.parseColor("#FFCA0707"));
            return false;
        }
        if(checkResult(val/3,val%3,opp))
        {
            if(opp == xo.charAt(0))
            {
                choice.setText("You won");
                choice.setTextColor(Color.parseColor("#2E6C06"));
                retry();
            }
            else
            {
                choice.setText("You lost");
                choice.setTextColor(Color.parseColor("#B32121"));
                retry();
            }
            return true;
        }
        if(checkDraw())
        {
            choice.setText("This is a draw");
            choice.setTextColor(Color.parseColor("#2E6C06"));
            retry();
            return true;
        }
        return false;
    }

    private int opponentMove(char opp)
    {
        TextView cell = null;
        List<Integer> list = getList();
        int no = -1;
        for(int i: list)
        {
            Log.e("list",String.valueOf(i));
        }
        while(!list.contains(no))
        {
            no = findPos(opp);
            if (no == -1)
            {
                no = findPos(xo.charAt(0));
            }
            if (no == -1)
            {
                no = (int) (Math.random() * 9);
                Log.e("exe", "String.valueOf(no)");
            }
        }
        switch (no)
        {
            case 0:
                cell = findViewById(R.id.top1);
                break;
            case 1:
                cell = findViewById(R.id.top2);
                break;
            case 2:
                cell = findViewById(R.id.top3);
                break;
            case 3:
                cell = findViewById(R.id.mid1);
                break;
            case 4:
                cell = findViewById(R.id.mid2);
                break;
            case 5:
                cell = findViewById(R.id.mid3);
                break;
            case 6:
                cell = findViewById(R.id.bottom1);
                break;
            case 7:
                cell = findViewById(R.id.bottom2);
                break;
            case 8:
                cell = findViewById(R.id.bottom3);
                break;
        }
        displayInsert(cell,opp);
        mat[no/3][no%3] = opp;
        return no;
    }

    private List<Integer> getList()
    {
        List<Integer> list = new ArrayList<>();
        for(int i=0; i<9; i++)
        {
            int a = i/3;
            int b = i%3;
            if(mat[a][b] == 'X' || mat[a][b] == 'O')
            {
                Log.e("res",String.valueOf(a)+String.valueOf(b));
                continue;
            }
            list.add(i);
        }
        return list;
    }

    private int findPos(char opp)
    {
        int no = -1;
        if(mat[0][0] == '\u0000' && ((mat[0][2] == mat[0][1] && mat[0][2] == opp) || (mat[2][0] == mat[1][0] && mat[2][0] == opp) || (mat[2][2] == mat[1][1] && mat[2][2] == opp)))
        {
            mat[0][0] = opp;
            no = 0;
            Log.e("exe",String.valueOf(no));
        }
        else if(mat[0][1] == '\u0000' && ((mat[0][2] == mat[0][0] && mat[0][2] == opp) || (mat[2][1] == mat[1][1] && mat[2][1] == opp)))
        {
            mat[0][1] = opp;
            no = 1;
            Log.e("exe",String.valueOf(no));
        }
        else if(mat[0][2] == '\u0000' && ((mat[0][1] == mat[0][0] && mat[0][1] == opp) || (mat[1][2] == mat[2][2] && mat[2][2] == opp) || (mat[2][0] == mat[1][1] && mat[2][0] == opp)))
        {
            mat[0][2] = opp;
            no = 2;
            Log.e("exe",String.valueOf(no));
        }
        else if(mat[1][0] == '\u0000' && ((mat[1][1] == mat[1][2] && mat[1][1] == opp) || (mat[0][0] == mat[2][0] && mat[2][0] == opp)))
        {
            mat[1][0] = opp;
            no = 3;
            Log.e("exe",String.valueOf(no));
        }
        else if(mat[1][1] == '\u0000' && ((mat[0][1] == mat[2][1] && mat[0][1] == opp) || (mat[1][2] == mat[1][0] && mat[1][2] == opp) || (mat[2][0] == mat[0][2] && mat[2][0] == opp) || (mat[0][0] == mat[2][2] && mat[2][2] == opp)))
        {
            mat[1][1] = opp;
            no = 4;
            Log.e("exe",String.valueOf(no));
        }
        else if(mat[1][2] == '\u0000' && ((mat[1][1] == mat[1][0] && mat[1][1] == opp) || (mat[0][2] == mat[2][2] && mat[2][2] == opp)))
        {
            mat[1][2] = opp;
            no = 5;
            Log.e("exe",String.valueOf(no));
        }
        else if(mat[2][0] == '\u0000' && ((mat[0][0] == mat[1][0] && mat[1][0] == opp) || (mat[2][1] == mat[2][2] && mat[2][2] == opp) || (mat[1][1] == mat[0][2] && mat[0][2] == opp)))
        {
            mat[2][0] = opp;
            no = 6;
            Log.e("exe",String.valueOf(no));
        }
        else if(mat[2][1] == '\u0000' && ((mat[1][1] == mat[0][1] && mat[1][1] == opp) || (mat[2][0] == mat[2][2] && mat[2][2] == opp)))
        {
            mat[2][1] = opp;
            no = 7;
            Log.e("exe",String.valueOf(no));
        }
        else if(mat[2][2] == '\u0000' && ((mat[2][1] == mat[2][0] && mat[2][1] == opp) || (mat[0][2] == mat[1][2] && mat[0][2] == opp) || (mat[0][0] == mat[1][1] && mat[0][0] == opp)))
        {
            mat[2][2] = opp;
            no = 8;
            Log.e("exe",String.valueOf(no));
        }
        return no;
    }

    private void retry()
    {
        findViewById(R.id.grid).setVisibility(View.GONE);
        findViewById(R.id.yes).setVisibility(View.VISIBLE);
        findViewById(R.id.no).setVisibility(View.VISIBLE);
        findViewById(R.id.retry).setVisibility(View.VISIBLE);
    }

    private boolean checkDraw()
    {
        List<Integer> list = getList();
        if(list.isEmpty())
        {
            return true;
        }
        return false;
    }

    private void displayInsert(TextView cell, char opp)
    {
        cell.setText(String.valueOf(opp));
        if(opp == 'O')
        {
            cell.setTextColor(Color.parseColor("#FFFFFF"));
        }
    }

    private int playerMove(View view)
    {
        TextView box = findViewById(view.getId());
        if(box.getText().equals("X") || box.getText().equals("O"))
        {
            Log.e("exe","show");
            return -2;
        }
        else
        {
            TextView choice = findViewById(R.id.choose);
            choice.setText("Let's Play");
        }
        box.setText(xo);
        if(xo.equals("O"))
        {
            box.setTextColor(Color.parseColor("#FFFFFF"));
        }
        return matrixInsert(view.getId());
    }

    private int matrixInsert(int no)
    {
        switch(no)
        {
            case R.id.bottom1:
                mat[2][0] = xo.charAt(0);
                return 6;
            case R.id.bottom2:
                mat[2][1] = xo.charAt(0);
                return 7;
            case R.id.bottom3:
                mat[2][2] = xo.charAt(0);
                return 8;
            case R.id.top1:
                mat[0][0] = xo.charAt(0);
                return 0;
            case R.id.top2:
                mat[0][1] = xo.charAt(0);
                return 1;
            case R.id.top3:
                mat[0][2] = xo.charAt(0);
                return 2;
            case R.id.mid1:
                mat[1][0] = xo.charAt(0);
                return 3;
            case R.id.mid2:
                mat[1][1] = xo.charAt(0);
                return 4;
            case R.id.mid3:
                mat[1][2] = xo.charAt(0);
                return 5;
        }
        Log.e("insert",String.valueOf(no));
        return -1;
    }

    private void playStart(View view)
    {
        findViewById(R.id.grid).setVisibility(View.VISIBLE);
        TextView choice = findViewById(R.id.choose);
        choice.setText("Let's Play");
        findViewById(R.id.zero).setVisibility(View.GONE);
        findViewById(R.id.cross).setVisibility(View.GONE);
    }

    private void reset()
    {
        xo = "";
        mat = new char[3][3];
        TextView choice = findViewById(R.id.choose);
        choice.setText("Choose");
        choice.setTextColor(Color.parseColor("#000000"));
        findViewById(R.id.yes).setVisibility(View.GONE);
        findViewById(R.id.no).setVisibility(View.GONE);
        findViewById(R.id.retry).setVisibility(View.GONE);
        findViewById(R.id.cross).setVisibility(View.VISIBLE);
        findViewById(R.id.zero).setVisibility(View.VISIBLE);
        TextView top1 = findViewById(R.id.top1);
        TextView top2 = findViewById(R.id.top2);
        TextView top3 = findViewById(R.id.top3);
        TextView mid1 = findViewById(R.id.mid1);
        TextView mid2 = findViewById(R.id.mid2);
        TextView mid3 = findViewById(R.id.mid3);
        TextView bottom1 = findViewById(R.id.bottom1);
        TextView bottom2 = findViewById(R.id.bottom2);
        TextView bottom3 = findViewById(R.id.bottom3);
        top1.setText("");
        top1.setTextColor(Color.parseColor("#000000"));
        top2.setText("");
        top2.setTextColor(Color.parseColor("#000000"));
        top3.setText("");
        top3.setTextColor(Color.parseColor("#000000"));
        bottom1.setText("");
        bottom1.setTextColor(Color.parseColor("#000000"));
        bottom2.setText("");
        bottom2.setTextColor(Color.parseColor("#000000"));
        bottom3.setText("");
        bottom3.setTextColor(Color.parseColor("#000000"));
        mid1.setText("");
        mid1.setTextColor(Color.parseColor("#000000"));
        mid2.setText("");
        mid2.setTextColor(Color.parseColor("#000000"));
        mid3.setText("");
        mid3.setTextColor(Color.parseColor("#000000"));
        return;
    }

    private boolean checkResult(int a,int b, char opp)
    {
        Log.e("resche",String.valueOf(a)+String.valueOf(b));
        for(int i=0; i<3; i++)
        {
            if(mat[a][i] != opp)
            {
                break;
            }
            if(i == 2)
            {
                return true;
            }
        }
        for(int i=0;i<3; i++)
        {
            if(mat[i][b] != opp)
            {
                break;
            }
            if(i == 2)
            {
                return true;
            }
        }
        if(a == b)
        {
            for(int i=0; i<3; i++)
            {
                if(mat[i][i] != opp)
                {
                    break;
                }
                if(i == 2)
                {
                    return true;
                }
            }
        }
        if(a + b == 2)
        {
            for(int i=0; i<3; i++)
            {
                if(mat[2-i][i] != opp)
                {
                    break;
                }
                if(i == 2)
                {
                    return true;
                }
            }
        }
        return false;
    }
}