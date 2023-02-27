package calculator.tools

import Console.{RED, RESET}
import util.control.Breaks._

import calculator.ir._
import calculator.parser._
import calculator.semantics._

/** Format an error message */
def error(message: String): String =
  s"${RESET}${RED}[Error] ${message}${RESET}"

/** Evaluate an AST, but catch arithmetic exceptions */
def doEval(ast: Expr): String =
  try {
    eval(ast).toString
  } catch {
    case e: ArithmeticException => error(e.getMessage)
  }

/** A simple REPL for the calculator */
def repl(prompt: String = "> ") =
  breakable {
    try {
      while (true) {
        // Read input from user
        print(prompt)
        val input = scala.io.StdIn.readLine()

        // Parser input. If successful, evaluate the AST and print the result
        CalcParser(input) match
          case CalcParser.Success(ast, _) => println(doEval(ast))
          case e: CalcParser.NoSuccess    => println(error(e.toString))
      }
    } catch {
      case e: Exception => break // end the loop
    }
  }
