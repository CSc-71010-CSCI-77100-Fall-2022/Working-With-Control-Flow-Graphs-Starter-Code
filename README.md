# Working With Control-Flow Graphs

## Steps

1. Visit https://classroom.github.com/a/PBW6Wpbc and set up your repository.
1. Clone your repository, for example:
    ```bash
    git clone https://github.com/CSc-71010-CSCI-77100-Fall-2022/working-with-control-flow-graphs-khatchad.git
    ```
1. In the directory that gets created as a result of the clone, run the following (CTA means compile-time analysis).
    ```bash
    mvn clean install
    java -jar target/CTA-0.0.1-SNAPSHOT.jar JLex.jar
    ```
1. The analysis may take a while, so don't give up. When it's finished, you'll see some output for particular methods declared in the classes within `JLex.jar`.
1. Have a look at the source code with an editor or open the project in Eclipse, for example:
    ```bash
    vim src/main/java/cta/Main.java
    ```

## Measurements

Change the code to measure the following:

1. Total number of CFG nodes in all CFGs built for the methods in jlex.
1. Number of edges in those CFGs.
1. Average number of instructions per basic block in those CFGs.

## Printing

Print each block. For each block ending with a conditional branch, immediately following the block, also print:

1. a tab and its taken successor on its own line.
1. a tab and its not taken successor, also on its own line.

## Example

Suppose we are processing the application method `LJLex/SparseBitSet$4.hasMoreElements()Z`, the method has only one block (block 4) that ends in a conditional instruction (with index 5), its taken successor is block 6, and its not taken successor is block 5. Then, the complete output for this method would be:

    Processing application method: < Application, LJLex/SparseBitSet$4, hasMoreElements()Z >
    BB[SSA:5..5]4 - JLex.SparseBitSet$4.hasMoreElements()Z
        BB[SSA:8..8]6 - JLex.SparseBitSet$4.hasMoreElements()Z
        BB[SSA:6..7]5 - JLex.SparseBitSet$4.hasMoreElements()Z

## Questions

Answer the following question:

1. Explain how the average number of instructions per basic block can be less than 1.
1. Explain each component of the block output. What do the numbers mean?

## Submission

1. Submit your code through GitHub by the deadline in Blackboard.
1. Also submit, in Blackboard, measurements and the answers to questions above for jlex.

## Credits

This is assignment is based on an assignment by Atanas Rountev.
