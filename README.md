# Calculator

A small example of a calculator app, with parser, intermediate representation,
interpreter, and a REPL.

### Running

- You can start up a REPL with `sbt run`. Type `Ctrl-D` or `Ctrl-C` to exit.
- You can run the interpreter on a file with `sbt "run <path to file>"` (for example,
  `sbt "run test.calc"`)

## Rewrite the parser to encode precedence

Rewrite the parser so that it implements the following grammar:

```
        n ∈ ℤ
e ∈ Expr ::= e + t | e - t | t
t ∈ Term ::= t * f | t / f | f
f ∈ Fact ::= n | ( e )
```

Check that precedence is implemented correctly by running the interpreter on a few
examples. You can also run the tests with `sbt test`.
