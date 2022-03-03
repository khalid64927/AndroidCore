#!/bin/bash
#
# Purpose of this script is to create Symbolic link
#
mkdir -p -v .git/hooks
cd .git/hooks/
ln -s -f ../../scripts/pre-commit
ln -s -f ../../scripts/pre-push
echo "Symbolic link created"

