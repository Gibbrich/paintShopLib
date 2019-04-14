package optimized

fun main(args: Array<String>) {
    val arguments = getArguments(args)
    val inputFile = getInputFile(arguments.inputFilePath)
    val cases = parseInput(inputFile)
    val answers = getAnswers(cases)

    if (arguments.outputFilePath == null) {
        answers.forEach(::println)
    } else {
        writeOutput(arguments.outputFilePath, answers)
    }
}