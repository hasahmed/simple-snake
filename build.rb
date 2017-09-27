#!/usr/bin/ruby
def main(cp)
    if ARGV.length == 0
        system("javac -cp #{cp} *.java")
        return
    end
    if ARGV.include?("compile")
        system("javac -cp #{cp} *.java")
    end
    if ARGV.include?("jar")
        system("jar cfm snake.jar Manifest.txt *.class")
        system("rm *.class")
    end
    if ARGV.include?("run")
        system("java -cp #{cp} Snake")
    end
end
classpath = "./jasypt-1.9.2.jar:."
main(classpath)
