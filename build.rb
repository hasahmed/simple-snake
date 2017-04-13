def main()
    if ARGV.length == 0
        system("javac *.java")
        return
    end
    if ARGV.include?("compile")
        system("javac *.java")
    end
    if ARGV.include?("jar")
        system("jar cfm snake.jar Manifest.txt *.class")
        system("rm *.class")
    end
end
main()
