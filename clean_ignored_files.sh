git ls-files --ignored --exclude-standard -z | xargs -0 git rm --cached
git commit -am "Remove ignored files"