package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Stack;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    StringBuilder equation = new StringBuilder();
    int openBrackets = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button num1 = findViewById(R.id.num1);
        Button num2 = findViewById(R.id.num2);
        Button num3 = findViewById(R.id.num3);
        Button num4 = findViewById(R.id.num4);
        Button num5 = findViewById(R.id.num5);
        Button num6 = findViewById(R.id.num6);
        Button num7 = findViewById(R.id.num7);
        Button num8 = findViewById(R.id.num8);
        Button num9 = findViewById(R.id.num9);
        Button num0 = findViewById(R.id.num0);
        Button multiply = findViewById(R.id.multiply);
        Button divide = findViewById(R.id.divide);
        Button subtract = findViewById(R.id.minus);
        Button add = findViewById(R.id.plus);
        Button bracket = findViewById(R.id.bracket);
        Button backSpace = findViewById(R.id.backspace);
        Button equal = findViewById(R.id.equal);
        num1.setOnClickListener(this);
        num2.setOnClickListener(this);
        num3.setOnClickListener(this);
        num4.setOnClickListener(this);
        num5.setOnClickListener(this);
        num6.setOnClickListener(this);
        num7.setOnClickListener(this);
        num8.setOnClickListener(this);
        num9.setOnClickListener(this);
        num0.setOnClickListener(this);
        multiply.setOnClickListener(this);
        divide.setOnClickListener(this);
        subtract.setOnClickListener(this);
        add.setOnClickListener(this);
        bracket.setOnClickListener(this);
        backSpace.setOnClickListener(this);
        equal.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {

        int len = equation.length();
        char lastChar = 0;
        if(len>=1)
        {
            lastChar = equation.charAt(len - 1);
        }
        setSize(len);
        switch(view.getId())
        {
            case R.id.num1:
                equation.append("1");
                break;
            case R.id.num2:
                equation.append("2");
                break;
            case R.id.num3:
                equation.append("3");
                break;
            case R.id.num4:
                equation.append("4");
                break;
            case R.id.num5:
                equation.append("5");
                break;
            case R.id.num6:
                equation.append("6");
                break;
            case R.id.num7:
                equation.append("7");
                break;
            case R.id.num8:
                equation.append("8");
                break;
            case R.id.num9:
                equation.append("9");
                break;
            case R.id.num0:
                equation.append("0");
                break;
            case R.id.plus:
                insertPlus(len,lastChar);
                break;
            case R.id.multiply:
                insertMultiply(len,lastChar);
                break;
            case R.id.divide:
                insertDivide(len,lastChar);
                break;
            case R.id.minus:
                insertMinus(len,lastChar);
                break;
            case R.id.bracket:
                insertBracket(len,lastChar);
                break;
            case R.id.backspace:
                if(len>=1)
                {
                    equation.deleteCharAt(len - 1);
                }
                break;
            case R.id.equal:
                try
                {
                    setFinalValue(len);
                }
                catch(Exception e)
                {
                    equation = new StringBuilder("something went wrong");
                }
                break;
        }
        TextView screen = findViewById(R.id.screen);
        screen.setText(equation);
    }

    private void setSize(int len)
    {
        TextView screen = findViewById(R.id.screen);
        if(len>13)
        {
            screen.setTextSize(2,24);
            return;
        }
        screen.setTextSize(2,34);
    }

    private void setFinalValue(int len)
    {
        for(int i=0; i<openBrackets; i++)
        {
            equation.append(")");
        }
        openBrackets = 0;
        if(len == 0)
        {
            return;
        }
        if(len == 1)
        {
            if(Character.isDigit(equation.charAt(0)))
            {
                return;
            }
            equation = new StringBuilder("0");
        }
        String postFix = convertPostfix();
        double answer = findAnswer(postFix);
        Log.e("check",String.valueOf(answer));
        if(String.valueOf(answer).equals("Infinity"))
        {
            equation = new StringBuilder("divide by zero error");
            return;
        }
        else if(answer == (long)answer)
        {
            equation = new StringBuilder(String.valueOf((long) answer));
        }
        else
        {
            equation = new StringBuilder(String.valueOf(answer));
        }
    }

    private double findAnswer(String postFix)
    {
        Stack<Double> stack = new Stack<>();
        String[] str = postFix.split(" ");
        for(int i=0; i<str.length;i++)
        {
            String element = str[i];
            if(element.isEmpty())
            {
                continue;
            }
            if(Character.isDigit(element.charAt(0)) || (element.length()>1 && Character.isDigit(element.charAt(1))))
            {
                stack.push(Double.parseDouble(element));
            }
            else
            {
                double no1 = stack.pop();
                double no2;
                if(stack.empty())
                {
                    no2 = no1;
                    no1 = 0;
                }
                else
                {
                    no2 = stack.pop();
                }
                switch(element)
                {
                    case "+":
                        stack.push(no2 + no1);
                        break;
                    case "-":
                        stack.push(no2 - no1);
                        break;
                    case "*":
                        stack.push(no2 * no1);
                        break;
                    case "/":
                        stack.push(no2 / no1);
                        break;
                }
            }
        }
        return stack.pop();
    }

    private String convertPostfix()
    {
        String result = "";

        Stack<Character> stack = new Stack<>();
        if(!checkHasDigit())
        {
            result += "0";
        }
        for (int i = 0; i < equation.length(); i++)
        {
            char ch = equation.charAt(i);
            if(ch == '-' && i== 0)
            {
                result += ch;
            }
            else if(ch == '-' && i>=1 && (equation.charAt(i-1) == '/' || equation.charAt(i-1) == '*' || equation.charAt(i-1) == '('))
            {
                result += ch;
            }
            else if (Character.isDigit(ch) || ch == '.')
            {
                result += ch;
            }
            else if (ch == '(')
            {
                result += " ";
                stack.push(ch);
            }
            else if (ch == ')')
            {
                result += " ";
                while (!stack.isEmpty() && stack.peek() != '(')
                {
                    result += stack.pop() + " ";
                }
                stack.pop();
            }
            else
            {
                result += " ";
                while (!stack.isEmpty() && (priority(ch) <= priority(stack.peek())))
                {
                    result += stack.pop() + " ";
                }
                stack.push(ch);
            }
        }
        while (!stack.isEmpty())
        {
            result += " " + stack.pop() + " ";
        }
        return result;
    }

    private boolean checkHasDigit()
    {
        for(int i=0;i<equation.length();i++)
        {
            if(Character.isDigit(equation.charAt(i)))
            {
                return true;
            }
        }
        return false;
    }

    private int priority(Character ch)
    {
        switch(ch)
        {
            case '-':
            case '+':
                return 1;
            case '*':
            case '/':
                return 2;
        }
        return -1;
    }

    private void insertPlus(int len,char lastChar)
    {
        if(len>=1)
        {
            if(lastChar == '-')
            {
                return;
            }
            else if ((lastChar < '0' || lastChar > '9') && lastChar != '(' && lastChar != ')')
            {
                equation.deleteCharAt(len - 1);
            }
            else if (lastChar != '(')
            {
                equation.append("+");
            }
        }
    }

    private void insertMultiply(int len,char lastChar)
    {
        if(len>=1)
        {
            if(lastChar == '-' && len>=2)
            {
                if(equation.charAt(len-2) == '(' || equation.charAt(len-2) == '*' || equation.charAt(len-2) == '/')
                {
                    return;
                }
            }
            else if ((lastChar < '0' || lastChar > '9') && lastChar != '(' && lastChar != ')')
            {
                equation.deleteCharAt(len - 1);
                equation.append("*");
            }
            else if (lastChar != '(')
            {
                equation.append("*");
            }
        }
    }

    private void insertDivide(int len,char lastChar)
    {
        if(len>=1)
        {
            if(lastChar == '-' && len>2)
            {
                if(equation.charAt(len-2) == '(' || equation.charAt(len-2) == '*' || equation.charAt(len-2) == '/')
                {
                    return;
                }
            }
            else if ((lastChar < '0' || lastChar > '9') && lastChar != '(' && lastChar != ')')
            {
                equation.deleteCharAt(len - 1);
            }
            else if (lastChar != '(')
            {
                equation.append("/");
            }
        }
    }

    private void insertMinus(int len,char lastChar)
    {
        if(len>=2)
        {
            if((equation.charAt(len-2) >= '0' && equation.charAt(len-2) <= '9') || equation.charAt(len-2) == ')')
            {
                if(lastChar == '-')
                {
                    equation.deleteCharAt(len - 1);
                    equation.append("+");
                    return;
                }
            }
            else
            {
                if(lastChar == '-')
                {
                    equation.deleteCharAt(len - 1);
                    return;
                }
            }
            if(lastChar == '+')
            {
                equation.deleteCharAt(len - 1);
            }
            equation.append("-");
        }
        else if(len>=1)
        {
            if(lastChar == '-')
            {
                equation.deleteCharAt(len - 1);
                return;
            }
            if(lastChar == '+')
            {
                equation.deleteCharAt(len - 1);
            }
            equation.append("-");
        }
        else
        {
            equation.append("-");
        }
    }

    private void insertBracket(int len,char lastChar)
    {
        if(len == 0)
        {
            equation.append("(");
            openBrackets++;
            return;
        }
        if((lastChar >= '0' && lastChar <= '9') || lastChar == ')')
        {
            if(openBrackets!=0)
            {
                equation.append(")");
                openBrackets--;
            }
            else
            {
                equation.append("*");
                equation.append("(");
                openBrackets++;
            }
        }
        else
        {
            equation.append("(");
            openBrackets++;
        }
    }
}