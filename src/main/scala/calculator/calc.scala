import scala.io.Source
import Console.{RED, RESET}
import util.control.Breaks._

import calculator.parser._
import calculator.ir._
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

/** Parse a line and potentially evaluate it */
def parseAndEvalLine(input: String) =
  CalcParser(input) match
    case CalcParser.Success(ast, _) => println(doEval(ast))
    case e: CalcParser.NoSuccess    => println(error(e.toString))

/** Parse a file and potentially evaluate it */
def runFile(filename: String): Unit =
  try {
    val input = Source.fromFile(filename).mkString
    parseAndEvalLine(input)
  } catch {
    case e: java.io.FileNotFoundException => println(error(e.getMessage))
  }

/** A simple REPL for the calculator */
def repl(prompt: String = "> ") =
  breakable {
    try {
      while (true) {
        // Read input from user
        print(prompt)
        val input = scala.io.StdIn.readLine()
        parseAndEvalLine(input)
      }
    } catch {
      case e: Exception => break // end the loop
    }
  }

@main
def main(args: String*): Unit =
  if args.isEmpty then repl()
  else runFile(args(0))
