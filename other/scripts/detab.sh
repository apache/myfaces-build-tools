# Unix/Linux shell script to clean up whitespace in all .java files in a directory.
#
# All tabs found are replaced with 4 spaces.
# All trailing whitespace on lines is removed.
find . -name ".svn" -prune -o -name "*.java" -exec \
  sed --in-place \
    -e "s/\t/    /g"  \
    -e "s/\s\s*$//"  \
  {} \;
