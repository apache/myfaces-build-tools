# Unix/Linux shell script to clean up whitespace in all .java files in a directory.
#
# All tabs found are replaced with 4 spaces.
#
# Add the following to also remove trailing whitespace from all lines:
#    -e "s/\s\s*$//"  \
find . -name ".svn" -prune -o -name "*.java" -exec \
  sed --in-place \
    -e "s/\t/    /g"  \
  {} \;
