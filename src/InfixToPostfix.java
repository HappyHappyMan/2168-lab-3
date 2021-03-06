import java.util.concurrent.ArrayBlockingQueue;
import java.util.Stack;
import java.util.StringTokenizer;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.ArrayDeque;
import java.util.Scanner;

/**
 * Converts infix expressions to postfix expressions, and evaluates them.
 */

public class InfixToPostfix 
{
  ArrayBlockingQueue<String> doubleQueue; 
  Stack<String> tokenStack;
  String expression;
  String[] operators = {"+","-","*","/"};

  public InfixToPostfix(String expression)
  {
    doubleQueue = new ArrayBlockingQueue<String>(expression.length());
    tokenStack = new Stack<String>();
    this.expression = expression;
  }

  public void convertExprToPostfix()
  {
    String eval;
    StringTokenizer tokenizer = new StringTokenizer(expression, " +-*/()", true);
    ArrayDeque<String> tempQueue = new ArrayDeque<String>();
    while (tokenizer.hasMoreTokens())
    {
      tempQueue.addLast(tokenizer.nextToken());
    }
    while (!tempQueue.isEmpty())
    {
      eval = tempQueue.removeFirst();
      if (eval.equals("("))                                                         // if token is a (
      {
        tokenStack.push(eval);
      }
      else if (Arrays.asList(operators).contains(eval))                             // if the token is an operator
      {
        if (tokenStack.isEmpty() || compareOperators(eval, tokenStack.peek()) > 0)  // if stack is empty, or the if the priority of the stack has a lower priority than that of the token
        {
          tokenStack.push(eval);
        }
        else
        {
          while (!tokenStack.isEmpty() && !tokenStack.peek().equals("("))
          {
            doubleQueue.add(tokenStack.pop());
          }
          tokenStack.push(eval);
        }
      }
      else if (isStringNumeric(eval))                                               // if it is a constant, enqueue
      {
        doubleQueue.add(eval);  
      }

      if (!tokenStack.isEmpty() && tempQueue.peek() != null)
      {
        if (tempQueue.peek().equals(")"))
        {
          while (!tokenStack.isEmpty() && !tokenStack.peek().equals("("))
          {
            doubleQueue.add(tokenStack.pop());
          }
          tokenStack.pop(); 
        }
      }
    }

    while (!tokenStack.isEmpty())
    {
      doubleQueue.add(tokenStack.pop());
    }
  }

  // TODO i'll get to this
  private double evalPostFix()
  {
    ArrayDeque<Double> numbers = new ArrayDeque<Double>();
    ArrayDeque<String> input = new ArrayDeque<String>();
    double num1, num2;

    doubleQueue.drainTo(input);

    while (!input.isEmpty())
    {
      String eval = input.removeFirst();
      if (Arrays.asList(operators).contains(eval))
      {
        num2 = numbers.pop();
        num1 = numbers.pop();

        if (eval.equals("+"))
        {
          numbers.push(num1 + num2);
        }
        else if (eval.equals("-"))
        {
          numbers.push(num1 - num2);
        }
        else if (eval.equals("*"))
        {
          numbers.push(num1 * num2);
        }
        else if (eval.equals("/"))
        {
          numbers.push(num1 / num2);
        }
      }
      else if (isStringNumeric(eval))
      {
        numbers.push(Double.parseDouble(eval));
      }
    }

    return numbers.pop();
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
      return -1;
    }
    else if ( (op2.equals("+") || op2.equals("-")) && ( (op1.equals("*") || op1.equals("/") ) ) )
    {
      return 1;
    }
    else
    {
      return 0;
    }
  }

  public static void main(String[] args) 
  {
    Scanner in = new Scanner(System.in);
    System.out.println("Please enter your mathematical expression here:");
    String equation = in.nextLine();


    InfixToPostfix itp = new InfixToPostfix(equation);
    itp.convertExprToPostfix();
    double returnval = itp.evalPostFix();
    System.out.println("Answer is " + returnval);
  }
}
