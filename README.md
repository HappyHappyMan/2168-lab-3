2168-lab-3
==========

from http://lucas.cis.temple.edu/~lafollet/2168/Lab3.txt:

                   Lab 3

The goal of this lab is to create a routine that can accept a
string containing an infix expression of double constants
and evaluate it, returning its value.

The easiest way to do this is to convert the infix expression into
postfix. Then use the following algorithm to evaluate the
postfix expression:
POSTFIX EXPRESSION EVALUATION
Each time you encounter a number, push it onto the stack.  Each time
you encounter an operand, pop two numbers off the stack, combine
them according to the operator, and push the result back on the stack.
If the expression was properly formulated, you should be left with one
thing on the stack which is the value of the expression.

INFIX to POSTFIX conversion:
For our purposes, the infix expression may contain doubles,
the four operators +, -, /, *, and opening and closing parentheses ().

Assume that + and - have the same priority, * and / have a higher
priority.


Implement the following algorithm---

Create a Queue A and a Stack B
    While (there are still tokens in the input array)
    {
      get the next token
      if it is a constant (double), enqueue it into A
      else if it is a (, push it into B
      else if it is an operator
      {
        if (B is empty, or if the priority of B has a lower priority than
        the token)
          push the new operator onto B
        else
        {
          while (!B.isEmpty() && B.peek() != '(') 
          {
            A.enqueue(B.pop());
          }
          push the new operator onto B
        }
      }
      if the next token is a ')'
      {
        while (!B.isempty() && B.peek() != '(')
        {
          A.enqueue(B.pop());
        }
        B.pop();
      }
    } //end of while
    while (!B.isempty() {A.enqueue(B.pop());}


At this point, A should be a queue containg the post-fix equivalent
of the infix expression.  You know how to evaluate  this.  Do it.

Finally, test this by writing  program that prompts you for an
expression and prints the value.