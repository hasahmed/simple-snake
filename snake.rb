class Snake < Formula
  desc "simple snake game. Written in pure java"
  homepage "http://silo.cs.indiana.edu:32903/snake/snakehome.html"
  url "http://silo.cs.indiana.edu:32903/snake/snake.tar.gz"
  version "1.0.0"
  sha256 "8da49ab43bfa442fc21890c49cc317e7393aa2e82112b8cd55b19f2373e795a6"
  depends_on "ant" => :build
  depends_on :java
  def install
    system "ant", "compile", "jar"
    mv "snake.jar", "#{prefix}/"
    bin.install "snake"
  end
  test do
    system "java", "-jar", "#{prefix}/snake.jar", "--version"
  end
end
