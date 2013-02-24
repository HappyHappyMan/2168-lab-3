package Homework3;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.Stack;
import java.util.StringTokenizer;
import java.text.DecimalFormatSymbols;
import java.util.Comparator;
import java.util.Arrays;

/**
 * Converts infix expressions to postfix expressions, and evaluates them.
 */

public class InfixToPostfix 
{
  ArrayBlockingQueue<String> doubleQueue; //check whether this needs char or string
  Stack<String> tokenStack;
  String expression;

  public InfixToPostfix(String expression)
  {
    // OperatorComparator comparator = new OperatorComparator();
    doubleQueue = new ArrayBlockingQueue<String>(expression.length());
    tokenStack = new Stack<String>();
    this.expression = expression;
  }

  public void convertExprToPostfix()
  {
    String eval;
    String[] operators = {"+","-","*","/"};
    StringTokenizer tokenizer = new StringTokenizer(expression, " +-*/()", true);
    while (tokenizer.hasMoreTokens())
    {
      // TODO
      eval = tokenizer.nextToken();
      System.out.println("Eval is: " + eval);                                                 // get the next token
      if (isStringNumeric(eval))                                                    // if it is a constant, enqueue
      {
        System.out.println("eval is a number.");
        doubleQueue.add(eval);  
      }
      else if (eval.equals("("))                                                    // if token is a (, push into stack
      {
        System.out.println("eval is a (");
        tokenStack.push(eval);
      }
      else if (Arrays.asList(operators).contains(eval))                             // if the token is an operator
      {
        System.out.println("eval is an operator");
        if (tokenStack.isEmpty() || compareOperators(eval, tokenStack.peek()) < 0)  // if stack is empty, or the if the priority of the stack has a lower priority than that of the token
        {
          System.out.println("took option 1 on line 51");
          tokenStack.push(eval);
        }
        else
        {
          System.out.println("took option 2 on line 56");
          while (!tokenStack.isEmpty() && !tokenStack.peek().equals("("))
          {
            System.out.println("entered while loop on line 59");
            doubleQueue.add(tokenStack.pop());
          }
          tokenStack.push(eval);
        }
      }

      if (!tokenStack.isEmpty())
      {
        System.out.println("peek of tokenStack is " + tokenStack.peek());
        if (tokenStack.peek().equals(")"))
        {
          System.out.println("peek() is a )");
          while (!tokenStack.isEmpty() && !tokenStack.peek().equals("("))
          {
            doubleQueue.add(tokenStack.pop());
          }
          System.out.println("tokenStack is popping");
          tokenStack.pop(); //this may throw an exception...
        }
      }
    System.out.println("doublequeue is " + doubleQueue);
    System.out.println("tokenstack is " + tokenStack);
    }

    while (!tokenStack.isEmpty())
    {
      doubleQueue.add(tokenStack.pop());
      System.out.println("doublequeue is " + doubleQueue);
      System.out.println("tokenstack is " + tokenStack);
    }

    System.out.println("doublequeue is " + doubleQueue);
    System.out.println("tokenstack is " + tokenStack);
  }

  // TODO i'll get to this
  private double evalPostFix()
  {
    Stack<Double> numbers = new Stack<Double>();
    String[] operators = {"*","/","+","-",")","("};
    // TODO just to shut up the linter
    return 0.0;

  }

  private boolean isStringNumeric(String str)
  { 
    DecimalFormatSymbols currentLocaleSymbols = DecimalFormatSymbols.getInstance();
    char localeMinusSign = currentLocaleSymbols.getMinusSign();

    if ( !Character.isDigit( str.charAt( 0 ) ) && str.charAt( 0 ) != localeMinusSign ) return false;

    boolean isDecimalSeparatorFound = false;
    char localeDecimalSeparator = currentLocaleSymbols.getDecimalSeparator();

    for ( char c : str.substring( 1 ).toCharArray() )
    {
        if ( !Character.isDigit( c ) )
        {
            if ( c == localeDecimalSeparator && !isDecimalSeparatorFound )
            {
                isDecimalSeparatorFound = true;
                continue;
            }
            return false;
        }
    }
    return true;
  }

  /**
   * compares operators (as defined in operators in convertExprToPostfix() ) to determine
   * priority.
   * @param  op1 first operator
   * @param  op2 second operator
   * @return     returns -1 if op1 has lesser priority, 1 if op1 has greater priority, or 
   * 0 if op1 has equal priority. Returns -2 otherwise.
   */
  private int compareOperators(String op1, String op2)
  {
    if ( (op1.equals("+") || op1.equals("-")) && ( (op2.equals("*") || op2.equals("/") ) ) )
    {
      System.out.println(-1);
      return -1;
    }
    else if ( (op2.equals("+") || op2.equals("-")) && ( (op1.equals("*") || op1.equals("/") ) ) )
    {
      System.out.println(1);
      return 1;
    }
    else if ( (op1.equals("/") || op1.equals("*")) && ( (op2.equals("*") || op2.equals("/") ) ) )
    {
      System.out.println(0);
      return 0;
    }
    else if ( (op1.equals("+") || op1.equals("-")) && ( (op2.equals("+") || op2.equals("-") ) ) )
    {
      System.out.println(0);
      return 0;
    }
    else
    {
      System.out.println(-2);
      return -2;
    }
  }
  public static void main(String[] args) 
  {
    String equation = "(1+3)*7";

    InfixToPostfix itp = new InfixToPostfix(equation);
    itp.convertExprToPostfix();
  }
}
